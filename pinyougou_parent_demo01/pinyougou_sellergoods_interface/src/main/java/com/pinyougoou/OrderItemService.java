package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbOrderItem;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface OrderItemService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbOrderItem> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbOrderItem> findPage(int pageNum, int pageSize, TbOrderItem orderItem);
	
	
	/**
	 * 增加
	*/
	public void add(TbOrderItem orderItem);
	
	
	/**
	 * 修改
	 */
	public void update(TbOrderItem orderItem);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbOrderItem getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
