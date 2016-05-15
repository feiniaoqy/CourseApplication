package com.feiniaoqy.bishe.Server;


import java.net.Socket;

/**
 * 在一个线程中开启SocketServer服务
 */
public class ServerThread extends Thread {

    private static GetDataThread getDataThread = null;
    private static SendThread sendThread = null;
    private Socket socket = null;

    private SocketListener socketListener;

    @Override
    public void run() {
        //start the socket server
        SocketServer socketServer = SocketServer.getSocketServer();
        //get the connect socket
        socket = socketServer.getSocket();
        socketListener.socketListener(socket);
    }

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }
}