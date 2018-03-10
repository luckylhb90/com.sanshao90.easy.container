package com.sanshao90.easy.container.config;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 服务容器配置类
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class ServerConfig {

    public static final int DEFAULT_PORT = 9101;

    private int port;

    public ServerConfig() {
        this(DEFAULT_PORT);
    }

    public ServerConfig(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
