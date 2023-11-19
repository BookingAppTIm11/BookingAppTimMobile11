package ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim11.R;

import java.util.ArrayList;
import java.util.List;

import adapters.AmenityCardAdapter;
import models.Accommodation;

public class AmenityCardsFragment extends Fragment {
    List<Accommodation> accommodationList;
    AmenityCardAdapter amenityCardAdapter;
    RecyclerView recyclerView;

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

        amenityCardAdapter = new AmenityCardAdapter(getActivity(), accommodationList);

        recyclerView.setAdapter(amenityCardAdapter);

        amenityCardAdapter.setOnItemClickListener(new AmenityCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mListener != null) {
                    Accommodation clickedAccommodation = accommodationList.get(position);
                    mListener.onAmenityClick(clickedAccommodation); // Propagate click event to activity
                }
            }
        });
        return root;
    }
}