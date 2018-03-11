package com.sanshao90.easy.container.event.listener.impl;

import com.sanshao90.easy.container.event.handler.EventHandler;
import com.sanshao90.easy.container.event.listener.AbstractEventListener;
import com.sanshao90.easy.container.exceptions.EventException;
import com.sanshao90.easy.container.exceptions.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : socket 事件处理
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class SocketEventListener extends AbstractEventListener<Socket> {

    private static final Logger logger = LoggerFactory.getLogger(SocketEventListener.class);

    private final EventHandler<Socket> eventHandler;

    public SocketEventListener(EventHandler<Socket> eventHandler) {
        this.eventHandler = eventHandler;
    }


    /**
     * 事件发生时的回调方法
     *
     * @param socket 事件对象
     * @throws EventException 统一异常处理
     */
    @Override
    public void onEvent(Socket socket) throws EventException {
        logger.info("新增连接 -- {} : {}", socket.getInetAddress(), socket.getPort());
        try {
            eventHandler.handler(socket);
        } catch (HandlerException e) {
            throw new EventException(e);
        }
    }

    /**
     * 返回具体的事件处理器
     *
     * @param event
     * @return
     */
    @Override
    protected EventHandler<Socket> getEventHandler(Socket event) {
        return eventHandler;
    }
}
