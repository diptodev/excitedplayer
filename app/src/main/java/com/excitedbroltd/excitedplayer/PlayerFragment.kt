package com.excitedbroltd.excitedplayer

import android.annotation.SuppressLint
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
import com.excitedbroltd.excitedplayer.player.MusicPlayer.Companion.songPos


class PlayerFragment : Fragment(), View.OnClickListener {
    lateinit var runnable: Runnable

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var _binding: FragmentPlayerBinding? = null
        val binding get() = _binding!!
        val handler = Handler(Looper.getMainLooper())
        lateinit var musicPlayer: MusicPlayer
    }

    var TAG = "PlayerFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicPlayer = activity?.let { MusicPlayer(it) }!!
        musicPlayer.createMedia()
        binding.seekbarControlSongId.progress = 0
        binding.ibPlayPauseId.setOnClickListener(this)
        binding.ibPlayNextId.setOnClickListener(this)
        binding.ibPlayPrevId.setOnClickListener(this)
        binding.seekbarControlSongId.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
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
                mediaPlayer?.seekTo(seekbar.progress)
                controlSeekbar(seekbar)
            }

        })
        mediaPlayer.setOnCompletionListener {
            musicPlayer.playNextSong()
            binding.seekbarControlSongId.progress = 0
            controlSeekbar(binding.seekbarControlSongId)
            binding.ibPlayPauseId.setBackgroundResource(R.drawable.icon_play)
        }
    }

    private fun controlSeekbar(seekbar: SeekBar) {
        seekbar.max = songList[songPos].duration.toInt()
        binding.tvTotalDurationId.text = formatTime(songList[songPos].duration)
        runnable = Runnable {
            binding.tvRunningDurationId.text = mediaPlayer.currentPosition.toLong()
                ?.let { formatTime(it) }
            seekbar.progress = mediaPlayer?.currentPosition!!
            handler.postDelayed(runnable, 200)
        }
        handler.postDelayed(runnable, 0)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ib_playPause_id -> {
                binding.seekbarControlSongId.let { it1 -> controlSeekbar(it1) }
                if (musicPlayer.playPause()) {
                    binding.ibPlayPauseId.setBackgroundResource(R.drawable.icon_pause)
                } else {
                    binding.ibPlayPauseId.setBackgroundResource(R.drawable.icon_play)
                }
            }
            R.id.ib_playNext_id -> {
                musicPlayer.playNextSong()
                binding.seekbarControlSongId.progress = 0
                controlSeekbar(binding.seekbarControlSongId)
                binding.ibPlayPauseId.setBackgroundResource(R.drawable.icon_pause)

            }
            R.id.ib_playPrev_id -> {
                musicPlayer.playPrevSong()
                binding.seekbarControlSongId.progress = 0
                controlSeekbar(binding.seekbarControlSongId)
                binding.ibPlayPauseId.setBackgroundResource(R.drawable.icon_pause)
            }
        }

    }


}

