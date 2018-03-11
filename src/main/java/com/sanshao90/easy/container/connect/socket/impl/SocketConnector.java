package com.sanshao90.easy.container.connect.socket.impl;

import com.sanshao90.easy.container.enums.ServerStatus;
import com.sanshao90.easy.container.event.listener.EventListener;
import com.sanshao90.easy.container.exceptions.ConnectorException;
import com.sanshao90.easy.container.connect.AbstractConnector;
import com.sanshao90.easy.container.utils.IoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class SocketConnector extends AbstractConnector {

    private static final Logger logger = LoggerFactory.getLogger(SocketConnector.class);

    private ServerSocket serverSocket;
    protected EventListener<Socket> eventListener;

    public SocketConnector(int port, EventListener<Socket> eventListener) {
        this(port, LOCOLHOST, DEFAULT_BACKLOG, eventListener);
    }

    public SocketConnector(int port, String host, int backLog, EventListener<Socket> eventListener) {
        this.port = port;
        this.host = StringUtils.isBlank(host) ? LOCOLHOST : host;
        this.backLog = backLog;
        this.eventListener = eventListener;
    }

    @Override
    protected void init() throws ConnectorException {
        try {
            InetAddress inetAddress = InetAddress.getByName(this.host);
            this.serverSocket = new ServerSocket(this.port, backLog, inetAddress);
            this.serverStatus = ServerStatus.STARTED;
        } catch (IOException e) {
            logger.error("SocketConnector.init IOException ", e);
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
                    whenAccept(socket);
                    logger.info("新增连接 -- {} : {}", socket.getInetAddress(), socket.getPort());
                } catch (IOException e) {
                    logger.error("SocketConnector.acceptConnect IOException", e);
                }
            }
        }).start();
    }

    private void whenAccept(Socket socket) throws ConnectorException {
        eventListener.onEvent(socket);
    }

    @Override
    public void stop() {
        IoUtils.close(serverSocket);
        this.serverStatus = ServerStatus.STOPED;
        logger.info("服务停止");
    }

    /**
     * 获取服务端口
     *
     * @return
     */
    @Override
    public int getPort() {
        return this.port;
    }

    /**
     * 获取服务的ip or 主机名
     *
     * @return
     */
    @Override
    public String getHost() {
        return this.host;
    }
}
