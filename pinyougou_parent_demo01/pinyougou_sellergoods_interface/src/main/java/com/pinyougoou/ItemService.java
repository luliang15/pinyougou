package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbItem;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface ItemService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbItem> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbItem> findPage(int pageNum, int pageSize, TbItem item);
	
	
	/**
	 * 增加
	*/
	public void add(TbItem item);
	
	
	/**
	 * 修改
	 */
	public void update(TbItem item);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItem getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
