package com.example.MyHomework2_JC;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



import java.util.ArrayList;
import java.util.List;

public class FilmFragment2 extends Fragment {

    private OnListFragmentInteractionListener mListener;
    public static List<Film> filmList;
    private static RecyclerView recyclerView;

    public FilmFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        if (view instanceof RecyclerView || view instanceof RelativeLayout) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            filmList = new ArrayList<>();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            FilmAdapter eventAdapter = new FilmAdapter(getContext(),MainActivity.filmList, mListener);
            recyclerView.setAdapter(eventAdapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(mListener.checkChanged())
        {
            notifyDataSetChange();
            mListener.setChanged(false);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static  RecyclerView.Adapter getAdapter()
    {
        return recyclerView.getAdapter();
    }

    public void notifyDataSetChange()
    {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public interface OnListFragmentInteractionListener {
        void showDetails(Film event);
        void reset();
        void delete(Film deleted);
        boolean checkChanged();
        void setChanged(boolean val);
    }
}
