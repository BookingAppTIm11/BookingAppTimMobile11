package com.example.bookingapptim11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.dto.NotificationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationDTO> notifications = new ArrayList<>();

    public void setNotifications(Collection<NotificationDTO> notifications){
        this.notifications.clear();
        this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        NotificationDTO notificationDTO = notifications.get(position);

        holder.notificationIdView.setText("Id: "+notificationDTO.getId());
        holder.notificationContentView.setText(notificationDTO.getMessage());
        holder.notificationReceiverView.setText(notificationDTO.getReceiverEmail());
        holder.notificationTypeView.setText(notificationDTO.getType().toString());
    }

    @Override
    public int getItemCount() {
        return this.notifications.size();
    }


    static class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView notificationContentView, notificationReceiverView, notificationIdView, notificationTypeView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationIdView = itemView.findViewById(R.id.notificationIdTextView);
            notificationContentView= itemView.findViewById(R.id.notificationContentTextView);
            notificationTypeView = itemView.findViewById(R.id.notificationTypeTextView);
            notificationReceiverView = itemView.findViewById(R.id.notificationRecieverTextView);




        }
    }
}
