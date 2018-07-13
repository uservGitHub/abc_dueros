package com.baidu.android.voicedemo.abc

import com.baidu.android.voicedemo.abc.RecogResult

interface OfflineListener {

    /**
     * ASR_START 输入事件调用后，引擎准备完毕
     */
    fun onAsrReady()

    /**
     * onAsrReady后检查到用户开始说话
     */
    fun onAsrBegin()

    /**
     * 检查到用户开始说话停止，或者ASR_STOP 输入事件调用后，
     */
    fun onAsrEnd()

    /**
     * onAsrBegin 后 随着用户的说话，返回的临时结果
     *
     * @param results     可能返回多个结果，请取第一个结果
     * @param recogResult 完整的结果
     */
    //fun onAsrPartialResult(results: Array<String>, recogResult: RecogResult)

    /**
     * 最终的识别结果
     *
     * @param results     可能返回多个结果，请取第一个结果
     * @param recogResult 完整的结果
     */
    //用在长语音中
    //fun onAsrFinalResult(results: Array<String>, recogResult: RecogResult)

    fun onAsrFinish(recogResult: RecogResult)

    fun onAsrFinishError(errorMessage: String, descMessage: String,
                         recogResult: RecogResult)
    /*fun onAsrFinishError(errorCode: Int, subErrorCode: Int, errorMessage: String, descMessage: String,
                         recogResult: RecogResult)*/

    /**
     * 长语音识别结束
     */
    //fun onAsrLongFinish()

    //语音合成中会传递
    //fun onAsrVolume(volumePercent: Int, volume: Int)

    //传输语音
    //fun onAsrAudio(data: ByteArray, offset: Int, length: Int)

    fun onAsrExit()

    //长语音会用
    //fun onAsrOnlineNluResult(nluResult: String)

    //收到这个，离线才起作用（比如没有离线文件或其他原因导致，无法离线）
    fun onOfflineLoaded()

    //何时用，难道是离线无法成功时触发？（初步认为是卸载离线文件，转在线）
    fun onOfflineUnLoaded()
}
