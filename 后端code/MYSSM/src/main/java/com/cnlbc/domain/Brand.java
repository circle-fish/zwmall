package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;

@Data
@Repository("brand")
public class Brand {
    int id;//品牌编号
    String name;//品牌名
    String introduction;//品牌介绍
    String logoUrl;//商品品牌logo
    int sortNo;//排序号
    Date createTime;//创建时间
    Date updateTime;//更新时间
}
