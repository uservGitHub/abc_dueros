package com.baidu.android.voicedemo.abc

import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * 离线（可假定离线）识别适配器，对应离线驱动操作
 */
class RecogOfflineAdapter(private val listener: OfflineListener):EventListener,AnkoLogger{
    override val loggerTag: String
        get() = "_ROA"
/*    protected var lastJson:String? = null
        private set*/


    private var tick:Long = 0
    private var isLog:Boolean = false
    private var isDump:Boolean = false
    private var eventIndex = 0
    private var dump:((String)->Unit)? = null
    /**
     * 开启打印行信息时，自动开启info
     * 打印原则：接收内容，接收后打印；发送内容，发送前打印
     */
    fun openPrint(baseTick:Long, print:(String)->Unit){
        tick = baseTick
        this.dump = print
        isDump = true
        isLog = true
    }

    override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
        //lastJson = params
        if (name == null){
            return
        }

        when(name!!){
            SpeechConstant.CALLBACK_EVENT_ASR_READY -> {
                if(isDump) {
                    eventIndex++
                }
                //就绪等待语音
                listener.onAsrReady()
            }
            SpeechConstant.CALLBACK_EVENT_ASR_BEGIN ->{
                //检测到语音开始
                listener.onAsrBegin()
            }
            SpeechConstant.CALLBACK_EVENT_ASR_END ->{
                //停止
                listener.onAsrEnd()
            }
            SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {
                //识别结束，有可能是错的
                val result = RecogResult.parseJson(params!!)
                if (result.hasError()) {
                    val errCode = result.error
                    val subErr = result.subError
                    listener.onAsrFinishError(ErrorTranslation.recogError(errCode), result.desc, result)
                } else {
                    //对结果进行特殊的额外打印
                    if (isDump) {
                        val text = if (result.resultsRecognition.size > 0) result.resultsRecognition[0] else ""
                        val line = "${eventIndex}\t${System.currentTimeMillis() - tick}\t【text】=$text\n"
                        dump?.invoke(line)
                        if (isLog){
                            info { line }
                        }
                    }
                    listener.onAsrFinish(result)
                }
            }
            SpeechConstant.CALLBACK_EVENT_ASR_EXIT ->{
                //结束
                listener.onAsrExit()
            }
            SpeechConstant.CALLBACK_EVENT_ASR_LOADED ->{
                //离线预料库载入
                listener.onOfflineLoaded()
            }
            SpeechConstant.CALLBACK_EVENT_ASR_UNLOADED ->{
                //离线预料库未能成功载入??
                listener.onOfflineUnLoaded()
            }

            //region 未启用，但记录
            SpeechConstant.CALLBACK_EVENT_ASR_AUDIO ->{
                //传来的语音
            }
            SpeechConstant.CALLBACK_EVENT_ASR_VOLUME ->{
                //传来的音量设置
            }
            SpeechConstant.CALLBACK_EVENT_ASR_LONG_SPEECH ->{
                //长语音
            }
            SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL ->{
                //长语音模式中的部分语音
            }
            //endregion

            else -> {
                //未能想到的情况

            }
        }

        if (isDump) {
            val line = "${eventIndex}\t${System.currentTimeMillis()-tick}\t$name\n"
            dump?.invoke(line)
            if (isLog){
                info { line }
            }
        }
    }

}


