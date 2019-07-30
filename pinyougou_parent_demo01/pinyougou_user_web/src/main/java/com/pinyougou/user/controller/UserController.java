package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import com.pinyougou.utils.PhoneFormatCheckUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:luliang
 * @Project:请求处理器
 * @CreateDate: Created in 2019-07-30 09:12
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbUser> findAll(){
        return userService.findAll();
    }


    /**
     * 分页查询数据
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int pageNo, int pageSize, @RequestBody TbUser user){
        return userService.findPage(pageNo, pageSize,user);
    }

    /**
     * 增加
     * @param user
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbUser user,String code){
        try {
            boolean checkSmsCode = userService.checkSmsCode(user.getPhone(), code);
            if (checkSmsCode == false) {
                return new Result(false, "验证码输入错误！");
            }
            userService.add(user);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbUser user){
        try {
            userService.update(user);
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
    public TbUser getById(Long id){
        return userService.getById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            userService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    //生成短信验证码
    @RequestMapping("createSmsCode")
    public Result createSmsCode(String phone){
        try {
            //验证手机号合法性
            if (!PhoneFormatCheckUtils.isPhoneLegal(phone)) {
                return new Result(false, "请输入正确的手机号！");
            }
            userService.createSmsCode(phone);
            return new Result(true, "验证码已发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "验证码已发送失败！");
    }
}
