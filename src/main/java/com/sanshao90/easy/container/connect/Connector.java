package com.sanshao90.easy.container.connect;

import com.sanshao90.easy.container.LifeCycle;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public interface Connector extends LifeCycle {


    /**
     * 获取服务端口
     *
     * @return
     */
    int getPort();

    /**
     * 获取服务的ip or 主机名
     *
     * @return
     */
    String getHost();
}
