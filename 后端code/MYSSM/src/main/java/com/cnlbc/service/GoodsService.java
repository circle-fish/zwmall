package com.cnlbc.service;

import com.cnlbc.domain.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface GoodsService
{

    public List<Goods> hotGoodsList() throws IOException;

    public List<String> promptKeywords(String keyword) throws IOException;

    public List<Goods> recommends(String keyword) throws IOException;

    public Goodinf details(int gid);
    public List<Goods> search(String keyword,int categoryId,int brandId) throws IOException;

    public List<GoodsComment> comments(int gid);

    public GoodsCategory getCategory(int gid);

}
