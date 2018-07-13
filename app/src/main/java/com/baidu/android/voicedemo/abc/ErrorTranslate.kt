package com.baidu.android.voicedemo.abc

import android.speech.SpeechRecognizer

object ErrorTranslation {
    fun recogError(errorCode: Int): String {
        val message: String
        when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> message = "音频问题"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> message = "没有语音输入"
            SpeechRecognizer.ERROR_CLIENT -> message = "其它客户端错误"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> message = "权限不足"
            SpeechRecognizer.ERROR_NETWORK -> message = "网络问题"
            SpeechRecognizer.ERROR_NO_MATCH -> message = "没有匹配的识别结果"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> message = "引擎忙"
            SpeechRecognizer.ERROR_SERVER -> message = "服务端错误"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> message = "连接超时"
            else -> message = "未知错误:$errorCode"
        }
        return message
    }

    fun wakeupError(errorCode: Int): String {
        val message: String
        when (errorCode) {
            1 -> message = "参数错误"
            2 -> message = "网络请求发生错误"
            3 -> message = "服务器数据解析错误"
            4 -> message = "网络不可用"
            else -> message = "未知错误:$errorCode"
        }
        return message
    }
}
