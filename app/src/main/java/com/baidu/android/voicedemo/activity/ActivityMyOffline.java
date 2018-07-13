package com.baidu.android.voicedemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.android.voicedemo.control.MyRecognizer;
import com.baidu.android.voicedemo.recognization.CommonRecogParams;
import com.baidu.android.voicedemo.recognization.MessageStatusRecogListener;
import com.baidu.android.voicedemo.recognization.StatusRecogListener;
import com.baidu.android.voicedemo.recognization.offline.OfflineRecogParams;
import com.baidu.speech.asr.SpeechConstant;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityMyOffline extends AppCompatActivity {
    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;

    /*
     * Api的参数类，仅仅用于生成调用START的json字符串，本身与SDK的调用无关
     */
    protected CommonRecogParams apiParams;

    /*
     * 本Activity中是否需要调用离线命令词功能。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     */
    protected boolean enableOffline = true;
    protected Handler handler;
    protected StatusRecogListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Button btn = new Button(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printLogI("start");
                startRough();
            }
        });
        setContentView(btn);
        handler = new Handler() {

            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };

        listener = new MessageStatusRecogListener(handler);
        myRecognizer = new MyRecognizer(this, listener);
        apiParams = getApiParams();
        if (enableOffline) {
            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        }
    }

    protected void startRough() {
        // initRecog中已经初始化，这里释放。不需要集成到您的代码中
        //myRecognizer.release();
        //myRecognizer = null;
        // 上面不需要集成到您的代码中

        /*********************************************/
        // 1. 确定识别参数
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        // 具体的params的值在 测试demo成功后，myRecognizer.start(params);中打印

        // 2. 初始化IRecogListener (已存在)
        //StatusRecogListener listener = new MessageStatusRecogListener(null);
        // 日志显示在logcat里，UI界面上是没有的。需要显示在界面上， 这里设置为handler

        // 3 初始化 MyRecognizer (已存在)
        //myRecognizer = new MyRecognizer(this, listener);

        // 4. 启动识别
        myRecognizer.start(params);
        // 日志显示在logcat里，UI界面上是没有的。

        // 5 识别结束了别忘了释放。

        // 需要离线识别过程，需要加上 myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        // 注意这个loadOfflineEngine是异步的， 不能连着调用 start
    }

    private void handleMsg(Message msg) {
        /*if (txtLog != null && msg.obj != null) {
            txtLog.append(msg.obj.toString() + "\n");
        }*/
        if (msg.obj != null){
            printLogI(msg.obj.toString());
        }
    }
    private void printLogI(String str){
        Log.i("RecogEventAdapter", "***>" + str);
    }
    private CommonRecogParams getApiParams() {
        return new OfflineRecogParams(this);
    }

    @Override
    protected void onDestroy() {
        myRecognizer.release();
        super.onDestroy();
    }

}
