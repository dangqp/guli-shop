package com.dangqp.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dangqp.acl.entity.AclUser;
import com.dangqp.acl.service.AclRoleService;
import com.dangqp.acl.service.AclUserService;
import commonutils.MD5;
import commonutils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class AclUserController {
    @Autowired
    private AclUserService userService;

    @Autowired
    private AclRoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    AclUser userQueryVo) {
        Page<AclUser> pageParam = new Page<>( page, limit );
        QueryWrapper<AclUser> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty( userQueryVo.getUsername() )) {
            wrapper.like( "username", userQueryVo.getUsername() );
        }

        IPage<AclUser> pageModel = userService.page( pageParam, wrapper );
        return Result.ok().data( "items", pageModel.getRecords() ).data( "total", pageModel.getTotal() );
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public Result save(@RequestBody AclUser user) {
        user.setPassword( MD5.encrypt( user.getPassword() ) );
        userService.save( user );
        return Result.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public Result updateById(@RequestBody AclUser user) {
        userService.updateById( user );
        return Result.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        userService.removeById( id );
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds( idList );
        return Result.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId( userId );
        return Result.ok().data( roleMap );
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip( userId, roleId );
        return Result.ok();
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        AclUser aclUser = userService.getById(id);
        return Result.ok().data("item", aclUser);
    }
}

