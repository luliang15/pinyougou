package com.pinyougou.shop.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougoou.SellerService;
import com.pinyougou.pojo.TbSeller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 扩展SpringSecurity认证方式
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.shop.service
 * @date 2019-7-14
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username + "进入方法loadUserByUsername....");
        //设置角色列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        //查询商家信息
        TbSeller seller = sellerService.getById(username);
        if(seller != null){
            //只有已审核通过的商家才能登录
            if("1".equals(seller.getStatus())){
                return new User(username,seller.getPassword(),authorities);
            }
        }
        //返回null对象，告诉SpringSecurity认证失败
        return null;
    }
}
