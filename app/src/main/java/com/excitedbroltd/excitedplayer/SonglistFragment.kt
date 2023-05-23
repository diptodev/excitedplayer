package com.excitedbroltd.excitedplayer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.excitedbroltd.excitedplayer.adapter.SonglistAdapter
import com.excitedbroltd.excitedplayer.adapter.TabAdapter


class SonglistFragment() : Fragment(R.layout.fragment_songlist),Shuffle {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rv_songList_id)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = context?.let {
            SonglistAdapter(it, MainActivity.songList)
        }

    }

    override fun shuffle(isShuffled: Boolean) {
        Log.d("Fragment", "shuffle: Changed")
    }


}