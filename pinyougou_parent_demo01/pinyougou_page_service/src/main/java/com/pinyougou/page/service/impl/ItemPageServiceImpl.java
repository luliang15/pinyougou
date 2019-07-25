package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.page.service.impl
 * @date 2019-7-24
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Value("${PAGEDIR}")
    private String PAGEDIR;

    @Override
    public boolean genItemHtml(Long goodsId) {
        try {
            //1、先查询商品相关信息
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            //查询商品三级分类的名称
            String category1Name = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String category2Name = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String category3Name = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();

            //查询商品sku列表
            Example example = new Example(TbItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("goodsId", goodsId);//指定spu id
            criteria.andEqualTo("status", "1");//只查询有效的sku
            example.setOrderByClause("isDefault desc"); //按默认降序，为了第一个选中默认的
            List<TbItem> itemList = itemMapper.selectByExample(example);


            //2、使用Freemarker生成页面
            Configuration cfg = freeMarkerConfigurer.getConfiguration();
            //2.1读取模板
            Template template = cfg.getTemplate("item.ftl");
            //数据模型
            Map map = new HashMap();
            //返回商品基本数据
            map.put("goods", goods);
            map.put("goodsDesc", goodsDesc);
            map.put("category1Name", category1Name);
            map.put("category2Name", category2Name);
            map.put("category3Name", category3Name);
            map.put("itemList", itemList);
            //输出路径
            Writer out = new FileWriter(PAGEDIR + goodsId + ".html");
            //执行输出
            template.process(map,out);
            //关闭文件流
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
