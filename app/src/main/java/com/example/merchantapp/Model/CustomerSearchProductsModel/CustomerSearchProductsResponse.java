package com.example.merchantapp.Model.CustomerSearchProductsModel;

import java.util.List;

public class CustomerSearchProductsResponse {
	private int statusCode;
	private String message;
	private List<com.example.merchantapp.Model.CustomerSearchProductsModel.DataItem> data;

	// Getters and setters

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<com.example.merchantapp.Model.CustomerSearchProductsModel.DataItem> getData() {
		return data;
	}

	public void setData(List<com.example.merchantapp.Model.CustomerSearchProductsModel.DataItem> data) {
		this.data = data;
	}

	public static class DataItem {
		private String _id;
		private List<AdminProductIdItem> adminProductId;

		// Getters and setters

		public String get_id() {
			return _id;
		}

		public void set_id(String _id) {
			this._id = _id;
		}

		public List<AdminProductIdItem> getAdminProductId() {
			return adminProductId;
		}

		public void setAdminProductId(List<AdminProductIdItem> adminProductId) {
			this.adminProductId = adminProductId;
		}
	}
}
