package com.dangqp.eduservice.controller;

import commonutils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title:com.dangqp.eduservice.controller
 * Description:
 * Copyright: Copyright (c) 2021
 *
 * @author dangqp
 * @version 1.0
 * @created 2021/01/09  20:16
 */
@Api("登陆")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginController {


    @RequestMapping("login")
    public Result login() {
        return Result.ok().data( "token", "admin" );
    }

    @RequestMapping("info")
    public Result info() {
        return Result.ok().data( "roles", "admin" ).data( "name", "admin" ).data( "avatar", "https://b-gold-cdn.xitu.io/favicons/v2/favicon-32x32.png" );
    }

}
