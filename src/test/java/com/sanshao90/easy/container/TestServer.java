package com.sanshao90.easy.container;

import com.sanshao90.TestBase;
import com.sanshao90.easy.container.config.ServerConfig;
import com.sanshao90.easy.container.enums.ServerStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class TestServer extends TestBase {

    private Server server;

    @Before
    public void before() {
        ServerConfig serverConfig = new ServerConfig();
        server = ServerFactory.getSimpleServer(serverConfig);
    }

    @Test
    public void test_start() throws IOException {
        super.startServer(server);
        super.waitServerRun(server);
        Assert.assertTrue("服务启动状态-" + ServerStatus.STARTED, ServerStatus.STARTED.equals(server.getServerStatus()));
    }

    @Test
    public void test_stop() {
        server.stop();
        Assert.assertTrue("服务启动状态-" + ServerStatus.STOPED, ServerStatus.STOPED.equals(server.getServerStatus()));
    }

    @Test
    public void test_port() {
        //int port = server.getPort();
       // Assert.assertTrue("默认端口号-" + ServerConfig.DEFAULT_PORT, ServerConfig.DEFAULT_PORT == port);
    }
}
