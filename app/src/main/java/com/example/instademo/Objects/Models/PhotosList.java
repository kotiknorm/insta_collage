package com.example.instademo.Objects.Models;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.instademo.Objects.Constants;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class PhotosList implements Parcelable {

    private  List<Photo> photoList = new ArrayList<Photo>();

    private String idUser;

    public String getNameUser() {
        return nameUser;
    }

    public String getIdUser() {
        return idUser;
    }

    private String nameUser;

    public PhotosList(){
    }

    public void addPhoto(Photo photo){
        photoList.add(photo);
    }

    public List<Photo> getPhotoList(){
        return photoList;
    }

    public PhotosList(JSONArray json, String _idUser, String _nameUser){

        idUser = _idUser;
        nameUser = _nameUser;

            for (int i = 0; i < json.length(); i++) {

                try {

                String urlPhoto = json.getJSONObject(i).getJSONObject("images").getJSONObject("low_resolution").getString("url");
                int countLikes = json.getJSONObject(i).getJSONObject("likes").getInt("count");

                    String location = "";
                    try {
                         location = json.getJSONObject(i).getJSONObject("caption").getString("text");
                    }catch (Exception e){
                        Log.e(Constants.TAG, e.toString());
                    }
                Photo photoInAccount = new Photo(urlPhoto, countLikes, location);
                photoList.add(photoInAccount);

                }catch (Exception e){
                    Log.e(Constants.TAG, e.toString());
                }
            }

    }

    public int countPhoto(){
        return photoList.size();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int N = photoList.size();
        dest.writeInt(N);
        dest.writeString(idUser);
        dest.writeString(nameUser);
        if (N > 0) {
            for (int i=0; i < photoList.size(); i++) {
                dest.writeString(photoList.get(i).getUrlPhoto());
                dest.writeInt(photoList.get(i).getCountLikes());
                dest.writeString(photoList.get(i).getLocation());
            }
        }
    }

    public static final Creator<PhotosList> CREATOR = new Creator<PhotosList>() {
        public PhotosList createFromParcel(Parcel source) {
            return new PhotosList(source);
        }

        public PhotosList[] newArray(int size) {
            return new PhotosList[size];
        }
    };

    private PhotosList(Parcel source) {
        final int N = source.readInt();
        idUser = source.readString();
        nameUser = source.readString();
        for (int i = 0; i < N; i++) {
            String urlPhoto = source.readString();
            int countLikes = source.readInt();
            String location = source.readString();
            photoList.add(new Photo(urlPhoto, countLikes, location));
        }
    }


    public class Photo {

        private final String UrlPhoto;

        private final int countLikes;

        private final String location;

        public Photo(String _url, int _count, String _location) {
            this.UrlPhoto = _url;
            this.countLikes = _count;
            this.location = _location;
        }

        public String getUrlPhoto() {
            return UrlPhoto;
        }

        public String getLocation() {
            return location;
        }

        public int getCountLikes() {
            return countLikes;
        }

    }












}

