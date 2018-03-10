package com.sanshao90.easy.container.io;

import com.sanshao90.easy.container.LifeCycle;
import com.sanshao90.easy.container.exceptions.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 请求接收处理器
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public abstract class Connector implements LifeCycle {

    private static final Logger logger = LoggerFactory.getLogger(Connector.class);

    @Override
    public void start() throws ConnectorException {
        init();
        acceptConnect();
    }


    protected abstract void init() throws ConnectorException;

    protected abstract void acceptConnect() throws ConnectorException;
}
