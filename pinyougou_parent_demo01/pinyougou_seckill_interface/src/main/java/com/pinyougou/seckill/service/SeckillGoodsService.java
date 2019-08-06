package com.pinyougou.seckill.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbSeckillGoods;

import java.util.List;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface SeckillGoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeckillGoods> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbSeckillGoods> findPage(int pageNum, int pageSize, TbSeckillGoods seckillGoods);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeckillGoods seckillGoods);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeckillGoods seckillGoods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeckillGoods getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 跟据id列表，更新状态
	 * @param ids 审核的列表
	 * @param status 审核的状态
	 */
	public void updateStatus(Long[] ids,String status);

	/**
	 * 查询当前正在参与秒杀的商品
	 * @return
	 */
	public List<TbSeckillGoods> findList();
}
