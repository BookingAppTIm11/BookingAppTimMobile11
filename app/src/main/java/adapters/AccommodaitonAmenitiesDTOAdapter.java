package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.dto.AccommodationWithAmenitiesDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccommodaitonAmenitiesDTOAdapter extends RecyclerView.Adapter<AccommodaitonAmenitiesDTOAdapter.AccommodationViewHolder> {

    private List<AccommodationWithAmenitiesDTO> accommodations = new ArrayList<>();

    public void setAccommodations(Collection<AccommodationWithAmenitiesDTO> accommodations) {
            this.accommodations.clear();
            this.accommodations.addAll(accommodations);
            notifyDataSetChanged();
            }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_accommodation_card, parent, false);
            return new AccommodationViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        AccommodationWithAmenitiesDTO accommodation = accommodations.get(position);

            // Populate your ViewHolder views with the data from the accommodation object
        holder.accommodationNameTextView.setText("Name: "+accommodation.getName());
        holder.accommodationIdTextView.setText("ID: "+String.valueOf(accommodation.getId()));
        holder.accommodationLocationTextView.setText("Location: "+String.valueOf(accommodation.getLocation()));
            // Add more fields as needed
    }

    @Override
    public int getItemCount() {
            return accommodations.size();
            }

    static class AccommodationViewHolder extends RecyclerView.ViewHolder {
        TextView accommodationNameTextView, accommodationIdTextView, accommodationLocationTextView;  // Add more TextViews for other fields

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            accommodationNameTextView = itemView.findViewById(R.id.accommodationNameTxt);
            accommodationIdTextView = itemView.findViewById(R.id.accommodationIdTxt);
            accommodationLocationTextView = itemView.findViewById(R.id.accommodationLocationTxt);

            // R.id.textViewName is an example, replace it with your actual TextView ID
            // Initialize other TextViews here
        }
    }
}