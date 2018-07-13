package com.baidu.android.voicedemo.abc

/**
 * 状态值的定义
 */
class StatusValue {
    companion object {

        val STATUS_NONE = 2

        val STATUS_READY = 3
        val STATUS_SPEAKING = 4
        val STATUS_RECOGNITION = 5

        val STATUS_FINISHED = 6
        val STATUS_STOPPED = 10

        val STATUS_WAITING_READY = 8001
        val WHAT_MESSAGE_STATUS = 9001

        val STATUS_WAKEUP_SUCCESS = 7001
        val STATUS_WAKEUP_EXIT = 7003
    }
}
