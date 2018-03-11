package com.sanshao90.easy.container.event.handler.impl;

import com.sanshao90.easy.container.event.handler.AbstractEventHandler;
import com.sanshao90.easy.container.exceptions.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : TODO
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class EchoEventHandler extends AbstractEventHandler<Socket> {

    private static final Logger logger = LoggerFactory.getLogger(EchoEventHandler.class);

    /**
     * 具体事件处理方法
     *
     * @param event
     */
    @Override
    protected void doHandler(Socket event) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = event.getInputStream();
            outputStream = event.getOutputStream();
            Scanner scanner = new Scanner(inputStream);
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.append("Server connected. Welcome to echo. \n");
            printWriter.flush();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("exit".equals(line)) {
                    printWriter.append("bye bye. \n");
                    printWriter.flush();
                    break;
                } else {
                    printWriter.append(line);
                    printWriter.append(" \n ");
                    printWriter.flush();
                }
            }
        } catch (IOException e) {
            logger.error("EchoEventHandler.doHandler IOException", e);
            throw new HandlerException(e);
        }


    }
}
