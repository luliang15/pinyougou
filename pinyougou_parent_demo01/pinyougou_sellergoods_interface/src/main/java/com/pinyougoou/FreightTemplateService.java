package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbFreightTemplate;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface FreightTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbFreightTemplate> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbFreightTemplate> findPage(int pageNum, int pageSize, TbFreightTemplate freightTemplate);
	
	
	/**
	 * 增加
	*/
	public void add(TbFreightTemplate freightTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbFreightTemplate freightTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbFreightTemplate getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
