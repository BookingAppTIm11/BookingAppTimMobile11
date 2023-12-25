package ui;

import static clients.ClientUtils.accommodationService;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import com.example.bookingapptim11.NavigationActivity;
import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.ReservationDTO;
import com.example.bookingapptim11.models.ReservationForShowDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.bookingapptim11.models.Accommodation;



public class AmenityDetailsFragment extends Fragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private GoogleMap googleMap;
    AccommodationDetailsDTO accommodation;
    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;
    private EditText guestsEditText;
    Button bookButton;
    public AmenityDetailsFragment(AccommodationDetailsDTO accommodation){
        this.accommodation = accommodation;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_amenity_details, container, false);


        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMaps);
        mapFragment.getMapAsync(this);

        TextView accommodationName = root.findViewById(R.id.accommodationName);
        TextView locationTextView = root.findViewById(R.id.locationTextView);
        TextView priceTextView = root.findViewById(R.id.priceTextView);
        TextView accommodationCapacityTextView = root.findViewById(R.id.accommodationCapacityTextView);
        TextView ratingTextView = root.findViewById(R.id.ratingTextView);
        ImageSlider imageSlider = root.findViewById(R.id.imageSlider);
        // ... find other views similarly

        // Set values from the Accommodation object to the views
        accommodationName.setText(accommodation.getName());
        locationTextView.setText(accommodation.getLocation());
        priceTextView.setText("$" + String.valueOf(accommodation.getDefaultPrice()));
        accommodationCapacityTextView.setText(String.valueOf(accommodation.getMinGuests() + "-" + accommodation.getMaxGuests()));
        ratingTextView.setText(String.valueOf("4.8"+"/5.0"));
        //searchInMap(accommodation.getLocation());

        loadImagesToSlider(imageSlider);


        
        
        ImageButton homeButton = root.findViewById(R.id.homeAccommodationDetailsImageButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });
        
        reservationDialog(root);
        
        return  root;
    }

    private void reservationDialog(View root) {

        Call<ArrayList<Availability>> call = accommodationService.getAccommodationAvailability(accommodation.getId());
        checkInDateEditText = root.findViewById(R.id.checkInTextDate2);
        checkOutDateEditText = root.findViewById(R.id.checkOutTextDate2);
        guestsEditText = root.findViewById(R.id.guestsNumber2);
        bookButton = root.findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your action on button click
                // For example, show a toast message
                bookReservation();
                //Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        call.enqueue(new Callback<ArrayList<Availability>>() {
            @Override
            public void onResponse(Call<ArrayList<Availability>> call, Response<ArrayList<Availability>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Availability> availabilities = response.body();

                    checkInDateEditText.setOnClickListener(v -> showDatePicker(checkInDateEditText, availabilities));
                    checkOutDateEditText.setOnClickListener(v -> showDatePicker(checkOutDateEditText, availabilities));



                } else {

                    Toast.makeText(getContext(),response.errorBody().toString(), Toast.LENGTH_LONG);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<Availability>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bookReservation() {
        if(!validateInputs()) return;
        String checkInDateStr = checkInDateEditText.getText().toString();
        String checkOutDateStr = checkOutDateEditText.getText().toString();
        String guestsStr = guestsEditText.getText().toString();
        createReservation(new ReservationDTO(0,accommodation.getId(),"ognjen_guest@gmail.com",checkInDateStr,checkOutDateStr,Integer.parseInt(guestsStr)));
    }


    public void createReservation(ReservationDTO reservationDTO) {
        // Assuming retrofitService is your Retrofit API service interface
        Call<ReservationForShowDTO> call = accommodationService.createReservation(reservationDTO);

        call.enqueue(new Callback<ReservationForShowDTO>() {
            @Override
            public void onResponse(Call<ReservationForShowDTO> call, Response<ReservationForShowDTO> response) {
                if (response.isSuccessful()) {
                    // Reservation created successfully
                    ReservationForShowDTO createdReservation = response.body();

                    Toast.makeText(getContext(), "Price: $"+ createdReservation.getPrice() + ", id: " + createdReservation.getId(), Toast.LENGTH_SHORT).show();

                    // Handle the created reservation data
                } else {
                    // Reservation creation failed
                    // Handle the error (e.g., display a message)
                    Toast.makeText(getContext(), "Failed to create reservation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationForShowDTO> call, Throwable t) {
                // Reservation creation request failed
                // Handle the failure (e.g., display a message)
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateInputs() {
        String checkInDateStr = checkInDateEditText.getText().toString();
        String checkOutDateStr = checkOutDateEditText.getText().toString();
        String guestsStr = guestsEditText.getText().toString();

        // Check if any of the fields are empty
        if (checkInDateStr.isEmpty() || checkOutDateStr.isEmpty() || guestsStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Convert strings to LocalDate objects
        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

        // Validate check out date is after check in date
        if (checkOutDate.isBefore(checkInDate)) {
            Toast.makeText(getContext(), "Check-out date should be after Check-in date", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate number of guests
        int guests = Integer.parseInt(guestsStr);
        if (guests < accommodation.getMinGuests() || guests > accommodation.getMaxGuests()) {
            Toast.makeText(getContext(), "Number of guests should be between " + accommodation.getMinGuests() + " and " + accommodation.getMaxGuests(), Toast.LENGTH_SHORT).show();
            return false;
        }

        // All validations passed
        return true;
    }
    private void loadImagesToSlider(ImageSlider imageSlider) {
        if(accommodation.getPhotos().size() > 0){
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            for(String photo: accommodation.getPhotos()){
                slideModels.add(new SlideModel("http://10.0.2.2:8083/pictures/"+photo, ScaleTypes.FIT));
            }
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location-related tasks
                // ... (e.g., start location updates)
            } else {
                // Permission denied, handle this case (e.g., show a message to the user)
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Customize the map as needed (e.g., set markers, move camera, etc.)
        // Example:
        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // Add markers, set camera position, etc.
        }
    }

    private void showDatePicker(EditText dateEditText, ArrayList<Availability> availabilities) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Set the selected date to the EditText field
                    LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                    dateEditText.setText(convertDateFormatForBackend(selectedDate));
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );

        // Prepare selectable days array to disable non-available dates
        ArrayList<Calendar> selectableDays = new ArrayList<>();
        for (Availability availability : availabilities) {
            LocalDate startDate = LocalDate.of(
                    availability.getTimeSlot().getStartDate().get(0),
                    availability.getTimeSlot().getStartDate().get(1),
                    availability.getTimeSlot().getStartDate().get(2)
            );
            LocalDate endDate = LocalDate.of(
                    availability.getTimeSlot().getEndDate().get(0),
                    availability.getTimeSlot().getEndDate().get(1),
                    availability.getTimeSlot().getEndDate().get(2)
            );

            // Iterate through available dates and add them to the selectableDays list
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                Calendar cal = Calendar.getInstance();
                cal.set(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
                selectableDays.add(cal);
                currentDate = currentDate.plusDays(1); // Increment date by one day
            }
        }

        // Convert ArrayList<Calendar> to Calendar[] for setSelectableDays method
        Calendar[] selectableDaysArray = selectableDays.toArray(new Calendar[0]);
        if(selectableDaysArray.length == 0){
            datePicker.setSelectableDays(new Calendar[]{});
        }else{
            datePicker.setSelectableDays(selectableDaysArray);

        }

        datePicker.show(getFragmentManager(), "DatePickerDialog");

    }

    public static String convertDateFormatForBackend(LocalDate localDate) {
        // Define the output date format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the LocalDate object to the desired output format
        return localDate.format(outputFormatter);
    }

    private long convertDateToMilliseconds(LocalDate localDate) {
        // Convert LocalDate to Date
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        // Convert Date to milliseconds
        return date.getTime();
    }
}