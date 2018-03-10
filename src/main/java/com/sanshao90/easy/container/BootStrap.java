package com.sanshao90.easy.container;

import com.sanshao90.easy.container.config.ServerConfig;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class BootStrap {

    public static void main(String[] args) {
        ServerConfig serverConfig = new ServerConfig();
        Server server = ServerFactory.getSimpleServer(serverConfig);
        server.start();
    }
}
