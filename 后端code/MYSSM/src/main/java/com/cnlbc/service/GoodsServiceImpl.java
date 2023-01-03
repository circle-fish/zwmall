package com.cnlbc.service;

import com.cnlbc.dao.GoodsMapper;
import com.cnlbc.dao.Goods_bannerMapper;
import com.cnlbc.dao.Goods_skuMapper;
import com.cnlbc.dao.UserMapper;
import com.cnlbc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("goodservice")
public class GoodsServiceImpl implements GoodsService
{
    @Autowired
    private GoodsMapper goodsMapper;

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }


    @Autowired
    private Goods_skuMapper goods_skuMapper;

    public void setGoods_skuMapper(Goods_skuMapper goods_skuMapper) {
        this.goods_skuMapper = goods_skuMapper;
    }
    @Autowired
    private Goods_bannerMapper goods_bannerMapper;
    public void setGoods_bannerMapper(Goods_bannerMapper goods_bannerMapper) {
        this.goods_bannerMapper = goods_bannerMapper;
    }


    public List<Goods> hotGoodsList() throws IOException
    {
        try{
            List<Goods> hotList = goodsMapper.hotGoodsList();
            System.out.println(hotList);
            return hotList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<String> promptKeywords(String keyword) throws IOException
    {
        try{
            List<String> str  =  goodsMapper.promptKeywords(keyword);
            System.out.println(str);
            return str;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Goods> recommends(String keyword) throws IOException
    {
        try {
            List<Goods> recommendList = goodsMapper.hotGoodsList();
            System.out.println(recommendList);
            return recommendList;
        }
        catch (Exception e){
            return null;
        }
    }


    public Goodinf details(int gid )
    {
       try{
           Goods goods = goodsMapper.details(gid);
           if(goods == null)return null;
           List<Goods_sku> goods_skuList = goods_skuMapper.findspecial(gid);
           if(goods_skuList==null)return null;
           for (Goods_sku goods_sku:goods_skuList){
               Goods_banner goods_banner =  goods_bannerMapper.findspecial(goods_sku.getBannersId());
               if(goods_banner == null)return  null;
               goods_sku.setBannerUrls(goods_banner.getUrls());
           }
           Goodinf goodinf = new Goodinf();
           goodinf.setGoods(goods);
           goodinf.setSkus(goods_skuList);
           return  goodinf;
       }
       catch (Exception e){
           return null;
       }
    }

    public boolean buysomething(int gid,int num){
//        Goods goods = details(gid);
//        if(goods==null)return false;
//        if(goods.getStock()<num)return false;
//        goods.setStock(goods.getStock()-num);
//        goods.setBuynum(goods.getBuynum()+num);
//        goods.setCreateTime(new Date());
//        goodsMapper.buysomething(goods);
        return true;
    }
    public List<Goods> search(String keyword,int categoryId,int brandId)throws IOException{
        try{
            List<Goods> goodsList = goodsMapper.search(keyword, categoryId, brandId);
            System.out.println(goodsList);
            System.out.println("ok");
            return goodsList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<GoodsComment> comments(int gid)
    {
        try {
            List<GoodsComment> comments=  goodsMapper.comments(gid);
            System.out.println(comments);
            return comments;
        }catch (Exception e)
        {
            return null;
        }
    }

    public GoodsCategory getCategory(int gid){
        try {

            System.out.println(goodsMapper.getCategory(gid));
            return goodsMapper.getCategory(gid);

        }catch (Exception e)
        {
            return null;
        }
    }
}
