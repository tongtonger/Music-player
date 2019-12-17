package com.example.music_player;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.music_player.db.Song;
import com.example.music_player.util.MusicDatabaseHelper;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {
    private MusicDatabaseHelper dbHelper;
    Button btOn = null;
    Button btNext = null;
    Button btLast = null;
    MediaPlayer mediaPlayer = new MediaPlayer();
    SeekBar bar = null;
    boolean isChanging = true;
    Song song = null;
    Song song1 = new Song();; //用于歌单
    TextView currentTime = null;
    TextView allTime = null;
    ListView listView = null;
    TextView songName = null;
    ChooseSongFragment songPaperFragment;


    public DrawerLayout drawerLayout;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            currentTime.setText(formatTime(Integer.parseInt(msg.obj.toString())));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new MusicDatabaseHelper(this, "SongPaper.db", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        drawerLayout = findViewById(R.id.drawer_layout);
        songName = findViewById(R.id.song_name);
        btOn = findViewById(R.id.onPause);
        btLast = findViewById(R.id.last);
        btNext = findViewById(R.id.next);
        currentTime = findViewById(R.id.currentTime);
        allTime = findViewById(R.id.allTime);
        bar = findViewById(R.id.seekbar);//时间轴

        LayoutInflater inflater = getLayoutInflater();
        View viewOfFragment = inflater.inflate(R.layout.choose_song, null);
        listView = viewOfFragment.findViewById(R.id.list_view);

        songPaperFragment = (ChooseSongFragment) getSupportFragmentManager().findFragmentById(R.id.songpaper);
        Intent intent = getIntent();
        song = (Song) intent.getSerializableExtra("song");
//        Log.e("name", song.song);
//        Log.e("path", song.path);
        SQLiteDatabase db = dbHelper.getWritableDatabase();//打开一个数据库并返回SQLiteDatabase类型的可读写的操作对象

        song1.song = song.song;
        song1.path = song.path;

        if(song.equals(song1))
            Log.e("compare","相同");
        else
            Log.e("compare","不同");

        String sql = "insert into song(name, path, idOfPaper) values(?,?,?)";
        String sql1 = "select * from song where name = ? and idOfPaper = ?";
        Cursor cursor = db.rawQuery(sql1, new String[]{song.song, String.valueOf(1)});//查询
        if(cursor.moveToNext()){
        }
        else
            db.execSQL(sql, new String[]{song.song, song.path, String.valueOf(1)});//插入歌曲

        //intent = getIntent();
        songPaperFragment.refresh();//更新歌单的显示
        String path = intent.getStringExtra("uri");
        playMusic(song1);



        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });

        btLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("当前音乐", song.song);
                songPaperFragment.last(song1);
            }
        });



        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("当前音乐", song.song);
                songPaperFragment.next(song1);
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            new Thread(new SeekBarThread()).start();
        }
    }

    class SeekBarThread implements Runnable {

        @Override
        public void run() {
            while (mediaPlayer.isPlaying()) {
                Message message = new Message();
                message.obj =  bar.getProgress();
                handler.sendMessage(message);
                //Log.e("jindu", String.valueOf(bar.getProgress()));
                // 将SeekBar位置设置到当前播放位置
                bar.setProgress(mediaPlayer.getCurrentPosition());
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                    //播放进度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }

    public void playMusic(final Song song){
        try {
            songName.setText(song.song);
            song1.song =song.song;
            Log.e("next",song1.song);
            song1.path = song.path;
            String path = song.path;
            //mediaPlayer.release();
            Log.e("play", "zhixing");

            mediaPlayer.reset();


            Log.e("path", path);
            Uri uri = Uri.parse(path);

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();//初始化
            //mediaPlayer.setLooping(true);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    songPaperFragment.next(song);
                }
            });

            bar.setMax(mediaPlayer.getDuration());
            allTime.setText("/" + formatTime(mediaPlayer.getDuration()));
            Log.e("max", String.valueOf(bar.getProgress()));
            mediaPlayer.start();
            new Thread(new SeekBarThread()).start();
            // new Thread(new SeekBarThread()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
