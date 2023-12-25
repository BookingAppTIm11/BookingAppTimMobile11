package adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.bookingapptim11.models.AccommodationDetailsDTO;

public class OwnersAccommodationsAdapter extends RecyclerView.Adapter<OwnersAccommodationsAdapter.ViewHolder>{

    private List<AccommodationDetailsDTO> data;
    Context context;

    public OwnersAccommodationsAdapter(Context context, List<AccommodationDetailsDTO> data) {
        this.data = data;
        this.context = context;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    @NonNull
    @Override
    public OwnersAccommodationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_owners_accommodations_item, parent, false);
        return new OwnersAccommodationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(String.valueOf(data.get(position).getName()));
        holder.locationTextView.setText(String.valueOf(data.get(position).getLocation()));
        holder.statusTextView.setText(String.valueOf(data.get(position).getStatus()));
        List<String> photos = data.get(position).getPhotos();
        if(!photos.isEmpty())
        {
            holder.setImageFromPath(photos.get(0));
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccommodationDetailsDTO selectedAccommodation = data.get(holder.getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedAccommodation", selectedAccommodation);
                Navigation.findNavController(view).navigate(R.id.update_accommodations, bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, locationTextView, statusTextView;
        Button editButton, deleteButton;
        ImageView accommodationImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.accommodationNameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            accommodationImageView = itemView.findViewById(R.id.accommodationImageView);
        }

        public void setImageFromPath(String imagePath) {
            Picasso.get().load("http://10.0.2.2:8083/pictures/"+imagePath).into(accommodationImageView);
        }

    }

}
