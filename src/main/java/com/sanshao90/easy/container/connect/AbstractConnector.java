package com.sanshao90.easy.container.connect;

import com.sanshao90.easy.container.enums.ServerStatus;
import com.sanshao90.easy.container.exceptions.ConnectorException;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 请求接收处理器
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public abstract class AbstractConnector implements Connector {

    protected static final String LOCOLHOST = "localhost";
    protected static final int DEFAULT_BACKLOG = 50;
    protected volatile ServerStatus serverStatus = ServerStatus.STOPED;

    protected int port;
    protected String host;
    protected int backLog;

    @Override
    public void start() {
        init();
        acceptConnect();
    }


    protected abstract void init() throws ConnectorException;

    protected abstract void acceptConnect() throws ConnectorException;
}
