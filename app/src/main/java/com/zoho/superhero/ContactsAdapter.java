package com.zoho.superhero;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zoho.superhero.pojo.SuperHeroPojo;

import java.util.ArrayList;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
class AlbumsAdapter extends RecyclerView.Adapter<com.zoho.superhero.AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SuperHeroPojo> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CardView card_view;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            card_view = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public AlbumsAdapter(Context mContext, ArrayList<SuperHeroPojo> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptor_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SuperHeroPojo album = albumList.get(position);
        holder.name.setText(album.getFullName_textView());
//        holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getImageUrl()).error(mContext.getDrawable(R.drawable.ic_superhero)).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperHeroPojo album = albumList.get(position);
                System.out.println("album = " + album);
                Intent i = new Intent(v.getContext(), HeroDetailsActivity.class);
                i.putExtra("id", String.valueOf(album.getId()));
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}