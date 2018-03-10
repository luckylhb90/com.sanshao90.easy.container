package com.sanshao90.easy.container.config;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class SocketConnectorConfig {

    private final int port;

    public SocketConnectorConfig(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
