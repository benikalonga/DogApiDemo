package com.beni.dogs.networks;

import com.beni.dogs.modeles.Breed;
import com.beni.dogs.modeles.Data;
import com.beni.dogs.modeles.Dog;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL = "https://dog.ceo/api/";

    // LIST ALL BREEDS
    @GET("breeds/list/all")
    Call<ResponseBody> getAllBreads();

    // RANDOM IMAGE BY BREED
    @GET("breed/{breed}/images/random")
    Call<ResponseBody> getRandomImage(@Path("breed") String breed);

    @GET("breed/{breed}/images/")
    Call<ResponseBody> getImagesByBreed(@Path("breed") String breed);

    // LIST BY BREED
    @GET("breed/%s/images")
    Call<List<Breed>> getByBreed();

    // LIST ALL BREEDS
    @GET("breed/%s/list")
    Call<List<Breed>> getSubBreeds();

}
