package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Data
@Repository("banner")
public class Banner {
    int id;//横幅id
    String type;// 类型 slide.轮播图 advert.商品横幅 promo.商品推销 category.商品类别广告',
    String pictureUrl;//图片地址,
    int linkMode;//链接方式 0.内链 1.外链'
    String linkUrl;//外部链接地址
    int goodsId;//链接的商品id
    String goodsName;//商品名称
    int categoryId;//商品类别id,仅type等于category时需要
    String categoryName;//商品类别名称
    int status;//状态 0.正常 1.下架BrandServiceImpl
    int sortNo;//排序号
    Date createTime;//创建时间
    Date updateTime;//更新时间
}
