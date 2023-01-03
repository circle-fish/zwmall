package com.cnlbc.dao;

import com.cnlbc.domain.Brand;
import com.cnlbc.domain.User;

import java.io.IOException;
import java.util.List;

public interface BrandMapper {
    public Brand findspecial(Brand brand);
    public List<Brand> findall();
}
