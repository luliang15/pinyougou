package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.SeckillGoodsService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;


/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillGoods> findAll() {
		return seckillGoodsMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbSeckillGoods seckillGoods) {
		PageResult<TbSeckillGoods> result = new PageResult<TbSeckillGoods>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(seckillGoods!=null){			
						//如果字段不为空
			if (seckillGoods.getTitle()!=null && seckillGoods.getTitle().length()>0) {
				criteria.andLike("title", "%" + seckillGoods.getTitle() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getSmallPic()!=null && seckillGoods.getSmallPic().length()>0) {
				criteria.andLike("smallPic", "%" + seckillGoods.getSmallPic() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getSellerId()!=null && seckillGoods.getSellerId().length()>0) {
				criteria.andLike("sellerId", "%" + seckillGoods.getSellerId() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getStatus()!=null && seckillGoods.getStatus().length()>0) {
				criteria.andLike("status", "%" + seckillGoods.getStatus() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getIntroduction()!=null && seckillGoods.getIntroduction().length()>0) {
				criteria.andLike("introduction", "%" + seckillGoods.getIntroduction() + "%");
			}
	
		}

        //查询数据
        List<TbSeckillGoods> list = seckillGoodsMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbSeckillGoods> info = new PageInfo<TbSeckillGoods>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeckillGoods seckillGoods) {
		seckillGoodsMapper.insertSelective(seckillGoods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillGoods seckillGoods){
		seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillGoods getById(Long id){
		return seckillGoodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        seckillGoodsMapper.deleteByExample(example);
	}
	
	
}
