package com.excited.lighterplayer.dataclass

import java.util.concurrent.TimeUnit

data class Music(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val path: String,
    val thumb: String
)

fun formatTime(duration: Long): String {
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes * 60;
    return "${if (minutes < 10) "0$minutes" else minutes} : ${if (seconds < 10) "0$seconds" else seconds}"
}
