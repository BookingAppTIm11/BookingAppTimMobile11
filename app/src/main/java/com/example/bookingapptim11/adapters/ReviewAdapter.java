package com.example.bookingapptim11.adapters;

import static com.example.bookingapptim11.clients.ClientUtils.reviewService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Review;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.bookingapptim11.login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<Review> data;
    private Context context;
    private String accommodationOwnerEmail;

    public ReviewAdapter(Context context,List<Review> data, String accommodationOwnerEmail) {
        this.data = data;
        this.context = context;
        this.accommodationOwnerEmail = accommodationOwnerEmail;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_review_item, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.userEmail.setText(String.valueOf(data.get(position).getGuestEmail()));
        holder.rating.setText(String.valueOf(data.get(position).getRating()));
        holder.description.setText(String.valueOf(data.get(position).getDescription()));
        holder.reviewDate.setText("Reviewed on: " + String.valueOf(
                Instant.ofEpochSecond(data.get(position).getDate()).atZone(ZoneId.systemDefault()).
                        toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        if (AuthManager.getUserEmail().equals(data.get(position).getGuestEmail())) {
            holder.deleteIcon.setVisibility(View.VISIBLE);
        }
        if(AuthManager.getUserEmail().equals(accommodationOwnerEmail)){
            holder.reportIcon.setVisibility(View.VISIBLE);
        }

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int clickedPosition = holder.getAdapterPosition();
                Call<Void> call = reviewService.deleteReview(data.get(position).getId(), "Bearer " + AuthManager.getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            data.remove(clickedPosition);
                            notifyItemRemoved(clickedPosition);
                            Toast.makeText(context, "Successfully deleted your review!", Toast.LENGTH_LONG).show();
                        } else {
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }
        });

        holder.reportIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).isReported()){
                    Toast.makeText(context, "This review has already been reported!", Toast.LENGTH_LONG).show();
                }else{
                    int clickedPosition = holder.getAdapterPosition();
                    data.get(position).setReported(true);
                    Call<Review> call = reviewService.updateReview(data.get(position).getId(),data.get(position), "Bearer " + AuthManager.getToken());
                    call.enqueue(new Callback<Review>() {
                        @Override
                        public void onResponse(Call<Review> call, Response<Review> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Successfully reported review!", Toast.LENGTH_LONG).show();
                            } else{
                            }
                        }
                        @Override
                        public void onFailure(Call<Review> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userEmail, rating, description, reviewDate;
        ImageView reportIcon, deleteIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.userEmail);
            rating = itemView.findViewById(R.id.rating);
            description = itemView.findViewById(R.id.description);
            reviewDate = itemView.findViewById(R.id.reviewDate);
            reportIcon = itemView.findViewById(R.id.reportIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);


        }

    }
}
