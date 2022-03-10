package com.beni.dogs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.beni.dogs.databinding.ActivityDogsBinding;
import com.beni.dogs.modeles.Breed;
import com.beni.dogs.modeles.Data;
import com.beni.dogs.modeles.Dog;
import com.beni.dogs.modeles.RecyclerViewDogAdapter;
import com.beni.dogs.networks.Network;

import org.json.JSONArray;
import org.json.JSONException;

public class DogsActivity extends AppCompatActivity {

    private ActivityDogsBinding binding;

    private DogPresenter dogPresenter;
    private String breedDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDogsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dogPresenter = new DogPresenter();
        binding.setPresenter(dogPresenter);

        try {
            initViews();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initViews() throws JSONException {

        breedDesignation = getIntent().getStringExtra(Breed.class.getSimpleName());
        String breedList = Data.BREEDS_LIST.getString(breedDesignation);

        dogPresenter.dogTitle = breedDesignation;

        binding.buttonRefresh.setOnClickListener(v -> {
            reloadDogs();
        });

        reloadDogs();

        binding.recyclerDog.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false));
    }

    private void startLoading(){
        binding.buttonRefresh.setVisibility(View.GONE);
        binding.loading.setVisibility(View.VISIBLE);
        binding.loading.startBlinking();
    }
    private void stopLoading(){
        binding.buttonRefresh.setVisibility(View.VISIBLE);
        binding.loading.setVisibility(View.GONE);
        binding.loading.stopBlinking();
    }

    private void populateData(JSONArray jsonDog) {
        binding.recyclerDog.setAdapter(new RecyclerViewDogAdapter(jsonDog, this));
    }

    private void reloadDogs() {
        startLoading();
        Network.getAllImgByBreed(breedDesignation, jsonObject -> {
            try {

                String status = jsonObject.getString(Data.STATUS);

                if (TextUtils.equals(status, Data.STATUS_SUCCESS)) {

                    JSONArray dogsList = jsonObject.getJSONArray(Data.MESSAGE);
                    int length = dogsList.length();

                    populateData(dogsList);

                    if (length <= 0) {
                        dogPresenter.dogTitle = "No dog found";
                    } else {
                        dogPresenter.dogTitle = breedDesignation+" ("+length+")";
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
                stopLoading();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();

                dogPresenter.dogTitle = "No breed found";
                stopLoading();
                e.printStackTrace();
            }
        });
    }

    public void onClickItem(Dog dog) {

    }

    public class DogPresenter {
        public String dogTitle = "";
    }
}