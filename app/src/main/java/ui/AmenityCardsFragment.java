package ui;

import com.example.bookingapptim11.models.AccommodationStatus;
import static clients.ClientUtils.accommodationService;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapptim11.AccommodationDetailsActivity;
import com.example.bookingapptim11.R;
import adapters.AmenityCardAdapter;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import adapters.AmenityCardAdapter;
import com.example.bookingapptim11.models.Accommodation;


public class AmenityCardsFragment extends Fragment  implements SensorEventListener {
    List<AccommodationDetailsDTO> accommodationList;
    AmenityCardAdapter amenityCardAdapter;
    RecyclerView recyclerView;
    private EditText checkInDateEditText;
    private EditText checkOutDateEditText;
    private EditText locationEditText;
    private EditText guestsEditText;
    private Button searchButton;

    private SensorManager sensorManager;
    private static final int SHAKE_THRESHOLD = 500;
    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastUpdate) > 200) {
                long diffTime = (currentTime - lastUpdate);
                lastUpdate = currentTime;

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                double acceleration = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                double tiltAngle = Math.atan2(y, Math.sqrt(x * x + z * z));

                // Convert tilt angle to degrees
                tiltAngle = Math.toDegrees(tiltAngle);

                // Check if the phone is tilted forward or backward
                if (tiltAngle < 45 && tiltAngle > -45) {

                    toggleSortingPrice();
                }


                if (acceleration > SHAKE_THRESHOLD) {
                    toggleSorting();
                }

                last_x = x;
                last_y = y;
                last_z = z;

            }
        }
    }

    Comparator<AccommodationDetailsDTO> priceComparator = new Comparator<AccommodationDetailsDTO>() {
        @Override
        public int compare(AccommodationDetailsDTO accommodation1, AccommodationDetailsDTO accommodation2) {

            return Double.compare(accommodation1.getDefaultPrice(), accommodation2.getDefaultPrice());
        }
    };
    private void toggleSortingPrice(){
        accommodationList.sort(priceComparator);
        refreshAccommodationAdapter();
    }

    private void toggleSorting() {
        accommodationList.sort(priceComparator);
        Collections.reverse(accommodationList);
        refreshAccommodationAdapter();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.i("REZ_ACCELEROMETER", String.valueOf(accuracy));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        sensorManager.unregisterListener(this);

    }



    public interface OnItemClickListener {
        void onAmenityClick(AccommodationDetailsDTO accommodation);
    }

    private OnItemClickListener mListener;

    // Method to set click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_amenity_cards, container, false);
        RecyclerView recyclerView1 = root.findViewById(R.id.amenity_cards_rec);

        recyclerView = root.findViewById(R.id.amenity_cards_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        accommodationList = new ArrayList<>();
        Call<ArrayList<AccommodationDetailsDTO>> call = accommodationService.searchAccommodations(null,null,null,null);
        call.enqueue(new Callback<ArrayList<AccommodationDetailsDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDetailsDTO>> call, Response<ArrayList<AccommodationDetailsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accommodationList = response.body();
                    Toast.makeText(getContext(), "Successfully loaded accommodations! ", Toast.LENGTH_LONG).show();
                    refreshAccommodationAdapter();
                } else {
                    Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationDetailsDTO>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




        checkInDateEditText = root.findViewById(R.id.checkInTextDate);
        checkInDateEditText.setOnClickListener(v -> showDatePicker(checkInDateEditText));
        checkOutDateEditText = root.findViewById(R.id.checkOutTextDate);
        checkOutDateEditText.setOnClickListener(v -> showDatePicker(checkOutDateEditText));


        bindSearchForm(root);

        return root;
    }

    private void refreshAccommodationAdapter() {
        amenityCardAdapter = new AmenityCardAdapter(getActivity(), accommodationList);
        recyclerView.setAdapter(amenityCardAdapter);
        filterActiveAccommodations();

        amenityCardAdapter.setOnItemClickListener(new AmenityCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                AccommodationDetailsDTO clickedAccommodation = accommodationList.get(position);
                Intent intent = new Intent(getActivity(), AccommodationDetailsActivity.class);
                intent.putExtra("accommodation", clickedAccommodation);
                startActivity(intent);
            }
        });
    }

    private void filterActiveAccommodations() {
        List<AccommodationDetailsDTO> accommodationDetailsDTOS = new ArrayList<>();
        for(AccommodationDetailsDTO accommodationDetailsDTO : accommodationList){
            if(accommodationDetailsDTO.getStatus().equals(AccommodationStatus.Active)){
                accommodationDetailsDTOS.add(accommodationDetailsDTO);
            }
        }
        accommodationList = accommodationDetailsDTOS;

    }
    private void bindSearchForm(View root) {
        checkInDateEditText = root.findViewById(R.id.checkInTextDate);
        checkOutDateEditText = root.findViewById(R.id.checkOutTextDate);
        locationEditText = root.findViewById(R.id.locationText);
        guestsEditText = root.findViewById(R.id.guestsNumber2);
        searchButton = root.findViewById(R.id.searchButton);

        // Set OnClickListener for the searchButton
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText fields

                String checkInUnformatted = checkInDateEditText.getText().toString();
                String checkOutUnformatted = checkOutDateEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String checkInDate = null;
                String checkOutDate = null;

                if (checkInUnformatted.isEmpty()) {
                    checkInUnformatted = null;
                }
                if (checkOutUnformatted.isEmpty()) {
                    checkOutUnformatted = null;
                }
                if (location.isEmpty()) {
                    location = null;
                }

                if(checkInUnformatted != null){
                    checkInDate = convertDateFormatForBackend(checkInUnformatted);
                }
                if(checkOutUnformatted != null){
                    checkOutDate = convertDateFormatForBackend(checkOutUnformatted);
                }

                int guests = 0;
                try {
                    guests = Integer.parseInt(guestsEditText.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Call a method to perform a backend operation
                performSearch(checkInDate, checkOutDate, location, guests);
            }
        });
    }

    private void performSearch(String checkInDate, String checkOutDate, String location, Integer guests) {

        Long checkInSeconds  = null;
        Long checkOutSeconds = null;
        if(checkInDate!=null && checkOutDate!=null){
            LocalDateTime checkInDateTime = LocalDate.parse(checkInDate).atStartOfDay();
            LocalDateTime checkOutDateTime = LocalDate.parse(checkOutDate).atStartOfDay();
            checkInSeconds = checkInDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
            checkOutSeconds = checkOutDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
            if(checkInSeconds > checkOutSeconds){
                checkInSeconds  = null;
                checkOutSeconds = null;
            }
        }


        Call<ArrayList<AccommodationDetailsDTO>> call = accommodationService.searchAccommodations(guests, location, checkInSeconds, checkOutSeconds);
        call.enqueue(new Callback<ArrayList<AccommodationDetailsDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDetailsDTO>> call, Response<ArrayList<AccommodationDetailsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accommodationList = response.body();
                    Toast.makeText(getContext(), "Successfully filtered accommodations! ", Toast.LENGTH_LONG).show();
                    refreshAccommodationAdapter();
                } else {
                    if(response.body() == null && response.isSuccessful()){
                        accommodationList = new ArrayList<>();
                        Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                        refreshAccommodationAdapter();
                    }else{
                        Toast.makeText(getContext(),response.errorBody().toString(), Toast.LENGTH_LONG);

                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationDetailsDTO>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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