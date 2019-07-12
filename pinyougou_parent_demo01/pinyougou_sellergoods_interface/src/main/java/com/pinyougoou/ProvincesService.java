package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbProvinces;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface ProvincesService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbProvinces> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbProvinces> findPage(int pageNum, int pageSize, TbProvinces provinces);
	
	
	/**
	 * 增加
	*/
	public void add(TbProvinces provinces);
	
	
	/**
	 * 修改
	 */
	public void update(TbProvinces provinces);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbProvinces getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
