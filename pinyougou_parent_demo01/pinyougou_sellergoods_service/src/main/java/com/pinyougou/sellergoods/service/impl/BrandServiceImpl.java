package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougoou.BrandService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;

    /***
     * 查询所有
     * @return
     */
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.select(null);
    }

    /***
     * 分页查询品牌列表
     * 服务实现类
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageResult<TbBrand> result = new PageResult<TbBrand>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);
        //查询数据
        List<TbBrand> brands = brandMapper.select(null);
        //保存数据列表
        result.setRows(brands);

        //获取总页数
        PageInfo<TbBrand> info = new PageInfo<TbBrand>(brands);
        result.setPages(info.getPages());

        return result;
    }

    /***
     * 实现添加
     * 新增品牌
     * @param brand
     */
    @Override
    public void add(TbBrand brand) {
        brandMapper.insertSelective(brand);
    }

    /***
     * 根据id进行查询
     * @param id
     * @return
     */
    @Override
    public TbBrand getById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /***
     * 进行修改
     * @param brand
     */
    @Override
    public void update(TbBrand brand) {
          brandMapper.updateByPrimaryKeySelective(brand);
    }

    /***
     * 根据Id列表进行删除
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        //数据转list
        List longs=Arrays.asList(ids);
        //构建查询条件
        final Example example = new Example(TbBrand.class);
        final Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",longs);
        //根据条件删除数据
        brandMapper.deleteByExample(example);

    }

}
