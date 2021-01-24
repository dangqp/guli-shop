package com.dangqp.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.dangqp.acl.entity.AclPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
public interface AclPermissionService extends IService<AclPermission> {
    //获取全部菜单
    List<AclPermission> queryAllMenu();

    //根据角色获取菜单
    List<AclPermission> selectAllMenu(String roleId);

    //给角色分配权限
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    //递归删除菜单
    void removeChildById(String id);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    //获取全部菜单
    List<AclPermission> queryAllMenuGuli();

    //递归删除菜单
    void removeChildByIdGuli(String id);

    //给角色分配权限
    void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionId);
}
