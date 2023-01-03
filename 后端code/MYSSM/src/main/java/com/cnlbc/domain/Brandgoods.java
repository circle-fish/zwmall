package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository("brandgoods")
public class Brandgoods {
    String name;
    int id;
    List<Goods> goodsList;
}
