package com.sanshao90.easy.container.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class ChannelUtils {

    public static void doWrite(final ByteBuffer buffer, final SocketChannel channel) throws IOException {
        if(Objects.isNull(buffer) || Objects.isNull(channel)){
            throw new IllegalArgumentException("Required buffer and channel.");
        }
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    public void doRead(final StringBuilder data, final ByteBuffer buffer, final SocketChannel channel) throws IOException {
        while (channel.read(buffer) != -1) {
            data.append(new String(buffer.array()).trim());
            buffer.clear();
        }
    }
}
