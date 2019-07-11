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

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.sellergoods.service.impl
 * @date 2019-7-9
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.select(null);
    }

    @Override
    public PageResult<TbBrand> findPage(Integer pageNum, Integer pageSize, TbBrand brand) {
        PageResult<TbBrand> page = new PageResult<>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);
        /*List<TbBrand> brandList = brandMapper.select(brand);*/
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        //组装查询条件
        if(brand != null){
            //品牌名称过滤
            if(brand.getName() != null && brand.getName().trim().length() > 0){
                criteria.andLike("name", "%" + brand.getName() + "%");
            }

            //首字母过滤
            if(brand.getFirstChar() != null && brand.getFirstChar().trim().length() > 0){
                criteria.andEqualTo("firstChar", brand.getFirstChar());
            }
        }
        List<TbBrand> brandList = brandMapper.selectByExample(example);

        page.setRows(brandList);

        //返回总页数
        PageInfo<TbBrand> info = new PageInfo<>(brandList);
        page.setPages(info.getPages());

        return page;
    }

    @Override
    public void add(TbBrand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public TbBrand getById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbBrand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Long[] ids) {
        //方式一：循环删除，省略

        //方式二：delete tb_brand where id in (1,2) --推荐
        //把数组转成list
        List longs = Arrays.asList(ids);
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",longs);
        brandMapper.deleteByExample(example);
    }

}
