package com.example.signuploginrealtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class RegisterPetActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    EditText register_petName, register_petBreed, register_petAge, register_phoneNumber, register_petStory;
    Spinner register_petGender, register_petCategory;
    ImageView petImage;
    Button buttonSelectImage, registerPetSave_button;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        // Initialize the UI components
        register_petName = findViewById(R.id.register_petName);
        register_petBreed = findViewById(R.id.register_petBreed);
        register_petAge = findViewById(R.id.register_petAge);
        register_phoneNumber = findViewById(R.id.register_phoneNumber);
        register_petStory = findViewById(R.id.register_petStory);
        register_petGender = findViewById(R.id.register_petGender);
        register_petCategory = findViewById(R.id.register_petCategory);
        petImage = findViewById(R.id.petImage);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        registerPetSave_button = findViewById(R.id.registerPetSave_button);

        // Initialize Firebase references
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Registered Pet");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Create and set ArrayAdapters for the spinners
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        register_petGender.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        register_petCategory.setAdapter(adapter2);

        // Set the item selected listeners for the spinners
        register_petGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        register_petCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        // Set a click listener for the select image button
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Set a click listener for the save button
        registerPetSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadImage();
                } else {
                    savePetInfo(null);
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            petImage.setImageURI(imageUri);
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss();
                                    savePetInfo(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterPetActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void savePetInfo(String imageUrl) {
        String PetName = register_petName.getText().toString().trim();
        String PetBreed = register_petBreed.getText().toString().trim();
        String PetAge = register_petAge.getText().toString().trim();
        String PetOwnerPhoneNumber = register_phoneNumber.getText().toString().trim();
        String PetStory = register_petStory.getText().toString().trim();
        String PetGender = register_petGender.getSelectedItem().toString();
        String PetCategory = register_petCategory.getSelectedItem().toString();

        // Save pet info to the database
        String key = reference.push().getKey();
        HelperClassRegisterPet pet = new HelperClassRegisterPet(PetName, PetBreed, PetAge, PetOwnerPhoneNumber, PetStory, PetGender, PetCategory, imageUrl);
        reference.child(key).setValue(pet);

        Toast.makeText(this, "Pet Registered Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterPetActivity.this, HomepageActivity.class);
        startActivity(intent);

        // Clear the fields
        register_petName.setText("");
        register_petBreed.setText("");
        register_petAge.setText("");
        register_phoneNumber.setText("");
        register_petStory.setText("");
        register_petGender.setSelection(0);
        register_petCategory.setSelection(0);
        petImage.setImageResource(0); // Clear the ImageView
    }
}