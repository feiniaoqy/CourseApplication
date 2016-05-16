package com.feiniaoqy.bishe.Server;

import com.feiniaoqy.bishe.util.JsonUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by feiniaoqy on 2016/5/1.
 */
public class SendThread extends Thread {
    private static Socket socket;
    private static PrintWriter oos;
    private boolean isStart = true;
    private String msg;
    public SendThread(Socket socket){
        this.socket = socket;
        try {
            oos = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }
    public void setMsg(String  msg) {
        this.msg = msg;
        synchronized (this) {
            notify();
        }
    }
    @Override
    public void run() {
        try {
            while (isStart) {
                synchronized (this) {
                    if (msg != null) {
                        if (oos==null){
                            oos = new PrintWriter(socket.getOutputStream());
                        }
                        oos.println(msg);
                        oos.flush();
                        System.out.printf(msg+"====");
                        wait();// 发送完消息后，线程进入等待状态
                    }
                }
            }
            oos.close();// 循环结束后，关闭输出流和socket
            if (socket != null)
                socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
