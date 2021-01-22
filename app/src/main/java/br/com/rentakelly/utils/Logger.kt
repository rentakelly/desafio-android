package br.com.rentakelly.utils

interface Logger {
    fun logMessege(
        tag: String = "", message: String
    )
}