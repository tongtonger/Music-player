package com.example.music_player.db;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Song implements Serializable {
    /**
     * 歌手
     */
    public String singer;
    /**
     * 歌曲名
     */
    public String song;
    /**
     * 歌曲的地址
     */
    public String path;
    /**
     * 歌曲长度
     */
    public int duration;
    /**
     * 歌曲的大小
     */
    public long size;


    @Override
    public boolean equals(@Nullable Object obj) {
        Song song =(Song) obj;
        if(this.song.equals(song.song) && this.path.equals(song.path)){
            return true;
        }
        else return false;
    }
}
