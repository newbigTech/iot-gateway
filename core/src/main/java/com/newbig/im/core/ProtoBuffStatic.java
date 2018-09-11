package com.newbig.im.core;

import com.newbig.im.core.proto.*;

public class ProtoBuffStatic {
    /**
     * 服务器错误的包
     */
    static final SocketMessage SERVER_ERROR = SocketMessage.newBuilder().setCode(SERVER_CODE.SERVER_ERROR).build();
    /**
     * 需要登录才能访问
     */
    static final SocketMessage UNAUTHORIZED = SocketMessage.newBuilder().setCode(SERVER_CODE.UNAUTHORIZED).build();

    /**
     * 心跳 回复的包
     */
    static final SocketMessage HEARTBEAT = SocketMessage.newBuilder().setCode(SERVER_CODE.HEARTBEAT_CODE).build();

    /**
     * 登录失败的包
     */
    static final SocketMessage LOGIN_FAILED = SocketMessage.newBuilder()
            .setCode(SERVER_CODE.RESONSE_CODE)
            .setDomain(SYSTEM_FORWARD_CODE.SYSTEM_VALUE)
            .setOpcode(SYSTEM_OPCODE.LOGIN_CODE_VALUE)
            .setBody(MessageBody.newBuilder().setError(SYSTEM_ERROR.LOGIN_FAILED_VALUE))
            .build();
    /**
     * 登录成功的包
     */
    static final SocketMessage LOGIN_SUCCESS = SocketMessage.newBuilder()
            .setCode(SERVER_CODE.RESONSE_CODE)
            .setDomain(SYSTEM_FORWARD_CODE.SYSTEM_VALUE)
            .setOpcode(SYSTEM_OPCODE.LOGIN_CODE_VALUE)
            .setBody(MessageBody.newBuilder().setError(SYSTEM_ERROR.LOGIN_SUCCESS_VALUE))
            .build();
}
