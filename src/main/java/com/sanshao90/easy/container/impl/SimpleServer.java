package com.sanshao90.easy.container.impl;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    private ServerSocket serverSocket;

    public SimpleServer() {
        this(new ServerConfig());
    }

    public SimpleServer(ServerConfig serverConfig) {
        this.port = serverConfig.getPort();
    }

    /**
     * 启动容器
     */
    @Override
    public void start() throws IOException {
        Socket socket = null;
        this.serverSocket = new ServerSocket(this.port);
        this.status = ServerStatus.STARTED;
        logger.info("服务启动。。。{}", status);
        try {
            while (true) {
                socket = serverSocket.accept();// 连接队列中取出一个连接，否则循环等待获取
                logger.info("新增连接--{}:{}", socket.getInetAddress(), socket.getPort());
            }
        } catch (IOException e) {
            logger.error("SimpleServer.start IOException", e);
        }
    }

    /**
     * 关闭容器
     */
    @Override
    public void stop() {
        try {
            if (this.serverSocket != null) {
                this.serverSocket.close();
                this.status = ServerStatus.STOPED;
                logger.info("服务停止。。。{}", status);
            }
        } catch (IOException e) {
            logger.error("SimpleServer.stop IOException", e);
        }
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
    public int getPort() {
        return port;
    }
}
