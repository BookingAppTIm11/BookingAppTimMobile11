package com.example.bookingapptim11;

import static clients.ClientUtils.accommodationCreationService;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import clients.ClientUtils;

import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.AccommodationStatus;
import com.example.bookingapptim11.models.AccommodationType;
import com.example.bookingapptim11.models.Amenity;
import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.Price;
import com.example.bookingapptim11.models.PriceType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationUpdateFragment extends Fragment {

    private TextView name;
    private TextView description;
    private TextView location;
    private TextView minGuests;
    private TextView maxGuests;
    private TextView defaultPrice;
    private RadioButton perNight;
    private RadioButton perUnit;
    private TextView cancelationDays;
    private Spinner accommodationType;
    private Spinner amenitySpinner;
    private EditText availabilityStart;
    private EditText availabilityEnd;
    private EditText pricesStart;
    private EditText pricesEnd;
    private TextView price;

    private Button addAmenity;
    private Button clearAmenities;
    private Button addAvailability;
    private Button clearAvailabilites;
    private Button addPriceBtn;
    private Button clearPrices;
    private Button updateAccommodationBtn;

    private TableLayout amenitiesTable;
    private TableLayout availabilityTable;

    private TableLayout pricesTable;
    private List<Amenity> allAmenities = new ArrayList<>();
    private List<Amenity> selectedAmenities = new ArrayList<>();
    private List<Price> prices = new ArrayList<>();
    private List<Availability> availabilities = new ArrayList<>();
    private List<String> uploadedPictures = new ArrayList<>();

    private Amenity selectedAmenity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_update, container, false);

        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        location = view.findViewById(R.id.location);
        minGuests = view.findViewById(R.id.minGuests);
        maxGuests = view.findViewById(R.id.maxGuests);
        defaultPrice = view.findViewById(R.id.defaultPrice);
        perNight = view.findViewById(R.id.perNight);
        perUnit = view.findViewById(R.id.perUnit);
        cancelationDays = view.findViewById(R.id.cancelationDays);
        accommodationType = view.findViewById(R.id.accommodationType);
        amenitySpinner = view.findViewById(R.id.amenities);
        availabilityStart = view.findViewById(R.id.availabilityStart);
        availabilityEnd = view.findViewById(R.id.availabilityEnd);
        pricesStart = view.findViewById(R.id.pricesStart);
        pricesEnd = view.findViewById(R.id.pricesEnd);
        price = view.findViewById(R.id.price);

        addAmenity = view.findViewById(R.id.addAmenity);
        clearAmenities = view.findViewById(R.id.clearAmenities);
        addAvailability = view.findViewById(R.id.addAvailability);
        clearAvailabilites = view.findViewById(R.id.clearAvailabilities);
        addPriceBtn = view.findViewById(R.id.addPrice);
        clearPrices = view.findViewById(R.id.clearPrices);
        updateAccommodationBtn = view.findViewById(R.id.updateAccommodation);


        amenitiesTable = view.findViewById(R.id.amenitiesTable);
        availabilityTable = view.findViewById(R.id.availabilityTable);
        pricesTable = view.findViewById(R.id.priceTable);

        availabilityStart.setOnClickListener(v -> showDatePicker(availabilityStart));
        availabilityEnd.setOnClickListener(v -> showDatePicker(availabilityEnd));
        pricesStart.setOnClickListener(v -> showDatePicker(pricesStart));
        pricesEnd.setOnClickListener(v -> showDatePicker(pricesEnd));

        accommodationType.setAdapter(loadAccommodationTypes());
        //loadAmenities();

        Bundle args = getArguments();
        if (args != null) {
            AccommodationDetailsDTO selectedAccommodation = args.getParcelable("selectedAccommodation");
            fillForm(selectedAccommodation.getId());
            loadAvailabilities(selectedAccommodation.getId());
            loadAvailabilityToTable();
        }


        amenitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAmenity = findAmenityById(parent.getItemAtPosition(position).toString().split(". ")[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addAmenity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAmenities.add(selectedAmenity);
                addAmenityToTable(amenitiesTable);
            }
        });

        clearAmenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAmenities();
            }
        });

        addAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       //         addAvailabilityToList();
            }
        });

        addAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           addAvailabilityToList();
            }
        });

        clearAvailabilites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //          clearAvailability();
            }
        });

        addPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        addPriceToList();
            }
        });

        clearPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     clearPrice();
            }
        });

        updateAccommodationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateAccommodation();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;
    }

    public void updateAccommodation(){
        Bundle args = getArguments();
        if (args != null) {
            AccommodationDetailsDTO selectedAccommodation = args.getParcelable("selectedAccommodation");
            selectedAccommodation.setName(name.getText().toString());
            selectedAccommodation.setDescription(description.getText().toString());
            selectedAccommodation.setLocation(location.getText().toString());
            selectedAccommodation.setMinGuests(Integer.parseInt(minGuests.getText().toString()));
            selectedAccommodation.setMaxGuests(Integer.parseInt(maxGuests.getText().toString()));
            selectedAccommodation.setDefaultPrice(Double.parseDouble(defaultPrice.getText().toString()));
            selectedAccommodation.setStatus(AccommodationStatus.Pending);

            Call<AccommodationDetailsDTO> call = ClientUtils.accommodationService.updateAccommodation(selectedAccommodation.getId(), selectedAccommodation);
            call.enqueue(new Callback<AccommodationDetailsDTO>() {
                @Override
                public void onResponse(Call<AccommodationDetailsDTO> call, Response<AccommodationDetailsDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        Toast.makeText(getContext(), "Successfully updated accommodations! ", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<AccommodationDetailsDTO> call, Throwable t) {
                    Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void fillForm(Long id){
        Call<AccommodationDetailsDTO> call = ClientUtils.accommodationService.getAccommodation(id);
        call.enqueue(new Callback<AccommodationDetailsDTO>() {
            @Override
            public void onResponse(Call<AccommodationDetailsDTO> call, Response<AccommodationDetailsDTO> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(getContext(), "Successfully loaded accommodations! ", Toast.LENGTH_LONG).show();

                    name.setText(response.body().getName());
                    description.setText(response.body().getDescription());
                    location.setText(response.body().getLocation());
                    minGuests.setText(String.valueOf(response.body().getMinGuests()));
                    maxGuests.setText(String.valueOf(response.body().getMaxGuests()));
                    defaultPrice.setText(String.valueOf(response.body().getDefaultPrice()));

                } else {
                    Toast.makeText(getContext(), "No accommodations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AccommodationDetailsDTO> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadAvailabilities(Long id){
        Call<List<Availability>> call = accommodationCreationService.getAvailabilitiesByAccommodationId(id);

        call.enqueue(new Callback<List<Availability>>() {
            @Override
            public void onResponse(Call<List<Availability>> call, Response<List<Availability>> response) {
                if (response.isSuccessful()) {
                    availabilities = response.body();

                } else {
                    Toast.makeText(getContext(), "Failed to load availabilities", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Availability>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load availabilities call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addAvailabilityToTable(TableLayout table,Availability availability){

        TableRow newRow = new TableRow(getContext());
        table.addView(newRow);

        TextView textView1 = new TextView(getContext());
        textView1.setText(availability.getTimeSlot().getStartDate().toString());
        textView1.setPadding(8,8,8,8);
        textView1.setBackgroundResource(R.drawable.table_border);

        TextView textView2 = new TextView(getContext());
        textView2.setText(availability.getTimeSlot().getEndDate().toString());
        textView2.setPadding(8,8,8,8);
        textView2.setBackgroundResource(R.drawable.table_border);

        newRow.addView(textView1);
        newRow.addView(textView2);


    }

    public void loadAvailabilityToTable(){
        for(Availability a: availabilities){
            addAvailabilityToTable(availabilityTable, a);
        }
    }


    private PriceType checkPriceType(RadioButton perUnit, RadioButton perNight) throws Exception{
        if (perUnit.isChecked() && !perNight.isChecked()) {
            return PriceType.PerNight;
        } else if (!perNight.isChecked() && perUnit.isChecked()) {
            return PriceType.PerGuest;
        } else if (perUnit.isChecked() && perNight.isChecked()) {
            throw new Exception("Both checkboxes cannot be checked at the same time");
        } else {
            throw new Exception("At least one checkbox should be checked");
        }
    }

    private ArrayAdapter<String> loadAccommodationTypes(){

        List<String> itemList = new ArrayList<>();
        AccommodationType[] accommodationTypes = AccommodationType.values();
        for (AccommodationType type : accommodationTypes) {
            itemList.add(type.name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    private ArrayAdapter<String> loadAmenitySpinner(List<Amenity> amenities){

        List<String> itemList = new ArrayList<>();
        for (Amenity amenity : amenities) {
            itemList.add(amenity.getId() + ". " + amenity.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    /*private void loadAmenities(){

        Call<List<Amenity>> call = accommodationCreationService.getAllAmenities();

        call.enqueue(new Callback<List<Amenity>>() {
            @Override
            public void onResponse(Call<List<Amenity>> call, Response<List<Amenity>> response) {
                if (response.isSuccessful()) {
                    allAmenities = response.body();
                    amenitySpinner.setAdapter(loadAmenitySpinner(allAmenities));
                } else {
                    Toast.makeText(getContext(), "Failed to load amenities", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Amenity>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load amenities call", Toast.LENGTH_SHORT).show();
            }
        });


    }*/

    private void showDatePicker(EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    String selectedDate = (selectedMonth + 1) + "/" + selectedDayOfMonth + "/" + selectedYear;
                    dateEditText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        datePickerDialog.show();

    }

    private Amenity findAmenityById(String id){
        for (Amenity amenity: allAmenities) {
            if(amenity.getId().toString().equals(id)){
                return amenity;
            }
        }
        return null;
    }

    private void addAmenityToTable(TableLayout table){

        TableRow newRow = new TableRow(getContext());
        table.addView(newRow);

        TextView textView1 = new TextView(getContext());
        textView1.setText(selectedAmenity.getId().toString());
        textView1.setPadding(8,8,8,8);
        //textView1.setBackgroundResource(R.drawable.table_border);

        TextView textView2 = new TextView(getContext());
        textView2.setText(selectedAmenity.getName());
        textView2.setPadding(8,8,8,8);
        //textView2.setBackgroundResource(R.drawable.table_border);

        newRow.addView(textView1);
        newRow.addView(textView2);

    }

    public void clearAmenities(){
        allAmenities.clear();
        int childCount = amenitiesTable.getChildCount();
        if (childCount > 1) {
            amenitiesTable.removeViews(1, childCount - 1);
        }
    }



}