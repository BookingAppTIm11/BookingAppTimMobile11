package com.example.bookingapptim11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;

import com.example.bookingapptim11.databinding.ActivityNavigationBinding;
import models.AccommodationDetailsDTO;
import com.example.bookingapptim11.interfaces.UserRoleChangeListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookingapptim11.databinding.ActivityNavigationBinding;

import login.AuthManager;
import models.Accommodation;
import ui.AmenityCardsFragment;
import ui.AmenityDetailsFragment;

public class NavigationActivity extends AppCompatActivity implements UserRoleChangeListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        navigationView.getMenu().clear();

        String userRole = AuthManager.getUserRole();
        // Inflate-ovanje odgovarajućeg menija
        if ("Admin".equals(userRole)) {
            navigationView.inflateMenu(R.menu.activity_admin_drawer);
            navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
                AuthManager.logOut(this);
                Navigation.findNavController(navigationView).navigate(R.id.nav_home);
                return true;
            });
        } else if("Owner".equals(userRole)){
            navigationView.inflateMenu(R.menu.activity_owner_drawer);
            navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
                AuthManager.logOut(this);
                Navigation.findNavController(navigationView).navigate(R.id.nav_home);
                return true;
            });
        }else if("Guest".equals(userRole)){
            navigationView.inflateMenu(R.menu.activity_guest_drawer);
            navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
                AuthManager.logOut(this);
                Navigation.findNavController(navigationView).navigate(R.id.nav_home);
                return true;
            });
        }else{
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }

        AuthManager.addListener(this);

        /*navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
            AuthManager.logOut();
            //openLogIn();
            return true;
        });*/

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.accommodation_requests, R.id.my_accommodations, R.id.update_accommodations)
                .setOpenableLayout(drawer)
                .build();

        /*if (savedInstanceState == null) {
            AmenityCardsFragment homeFragment = new AmenityCardsFragment();
            // Set the listener for item clicks in the fragment
            homeFragment.setOnItemClickListener(this);

//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment_content_navigation, homeFragment)
//                    .commit();
        }*/


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logInMenuItem = menu.findItem(R.id.action_logIn);
        if(logInMenuItem != null){
            if (AuthManager.getUserEmail() == null || !AuthManager.getUserEmail().contains("@")) {
                logInMenuItem.setVisible(true);
            } else {
                logInMenuItem.setVisible(false);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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

    @Override
    protected void onPause() {

        super.onPause();
        AuthManager.removeListener(this);
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
    public void onAmenityClick(AccommodationDetailsDTO accommodation) {}

    /*@Override
    public void onAmenityClick(Accommodation accommodation) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_navigation, new AmenityDetailsFragment(accommodation))
                .addToBackStack("name")
                .commit();
    }*/

    @Override
    public void onUserRoleChanged(String newUserRole) {
        // Reload the navigation menu when the user role changes
        reloadNavigationMenu();
        Toolbar toolbar = binding.appBarNavigation.toolbar;
        onPrepareOptionsMenu(toolbar.getMenu());
    }

    private void reloadNavigationMenu() {
        NavigationView navigationView = binding.navView;

        navigationView.getMenu().clear();
        TextView roleText = navigationView.findViewById(R.id.roleTextView);
        TextView emailText = navigationView.findViewById(R.id.emailTextView);
        emailText.setText(AuthManager.getUserEmail());
        roleText.setText(AuthManager.getUserRole());
        String userRole = AuthManager.getUserRole();
        // Inflate the appropriate menu based on the user's role
        if ("Admin".equals(userRole)) {
            navigationView.inflateMenu(R.menu.activity_admin_drawer);
            navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
                AuthManager.logOut(this);
                return true;
            });
        } else if("Owner".equals(userRole)){
            navigationView.inflateMenu(R.menu.activity_owner_drawer);
            navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
                AuthManager.logOut(this);
                return true;
            });
        }else if("Guest".equals(userRole)){
            navigationView.inflateMenu(R.menu.activity_guest_drawer);
            navigationView.getMenu().findItem(R.id.nav_logOut).setOnMenuItemClickListener(menuItem -> {
                AuthManager.logOut(this);
                return true;
            });
        }else{
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister the activity as a listener when it's destroyed
        AuthManager.removeListener(this);
        super.onDestroy();
    }
}