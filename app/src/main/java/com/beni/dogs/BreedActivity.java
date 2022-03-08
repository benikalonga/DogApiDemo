package com.beni.dogs;

import android.content.Intent;
import android.os.Bundle;

import com.beni.dogs.modeles.Breed;
import com.beni.dogs.modeles.Data;
import com.beni.dogs.modeles.RecyclerViewBreedAdapter;
import com.beni.dogs.networks.Api;
import com.beni.dogs.networks.Network;
import com.beni.dogs.networks.Result;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beni.dogs.databinding.ActivityBreedBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BreedActivity extends AppCompatActivity {

    private ActivityBreedBinding binding;

    private BreedPresenter breedPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBreedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        breedPresenter = new BreedPresenter();
        binding.setPresenter(breedPresenter);

        initViews();

    }

    private void initViews() {
        int length = Data.BREEDS_LIST.length();
//        int length = 12;
        breedPresenter.breedStatus = (length + " " + (length == 1 ? "breed" : "breeds") + " found");

        binding.buttonRefresh.setOnClickListener(v -> {
            binding.buttonRefresh.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
            binding.loading.startBlinking();
            reloadBreeds();
        });

        binding.recyclerBreed.setLayoutManager(new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false));

        populateData();
    }
    private void populateData() {
        List<Breed> breedList = new ArrayList<>();
        Iterator<String> i = Data.BREEDS_LIST.keys();
        while (i.hasNext()){
            try {
                String key = i.next();
                int subBreedLength = new JSONArray(Data.BREEDS_LIST.getString(key)).length();
                String name = subBreedLength == 0? "" : subBreedLength + (subBreedLength == 1?" subbreed" : " subbreeds");
                String icon = Api.BASE_URL+name+
                breedList.add(new Breed(key, "icon", name));
            }
            catch (Exception e){
                continue;
            }
        }
        binding.recyclerBreed.setAdapter(new RecyclerViewBreedAdapter(breedList, this));
    }

    private void reloadBreeds() {
        Network.getAllBreeds(jsonObject -> {
            try {

                String status = jsonObject.getString(Data.STATUS);

                if (TextUtils.equals(status, Data.STATUS_SUCCESS)) {
                    Data.BREEDS_LIST = jsonObject.getJSONObject(Data.MESSAGE);
                    int length = Data.BREEDS_LIST.length();

                    binding.loading.setVisibility(View.GONE);
                    binding.buttonRefresh.setVisibility(View.VISIBLE);
                    populateData();

                    if (length <= 0) {
                        breedPresenter.breedStatus = "No breed found";
                    } else {

                        breedPresenter.breedStatus = length + " " + (length == 1 ? "breed" : "breeds") + " found";

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                binding.loading.setVisibility(View.GONE);
                binding.buttonRefresh.setVisibility(View.VISIBLE);

                breedPresenter.breedStatus = "No breed found";
                e.printStackTrace();
            }
        });
    }
    public void onClickItem(Breed breed){
        Intent intent = new Intent(this, DogsActivity.class);
        intent.putExtra(Breed.class.getSimpleName(), breed.getDesignation());
        startActivity(intent);
    }

    public class BreedPresenter {
        public String breedStatus = "";

    }
}