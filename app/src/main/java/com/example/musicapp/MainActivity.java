package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView tviewName, tvstart, tvend;
    private ImageButton imagenext, imageback, imageplay;
    private ImageView imagePlayer;
    private ListView listView;
    private ArrayList<Music> musicArrayList;
    private MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        musicAdapter = new MusicAdapter(this, R.layout.image_row, musicArrayList);
        listView.setAdapter(musicAdapter);

        mediaPlayer = MediaPlayer.create(MainActivity.this,musicArrayList.get(position).getFile());

        //Xử lý sự kiện khi click vào 1 dòng của list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                KhoitaoMedia();
                StartNewMediaPlayer();
            }
        });

        //Xử lý sự kiện khi nhấn giữ vào 1 phần tử của list view
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse(musicArrayList.get(i).getUrl()));
                startActivity(i1);
                if(mediaPlayer.isPlaying() && mediaPlayer != null) {
                    mediaPlayer.pause();
                    imageplay.setImageResource(R.drawable.icon_play);
                }
                return false;
            }
        });

        //Xử lý sự kiện khi tác động vào seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setEnabled(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        //Xử lý sự kiện khi click vào nút play
        imageplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tviewName.setText(musicArrayList.get(position).getName());
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    imageplay.setImageResource(R.drawable.icon_play);
                }else{
                    mediaPlayer.start();
                    imageplay.setImageResource(R.drawable.icon_pause);
                }
            }
        });

        //Xử lý sự kiện khi click vào nút next
        imagenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopMediaPlayer();
                position++;
                if(position >= musicArrayList.size())
                    position = 0;
                KhoitaoMedia();
                StartNewMediaPlayer();
            }
        });

        //Xử lý sự kiện khi click vào nút back
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopMediaPlayer();
                position--;
                if(position < 0)
                    position = musicArrayList.size() - 1;
                KhoitaoMedia();
                StartNewMediaPlayer();
            }
        });
    }

    //Xử lý cập nhật thời gian liên tục
    private void UpdateTime() {
        //Xử lý cập nhật thời gian liên tục trong quá trình phát
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Lấy thời gian thực tế đang chạy
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvstart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                //Khi media player phát xong sẽ tự động dừng
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                    }
                });
                //Lặp lại thời gian cập nhật trong 1 giây
                handler.postDelayed(this, 1000);
            }
        };
        //Bắt đầu cập nhật thời gian hiện tại
        handler.postDelayed(runnable,1000);
    }

    //Khởi tạo tệp âm thành từ Media Player
    private void KhoitaoMedia() {
        mediaPlayer = MediaPlayer.create(MainActivity.this,musicArrayList.get(position).getFile());
        tviewName.setText(musicArrayList.get(position).getName());
    }

    //Chuyển đổi đối tượng Date thành chuỗi
    private void timeTotal (){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss"); //Đặt định dạng phút:giây
        tvend.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }

    //Ánh xạ các phần tử bên file xml và thêm các phần tử vào ArrayList
    private void AnhXa (){
        listView = (ListView) findViewById(R.id.lvMusic);
        tviewName = (TextView) findViewById(R.id.tvName);
        imageback = (ImageButton) findViewById(R.id.imgBack);
        imagenext = (ImageButton) findViewById(R.id.imgNext);
        imageplay = (ImageButton) findViewById(R.id.imgPlay);
        seekBar = (SeekBar) findViewById(R.id.sbarStart);
        imagePlayer = (ImageView) findViewById(R.id.imgPlayer);
        tvstart = (TextView) findViewById(R.id.tvStart);
        tvend = (TextView) findViewById(R.id.tvEnd);

        musicArrayList = new ArrayList<>();
        musicArrayList.add(new Music("Anh thanh niên","Huy R",R.drawable.anhthanhnien,R.raw.anhthanhnien,"https://www.youtube.com/watch?v=HPL74s4VPdk"));
        musicArrayList.add(new Music("Chuyện đôi ta","Emcee L ft Muội",R.drawable.chuyendoita,R.raw.chuyendoita,"https://www.youtube.com/watch?v=6eONmnFB9sw"));
        musicArrayList.add(new Music("Đóa quỳnh lan","H2K ft Yuni Boo",R.drawable.doaquynhlan,R.raw.doaquynhlan,"https://www.youtube.com/watch?v=P3h_HY5QEas"));
        musicArrayList.add(new Music("Hãy trao cho anh","Sơn Tùng MTP ft Snoop Dogg",R.drawable.haytraochoanh,R.raw.haytraochoanh,"https://www.youtube.com/watch?v=knW7-x7Y7RE"));
        musicArrayList.add(new Music("Lỡ Say Bye là Bye","Lemese x Changg",R.drawable.losaybyelabye,R.raw.losaybyelabye,"https://www.youtube.com/watch?v=bqUped2yw5E"));
        musicArrayList.add(new Music("Lừa tình","Haro x Phong Max",R.drawable.luatinh,R.raw.luatinh,"https://www.youtube.com/watch?v=2SCzVXZSWYU"));
        musicArrayList.add(new Music("Ngây thơ","Tăng Duy Tân x Phong Max",R.drawable.ngay_tho,R.raw.ngaytho,"https://www.youtube.com/watch?v=F9lDqpfHCcI"));
        musicArrayList.add(new Music("Sài gòn hôm nay mưa","JSOL x Hoàng Duyên",R.drawable.saigonhomnaymua,R.raw.saigonhomnaymua,"https://www.youtube.com/watch?v=mnBAZ-VkuEg"));
        musicArrayList.add(new Music("Túy âm","Xesi x Nhật Nguyễn",R.drawable.tuyam,R.raw.tuyam,"https://www.youtube.com/watch?v=EV-91JV4Fws"));
        musicArrayList.add(new Music("Waiting For You","Mono",R.drawable.waitingforyou,R.raw.waitingforyou,"https://www.youtube.com/watch?v=CHw1b_1LVBA"));
    }

    @Override
    //Giải phóng media player
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    //Dừng phát media player
    private void StopMediaPlayer(){
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            imageplay.setImageResource(R.drawable.icon_play);
        }
    }
    //Xử lý khi bắt đầu chạy một media player
    private void StartNewMediaPlayer(){
        mediaPlayer.start();
        imageplay.setImageResource(R.drawable.icon_pause);
        imagePlayer.setImageResource(musicArrayList.get(position).getImage());
        timeTotal();
        UpdateTime();
    }
}