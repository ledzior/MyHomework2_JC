package com.example.MyHomework2_JC;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class FragmentsActivity extends AppCompatActivity implements AddFragment.OnAddInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments_activity);
    }

    @Override
    public void add(Film film) {
        Intent intent = new Intent();
        intent.putExtra("film", film);
        setResult(RESULT_OK, intent);
        finish();
    }
}
