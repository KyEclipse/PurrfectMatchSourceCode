package com.example.signuploginrealtime;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

    public class RegisterPetActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register_pet);

            // Find the Spinner by ID
            Spinner petGenderSpinner = findViewById(R.id.register_petGender);
            Spinner petCategorySpinner = findViewById(R.id.register_petCategory);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                    R.array.gender_array, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.category_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            petGenderSpinner.setAdapter(adapter1);
            petCategorySpinner.setAdapter(adapter2);

            // Set an item selected listener for the spinner
            petGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        Toast.makeText(RegisterPetActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

            petCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        Toast.makeText(RegisterPetActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });
        }
    }

