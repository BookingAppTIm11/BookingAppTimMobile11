package com.example.bookingapptim11.adapters;

import static com.example.bookingapptim11.clients.ClientUtils.accommodationService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim11.R;
import com.example.bookingapptim11.dto.AccommodationIsAutomaticApprovalDto;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.bookingapptim11.models.AccommodationDetailsDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnersAccommodationsAdapter extends RecyclerView.Adapter<OwnersAccommodationsAdapter.ViewHolder>{

    private final List<AccommodationDetailsDTO> data;
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

        loadIsAutomaticApproval(holder, position);


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

        toggleAutomaticApprovalListener(holder);

    }

    private void loadIsAutomaticApproval(@NonNull ViewHolder holder, int position) {
        Call<AccommodationIsAutomaticApprovalDto> call = accommodationService.getAccommodationIsAutomaticApprovalById(data.get(position).getId());
        call.enqueue(new Callback<AccommodationIsAutomaticApprovalDto>() {
            @Override
            public void onResponse(Call<AccommodationIsAutomaticApprovalDto> call, Response<AccommodationIsAutomaticApprovalDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle the successful response here
                    holder.automaticApprovalSwitch.setChecked(response.body().isAutomaticApproval());
                }
            }

            @Override
            public void onFailure(Call<AccommodationIsAutomaticApprovalDto> call, Throwable t) {
                // Handle failure, e.g., network issues or parsing error
            }
        });
    }

    private void toggleAutomaticApprovalListener(@NonNull ViewHolder holder) {
        holder.setAutomaticApprovalSwitchListener(new AccommodationIsAutomaticApprovalListener() {
            @Override
            public void onSwitchChanged(int position, boolean isChecked) {

                    AccommodationDetailsDTO selectedAccommodation = data.get(position);

                    // Create the DTO with the updated approval status
                    AccommodationIsAutomaticApprovalDto approvalDto = new AccommodationIsAutomaticApprovalDto();
                    approvalDto.setId(selectedAccommodation.getId());
                    approvalDto.setAutomaticApproval(isChecked);

                    // Call the backend API to update the approval status
                    Call<AccommodationIsAutomaticApprovalDto> call = accommodationService.setAccommodationIsAutomaticApproval(approvalDto);
                    call.enqueue(new Callback<AccommodationIsAutomaticApprovalDto>() {
                        @Override
                        public void onResponse(Call<AccommodationIsAutomaticApprovalDto> call, Response<AccommodationIsAutomaticApprovalDto> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // Handle the successful response here
                                AccommodationIsAutomaticApprovalDto updatedDto = response.body();
                            } else {
                                // Handle unsuccessful response
                                // You can get more details from response.errorBody() if needed
                            }
                        }

                        @Override
                        public void onFailure(Call<AccommodationIsAutomaticApprovalDto> call, Throwable t) {
                            // Handle failure, e.g., network issues or parsing error
                        }
                    });
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, locationTextView, statusTextView;
        Button editButton, deleteButton;
        ImageView accommodationImageView;
        Switch automaticApprovalSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.accommodationNameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            accommodationImageView = itemView.findViewById(R.id.accommodationImageView);
            automaticApprovalSwitch = itemView.findViewById(R.id.automaticApprovalSwitch);
        }

        public void setAutomaticApprovalSwitchListener(AccommodationIsAutomaticApprovalListener listener) {
            automaticApprovalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onSwitchChanged(getAdapterPosition(), isChecked);
                }
            });
        }

        public void setSwitchListener(AccommodationIsAutomaticApprovalListener listener) {
            automaticApprovalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onSwitchChanged(getAdapterPosition(), isChecked);
                }
            });
        }
        public void setImageFromPath(String imagePath) {
            Picasso.get().load("http://10.0.2.2:8083/pictures/"+imagePath).into(accommodationImageView);
        }

    }

}


