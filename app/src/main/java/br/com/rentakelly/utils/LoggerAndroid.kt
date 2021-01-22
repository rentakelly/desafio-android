package br.com.rentakelly.utils

import android.util.Log

class LoggerAndroid: Logger {
    override fun logMessege(tag: String, message: String) {
        Log.d(tag, message)
    }
}