package com.sample.pronunciation.model.OCRModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OCRResponseModel {

    @SerializedName("ParsedResults")
    @Expose
    private List<ParsedResult> parsedResults = null;

    @SerializedName("OCRExitCode")
    @Expose
    private String OCRExitCode;

    @SerializedName("IsErroredOnProcessing")
    @Expose
    private Boolean isErroredOnProcessing;

    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;

    @SerializedName("ErrorDetails")
    @Expose
    private Object errorDetails;

    @SerializedName("ProcessingTimeInMilliseconds")
    @Expose
    private String processingTimeInMilliseconds;

    public List<ParsedResult> getParsedResults() {
        return parsedResults;
    }

    public void setParsedResults(List<ParsedResult> parsedResults) {
        this.parsedResults = parsedResults;
    }

    public String getOCRExitCode() {
        return OCRExitCode;
    }

    public void setOCRExitCode(String oCRExitCode) {
        this.OCRExitCode = oCRExitCode;
    }

    public Boolean getIsErroredOnProcessing() {
        return isErroredOnProcessing;
    }

    public void setIsErroredOnProcessing(Boolean isErroredOnProcessing) {
        this.isErroredOnProcessing = isErroredOnProcessing;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Object errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getProcessingTimeInMilliseconds() {
        return processingTimeInMilliseconds;
    }

    public void setProcessingTimeInMilliseconds(String processingTimeInMilliseconds) {
        this.processingTimeInMilliseconds = processingTimeInMilliseconds;
    }

}