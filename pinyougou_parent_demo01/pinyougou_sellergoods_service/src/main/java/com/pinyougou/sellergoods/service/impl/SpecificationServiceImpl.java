package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.SpecificationService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbSpecification specification) {
		PageResult<TbSpecification> result = new PageResult<TbSpecification>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						//如果字段不为空
			if (specification.getSpecName()!=null && specification.getSpecName().length()>0) {
				criteria.andLike("specName", "%" + specification.getSpecName() + "%");
			}
	
		}

        //查询数据
        List<TbSpecification> list = specificationMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbSpecification> info = new PageInfo<TbSpecification>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//保存规格
		specificationMapper.insertSelective(specification.getSpecification());
		//保存规格选项
		for (TbSpecificationOption option : specification.getSpecificationOptionList()) {
			//设置规格id
			option.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insertSelective(option);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){

		//更新规格信息
		specificationMapper.updateByPrimaryKeySelective(specification.getSpecification());

		//更新规格选项信息
		//首先删除之前所有的选项
		TbSpecificationOption where = new TbSpecificationOption();
		where.setSpecId(specification.getSpecification().getId());
		specificationOptionMapper.delete(where);
		//再次添加选项
		for (TbSpecificationOption option : specification.getSpecificationOptionList()) {
			//设置规格id
			option.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insertSelective(option);
		}

	}	
	
	/**
	 * Specification:规格
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification getById(Long id){
		final Specification result = new Specification();
		//查询规格
		TbSpecification tbSpecification=specificationMapper.selectByPrimaryKey(id);
		result.setSpecification(tbSpecification);
		//查询规格选项
		final TbSpecificationOption where = new TbSpecificationOption();
		where.setSpecId(id);
		List<TbSpecificationOption> options=specificationOptionMapper.select(where);
		result.setSpecificationOptionList(options);
		return result;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        specificationMapper.deleteByExample(example);

		//删除关联数据规格选项
		for (Long id : ids) {
			TbSpecificationOption where = new TbSpecificationOption();
			where.setSpecId(id);
			specificationOptionMapper.delete(where);
		}

	}
	
	
}
