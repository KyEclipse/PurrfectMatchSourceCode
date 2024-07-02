package com.example.signuploginrealtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private Context context;
    private List<HelperClassRegisterPet> petList;

    public PetAdapter(Context context, List<HelperClassRegisterPet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        HelperClassRegisterPet pet = petList.get(position);
        holder.petName.setText(pet.getPetName());
        holder.petBreed.setText(pet.getPetBreed());
        holder.petAge.setText(pet.getPetAge());
        holder.petGender.setText(pet.getPetGender());
        holder.petOwnerPhoneNumber.setText(pet.getPetOwnerPhoneNumber());
        holder.petCategory.setText(pet.getPetCategory());
        holder.petStory.setText(pet.getPetStory());

        Glide.with(context)
                .load(pet.getPetImage())
                .into(holder.petImage);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petBreed, petAge, petStory, petGender, petOwnerPhoneNumber, petCategory;
        ImageView petImage;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            petBreed = itemView.findViewById(R.id.petBreed);
            petAge = itemView.findViewById(R.id.petAge);
            petGender = itemView.findViewById(R.id.petGender);
            petOwnerPhoneNumber = itemView.findViewById(R.id.petOwnerPhoneNumber);
            petCategory = itemView.findViewById(R.id.petCategory);
            petStory = itemView.findViewById(R.id.petStory);
            petImage = itemView.findViewById(R.id.petImage);
        }
    }
}
