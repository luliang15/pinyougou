package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougoou.BrandService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbBrand;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @program:PinYouGou01
 * @description:控制层
 * @author:Mr.lu
 * @create:2019-07-09 17:21
 **/
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    /***
     * 返回全部列表
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    /***
     * 分页查询品牌
     * 控制层
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult<TbBrand> findPage(Integer pageNo,Integer pageSize){
        return brandService.findPage(pageNo,pageSize);
    }
    Logger logger = Logger.getLogger(BrandController.class);

    /***
     * 增加
     * @param brand
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            return new Result(true,"保存品牌成功");
        } catch (Exception e) {
            logger.error("保存品牌出现异常",e);
            return new Result(false,"保存品牌失败");
        }
    }

    /***
     * 根据id查找数据
     * @param id
     * @return
     */
    @RequestMapping("getById")
    public TbBrand getById(Long id){
        return  brandService.getById(id);
    }

    /***
     * 修改品牌
     * @param brand
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody TbBrand brand){
        try {
            //进行修改
            brandService.update(brand);
            return  new Result(true,"修改品牌成功");
        } catch (Exception e) {
            logger.error("修改品牌出现异常",e);
            return new Result(false,"修改品牌失败");
        }

    }
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除品牌成功");
        } catch (Exception e) {
            logger.error("删除品牌出现异常",e);
            return new Result(false,"删除品牌成功");
        }
    }

}
