package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.entity.EsItem;
import com.pinyougou.service.ItemSearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.search.service.impl
 * @date 2019-7-19
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Map search(Map searchMap) {
        Map map = new HashMap();
        //1、创建查询条件构建器-builder = new NativeSearchQueryBuilder()
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //2、先默认搜索所有商品-builder.withQuery(QueryBuilders.matchAllQuery())添加一个搜索条件
        builder.withQuery(QueryBuilders.matchAllQuery());
        //3、组装查询条件
        if(searchMap != null){
            //3.1关键字搜索-builder.withQuery(QueryBuilders.matchQuery(域名，内容))
            String keyword = searchMap.get("keyword") == null ? "" : searchMap.get("keyword").toString().trim();
            if(keyword.length() > 0){
                builder.withQuery(QueryBuilders.matchQuery("keyword", keyword));
            }
        }
        //4、获取NativeSearchQuery搜索条件对象-builder.build()
        NativeSearchQuery nativeSearchQuery = builder.build();
        //5.查询数据-esTemplate.queryForPage(条件对象,搜索结果对象)
        AggregatedPage<EsItem> page = elasticsearchTemplate.queryForPage(nativeSearchQuery, EsItem.class);
        //6、包装结果并返回
        map.put("rows", page.getContent());
        return map;
    }

}
