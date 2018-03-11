package com.sanshao90.easy.container.event.listener.impl;

import com.sanshao90.easy.container.event.handler.EventHandler;
import com.sanshao90.easy.container.event.listener.AbstractEventListener;

import java.nio.channels.SelectionKey;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class NIOEventListener extends AbstractEventListener<SelectionKey> {

    private final EventHandler<SelectionKey> eventHandler;

    public NIOEventListener(EventHandler<SelectionKey> eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * 返回具体的事件处理器
     *
     * @param event
     * @return
     */
    @Override
    protected EventHandler<SelectionKey> getEventHandler(SelectionKey event) {
        return eventHandler;
    }
}
