package com.example.merchantapp.Model.GoogleAPiModel;

import java.util.List;

public class GeocodeResponse{
	private PlusCode plusCode;
	private List<ResultsItem> results;
	private String status;

	public PlusCode getPlusCode(){
		return plusCode;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public String getStatus(){
		return status;
	}
}