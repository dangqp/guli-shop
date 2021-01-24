package com.dangqp.acl.mapper;

import com.dangqp.acl.entity.AclPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
public interface AclPermissionMapper extends BaseMapper<AclPermission> {
    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<AclPermission> selectPermissionByUserId(String userId);
}
