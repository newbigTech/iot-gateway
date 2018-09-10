package com.newbig.im.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Table(name = "t_order")
public class Order {
    @Id
    private Long orderId;
    private Integer userId;
    private BigDecimal totalPrice;
    private BigDecimal payPrice;
    private Long amount;
    private String buyerName;
    private String buyerPhone;
    private Integer status;
}
