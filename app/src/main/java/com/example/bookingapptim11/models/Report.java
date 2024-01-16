package com.example.bookingapptim11.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Report implements Parcelable {

    private Long id;
    private String senderEmail;
    private String receiverEmail;
    private String content;

    public Report(Long id, String senderEmail, String receiverEmail, String content) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
    }

    protected Report(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        senderEmail = in.readString();
        receiverEmail = in.readString();
        content = in.readString();
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(senderEmail);
        dest.writeString(receiverEmail);
        dest.writeString(content);
    }
}
