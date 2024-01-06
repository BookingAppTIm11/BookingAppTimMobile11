package com.example.bookingapptim11.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static clients.ClientUtils.accommodationCreationService;
import com.example.bookingapptim11.R;
import com.example.bookingapptim11.accommodationCreation.AccommodationDetails;
import com.example.bookingapptim11.accommodationCreation.TimeSlot;
import com.example.bookingapptim11.models.AccommodationStatus;
import com.example.bookingapptim11.models.AccommodationType;
import com.example.bookingapptim11.models.Amenity;
import com.example.bookingapptim11.models.Availability;
import com.example.bookingapptim11.models.Price;
import com.example.bookingapptim11.models.PriceType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import login.AuthManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class AccommodationCreationFragment extends Fragment {

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
    private Button createAccommodationBtn;
    private Button uploadImagesBtn;
    private ListView uploadedPhotosList;

    private TableLayout amenitiesTable;
    private TableLayout availabilityTable;

    private TableLayout pricesTable;
    private List<Amenity> allAmenities = new ArrayList<>();
    private final List<Amenity> selectedAmenities = new ArrayList<>();
    private final List<Price> prices = new ArrayList<>();
    private final List<Availability> availabilities = new ArrayList<>();
    private final List<Uri> uploadedPictures = new ArrayList<>();
    private static final int PICK_IMAGES_REQUEST = 1;
    private Amenity selectedAmenity;


    public AccommodationCreationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.accommodation_creation, container, false);

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
        createAccommodationBtn = view.findViewById(R.id.createAccommodation);
        uploadImagesBtn = view.findViewById(R.id.uploadImages);


        amenitiesTable = view.findViewById(R.id.amenitiesTable);
        availabilityTable = view.findViewById(R.id.availabilityTable);
        pricesTable = view.findViewById(R.id.priceTable);
        uploadedPhotosList = view.findViewById(R.id.uploadedImages);

        availabilityStart.setOnClickListener(v -> showDatePicker(availabilityStart));
        availabilityEnd.setOnClickListener(v -> showDatePicker(availabilityEnd));
        pricesStart.setOnClickListener(v -> showDatePicker(pricesStart));
        pricesEnd.setOnClickListener(v -> showDatePicker(pricesEnd));

        accommodationType.setAdapter(loadAccommodationTypes());
        loadAmenities();


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
                addAvailabilityToList();
            }
        });

        addAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAvailabilityToList();
            }
        });

        clearAvailabilites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAvailability();
            }
        });

        addPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPriceToList();
            }
        });

        clearPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPrice();
            }
        });

        createAccommodationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createAccommdoation();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        uploadImagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImagesFromGallery();
            }
        });

        return view;
    }
    public AccommodationDetails getAccommodationDetails() throws Exception {


        AccommodationDetails accommodation = new AccommodationDetails(
                0L,
                AuthManager.getUserEmail(),
                name.getText().toString(),
                description.getText().toString(),
                location.getText().toString(),
                (Double.parseDouble(defaultPrice.getText().toString())),
                loadUploadedImagesNames(),
                Integer.parseInt(minGuests.getText().toString()),
                Integer.parseInt(maxGuests.getText().toString()),
                LocalDate.now().atStartOfDay(ZoneOffset.UTC).toEpochSecond(),
                accommodationType.getSelectedItem().toString(),
                PriceType.PerNight,
//                checkPriceType(perUnit,perNight),
                AccommodationStatus.Pending
        );

        return accommodation;
    }

    private void createAccommdoation() throws Exception {
        AccommodationDetails accommodation = getAccommodationDetails();
        Call<AccommodationDetails> call = accommodationCreationService.createAccommodation(accommodation);
        call.enqueue(new Callback<AccommodationDetails>() {
            @Override
            public void onResponse(Call<AccommodationDetails> call, Response<AccommodationDetails> response) {
                Toast.makeText(getContext(), "Successfully created accommodation! ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<AccommodationDetails> call, Throwable t) {
                Toast.makeText(getContext(), "Error while creating accommodation!", Toast.LENGTH_SHORT).show();
            }
        });

        addImagesToAccommodation(uploadedPictures);

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

    private ArrayAdapter<String> showUploadedImages(){

        List<String> itemList = loadUploadedImagesNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, itemList);
        return adapter;
    }

    private void updateListViewWithNewAdapter() {
        ArrayAdapter<String> newAdapter = showUploadedImages();
        Log.d("IMENA", loadUploadedImagesNames().toString());
        uploadedPhotosList.setAdapter(newAdapter);
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

    private void loadAmenities(){

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


    }

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
        textView1.setBackgroundResource(R.drawable.table_border);

        TextView textView2 = new TextView(getContext());
        textView2.setText(selectedAmenity.getName());
        textView2.setPadding(8,8,8,8);
        textView2.setBackgroundResource(R.drawable.table_border);

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

    public void addAvailabilityToList(){

        LocalDate from = LocalDate.parse(getDateFromPicker(availabilityStart.getText().toString()), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate to = LocalDate.parse(getDateFromPicker(availabilityEnd.getText().toString()), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        Availability availability = new Availability(new TimeSlot(
                from.atStartOfDay(ZoneOffset.UTC).toEpochSecond(),
                to.plusDays(1).atStartOfDay(ZoneOffset.UTC).toEpochSecond()
        ));

        availabilities.add(availability);

        addAvailabilityToTable(availabilityTable,availability);
    }

    public void addAvailabilityToTable(TableLayout table,Availability availability){

        TableRow newRow = new TableRow(getContext());
        table.addView(newRow);

        TextView textView1 = new TextView(getContext());
        String startDate = Instant.ofEpochSecond(availability.getTimeSlot().getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        textView1.setText(startDate);
        textView1.setPadding(8,8,8,8);
        textView1.setBackgroundResource(R.drawable.table_border);

        TextView textView2 = new TextView(getContext());
        String endDate = Instant.ofEpochSecond(availability.getTimeSlot().getEndDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        textView2.setText(endDate);
        textView2.setPadding(8,8,8,8);
        textView2.setBackgroundResource(R.drawable.table_border);

        newRow.addView(textView1);
        newRow.addView(textView2);

        clearAvailabilityInputs();
    }

    public void clearAvailabilityInputs(){
        availabilityStart.setText("");
        availabilityEnd.setText("");
    }

    public void clearAvailability(){
        availabilities.clear();
        int childCount = availabilityTable.getChildCount();
        if (childCount > 1) {
            availabilityTable.removeViews(1, childCount - 1);
        }
    }
    public void addPriceToList(){

        LocalDate from = LocalDate.parse(getDateFromPicker(pricesStart.getText().toString()), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate to = LocalDate.parse(getDateFromPicker(pricesEnd.getText().toString()), DateTimeFormatter.ofPattern("MM/dd/yyyy"));


        Price p = new Price(
                new TimeSlot(
                        from.atStartOfDay(ZoneOffset.UTC).toEpochSecond(),
                        to.plusDays(1).atStartOfDay(ZoneOffset.UTC).toEpochSecond()
                ),
                Double.parseDouble(price.getText().toString())
        );
        prices.add(p);
        addPricesToTable(pricesTable,p);
        clearPriceInputs();
    }

    public String getDateFromPicker(String date){

        String[] splitDates = date.split("/");
        for (int i = 0; i < 2; i++) {
            if (Integer.parseInt(splitDates[i]) < 10) {
                splitDates[i] = "0" + splitDates[i];
            }
        }
        return String.join("/", splitDates);
    }
    public void addPricesToTable(TableLayout table,Price price){

        TableRow newRow = new TableRow(getContext());
        table.addView(newRow);

        TextView from = new TextView(getContext());
        String startDate = Instant.ofEpochSecond(price.getTimeSlot().getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        from.setText(startDate);
        from.setPadding(8,8,8,8);
        from.setBackgroundResource(R.drawable.table_border);

        TextView to = new TextView(getContext());
        String endDate = Instant.ofEpochSecond(price.getTimeSlot().getEndDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        to.setText(endDate);
        to.setPadding(8,8,8,8);
        to.setBackgroundResource(R.drawable.table_border);

        TextView pr = new TextView(getContext());
        pr.setText(price.getPrice().toString());
        pr.setPadding(8,8,8,8);
        pr.setBackgroundResource(R.drawable.table_border);

        newRow.addView(from);
        newRow.addView(to);
        newRow.addView(pr);

        clearAvailabilityInputs();
    }

    public void clearPrice(){
        prices.clear();
        int childCount = pricesTable.getChildCount();
        if (childCount > 1) {
            pricesTable.removeViews(1, childCount - 1);
        }
    }

    public void clearPriceInputs(){
        pricesStart.setText("");
        pricesEnd.setText("");
        price.setText("");
    }

    private void uploadImagesFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        uploadImagesLauncher.launch(intent);
    }
    ActivityResultLauncher<Intent> uploadImagesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri selectedImageUri = clipData.getItemAt(i).getUri();
                                uploadedPictures.add(selectedImageUri);
                            }
                        } else {
                            Uri selectedImageUri = data.getData();
                            uploadedPictures.add(selectedImageUri);
                        }
                        updateListViewWithNewAdapter();
                    }
                }
            });

    private String getPathFromUri(Uri uri) {
        String path = "";
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(columnIndex);
            cursor.close();
        }
        return path;
    }

    private List<String> loadUploadedImagesNames(){
        List<String> imageNames = new ArrayList<>();
        for (Uri uri : uploadedPictures) {
            File file = new File(getPathFromUri(uri));
            imageNames.add(file.getName());
        }
        return imageNames;
    }
    private List<MultipartBody.Part> getMultipartFilesFromUri(List<Uri> uriList){

        List<MultipartBody.Part> parts = new ArrayList<>();

        for (Uri uri : uriList) {
            File file = new File(getPathFromUri(uri));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), requestBody);
            parts.add(part);
        }

        return parts;
    }

    private void addImagesToAccommodation(List<Uri> uriList) {
        List<MultipartBody.Part> parts = getMultipartFilesFromUri(uriList);

        Call<List<String>> call = accommodationCreationService.uploadPhotos(parts);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.d("Successfull","Successfully uploaded pictures");
                uploadedPictures.clear();
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("Unsuccessfull","Unsuccessfully uploaded pictures");
                uploadedPictures.clear();
            }
        });
    }
}