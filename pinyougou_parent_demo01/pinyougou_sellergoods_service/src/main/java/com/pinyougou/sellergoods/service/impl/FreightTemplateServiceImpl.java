package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.FreightTemplateService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbFreightTemplateMapper;
import com.pinyougou.pojo.TbFreightTemplate;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class FreightTemplateServiceImpl implements FreightTemplateService {

	@Autowired
	private TbFreightTemplateMapper freightTemplateMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbFreightTemplate> findAll() {
		return freightTemplateMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbFreightTemplate freightTemplate) {
		PageResult<TbFreightTemplate> result = new PageResult<TbFreightTemplate>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbFreightTemplate.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(freightTemplate!=null){			
						//如果字段不为空
			if (freightTemplate.getSellerId()!=null && freightTemplate.getSellerId().length()>0) {
				criteria.andLike("sellerId", "%" + freightTemplate.getSellerId() + "%");
			}
			//如果字段不为空
			if (freightTemplate.getIsDefault()!=null && freightTemplate.getIsDefault().length()>0) {
				criteria.andLike("isDefault", "%" + freightTemplate.getIsDefault() + "%");
			}
			//如果字段不为空
			if (freightTemplate.getName()!=null && freightTemplate.getName().length()>0) {
				criteria.andLike("name", "%" + freightTemplate.getName() + "%");
			}
			//如果字段不为空
			if (freightTemplate.getSendTimeType()!=null && freightTemplate.getSendTimeType().length()>0) {
				criteria.andLike("sendTimeType", "%" + freightTemplate.getSendTimeType() + "%");
			}
	
		}

        //查询数据
        List<TbFreightTemplate> list = freightTemplateMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbFreightTemplate> info = new PageInfo<TbFreightTemplate>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbFreightTemplate freightTemplate) {
		freightTemplateMapper.insertSelective(freightTemplate);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbFreightTemplate freightTemplate){
		freightTemplateMapper.updateByPrimaryKeySelective(freightTemplate);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbFreightTemplate getById(Long id){
		return freightTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbFreightTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        freightTemplateMapper.deleteByExample(example);
	}
	
	
}
