package com.pinyougou.page.service;

/**
 * 商品详细页接口
 * @author Steven
 *
 */
public interface ItemPageService {
    /**
    * 生成商品详细页
    * @param goodsId 生成:spu-id详情页
    */
   public boolean genItemHtml(Long goodsId);
}
