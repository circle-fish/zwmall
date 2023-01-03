package com.cnlbc.service;

import com.cnlbc.domain.Brand;
import com.cnlbc.domain.Brandgoods;
import com.cnlbc.domain.User;

import java.io.IOException;
import java.util.List;

public interface BrandService {
    public Brand findbrand(int id) throws IOException;

    List<String> findallbrand() throws IOException;

    List<Brandgoods> headerBrands() throws IOException;
}
