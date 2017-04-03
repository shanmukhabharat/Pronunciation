package com.sample.pronunciation.utils;

public class GlobalUtils {

    public static String APP_FOLDER_NAME = "pronunciation";
    
    public static String APP_TEMP_FOLDER_NAME = "temp";
    
    public static final String API_URL = "https://api.ocr.space/parse/";
    
    public static final String API_KEY = "ea0d61717088957";
    
    public static final String EXIT_CODE_SUCCESS = "1";
    
    public static final String EXIT_CODE_PARTIAL_SUCCESS = "2";
    
    public static final String EXIT_CODE_FAILED_PARSING = "3";
    
    public static final String EXIT_CODE_FATAL_ERROR = "4";
    
    public static final String MESSAGE_EXIT_CODE_FAILED_PARSING = "Could not parse the image properly. Please re-try.";
    
    public static final String MESSAGE_EXIT_CODE_FATAL_ERROR = "Some thing went really bad. Please contact the customer support.";
    
    public static final String MESSAGE_EXIT_CODE_GENERIC = "Some thing went wrong. Please re-try";
    
    
    public static String getMessageFromExitCode(String exitCode) {
        
        String message;
        if (exitCode.equals(EXIT_CODE_FAILED_PARSING)) {
            message = MESSAGE_EXIT_CODE_FAILED_PARSING;
        } else if (exitCode.equals(EXIT_CODE_FATAL_ERROR)) {
            message = MESSAGE_EXIT_CODE_FATAL_ERROR;
        } else {
            message = MESSAGE_EXIT_CODE_GENERIC;
        }
        
        return message;
    }
    
}
