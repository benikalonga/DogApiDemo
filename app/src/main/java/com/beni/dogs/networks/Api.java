package com.beni.dogs.networks;

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

}
