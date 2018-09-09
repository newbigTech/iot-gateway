package com.newbig.im.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class RPCModel {
    private String one;
    private Integer two;
    private Long three;
    private BigDecimal four;
    private List<String> five;
    private Map<String,Integer> six;
    private LoginDto seven;
}
