package com.sanshao90.easy.container;

import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.impl.SimpleServer;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 容器服务工厂类
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class ServerFactory {

    /**
     * 返回simple 服务实例
     * @return
     */
    public static Server getSimpleServer() {
        return new SimpleServer();
    }

    public static Server getSimpleServer(ServerConfig serverConfig){
        return new SimpleServer(serverConfig);
    }
}
