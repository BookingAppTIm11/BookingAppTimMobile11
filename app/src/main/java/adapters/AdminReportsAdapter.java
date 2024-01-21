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
import com.example.bookingapptim11.models.Report;
import com.example.bookingapptim11.models.Review;

import java.util.List;

import clients.ClientUtils;
import login.AuthManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReportsAdapter extends RecyclerView.Adapter<AdminReportsAdapter.ViewHolder>{
    private final List<Report> data;
    Context context;

    public AdminReportsAdapter(Context context, List<Report> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_admin_reports_item, parent, false);
        return new AdminReportsAdapter.ViewHolder(view);
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
    public void onBindViewHolder(@NonNull AdminReportsAdapter.ViewHolder holder, int position) {
        String id = "Id: " + data.get(position).getId();
        holder.reportIdTextView.setText(id);
        String reporter = "Reporter: " + data.get(position).getSenderEmail();
        String reported = "Reported:\n " + data.get(position).getReceiverEmail();

        holder.reporterTextView.setText(reporter);
        holder.reportedTextView.setText(reported);
        holder.reportText.setText(String.valueOf(data.get(position).getContent()));
        holder.reportText.setKeyListener(null);
        holder.reportText.setCursorVisible(false);
        holder.reportText.setPressed(false);
        holder.reportText.setFocusable(false);


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();

                Call<Void> call = ClientUtils.reportService.deleteReport(data.get(clickedPosition).getId());
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

        holder.blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();

                Call<Void> call = ClientUtils.reportService.blockUser(data.get(clickedPosition).getReceiverEmail(), "Bearer " + AuthManager.getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200){
                            Log.d("REZ",response.toString());
                            Call<Void> call1 = ClientUtils.reportService.deleteReport(data.get(clickedPosition).getId());
                            call1.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call1, Response<Void> response) {
                                    if (response.code() == 200){
                                        Log.d("REZ",response.toString());
                                    }else{
                                        Log.d("REZ","Meesage recieved: "+response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call1, Throwable t) {
                                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                                }
                            });
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
        TextView reportIdTextView, reporterTextView, reportedTextView, reportText;
        Button blockButton, deleteButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reportIdTextView = itemView.findViewById(R.id.reportIdTextView);
            reporterTextView = itemView.findViewById(R.id.reporterTextView);
            reportedTextView = itemView.findViewById(R.id.reportedTextView);
            reportText = itemView.findViewById(R.id.reportText);
            blockButton = itemView.findViewById(R.id.blockButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
