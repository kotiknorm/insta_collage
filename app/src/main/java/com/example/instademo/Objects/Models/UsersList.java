package com.example.instademo.Objects.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 14.08.14.
 */
public class UsersList implements Parcelable {

    private List<User> userList = new ArrayList<User>();

    public UsersList(){
    }

    public void addPhoto(User user){
        userList.add(user);
    }

    public List<User> getUserList(){
        return userList;
    }

    public UsersList(JSONArray json){
        try {

            for (int i = 0; i < json.length(); i++) {
                String id = json.getJSONObject(i).getString("id");
                String name = json.getJSONObject(i).getString("username");
                String info = json.getJSONObject(i).getString("bio");
                String urlPhoto = json.getJSONObject(i).getString("profile_picture");
                User userInAccount = new User(id, name, info, urlPhoto);
                userList.add(userInAccount);
            }
        }catch (Exception e){

        }
    }

    public int countPhoto(){
        return userList.size();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int N = userList.size();
        dest.writeInt(N);
        if (N > 0) {
            for (int i=0; i < userList.size(); i++) {
                dest.writeString(userList.get(i).getName());
                dest.writeString(userList.get(i).getInfo());
                dest.writeString(userList.get(i).getUrlPhoto());
            }
        }
    }

    public static final Creator<UsersList> CREATOR = new Creator<UsersList>() {
        public UsersList createFromParcel(Parcel source) {
            return new UsersList(source);
        }

        public UsersList[] newArray(int size) {
            return new UsersList[size];
        }
    };

    private UsersList(Parcel source) {
        final int N = source.readInt();
        for (int i = 0; i < N; i++) {
            String id = source.readString();
            String name = source.readString();
            String info = source.readString();
            String urlPhoto = source.readString();
            userList.add(new User(id, name, info, urlPhoto));
        }
    }


    public class User {

        private final String name;

        private final String info;

        private final String id;

        private final String urlPhoto;

        public User(String _id, String _name, String _info, String _urlPhoto) {
            id = _id;
            this.name = _name;
            this.info = _info;
            this.urlPhoto = _urlPhoto;
        }

        public String getId() {
            return id;
        }


        public String getUrlPhoto() {
            return urlPhoto;
        }

        public String getInfo() {
            return info;
        }

        public String getName() {
            return name;
        }


    }












}