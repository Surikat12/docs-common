package com.surikat.docs.common.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ApiError {

    @JsonProperty("error_code")
    private Integer errorCode;
    private String description;

    public ApiError() {
    }

    public ApiError(Integer errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorCode=" + errorCode +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(errorCode, apiError.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode);
    }
}
