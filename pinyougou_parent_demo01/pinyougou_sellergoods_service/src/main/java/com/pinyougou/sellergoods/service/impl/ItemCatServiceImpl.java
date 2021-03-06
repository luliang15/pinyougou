package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougoou.ItemCatService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务逻辑实现
 *
 * @author Steven
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbItemCat> findAll() {
        return itemCatMapper.select(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbItemCat itemCat) {
        PageResult<TbItemCat> result = new PageResult<TbItemCat>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();

        if (itemCat != null) {
            //如果字段不为空
            if (itemCat.getName() != null && itemCat.getName().length() > 0) {
                criteria.andLike("name", "%" + itemCat.getName() + "%");
            }

        }

        //查询数据
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbItemCat> info = new PageInfo<TbItemCat>(list);
        result.setPages(info.getPages());

        return result;
    }

    /**
     * 增加
     */
    @Override
    public void add(TbItemCat itemCat) {
        itemCatMapper.insertSelective(itemCat);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbItemCat itemCat) {
        itemCatMapper.updateByPrimaryKeySelective(itemCat);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbItemCat getById(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        //数组转list
        //List longs = Arrays.asList(ids);

        //搜索出所有要删除的节点id
        List longs = new ArrayList();
        for (Long id : ids) {
            this.findAllIds(id, longs);
        }

        //构建查询条件
        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        itemCatMapper.deleteByExample(example);
    }

    /**
     * 递归查询所有子节点
     *
     * @param id
     * @param ids
     */
    private void findAllIds(Long id, List<Long> ids) {
        ids.add(id);
        List<TbItemCat> itemCatList = this.findByParentId(id);
        for (TbItemCat itemCat : itemCatList) {
            //查询当前节点还有没有子节点
            TbItemCat where = new TbItemCat();
            where.setParentId(itemCat.getId());
            int count = itemCatMapper.selectCount(where);
            //有子节点
            if (count > 0) {
                //调用自身查询子节点
                this.findAllIds(itemCat.getId(), ids);
            } else {
                //记录当前要删除的ids
                ids.add(itemCat.getId());
            }
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        //组装查询条件
        TbItemCat where = new TbItemCat();
        where.setParentId(parentId);
        //查询数据
        List<TbItemCat> catList = itemCatMapper.select(where);
        //将商品分类数据放入缓存(Hash)以分类名称作为key,以模板ID作为值
        //在这里写的原因时商品分类的增删改都会经过这个方法
        final List<TbItemCat> itemCats = findAll();
        for (TbItemCat itemCat : itemCats) {
            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());
        }
        return catList;
    }


}
