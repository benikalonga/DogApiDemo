package com.beni.dogs.modeles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beni.dogs.BR;
import com.beni.dogs.BreedActivity;
import com.beni.dogs.R;
import com.beni.dogs.databinding.ItemBreedBinding;
import com.beni.dogs.networks.Network;
import com.beni.dogs.networks.Result;

import java.util.List;

public class RecyclerViewBreedAdapter extends RecyclerView.Adapter<RecyclerViewBreedAdapter.ViewHolder> implements ItemClickListener<Breed> {

    private List<Breed> dataModelList;
    private Context context;

    public RecyclerViewBreedAdapter(List<Breed> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public RecyclerViewBreedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        ItemBreedBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_breed, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Breed dataModel = dataModelList.get(position);
        holder.itemRowBinding.setBreed(dataModel);
        holder.bind(dataModel);
        holder.itemRowBinding.setItemClickListener(this);
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemBreedBinding itemRowBinding;

        public ViewHolder(ItemBreedBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Breed breed) {
            itemRowBinding.setVariable(BR.breed, breed);
            Network.getImgByBreed(breed.getDesignation(), new Result<String>() {
                @Override
                public void result(String s) {
                    Breed.loadImage(itemRowBinding.imgIcon, s);
                }
            });
            itemRowBinding.executePendingBindings();
        }
    }

    public void onItemClick (Breed b) {
        ((BreedActivity)context).onClickItem(b);
    }
}
