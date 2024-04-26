package com.koje.framework.utils

import kotlin.experimental.xor

object Text {

    fun masc(data: ByteArray): ByteArray {
        val code = "mdu7chdnwl9adknaswnwdicyöwebvnmf".toByteArray()
        var codeIndex = 0
        val bytes = data
        var bytesIndex = 0

        while (bytesIndex < bytes.size) {
            bytes[bytesIndex] = bytes[bytesIndex] xor code[codeIndex]
            codeIndex++
            if (codeIndex == code.size) {
                codeIndex = 0
            }
            bytesIndex++
        }

        return bytes
    }


    fun getFormattedDuration(duration: Int): String {
        var seconds = duration
        val hours = seconds / 3600
        seconds = seconds % 3600

        val minutes = seconds / 60
        seconds = seconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }

}