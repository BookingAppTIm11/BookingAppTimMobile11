package com.example.bookingapptim11.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;

import java.io.File;
import java.util.List;

import com.example.bookingapptim11.models.Accommodation;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.squareup.picasso.Picasso;

public class AmenityCardAdapter  extends RecyclerView.Adapter<AmenityCardAdapter.ViewHolder> {

    private OnItemClickListener mListener;

    // Interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    Context context;
    List<AccommodationDetailsDTO> list;

    public AmenityCardAdapter(Context context, List<AccommodationDetailsDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AmenityCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.amenity_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AmenityCardAdapter.ViewHolder holder, int position) {
        holder.amenityMinCapacity.setText(String.valueOf(list.get(position).getMinGuests()));
        holder.amenityMaxCapacity.setText(String.valueOf(list.get(position).getMaxGuests()));
        holder.amenityName.setText(list.get(position).getName());
        holder.amenityLocation.setText(list.get(position).getLocation());
        holder.amenityPrice.setText(String.valueOf(list.get(position).getDefaultPrice().doubleValue()));
        holder.amenityRating.setText(String.valueOf(4.5));
        holder.accommodationId.setText(String.valueOf(list.get(position).getId()));
        List<String> photos = list.get(position).getPhotos();
        if(!photos.isEmpty())
        {
            holder.setImageFromPath(photos.get(0));
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amenityMaxCapacity, amenityMinCapacity, amenityName, amenityPrice, amenityRating, amenityLocation, accommodationId;
        ImageView amenityImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amenityName = itemView.findViewById(R.id.nameTextView);
            amenityLocation = itemView.findViewById(R.id.locationTextView);
            amenityPrice = itemView.findViewById(R.id.priceTextView);
            amenityRating = itemView.findViewById(R.id.ratingTextView);
            amenityMaxCapacity = itemView.findViewById(R.id.maxCapacityTxt);
            amenityMinCapacity = itemView.findViewById(R.id.minCapacityTxt);

            accommodationId = itemView.findViewById(R.id.accommodationId);
            amenityImageView = itemView.findViewById(R.id.amenityImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void setImageFromPath(String imagePath) {
            Picasso.get().load(new File(imagePath)).into(amenityImageView);
        }

    }

}
