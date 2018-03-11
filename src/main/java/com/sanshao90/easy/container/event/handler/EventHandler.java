package com.sanshao90.easy.container.event.handler;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 事件处理接口
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public interface EventHandler<T> {

    void handler(T event);
}
