package com.example.bookingapptim11.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.clients.ClientUtils;
import com.example.bookingapptim11.login.AuthManager;
import com.example.bookingapptim11.models.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private EditText emailInput, firstNameInput, lastNameInput, addressInput, phoneNumberInput, passwordInput;
    private CheckBox showPasswordCheckbox, notificationsCheckBox;

    private Profile profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.profileImage);
        emailInput = view.findViewById(R.id.emailInput);
        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        addressInput = view.findViewById(R.id.addressInput);
        phoneNumberInput = view.findViewById(R.id.phoneNumberInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        showPasswordCheckbox = view.findViewById(R.id.showPasswordCheckbox);
        notificationsCheckBox = view.findViewById(R.id.notificationsCheckbox);


        Button saveButton = view.findViewById(R.id.saveButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){deleteProfile();}
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProfilePicture();
            }
        });

        showPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordInput.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                passwordInput.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        getProfileData();


        return view;
    }

    private void loadUserProfileInfo() {

        profileImageView.setImageResource(R.drawable.ic_person);
        emailInput.setText(this.profile.getEmail());
        firstNameInput.setText(this.profile.getName());
        lastNameInput.setText(this.profile.getLastName());
        addressInput.setText(this.profile.getAddress());
        phoneNumberInput.setText(this.profile.getPhoneNumber());
        notificationsCheckBox.setChecked(this.profile.isNotifications());
    }

    private void changeProfilePicture() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);
        }
    }

    private void saveChanges() {

        updateProfile();
        this.passwordInput.setText("");
        Toast.makeText(requireActivity(), "Profile Saved", Toast.LENGTH_SHORT).show();
    }

    private void deleteProfile(){
        Call<Void> call = ClientUtils.profileService.delete(AuthManager.getUserEmail(),"Bearer " + AuthManager.getToken());
        call.enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200){

                    Log.d("REZ","Meesage recieved");
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
        AuthManager.logOut(getActivity());
        Navigation.findNavController(getView()).navigate(R.id.nav_home);
    }

    private Profile collectFormData(){
        return new Profile(this.emailInput.getText().toString(), this.passwordInput.getText().toString(),
                this.firstNameInput.getText().toString(), this.lastNameInput.getText().toString(), this.addressInput.getText().toString(),
                this.phoneNumberInput.getText().toString(), this.notificationsCheckBox.isChecked(), "");
    }

    private void updateProfile(){

        Call<Profile> call = ClientUtils.profileService.edit(AuthManager.getUserEmail(), collectFormData(), "Bearer " + AuthManager.getToken());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    System.out.println(response.body());
                    profile = response.body();
                    loadUserProfileInfo();
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }


        });
    }

    private void getProfileData(){
        Call<Profile> call = ClientUtils.profileService.getById(AuthManager.getUserEmail());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    System.out.println(response.body());
                    profile = response.body();
                    loadUserProfileInfo();
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }


        });
    }
}