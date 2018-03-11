package com.sanshao90.easy.container.connect;

import com.sanshao90.easy.container.exceptions.ConnectorException;

import java.nio.channels.SelectionKey;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public abstract class AbstractChannelConnector extends AbstractConnector {



    protected abstract void communicate(SelectionKey selectionKey) throws ConnectorException;

}
