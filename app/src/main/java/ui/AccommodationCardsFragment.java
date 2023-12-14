package ui;


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

import com.example.bookingapptim11.AccommodationDetailsActivity;
import com.example.bookingapptim11.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.AccommodationCardAdapter;
import models.Accommodation;

public class AccommodationCardsFragment extends Fragment {
    List<Accommodation> accommodationList;
    AccommodationCardAdapter accommodationCardAdapter;
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

        accommodationList.add(new Accommodation("Swimming Pool", "Resort A", 4.5, 10.0, 50));
        accommodationList.add(new Accommodation("Gym", "Hotel B", 4.2, 5.0, 30));
        accommodationList.add(new Accommodation("Spa", "Resort C", 4.7, 20.0, 20));
        accommodationList.add(new Accommodation("Restaurant", "Hotel A", 4.0, 15.0, 100));
        accommodationList.add(new Accommodation("Conference Room", "Resort B", 4.3, 30.0, 80));
        accommodationList.add(new Accommodation("Bar", "Hotel C", 4.6, 8.0, 40));

        accommodationCardAdapter = new AccommodationCardAdapter(getActivity(), accommodationList);

        recyclerView.setAdapter(accommodationCardAdapter);

        accommodationCardAdapter.setOnItemClickListener(new AccommodationCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                if (mListener != null) {
//                    Accommodation clickedAccommodation = accommodationList.get(position);
//                    mListener.onAmenityClick(clickedAccommodation); // Propagate click event to activity
//                }
                Accommodation clickedAccommodation = accommodationList.get(position);

                Intent intent = new Intent(getActivity(), AccommodationDetailsActivity.class);
                intent.putExtra("accommodation", clickedAccommodation);
                startActivity(intent);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.nav_host_fragment_content_navigation, new AmenityDetailsFragment(clickedAccommodation))
//                        .addToBackStack("name")
//                        .commit();
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