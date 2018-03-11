package com.sanshao90.easy.container.connect.socket.impl;

import com.sanshao90.easy.container.config.SocketConnectorConfig;
import com.sanshao90.easy.container.event.listener.EventListener;
import com.sanshao90.easy.container.connect.AbstractConnector;
import com.sanshao90.easy.container.connect.ConnectorFactory;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class SocketConnectorFactory implements ConnectorFactory {

    private final SocketConnectorConfig socketConnectorConfig;
    private final EventListener eventListener;

    public SocketConnectorFactory(SocketConnectorConfig socketConnectorConfig, EventListener eventListener) {
        this.socketConnectorConfig = socketConnectorConfig;
        this.eventListener = eventListener;
    }

    @Override
    public AbstractConnector getConnector() {
        return new SocketConnector(this.socketConnectorConfig.getPort(), eventListener);
    }
}
