package com.example.merchantapp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Response{

	private int statusCode;
	private String message;
	private Product data;

	// Getters and setters
	public int getStatusCode() { return statusCode; }
	public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }

	public Product getData() { return data; }
	public void setData(Product data) { this.data = data; }
//	private Data data;
//	private String message;
//	private int statusCode;
//
//	public Data getData(){
//		return data;
//	}
//
//	public String getMessage(){
//		return message;
//	}
//
//	public int getStatusCode(){
//		return statusCode;
//	}
//
//	// Method to extract key-value pairs from specifications object
//	public String getSpecificationsKeyValueString() {
//		if (data != null && data.getProductSpecifications() != null) {
//			Object specifications = data.getProductSpecifications();
//
//			// Create a map to store unique key-value pairs for specifications
//			Map<String, Object> keyValueMap = new HashMap<>();
//
//			// Get all fields of the specifications object
//			StringBuilder keyValueString = new StringBuilder();
//			Field[] fields = specifications.getClass().getDeclaredFields();
//			for (Field field : fields) {
//				field.setAccessible(true); // Make the field accessible (in case it's private)
//
//				try {
//					// Get the name and value of each field
//					String fieldName = field.getName();
//					Object fieldValue = field.get(specifications);
//
//					// Check if the key already exists in the map
//					if (!keyValueMap.containsKey(fieldName)) {
//						// If not, add the key-value pair to the map and append it to the string
//						keyValueMap.put(fieldName, fieldValue);
//						keyValueString.append("Key: ").append(fieldName).append(", Value: ").append(fieldValue).append("\n");
//					}
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//			}
//
//			// Return the key-value pairs string
//			return keyValueString.toString();
//		} else {
//			// Handle the case where specifications is null
//			// For example, return an appropriate default value or handle the error
//			return "Specifications data is null.";
//		}
//	}
}
