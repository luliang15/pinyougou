package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.utils.HttpClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付实现
 * @Description:luliang
 * @Project:实现类
 * @CreateDate: Created in 2019-08-03 10:39
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {
    //公众号
    @Value("${appid}")
    private String appid;
    //商家号id
    @Value("${partner}")
    private  String partner;
    //回调地址
    @Value("${notifyurl}")
    private String notifyurl;
    //支付密钥
    @Value("${partnerkey}")
    private String partnerkey;
    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        Map map=new HashMap();
        try {
            //1.包装微信接口需要的参数
            Map param=new HashMap();
            param.put("appid",appid);//公众号ID
            param.put("mch_id", partner);  //商户号
            param.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串
            param.put("body", "品优购");  //商品描述，扫码后用户看到的商品信息
            param.put("out_trade_no", out_trade_no); //订单号
            param.put("total_fee", total_fee);  //订单总金额，单位为分
            param.put("spbill_create_ip", "127.0.0.1");  //终端IP，只要附合ip地址规范，可以随意写
            param.put("notify_url", notifyurl);  //回调地址
            param.put("trade_type", "NATIVE");  //交易类型，NATIVE 扫码支付
            //2.生成xml.通过httpClient发送请求得到数据
            //MAP转换为XML字符串(自动添加签名)
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            System.out.println("请求参数:" + xmlParam);
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3、解析结果
            String xmlResult = httpClient.getContent();
            System.out.println("微信返回结果：" + xmlResult);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
            map.put("code_url", resultMap.get("code_url"));//支付地址
            map.put("total_fee", total_fee);//总金额
            map.put("out_trade_no",out_trade_no);//订单号
            return map;
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> queryPayStatus(String out_trade_no) {
        try {
            //1、组装请求参数
            Map paramMap = new HashMap();
            paramMap.put("appid", appid);  //公众号id
            paramMap.put("mch_id", partner);  //商户号
            paramMap.put("out_trade_no", out_trade_no);  //商户订单号
            //生成随机字符串
            String nonce_str = WXPayUtil.generateNonceStr();
            paramMap.put("nonce_str", nonce_str);  //随机字符串
            //sign-签名，我们一般不会直接设置，后续有专用api生成
            String paramXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            System.out.println("正在发起查询订单接口成功，请求参数为：" + paramXml);
            //2、通过HttpClient发起微信统一下单请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);  //使用加密协议
            //设置方法入参
            httpClient.setXmlParam(paramXml);
            httpClient.post();   //发起psot请求
            String content = httpClient.getContent();//读取内容
            System.out.println("发起查询订单接口成功，响应参数为：" + content);
            //解释内容:把xml转成Map对象
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            //3、读取与接收结果,组装返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
