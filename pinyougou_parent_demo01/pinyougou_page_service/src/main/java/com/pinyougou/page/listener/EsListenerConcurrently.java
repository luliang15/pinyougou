package com.pinyougou.page.listener;

import com.alibaba.fastjson.JSON;
import com.pinyougou.entity.EsItem;
import com.pinyougou.entity.MessageInfo;
import com.pinyougou.page.service.ItemPageService;
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
 * @Description:luliang
 * @Project:静态页更新监听器
 * @CreateDate: Created in 2019-07-28 17:54
 */
public class EsListenerConcurrently implements MessageListenerConcurrently {
        @Autowired
        private ItemPageService itemPageService;
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
                        List<EsItem> esItemList = JSON.parseArray(info.getContext().toString(), EsItem.class);
                        //生成静态页
                        for (EsItem esItem : esItemList) {
                            itemPageService.genItemHtml(esItem.getGoodsId());
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
