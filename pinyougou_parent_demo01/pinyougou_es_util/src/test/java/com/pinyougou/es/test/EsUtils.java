package com.pinyougou.es.test;

import com.alibaba.fastjson.JSON;
import com.pinyougou.entity.EsItem;
import com.pinyougou.es.dao.ItemDao;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program:PinYouGou01
 * @description:实现商品数据的查询
 * @author:Mr.lu
 * @create:2019-07-19 17:32
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class EsUtils {
    @Autowired
    //数据访问对象
    private TbItemMapper itemMapper;
    @Autowired
    private ItemDao itemDao;
    private EsItem esItem;

    @Test
    public void testImporData(){
        final TbItem where = new TbItem();
        //只导入已审核的商品
        //where.setSeller("1");
        final List<TbItem> items = itemMapper.select(null);
        System.out.println("总共将要导入:"+items.size()+"个商品");
        System.out.println("开始组装导入的数据");
        //es数据实体的列表
        List<EsItem> esItemList = new ArrayList<>();

        for (TbItem item : items) {
            esItem = new EsItem();
            //使用spring的BeanUtils深克隆对象
            //相当于TbiTem索引的属性姓名与数据类型相同的属性值设置到EsItem中
            BeanUtils.copyProperties(item,esItem);
            //注意这里的价格的类型不一样需要单独设置
            esItem.setPrice(item.getPrice().doubleValue());
            //嵌套域规格数据的绑定
            Map specMap= JSON.parseObject(item.getSpec(),Map.class);
            esItem.setSpec(specMap);
            //组装es实体类列表
            esItemList.add(esItem);
        }
        System.out.println("开始导入到索引库");
        itemDao.saveAll(esItemList);
        System.out.println("导入索引库导入完毕...");
    }

}
