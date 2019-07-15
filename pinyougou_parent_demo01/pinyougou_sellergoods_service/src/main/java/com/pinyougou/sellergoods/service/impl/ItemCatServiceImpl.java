package com.pinyougou.sellergoods.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.ItemCatService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;


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
        //有多少次查询多少次
        for (Long id : ids) {
            this.findAllIds(id, longs);
        }
        //构建查询条件
        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        //itemCatMapper.deleteByExample(example);
    }

    /**
     * 递归查询所有的子节点
     *
     * @param id
     * @param ids
     */
    private void findAllIds(Long id, List<Long> ids) {
        ids.add(id);
        //获取子节点的Id
        final List<TbItemCat> itemCatList = this.findByParentId(id);
        for (TbItemCat tbItemCat : itemCatList) {
            //查询当前节点有没有子节点
            TbItemCat where = new TbItemCat();
            where.setParentId(tbItemCat.getId());
            final int count = itemCatMapper.selectCount(where);
            //有子节点
            if (count > 0) {
                //调用自身的查询的子节点
                this.findAllIds(tbItemCat.getId(), ids);
            }else {
                //记录当前要查询的删除的ids
                ids.add(tbItemCat.getId());
            }
        }
    }

    /***
     * 根据父ID查询商品分类列表
     * @param parentId
     * @return
     */
    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        //主装查询的条件
        final TbItemCat where = new TbItemCat();
        where.setParentId(parentId);
        //查询数据
        final List<TbItemCat> catList = itemCatMapper.select(where);
        return catList;
    }


}
