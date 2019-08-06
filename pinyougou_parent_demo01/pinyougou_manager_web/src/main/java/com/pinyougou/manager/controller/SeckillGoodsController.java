package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.MessageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.manager.utils.MessageSender;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

	@Reference
	private SeckillGoodsService seckillGoodsService;

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeckillGoods> findAll(){
		return seckillGoodsService.findAll();
	}


	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int pageNo, int pageSize, @RequestBody TbSeckillGoods seckillGoods){
		return seckillGoodsService.findPage(pageNo, pageSize,seckillGoods);
	}

	/**
	 * 增加
	 * @param seckillGoods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeckillGoods seckillGoods){
		try {
			seckillGoodsService.add(seckillGoods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	/**
	 * 修改
	 * @param seckillGoods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeckillGoods seckillGoods){
		try {
			seckillGoodsService.update(seckillGoods);
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
	public TbSeckillGoods getById(Long id){
		return seckillGoodsService.getById(id);
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			seckillGoodsService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}

	@Autowired
	private MessageSender messageSender;

	/**
	 * 审核
	 * @param ids 商品列表
	 * @param status 审核状态
	 * @return
	 */
	@RequestMapping("updateStatus")
	public Result updateStatus(Long[] ids, String status){
		try {
			seckillGoodsService.updateStatus(ids, status);
			//审核通过
			if ("1".equals(status)) {
				//发送MQ消息到RocketMQ中-新增操作
				MessageInfo info = new MessageInfo(
						MessageInfo.METHOD_ADD,  //操作业务方式-新增
						ids, //发送内容
						"topic-seckill-goods",  //主题
						"tag-seckill-goods-add", //标签
						"key-seckill-goods-add"  //keys
				);
				messageSender.sendMessage(info);
			}
			return new Result(true, "操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(false, "操作失败！");
	}

}
