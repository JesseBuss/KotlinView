package com.jbuss.kotlinview.utils

/**
 * Created by jbuss on 2/23/18.
 */

fun Int.getMmSsString(): String {
    val format = "%02d:%02d"
    return String.format(format, this / 60, this % 60)
}