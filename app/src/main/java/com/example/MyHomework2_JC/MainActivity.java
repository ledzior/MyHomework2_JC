package com.example.MyHomework2_JC;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JC, FilmFragment2.OnListFragmentInteractionListener, FilmFragment.OnViewInteractionListener{

    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private static final int DETAILS = 4;
    private final String CURRENT_FILM = "current_film";
    public static final String FILM_ID="film_id";
    public static final String FILM_TITLE="film_title";
    public static final String FILM_DIRECTOR="film_director";
    public static final String FILM_PREMIERE="film_premiere";
    public static final String FILM_IMAGE="film_image";
    public static final String FILM_IMAGE_POSITION="film_image_int";
    public static final int BUTTON_REQUEST1=1;
    public static final int BUTTON_REQUEST2=2;
    private final String SHARED_PREFERENCES = "shared_preferences";
    private final String NUMBER = "number";
    public static final Bundle extras = new Bundle();
    public int id, image;
    private Film currentFilm;
    public String title, premiere, director;
    public static List<Film> filmList = new ArrayList<>();
    public static Integer[] images = {R.drawable.annie_hall, R.drawable.braveheart, R.drawable.gran_torino, R.drawable.interstellar, R.drawable.no_country};
    public static String mCurrentPhotoPath;
    public Film cameraEvent;
    private boolean Changed;
    public static ImageView im;


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(currentFilm != null) outState.putParcelable(CURRENT_FILM, currentFilm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) currentFilm = savedInstanceState.getParcelable(CURRENT_FILM);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restore();
        Changed = false;

        im = (ImageView) findViewById(R.id.imageView);
        FilmFragment fragment = new FilmFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.activityMain, fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if(currentFilm != null) displayInFragment(currentFilm);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        save();
    }

    @Override
    public void showDetails(Film film) {
        currentFilm = film;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            displayInFragment(film);
        }
        else {
            Intent intent = new Intent(this, ActivityDet.class);
            intent.putExtra("film", film);
            startActivityForResult(intent, DETAILS);
        }

    }

    @Override
    public void reset() {
        save();
        restore();
    }

    @Override
    public void delete(Film deleted) {
        filmList.remove(deleted);
        reset();
    }

    public boolean checkChanged()
    {
        return Changed;
    }

    @Override
    public void setChanged(boolean val) {
        Changed = val;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Camera(View view) {
        Intent pickAdd = new Intent(this, FragmentsActivity.class);
        startActivityForResult(pickAdd,BUTTON_REQUEST1);
    }

    public void Add(View view) {
        Intent pickAdd = new Intent(this, FragmentsActivity.class);
        startActivityForResult(pickAdd,BUTTON_REQUEST2);
    }

    public void displayInFragment(Film film)
    {
        FilmFragment detailsFragment = ((FilmFragment) getSupportFragmentManager().findFragmentById(R.id.rightFragment));
        if(detailsFragment!=null)
        {
            detailsFragment.displayFilm(film);
        }
    }

    private void save()
    {
        SharedPreferences films = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = films.edit();
        editor.clear();
        editor.putInt(NUMBER, filmList.size());
        for(int i=0; i<filmList.size(); i++)
        {
            Film film = filmList.get(i);
            editor.putString(FILM_TITLE+i, film.getTitle());
            editor.putString(FILM_DIRECTOR+i, film.getCountry());
            editor.putString(FILM_PREMIERE+i, film.getDescription());
            editor.putInt(FILM_IMAGE_POSITION+i, film.getImage());
            editor.putString(FILM_IMAGE+i, film.getImageString());
            editor.putInt(FILM_ID+i, i);
        }
        editor.apply();
    }

    public void clearList()
    {
        filmList.clear();
    }

    private void restore()
    {
        SharedPreferences films = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        int numOfTracks = films.getInt(NUMBER, 0);
        if(numOfTracks != 0)
        {
            clearList();
            for(int i=0; i<numOfTracks; i++)
            {
                String title = films.getString(FILM_TITLE+i, "0");
                String director = films.getString(FILM_DIRECTOR+i, "0");
                String premiere = films.getString(FILM_PREMIERE+i, "0");
                int position = films.getInt(FILM_IMAGE_POSITION+i,0);
                String image = films.getString(FILM_IMAGE+i, "0");
                filmList.add(new Film(i, director, title, premiere, position, image));
            }
        }
        FilmFragment2.getAdapter().notifyDataSetChanged();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try{
                photoFile = CreatePhotoFile(cameraEvent.getTitle());
            }
            catch(IOException ex)
            {

            }
            if(photoFile!=null) {
                Uri photoURI = FileProvider.getUriForFile(this, getString(R.string.fileProvider), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File CreatePhotoFile(String name) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = name + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void setPhotoPath(String photoPath)
    {
        mCurrentPhotoPath = photoPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode==RESULT_OK){
            switch(requestCode) {
                case BUTTON_REQUEST1:
                    cameraEvent = data.getParcelableExtra("film");
                    dispatchTakePictureIntent();
                    break;
                case BUTTON_REQUEST2:
                    Film extraAdd = data.getParcelableExtra("film");
                    extraAdd.setImage();
                    add(extraAdd);
                    break;
                case FilmFragment.REQUEST_IMAGE_CAPTURE_DET:
                    currentFilm.setImageString(mCurrentPhotoPath);
                    setChanged(true);
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    cameraEvent.setImageString(mCurrentPhotoPath);
                    add(cameraEvent);
                    setChanged(true);
                    break;
                case DETAILS:
                    if(data.getBooleanExtra(ActivityDet.DATA_CHANGED_KEY, false))
                    {
                        reset();
                        setChanged(true);
                    }
                    break;
                default:
                        break;
            }
        }
    }


    public void add(Film film){
        filmList.add(film);
        reset();}

    @Override
    public void changeImage(Film film, String image) {
        film.setImageString(image);
        setChanged(true);
    }
}
