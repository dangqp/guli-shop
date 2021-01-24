package com.dangqp.acl.controller;


import com.dangqp.acl.entity.AclPermission;
import com.dangqp.acl.service.AclPermissionService;
import commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class AclPermissionController {

    @Autowired
    private AclPermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public Result indexAllPermission() {
        List<AclPermission> list = permissionService.queryAllMenu();
        return Result.ok().data( "children", list );
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        permissionService.removeChildById( id );
        return Result.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(String roleId, String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli( roleId, permissionId );
        return Result.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<AclPermission> list = permissionService.selectAllMenu( roleId );
        return Result.ok().data( "children", list );
    }


    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody AclPermission permission) {
        permissionService.save( permission );
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody AclPermission permission) {
        permissionService.updateById( permission );
        return Result.ok();
    }

}
