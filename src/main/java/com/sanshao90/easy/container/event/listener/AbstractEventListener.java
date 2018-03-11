package com.sanshao90.easy.container.event.listener;


import com.sanshao90.easy.container.event.handler.EventHandler;
import com.sanshao90.easy.container.exceptions.EventException;


/**
 * @Project : com.sanshao90.easy.container
 * @Description : 事件监听接口
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public abstract class AbstractEventListener<T> implements EventListener<T> {
    /**
     * 事件处理流程模板方法
     *
     * @param event 事件对象
     * @throws EventException 统一异常处理
     */
    @Override
    public void onEvent(T event) throws EventException {
        EventHandler<T> eventHandler = getEventHandler(event);
        eventHandler.handler(event);
    }

    /**
     * 返回具体的事件处理器
     * @param event
     * @return
     */
    protected abstract EventHandler<T> getEventHandler(T event);
}
