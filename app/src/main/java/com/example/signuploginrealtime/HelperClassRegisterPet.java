package com.example.signuploginrealtime;

public class HelperClassRegisterPet {
    private String petName;
    private String petBreed;
    private String petAge;
    private String petOwnerPhoneNumber;
    private String petStory;
    private String petGender;
    private String petCategory;
    private String petImage;

    // Default constructor (required by Firebase)
    public HelperClassRegisterPet() {
        // Default constructor required for calls to DataSnapshot.getValue(HelperClassRegisterPet.class)
    }

    // Constructor with parameters
    public HelperClassRegisterPet(String petName, String petBreed, String petAge, String petOwnerPhoneNumber,
                                  String petStory, String petGender, String petCategory, String petImage) {
        this.petName = petName;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petOwnerPhoneNumber = petOwnerPhoneNumber;
        this.petStory = petStory;
        this.petGender = petGender;
        this.petCategory = petCategory;
        this.petImage = petImage;
    }

    // Getters and setters (generated for each field)
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetOwnerPhoneNumber() {
        return petOwnerPhoneNumber;
    }

    public void setPetOwnerPhoneNumber(String petOwnerPhoneNumber) {
        this.petOwnerPhoneNumber = petOwnerPhoneNumber;
    }

    public String getPetStory() {
        return petStory;
    }

    public void setPetStory(String petStory) {
        this.petStory = petStory;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getPetImage() {
        return petImage;
    }

    public void setPetImage(String petImage) {
        this.petImage = petImage;
    }
}
