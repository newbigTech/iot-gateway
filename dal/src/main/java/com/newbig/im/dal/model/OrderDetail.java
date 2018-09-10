package com.newbig.im.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Table(name = "t_order_detail")
public class OrderDetail {
    @Id
    private Long detailId;
    private Long orderId;
    private Long goodsId;
    private BigDecimal price;
    private Long count;
    private String goodsName;
    private Integer status;
}
