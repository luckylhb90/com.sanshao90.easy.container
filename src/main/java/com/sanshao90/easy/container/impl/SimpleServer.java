package com.sanshao90.easy.container.impl;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;
import com.sanshao90.easy.container.exceptions.ConnectorException;
import com.sanshao90.easy.container.io.Connector;
import com.sanshao90.easy.container.io.impl.SocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 容器启动接口实现类
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class SimpleServer implements Server {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    private volatile ServerStatus status = ServerStatus.STOPED;

    private int port;

    private Connector socketConnector;

    public SimpleServer() {
        this(new ServerConfig());
    }

    public SimpleServer(ServerConfig serverConfig) {
        this.port = serverConfig.getPort();
        socketConnector = new SocketConnector(serverConfig);
    }

    /**
     * 启动容器
     */
    @Override
    public void start() throws ConnectorException {
        socketConnector.start();
        this.status = ServerStatus.STARTED;
        logger.info("服务启动。。。{}", status);
    }

    /**
     * 关闭容器
     */
    @Override
    public void stop() {
        socketConnector.stop();
        this.status = ServerStatus.STOPED;
        logger.info("服务停止。。。{}", status);
    }

    /**
     * 获取服务状态
     *
     * @return
     */
    @Override
    public ServerStatus getServerStatus() {
        return this.status;
    }

    /**
     * 获取端口号
     *
     * @return
     */
    @Override
    public int getPort() {
        return port;
    }

}
