package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbTypeTemplate;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface TypeTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbTypeTemplate> findPage(int pageNum, int pageSize, TbTypeTemplate typeTemplate);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
