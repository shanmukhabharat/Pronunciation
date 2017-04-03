package com.sample.pronunciation.utils;

/**
 * Created by Bharath on 01/04/17, April, 2017.
 */

public class ApiServiceException extends RuntimeException{
	
	
	public ApiServiceException(){
		
	}
	
	public ApiServiceException(String message){
		super(message);
	}
	
	public ApiServiceException(Throwable t){
		super(t);
	}
	
}