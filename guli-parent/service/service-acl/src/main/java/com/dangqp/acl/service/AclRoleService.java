package com.dangqp.acl.service;

import com.dangqp.acl.entity.AclRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
public interface AclRoleService extends IService<AclRole> {
    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(String userId);

    //根据用户分配角色
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    List<AclRole> selectRoleByUserId(String id);

}
