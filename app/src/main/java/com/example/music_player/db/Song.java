package com.example.music_player.db;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Song implements Serializable {

    public String singer;
    public String song;//歌曲名
    public String path;//歌曲的地址
    public int duration;//长度
    public long size;//大小

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Song song =(Song) obj;
        if(this.song.equals(song.song) && this.path.equals(song.path)){
            return true;
        }
        else return false;
    }
}
