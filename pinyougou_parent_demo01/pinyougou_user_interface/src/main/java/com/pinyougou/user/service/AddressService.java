package com.pinyougou.user.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbAddress;

import java.util.List;

/**
 * @Description:luliang
 * @Project:业务逻辑接口
 * @CreateDate: Created in 2019-08-02 10:29
 */
public interface AddressService {
    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbAddress> findAll();


    /**
     * 分页查询列表
     *
     * @return
     */
    public PageResult<TbAddress> findPage(int pageNum, int pageSize, TbAddress address);


    /**
     * 增加
     */
    public void add(TbAddress address);


    /**
     * 修改
     */
    public void update(TbAddress address);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbAddress getById(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 根据用户查询地址
     *
     * @param userId
     * @return
     */
    public List<TbAddress> findListByUserId(String userId);
}
