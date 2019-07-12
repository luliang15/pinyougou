package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougoou.TypeTemplateService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll(){			
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int pageNo, int pageSize, @RequestBody TbTypeTemplate typeTemplate){
		return typeTemplateService.findPage(pageNo, pageSize,typeTemplate);
	}
	
	/**
	 * 增加
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.add(typeTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
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
	public TbTypeTemplate getById(Long id){
		return typeTemplateService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			typeTemplateService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
}
