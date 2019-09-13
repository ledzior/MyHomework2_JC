package com.example.MyHomework2_JC;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

import static com.example.MyHomework2_JC.MainActivity.images;

public class Film implements Parcelable {
    private int id;
    private String title;
    private String premiere;
    private String director;
    private int image;
    private String imageString;

    public Film(int id, String director, String title, String premiere, int image, String imageString) {
        this.id = id;
        this.title = title;
        this.premiere = premiere;
        this.director = director;
        this.image = image;
        if(image<=5 && !imageString.contains(".jpg"))
        {
            switch (image){
                case 0:
                    imageString="film_annie_hall";
                    break;
                case 1:
                    imageString="film_braveheart";
                    break;
                case 2:
                    imageString="film_gran_torino";
                    break;
                case 3:
                    imageString="film_interstellar";
                    break;
                case 4:
                    imageString="film_no_country";
                    break;
            }
        }
        this.imageString = imageString;
    }

    public void setImage() {
        Random r = new Random();
        int i = r.nextInt(images.length);
        switch (i){
            case 0:
                imageString="film_annie_hall";
                break;
            case 1:
                imageString="film_braveheart";
                break;
            case 2:
                imageString="film_gran_torino";
                break;
            case 3:
                imageString="film_interstellar";
                break;
            case 4:
                imageString="film_no_country";
                break;

        }
        setImageString(imageString);
        this.image = i;
    }

    protected Film(Parcel in) {
        id = in.readInt();
        title = in.readString();
        premiere = in.readString();
        director = in.readString();
        image = in.readInt();
        imageString = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getCountry() {
        return director;
    }

    public String getImageString() {
        return imageString;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return premiere;
    }

    public int getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(premiere);
        dest.writeString(director);
        dest.writeInt(image);
        dest.writeString(imageString);
    }
}
