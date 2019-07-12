package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbSpecificationOption;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface SpecificationOptionService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecificationOption> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbSpecificationOption> findPage(int pageNum, int pageSize, TbSpecificationOption specificationOption);
	
	
	/**
	 * 增加
	*/
	public void add(TbSpecificationOption specificationOption);
	
	
	/**
	 * 修改
	 */
	public void update(TbSpecificationOption specificationOption);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSpecificationOption getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
