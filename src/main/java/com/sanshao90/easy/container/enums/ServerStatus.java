package com.sanshao90.easy.container.enums;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 服务启动状态
 * @Author : sanshao90
 * @Date : 2018/3/10
 */
public enum ServerStatus {

    /**
     * 服务启动
     */
    STARTED,
    /**
     * 服务关闭
     */
    STOPED;

    public static boolean isStart(ServerStatus serverStatus) {
        return STARTED.equals(serverStatus);
    }

    public static boolean isStop(ServerStatus serverStatus) {
        return STOPED.equals(serverStatus);
    }

}
