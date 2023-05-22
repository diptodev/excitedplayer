package com.excitedbroltd.excitedplayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.excited.lighterplayer.dataclass.formatTime
import com.excitedbroltd.excitedplayer.MainActivity.Companion.songList
import com.excitedbroltd.excitedplayer.databinding.FragmentPlayerBinding
import com.excitedbroltd.excitedplayer.player.MusicPlayer
import com.excitedbroltd.excitedplayer.player.MusicPlayer.Companion.mediaPlayer


class PlayerFragment : Fragment() {
    lateinit var runnable: Runnable

    companion object {
        private var _binding: FragmentPlayerBinding? = null
        val binding get() = _binding
        val handler = Handler(Looper.getMainLooper())
    }

    var TAG = "PlayerFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val musicPlayer = activity?.let { MusicPlayer(it) }
        musicPlayer?.createMedia()
        binding?.seekbarControlSongId?.progress = 0
        binding?.ibPlayPauseId?.setOnClickListener {
            binding?.seekbarControlSongId?.let { it1 -> controlSeekbar(it1) }
            if (musicPlayer?.playPause() == true) {
                binding?.ibPlayPauseId?.setBackgroundResource(R.drawable.icon_pause)
            } else {
                binding?.ibPlayPauseId?.setBackgroundResource(R.drawable.icon_play)
            }
        }
        binding?.seekbarControlSongId?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    seekbar.progress = progress
                    handler.removeCallbacks(runnable)
                    binding?.tvRunningDurationId?.text = formatTime(progress.toLong())

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekbar: SeekBar) {
                mediaPlayer.seekTo(seekbar.progress)
                controlSeekbar(seekbar)
            }

        })
    }

    private fun controlSeekbar(seekbar: SeekBar) {
        seekbar.max = songList[0].duration.toInt()
        binding?.tvTotalDurationId?.text = formatTime(songList[0].duration)
        runnable = Runnable {
            binding?.tvRunningDurationId?.text = formatTime(mediaPlayer.currentPosition.toLong())
            seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 200)
        }
        handler.postDelayed(runnable, 0)
    }
}

