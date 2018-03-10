package com.sanshao90;

import com.sanshao90.easy.container.Server;
import com.sanshao90.easy.container.enums.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    /**
     * 单独线程中启动server start
     *
     * @param server
     */
    public void startServer(Server server) {
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    public void waitServerRun(Server server) {
        while (ServerStatus.STOPED.equals(server.getServerStatus())) {
            logger.info("等待服务启动");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("TestBase.waitServerRun InterruptedException", e);
            }
        }
    }
}
