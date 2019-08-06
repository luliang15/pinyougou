package com.pinyougou.seckill.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录跳转控制器
 * @author Steven
 * @description com.pinyougou.seckill.controller
 */
@Controller
public class RedirectController {
    /**
     * 跳转到请求前的页面
     * @param url 通过读取请求头信息Referer获取跳转到这里前的url
     * @return
     */
    @RequestMapping("jump")
    public String jump(@RequestHeader(value = "Referer")String url){
        //" "  == true
        if(StringUtils.isNotBlank(url)){
            //跳转到请求前的url中
            return "redirect:" + url;
        }else{
            //默认跳回秒杀首页
            return "redirect:/seckill-index.html";
        }
    }
}
