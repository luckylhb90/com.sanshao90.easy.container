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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @Project : com.sanshao90.easy.container
 * @Description : 返回文件的事件处理类
 * @Author : sanshao90
 * @Date : 2018/3/11
 */
public class FlieEventHandler extends AbstractEventHandler<Socket> {

    private static final Logger logger = LoggerFactory.getLogger(FlieEventHandler.class);

    private final String docBase;

    public FlieEventHandler(String docBase) {
        this.docBase = docBase;
    }

    /**
     * 具体事件处理方法
     *
     * @param event
     */
    @Override
    protected void doHandler(Socket event) {
        getFile(event);
    }

    /**
     * 返回文件
     *
     * @param socket
     */
    private void getFile(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            Scanner scanner = new Scanner(inputStream);
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.append("Server connected. Welcome to File Server. \n");
            printWriter.flush();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("exit".equals(line)) {
                    printWriter.append("bye bye... \n");
                    printWriter.flush();
                    break;
                } else {
                    Path filePath = Paths.get(this.docBase, line);
                    // 如果是目录， 打印文件列表
                    if (Files.isDirectory(filePath)) {
                        printWriter.append("目录：").append(filePath.toString()).append(" 下所有文件: ").append("\n");
                        Files.list(filePath).forEach(fileName -> {
                            printWriter.append(fileName.getFileName().toString())
                                    .append("\n").flush();
                        });
                    } else if (Files.isReadable(filePath)) { // 文件可读，打印文件内容
                        printWriter.append("文件: ").append(filePath.toString())
                                .append(" 的内容是： ").append("\n").flush();
                        Files.copy(filePath, outputStream);
                        printWriter.append("\n").flush();
                    } else {
                        printWriter.append("File: ").append(filePath.toString())
                                .append(" is not found. ").append("\n").flush();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("FileEventHandler.getFile IOException ", e);
            throw new HandlerException(e);
        }
    }
}
