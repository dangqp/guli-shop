package com.dangqp.acl.helper;

import com.alibaba.fastjson.JSONObject;
import com.dangqp.acl.entity.AclPermission;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 */
public class MemuHelper {

    /**
     * 构建菜单
     * vue route 中定义的路由
     * {
     *     path: '/sta',
     *     component: Layout,
     *     redirect: '/sta/create',
     *     name: '统计分析',
     *     meta: { title: '统计分析', icon: 'example' },
     *     children: [
     *       {
     *         path: 'create',
     *         name: '生成数据',
     *         component: () => import('@/views/sta/create'),
     *         meta: { title: '生成数据', icon: 'table' }
     *       },
     *       {
     *         path: 'show',
     *         name: '图表显示',
     *         component: () => import('@/views/sta/show'),
     *         meta: { title: '图表显示', icon: 'tree' }
     *       }
     *     ]
     *   }
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> bulid(List<AclPermission> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            AclPermission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<AclPermission> oneMeunList = topNode.getChildren();
           for(AclPermission one :oneMeunList) {
               JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);
//
                List<JSONObject> children = new ArrayList<>();
//                List<AclPermission> twoMeunList = one.getChildren();
//                for(AclPermission two :twoMeunList) {
//                    JSONObject twoMeun = new JSONObject();
//                    twoMeun.put("path", two.getPath());
//                    twoMeun.put("component", two.getComponent());
//                    twoMeun.put("name", "name_"+two.getId());
//                    twoMeun.put("hidden", false);
//
//                    JSONObject twoMeta = new JSONObject();
//                    twoMeta.put("title", two.getName());
//                    twoMeun.put("meta", twoMeta);
//
//                    children.add(twoMeun);
//
//                    List<AclPermission> threeMeunList = two.getChildren();
//                    for(AclPermission three :threeMeunList) {
//                        if(StringUtils.isEmpty(three.getPath())) continue;
//
//                        JSONObject threeMeun = new JSONObject();
//                        threeMeun.put("path", three.getPath());
//                        threeMeun.put("component", three.getComponent());
//                        threeMeun.put("name", "name_"+three.getId());
//                        threeMeun.put("hidden", true);
//
//                        JSONObject threeMeta = new JSONObject();
//                        threeMeta.put("title", three.getName());
//                        threeMeun.put("meta", threeMeta);
//
//                        children.add(threeMeun);
//                    }
//                }
                oneMeun.put("children",  bulidPersion(one.getChildren(),children));
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }

    public static List<JSONObject> bulid1(List<AclPermission> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            AclPermission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<AclPermission> oneMeunList = topNode.getChildren();
            //bulidPersion(oneMeunList,meuns);
            for(AclPermission one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<AclPermission> twoMeunList = one.getChildren();
                for(AclPermission two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<AclPermission> threeMeunList = two.getChildren();
                    for(AclPermission three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }

    public static List<JSONObject> bulidPersion(List<AclPermission> twoMeunList,List<JSONObject> children){
        twoMeunList.stream().filter(one->!StringUtils.isEmpty(one.getPath())).forEach(two->{
            JSONObject twoMeun = new JSONObject();
            twoMeun.put("path", two.getPath());
            twoMeun.put("component", two.getComponent());
            twoMeun.put("name", "name_"+two.getId());
            boolean hiddenFllag = false;
            //第四级菜单不显示
            if (NumberUtils.compare(4,two.getLevel())==0){
                hiddenFllag=true;
            }
            twoMeun.put("hidden", hiddenFllag);

            JSONObject twoMeta = new JSONObject();
            twoMeta.put("title", two.getName());
            twoMeun.put("meta", twoMeta);
            children.add(twoMeun);
            bulidPersion(two.getChildren(),children);
        });
//        for(AclPermission two :twoMeunList) {
//            if (StringUtils.isEmpty(two.getPath()))
//                continue;
//            JSONObject twoMeun = new JSONObject();
//            twoMeun.put("path", two.getPath());
//            twoMeun.put("component", two.getComponent());
//            twoMeun.put("name", "name_"+two.getId());
//            twoMeun.put("hidden", false);
//
//            JSONObject twoMeta = new JSONObject();
//            twoMeta.put("title", two.getName());
//            twoMeun.put("meta", twoMeta);
//            children.add(twoMeun);
//            bulidPersion(two.getChildren(),children);
//        }
        return  children;
    }
}
