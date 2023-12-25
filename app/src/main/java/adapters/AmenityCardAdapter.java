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

import com.example.bookingapptim11.models.Accommodation;

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
    List<Accommodation> list;

    public AmenityCardAdapter(Context context, List<Accommodation> list) {
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


    }

}
