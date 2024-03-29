package com.example.bookingapptim11.ui.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.bookingapptim11.R;

public class LocationDialog extends AlertDialog.Builder{
    public LocationDialog(Context context) {

        super(context);

        setUpDialog();
    }

    private void setUpDialog(){
        setTitle("Oops");
        setMessage("Location is disabled");
        setCancelable(false);

        setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getContext().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.dismiss();
            }
        });

        setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
