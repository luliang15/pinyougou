package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.ProvincesService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbProvincesMapper;
import com.pinyougou.pojo.TbProvinces;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class ProvincesServiceImpl implements ProvincesService {

	@Autowired
	private TbProvincesMapper provincesMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbProvinces> findAll() {
		return provincesMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbProvinces provinces) {
		PageResult<TbProvinces> result = new PageResult<TbProvinces>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbProvinces.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(provinces!=null){			
						//如果字段不为空
			if (provinces.getProvinceid()!=null && provinces.getProvinceid().length()>0) {
				criteria.andLike("provinceid", "%" + provinces.getProvinceid() + "%");
			}
			//如果字段不为空
			if (provinces.getProvince()!=null && provinces.getProvince().length()>0) {
				criteria.andLike("province", "%" + provinces.getProvince() + "%");
			}
	
		}

        //查询数据
        List<TbProvinces> list = provincesMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbProvinces> info = new PageInfo<TbProvinces>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbProvinces provinces) {
		provincesMapper.insertSelective(provinces);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbProvinces provinces){
		provincesMapper.updateByPrimaryKeySelective(provinces);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbProvinces getById(Long id){
		return provincesMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbProvinces.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        provincesMapper.deleteByExample(example);
	}
	
	
}
