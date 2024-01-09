package com.example.bookingapptim11.ui.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment implements LocationListener, OnMapReadyCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private LocationManager locationManager;
    private String provider;
    private SupportMapFragment mMapFragment;
    private AlertDialog dialog;
    private Marker home;
    private GoogleMap map;

    private String accommodationAddress;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    /**
     * Prilikom kreidanja fragmenta preuzimamo sistemski servis za rad sa lokacijama
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        createMapFragmentAndInflate();

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("LOCATION", String.valueOf(gps));
        Log.i("LOCATION", String.valueOf(wifi));

        if (!gps && !wifi) {
            Log.i("LOCATION", "Nije dopustena lokacija");
            //dinamička prava pristupa
            showLocationDialog();
        } else {
            if (checkLocationPermission()) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                    Log.i("LOCATION", "ACCESS_FINE_LOCATION");
                    Toast.makeText(getContext(), "ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();

                }else if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                    Log.i("LOCATION", "ACCESS_COARSE_LOCATION");
                    Toast.makeText(getContext(), "ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accommodationAddress = getArguments().getString("ADDRESS");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /**
     * Svaki put kada uredjaj dobije novu informaciju o lokaciji ova metoda se poziva
     * i prosledjuje joj se nova informacija o kordinatama
     * */
    @Override
    public void onLocationChanged(@NonNull Location location) {
//        Toast.makeText(getActivity(), "NEW LOCATION", Toast.LENGTH_SHORT).show();
//        if (map != null) {
//            addMarker(location);
//        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        Location location = null;

        if (provider == null) {
            Log.i("LOCATION", "Provider");
            showLocationDialog();
        }else {
            if (checkLocationPermission()) {
                Log.i("ASD", "str" + provider);

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    location = locationManager.getLastKnownLocation(provider);
                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    location = locationManager.getLastKnownLocation(provider);
                }
            }
        }

        //ako zelimo da rucno postavljamo markere to radimo
        //dodavajuci click listener
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addMarker(new MarkerOptions()
                        .title("YOUR_POSITON")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(latLng));
                home.setFlat(true);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng).zoom(14).build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        //ako zelmo da reagujemo na klik markera koristimo marker click listener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i("LOCATION", "Marker " + marker.getTitle() );
                Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //ako je potrebno da reagujemo na pomeranje markera koristimo marker drag listener
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Toast.makeText(getActivity(), "Drag started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Toast.makeText(getActivity(), "Dragging", Toast.LENGTH_SHORT).show();
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(getActivity(), "Drag ended", Toast.LENGTH_SHORT).show();
            }
        });

        if (location != null) {
            addMarker(location);
        }

        LatLng hotelCentar = new LatLng(45.254505,19.841919);
        map.addMarker(new MarkerOptions().position(hotelCentar).title("Marker in Hotel Centar"));

        LatLng Camelot = new LatLng(45.2490748462,19.8431110382);
        map.addMarker(new MarkerOptions().position(Camelot).title("Marker in Camelot"));


        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(hotelCentar, 6));

        searchAndPinLocation();
    }

    private void addMarker(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

        if (home != null) {
            home.remove();
        }

        home = map.addMarker(new MarkerOptions()
                .title(accommodationAddress)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .position(loc));
        home.setFlat(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loc).zoom(14).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * Kada zelmo da dobijamo informacije o lokaciji potrebno je da specificiramo
     * po kom kriterijumu zelimo da dobijamo informacije GSP, MOBILNO(WIFI, MObilni internet), GPS+MOBILNO
     * **/
    private void createMapFragmentAndInflate() {
        //specificiramo krijterijum da dobijamo informacije sa svih izvora
        //ako korisnik to dopusti
        Criteria criteria = new Criteria();

        //sistemskom servisu prosledjujemo taj kriterijum da bi
        //mogli da dobijamo informacje sa tog izvora
        provider = locationManager.getBestProvider(criteria, true);

        //kreiramo novu instancu map fragmenta
        mMapFragment = SupportMapFragment.newInstance();

        //i vrsimo zamenu trenutnog prikaza sa prikazom mape
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map_container, mMapFragment).commit();

        //pozivamo ucitavnje mape.
        //VODITI RACUNA OVO JE ASINHRONA OPERACIJA
        //LOKACIJE MOGU DA SE DOBIJU PRE MAPE I OBRATNO
        mMapFragment.getMapAsync(this);
    }

    private void showLocationDialog() {
        if (dialog == null) {
            dialog = new LocationDialog(getActivity()).prepareDialog();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        dialog.show();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow user location")
                        .setMessage("To continue working we need your locations....Allow now?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                }

            } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 0, 0, this);
                }

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    private void searchAndPinLocation() {
        LatLng searchLocation = getLatLngFromAddress(accommodationAddress);

        if (searchLocation != null && map != null) {
            // Clear previous markers
            map.clear();

            // Add a marker for the searched location
            map.addMarker(new MarkerOptions()
                    .position(searchLocation)
                    .title(accommodationAddress)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            // Move camera to the searched location
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(searchLocation, 15));
        } else {
            Toast.makeText(getActivity(), "Location not found", Toast.LENGTH_SHORT).show();
        }
    }

    private LatLng getLatLngFromAddress(String addressString) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            // Get a maximum of 1 address that matches the addressString
            List<Address> addressList = geocoder.getFromLocationName(addressString, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}