package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车业务逻辑实现
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.cart.service.impl
 * @date 2019-7-30
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1.根据商品SKU ID查询SKU商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        if(item == null){
            throw new RuntimeException("要添加的商品信息不存在，或者已下架！");
        }else {
            //2.获取商家ID
            String sellerId = item.getSellerId();
            //3.根据商家ID判断购物车列表中是否存在该商家的购物车
            Cart cart = this.searchCartBySellerId(cartList,sellerId);
            //4.如果购物车列表中不存在该商家的购物车
            if(cart == null){
                //4.1 新建购物车对象
                cart = new Cart();
                cart.setSellerId(sellerId); //商家id
                cart.setSellerName(item.getSeller());  //商家名称
                //商品列表
                List<TbOrderItem> orderItems = new ArrayList<>();
                //创建商品信息
                TbOrderItem orderItem = createOrderItem(item,num);
                orderItems.add(orderItem);
                cart.setOrderItemList(orderItems);
                //4.2 将新建的购物车对象添加到购物车列表
                cartList.add(cart);
            }else{  //5.如果购物车列表中存在该商家的购物车
                // 查询购物车明细列表中是否存在该商品
                TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(),itemId);
                //5.1. 如果没有，新增购物车明细
                if(orderItem == null){
                    orderItem = this.createOrderItem(item, num);
                    //追加商品列表
                    cart.getOrderItemList().add(orderItem);
                }else {
                    //5.2. 如果有，在原购物车明细上添加数量，更改金额
                    orderItem.setNum(orderItem.getNum() + num);
                    //计算小计
                    double totalFee = 0.0;  //小计
                    totalFee = orderItem.getNum() * item.getPrice().doubleValue();
                    orderItem.setTotalFee(new BigDecimal(totalFee));
                    //如果修改数量后，当前购买数量不足1
                    if(orderItem.getNum() < 1){
                        //删除当前商品信息
                        cart.getOrderItemList().remove(orderItem);

                        //如果删除商品后，购买的商品列表没有了
                        if(cart.getOrderItemList().size() < 1){
                            //删除整个购物车节点
                            cartList.remove(cart);
                        }
                    }
                }
            }
        }
        return cartList;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if(cartList == null){
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username, cartList);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        //把第一个集合，合并到第二个集合中
        for (Cart cart : cartList1) {
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                this.addGoodsToCartList(cartList2, orderItem.getItemId(), orderItem.getNum());
            }
        }
        return cartList2;
    }

    /**
     * 根据商品skuId查找当前商家商品列表中有没有添加过当前商品
     * @param orderItemList 要查找的商品列表
     * @param itemId 要查找的商品ID
     * @return 结果(null || 找到商品信息)
     */
    private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {
        for (TbOrderItem orderItem : orderItemList) {
            //如果找到了对应商品
            if(orderItem.getItemId().longValue() == itemId.longValue()){
                return orderItem;
            }
        }
        return null;
    }

    /**
     * 创建购物车商品信息
     * @param item 当前要添加的商品
     * @param num 购买数量
     * @return 购物车商品信息
     */
    private TbOrderItem createOrderItem(TbItem item, Integer num) {
        if(num < 1){
            throw new RuntimeException("购买的数量不正确!");
        }
        TbOrderItem orderItem = new TbOrderItem();
        orderItem.setItemId(item.getId());   //skuId
        orderItem.setGoodsId(item.getGoodsId());  //spuId
        orderItem.setTitle(item.getTitle());  //标题
        orderItem.setPrice(item.getPrice());  //单价
        orderItem.setNum(num);  //购买数量
        double totalFee = 0.0;  //小计
        totalFee = num * item.getPrice().doubleValue();
        orderItem.setTotalFee(new BigDecimal(totalFee));
        orderItem.setPicPath(item.getImage());  //图片
        orderItem.setSellerId(item.getSellerId());  //商家id
        return orderItem;
    }

    /**
     * 根据商家id查询购物车信息
     * @param cartList 当前用户的购物车列表
     * @param sellerId 商家id
     * @return 当前用户对应当前商家的购物车对象cart{sellerId,sellerName,[]}，返回null代表找不到
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            //找到相应的商家
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }
}
