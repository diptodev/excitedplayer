package com.excitedbroltd.excitedplayer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.excitedbroltd.excitedplayer.ListFragment
import com.excitedbroltd.excitedplayer.PlayerFragment
import com.excitedbroltd.excitedplayer.SonglistFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SonglistFragment()
            }
            1 -> {
                PlayerFragment()
            }
            else -> {
                ListFragment()
            }
        }
    }
}