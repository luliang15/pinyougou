package com.pinyougou.user.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbUser;

import java.util.List;

/**
 * @Description:luliang
 * @Project:用户业务逻辑接口
 * @CreateDate: Created in 2019-07-30 09:08
 */
public interface UserService {
    /**
     * 返回全部列表
     * @return
     */
    public List<TbUser> findAll();


    /**
     * 分页查询列表
     * @return
     */
    public PageResult<TbUser> findPage(int pageNum, int pageSize, TbUser user);


    /**
     * 增加
     */
    public void add(TbUser user);


    /**
     * 修改
     */
    public void update(TbUser user);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public TbUser getById(Long id);


    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long [] ids);

    /**
     * 生成短信验证码
     * @return
     */
    public void createSmsCode(String phone);


    /**
     * 判断短信验证码是否存在
     * @param phone 手机
     * @param code 验证码
     * @return 结果true|false
     */
    public boolean  checkSmsCode(String phone,String code);

}
