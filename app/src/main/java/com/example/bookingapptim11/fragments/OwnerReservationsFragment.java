package com.example.bookingapptim11.fragments;

import static clients.ClientUtils.reservationService;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.OwnerReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.AccommodationRequestAdapter;
import adapters.OwnerReservationsAdapter;
import clients.ClientUtils;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerReservationsFragment extends Fragment {


    List<OwnerReservation> reservations;
    OwnerReservationsAdapter adapter;
    RecyclerView recyclerView;
    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;
    private Spinner spinner;
    private Button searchButton;

    private EditText accommodationNameEditText;

    public OwnerReservationsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_owner_reservations, container, false);

        recyclerView = root.findViewById(R.id.ownersReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        reservations = new ArrayList<>();
        Call<ArrayList<OwnerReservation>> call = reservationService.searchOwnersReservations(null,null,null,AuthManager.getUserEmail());
        call.enqueue(new Callback<ArrayList<OwnerReservation>>() {
            @Override
            public void onResponse(Call<ArrayList<OwnerReservation>> call, Response<ArrayList<OwnerReservation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (OwnerReservation reservation : response.body()) {
                        if ("Waiting".equals(reservation.getStatus().toString())) {
                            reservations.add(reservation);
                        }
                    }
                    refreshOwnerReservationsAdapter();
                } else {
                    Toast.makeText(getContext(), "No reservations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<OwnerReservation>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        setupReservationStatusSpinner(root);
        datePickerSetup(root);
        accommodationNameEditText = root.findViewById(R.id.accommodationNameEditText);
        setupSearchButton(root);

        return root;
    }

    private void datePickerSetup(View root) {
        checkInDateEditText = root.findViewById(R.id.checkInTextDate);
        checkInDateEditText.setOnClickListener(v -> showDatePicker(checkInDateEditText));
        checkOutDateEditText = root.findViewById(R.id.checkOutTextDate);
        checkOutDateEditText.setOnClickListener(v -> showDatePicker(checkOutDateEditText));
    }

    private void setupSearchButton(View root) {
        searchButton = root.findViewById(R.id.searchOwnerReservationsButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the searchOwnersReservations method here
                callSearchOwnersReservations();
            }
        });
    }

    private void setupReservationStatusSpinner(View root) {
        spinner = root.findViewById(R.id.spinner2);

        // Create an ArrayAdapter with the data
        String[] statusOptions = {"Status (default)", "Accepted", "Declined", "Waiting"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusOptions);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void callSearchOwnersReservations() {

        LocalDateTime checkInDateTime = LocalDate.parse(checkInDateEditText.getText()).atStartOfDay();
        LocalDateTime checkOutDateTime = LocalDate.parse(checkOutDateEditText.getText()).atStartOfDay();
        Long checkInSeconds = checkInDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
        Long checkOutSeconds = checkOutDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
        if(checkInSeconds > checkOutSeconds){
            checkInSeconds  = null;
            checkOutSeconds = null;
        }

        String accommodationName = String.valueOf(accommodationNameEditText.getText()); // set your value
        if(accommodationName.isEmpty()) accommodationName = null;

        // Call the searchOwnersReservations method
        Call<ArrayList<OwnerReservation>> call = reservationService.searchOwnersReservations(
                checkInSeconds,
                checkOutSeconds,
                accommodationName,
                AuthManager.getUserEmail()
        );

        call.enqueue(new Callback<ArrayList<OwnerReservation>>() {
            @Override
            public void onResponse(Call<ArrayList<OwnerReservation>> call, Response<ArrayList<OwnerReservation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reservations.clear();
                    for (OwnerReservation reservation : response.body()) {
                        if ("Waiting".equals(reservation.getStatus().toString())) {
                            reservations.add(reservation);
                        }
                    }
                    refreshOwnerReservationsAdapter();
                } else {
                    Toast.makeText(getContext(), "No reservations! ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OwnerReservation>> call, Throwable t) {
                // Handle failure
            }
        });
    }


    private void refreshOwnerReservationsAdapter() {
        adapter = new OwnerReservationsAdapter(getActivity(), reservations);
        recyclerView.setAdapter(adapter);
    }
    private void showDatePicker(EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Set the selected date to the EditText field
                    String selectedDate = (selectedMonth + 1) + "/" + selectedDayOfMonth + "/" + selectedYear;
                    dateEditText.setText(convertDateFormatForBackend(selectedDate));
                },
                year, month, dayOfMonth);

        datePickerDialog.show();

    }
    public static String convertDateFormatForBackend(String inputDate) {
        // Define the input and output date formats
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the input string to a LocalDate object
        LocalDate localDate = LocalDate.parse(inputDate, inputFormatter);

        // Format the LocalDate object to the desired output format
        return localDate.format(outputFormatter);
    }

}