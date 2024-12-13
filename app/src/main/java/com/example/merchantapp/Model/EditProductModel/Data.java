package com.example.merchantapp.Model.EditProductModel;

public class Data{
	private Object upsertedId;
	private int upsertedCount;
	private boolean acknowledged;
	private int modifiedCount;
	private int matchedCount;

	public Object getUpsertedId(){
		return upsertedId;
	}

	public int getUpsertedCount(){
		return upsertedCount;
	}

	public boolean isAcknowledged(){
		return acknowledged;
	}

	public int getModifiedCount(){
		return modifiedCount;
	}

	public int getMatchedCount(){
		return matchedCount;
	}
}
