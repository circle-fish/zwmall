package com.cnlbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.xml.soap.Text;
import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository("goodsComment")
public class GoodsComment {
    int id;
    String orderNo;
    int goodsId;
    int userId;
    String content;
    String photoUrls;
    int likes;
    int status;
    Date createTime;
    Date updateTime;
}
