package com.beni.dogs.modeles;

import com.google.gson.annotations.SerializedName;

public class Dog {
    @SerializedName("url")
    private String url;

    @SerializedName("breed")
    private String breed;

    public Dog(String url) {
        setUrl(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setBreed(url.substring(30, url.lastIndexOf('/')));
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
