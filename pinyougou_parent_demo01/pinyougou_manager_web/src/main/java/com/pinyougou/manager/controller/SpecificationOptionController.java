package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougoou.SpecificationOptionService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecificationOption;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

	@Reference
	private SpecificationOptionService specificationOptionService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecificationOption> findAll(){			
		return specificationOptionService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int pageNo, int pageSize, @RequestBody TbSpecificationOption specificationOption){
		return specificationOptionService.findPage(pageNo, pageSize,specificationOption);
	}
	
	/**
	 * 增加
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSpecificationOption specificationOption){
		try {
			specificationOptionService.add(specificationOption);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSpecificationOption specificationOption){
		try {
			specificationOptionService.update(specificationOption);
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
	public TbSpecificationOption getById(Long id){
		return specificationOptionService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			specificationOptionService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
}
