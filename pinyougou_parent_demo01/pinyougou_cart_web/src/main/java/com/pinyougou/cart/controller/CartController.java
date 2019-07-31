package com.pinyougou.cart.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.entity.Result;
import com.pinyougou.pojogroup.Cart;
import com.pinyougou.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.cart.controller
 * @date 2019-7-30
 */
@RestController
@RequestMapping("cart")
public class CartController {
    @Reference
    private CartService cartService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    //查询当前用户的购物车列表 --带合并功能的逻辑
    @RequestMapping("findCartList")
    public List<Cart> findCartList(){
        List<Cart> cartList = new ArrayList<>();
        //获取登录名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //先把cookie中的购物车信息读取出来
        String cartListStr = CookieUtil.getCookieValue(request, "cartList", true);
        if(cartListStr != null && cartListStr.length() > 0){
            //把json串转换为List<Cart>
            cartList = JSON.parseArray(cartListStr, Cart.class);
        }
        //未登录
        if("anonymousUser".equals(username)){
            System.out.println("从Cookie中读取了购物车数据....");
        }else{
            System.out.println("从Redis中读取了购物车数据....");
            //登录版本购物车查询方案....查询redis
            List<Cart> cartListFromRedis = cartService.findCartListFromRedis(username);
            //如果cookie中存在购物车数据
            if(cartList.size() > 0){
                System.out.println("合并了购物车....");
                //开始合并........合并后，要把合并的结果返回，并接收作为返回值
                cartList = cartService.mergeCartList(cartList, cartListFromRedis);
                //保存合并后的结果，保存到redis中
                cartService.saveCartListToRedis(username, cartList);
                //清除cookie中的购物车数据
                CookieUtil.deleteCookie(request,response,"cartList");
            }else{
                cartList = cartListFromRedis;
            }
        }
        return cartList;
    }

    //增删改购物车逻辑
    @RequestMapping("addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num){
        try {
            //查询当前用户的购物车列表
            List<Cart> cartList = this.findCartList();
            //增删改逻辑调用
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);
            //获取登录名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            //未登录
            if("anonymousUser".equals(username)){
                System.out.println("操作了Cookie中的购物车数据....");
                String jsonString = JSON.toJSONString(cartList);
                //把商品信息存入cookie
                CookieUtil.setCookie(request,response,"cartList",jsonString,60*60*24,true);
            }else{
                System.out.println("操作了Redis中的购物车数据....");
                //保存购物车到redis
                cartService.saveCartListToRedis(username, cartList);
            }
            return new Result(true, "操作成功！");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "操作失败！");
    }
}
