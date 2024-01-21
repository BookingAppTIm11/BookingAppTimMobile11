package com.example.bookingapptim11.adapters;

import static com.example.bookingapptim11.clients.ClientUtils.profileService;

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

import com.example.bookingapptim11.dto.NumberOfCancellationsDTO;

import com.example.bookingapptim11.models.OwnerReservation;

import java.util.List;

import com.example.bookingapptim11.clients.ClientUtils;
import com.example.bookingapptim11.login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerReservationsAdapter extends RecyclerView.Adapter<OwnerReservationsAdapter.ViewHolder>{

    private final List<OwnerReservation> data;
    Context context;

    public OwnerReservationsAdapter(Context context,List<OwnerReservation> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public OwnerReservationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_owner_reservations_item, parent, false);
        return new OwnerReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerReservationsAdapter.ViewHolder holder, int position) {
        String date = String.valueOf(data.get(position).getStartDate()) + " - " + String.valueOf(data.get(position).getEndDate());
        holder.idTextView.setText(String.valueOf(data.get(position).getId()));
        holder.guestTextView.setText(String.valueOf(data.get(position).getGuest()));
        holder.dateTextView.setText(String.valueOf(date));
        holder.statusTextView.setText(String.valueOf(data.get(position).getStatus()));

        Call<NumberOfCancellationsDTO> call = ClientUtils.reservationService.getNumberOfCancellations(data.get(position).getGuest());
        call.enqueue(new Callback<NumberOfCancellationsDTO>() {
            @Override
            public void onResponse(Call<NumberOfCancellationsDTO> call, Response<NumberOfCancellationsDTO> response) {
                if (response.isSuccessful() && response.body() != null){
                    holder.cancellationsTextView.setText(String.valueOf(response.body().getNumberOfCancellations()));
                }
            }
            @Override
            public void onFailure(Call<NumberOfCancellationsDTO> call, Throwable t) {

            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                Call<OwnerReservation> call = ClientUtils.reservationService.acceptReservation(data.get(clickedPosition).getId());
                call.enqueue(new Callback<OwnerReservation>() {
                    @Override
                    public void onResponse(Call<OwnerReservation> call, Response<OwnerReservation> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());


                            NotificationDTO notificationCreatedDTO = new NotificationDTO(0L, data.get(clickedPosition).getGuest(), NotificationType.RESERVATION_RESPONSE,
                                    AuthManager.getUserEmail() +" acceppted reservation with id: " + data.get(clickedPosition).getId());

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
                    public void onFailure(Call<OwnerReservation> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });

        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                Call<OwnerReservation> call = ClientUtils.reservationService.declineReservation(data.get(clickedPosition).getId());
                call.enqueue(new Callback<OwnerReservation>() {
                    @Override
                    public void onResponse(Call<OwnerReservation> call, Response<OwnerReservation> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());


                            NotificationDTO notificationCreatedDTO = new NotificationDTO(0L, data.get(clickedPosition).getGuest(), NotificationType.RESERVATION_RESPONSE,
                                    AuthManager.getUserEmail() +" declined reservation with id: " + data.get(clickedPosition).getId());

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
                    public void onFailure(Call<OwnerReservation> call, Throwable t) {
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
        TextView idTextView, guestTextView, dateTextView, statusTextView, cancellationsTextView;
        Button acceptButton, declineButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.accommodationIdTextView);
            guestTextView = itemView.findViewById(R.id.guestTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            declineButton = itemView.findViewById(R.id.declineButton);
            cancellationsTextView = itemView.findViewById(R.id.cancellationText);
        }
    }
}
