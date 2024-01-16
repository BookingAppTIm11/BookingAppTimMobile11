package com.example.bookingapptim11.fragments;

import static clients.ClientUtils.reportService;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Report;

import java.util.List;

import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GuestReportFragment extends Fragment {

    private Spinner reportOwnerSpinner;
    private EditText reportDescription;

    private Button reportOwnerButton;

    private String selectedEmail = "";

    public GuestReportFragment() {
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
        View view = inflater.inflate(R.layout.fragment_guest_report, container, false);

        reportOwnerSpinner = view.findViewById(R.id.reportOwnerSpinner);
        reportDescription = view.findViewById(R.id.reportDescription);
        reportOwnerButton = view.findViewById(R.id.reportOwnerButton);

        getOwnersForGuestReport();


        reportOwnerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEmail = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        reportOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReport();
            }
        });

        return view;
    }


    private void getOwnersForGuestReport() {
        Call<List<String>> call = reportService.getOwnersForGuestReport(AuthManager.getUserEmail());
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> owners = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, owners);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    reportOwnerSpinner.setAdapter(adapter);
                } else {
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });
    }


    public boolean validateReport(){

        if(selectedEmail.equals("")) {
            Toast.makeText(getContext(), "You need to select owner for report!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if( reportDescription.getText().toString().equals("")){
            Toast.makeText(getContext(), "You need to write a reason for report!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void clearFields(){
        Toast.makeText(getContext(), "Successfully reported " + selectedEmail, Toast.LENGTH_SHORT).show();
        reportDescription.setText("");
    }
    public void createReport(){

        if(validateReport()){

            Report report = new Report(0L,AuthManager.getUserEmail(),selectedEmail,reportDescription.getText().toString());
            Call<Report> call = reportService.createReport(report,"Bearer " + AuthManager.getToken());
            call.enqueue(new Callback<Report>() {
                @Override
                public void onResponse(Call<Report> call, Response<Report> response) {
                    if (response.isSuccessful()) {
                        clearFields();
                    } else {
                        Toast.makeText(getContext(), "You alredy reported " + selectedEmail, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Report> call, Throwable t) {
                }
            });

        }

    }

}