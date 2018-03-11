package com.sanshao90.easy.container.event.handler.impl;

import com.sanshao90.easy.container.event.handler.AbstractEventHandler;
import com.sanshao90.easy.container.exceptions.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class NIOEchoEventHandler extends AbstractEventHandler<SelectionKey> {

    private static final Logger logger = LoggerFactory.getLogger(NIOEchoEventHandler.class);
    public static final int BUFFER_SIZE = 2048;
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String LINE_SPLITTER = System.getProperty("line.separator");

    /**
     * 具体事件处理方法
     *
     * @param event
     */
    @Override
    protected void doHandler(SelectionKey event) {
        try {
            if (event.isReadable()) {// 读数据
                logger.info(event.attachment()
                        + " - 读数据事件");
                SocketChannel clntChan = (SocketChannel) event.channel();
                //获取该信道所关联的附件，这里为缓冲区
                ByteBuffer buf = (ByteBuffer) event.attachment();
                buf.clear();
                long bytesRead = clntChan.read(buf);
                //如果read（）方法返回-1，说明客户端关闭了连接，那么客户端已经接收到了与自己发送字节数相等的数据，可以安全地关闭
                if (bytesRead == -1){
                    clntChan.close();
                }else if(bytesRead > 0){
                    //如果缓冲区总读入了数据，则将该信道感兴趣的操作设置为为可读可写
                    event.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                logger.info(event.attachment()
                        + " - 读取数据：" + getString(buf));
            } else if (event.isWritable()) {// 写数据
                logger.info(event.attachment()
                        + " - 写数据事件");
                ByteBuffer buffer=(ByteBuffer) event.attachment();
                buffer.flip();
                SocketChannel clntChan = (SocketChannel) event.channel();
                //将数据写入到信道中
                clntChan.write(buffer);
                if (!buffer.hasRemaining()){
                    //如果缓冲区中的数据已经全部写入了信道，则将该信道感兴趣的操作设置为可读
                    event.interestOps(SelectionKey.OP_READ);
                }
                //为读入更多的数据腾出空间
                buffer.compact();
            }
        } catch (IOException e) {
            logger.error("NIOEchoEventHandler.doHandler IOException ", e);
            throw new HandlerException(e);
        }
    }

    /**
     * ByteBuffer 转换 String
     *
     * @param buffer
     * @return
     */
    public static String getString(ByteBuffer buffer) {
        String string = "";
        try {
            for (int i = 0; i < buffer.position(); i++) {
                string += (char) buffer.get(i);
            }
            return string;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String toString(ByteBuffer output) {
        CharBuffer charBuffer = CHARSET.decode(output);
        return charBuffer.toString();
    }

    public ByteBuffer fromString(String str) {
        return CHARSET.encode(str);
    }
}
