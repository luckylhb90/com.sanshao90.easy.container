package com.sanshao90.easy.container;

import com.google.common.collect.Lists;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.config.SocketConnectorConfig;
import com.sanshao90.easy.container.connect.Connector;
import com.sanshao90.easy.container.connect.ConnectorFactory;
import com.sanshao90.easy.container.connect.nio.impl.SocketChannelConnector;
import com.sanshao90.easy.container.connect.socket.impl.SocketConnectorFactory;
import com.sanshao90.easy.container.event.handler.EventHandler;
import com.sanshao90.easy.container.event.handler.impl.FlieEventHandler;
import com.sanshao90.easy.container.event.handler.impl.NIOEchoEventHandler;
import com.sanshao90.easy.container.event.listener.EventListener;
import com.sanshao90.easy.container.event.listener.impl.NIOEventListener;
import com.sanshao90.easy.container.event.listener.impl.SocketEventListener;
import com.sanshao90.easy.container.impl.SimpleServer;

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

//        EventHandler eventHandler = new EchoEventHandler();
        EventHandler eventHandler = new FlieEventHandler(System.getProperty("user.dir"));//new EchoEventHandler();
        EventListener socketEventListener = new SocketEventListener(eventHandler);
        ConnectorFactory connectorFactory = new SocketConnectorFactory(new SocketConnectorConfig(serverConfig.getPort()), socketEventListener);
        connectors.add(connectorFactory.getConnector());

        EventHandler nioEventHandler = new NIOEchoEventHandler();
        EventListener nioEventListener = new NIOEventListener(nioEventHandler);
        Connector connector = new SocketChannelConnector(serverConfig.getPort() + 1, nioEventListener);
        connectors.add(connector);


        return new SimpleServer(connectors);
    }
}
