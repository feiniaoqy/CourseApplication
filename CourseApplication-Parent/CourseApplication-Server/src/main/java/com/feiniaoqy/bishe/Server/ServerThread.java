package com.feiniaoqy.bishe.Server;


import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 在一个线程中开启SocketServer服务
 */
public class ServerThread extends Thread {

    private ThreadListener threadListener;
    private boolean flag = false;

    public Map<String,SendThread> sendThreadMap = new HashMap<String, SendThread>();
    public Map<String,GetDataThread> getDataThreadMap = new HashMap<String, GetDataThread>();

    @Override
    public void run() {
        //start the socket server
        SocketServer socketServer = SocketServer.getSocketServer();
        /***
         * 每监听到socket连接 都为它开启两个子线程
         */
        socketServer.setSocketListener(new SocketListener() {
            public void socketListener(Socket socket) {
                if (socket.isConnected()){
                    String ip = socket.getInetAddress().toString();
                    //开启两个子线程
                    SendThread sendThread = new SendThread(socket);
                    GetDataThread getDataThread = new GetDataThread(socket);
                    sendThread.start();
                    getDataThread.start();
                    sendThreadMap.put(ip,sendThread);
                    getDataThreadMap.put(ip,getDataThread);
                    threadListener.socketListener(1);
                }
            }
        });
        socketServer.getSocket();
    }
    public Map<String, SendThread> getSendThreadMap(){
        return sendThreadMap;
    }

    public Map<String, GetDataThread> getGetDataThreadMap(){
        return getDataThreadMap;
    }

    public void setThreadListener(ThreadListener threadListener) {

        this.threadListener = threadListener;
    }

    public void setFlag(boolean flag){
        this.flag = flag;
    }
}