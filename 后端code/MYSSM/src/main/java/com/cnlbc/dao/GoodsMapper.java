package com.cnlbc.dao;

import com.cnlbc.domain.Goods;
import com.cnlbc.domain.GoodsCategory;
import com.cnlbc.domain.GoodsComment;
import com.cnlbc.domain.User;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;


public interface GoodsMapper {

    public List<Goods> hotGoodsList();//热门货物
    public List<String> promptKeywords(String keyword);//关键词检索

    public List<Goods> recommends(String keyword);//推荐商品

    public List<Goods> findbrand(@Param("brandId")int brandId);
    public Goods details(int gid);
    public boolean buysomething(Goods goods);

    public List<Goods> search(@Param("keyword")String keyword,@Param("categoryId")int categoryId,@Param("brandId")int brandId);
    public List<GoodsComment> comments(int gid);
    public GoodsCategory getCategory(int gid);

}
