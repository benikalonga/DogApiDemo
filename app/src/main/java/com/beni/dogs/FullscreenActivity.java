package com.beni.dogs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.beni.dogs.databinding.ActivityFullscreenBinding;
import com.beni.dogs.modeles.Breed;
import com.beni.dogs.modeles.Data;
import com.beni.dogs.networks.Network;
import com.beni.dogs.networks.Result;
import com.beni.dogs.networks.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import app.m4ntis.blinkingloader.BlinkingLoader;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenActivity extends AppCompatActivity {

    View mContentView;
    BlinkingLoader loadingView;
    FancyButton buttonRefresh;

    private ActivityFullscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mContentView = binding.fullscreenContent;
        loadingView = binding.loading;
        buttonRefresh = binding.buttonRefresh;

        //Show in fullscreen
        setFullscreen();

        mContentView.setOnClickListener(v->{
            ActionBar actionBar = getSupportActionBar();
            if (actionBar!= null ){
                if (actionBar.isShowing()){
                    setFullscreen();
                }
                else{
                    actionBar.show();
                }
            }
        });

        initViews();
    }
    private void setFullscreen(){
        if (Build.VERSION.SDK_INT >= 30) {
            mContentView.getWindowInsetsController().hide(
                    WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    public void initViews() {
        binding.setLoadingState("Dog Api Demo");

        buttonRefresh.setOnClickListener(v -> {
            initStart();
            getAllBreeds();
        });

        mContentView.postDelayed(this::initStart, 2000);
        mContentView.postDelayed(this::errorLoading, 5000);

    }

    public void initStart() {
        binding.setLoadingState("Loading...");
        loadingView.setVisibility(View.VISIBLE);
        buttonRefresh.setVisibility(View.GONE);
    }

    public void errorLoading() {
        loadingView.setVisibility(View.GONE);
        buttonRefresh.setVisibility(View.VISIBLE);
        binding.setLoadingState("No data found");
    }

    private void getAllBreeds() {

        Network.getAllBreeds(jsonObject -> {
            try {

                String status = jsonObject.getString(Data.STATUS);

                if (TextUtils.equals(status, Data.STATUS_SUCCESS)) {
                    Data.BREEDS_LIST = jsonObject.getJSONObject(Data.MESSAGE);
                    int length = Data.BREEDS_LIST.length();

                    loadingView.setVisibility(View.GONE);

                    if (length <= 0) {
                        errorLoading();
                    } else
                    {
                        binding.setLoadingState(length + " " + (length == 1 ? "breed" : "breeds") + " found");
                        mContentView.postDelayed(() -> {
                            startActivity(new Intent(FullscreenActivity.this, BreedActivity.class));
                            finish();
                        }, 1500);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                    FullscreenActivity.this.errorLoading();;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();

                FullscreenActivity.this.errorLoading();
                e.printStackTrace();
            }
        });
    }
}