package com.example.bookingapptim11;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapptim11.databinding.ActivityNavigationBinding;

import models.Accommodation;
import ui.AmenityCardsFragment;
import ui.AmenityDetailsFragment;

public class NavigationActivity extends AppCompatActivity implements AmenityCardsFragment.OnItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        if (savedInstanceState == null) {
            AmenityCardsFragment homeFragment = new AmenityCardsFragment();
            // Set the listener for item clicks in the fragment
            homeFragment.setOnItemClickListener(this);

//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment_content_navigation, homeFragment)
//                    .commit();
        }


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logIn) {
            openLogIn();
            return true;
        } else if (id == R.id.nav_home){
            replaceFragment(new AmenityCardsFragment());
        } else if (id == R.id.nav_profile){
            replaceFragment(new ProfileFragment());
        }

        return super.onOptionsItemSelected(item);
    }

    private void openLogIn() {
        Intent loginIntent = new Intent(this, LoginScreenActivity.class);
        startActivity(loginIntent);
    }
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_navigation, fragment)
                .addToBackStack("name") // Optional: Add to back stack for back navigation
                .commit();
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onAmenityClick(Accommodation accommodation) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_navigation, new AmenityDetailsFragment(accommodation))
                .addToBackStack("name")
                .commit();
    }
}