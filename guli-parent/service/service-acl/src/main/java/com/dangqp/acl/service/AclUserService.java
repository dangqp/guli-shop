package com.dangqp.acl.service;

import com.dangqp.acl.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
public interface AclUserService extends IService<AclUser> {
    // 从数据库中取出用户信息
    AclUser selectByUsername(String username);
}
