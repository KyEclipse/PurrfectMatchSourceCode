package com.example.signuploginrealtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<HelperClassRegisterPet> petList;

    private Button dogCategoryBtn, catCategoryBtn, rabbitCategoryBtn, birdCategoryBtn;
    private SearchView searchByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petList = new ArrayList<>();
        petAdapter = new PetAdapter(this, petList);
        recyclerView.setAdapter(petAdapter);

        // Initialize buttons
        dogCategoryBtn = findViewById(R.id.dogCategoryBtn);
        catCategoryBtn = findViewById(R.id.catCategoryBtn);
        rabbitCategoryBtn = findViewById(R.id.rabbitCategoryBtn);
        birdCategoryBtn = findViewById(R.id.birdCategoryBtn);

        // Initialize SearchView
        searchByName = findViewById(R.id.searchByName);
        searchByName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search operation here
                searchPetsByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search operation as text changes (optional)
                return false;
            }
        });

        // Set onClickListeners for category buttons
        dogCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPetsByCategory("Dog");
            }
        });

        catCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPetsByCategory("Cat");
            }
        });

        rabbitCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPetsByCategory("Rabbit");
            }
        });

        birdCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPetsByCategory("Bird");
            }
        });

        // Initially load all pets
        loadAllPets();

        // Handle click on RegisterPetRedirectText to navigate to RegisterPetActivity
        TextView registerPetRedirectText = findViewById(R.id.RegisterPetRedirectText);
        registerPetRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, RegisterPetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadAllPets() {
        FirebaseDatabase.getInstance().getReference("Registered Pet")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        petList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HelperClassRegisterPet pet = snapshot.getValue(HelperClassRegisterPet.class);
                            if (pet != null) {
                                petList.add(pet);
                            }
                        }
                        petAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("HomepageActivity", "onCancelled", databaseError.toException());
                    }
                });
    }

    private void filterPetsByCategory(String category) {
        Query query = FirebaseDatabase.getInstance().getReference("Registered Pet")
                .orderByChild("petCategory").equalTo(category);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HelperClassRegisterPet pet = snapshot.getValue(HelperClassRegisterPet.class);
                    if (pet != null) {
                        petList.add(pet);
                    }
                }
                petAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HomepageActivity", "onCancelled", databaseError.toException());
            }
        });
    }

    private void searchPetsByName(String name) {
        Query query = FirebaseDatabase.getInstance().getReference("Registered Pet")
                .orderByChild("petName").startAt(name).endAt(name + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HelperClassRegisterPet pet = snapshot.getValue(HelperClassRegisterPet.class);
                    if (pet != null) {
                        petList.add(pet);
                    }
                }
                petAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HomepageActivity", "onCancelled", databaseError.toException());
            }
        });
    }
}
