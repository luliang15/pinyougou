package com.pinyougou.pay.service;

import java.util.Map;

/**
 * 微信支付接口
 *
 * @Description:luliang
 * @Project:服务层接口
 * @CreateDate: Created in 2019-08-03 10:36
 */
public interface WeixinPayService {
    /**
     * 生成微信支付二维码
     *
     * @param out_trade_no 订单号
     * @param total_fee    金额(分)
     * @return
     */
    public Map createNative(String out_trade_no, String total_fee);

    /**
     * 查看支付状态
     * @param out_trade_no 商家订单号
     * @return
     */
    public Map<String, String> queryPayStatus(String out_trade_no);
}
