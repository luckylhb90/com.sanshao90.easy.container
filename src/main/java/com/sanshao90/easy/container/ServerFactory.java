package com.sanshao90.easy.container;

import com.google.common.collect.Lists;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.config.SocketConnectorConfig;
import com.sanshao90.easy.container.impl.SimpleServer;
import com.sanshao90.easy.container.io.Connector;
import com.sanshao90.easy.container.io.ConnectorFactory;
import com.sanshao90.easy.container.io.impl.SocketConnectorFactory;

import java.util.List;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 容器服务工厂类
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class ServerFactory {

    /**
     * 返回simple 服务实例
     *
     * @return
     */
    public static Server getSimpleServer(ServerConfig serverConfig) {
        List<Connector> connectors = Lists.newArrayList();
        ConnectorFactory connectorFactory = new SocketConnectorFactory(new SocketConnectorConfig(serverConfig.getPort()));
        connectors.add(connectorFactory.getConnector());
        return new SimpleServer(serverConfig, connectors);
    }
}
