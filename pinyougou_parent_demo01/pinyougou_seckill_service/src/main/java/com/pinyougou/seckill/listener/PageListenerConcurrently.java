package com.pinyougou.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.pinyougou.entity.MessageInfo;
import com.pinyougou.seckill.service.impl.CreatePageService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.search.listener
 * @date 2019-7-25
 */
public class PageListenerConcurrently implements MessageListenerConcurrently {
    @Autowired
    private CreatePageService createPageService;
    @Value("${PAGEDIR}")
    private String PAGEDIR;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            //4.1.循环读取消息-msgs.for
            for (MessageExt msg : msgs) {
                //4.1.1输出消息-主题、标签、消息key、内容(body)
                String body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                //转换为MessageInfo
                MessageInfo info = JSON.parseObject(body, MessageInfo.class);
                //新增操作
                if(info.getMethod() == MessageInfo.METHOD_ADD){
                    //转换对象
                    List<Long> idArray = JSON.parseArray(info.getContext().toString(), Long.class);
                    //生成静态页
                    for (Long id : idArray) {
                        System.out.println("生成了商品id为:" + id + "的商品详情页...");
                        createPageService.genItemHtml(id);
                    }
                }else if(info.getMethod() == MessageInfo.METHOD_DELETE){
                    List<Long> idArray = JSON.parseArray(info.getContext().toString(), Long.class);
                    //List转数组
                    Long[] ids = new Long[idArray.size()];
                    idArray.toArray(ids);
                    //删除静态详情页
                    for (Long id : ids) {
                        File beDele = new File(PAGEDIR + id + ".html");
                        boolean exists = beDele.exists();
                        if(exists){
                            boolean result = beDele.delete();
                            System.out.println("删除商品 "+ id + " 静态页：" + result);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //4.2.返回消息读取状态-CONSUME_SUCCESS
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
