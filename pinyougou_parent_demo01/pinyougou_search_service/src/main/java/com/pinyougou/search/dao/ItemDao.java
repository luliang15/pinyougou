package com.pinyougou.search.dao;


import com.pinyougou.entity.EsItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * spring-data-es-dao开发回顾
 * @author Steven
 * @version 1.0
 * @description com.itheima.dao
 * @date 2019-7-19
 */
public interface ItemDao extends ElasticsearchRepository<EsItem,Long> {
}
