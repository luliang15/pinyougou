package com.pinyougoou;

import com.pinyougou.pojo.TbBrand;

import java.util.List;

/***
 * 品牌业务层逻辑接口
 */
public interface BrandService {
    /**
     * 查询所有品牌
     * @return
     */
    public List<TbBrand> findAll();


}
