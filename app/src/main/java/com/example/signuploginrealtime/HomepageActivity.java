package com.example.signuploginrealtime;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<HelperClassRegisterPet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petList = new ArrayList<>();
        petAdapter = new PetAdapter(this, petList);
        recyclerView.setAdapter(petAdapter);

        FirebaseDatabase.getInstance().getReference("Registered Pet")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        petList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                HelperClassRegisterPet pet = snapshot.getValue(HelperClassRegisterPet.class);
                                if (pet != null) {
                                    petList.add(pet);
                                }
                            }
                            petAdapter.notifyDataSetChanged();
                        } else {
                            // Handle case where no pets are found
                            Log.d("HomepageActivity", "No pets found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("HomepageActivity", "onCancelled", databaseError.toException());
                    }
                });
    }
}
