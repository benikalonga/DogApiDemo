package com.beni.dogs.modeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beni.dogs.BR;
import com.beni.dogs.R;
import com.beni.dogs.databinding.FragmentDogBinding;
import com.beni.dogs.databinding.ItemDogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;

public class RecyclerViewDogAdapter extends RecyclerView.Adapter<RecyclerViewDogAdapter.ViewHolder> {

    private JSONArray dataModelList;
    private Context context;

    public RecyclerViewDogAdapter(JSONArray dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public RecyclerViewDogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_dog, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Dog dataModel = new Dog(dataModelList.getString(position));

            holder.itemRowBinding.setDog(dataModel);
            holder.bind(dataModel);
        } catch (Exception e) {
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
            View root = itemRowBinding.getRoot();

            root.setOnClickListener(v -> {
                showBottomSheetDialog(itemRowBinding.getDog());
            });
            this.itemRowBinding = itemRowBinding;
        }
        private void showBottomSheetDialog(Dog dog) {

            FragmentDogBinding fragmentDogBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.fragment_dog, null, false);

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(fragmentDogBinding.getRoot());

            fragmentDogBinding.setDog(dog);
            Breed.loadImage(fragmentDogBinding.imgDoc, dog.getUrl());

            bottomSheetDialog.show();
        }

        public void bind(Dog dog) {
            itemRowBinding.setVariable(BR.dog, dog);
            Breed.loadImage(itemRowBinding.imgIcon, dog.getUrl());
            itemRowBinding.executePendingBindings();
        }
    }
}
