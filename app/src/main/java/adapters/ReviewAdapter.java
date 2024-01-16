package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.models.Review;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import login.AuthManager;

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
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {

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
