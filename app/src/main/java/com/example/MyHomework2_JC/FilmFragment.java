package com.example.MyHomework2_JC;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FilmFragment extends Fragment implements View.OnClickListener{

    private ImageView imageView;
    private TextView titleView;
    private TextView directorView;
    private TextView premiereView;
    private Intent intent;
    public static final int REQUEST_IMAGE_CAPTURE_DET = 5;
    private OnViewInteractionListener mListener;
    private String mCurrentPhotoPath;
    private Film mCurrent;

    public FilmFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.film_fragment, container, false);
        imageView = view.findViewById(R.id.imageView2);
        titleView = view.findViewById(R.id.textView);
        directorView = view.findViewById(R.id.textView3);
        premiereView = view.findViewById(R.id.textView5);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewInteractionListener) {
            mListener = (OnViewInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private File CreatePhotoFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = mCurrent.getTitle() + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        mListener.setPhotoPath(mCurrentPhotoPath);
        return image;
    }

    @Override
    public void onClick(View v) {
        if(mCurrent!=null)
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null)
            {
                File photoFile = null;
                try{
                    photoFile = CreatePhotoFile();
                }
                catch(IOException ex)
                {

                }
                if(photoFile!=null)
                {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), getString(R.string.fileProvider), photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_DET);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.imageView2).setOnClickListener(this);
        intent = getActivity().getIntent();
        if(intent != null)
        {
            Film film = intent.getParcelableExtra("film");
            if(film != null) displayFilm(film);
        }

    }

    public void displayFilm(Film film)
    {
        mCurrent = film;
        titleView.setText(film.getTitle());
        directorView.setText(film.getCountry());
        premiereView.setText(film.getDescription());

        if(film.getImageString() != null && !film.getImageString().isEmpty()) {
            if (film.getImageString().contains("film")) {
                switch (film.getImageString()) {
                    case "film_annie_hall":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.annie_hall));
                        break;
                    case "film_braveheart":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.braveheart));
                        break;
                    case "film_gran_torino":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.gran_torino));
                        break;
                    case "film_interstellar":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.interstellar));
                        break;
                    case "film_no_director":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.no_country));
                        break;
                }
            }
            else{
                Handler handler = new Handler();
                imageView.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {
                        imageView.setVisibility(View.VISIBLE);
                        Bitmap cameraImage = Camera.decodePic(mCurrent.getImageString(), imageView.getWidth(), imageView.getHeight());
                        imageView.setImageBitmap(cameraImage);
                    }
                }, 200);
            }
        }

    }

    @Override
    public void onActivityResult(int request_code, int result_code, Intent data)
    {
        if(request_code == REQUEST_IMAGE_CAPTURE_DET && result_code == RESULT_OK)
        {
            FragmentActivity holdingActivity = getActivity();
            if(holdingActivity != null)
            {
                ImageView filmImage = holdingActivity.findViewById(R.id.imageView2);
                Bitmap cameraImage = Camera.decodePic(mCurrentPhotoPath, filmImage.getWidth(), filmImage.getHeight());
                filmImage.setImageBitmap(cameraImage);
                mCurrent.setImageString(mCurrentPhotoPath);
                mListener.changeImage(mCurrent, mCurrentPhotoPath);
            }
        }
    }

    public interface OnViewInteractionListener {
        void changeImage(Film film, String image);
        void setPhotoPath(String photoPath);
    }


}
