package com.pinyougou.service;


import java.util.List;
import java.util.Map;

/**
 * @program:PinYouGou01
 * @description:搜索方法
 * @return:结果集,除了商品列表,还包含规格等等信息
 * @author:Mr.lu
 * @create:2019-07-19 18:49
 **/
public interface ItemSearchService {
    /**
     * 搜索方法
     *
     * @param searchMap 查询条件列表
     * @param searchMap
     */
    public Map search(Map searchMap);

    /**
     * 批量导入数据
     * @param list
     */
    public void importList(List list);

    /**
     * 根据id列表删除索引
     * @param goodsIdList
     */
    public void deleteByGoodsId(Long[] goodsIdList);

}
