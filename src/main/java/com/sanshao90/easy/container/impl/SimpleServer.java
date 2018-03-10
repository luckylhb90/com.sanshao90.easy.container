package com.sanshao90.easy.container.impl;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;
import com.sanshao90.easy.container.exceptions.ConnectorException;
import com.sanshao90.easy.container.io.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    private List<Connector> connectors;

    public SimpleServer(ServerConfig serverConfig, List<Connector> connectors) {
        this.port = serverConfig.getPort();
        this.connectors = connectors;
    }

    /**
     * 启动容器
     */
    @Override
    public void start() throws ConnectorException {
        this.connectors.stream().forEach(connector -> connector.start());
        this.status = ServerStatus.STARTED;
        logger.info("服务启动。。。{}", status);
    }

    /**
     * 关闭容器
     */
    @Override
    public void stop() {
        this.connectors.stream().forEach(connector -> connector.stop());
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
