package com.cnlbc.dao;

import com.cnlbc.domain.Brand;
import com.cnlbc.domain.Goods_banner;
import org.apache.ibatis.annotations.Param;

public interface Goods_bannerMapper {
    public Goods_banner findspecial(@Param("id") int id);
}
