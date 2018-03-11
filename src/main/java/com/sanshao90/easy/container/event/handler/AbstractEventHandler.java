package com.sanshao90.easy.container.event.handler;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public abstract class AbstractEventHandler<T> implements EventHandler<T> {
    @Override
    public void handler(T event) {
        beroreHandler(event);
        doHandler(event);
        afterHandler(event);
    }

    /**
     * 事件处理后置方法
     *
     * @param event
     */
    private void afterHandler(T event) {
    }

    /**
     * 具体事件处理方法
     *
     * @param event
     */
    protected abstract void doHandler(T event);

    /**
     * 事件处理前置方法
     *
     * @param event
     */
    private void beroreHandler(T event) {
    }
}
