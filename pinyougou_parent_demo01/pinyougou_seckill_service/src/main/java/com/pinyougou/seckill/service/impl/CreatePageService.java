package com.pinyougou.seckill.service.impl;

import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.page.service.impl
 * @date 2019-7-24
 */
@Component
public class CreatePageService{
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;
    @Value("${PAGEDIR}")
    private String PAGEDIR;

    public boolean genItemHtml(Long seckillId) {
        try {
            //1、先查询商品相关信息
            TbSeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillId);
            //2、使用Freemarker生成页面
            Configuration cfg = freeMarkerConfigurer.getConfiguration();
            //2.1读取模板
            Template template = cfg.getTemplate("seckill-item.ftl");
            //数据模型
            Map map = new HashMap();
            //返回商品基本数据
            map.put("seckillGoods", seckillGoods);
            //输出路径
            //Writer out = new FileWriter(PAGEDIR + seckillId + ".html");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PAGEDIR + seckillId + ".html"), "UTF-8"));
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
