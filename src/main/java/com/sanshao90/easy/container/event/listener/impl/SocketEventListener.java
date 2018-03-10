package com.sanshao90.easy.container.event.listener.impl;

import com.sanshao90.easy.container.event.listener.EventListener;
import com.sanshao90.easy.container.exceptions.EventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : socket 事件处理
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class SocketEventListener implements EventListener<Socket> {

    private static final Logger logger = LoggerFactory.getLogger(SocketEventListener.class);

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
            echo(socket);
        } catch (IOException e) {
            throw new EventException(e);
        }
    }

    private void echo(Socket socket) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(inputStream);
        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.append("Server connected.welcome to echo. \n");
        printWriter.flush();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("stop".equals(line)) {
                printWriter.append("bye bye. \n");
                printWriter.flush();
                break;
            } else {
                printWriter.append(line);
                printWriter.append("\n");
                printWriter.flush();
            }
        }
    }
}
