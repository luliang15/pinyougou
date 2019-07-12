package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.OrderService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.pojo.TbOrder;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrder> findAll() {
		return orderMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbOrder order) {
		PageResult<TbOrder> result = new PageResult<TbOrder>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbOrder.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(order!=null){			
						//如果字段不为空
			if (order.getPaymentType()!=null && order.getPaymentType().length()>0) {
				criteria.andLike("paymentType", "%" + order.getPaymentType() + "%");
			}
			//如果字段不为空
			if (order.getPostFee()!=null && order.getPostFee().length()>0) {
				criteria.andLike("postFee", "%" + order.getPostFee() + "%");
			}
			//如果字段不为空
			if (order.getStatus()!=null && order.getStatus().length()>0) {
				criteria.andLike("status", "%" + order.getStatus() + "%");
			}
			//如果字段不为空
			if (order.getShippingName()!=null && order.getShippingName().length()>0) {
				criteria.andLike("shippingName", "%" + order.getShippingName() + "%");
			}
			//如果字段不为空
			if (order.getShippingCode()!=null && order.getShippingCode().length()>0) {
				criteria.andLike("shippingCode", "%" + order.getShippingCode() + "%");
			}
			//如果字段不为空
			if (order.getUserId()!=null && order.getUserId().length()>0) {
				criteria.andLike("userId", "%" + order.getUserId() + "%");
			}
			//如果字段不为空
			if (order.getBuyerMessage()!=null && order.getBuyerMessage().length()>0) {
				criteria.andLike("buyerMessage", "%" + order.getBuyerMessage() + "%");
			}
			//如果字段不为空
			if (order.getBuyerNick()!=null && order.getBuyerNick().length()>0) {
				criteria.andLike("buyerNick", "%" + order.getBuyerNick() + "%");
			}
			//如果字段不为空
			if (order.getBuyerRate()!=null && order.getBuyerRate().length()>0) {
				criteria.andLike("buyerRate", "%" + order.getBuyerRate() + "%");
			}
			//如果字段不为空
			if (order.getReceiverAreaName()!=null && order.getReceiverAreaName().length()>0) {
				criteria.andLike("receiverAreaName", "%" + order.getReceiverAreaName() + "%");
			}
			//如果字段不为空
			if (order.getReceiverMobile()!=null && order.getReceiverMobile().length()>0) {
				criteria.andLike("receiverMobile", "%" + order.getReceiverMobile() + "%");
			}
			//如果字段不为空
			if (order.getReceiverZipCode()!=null && order.getReceiverZipCode().length()>0) {
				criteria.andLike("receiverZipCode", "%" + order.getReceiverZipCode() + "%");
			}
			//如果字段不为空
			if (order.getReceiver()!=null && order.getReceiver().length()>0) {
				criteria.andLike("receiver", "%" + order.getReceiver() + "%");
			}
			//如果字段不为空
			if (order.getInvoiceType()!=null && order.getInvoiceType().length()>0) {
				criteria.andLike("invoiceType", "%" + order.getInvoiceType() + "%");
			}
			//如果字段不为空
			if (order.getSourceType()!=null && order.getSourceType().length()>0) {
				criteria.andLike("sourceType", "%" + order.getSourceType() + "%");
			}
			//如果字段不为空
			if (order.getSellerId()!=null && order.getSellerId().length()>0) {
				criteria.andLike("sellerId", "%" + order.getSellerId() + "%");
			}
	
		}

        //查询数据
        List<TbOrder> list = orderMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbOrder> info = new PageInfo<TbOrder>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbOrder order) {
		orderMapper.insertSelective(order);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrder order){
		orderMapper.updateByPrimaryKeySelective(order);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbOrder getById(Long id){
		return orderMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        orderMapper.deleteByExample(example);
	}
	
	
}
