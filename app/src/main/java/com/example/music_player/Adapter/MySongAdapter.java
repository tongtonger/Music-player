package com.example.music_player.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.music_player.R;
import com.example.music_player.db.Song;

import java.util.List;

public class MySongAdapter extends ArrayAdapter<Song> {
    private int resourceId;

    public MySongAdapter(Context context, int textViewResourceId, List<Song> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Song song = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView songName = (TextView) view.findViewById(R.id.name);
        songName.setText(song.song);
        return view;
    }
}
