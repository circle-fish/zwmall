package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository("user_address")
public class User_address {
    int id;//收货地址id
    int userId;//用户id
    String name;//收货人名称
    String phone;//收货人手机号
    String province;//'省份/自治区/直辖市',
    String city;//城市
    String district;//区/县
    String address;//详细地址
    String label;//地址标签
    Date createTime;//创建时间
    Date updateTime;//更新时间
}
