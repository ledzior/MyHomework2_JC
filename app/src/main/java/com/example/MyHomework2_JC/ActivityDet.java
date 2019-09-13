package com.example.MyHomework2_JC;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ActivityDet extends AppCompatActivity implements FilmFragment.OnViewInteractionListener {

    public static String DATA_CHANGED_KEY = "dataSetChanged";
    private boolean imgChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.det_activity);
        imgChanged = false;
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra(DATA_CHANGED_KEY, imgChanged);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    public void setImgChanged(boolean val)
    {
        imgChanged = val;
    }

    @Override
    public void changeImage(Film film, String image)
    {
        MainActivity.filmList.get(film.getId()).setImageString(image);
        setImgChanged(true);
    }

    @Override
    public void setPhotoPath(String photoPath) {

    }
}
