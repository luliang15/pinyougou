package com.pinyougou.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.mapper.TbPayLogMapper;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.TbPayLog;
import com.pinyougou.pojogroup.Cart;
import com.pinyougou.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbPayLogMapper payLogMapper;

	/**
	 * 增加--清空购物车(全买了)
	 * @param order 前端传入的订单对象，这个是不能直接保存(支付方式、收件人信息,用户名)
	 */
	@Override
	public void add(TbOrder order) {
		//1、把购物车查询出
		List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(order.getUserId());
		//2、拆单-生成新订单保存
		TbOrder beSave = null;
		List<Long> orderIdList = new ArrayList<>();  //订单号列表
		double totalMoney = 0.0;  //支付总金额
		for (Cart cart : cartList) {
			//一个商家一张订单
			beSave = new TbOrder();
			//生成订单号
			long orderId = idWorker.nextId();
			beSave.setOrderId(orderId);  //订单号
			orderIdList.add(orderId);  //记录订单列表
			double money = 0.0;  //实付金额
			beSave.setPaymentType(order.getPaymentType());  //支付方式
			beSave.setStatus("1");  //新增订单-未付款状态
			beSave.setCreateTime(new Date());  //创建时间\
			beSave.setUpdateTime(beSave.getCreateTime()); //更新时间
			beSave.setUserId(order.getUserId());  //下单用户
			beSave.setReceiverAreaName(order.getReceiverAreaName());  //收货地址
			beSave.setReceiverMobile(order.getReceiverMobile());  //收货人手机
			beSave.setReceiver(order.getReceiver());  //收件人
			beSave.setSourceType(order.getSourceType());  //来源
			beSave.setSellerId(cart.getSellerId());  //订单所属商家

			//保存订单商品列表
			for (TbOrderItem orderItem : cart.getOrderItemList()) {
				orderItem.setId(idWorker.nextId());   //订单商品id
				orderItem.setOrderId(orderId);   //关联的订单id
				//计算实付金额......
				money += orderItem.getTotalFee().doubleValue();
				//保存商品列表
				orderItemMapper.insertSelective(orderItem);
			}
			//设置实付金额
			beSave.setPayment(new BigDecimal(money));
			totalMoney += money;  //统计总金额
			orderMapper.insertSelective(beSave);
		}
		//3、把当前用户的购物车清空
		redisTemplate.boundHashOps("cartList").delete(order.getUserId());

		//保存日志,如果是在线支付
		if("1".equals(order.getPaymentType())){
			TbPayLog payLog = new TbPayLog();
			String outTradeNo = idWorker.nextId() + "";//支付订单号
			payLog.setOutTradeNo(outTradeNo);//支付订单号
			payLog.setCreateTime(new Date());//创建时间
			//订单号列表，逗号分隔
			String ids = orderIdList.toString().replace("[", "").replace("]", "").replace(" ", "");
			payLog.setOrderList(ids);//订单号列表，逗号分隔
			payLog.setPayType("1");//支付类型-在线支付
			payLog.setTotalFee((long) (totalMoney * 100));//总金额(分)
			payLog.setTradeState("0");//支付状态
			payLog.setUserId(order.getUserId());//用户ID
			payLogMapper.insertSelective(payLog);//插入到支付日志表
			//PayLog放入缓存
			redisTemplate.boundHashOps("payLogs").put(order.getUserId(), payLog);
		}
	}

	@Override
	public TbPayLog searchPayLogFromRedis(String userId) {
		return (TbPayLog) redisTemplate.boundHashOps("payLogs").get(userId);
	}

	@Override
	public void updateOrderStatus(String out_trade_no, String transaction_id) {
		//1. 修改支付日志状态
		TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);
		payLog.setTransactionId(transaction_id);  //交易流水
		payLog.setTradeState("1");  //已付款
		payLog.setPayTime(new Date());
		payLogMapper.updateByPrimaryKeySelective(payLog);
		//2. 修改关联的订单的状态
		String[] ids = payLog.getOrderList().split(",");
		for (String id : ids) {
			TbOrder beUpdate = new TbOrder();
			beUpdate.setOrderId(new Long(id));
			beUpdate.setStatus("2");  //已支付状态
			beUpdate.setPaymentTime(payLog.getPayTime());   //支付时间
			orderMapper.updateByPrimaryKeySelective(beUpdate);
		}
		//3. 清除缓存中的支付日志对象
		redisTemplate.boundHashOps("payLogs").delete(payLog.getUserId());
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
