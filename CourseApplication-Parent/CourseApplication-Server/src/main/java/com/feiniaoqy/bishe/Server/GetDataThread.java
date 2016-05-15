package com.feiniaoqy.bishe.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by feiniaoqy on 2016/5/1.
 */
public class GetDataThread extends Thread {
    private final Socket socket;
    private String msg = "";
    private boolean isStart = true;
    private BufferedReader ois;
    private MessageListener messageListener;// 消息监听接口对象

    public GetDataThread(Socket socket){
        this.socket = socket;
        try {
            ois = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (isStart) {
                if (ois==null){
                    ois = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                }
                msg = ois.readLine();
                if ((!"".equals(msg))&&msg!=null){
                    messageListener.Message(msg);
                }
            }
            ois.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 提供给外部的消息监听方法
     * @param messageListener
     *            消息监听接口对象
     */
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }
}