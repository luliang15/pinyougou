package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.OrderItemService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.pojo.TbOrderItem;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrderItem> findAll() {
		return orderItemMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbOrderItem orderItem) {
		PageResult<TbOrderItem> result = new PageResult<TbOrderItem>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbOrderItem.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(orderItem!=null){			
						//如果字段不为空
			if (orderItem.getTitle()!=null && orderItem.getTitle().length()>0) {
				criteria.andLike("title", "%" + orderItem.getTitle() + "%");
			}
			//如果字段不为空
			if (orderItem.getPicPath()!=null && orderItem.getPicPath().length()>0) {
				criteria.andLike("picPath", "%" + orderItem.getPicPath() + "%");
			}
			//如果字段不为空
			if (orderItem.getSellerId()!=null && orderItem.getSellerId().length()>0) {
				criteria.andLike("sellerId", "%" + orderItem.getSellerId() + "%");
			}
	
		}

        //查询数据
        List<TbOrderItem> list = orderItemMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbOrderItem> info = new PageInfo<TbOrderItem>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbOrderItem orderItem) {
		orderItemMapper.insertSelective(orderItem);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrderItem orderItem){
		orderItemMapper.updateByPrimaryKeySelective(orderItem);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbOrderItem getById(Long id){
		return orderItemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbOrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        orderItemMapper.deleteByExample(example);
	}
	
	
}
