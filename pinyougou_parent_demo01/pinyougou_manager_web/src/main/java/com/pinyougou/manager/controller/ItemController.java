package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougoou.ItemService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItem;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/item")
public class ItemController {

	@Reference
	private ItemService itemService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItem> findAll(){			
		return itemService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int pageNo, int pageSize, @RequestBody TbItem item){
		return itemService.findPage(pageNo, pageSize,item);
	}
	
	/**
	 * 增加
	 * @param item
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbItem item){
		try {
			itemService.add(item);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param item
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbItem item){
		try {
			itemService.update(item);
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
	public TbItem getById(Long id){
		return itemService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			itemService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
}
