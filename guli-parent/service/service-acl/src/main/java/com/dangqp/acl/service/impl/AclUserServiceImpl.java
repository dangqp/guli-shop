package com.dangqp.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dangqp.acl.entity.AclUser;
import com.dangqp.acl.mapper.AclUserMapper;
import com.dangqp.acl.service.AclUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements AclUserService {
    @Override
    public AclUser selectByUsername(String username) {
        return baseMapper.selectOne( new QueryWrapper<AclUser>().eq( "username", username ) );
    }
}
