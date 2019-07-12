package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbCities;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface CitiesService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbCities> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbCities> findPage(int pageNum, int pageSize, TbCities cities);
	
	
	/**
	 * 增加
	*/
	public void add(TbCities cities);
	
	
	/**
	 * 修改
	 */
	public void update(TbCities cities);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbCities getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
