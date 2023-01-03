package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Data
@Repository("goods_banner")
public class Goods_banner {
    int id;//商品轮播图集合id
    int goodsId;//关联的商品id
    String name;//轮播图标识
    String urls;//轮播图地址集合
    Date createTime;//创建时间
    Date updateTime;//更新时间
}
