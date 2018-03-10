package com.sanshao90.easy.container.io.impl;

import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;
import com.sanshao90.easy.container.exceptions.ConnectorException;
import com.sanshao90.easy.container.io.Connector;
import com.sanshao90.easy.container.utils.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class SocketConnector extends Connector {

    private static final Logger logger = LoggerFactory.getLogger(SocketConnector.class);

    private int port;
    private ServerSocket serverSocket;
    private volatile ServerStatus serverStatus = ServerStatus.STOPED;

    public SocketConnector(int port) {
        this.port = port;
    }

    public SocketConnector(ServerConfig serverConfig) {
        this(serverConfig.getPort());
    }

    @Override
    protected void init() throws ConnectorException {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.serverStatus = ServerStatus.STARTED;
        } catch (IOException e) {
            throw new ConnectorException(e);
        }

    }

    @Override
    protected void acceptConnect() throws ConnectorException {
        new Thread(() -> {
            while (true && ServerStatus.isStart(this.serverStatus)) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    logger.info("新增连接 -- {} : {}", socket.getInetAddress(), socket.getPort());
                } catch (IOException e) {
                    logger.error("SocketConnector.acceptConnect IOException", e);
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        IoUtils.close(serverSocket);
        this.serverStatus = ServerStatus.STOPED;
        logger.info("服务停止");
    }
}
