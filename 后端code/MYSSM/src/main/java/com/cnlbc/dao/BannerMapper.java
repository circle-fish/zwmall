package com.cnlbc.dao;

import com.cnlbc.domain.Banner;
import com.cnlbc.domain.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    public List<Brand> findspecial(Brand brand);
}
