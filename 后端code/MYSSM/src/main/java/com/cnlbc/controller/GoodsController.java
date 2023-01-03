package com.cnlbc.controller;
import com.cnlbc.domain.*;
import com.cnlbc.service.GoodsService;
import com.cnlbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    @Qualifier("goodservice")
    private GoodsService goodsService;
    public void GoodsService(GoodsService gservice){
        this.goodsService = gservice;
    }

    @RequestMapping("/promptKey")
    public Result promptKeywords(@RequestParam("keyword")String keyword) throws IOException{
        List<String> goodsList = goodsService.promptKeywords(keyword);
        if(goodsList!=null){
            return Result.success("找到关键词",goodsList);
        }
        return Result.error("401","未查询到关键词");
    }

    @RequestMapping("/recommends")
    public Result recommends() throws IOException{
        List<Goods> goodsList = goodsService.hotGoodsList();
        System.out.println(goodsList);
        if(goodsList!=null){
            return Result.success("找到热门货物",goodsList);
        }
        return Result.error("401","未查询到热门货物");
    }

    @RequestMapping("detail")
    public Result details(@RequestParam("detail")int detail){

        Goodinf res = goodsService.details(detail);
        if(res!=null){
            return Result.success("找到对应货物",res);
        }
        return Result.error("401","未查询到对应货物");
    }

    @RequestMapping("/search")
    public Result search(@RequestParam("keyword")String keyword,@RequestParam("categoryId")int categoryId,@RequestParam("brandId")int brandId) throws IOException{
        System.out.println(keyword);
        System.out.println(categoryId);
        System.out.println(brandId);
        List<Goods> goodsList = goodsService.search(keyword, categoryId, brandId);
        System.out.println(goodsList);
        if(goodsList!=null){
            return Result.success("找到热门货物",goodsList);
        }
        return Result.error("401","未查询到热门货物");
    }
    @RequestMapping("/comment")
    public Result comment(@RequestParam("comment") int comment)
    {
        System.out.println(comment);
        List<GoodsComment> goodsComments = goodsService.comments(comment);
        System.out.println(goodsComments);
        if(goodsComments!=null)
        {
            return Result.success("找到对应评论",goodsComments);
        }
            return Result.error("401","未找到商品对应评论");
    }
    @RequestMapping("/getcategory")
    public Result getCategory(@RequestParam("category") int category)
    {
        System.out.println(category);
        GoodsCategory goodsCategory = goodsService.getCategory(category);
        System.out.println(goodsCategory);
        if(goodsCategory!=null)
        {
            return Result.success("找到对应评论",goodsCategory);
        }
        return Result.error("401","未找到商品对应评论");
    }
}