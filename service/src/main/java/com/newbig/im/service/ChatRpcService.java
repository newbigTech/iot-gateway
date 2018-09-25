package com.newbig.im.service;

import com.newbig.im.model.proto.ChatMsg;

public interface ChatRpcService {
    Boolean sendChatMsg(ChatMsg chatMsg);
}
