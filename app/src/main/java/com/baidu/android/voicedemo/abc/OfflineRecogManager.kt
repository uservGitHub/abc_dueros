package com.baidu.android.voicedemo.abc

import android.content.Context
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory

/**
 * 离线识别管理
 */
class OfflineRecogManager(private val ctx:Context){
    private val asr:EventManager
    protected val adapter:RecogOfflineAdapter
    protected val driver:EventOfflineDriver
    protected var status:Int
        private set
    init {
        adapter = RecogOfflineAdapter(object :OfflineListener{
            override fun onAsrBegin() {

            }

            override fun onAsrEnd() {

            }

            override fun onAsrExit() {

            }

            override fun onAsrFinish(recogResult: RecogResult) {

            }

            override fun onAsrFinishError(errorMessage: String, descMessage: String, recogResult: RecogResult) {

            }

            override fun onAsrReady() {

            }

            override fun onOfflineLoaded() {

            }

            override fun onOfflineUnLoaded() {

            }
        })
        asr = EventManagerFactory.create(ctx, "asr")
        asr.registerListener(adapter)
        status = StatusValue.STATUS_NONE
        driver = EventOfflineDriver(asr)
    }

    fun initRecog(){

        status = StatusValue.STATUS_NONE
        driver.loadedOffline()
    }

    fun start(){
        driver.start()
    }

    fun release(){
        //asr.unregisterListener()
    }
}