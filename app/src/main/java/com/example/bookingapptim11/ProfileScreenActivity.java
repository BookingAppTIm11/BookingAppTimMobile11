package com.example.bookingapptim11;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileScreenActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private EditText emailInput, firstNameInput, lastNameInput, addressInput, phoneNumberInput, passwordInput;
    private CheckBox showPasswordCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        profileImageView = findViewById(R.id.profileImage);
        emailInput = findViewById(R.id.emailInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        addressInput = findViewById(R.id.addressInput);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        passwordInput = findViewById(R.id.passwordInput);
        showPasswordCheckbox = findViewById(R.id.showPasswordCheckbox);


        Button saveButton = findViewById(R.id.saveButton);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);
        }
    }

    private void saveChanges() {
        // Logic needs to be added with backend

        Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
    }


}