package com.beni.dogs.modeles;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.beni.dogs.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;

public class Breed {
    @SerializedName("designation")
    private String designation;

    @SerializedName("icon")
    private String icon;

    @SerializedName("list")
    private String list;

    public Breed(String designation, String icon, String list) {
        this.designation = designation;
        this.list = list;
        this.icon = icon;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    // important code for loading image here
    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .centerCrop())
                .load(imageURL)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_baseline_pets_24)
                .into(imageView);
    }
}
