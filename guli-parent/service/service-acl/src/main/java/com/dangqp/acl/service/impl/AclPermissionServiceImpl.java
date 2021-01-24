package com.dangqp.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dangqp.acl.entity.AclPermission;
import com.dangqp.acl.entity.AclRolePermission;
import com.dangqp.acl.entity.AclUser;
import com.dangqp.acl.helper.MemuHelper;
import com.dangqp.acl.helper.PermissionHelper;
import com.dangqp.acl.mapper.AclPermissionMapper;
import com.dangqp.acl.service.AclPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dangqp.acl.service.AclRolePermissionService;
import com.dangqp.acl.service.AclUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
@Service
public class AclPermissionServiceImpl extends ServiceImpl<AclPermissionMapper, AclPermission> implements AclPermissionService {

    @Autowired
    private AclRolePermissionService rolePermissionService;

    @Autowired
    private AclUserService userService;

    @Override
    public List<AclPermission> queryAllMenu() {
        QueryWrapper<AclPermission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc( "id" );
        List<AclPermission> permissionList = baseMapper.selectList( wrapper );

        List<AclPermission> result = bulid( permissionList );

        return result;
    }

    @Override
    public List<AclPermission> selectAllMenu(String roleId) {
        List<AclPermission> allPermissionList = baseMapper.selectList( new QueryWrapper<AclPermission>().orderByAsc( "CAST(id AS SIGNED)" ) );

        //根据角色id获取角色权限
        List<AclRolePermission> rolePermissionList = rolePermissionService.list( new QueryWrapper<AclRolePermission>().eq( "role_id", roleId ) );
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            AclPermission permission = allPermissionList.get( i );
            for (int m = 0; m < rolePermissionList.size(); m++) {
                AclRolePermission rolePermission = rolePermissionList.get( m );
                if (rolePermission.getPermissionId().equals( permission.getId() )) {
                    permission.setSelect( true );
                }
            }
        }

        List<AclPermission> permissionList = bulid( allPermissionList );
        return permissionList;
    }

    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {

        rolePermissionService.remove( new QueryWrapper<AclRolePermission>().eq( "role_id", roleId ) );

        List<AclRolePermission> rolePermissionList = new ArrayList<>();
        for (String permissionId : permissionIds) {
            if (StringUtils.isEmpty( permissionId )) continue;

            AclRolePermission rolePermission = new AclRolePermission();
            rolePermission.setRoleId( roleId );
            rolePermission.setPermissionId( permissionId );
            rolePermissionList.add( rolePermission );
        }
        rolePermissionService.saveBatch( rolePermissionList );
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {

        List<String> idList = new ArrayList<>();
        this.selectChildListById( id, idList );

        idList.add( id );
        baseMapper.deleteBatchIds( idList );
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin( id )) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId( id );
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<AclPermission> selectPermissionList = null;
        if (this.isSysAdmin( userId )) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList( null );
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId( userId );
        }

        List<AclPermission> permissionList = PermissionHelper.bulid( selectPermissionList );
        List<JSONObject> result = MemuHelper.bulid( permissionList );
        return result;
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {

        AclUser user = userService.getById( userId );

        if (null != user && "admin".equals( user.getUsername() )) {
            return true;
        }
        return false;
    }

    /**
     * 递归获取子节点
     *
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<AclPermission> childList = baseMapper.selectList( new QueryWrapper<AclPermission>().eq( "pid", id ).select( "id" ) );
        childList.stream().forEach( item -> {
            idList.add( item.getId() );
            this.selectChildListById( item.getId(), idList );
        } );
    }

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes
     * @return
     */
    private static List<AclPermission> bulid(List<AclPermission> treeNodes) {
        List<AclPermission> trees = new ArrayList<>();
        treeNodes.stream().forEach( treeNode -> {
            if ("0".equals( treeNode.getPid() )) {
                treeNode.setLevel(1);
                trees.add( findChildren( treeNode, treeNodes ) );
            }
        } );
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static AclPermission findChildren(AclPermission parentTreeNode, List<AclPermission> treeNodes) {

        //为treeNode子节点初始化
        parentTreeNode.setChildren( new ArrayList<>() );

        treeNodes.stream().forEach( it -> {
            //节点id和父类节点ID比较
            if (parentTreeNode.getId().equals( it.getPid() )) {
                int level = parentTreeNode.getLevel() + 1;
                it.setLevel( level );
                if (parentTreeNode.getChildren() == null) {
                    parentTreeNode.setChildren( new ArrayList<>() );
                }
                parentTreeNode.getChildren().add( findChildren( it, treeNodes ) );
            }
        } );
        return parentTreeNode;
    }


    //========================递归查询所有菜单================================================
    //获取全部菜单
    @Override
    public List<AclPermission> queryAllMenuGuli() {
        //1 查询菜单表所有数据
        QueryWrapper<AclPermission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc( "id" );
        List<AclPermission> permissionList = baseMapper.selectList( wrapper );
        //2 把查询所有菜单list集合按照要求进行封装
        List<AclPermission> resultList = bulidPermission( permissionList );
        return resultList;
    }

    //把返回所有菜单list集合进行封装的方法
    public static List<AclPermission> bulidPermission(List<AclPermission> permissionList) {

        //创建list集合，用于数据最终封装
        List<AclPermission> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        permissionList.stream().forEach( permissionNode -> {
            //得到顶层菜单 pid=0菜单
            if ("0".equals( permissionNode.getPid() )) {
                //设置顶层菜单的level是1
                permissionNode.setLevel( 1 );
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add( selectChildren( permissionNode, permissionList ) );
            }
        } );
        return finalNode;
    }

    private static AclPermission selectChildren(AclPermission permissionNode, List<AclPermission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        permissionList.stream().forEach( it -> {
            //判断 id和pid值是否相同
            if (permissionNode.getId().equals( it.getPid() )) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel() + 1;
                it.setLevel( level );
                //如果children为空，进行初始化操作
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren( new ArrayList<AclPermission>() );
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add( selectChildren( it, permissionList ) );
            }
        } );
        return permissionNode;
    }

    //============递归删除菜单==================================
    @Override
    public void removeChildByIdGuli(String id) {
        //1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById( id, idList );
        //把当前id封装到list里面
        idList.add( id );
        baseMapper.deleteBatchIds( idList );
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(String id, List<String> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<AclPermission> wrapper = new QueryWrapper<>();
        wrapper.eq( "pid", id );
        wrapper.select( "id" );
        List<AclPermission> childIdList = baseMapper.selectList( wrapper );
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach( item -> {
            //封装idList里面
            idList.add( item.getId() );
            //递归查询
            this.selectPermissionChildById( item.getId(), idList );
        } );
    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<AclRolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for (String perId : permissionIds) {
            //RolePermission对象
            AclRolePermission rolePermission = new AclRolePermission();
            rolePermission.setRoleId( roleId );
            rolePermission.setPermissionId( perId );
            //封装到list集合
            rolePermissionList.add( rolePermission );
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch( rolePermissionList );
    }
}
