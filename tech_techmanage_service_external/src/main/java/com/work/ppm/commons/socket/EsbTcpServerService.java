package com.work.ppm.commons.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * EsbTcpServerService
 *
 *
 */
@Slf4j
@Component
public class EsbTcpServerService extends BaseSpringService implements ApplicationListener {
    
    /**
     * serverSocket
     */
    private ServerSocket serverSocket = null;

    /**
     * port
     */
    private int port = 9081;

    /**
     * ip
     */
    private String ip;

    /**
     * running
     */
    private boolean running = false;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    /**
     * acceptThread
     */
    private Thread acceptThread;
    /**
     * start
     */
    public synchronized void startListen() {
        try {
            serverSocket = new ServerSocket(this.port);
            log.info("### listen in port [" + this.getPort() + "] success");
        } catch (IOException e) {
            // throw new GFRuntimeException("### server socket create error",
            // e);
            log.error("### server socket create error! ", e);
            return;
        }

        this.running = true;

        // 启动accept 线程 ，一个线程够了
        this.acceptThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        Socket client = serverSocket.accept();
                        if(log.isDebugEnabled()){
                            log.debug("### accept a new client");
                        }
                        new Thread(new TaskWork(client,
                                EsbTcpServerService.this)).start();
                    } catch (Throwable e) {
                        log.error("### accept client socket error", e);
                    }
                }
            }
        });
        this.acceptThread.start();
    }

    /**
     * stop
     */
    public synchronized void stopListen() {
        this.running = false;

        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
                log.info("### listen in port [{}" , this.getPort()
                        + "] stop success");
            } catch (IOException e) {
                log.error("### close server socket error{}", e);
            }
            this.serverSocket = null;
        }
    }

    @Override
    protected void onStart() {
        this.startListen();
    }

    @Override
    protected void onStop() {
        this.stopListen();
    }

    /**
     * @param fileName
     * @return
     */
    public static String readTxt(String fileName) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String s = null;
            while ((s = br.readLine()) != null) {
                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String strRet = readTxt("D:/777.txt");
        // 模拟请求socket
        OutputStream out = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 9283);
            out = socket.getOutputStream();
            out.write(strRet.getBytes());
        } catch (UnknownHostException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }

    }
}