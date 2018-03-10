package com.sanshao90.easy.container;

import com.sanshao90.easy.container.exceptions.ConnectorException;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 接收请求的生命周期 接口
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public interface LifeCycle {

    void start() throws ConnectorException;

    void stop();
}
