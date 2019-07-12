package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbPayLog;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface PayLogService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbPayLog> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize, TbPayLog payLog);
	
	
	/**
	 * 增加
	*/
	public void add(TbPayLog payLog);
	
	
	/**
	 * 修改
	 */
	public void update(TbPayLog payLog);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbPayLog getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
