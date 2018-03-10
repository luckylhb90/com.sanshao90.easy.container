package com.sanshao90.easy.container;

import com.sanshao90.easy.container.enums.ServerStatus;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 容器启动接口
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public interface Server {

    /**
     * 启动容器
     */
    void start();

    /**
     * 关闭容器
     */
    void stop();

    /**
     * 获取服务状态
     * @return
     */
    ServerStatus getServerStatus();

    /**
     * 获取服务器端口
     * @return
     */
    int getPort();
}
