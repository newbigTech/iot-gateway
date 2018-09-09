package com.newbig.im.service;

import com.newbig.im.model.dto.RPCModel;

public interface HelloService {
    String sayHello(String string);
    RPCModel sayModel(RPCModel model);
}
