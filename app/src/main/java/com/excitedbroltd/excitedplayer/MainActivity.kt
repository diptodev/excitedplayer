package com.excitedbroltd.excitedplayer

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.excited.lighterplayer.dataclass.Music
import com.excitedbroltd.excitedplayer.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TabAdapter
    private lateinit var mainActivity: ActivityMainBinding

    companion object {
        lateinit var songList: ArrayList<Music>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization();
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
                if (position == 1) {
                    mainActivity.miniPlayerId.visibility = View.GONE
                    mainActivity.view.visibility = View.GONE
                } else {
                    mainActivity.miniPlayerId.visibility = View.VISIBLE
                    mainActivity.view.visibility = View.VISIBLE
                }
            }
        })
        mainActivity.viewPager2.setCurrentItem(1, false)
        try {
            songList = getAudioFile()

        } catch (e: java.lang.Exception) {

        }
    }

    private fun initialization() {
        runtimePermission()
        if (runtimePermission()) {
            songList = getAudioFile()
            Log.d("TAGList", "onCreate: ${songList.size}")
        }
    }

    private fun runtimePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                108
            )
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        try {
            if (requestCode == 108) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                else
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        108
                    )
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    // get audio file list
    @SuppressLint("Range")
    private fun getAudioFile(): ArrayList<Music> {
        val tempList = ArrayList<Music>()

        val selection = Media.IS_MUSIC + "!=0"
        val uri = Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(Media._ID, Media.TITLE, Media.ALBUM, Media.ARTIST, Media.DURATION, Media.DATA)
        val cursor =
            this.contentResolver.query(
                uri,
                projection,
                selection,
                null,
                Media.TITLE + " ASC",
                null
            )

        if (cursor != null && cursor.moveToFirst()) {
            do {
//                    val mID = cursor.getColumnIndex(Media._ID);
//                    val mTitle = cursor.getColumnIndex(Media.TITLE);
//                    val mAlbum = cursor.getColumnIndex(Media.ALBUM);
//                    val mArtist = cursor.getColumnIndex(Media.ARTIST);
//                    val mDuration = cursor.getColumnIndex(Media.DURATION);
//                    val mData = cursor.getColumnIndex(Media.DATA);
//                    val mThumb = cursor.getColumnIndex(Media.ALBUM_ID)
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Media.TITLE))
                val album = cursor.getString(cursor.getColumnIndexOrThrow(Media.ALBUM))
                val id = cursor.getString(cursor.getColumnIndexOrThrow(Media._ID))
                val path = cursor.getString(cursor.getColumnIndexOrThrow(Media.DATA))
                val atrist = cursor.getString(cursor.getColumnIndexOrThrow(Media.ARTIST))
                val duration = cursor.getLong(cursor.getColumnIndexOrThrow(Media.DURATION))
                var mThumbId = cursor.getLong(cursor.getColumnIndexOrThrow(Media._ID))
                val thumbUri = Uri.parse("content://media/external/audio/albumart")
                val thumb = ContentUris.withAppendedId(thumbUri, mThumbId).toString()
                val file = File(path)
                if (file.exists()) {
                    val music = Music(
                        id = id,
                        title = title,
                        album = album,
                        artist = atrist,
                        duration = duration,
                        path = path,
                        thumb = thumb
                    )
                    tempList.add(music)
                }

            } while (cursor.moveToNext())
            cursor.close()
        }
        return tempList
    }
}