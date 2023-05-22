package com.excitedbroltd.excitedplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.excitedbroltd.excitedplayer.adapter.SonglistAdapter


class SonglistFragment : Fragment(R.layout.fragment_songlist) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rv_songList_id)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = context?.let {
            SonglistAdapter(it)
        }
    }

}