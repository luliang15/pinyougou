package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.user.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @Description:luliang
 * @Project:请求处理器
 * @CreateDate: Created in 2019-08-02 10:51
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference
    private AddressService addressService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbAddress> findAll(){
        return addressService.findAll();
    }


    /**
     * 分页查询数据
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int pageNo, int pageSize, @RequestBody TbAddress address){
        return addressService.findPage(pageNo, pageSize,address);
    }

    /**
     * 增加
     * @param address
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbAddress address){
        try {
            addressService.add(address);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param address
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbAddress address){
        try {
            addressService.update(address);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public TbAddress getById(Long id){
        return addressService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            addressService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 按用户ID查找列表
     * @return
     */
    @RequestMapping("findListByUserId")
    public List<TbAddress> findListByUserId(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TbAddress> addressList = addressService.findListByUserId(userId);
        return addressList;
    }
}
