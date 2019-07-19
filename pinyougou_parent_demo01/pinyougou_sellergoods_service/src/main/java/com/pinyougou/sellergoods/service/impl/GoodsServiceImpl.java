package com.pinyougou.sellergoods.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougoou.GoodsService;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务逻辑实现
 *
 * @author Steven
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.select(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize, TbGoods goods) {
        PageResult<TbGoods> result = new PageResult<TbGoods>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();

        if (goods != null) {
            //如果字段不为空
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                //精准配备
                criteria.andEqualTo("sellerId", goods.getSellerId());
            }
            //如果字段不为空
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
            }
            //如果字段不为空
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andLike("auditStatus", "%" + goods.getAuditStatus() + "%");
            }
            //如果字段不为空
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andLike("isMarketable", "%" + goods.getIsMarketable() + "%");
            }
            //如果字段不为空
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andLike("caption", "%" + goods.getCaption() + "%");
            }
            //如果字段不为空
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andLike("smallPic", "%" + goods.getSmallPic() + "%");
            }
            //如果字段不为空
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andLike("isEnableSpec", "%" + goods.getIsEnableSpec() + "%");
            }
            /*//如果字段不为空
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andLike("isDelete", "%" + goods.getIsDelete() + "%");
            }*/
            //查询没删除的数据
            criteria.andIsNull("isDelete");

        }

        //查询数据
        List<TbGoods> list = goodsMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbGoods> info = new PageInfo<TbGoods>(list);
        result.setPages(info.getPages());

        return result;
    }

    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbSellerMapper sellerMapper;
    @Autowired
    private TbBrandMapper brandMapper;
    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 增加
     */
    @Override
    public void add(Goods goods) {
        //保存商品基本信息
        goods.getGoods().setAuditStatus("0");  //新增商品为未审核状态
        goodsMapper.insertSelective(goods.getGoods());

        //保存商品扩展信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insertSelective(goods.getGoodsDesc());

        //保存商品SKU列表.......
        saveItems(goods);
    }

    /**
     * 构建sku基本信息
     *
     * @param goods
     * @param item
     */
    private void setItemValus(Goods goods, TbItem item) {
        item.setSellPoint(goods.getGoods().getCaption());  //卖点
        //把图片Json串转成List<map>
        List<Map> imgList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imgList != null && imgList.size() > 0) {
            item.setImage(imgList.get(0).get("url").toString());
        }
        item.setCategoryid(goods.getGoods().getCategory3Id());
        //商品分类名称
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(item.getCategoryid());
        item.setCategory(itemCat.getName());

        //创建日期
        item.setCreateTime(new Date());
        //更新日期
        item.setUpdateTime(item.getCreateTime());

        //所属SPU-id
        item.setGoodsId(goods.getGoods().getId());
        //所属商家
        item.setSellerId(goods.getGoods().getSellerId());
        TbSeller seller = sellerMapper.selectByPrimaryKey(item.getSellerId());
        item.setSeller(seller.getNickName());

        //品牌信息
        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        item.setBrand(brand.getName());
    }



    /**
     * 修改
     */
    @Override
    public void update(Goods goods){
        //更新商品基本信息
        goods.getGoods().setAuditStatus("0");  //修改的商品要重新审核
        goodsMapper.updateByPrimaryKeySelective(goods.getGoods());
        //更新扩展信息
        goodsDescMapper.updateByPrimaryKeySelective(goods.getGoodsDesc());
        //更新sku列表
        //先删除商品sku列表
        TbItem where = new TbItem();
        where.setGoodsId(goods.getGoods().getId());
        itemMapper.delete(where);
        //保存新的sku列表
        this.saveItems(goods);
    }
    /**
     * 保存商品sku列表
     * @param goods
     */
    private void saveItems(Goods goods) {
        //如果不启用规格
        if("0".equals(goods.getGoods().getIsEnableSpec())) {
            //创建默认的sku信息
            TbItem item=new TbItem();
            item.setTitle(goods.getGoods().getGoodsName());//商品KPU+规格描述串作为SKU名称
            item.setPrice( goods.getGoods().getPrice() );//价格
            item.setStatus("1");//状态
            item.setIsDefault("1");//是否默认
            item.setNum(99999);//库存数量
            item.setSpec("{}");
            setItemValus(goods,item);
            itemMapper.insertSelective(item);
        }else{
            for (TbItem item : goods.getItemList()) {
                String title = goods.getGoods().getGoodsName();
                //{"机身内存":"16G","网络":"移动4G"}
                Map<String, String> specMap = JSON.parseObject(item.getSpec(), Map.class);
                for (String key : specMap.keySet()) {
                    title += " " + specMap.get(key);
                }
                item.setTitle(title);  //标题
                setItemValus(goods, item);
                //保存SKU
                itemMapper.insertSelective(item);
            }
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods getById(Long id) {
        final Goods goods = new Goods();
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        final TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);
        //查询商品SKU信息
        TbItem where = new TbItem();
        where.setGoodsId(id);
        List<TbItem> items = itemMapper.select(where);
        goods.setItemList(items);
        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        //数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        //goodsMapper.deleteByExample(example);

        //逻辑删除
        TbGoods record = new TbGoods();
        record.setIsDelete("1");   //已删除
        goodsMapper.updateByExampleSelective(record, example);

    }


}
