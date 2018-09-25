package com.newbig.im.model.proto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatMsg {
    private Long from;
    private Long to;
    private Integer type;//消息类型 文本 图片 音频 视频 地图 红包等
    private String body;
}
