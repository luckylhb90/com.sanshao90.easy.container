package com.sanshao90.easy.container.connect.nio.impl;

import com.sanshao90.easy.container.connect.AbstractChannelConnector;
import com.sanshao90.easy.container.enums.ServerStatus;
import com.sanshao90.easy.container.event.listener.EventListener;
import com.sanshao90.easy.container.exceptions.ConnectorException;
import com.sanshao90.easy.container.utils.IoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class SocketChannelConnector extends AbstractChannelConnector {
    private static final Logger logger = LoggerFactory.getLogger(SocketChannelConnector.class);

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    protected EventListener<SelectionKey> eventListener;

    public SocketChannelConnector(int port, EventListener<SelectionKey> eventListener) {
        this(port, LOCOLHOST, DEFAULT_BACKLOG, eventListener);
    }

    public SocketChannelConnector(int port, String host, int backLog, EventListener<SelectionKey> eventListener) {
        this.port = port;
        this.host = StringUtils.isBlank(host) ? LOCOLHOST : host;
        this.backLog = backLog;
        this.eventListener = eventListener;
    }


    @Override
    protected void communicate(SelectionKey selectionKey) throws ConnectorException {
        this.eventListener.onEvent(selectionKey);
    }

    @Override
    protected void init() throws ConnectorException {
        try {
            InetAddress inetAddress = InetAddress.getByName(this.host);
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, this.port);
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.bind(socketAddress, backLog);
            this.serverStatus = ServerStatus.STARTED;
            this.selector = Selector.open();
            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("Nio Server started.");
        } catch (IOException e) {
            logger.error("SocketChannelConnector.init IOException", e);
            throw new ConnectorException(e);
        }
    }

    @Override
    protected void acceptConnect() throws ConnectorException {
        new Thread(() -> {
            while (ServerStatus.isStart(serverStatus)) {
                Set<SelectionKey> selectionKeys = null;
                try {
                    //无论是否有读写事件发生，selector每隔1s被唤醒一次
                    // selector.select(1000);
                    // 阻塞,只有当至少一个注册的事件发生的时候才会继续.
                    selector.select();
                    selectionKeys = selector.selectedKeys();
                } catch (IOException e) {
                    logger.error("SocketChannelConnector.acceptConnect selector.selectedKeys() IOException", e);
                }

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // 必须removed 否则会继续存在，下一次循环还会进来,
                    // 注意removed 的位置，针对一个.next() remove一次
                    iterator.remove();
                    try {
                        handlerKey(selectionKey);
                    } catch (IOException e) {
                        logger.error("SocketChannelConnector.acceptConnect IOException ", e);
                        if (selectionKey != null) {
                            selectionKey.cancel();
                            // if (selectionKey.channel() != null) {
                            //     selectionKey.channel().close();
                            // }
                        }
                    }
                }
            }

            //selector关闭后会自动释放里面管理的资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    logger.error("SocketChannelConnector.acceptConnect selector.close() IOException ", e);
                }
            }
        }).start();
    }

    private void handlerKey(SelectionKey selectionKey) throws IOException {
        // 判断是哪个事件
        if (!selectionKey.isValid()) {
            return;
        }
        if (selectionKey.isAcceptable()) {// 客户请求连接
            // 获取通道 接受连接,
            // 设置非阻塞模式（必须），同时需要注册 读写数据的事件，这样有消息触发时才能捕获
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //通过ServerSocketChannel的accept创建SocketChannel实例
            //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
            SocketChannel socketChannel = ssc.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, buffer);
            logger.info("NIO Connector accept Connect from {}", socketChannel.getRemoteAddress());
        } else if (selectionKey.isReadable() || selectionKey.isWritable()) {
            communicate(selectionKey);
        } else if (selectionKey.isConnectable()) {
            logger.info(selectionKey.attachment() + " - 连接事件");
        }
    }

    /**
     * 获取服务端口
     *
     * @return
     */
    @Override
    public int getPort() {
        return this.port;
    }

    /**
     * 获取服务的ip or 主机名
     *
     * @return
     */
    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void stop() {
        IoUtils.close(this.serverSocketChannel);
        this.serverStatus = ServerStatus.STOPED;
    }
}
