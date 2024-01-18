package com.example.bookingapptim11.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static clients.ClientUtils.accommodationService;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.dto.AccommodationNumberOfReservations;
import com.example.bookingapptim11.dto.AccommodationProfitDTO;
import com.example.bookingapptim11.dto.AccommodationYearlyNumberOfReservations;
import com.example.bookingapptim11.dto.AccommodationYearlyProfitDTO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import login.AuthManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticsOwnersFragment extends Fragment {
    private EditText startDateEditText;
    private EditText endDateEditText;
    private Spinner yearSpinner;

    private BarChart reservationsTimeSpanChart;
    private BarChart profitTimeSpanChart;
    private BarChart reservationsYearlyChart;
    private BarChart profitYearlyChart;

    private List<BarDataSet> dataSetList;
    private Button calculateButton;
    private Button pdfButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_statistics_owners, container, false);
        loadYearsToSpinner(root);

        setupDatePickers(root);
        setupCharts(root);

        setupCalculateButton(root);
        setupDownloadPdfButton(root);

        return root;
    }

    private void setupDownloadPdfButton(View root) {
        if(!isStoragePermissionGranted(getActivity())){
            return;
        }

        pdfButton = root.findViewById(R.id.getPdfButton);
        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText startTextDate = root.findViewById(R.id.startTextDate);
                EditText endTextDate = root.findViewById(R.id.endTextDate);

                String startDate = startTextDate.getText().toString().trim();
                String endDate = endTextDate.getText().toString().trim();

                if (!startDate.isEmpty() && !endDate.isEmpty() && !yearSpinner.isSelected()) {
                    LocalDateTime checkInDateTime = LocalDate.parse(convertDateFormatForBackend(startDate)).atStartOfDay();
                    LocalDateTime checkOutDateTime = LocalDate.parse(convertDateFormatForBackend(endDate)).atStartOfDay();
                    Long checkInSeconds = checkInDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
                    Long checkOutSeconds = checkOutDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
                    Integer yearSelected = Integer.valueOf(yearSpinner.getSelectedItem().toString());

                    callDownloadPdf(checkInSeconds,checkOutSeconds,yearSelected);

                } else {
                    // Show a message indicating that the fields are empty
                    Toast.makeText(getContext(),"Please enter start/end dates and year", Toast.LENGTH_SHORT).show();
                }
            }

            private void callDownloadPdf(Long checkInSeconds, Long checkOutSeconds, Integer yearSelected) {
                Call<ResponseBody> call = accommodationService.getStatisticPdf(checkInSeconds, checkOutSeconds, yearSelected, AuthManager.getUserEmail());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            try (ResponseBody responseBody = response.body()) {
                                // Create a unique file name with timestamp
                                String fileName = "Statistics_" + System.currentTimeMillis() + ".pdf";

                                // Create a file in the Downloads directory
                                File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

                                try (InputStream inputStream = response.body().byteStream();
                                     OutputStream outputStream = new FileOutputStream(futureStudioIconFile)) {

                                    byte[] fileReader = new byte[4096];
                                    long fileSize = response.body().contentLength();
                                    long fileSizeDownloaded = 0;

                                    while (true) {
                                        int read = inputStream.read(fileReader);
                                        if (read == -1) {
                                            break;
                                        }

                                        outputStream.write(fileReader, 0, read);
                                        fileSizeDownloaded += read;

                                        Log.d(TAG, "File download: " + fileSizeDownloaded + " of " + fileSize);
                                    }

                                    outputStream.flush();

                                    try {
                                        // Use FileProvider to get a content URI
                                        Uri fileUri = FileProvider.getUriForFile(getContext(), "com.example.bookingapptim11.fileprovider", futureStudioIconFile);

                                        // Create an intent to view the PDF
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(fileUri, "application/pdf");
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant read permission
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                        // Start the activity
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        Snackbar.make(getContext(),getView(), "No PDF reader found to open this file.", Snackbar.LENGTH_LONG).show();
                                    }

                                } catch (IOException e) {
                                    Log.e(TAG, "Error during file download", e);
                                }
                                // Now you can use pdfBytes as needed, e.g., save it to a file or display it
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Handle failure, such as network error
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private void setupCalculateButton(View root) {
        calculateButton = root.findViewById(R.id.getStatisticsButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get references to EditText fields
                EditText startTextDate = root.findViewById(R.id.startTextDate);
                EditText endTextDate = root.findViewById(R.id.endTextDate);

                // Get the text from EditText fields
                String startDate = startTextDate.getText().toString().trim();
                String endDate = endTextDate.getText().toString().trim();

                // Check if EditText fields are not empty
                if (!startDate.isEmpty() && !endDate.isEmpty() && !yearSpinner.isSelected()) {
                    LocalDateTime checkInDateTime = LocalDate.parse(convertDateFormatForBackend(startDate)).atStartOfDay();
                    LocalDateTime checkOutDateTime = LocalDate.parse(convertDateFormatForBackend(endDate)).atStartOfDay();
                    Long checkInSeconds = checkInDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
                    Long checkOutSeconds = checkOutDateTime.atZone(ZoneOffset.UTC).toEpochSecond();

                    Integer yearSelected = Integer.valueOf(yearSpinner.getSelectedItem().toString());

                    callNumberOfReservationsTimeSpan(checkInSeconds, checkOutSeconds);
                    callProfitTimeSpan(checkInSeconds, checkOutSeconds);
                    callNumberOfReservationsYearly(yearSelected);
                    callNumberOfProfitYearly(yearSelected);
                } else {
                    // Show a message indicating that the fields are empty
                    Toast.makeText(getContext(),"Please enter start/end dates and year", Toast.LENGTH_SHORT).show();
                }
            }

            private void callNumberOfProfitYearly(Integer yearSelected) {
                Call<Collection<AccommodationYearlyProfitDTO>> call = accommodationService.getStatisticProfitYearly(yearSelected, AuthManager.getUserEmail());

                call.enqueue(new Callback<Collection<AccommodationYearlyProfitDTO>>() {
                    @Override
                    public void onResponse(Call<Collection<AccommodationYearlyProfitDTO>> call, Response<Collection<AccommodationYearlyProfitDTO>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Collection<AccommodationYearlyProfitDTO> accommodationsNumberOfReservationsYearly = response.body();

                            BarData barData = new BarData();

                            for (AccommodationYearlyProfitDTO accommodationNumberOfReservationsYearly : accommodationsNumberOfReservationsYearly) {
                                List<BarEntry> entries = new ArrayList<>();


                                for(int i = 0; i < accommodationNumberOfReservationsYearly.getMonthlyProfits().size(); i++ ){
                                    entries.add(new BarEntry(i, accommodationNumberOfReservationsYearly.getMonthlyProfits().get(i).floatValue()));
                                }
                                BarDataSet barDataSet = new BarDataSet(entries, accommodationNumberOfReservationsYearly.getAccommodationName());

                                barDataSet.setColor(generateRandomColor());

                                barData.addDataSet(barDataSet);
                            }

                            // Set the BarData to the chart
                            profitYearlyChart.setData(barData);

                            // Customize the x-axis labels
                            XAxis xAxis = profitYearlyChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(getYearlyMonths())); // Set month names as x-axis labels
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setGranularity(1f); // Minimum axis step (interval) is 1
                            xAxis.setLabelCount(getYearlyMonths().size());

                            // Refresh the chart
                            profitYearlyChart.invalidate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Collection<AccommodationYearlyProfitDTO>> call, Throwable t) {
                        // Handle failure, such as network error
                        t.printStackTrace();
                    }
                });
            }

            private void callNumberOfReservationsYearly(Integer yearSelected) {
                Call<Collection<AccommodationYearlyNumberOfReservations>> call = accommodationService.getStatisticReservationsYearly(yearSelected, AuthManager.getUserEmail());

                call.enqueue(new Callback<Collection<AccommodationYearlyNumberOfReservations>>() {
                    @Override
                    public void onResponse(Call<Collection<AccommodationYearlyNumberOfReservations>> call, Response<Collection<AccommodationYearlyNumberOfReservations>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Collection<AccommodationYearlyNumberOfReservations> accommodationsNumberOfReservationsYearly = response.body();

                            BarData barData = new BarData();

                            for (AccommodationYearlyNumberOfReservations accommodationNumberOfReservationsYearly : accommodationsNumberOfReservationsYearly) {
                                List<BarEntry> entries = new ArrayList<>();


                                for(int i = 0; i < accommodationNumberOfReservationsYearly.getMonthlyNumberOfReservations().size(); i++ ){
                                    entries.add(new BarEntry(i, accommodationNumberOfReservationsYearly.getMonthlyNumberOfReservations().get(i).floatValue()));
                                }
                                BarDataSet barDataSet = new BarDataSet(entries, accommodationNumberOfReservationsYearly.getAccommodationName());

                                barDataSet.setColor(generateRandomColor());

                                barData.addDataSet(barDataSet);
                            }

                            // Set the BarData to the chart
                            reservationsYearlyChart.setData(barData);

                            // Customize the x-axis labels
                            XAxis xAxis = reservationsYearlyChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(getYearlyMonths())); // Set month names as x-axis labels
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setGranularity(1f); // Minimum axis step (interval) is 1
                            xAxis.setLabelCount(getYearlyMonths().size());

                            // Refresh the chart
                            reservationsYearlyChart.invalidate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Collection<AccommodationYearlyNumberOfReservations>> call, Throwable t) {
                        // Handle failure, such as network error
                        t.printStackTrace();
                    }
                });
            }

            private void callProfitTimeSpan(Long checkInSeconds, Long checkOutSeconds) {
                Call<Collection<AccommodationProfitDTO>> call = accommodationService.getStatisticProfitTimeSpan(checkInSeconds, checkOutSeconds, AuthManager.getUserEmail());

                call.enqueue(new Callback<Collection<AccommodationProfitDTO>>() {
                    @Override
                    public void onResponse(Call<Collection<AccommodationProfitDTO>> call, Response<Collection<AccommodationProfitDTO>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Collection<AccommodationProfitDTO> accommodationsNumberOfReservations = response.body();

                            BarData barData = new BarData();

                            // Counter for the group index
                            int groupIndex = 0;

                            for (AccommodationProfitDTO accommodationNumberOfReservations : accommodationsNumberOfReservations) {
                                List<BarEntry> entries = new ArrayList<>();

                                // Add a BarEntry for each group with a unique groupIndex
                                entries.add(new BarEntry(groupIndex, accommodationNumberOfReservations.getProfit().floatValue()));

                                BarDataSet barDataSet = new BarDataSet(entries, accommodationNumberOfReservations.getAccommodationName());

                                // Set a distinct color for each group
                                barDataSet.setColor(generateRandomColor());

                                // Increment the group index for the next group
                                groupIndex++;

                                // Add the BarDataSet to the BarData
                                barData.addDataSet(barDataSet);
                            }

                            // Set the BarData to the chart
                            profitTimeSpanChart.setData(barData);

                            // Customize the x-axis labels
                            XAxis xAxis = profitTimeSpanChart.getXAxis();
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setAxisMinimum(-0.5f);
                            xAxis.setGranularity(1f);
                            xAxis.setLabelCount(accommodationsNumberOfReservations.size());

                            // Refresh the chart
                            profitTimeSpanChart.invalidate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Collection<AccommodationProfitDTO>> call, Throwable t) {
                        // Handle failure, such as network error
                        t.printStackTrace();
                    }
                });
            }

            private void callNumberOfReservationsTimeSpan(Long checkInSeconds, Long checkOutSeconds) {
                Call<Collection<AccommodationNumberOfReservations>> call = accommodationService.getStatisticNumberReservationsTimeSpan(checkInSeconds, checkOutSeconds, AuthManager.getUserEmail());

                call.enqueue(new Callback<Collection<AccommodationNumberOfReservations>>() {
                    @Override
                    public void onResponse(Call<Collection<AccommodationNumberOfReservations>> call, Response<Collection<AccommodationNumberOfReservations>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Collection<AccommodationNumberOfReservations> accommodationsNumberOfReservations = response.body();

                            BarData barData = new BarData();

                            // Counter for the group index
                            int groupIndex = 0;

                            for (AccommodationNumberOfReservations accommodationNumberOfReservations : accommodationsNumberOfReservations) {
                                List<BarEntry> entries = new ArrayList<>();

                                // Add a BarEntry for each group with a unique groupIndex
                                entries.add(new BarEntry(groupIndex, accommodationNumberOfReservations.getNumberOfReservations()));

                                BarDataSet barDataSet = new BarDataSet(entries, accommodationNumberOfReservations.getAccommodationName());

                                // Set a distinct color for each group
                                barDataSet.setColor(generateRandomColor());

                                // Increment the group index for the next group
                                groupIndex++;

                                // Add the BarDataSet to the BarData
                                barData.addDataSet(barDataSet);
                            }

                            // Set the BarData to the chart
                            reservationsTimeSpanChart.setData(barData);

                            // Customize the x-axis labels
                            XAxis xAxis = reservationsTimeSpanChart.getXAxis();
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setAxisMinimum(-0.5f);
                            xAxis.setGranularity(1f);
                            xAxis.setLabelCount(accommodationsNumberOfReservations.size());

                            // Refresh the chart
                            reservationsTimeSpanChart.invalidate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Collection<AccommodationNumberOfReservations>> call, Throwable t) {
                        // Handle failure, such as network error
                        t.printStackTrace();
                    }
                });
            }
        });
    }


    private void setupCharts(View root) {
        reservationsTimeSpanChart = root.findViewById(R.id.reservationsTimeSpan);
        profitTimeSpanChart = root.findViewById(R.id.profitTimeSpan);
        reservationsYearlyChart = root.findViewById(R.id.reservationsYearly);
        profitYearlyChart = root.findViewById(R.id.profitYearly);

        // Assuming you have a method to set data to the charts
        setupChartData();

        // You can customize other settings for the charts as needed
    }

    private void setupChartData() {
        // Example: Adding some months to yearly charts
        List<String> yearlyMonths = getYearlyMonths();


        // Assuming you have a method to set data to the yearly charts
        setYearlyChartData(reservationsYearlyChart, yearlyMonths);
        setYearlyChartData(profitYearlyChart, yearlyMonths);
    }

    @NonNull
    private static List<String> getYearlyMonths() {
        List<String> yearlyMonths = new ArrayList<>();

        yearlyMonths.add("Jan");
        yearlyMonths.add("Feb");
        yearlyMonths.add("Mar");
        yearlyMonths.add("Apr");
        yearlyMonths.add("May");
        yearlyMonths.add("Jun");
        yearlyMonths.add("Jul");
        yearlyMonths.add("Aug");
        yearlyMonths.add("Sep");
        yearlyMonths.add("Oct");
        yearlyMonths.add("Nov");
        yearlyMonths.add("Dec");
        return yearlyMonths;
    }

    private void setYearlyChartData(BarChart chart, List<String> months) {
        // Create a list to hold BarDataSet for each apartment
        List<BarDataSet> dataSetList = new ArrayList<>();

        // Create BarData to hold all BarDataSets
        BarData barData = new BarData();

        // Number of apartments
        int numApartments = 3;

        // Iterate over each apartment
        for (int i = 1; i <= numApartments; i++) {
            List<Integer> data = generateRandomData(months.size());

            List<BarEntry> entries = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                entries.add(new BarEntry(j, data.get(j)));
            }

            // Create a BarDataSet for each apartment
            BarDataSet dataSet = new BarDataSet(entries, "Apartment " + i);
            dataSet.setColor(generateRandomColor());
            dataSetList.add(dataSet);

            // Add the BarDataSet to the BarData
            barData.addDataSet(dataSet);
        }

        // Set the combined data to the chart
        chart.setData(barData);

        // Customize the x-axis labels
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months)); // Set month names as x-axis labels
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Minimum axis step (interval) is 1
        xAxis.setLabelCount(months.size());

        // Refresh the chart
        chart.invalidate();

    }

    // Method to generate random data for each apartment
    private List<Integer> generateRandomData(int size) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add((int) (Math.random() * 1000));
        }
        return data;
    }
    private void setupDatePickers(View root) {
        startDateEditText = root.findViewById(R.id.startTextDate);
        startDateEditText.setOnClickListener(v -> showDatePicker(startDateEditText));
        endDateEditText = root.findViewById(R.id.endTextDate);
        endDateEditText.setOnClickListener(v -> showDatePicker(endDateEditText));
    }

    private int generateRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void loadYearsToSpinner(View root) {
        yearSpinner = root.findViewById(R.id.yearPickerSpinner);

        // Create a list of years from 2016 to 2045
        List<String> yearsList = getYearsList();

        // Create an ArrayAdapter using the years list and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, yearsList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        yearSpinner.setAdapter(adapter);
    }


    private List<String> getYearsList() {
        List<String> yearsList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int year = 2016; year <= 2045; year++) {
            yearsList.add(String.valueOf(year));
        }

        return yearsList;
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


    public static boolean isStoragePermissionGranted(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) { // Fix here
            //Log.v(TAG, "Permission is granted");
            return true;
        } else {
            //Toast.makeText(getApplicationContext(), "Permission is revoked",Toast.LENGTH_SHORT).show();
            //Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
    }
}