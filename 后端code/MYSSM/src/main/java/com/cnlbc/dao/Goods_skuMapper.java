package com.cnlbc.dao;

import com.cnlbc.domain.Goods;
import com.cnlbc.domain.Goods_sku;
import com.cnlbc.domain.User;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface Goods_skuMapper {
    List<Goods_sku> findspecial(@Param("goodsId")int goodsId);
}
