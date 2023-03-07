package com.example.lamzone.Model;

import android.graphics.drawable.Drawable;

public class Participant {

    int image;
    private String name, email;

    public Participant(String email, String name, int image) {
        this.email = email;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {return image;}

    public void setImage(int image) {this.image = image;}
}
