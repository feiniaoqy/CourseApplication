package com.feiniaoqy.bishe.Server;

import com.feiniaoqy.bishe.dao.AnswerDao;
import com.feiniaoqy.bishe.dataserver.HandleDataService;
import com.feiniaoqy.bishe.util.JsonUtil;
import entity.Answer;
import entity.Comment;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;

/**
 * Created by feiniaoqy on 2016/5/1.
 */
public class GetDataThread extends Thread {
    private final Socket socket;
    private String msg = "";
    private boolean isStart = true;
    private BufferedReader ois;
    private MessageListener messageListener;// 消息监听接口对象
    private boolean flag = false;

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
                    JSONTokener jsonTokener = new JSONTokener(msg);
                    try {
                        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                        if (jsonObject.has("answer")){
                            Answer answer = JsonUtil.jsonToAnswer(jsonObject.getJSONObject("answer"));
                            HandleDataService.insertAnswer(answer);
                        }else if (jsonObject.has("comment")){
                            Comment comment = JsonUtil.JsonToComment(jsonObject.getJSONObject("comment"));
                            HandleDataService.insertComment(comment);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //对接收到的消息做处理
                    //通知界面更新数据
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

    public void setFlag(boolean flag){
        this.flag = flag;
    }
}