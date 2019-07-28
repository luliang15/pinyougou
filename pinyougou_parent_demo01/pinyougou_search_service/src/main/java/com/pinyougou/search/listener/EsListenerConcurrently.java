package com.pinyougou.search.listener;

import com.alibaba.fastjson.JSON;import com.pinyougou.entity.EsItem;
import com.pinyougou.entity.MessageInfo;
import com.pinyougou.service.ItemSearchService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.pinyougou.search.listener
 * @date 2019-7-25
 */
public class EsListenerConcurrently implements MessageListenerConcurrently {
    @Autowired
    private ItemSearchService itemSearchService;
    @Value("${pagedir}")
    private String pagedir;
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
                    List<EsItem> esItemList = JSON.parseArray(info.getContext().toString(), EsItem.class);
                    //导入索引库
                    itemSearchService.importList(esItemList);
                }else if(info.getMethod() == MessageInfo.METHOD_DELETE){
                    List<Long> idArray = JSON.parseArray(info.getContext().toString(), Long.class);
                    Long[] ids = new Long[idArray.size()];
                    idArray.toArray(ids);
                    //删除索引库
                    itemSearchService.deleteByGoodsId(ids);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //4.2.返回消息读取状态-CONSUME_SUCCESS
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
