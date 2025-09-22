package com.example.petstore.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

	  private ResponseEntityUtil() {
		    throw new IllegalStateException("Utility class");
		  }
	
	public static ResponseEntity<Map<String, Object>> getResponseEntity(String string, HttpStatus httpStatus, Throwable throwable) {

		Map<String, Object> restfulResponse = new HashMap<>();
		restfulResponse.put("message", string);
		restfulResponse.put("status", httpStatus);
		restfulResponse.put("code", httpStatus.value());
		
		if (throwable != null) {
			restfulResponse.put("exception", throwable.getMessage());
		}

		return new ResponseEntity<>(restfulResponse, httpStatus);
	}
	
	public static ResponseEntity<Map<String, Object>> getResponseEntity(Object msg, HttpStatus httpStatus, Exception e) {

		Map<String, Object> restfulResponse = new HashMap<>();
		restfulResponse.put("message", msg);
		restfulResponse.put("status", httpStatus);
		restfulResponse.put("code", httpStatus.value());
		
		if (e != null) {
			restfulResponse.put("exception", e.getMessage());
		}

		return new ResponseEntity<>(restfulResponse, httpStatus);
	}
}