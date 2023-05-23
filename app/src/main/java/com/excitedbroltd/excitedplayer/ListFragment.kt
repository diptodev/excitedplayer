package com.excitedbroltd.excitedplayer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.excitedbroltd.excitedplayer.adapter.SonglistAdapter


class ListFragment : Fragment(), Shuffle {

    companion object {
        lateinit var rv: RecyclerView
        lateinit var contextm: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Fragment", "onViewCreated: Fragment opend")
        rv = view.findViewById(R.id.rv_songList_id)
        rv.layoutManager = LinearLayoutManager(context)
        contextm = requireContext()
        var playerFragment = PlayerFragment()
        playerFragment.rearrangeList()
    }

    override fun shuffle(isShuffled: Boolean) {
        rv.adapter = context?.let {
            SonglistAdapter(it, MainActivity.songList)
        }
    }
}