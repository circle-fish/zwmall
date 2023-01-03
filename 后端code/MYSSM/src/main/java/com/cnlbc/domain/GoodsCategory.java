package com.cnlbc.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository("goodsCategory")
public class GoodsCategory {
    int id ;
    String name;
    //类别名称
    int parentId;
    //父类别id
    int sortNo;
    //排序号
    Date createTime;
    Date updateTime;
}
