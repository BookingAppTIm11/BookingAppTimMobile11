package adapters;

// AccommodationRequestAdapter.java
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;

import java.util.List;

import clients.ClientUtils;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.AccommodationStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;

public class AccommodationRequestAdapter extends RecyclerView.Adapter<AccommodationRequestAdapter.ViewHolder> {
    private List<AccommodationDetailsDTO> data;
    Context context;


    public AccommodationRequestAdapter(Context context, List<AccommodationDetailsDTO> data) {
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
    public AccommodationRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_accommodation_requests_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameTextView.setText(String.valueOf(data.get(position).getName()));
        holder.ownerEmailTextView.setText(String.valueOf(data.get(position).getOwnerEmail()));
        holder.statusTextView.setText(String.valueOf(data.get(position).getStatus()));
        List<String> photos = data.get(position).getPhotos();
        if(!photos.isEmpty())
        {
            holder.setImageFromPath(photos.get(0));
        }
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                data.get(clickedPosition).setStatus(AccommodationStatus.Active);
                Call<AccommodationDetailsDTO> call = ClientUtils.accommodationService.updateAccommodation(data.get(clickedPosition).getId(), data.get(clickedPosition));
                call.enqueue(new Callback<AccommodationDetailsDTO>() {
                    @Override
                    public void onResponse(Call<AccommodationDetailsDTO> call, Response<AccommodationDetailsDTO> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());
                            data.remove(clickedPosition);
                            notifyItemRemoved(clickedPosition);
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<AccommodationDetailsDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });

        // Set OnClickListener for decline button
        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                data.get(clickedPosition).setStatus(AccommodationStatus.Declined);
                Call<AccommodationDetailsDTO> call = ClientUtils.accommodationService.updateAccommodation(data.get(clickedPosition).getId(), data.get(clickedPosition));
                call.enqueue(new Callback<AccommodationDetailsDTO>() {
                    @Override
                    public void onResponse(Call<AccommodationDetailsDTO> call, Response<AccommodationDetailsDTO> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());
                            data.remove(clickedPosition);
                            notifyItemRemoved(clickedPosition);
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<AccommodationDetailsDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }


                });
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ownerEmailTextView, statusTextView;
        Button acceptButton, declineButton;
        ImageView accommodationImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.accommodationNameTextView);
            ownerEmailTextView = itemView.findViewById(R.id.ownerEmailTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            declineButton = itemView.findViewById(R.id.declineButton);
            accommodationImageView = itemView.findViewById(R.id.accommodationImageView);
        }

        public void setImageFromPath(String imagePath) {
            Picasso.get().load("http://10.0.2.2:8083/pictures/"+imagePath).into(accommodationImageView);
        }

    }


}
