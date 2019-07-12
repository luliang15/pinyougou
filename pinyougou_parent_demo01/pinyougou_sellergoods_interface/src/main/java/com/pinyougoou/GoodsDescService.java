package com.pinyougoou;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbGoodsDesc;


/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface GoodsDescService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoodsDesc> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbGoodsDesc> findPage(int pageNum, int pageSize, TbGoodsDesc goodsDesc);
	
	
	/**
	 * 增加
	*/
	public void add(TbGoodsDesc goodsDesc);
	
	
	/**
	 * 修改
	 */
	public void update(TbGoodsDesc goodsDesc);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoodsDesc getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	
}
