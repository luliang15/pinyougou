package com.pinyougou.task;

import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * SpringTask入门案例
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.task
 * @date 2019-8-3
 */
@Component
public class SeckillGoodsTask {

    /*@Scheduled(cron = "* * * * * ?")
    public void startTask(){
        System.out.println("定时任务执行了startTask方法，当前时间为：" + new Date());
    }*/

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //5秒执行一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void refreshSeckillGoods(){
        //1、查询附合条件的秒杀商品
        //组装查询条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", "1");  //只查询审核通过的商品
        criteria.andGreaterThan("stockCount", 0);  //查询有货商品
        //startTime < now < endTime
        Date now = new Date();   //当前时间
        //开始时间 <= 当前时间
        criteria.andLessThanOrEqualTo("startTime", now);
        //结束时间 >= 当前时间
        criteria.andGreaterThanOrEqualTo("endTime", now);
        //排除在redis中已经存在的商品
        Set ids = redisTemplate.boundHashOps("seckillGoods").keys();
        if(ids != null && ids.size() > 0) {
            //Set转List
            List idList = new ArrayList(ids);
            criteria.andNotIn("id", idList);
        }
        //查询商品列表
        List<TbSeckillGoods> goodsList = seckillGoodsMapper.selectByExample(example);
        if(goodsList != null && goodsList.size() > 0) {
            //2、把秒杀商品导入redis
            for (TbSeckillGoods seckillGoods : goodsList) {
                System.out.println("商品id为" + seckillGoods.getId() + "，的商品信息同步到Redis中....");
                //把商品放入Redis,Hash<商品id,商品对象>
                redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);

                //第一次，先把商品所有库存读入redis
                //increment(操作的key,操作的值(可以是负数))
                redisTemplate.boundHashOps("seckillGoodsStockCount").increment(seckillGoods.getId(), seckillGoods.getStockCount());

            }
        }else {
            System.out.println("此次定时任务，没有找到附合条件的商品信息....");
        }
    }
}
