package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;

import java.util.List;

import models.Amenity;

public class AmenityCardAdapter  extends RecyclerView.Adapter<AmenityCardAdapter.ViewHolder> {

    Context context;
    List<Amenity> list;

    public AmenityCardAdapter(Context context, List<Amenity> list) {
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
        holder.amenityCapacity.setText(String.valueOf(list.get(position).getCapacity()));
        holder.amenityName.setText(list.get(position).getName());
        holder.amenityLocation.setText(list.get(position).getLocation());
        holder.amenityPrice.setText(String.valueOf(list.get(position).getPrice().doubleValue()));
        holder.amenityRating.setText(String.valueOf(list.get(position).getRating().doubleValue()));
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amenityCapacity, amenityName, amenityPrice, amenityRating, amenityLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amenityName = itemView.findViewById(R.id.nameTextView);
            amenityLocation = itemView.findViewById(R.id.locationTextView);
            amenityPrice = itemView.findViewById(R.id.priceTextView);
            amenityRating = itemView.findViewById(R.id.ratingTextView);
            amenityCapacity = itemView.findViewById(R.id.capacityTextView);
        }
    }

}
