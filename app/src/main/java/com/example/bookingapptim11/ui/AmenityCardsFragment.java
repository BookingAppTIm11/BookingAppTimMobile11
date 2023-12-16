package com.example.bookingapptim11.ui;


import static com.example.bookingapptim11.clients.ClientUtils.accommodationService;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapptim11.AccommodationDetailsActivity;
import com.example.bookingapptim11.R;
import com.example.bookingapptim11.adapters.AmenityCardAdapter;
import com.example.bookingapptim11.models.Accommodation;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmenityCardsFragment extends Fragment {
    List<AccommodationDetailsDTO> accommodationList;
    AmenityCardAdapter amenityCardAdapter;
    RecyclerView recyclerView;
    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;

    public interface OnItemClickListener {
        void onAmenityClick(Accommodation accommodation);
    }

    private OnItemClickListener mListener;

    // Method to set click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_amenity_cards, container, false);
        RecyclerView recyclerView1 = root.findViewById(R.id.amenity_cards_rec);


        recyclerView = root.findViewById(R.id.amenity_cards_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        accommodationList = new ArrayList<>();
        Call<ArrayList<AccommodationDetailsDTO>> call = accommodationService.getAccommodations();
        call.enqueue(new Callback<ArrayList<AccommodationDetailsDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDetailsDTO>> call, Response<ArrayList<AccommodationDetailsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accommodationList = response.body();
                    Toast.makeText(getContext(), "Successfully loaded accommodations! ", Toast.LENGTH_LONG).show();
                    amenityCardAdapter = new AmenityCardAdapter(getActivity(), accommodationList);

                    recyclerView.setAdapter(amenityCardAdapter);

                    amenityCardAdapter.setOnItemClickListener(new AmenityCardAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//                if (mListener != null) {
//                    Accommodation clickedAccommodation = accommodationList.get(position);
//                    mListener.onAmenityClick(clickedAccommodation); // Propagate click event to activity
//                }
                            AccommodationDetailsDTO clickedAccommodation = accommodationList.get(position);

                            Intent intent = new Intent(getActivity(), AccommodationDetailsActivity.class);
                            intent.putExtra("accommodation", clickedAccommodation);
                            startActivity(intent);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.nav_host_fragment_content_navigation, new AmenityDetailsFragment(clickedAccommodation))
//                        .addToBackStack("name")
//                        .commit();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationDetailsDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Error loading accommodations! ", Toast.LENGTH_LONG).show();
            }
        });


        checkInDateEditText = root.findViewById(R.id.checkInTextDate);
        checkInDateEditText.setOnClickListener(v -> showDatePicker(checkInDateEditText));
        checkOutDateEditText = root.findViewById(R.id.checkOutTextDate);
        checkOutDateEditText.setOnClickListener(v -> showDatePicker(checkOutDateEditText));


        return root;
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
                    dateEditText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        datePickerDialog.show();

    }
}