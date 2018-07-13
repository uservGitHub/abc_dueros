package com.baidu.android.voicedemo.abc

import com.baidu.speech.EventManager
import org.json.JSONObject
import com.baidu.speech.asr.SpeechConstant



interface OfflineDriver {
    val asr:EventManager
    /**
     * 启动
     */
    fun start(){
        val params = LinkedHashMap<String, Any>()
        params[SpeechConstant.DECODER] = 2
        params[SpeechConstant.ACCEPT_AUDIO_VOLUME] = false
        //params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号
        asr.send(SpeechConstant.ASR_START, JSONObject(params).toString(), null, 0, 0)
    }

    /**
     * 停止（录音）
     */
    fun stop(){
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0)
    }

    /**
     * 取消
     * 取消本次识别，取消后将立即停止不会返回识别结果。
     * cancel 与stop的区别是 cancel在stop的基础上，完全停止整个识别流程，
     */
    fun cancel(){
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
    }

    /**
     * 装载离线文件
     */
    fun loadedOffline(){
        val params = LinkedHashMap<String, Any>()
        params[SpeechConstant.DECODER] = 2
        params[SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH] = "assets://baidu_speech_grammar.bsg"
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, JSONObject(params).toString(), null, 0, 0)
    }

    /**
     * 卸载离线文件
     */
    fun unloadedOffline(){
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0)
    }
}