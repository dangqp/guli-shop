package com.dangqp.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dangqp.acl.entity.AclRole;
import com.dangqp.acl.service.AclRoleService;
import commonutils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/admin/acl/role")
//@CrossOrigin
public class AclRoleController {
    @Autowired
    private AclRoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            AclRole role) {
        Page<AclRole> pageParam = new Page<>( page, limit );
        QueryWrapper<AclRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty( role.getRoleName() )) {
            wrapper.like( "role_name", role.getRoleName() );
        }
        roleService.page( pageParam, wrapper );
        return Result.ok().data( "items", pageParam.getRecords() ).data( "total", pageParam.getTotal() );
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        AclRole role = roleService.getById( id );
        return Result.ok().data( "item", role );
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Result save(@RequestBody AclRole role) {
        roleService.save( role );
        return Result.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public Result updateById(@RequestBody AclRole role) {
        roleService.updateById( role );
        return Result.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        roleService.removeById( id );
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds( idList );
        return Result.ok();
    }
}


