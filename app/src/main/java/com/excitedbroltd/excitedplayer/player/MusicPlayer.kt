package com.excitedbroltd.excitedplayer.player

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.excitedbroltd.excitedplayer.MainActivity

class MusicPlayer(val context: Context) {
    companion object {
        lateinit var mediaPlayer: MediaPlayer
        var isPlaying = false
    }

    val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
    val sessionId = audioManager.generateAudioSessionId()
    val at = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
        .build()

    fun createMedia() {
        val uri = Uri.parse(MainActivity.songList[0].path)
        mediaPlayer =
            MediaPlayer.create(context, uri, null, at, sessionId)
    }

    fun playPause(): Boolean {
        isPlaying = if (isPlaying) {
            mediaPlayer.pause()
            false
        } else {
            mediaPlayer.start()
            true
        }
        return isPlaying
    }

}