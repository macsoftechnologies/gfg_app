package com.example.merchantapp.Model.DeleteProductModel;

public class Data{
	private boolean acknowledged;
	private int deletedCount;

	public boolean isAcknowledged(){
		return acknowledged;
	}

	public int getDeletedCount(){
		return deletedCount;
	}
}
