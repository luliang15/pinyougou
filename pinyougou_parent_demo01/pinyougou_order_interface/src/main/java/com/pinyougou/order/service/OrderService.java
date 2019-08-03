package com.pinyougou.order.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbOrder;

import java.util.List;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface OrderService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbOrder> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbOrder> findPage(int pageNum, int pageSize, TbOrder order);
	
	
	/**
	 * 增加
	*/
	public void add(TbOrder order);
	
	
	/**
	 * 修改
	 */
	public void update(TbOrder order);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbOrder getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
