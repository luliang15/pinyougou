package com.pinyougoou;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;

/***
 * 品牌业务层逻辑接口
 */
public interface BrandService {
    /**
     * 查询所有品牌
     *
     * @return
     */
    public List<TbBrand> findAll();

    /***
     * 分页查询品牌列表
     * PageResult:页面结果
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult<TbBrand> findPage(int pageNum, int pageSize);

    /**
     * 新增品牌
     *
     * @param brand
     */
    public void add(TbBrand brand);

    /***
     * 根据id进行查询数据
     * @param id
     * @return
     */
    public TbBrand getById(Long id);

    /***
     * 修改品牌
     * @param brand
     */
    public void update(TbBrand brand);

    /***
     * 根据id列表进行删除
     * 删除品牌
     * @param ids
     */
    public void delete(Long[] ids);
}
