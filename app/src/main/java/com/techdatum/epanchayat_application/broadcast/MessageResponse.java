package com.techdatum.epanchayat_application.broadcast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sonal Naidu on 23-03-2018.
 */

public  class MessageResponse {
    @SerializedName("otp")
    @Expose
    private String status;
    @SerializedName("responseCode")
    @Expose
    private String details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
