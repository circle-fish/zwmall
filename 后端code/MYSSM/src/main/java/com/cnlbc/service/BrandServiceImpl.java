package com.cnlbc.service;

import com.cnlbc.dao.BrandMapper;
import com.cnlbc.dao.GoodsMapper;
import com.cnlbc.domain.Brand;
import com.cnlbc.domain.Brandgoods;
import com.cnlbc.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("brandservice")
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    public void setBrandMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Autowired
    private GoodsMapper goodsMapper;
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }
    @Override
    public Brand findbrand(int id) throws IOException {
        try{
            Brand brand = new Brand();
            brand.setId(id);
            return brandMapper.findspecial(brand);
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public List<String> findallbrand() throws IOException {
        try{
            List<Brand> brandList = brandMapper.findall();
            if(brandList == null)return null;
            List<String> stringList = new ArrayList<>();
            for(Brand brand: brandList){
                stringList.add(brand.getName());
            }
            return stringList;
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public List<Brandgoods> headerBrands() throws IOException {
        try{
            List<Brand> brandList =  brandMapper.findall();
            if(brandList  == null) return null;
            List<Brandgoods> brandgoodsList = new ArrayList<>();
//            System.out.println(brandList);
            for(Brand brand: brandList){
                List<Goods> goodsList = goodsMapper.findbrand(brand.getId());
                if(goodsList == null)return null;
                Brandgoods brandgoods = new Brandgoods();
                brandgoods.setId(brand.getId());
//                System.out.println(brand.getName());
                brandgoods.setName(brand.getName());
//                System.out.println(goodsList);
                brandgoods.setGoodsList(goodsList);
                brandgoodsList.add(brandgoods);
            }
            return brandgoodsList;
        }
        catch (Exception e){
            return null;
        }
    }
}