package com.pinyougou.sellergoods.service.impl;
import java.util.Arrays;
import java.util.List;

import com.pinyougoou.UserService;
import com.pinyougou.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;
/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize, TbUser user) {
		PageResult<TbUser> result = new PageResult<TbUser>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(user!=null){			
						//如果字段不为空
			if (user.getUsername()!=null && user.getUsername().length()>0) {
				criteria.andLike("username", "%" + user.getUsername() + "%");
			}
			//如果字段不为空
			if (user.getPassword()!=null && user.getPassword().length()>0) {
				criteria.andLike("password", "%" + user.getPassword() + "%");
			}
			//如果字段不为空
			if (user.getPhone()!=null && user.getPhone().length()>0) {
				criteria.andLike("phone", "%" + user.getPhone() + "%");
			}
			//如果字段不为空
			if (user.getEmail()!=null && user.getEmail().length()>0) {
				criteria.andLike("email", "%" + user.getEmail() + "%");
			}
			//如果字段不为空
			if (user.getSourceType()!=null && user.getSourceType().length()>0) {
				criteria.andLike("sourceType", "%" + user.getSourceType() + "%");
			}
			//如果字段不为空
			if (user.getNickName()!=null && user.getNickName().length()>0) {
				criteria.andLike("nickName", "%" + user.getNickName() + "%");
			}
			//如果字段不为空
			if (user.getName()!=null && user.getName().length()>0) {
				criteria.andLike("name", "%" + user.getName() + "%");
			}
			//如果字段不为空
			if (user.getStatus()!=null && user.getStatus().length()>0) {
				criteria.andLike("status", "%" + user.getStatus() + "%");
			}
			//如果字段不为空
			if (user.getHeadPic()!=null && user.getHeadPic().length()>0) {
				criteria.andLike("headPic", "%" + user.getHeadPic() + "%");
			}
			//如果字段不为空
			if (user.getQq()!=null && user.getQq().length()>0) {
				criteria.andLike("qq", "%" + user.getQq() + "%");
			}
			//如果字段不为空
			if (user.getIsMobileCheck()!=null && user.getIsMobileCheck().length()>0) {
				criteria.andLike("isMobileCheck", "%" + user.getIsMobileCheck() + "%");
			}
			//如果字段不为空
			if (user.getIsEmailCheck()!=null && user.getIsEmailCheck().length()>0) {
				criteria.andLike("isEmailCheck", "%" + user.getIsEmailCheck() + "%");
			}
			//如果字段不为空
			if (user.getSex()!=null && user.getSex().length()>0) {
				criteria.andLike("sex", "%" + user.getSex() + "%");
			}
	
		}

        //查询数据
        List<TbUser> list = userMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbUser> info = new PageInfo<TbUser>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbUser user) {
		userMapper.insertSelective(user);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbUser user){
		userMapper.updateByPrimaryKeySelective(user);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbUser getById(Long id){
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        userMapper.deleteByExample(example);
	}
	
	
}
