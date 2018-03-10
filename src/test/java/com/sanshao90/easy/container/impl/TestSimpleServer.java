package com.sanshao90.easy.container.impl;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.ServerFactory;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class TestSimpleServer {


    private Server server;

    private static final int TIMEOUT = 500;// 设置超时时间为500毫秒

    @Before
    public void before() {
        ServerConfig serverConfig = new ServerConfig();
        server = ServerFactory.getSimpleServer(serverConfig);
    }

    @Test
    public void test_request() {
        // 如果server没启动，首先启动服务
        if (ServerStatus.STOPED.equals(server.getServerStatus())) {
            // 创建线程启动server
            new Thread(new Runnable() {
                @Override
                public void run() {
                    server.start();
                }
            }).run();
            // 如果server 未启动，就sleep 下
            while (ServerStatus.STOPED.equals(server.getServerStatus())) {
                System.out.println("等待 server 启动");
                try {
                    Thread.sleep(TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Socket socket = new Socket();
        SocketAddress endpoint = new InetSocketAddress("127.0.0.1", ServerConfig.DEFAULT_PORT);
        try {
            // 与服务器建立连接，超时时间为 TIMEOUT
            socket.connect(endpoint, TIMEOUT);
            Assert.assertTrue("服务器启动后，可以接受请求", socket.isConnected());
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

    @After
    public void after() {
        server.stop();
    }
}
