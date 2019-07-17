package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougoou.TypeTemplateService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbTypeTemplate;
/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;
	@Autowired
	private TbSpecificationOptionMapper optionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbTypeTemplate typeTemplate) {
		PageResult<TbTypeTemplate> result = new PageResult<TbTypeTemplate>();
		//设置分页条件
		PageHelper.startPage(pageNum, pageSize);

		//构建查询条件
		Example example = new Example(TbTypeTemplate.class);
		Example.Criteria criteria = example.createCriteria();

		if(typeTemplate!=null){
			//如果字段不为空
			if (typeTemplate.getName()!=null && typeTemplate.getName().length()>0) {
				criteria.andLike("name", "%" + typeTemplate.getName() + "%");
			}
			//如果字段不为空
			if (typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0) {
				criteria.andLike("specIds", "%" + typeTemplate.getSpecIds() + "%");
			}
			//如果字段不为空
			if (typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0) {
				criteria.andLike("brandIds", "%" + typeTemplate.getBrandIds() + "%");
			}
			//如果字段不为空
			if (typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0) {
				criteria.andLike("customAttributeItems", "%" + typeTemplate.getCustomAttributeItems() + "%");
			}

		}

		//查询数据
		List<TbTypeTemplate> list = typeTemplateMapper.selectByExample(example);
		//返回数据列表
		result.setRows(list);

		//获取总页数
		PageInfo<TbTypeTemplate> info = new PageInfo<TbTypeTemplate>(list);
		result.setPages(info.getPages());

		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insertSelective(typeTemplate);
	}


	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
	}

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate getById(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
		List longs = Arrays.asList(ids);
		//构建查询条件
		Example example = new Example(TbTypeTemplate.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andIn("id", longs);

		//跟据查询条件删除数据
		typeTemplateMapper.deleteByExample(example);
	}

	@Override
	public List<Map> findSpecList(Long id) {
		//查询模板
		TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		if(typeTemplate != null){
			//取出specIds:[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
			List<Map> maps = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);

			for (Map map : maps) {
				//根据规格名称id，查询选项列表
				TbSpecificationOption where = new TbSpecificationOption();
				where.setSpecId(new Long(map.get("id").toString()));
				List<TbSpecificationOption> options = optionMapper.select(where);

				//结果添加选项列表
				map.put("options", options);
			}
			return maps;
		}
		return null;
	}


}
