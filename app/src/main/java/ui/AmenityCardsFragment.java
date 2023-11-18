package ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bookingapptim11.R;

import java.util.ArrayList;
import java.util.List;

import adapters.AmenityCardAdapter;
import models.Amenity;

public class AmenityCardsFragment extends Fragment {
    List<Amenity> amenityList;
    AmenityCardAdapter amenityCardAdapter;
    RecyclerView recyclerView;

    public interface OnItemClickListener {
        void onAmenityClick(int position);
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
        amenityList = new ArrayList<>();

        amenityList.add(new Amenity("Swimming Pool", "Resort A", 4.5, 10.0, 50));
        amenityList.add(new Amenity("Gym", "Hotel B", 4.2, 5.0, 30));
        amenityList.add(new Amenity("Spa", "Resort C", 4.7, 20.0, 20));
        amenityList.add(new Amenity("Restaurant", "Hotel A", 4.0, 15.0, 100));
        amenityList.add(new Amenity("Conference Room", "Resort B", 4.3, 30.0, 80));
        amenityList.add(new Amenity("Bar", "Hotel C", 4.6, 8.0, 40));

        amenityCardAdapter = new AmenityCardAdapter(getActivity(),amenityList);

        recyclerView.setAdapter(amenityCardAdapter);

//        amenityList.add(new Amenity("Swimming Pool", "Resort A", 4.5, 10.0, 50));
//        amenityCardAdapter.notifyDataSetChanged();
//        amenityList.add(new Amenity("Gym", "Hotel B", 4.2, 5.0, 30));
//        amenityCardAdapter.notifyDataSetChanged();
//        amenityList.add(new Amenity("Spa", "Resort C", 4.7, 20.0, 20));
//        amenityCardAdapter.notifyDataSetChanged();
//        amenityList.add(new Amenity("Restaurant", "Hotel A", 4.0, 15.0, 100));
//        amenityCardAdapter.notifyDataSetChanged();
//        amenityList.add(new Amenity("Conference Room", "Resort B", 4.3, 30.0, 80));
//        amenityCardAdapter.notifyDataSetChanged();
//        amenityList.add(new Amenity("Bar", "Hotel C", 4.6, 8.0, 40));
//        amenityCardAdapter.notifyDataSetChanged();
        amenityCardAdapter.setOnItemClickListener(new AmenityCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mListener != null) {
                    mListener.onAmenityClick(position); // Propagate click event to activity
                }
            }
        });
        return root;
    }
}