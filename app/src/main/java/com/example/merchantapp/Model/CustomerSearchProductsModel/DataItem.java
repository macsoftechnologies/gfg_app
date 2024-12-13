package com.example.merchantapp.Model.CustomerSearchProductsModel;

import java.util.List;

public class DataItem{
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