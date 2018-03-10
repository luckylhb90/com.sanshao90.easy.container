package com.sanshao90.easy.container.event.listener;

import org.w3c.dom.events.EventException;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 事件回调接口
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public interface EventListener<T> {

    /**
     * 事件发生时的回调方法
     * @param event 事件对象
     * @throws EventException 统一异常处理
     */
    void onEvent(T event) throws EventException;
}
