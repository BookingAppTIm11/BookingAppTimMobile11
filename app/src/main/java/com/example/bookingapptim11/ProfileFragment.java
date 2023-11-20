package com.example.bookingapptim11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private EditText emailInput, firstNameInput, lastNameInput, addressInput, phoneNumberInput, passwordInput;
    private CheckBox showPasswordCheckbox;

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

        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
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

        loadUserProfileInfo();

        return view;
    }

    private void loadUserProfileInfo() {
        // Logic to be added in future with backend info

        profileImageView.setImageResource(R.drawable.ic_person);
        emailInput.setText("vladimir123@gmail.com");
        firstNameInput.setText("Vladimir");
        lastNameInput.setText("Cornenki");
        addressInput.setText("Bulevar Oslobodjenja 23");
        phoneNumberInput.setText("0631234567");
        passwordInput.setText("password123");
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
        // Logic needs to be added with backend

        Toast.makeText(requireActivity(), "Profile Saved", Toast.LENGTH_SHORT).show();
    }
}