package adapters;

import static clients.ClientUtils.profileService;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.dto.NotificationDTO;
import com.example.bookingapptim11.dto.NotificationType;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.GuestReservation;
import com.example.bookingapptim11.models.Review;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import clients.ClientUtils;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReviewsAdapter extends RecyclerView.Adapter<AdminReviewsAdapter.ViewHolder>{
    private final List<Review> data;
    Context context;

    public AdminReviewsAdapter(Context context, List<Review> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_admin_ratings_item, parent, false);
        return new AdminReviewsAdapter.ViewHolder(view);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdminReviewsAdapter.ViewHolder holder, int position) {
        String id = "Id: " + data.get(position).getId();
        holder.reviewIdTextView.setText(id);
        String type;
        if(data.get(position).getOwnerEmail() == null){
            type = "Accommodation: " + data.get(position).getAccommodationId();
        }else{
            type = "Owner: " + data.get(position).getOwnerEmail();
        }
        String rating = "Rating: " + data.get(position).getRating();
        holder.typeTextView.setText(String.valueOf(type));
        holder.ratingTextView.setText(rating);
        holder.reviewText.setText(String.valueOf(data.get(position).getDescription()));
        holder.reviewText.setKeyListener(null);
        holder.reviewText.setCursorVisible(false);
        holder.reviewText.setPressed(false);
        holder.reviewText.setFocusable(false);
        if(!data.get(position).isReported()){
            holder.deleteButton.setVisibility(View.GONE);
        }

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                data.get(clickedPosition).setApproved(true);
                data.get(clickedPosition).setReported(false);
                Call<Review> call = ClientUtils.reviewService.updateReview(data.get(clickedPosition).getId(), data.get(clickedPosition), "Bearer " + AuthManager.getToken());
                call.enqueue(new Callback<Review>() {
                    @Override
                    public void onResponse(Call<Review> call, Response<Review> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }

                        if(data.get(clickedPosition).getOwnerEmail() == null || data.get(clickedPosition).getOwnerEmail() == ""){

                            Call<AccommodationDetailsDTO> call2 = ClientUtils.accommodationService.getAccommodation(data.get(clickedPosition).getAccommodationId());
                            call2.enqueue(new Callback<AccommodationDetailsDTO>() {
                                @Override
                                public void onResponse(Call<AccommodationDetailsDTO> call2, Response<AccommodationDetailsDTO> response) {
                                    if (response.code() == 200){
                                        NotificationDTO notificationCreatedDTO = new NotificationDTO(0L,
                                                response.body().getOwnerEmail(),
                                                NotificationType.RATING_ACCOMMODATIONS,
                                                data.get(clickedPosition).getGuestEmail() +" has reviewed your accommodation " + data.get(clickedPosition).getAccommodationId());

                                        Call<NotificationDTO> call1 = profileService.createNotification(notificationCreatedDTO);
                                        call1.enqueue(new Callback<NotificationDTO>() {
                                            @Override
                                            public void onResponse(Call<NotificationDTO> call1, Response<NotificationDTO> response) {
                                                if (response.isSuccessful()) {
                                                    data.remove(clickedPosition);
                                                    notifyItemRemoved(clickedPosition);
                                                } else {
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<NotificationDTO> call1, Throwable t) {

                                            }
                                        });
                                    }else{
                                        Log.d("REZ","Meesage recieved: "+response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<AccommodationDetailsDTO> call, Throwable t) {
                                }
                            });

                        }else {
                            NotificationDTO notificationCreatedDTO = new NotificationDTO(0L,
                                    data.get(clickedPosition).getOwnerEmail(),
                                    NotificationType.RATING_ACCOMMODATIONS,
                                    data.get(clickedPosition).getGuestEmail() + " has reviewed you! ");
                            Call<NotificationDTO> call1 = profileService.createNotification(notificationCreatedDTO);
                            call1.enqueue(new Callback<NotificationDTO>() {
                                @Override
                                public void onResponse(Call<NotificationDTO> call1, Response<NotificationDTO> response) {
                                    if (response.isSuccessful()) {
                                        data.remove(clickedPosition);
                                        notifyItemRemoved(clickedPosition);
                                    } else {
                                    }
                                }
                                @Override
                                public void onFailure(Call<NotificationDTO> call1, Throwable t) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<Review> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                data.get(clickedPosition).setApproved(true);
                data.get(clickedPosition).setReported(false);
                Call<Void> call = ClientUtils.reviewService.deleteReview(data.get(clickedPosition).getId(), "Bearer " + AuthManager.getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });


    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewIdTextView, typeTextView, ratingTextView, reviewText;
        Button approveButton, deleteButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewIdTextView = itemView.findViewById(R.id.reviewIdTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            reviewText = itemView.findViewById(R.id.reviewText);
            approveButton = itemView.findViewById(R.id.approveButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
