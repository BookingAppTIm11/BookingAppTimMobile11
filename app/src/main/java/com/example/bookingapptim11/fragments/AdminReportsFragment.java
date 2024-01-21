package com.example.bookingapptim11.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Report;
import com.example.bookingapptim11.models.Review;

import java.util.ArrayList;
import java.util.List;

import adapters.AdminReportsAdapter;
import adapters.AdminReviewsAdapter;
import clients.ClientUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminReportsFragment extends Fragment {

    List<Report> reports;
    AdminReportsAdapter adapter;
    RecyclerView recyclerView;

    public AdminReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_reports, container, false);

        recyclerView = root.findViewById(R.id.adminReports);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        reports = new ArrayList<>();
        Call<ArrayList<Report>> call = ClientUtils.reportService.getReports();
        call.enqueue(new Callback<ArrayList<Report>>() {
            @Override
            public void onResponse(Call<ArrayList<Report>> call, Response<ArrayList<Report>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reports = response.body();

                    refreshAdminReportsAdapter();
                } else {
                    Toast.makeText(getContext(), "No reservations! ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                Toast.makeText(getContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    private void refreshAdminReportsAdapter() {
        adapter = new AdminReportsAdapter(getActivity(), reports);
        recyclerView.setAdapter(adapter);
    }

}