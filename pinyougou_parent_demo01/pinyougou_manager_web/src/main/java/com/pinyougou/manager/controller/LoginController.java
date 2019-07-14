package com.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

/**
 * @program:PinYouGou01
 * @description:显示前端登陆用户名
 * @author:Mr.lu
 * @create:2019-07-14 19:13
 **/
@RestController
@RequestMapping("login")
public class LoginController {
    @RequestMapping("info")
    public Map<String,Object> name(){
        //从springSecuruty中获取登陆名
        final String name = SecurityContextHolder.getContext().getAuthentication().getName();
        final Map<String, Object> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
