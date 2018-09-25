package com.newbig.im.model.proto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class IMMessage {
    private Integer type;
    private Integer proto;//json pb msgpack 还是自定义的
    private String body;

    public static IMMessage buildChatMsg(ChatMsg chatMsg){
        IMMessage imMessage = new IMMessage();
        imMessage.setType(1);
        imMessage.setProto(1);
        imMessage.setBody(JSON.toJSONString(chatMsg));
        return imMessage;
    }
}
