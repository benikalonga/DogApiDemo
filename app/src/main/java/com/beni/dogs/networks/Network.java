package com.beni.dogs.networks;

import android.util.Log;

import androidx.annotation.NonNull;

import com.beni.dogs.modeles.Data;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Network {

    public static void getAllBreeds(@NonNull Result<JSONObject> jsonResult) {

        Call<ResponseBody> call = RetrofitClient.getInstance().getMyApi().getAllBreads();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    jsonResult.result(new JSONObject(response.body().string()));

                    Log.i("Response status", "success");
                }
                catch (Exception e) {
                    JSONObject jError = new JSONObject();
                    try {
                        jError.put(Data.STATUS, Data.STATUS_ERROR);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    jsonResult.result(jError);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                JSONObject jError = new JSONObject();
                try {
                    jError.put(Data.STATUS, Data.STATUS_ERROR);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                jsonResult.result(jError);
                t.printStackTrace();
            }
        });
    }
    public static void getImgByBreed(String breed, @NonNull Result<String> stringResult) {

        Call<ResponseBody> call = RetrofitClient.getInstance().getMyApi().getImagesByBreed(breed);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    stringResult.result(jsonObject.getJSONArray(Data.MESSAGE).getString(0));

                    Log.i("Response status", "success");
                }
                catch (Exception e) {
                    stringResult.result(Data.STATUS_ERROR);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stringResult.result(Data.STATUS_ERROR);
                t.printStackTrace();
            }
        });
    }
    public static void getAllImgByBreed(String breed, @NonNull Result<JSONObject> jsonResult) {

        Call<ResponseBody> call = RetrofitClient.getInstance().getMyApi().getImagesByBreed(breed);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    jsonResult.result(new JSONObject(response.body().string()));

                    Log.i("Response status", "success");
                }
                catch (Exception e) {
                    JSONObject jError = new JSONObject();
                    try {
                        jError.put(Data.STATUS, Data.STATUS_ERROR);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    jsonResult.result(jError);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                JSONObject jError = new JSONObject();
                try {
                    jError.put(Data.STATUS, Data.STATUS_ERROR);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                jsonResult.result(jError);
                t.printStackTrace();
            }
        });
    }
}
