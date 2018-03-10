package com.sanshao90.easy.container.impl;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;

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

    private volatile ServerStatus status = ServerStatus.STOPED;

    private int port;

    private ServerSocket serverSocket;
    private Socket socket;

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
    public void start() {
        Socket socket = null;
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.status = ServerStatus.STARTED;
            System.out.println("服务启动。。。" + status);
            while (true) {
                this.socket = serverSocket.accept();// 连接队列中取出一个连接，否则循环等待获取
                System.out.println("新增连接：" + socket.getInetAddress() + ":" + socket.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                System.out.println("服务停止。。。" + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public int getPort() {
        return port;
    }
}
