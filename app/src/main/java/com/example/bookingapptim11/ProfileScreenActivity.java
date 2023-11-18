package com.example.bookingapptim11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProfileScreenActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText firstNameInput, lastNameInput, addressInput, phoneNumberInput, passwordInput;
    private CheckBox showPasswordCheckbox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
    }
}