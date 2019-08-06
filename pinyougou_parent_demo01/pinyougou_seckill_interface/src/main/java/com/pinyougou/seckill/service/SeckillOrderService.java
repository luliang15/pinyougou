package com.pinyougou.seckill.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbSeckillOrder;

import java.util.List;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface SeckillOrderService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeckillOrder> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbSeckillOrder> findPage(int pageNum, int pageSize, TbSeckillOrder seckillOrder);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeckillOrder seckillOrder);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeckillOrder seckillOrder);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeckillOrder getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 立即抢购
	 * @param seckillId 抢购商品id
	 * @param userId 下单用户
	 */
	public void submitOrder(Long seckillId,String userId);

}
