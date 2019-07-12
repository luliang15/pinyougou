package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbSpecification> findPage(int pageNum, int pageSize, TbSpecification specification);
	
	
	/**
	 * 增加
	*/
	public void add(Specification specification);
	
	
	/**
	 * 修改
	 */
	public void update(Specification specification);
	

	/**
	 * 根据ID获取实体
	 * Specification:规格
	 * @param id
	 * @return
	 */
	public Specification getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
