package com.pinyougou.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 商品信息es业务实体
 */
@Document(indexName = "pinyougou", type = "item")
public class EsItem implements Serializable {
    @Id
    @Field(index = true, store = false, type = FieldType.Long)
    private Long id;

    @Field(index = true, analyzer = "ik_smart", store = false, searchAnalyzer = "ik_smart",
            type = FieldType.Text,copyTo = "keyword")
    private String title;

    //注意这里的类型为Double
    @Field(type = FieldType.Double)
    private Double price;

    @Field(index = false, type = FieldType.Keyword)
    private String image;

    @Field(store = false, type = FieldType.Long)
    private Long goodsId;

    @Field(store = false, type = FieldType.Keyword,copyTo = "keyword")
    private String category;

    @Field(store = false, type = FieldType.Keyword,copyTo = "keyword")
    private String brand;

    @Field(store = false, type = FieldType.Keyword,copyTo = "keyword")
    private String seller;

    //嵌套域-用于存储规格
    @Field(index = true,type = FieldType.Nested)
    private Map<String,String> spec;

    @Field(store = true, type = FieldType.Date)
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    //...省略gett与sett..


    public Map<String, String> getSpec() {
        return spec;
    }

    public void setSpec(Map<String, String> spec) {
        this.spec = spec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", goodsId=" + goodsId +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", seller='" + seller + '\'' +
                '}';
    }
}
