package com.example.dev_200_1_network_client_data_storage_system_jpa.beans;

import jakarta.ejb.Stateless;

@Stateless
public class ReturningErrors {
    private String errorFieldType;
    private String errorReasonType;

    public String getErrorFieldType() {
        return errorFieldType;
    }

    public void setErrorFieldType(String errorFieldType) {
        this.errorFieldType = errorFieldType;
    }

    public String getErrorReasonType() {
        return errorReasonType;
    }

    public void setErrorReasonType(String errorReasonType) {
        this.errorReasonType = errorReasonType;
    }
}