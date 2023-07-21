package com.example.musicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Music> musicList;

    public MusicAdapter(Context context, int layout, List<Music> musicList) {
        this.context = context;
        this.layout = layout;
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgHinh;
        TextView tvname, tvauthor;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            holder.imgHinh = (ImageView) view.findViewById(R.id.imgSong);
            holder.tvname = (TextView) view.findViewById(R.id.tvSongName);
            holder.tvauthor = (TextView) view.findViewById(R.id.tvSongAuthor);

            view.setTag(holder);
        }else
            holder = (ViewHolder) view.getTag();

        //Gán giá trị cho mỗi dòng của listview tương ứng với mỗi lần click 1 dòng
        Music music = musicList.get(i);
        holder.imgHinh.setImageResource(music.getImage());
        holder.tvname.setText(music.getName());
        holder.tvauthor.setText(music.getAuthor());

        return view;
    }
}
