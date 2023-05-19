package com.excitedbroltd.excitedplayer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.excitedbroltd.excitedplayer.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TabAdapter
    private lateinit var mainActivity: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)
        adapter = TabAdapter(supportFragmentManager, lifecycle)
        mainActivity.viewPager2.adapter = adapter
        mainActivity.tabLayout.addTab(mainActivity.tabLayout.newTab().setText("SongList"))
        mainActivity.tabLayout.addTab(mainActivity.tabLayout.newTab().setText("Player"))
        mainActivity.tabLayout.addTab(mainActivity.tabLayout.newTab().setText("PlayList"))

        mainActivity.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    mainActivity.viewPager2.currentItem = tab.position
                }
                if (tab != null) {
                    Toast.makeText(applicationContext, "${tab.position}", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        mainActivity.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mainActivity.tabLayout.selectTab(mainActivity.tabLayout.getTabAt(position))
            }
        })
        mainActivity.viewPager2.setCurrentItem(1, false)
    }
}