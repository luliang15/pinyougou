package com.pinyougou.content.service;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbContentCategory;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface ContentCategoryService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbContentCategory> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbContentCategory> findPage(int pageNum, int pageSize, TbContentCategory contentCategory);
	
	
	/**
	 * 增加
	*/
	public void add(TbContentCategory contentCategory);
	
	
	/**
	 * 修改
	 */
	public void update(TbContentCategory contentCategory);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbContentCategory getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	
}
