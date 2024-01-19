package adapters;

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
import com.example.bookingapptim11.fragments.GuestReservationsFragment;
import com.example.bookingapptim11.models.AccommodationDetailsDTO;
import com.example.bookingapptim11.models.GuestReservation;
import com.example.bookingapptim11.models.OwnerReservation;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import clients.ClientUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestReservationsAdapter  extends RecyclerView.Adapter<GuestReservationsAdapter.ViewHolder>{

    private final List<GuestReservation> data;
    Context context;

    public GuestReservationsAdapter(Context context, List<GuestReservation> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public GuestReservationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_guest_reservations_item, parent, false);
        return new GuestReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestReservationsAdapter.ViewHolder holder, int position) {
        String date = String.valueOf(data.get(position).getStartDate()) + " - " + String.valueOf(data.get(position).getEndDate());
        holder.accommodationIdTextView.setText(String.valueOf(data.get(position).getAccommodation()));
        holder.priceTextView.setText(String.valueOf(data.get(position).getPrice()));
        holder.dateTextView.setText(String.valueOf(date));
        holder.statusTextView.setText(String.valueOf(data.get(position).getStatus()));

        int clickedPosition = holder.getAdapterPosition();
        Call<AccommodationDetailsDTO> call = ClientUtils.accommodationService.getAccommodation(data.get(clickedPosition).getAccommodation());
        call.enqueue(new Callback<AccommodationDetailsDTO>() {
            @Override
            public void onResponse(Call<AccommodationDetailsDTO> call, Response<AccommodationDetailsDTO> response) {
                if (response.code() == 200){
                    Log.d("REZ",response.toString());
                    String[] dateParts = data.get(clickedPosition).getStartDate().split("-");
                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);


                    Date parsedDate = new Date(year - 1900, month - 1, day);

                    long currentTimeInSeconds = System.currentTimeMillis() / 1000;
                    long reservationTimeInSeconds = parsedDate.getTime() / 1000;

                    boolean cancelable = (data.get(clickedPosition).getStatus().toString().equals("Accepted") &&
                            (reservationTimeInSeconds - currentTimeInSeconds) > (long) Objects.requireNonNull(response.body()).getCancellationDays() * 24 * 60 * 60);

                    if(cancelable){
                        holder.cancelButton.setVisibility(View.VISIBLE);
                    }else{
                        holder.cancelButton.setVisibility(View.GONE);
                    }
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<AccommodationDetailsDTO> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                Call<GuestReservation> call = ClientUtils.reservationService.cancelReservation(data.get(clickedPosition).getId());
                call.enqueue(new Callback<GuestReservation>() {
                    @Override
                    public void onResponse(Call<GuestReservation> call, Response<GuestReservation> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());
                            //reload the adapter
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<GuestReservation> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView accommodationIdTextView, priceTextView, dateTextView, statusTextView;
        Button cancelButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accommodationIdTextView = itemView.findViewById(R.id.accommodationIdTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            cancelButton = itemView.findViewById(R.id.cancelButton);
        }
    }
}
