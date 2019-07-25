package com.pinyougou.manager.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougoou.GoodsService;
import com.pinyougou.entity.EsItem;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.service.ItemSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;
    @Reference
    private ItemSearchService itemSearchService;
    @Reference
    private ItemPageService itemPageService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll(){
        return goodsService.findAll();
    }


    /**
     * 分页查询数据
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int pageNo, int pageSize, @RequestBody TbGoods goods){
        return goodsService.findPage(pageNo, pageSize,goods);
    }

    /**
     * 增加
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods){
        try {
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods){
        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Goods getById(Long id){
        return goodsService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            goodsService.delete(ids);
            //删除商品同时删除索引库
            itemSearchService.deleteByGoodsId(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            goodsService.updateStatus(ids, status);
            //审核通过
            if ("1".equals(status)) {
                List<TbItem> itemList = goodsService.findItemListByGoodsIdsAndStatus(ids, status);

                List<EsItem> esItemList = new ArrayList<>();
                EsItem esItem = null;
                for (TbItem item : itemList) {
                    esItem = new EsItem();
                    //深克隆，反射-copyProperties(源,目标)
                    //使用限制，两个对象之间属性名与数据类型完一致时，能自动属性copy
                    BeanUtils.copyProperties(item,esItem);
                    //类型不一样，需要手动绑定
                    esItem.setPrice(item.getPrice().doubleValue());

                    //设置嵌套域的数据-规格
                    Map specMap = JSON.parseObject(item.getSpec(), Map.class);
                    esItem.setSpec(specMap);

                    esItemList.add(esItem);
                }
                //更新索引库
                itemSearchService.importList(esItemList);

                //生成商品静态详情页
                for (Long id : ids) {
                    itemPageService.genItemHtml(id);
                }
            }
            return new Result(true, "操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "操作失败！");
    }

    /**
     * 生成静态页（测试）
     * @param goodsId
     */
    @RequestMapping("/genHtml")
    public boolean genHtml(Long goodsId){
        return itemPageService.genItemHtml(goodsId);
    }

}
