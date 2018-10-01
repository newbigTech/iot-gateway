package com.newbig.im.model.proto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class IMMessage {
    private Integer cmd;
    private Integer proto;//json pb msgpack 还是自定义的
    private String data;

    public static IMMessage buildChatMsg(ChatMsg chatMsg){
        IMMessage imMessage = new IMMessage();
        imMessage.setCmd(2);
        imMessage.setProto(1);
        imMessage.setData(JSON.toJSONString(chatMsg));
        return imMessage;
    }
    public static String buildAuthMsg(AuthResultMsg authResultMsg){
        IMMessage imMessage = new IMMessage();
        imMessage.setCmd(Command.CommandType.AUTH_BACK.getNumber());
        imMessage.setProto(1);
        imMessage.setData(JSON.toJSONString(authResultMsg));
        return JSON.toJSONString(imMessage);
    }
    public static String buildPong(){
        IMMessage imMessage = new IMMessage();
        imMessage.setCmd(Command.CommandType.PONG.getNumber());
        imMessage.setProto(1);
        imMessage.setData("");
        return JSON.toJSONString(imMessage);
    }
}
