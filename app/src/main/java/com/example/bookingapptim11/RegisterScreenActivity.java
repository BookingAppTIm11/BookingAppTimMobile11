package com.example.bookingapptim11;

import static clients.ClientUtils.authService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import clients.services.AuthService;
import clients.services.Validate;
import login.AuthManager;
import login.AuthResponse;
import registration.UserRegistration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreenActivity extends AppCompatActivity {

    private TextView email;
    private TextView password, confirmPassword;
    private TextView name;
    private TextView lastName;
    private TextView address;
    private TextView phone;
    private CheckBox guestChoice;
    private CheckBox ownerChoice;
    private TextView logIn;
    private ImageButton homeButton;

    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        guestChoice = findViewById(R.id.guestBox);
        ownerChoice = findViewById(R.id.ownerBox);
        logIn = findViewById(R.id.textViewLogIn);
        homeButton = findViewById(R.id.homeRegisterImageButton);
        registerButton = findViewById(R.id.registerBtn);
        confirmPassword = findViewById(R.id.confirmPassword);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToRegisterScreen();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToHomeScreen();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateUser();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    private void changeToRegisterScreen(){
        Intent intent = new Intent(RegisterScreenActivity.this, LoginScreenActivity.class);
        startActivity(intent);
    }

    private void changeToHomeScreen(){
        Intent intent = new Intent(RegisterScreenActivity.this, NavigationActivity.class);
        startActivity(intent);
    }

    private void changeToLoginScreen(){
        Intent intent = new Intent(RegisterScreenActivity.this, LoginScreenActivity.class);
        startActivity(intent);
    }
    private void validateUser() throws Exception {

        if (!Validate.isValidInputWithSpaces(name.getText().toString())) {
            Toast.makeText(RegisterScreenActivity.this, "First name should contain only letters and spaces.", Toast.LENGTH_LONG).show();
        }
        else if (!Validate.isValidInputWithSpaces(lastName.getText().toString())) {
            Toast.makeText(RegisterScreenActivity.this, "Last name should contain only letters and spaces.", Toast.LENGTH_LONG).show();
        }
        else if (!Validate.isValidEmail(email.getText().toString())) {
            Toast.makeText(RegisterScreenActivity.this, "Invalid email address.", Toast.LENGTH_LONG).show();
        }
        else if (password.getText().toString().trim().length() < 3) {
            Toast.makeText(RegisterScreenActivity.this, "Password must have more than 3 letters", Toast.LENGTH_LONG).show();
        }
        else if (!Validate.isValidAlphanumericWithSpaces(address.getText().toString())) {
            Toast.makeText(RegisterScreenActivity.this, "Address should contain only letters, numbers, and spaces.", Toast.LENGTH_LONG).show();
        }
        else if (!Validate.containsOnlyNumbers(phone.getText().toString())) {
            Toast.makeText(RegisterScreenActivity.this, "Phone number should contain only numbers.", Toast.LENGTH_LONG).show();
        }
        else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            Toast.makeText(RegisterScreenActivity.this, "Passwords must mach", Toast.LENGTH_LONG).show();
        }else{
            registerUser(getRegisteredUserData());
            Toast.makeText(RegisterScreenActivity.this,"Verification email has been sent. Click on the link to activate the account! ", Toast.LENGTH_LONG).show();
            changeToLoginScreen();
        }
      }

      public UserRegistration getRegisteredUserData() throws Exception {
          String role = checkUserRole(guestChoice,ownerChoice);
          UserRegistration registeredUser = new UserRegistration(
                  email.getText().toString().trim(),
                  password.getText().toString().trim(),
                  false,
                  name.getText().toString().trim(),
                  lastName.getText().toString().trim(),
                  address.getText().toString().trim(),
                  phone.getText().toString().trim(),
                  role);
          return registeredUser;
      }

    private String checkUserRole(CheckBox guest, CheckBox owner) throws Exception{
        if (guest.isChecked() && !owner.isChecked()) {
            return "Guest";
        } else if (!guest.isChecked() && owner.isChecked()) {
            return "Owner";
        } else if (guest.isChecked() && owner.isChecked()) {
            throw new Exception("Both checkboxes cannot be checked at the same time");
        } else {
            throw new Exception("At least one checkbox should be checked");
        }
    }
    private void registerUser(UserRegistration registeredUser) throws Exception {

        Call<UserRegistration> call = authService.register(registeredUser);
        call.enqueue(new Callback<UserRegistration>() {
            @Override
            public void onResponse(Call<UserRegistration> call, Response<UserRegistration> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserRegistration user = response.body();
                } else {
                    Toast.makeText(RegisterScreenActivity.this, "Wrong inputs! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserRegistration> call, Throwable t) {
                Toast.makeText(RegisterScreenActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }
    @Override
    protected void onRestart(){
        super.onRestart();

    }
    @Override
    protected void onResume(){
        super.onResume();

    }
}