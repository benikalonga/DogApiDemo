package com.beni.dogs.modeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beni.dogs.BR;
import com.beni.dogs.BreedActivity;
import com.beni.dogs.R;
import com.beni.dogs.databinding.ItemBreedBinding;
import com.beni.dogs.databinding.ItemDogBinding;
import com.beni.dogs.networks.Network;
import com.beni.dogs.networks.Result;

import org.json.JSONArray;

import java.util.List;

public class RecyclerViewDogAdapter extends RecyclerView.Adapter<RecyclerViewDogAdapter.ViewHolder> implements ItemClickListener<Dog> {

    private JSONArray dataModelList;
    private Context context;

    public RecyclerViewDogAdapter(JSONArray dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public RecyclerViewDogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDogBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_dog, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Dog dataModel = new Dog(dataModelList.getString(position));

            holder.itemRowBinding.setDog(dataModel);
            holder.bind(dataModel);
            holder.itemRowBinding.setItemClickListener(this);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return dataModelList.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemDogBinding itemRowBinding;

        public ViewHolder(ItemDogBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Dog dog) {
            itemRowBinding.setVariable(BR.dog, dog);
            Breed.loadImage(itemRowBinding.imgIcon, dog.getUrl());
            itemRowBinding.executePendingBindings();
        }
    }

    public void onItemClick (Dog d) {

    }
}
