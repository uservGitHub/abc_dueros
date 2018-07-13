package com.baidu.android.voicedemo.abc

import com.baidu.speech.EventManager
import com.baidu.speech.asr.SpeechConstant
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Asr 离线驱动操作，对应离线识别适配器
 * 没有初始化、注册、释放
 */
class EventOfflineDriver(private val eventManager: EventManager):OfflineDriver,AnkoLogger {
    override val loggerTag: String
        get() = "_EOD"
    override val asr: EventManager
        get() = eventManager

    private var tick: Long = 0
    private var isLog: Boolean = false
    private var isDump: Boolean = false
    private var eventIndex = 0
    private var dump: ((String) -> Unit)? = null

    /**
     * 开启打印行信息时，自动开启info
     * 打印原则：接收内容，接收后打印；发送内容，发送前打印
     */
    fun openPrint(baseTick: Long, print: (String) -> Unit) {
        tick = baseTick
        this.dump = print
        isDump = true
        isLog = true
    }

    override fun start() {
        nativeDump(SpeechConstant.ASR_START)
        super.start()
    }

    override fun stop() {
        nativeDump(SpeechConstant.ASR_STOP)
        super.stop()
    }

    override fun cancel() {
        nativeDump(SpeechConstant.ASR_CANCEL)
        super.cancel()
    }

    override fun loadedOffline() {
        nativeDump(SpeechConstant.ASR_KWS_LOAD_ENGINE)
        super.loadedOffline()
    }

    override fun unloadedOffline() {
        nativeDump(SpeechConstant.ASR_KWS_UNLOAD_ENGINE)
        super.unloadedOffline()
    }

    private fun nativeDump(name: String) {
        if (isDump) {
            val line = "${eventIndex}\t${System.currentTimeMillis() - tick}\t$name\n"
            dump?.invoke(line)
            if (isLog) {
                info { line }
            }
        }
    }
}

class OfflineRecog