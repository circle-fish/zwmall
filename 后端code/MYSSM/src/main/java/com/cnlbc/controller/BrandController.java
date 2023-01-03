package com.cnlbc.controller;
import com.cnlbc.domain.Brand;
import com.cnlbc.domain.Brandgoods;
import com.cnlbc.domain.Cart;
import com.cnlbc.domain.Result;
import com.cnlbc.service.BrandService;
import com.cnlbc.service.CartService;
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
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    @Qualifier("brandservice")
    private BrandService brandService;

    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping("/findbrand")
    public Result findbrand(@RequestParam("id")int id) throws IOException{
        System.out.println(id);
        Brand brand = brandService.findbrand(id);
        System.out.println(brand);
        if(brand!=null){
            return Result.success("找到购物车",brand);
        }
        return Result.error("401","未查询到用户购物车");
    }

    @RequestMapping("/headerBrands")
    public Result headerBrands() throws IOException{
        List<Brandgoods> brandgoodsList = brandService.headerBrands();
        if(brandgoodsList !=null){
            System.out.println("head,ok");
            return Result.success("品牌查询成功",brandgoodsList );
        }
        System.out.println("head,false");
        return Result.error("401","品牌查询失败");
    }

    @RequestMapping("/allbrand")
    public Result allbrand() throws IOException{
        List<String> stringList = brandService.findallbrand();
        if(stringList !=null){
            System.out.println("brand,ok");
            return Result.success("品牌查询成功",stringList);
        }
        System.out.println("brand,false");
        return Result.error("401","品牌查询失败");
    }
}