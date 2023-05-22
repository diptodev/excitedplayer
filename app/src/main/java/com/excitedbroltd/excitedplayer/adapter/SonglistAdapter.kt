package com.excitedbroltd.excitedplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.excited.lighterplayer.dataclass.formatTime
import com.excitedbroltd.excitedplayer.MainActivity
import com.excitedbroltd.excitedplayer.R

class SonglistAdapter(private val context: Context) :
    RecyclerView.Adapter<SonglistAdapter.MyViewHolderClass>() {

    private val songlist = MainActivity.songList

    class MyViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songThumb = itemView.findViewById<ImageView>(R.id.album_art_id)
        var songTitle = itemView.findViewById<TextView>(R.id.tv_songTitle_id)
        val songArtist = itemView.findViewById<TextView>(R.id.tv_songArtistName_id)
        val songDuration = itemView.findViewById<TextView>(R.id.tv_songDuration_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderClass {
        val view = LayoutInflater.from(context).inflate(R.layout.songlist, parent, false)
        return MyViewHolderClass(view)

    }

    override fun getItemCount(): Int = songlist.size

    override fun onBindViewHolder(holder: MyViewHolderClass, position: Int) {
        var songDetails = songlist[position]
        holder.songTitle.text = songDetails.title
        holder.songArtist.text = songDetails.artist
        holder.songDuration.text = formatTime(songDetails.duration)
    }

}