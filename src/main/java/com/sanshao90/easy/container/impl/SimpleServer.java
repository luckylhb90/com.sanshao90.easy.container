package com.sanshao90.easy.container.impl;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 容器启动接口实现类
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class SimpleServer implements Server {

    private volatile ServerStatus status = ServerStatus.STOPED;

    private int port;

    public SimpleServer() {
        this(new ServerConfig());
    }

    public SimpleServer(ServerConfig serverConfig) {
        this.port = serverConfig.getPort();
    }

    /**
     * 启动容器
     */
    @Override
    public void start() {
        this.status = ServerStatus.STARTED;
        System.out.println("服务启动。。。" + status);
    }

    /**
     * 关闭容器
     */
    @Override
    public void stop() {
        this.status = ServerStatus.STOPED;
        System.out.println("服务停止。。。" + status);
    }

    /**
     * 获取服务状态
     *
     * @return
     */
    @Override
    public ServerStatus getServerStatus() {
        return this.status;
    }

    public int getPort() {
        return port;
    }
}
