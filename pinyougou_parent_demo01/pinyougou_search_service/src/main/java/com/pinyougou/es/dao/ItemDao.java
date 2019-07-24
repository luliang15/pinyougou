package com.pinyougou.es.dao;

import com.pinyougou.entity.EsItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program:PinYouGou01
 * @description:商品信息的ES接口
 * @author:Mr.lu
 * @create:2019-07-19 17:53
 **/
public interface ItemDao extends ElasticsearchRepository<EsItem, Long> {
    //ElasticsearchRepository删除语法：deleteBy+域名+匹配方式
    void deleteByGoodsIdIn(Long[] ids);

}
