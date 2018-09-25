package com.newbig.im.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.newbig.im.service.RedisService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NettyServerContext {
    /**
     * 用户会话组
     */
    final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("sessionGroups", GlobalEventExecutor.INSTANCE);
    private final Lock removeLock = new ReentrantLock();

    /**
     * 用户会话管理
     */
    final BiMap<ChannelId, Long> LOGIN_USERS = HashBiMap.create(128);

    public static final NettyServerContext INSTANCE = new NettyServerContext();

    private NettyServerContext() {

    }

    /**
     * 用户下线
     *
     * @param channel
     * @return
     */
    public Long channelInactive(Channel channel) {
        // 先把这个 channel 的用户下线
        Long userId = LOGIN_USERS.remove(channel.id());
        CHANNEL_GROUP.remove(channel);
        return userId;
    }

    /**
     * 把用户加入到登录会话中去
     */
    public void userJoin(Channel channel, long userId) {
        CHANNEL_GROUP.add(channel);
        LOGIN_USERS.forcePut(channel.id(), userId);
    }

    /**
     * 根据channel id 获取用户登录的 user id
     *
     * @param channelId
     * @return
     */
    public Long getLoginUserId(ChannelId channelId) {
        if (CHANNEL_GROUP.find(channelId) == null) {
            return null;
        }
        return LOGIN_USERS.get(channelId);
    }

    /**
     * 用户是否在线
     *
     * @param userId
     * @return
     */
    public boolean isOnline(Long userId) {
        ChannelId channelId = LOGIN_USERS.inverse().get(userId);
        if (channelId == null) {
            return false;
        }
        Channel channel = CHANNEL_GROUP.find(channelId);
        if (channel == null || !channel.isActive()) {
            if (removeLock.tryLock()) {
                try {
                    LOGIN_USERS.inverse().remove(userId);
                } finally {
                    removeLock.unlock();
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 获取用户的 channel
     *
     * @param userId
     * @return
     */
    public Channel getUserChannel(Long userId) {
        ChannelId channelId = LOGIN_USERS.inverse().get(userId);
        if (channelId == null) {
            // 用户未登录
            return null;
        }
        Channel channel = CHANNEL_GROUP.find(channelId);
        if (channel == null) {
            // 用户下线了
//            throw new UserChannelNotFoundException();
            return null;
        }
        if (!channel.isActive()) {
            this.channelInactive(channel);
//            throw new UserChannelUnActiveException();
            return null;
        }
        return channel;
    }

}
