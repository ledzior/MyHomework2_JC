package com.example.MyHomework2_JC;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.MyHomework2_JC.FilmFragment2.OnListFragmentInteractionListener;



public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.filmViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private final Context cxt;
    private final List<Film> listfilm;

    public FilmAdapter(Context cxt, List<Film> listfilm, OnListFragmentInteractionListener mListener) {
        this.cxt = cxt;
        this.listfilm = listfilm;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public filmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_layout, viewGroup, false);
        filmViewHolder holder = new filmViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final filmViewHolder filmViewHolder, int i) {
        filmViewHolder.film = listfilm.get(i);
        filmViewHolder.textTitle.setText(filmViewHolder.film.getTitle());
        if(!filmViewHolder.film.getImageString().contains("film")){
            Bitmap cameraImage = Camera.decodePic(filmViewHolder.film.getImageString(), 60, 80);
            filmViewHolder.imageView.setImageBitmap(cameraImage);
        }
        else{
            switch (filmViewHolder.film.getImageString()){
                case "film_annie_hall":
                    filmViewHolder.imageView.setImageDrawable(cxt.getResources().getDrawable(R.drawable.annie_hall));
                    break;
                case "film_braveheart":
                    filmViewHolder.imageView.setImageDrawable(cxt.getResources().getDrawable(R.drawable.braveheart));
                    break;
                case "film_gran_torino":
                    filmViewHolder.imageView.setImageDrawable(cxt.getResources().getDrawable(R.drawable.gran_torino));
                    break;
                case "film_interstellar":
                    filmViewHolder.imageView.setImageDrawable(cxt.getResources().getDrawable(R.drawable.interstellar));
                    break;
                case "film_no_country":
                    filmViewHolder.imageView.setImageDrawable(cxt.getResources().getDrawable(R.drawable.no_country));
                    break;
                
            }
        }
        filmViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showDetails(filmViewHolder.film);
            }
        });

        filmViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(cxt).setTitle("Delete film").setMessage("Delete an film?").setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mListener.delete(filmViewHolder.film);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", null).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listfilm.size();
    }

    class filmViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView imageView;
        public final TextView textTitle;
        public final FloatingActionButton delete;
        public Film film;

        public filmViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            imageView = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.textViewTitle);
            delete = itemView.findViewById(R.id.delete);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + textTitle.getText() + "'";
        }
    }
}
