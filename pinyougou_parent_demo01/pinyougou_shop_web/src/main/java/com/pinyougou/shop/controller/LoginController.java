package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.manager.controller
 * @date 2019-7-14
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @RequestMapping("info")
    public Map info(){
        Map info = new HashMap();

        //获取SpringSecurity登录名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        info.put("loginName", username);

        return info;
    }
}
