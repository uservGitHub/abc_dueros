package com.baidu.android.voicedemo.abc


import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by fujiayi on 2017/6/24.
 */
class RecogResult {
    var origalJson: String = ""
        private set
    var resultsRecognition: Array<String> = emptyArray()
        private set
    var origalResult: String = ""
        private set
    var sn: String = "" // 日志id， 请求有问题请提问带上sn
        private set
    var desc: String = ""
        private set
    var resultType: String = ""
        private set
    var error = -1
        private set
    var subError = -1
        private set

    val isFinalResult: Boolean
        get() = "final_result" == resultType

    val isPartialResult: Boolean
        get() = "partial_result" == resultType

    val isNluResult: Boolean
        get() = "nlu_result" == resultType

    fun hasError(): Boolean {
        return error != ERROR_NONE
    }

    companion object {
        private val ERROR_NONE = 0

        fun parseJson(jsonStr: String): RecogResult {
            val result = RecogResult()
            result.origalJson = jsonStr
            try {
                val json = JSONObject(jsonStr)
                val error = json.optInt("error")
                val subError = json.optInt("sub_error")
                result.error = error
                result.desc = json.optString("desc")
                result.resultType = json.optString("result_type")
                result.subError = subError
                if (error == ERROR_NONE) {
                    result.origalResult = json.getString("origin_result")
                    val arr = json.optJSONArray("results_recognition")
                    if (arr != null) {
                        val size = arr.length()
                        result.resultsRecognition = Array<String>(size, {arr.getString(it)})
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return result
        }
    }
}
