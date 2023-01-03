package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository("goodinf")
public class Goodinf {
    Goods goods;
    List<Goods_sku> skus;
}
