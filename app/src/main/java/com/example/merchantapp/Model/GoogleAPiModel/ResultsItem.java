package com.example.merchantapp.Model.GoogleAPiModel;

import java.util.List;

public class ResultsItem{
	private String formatted_address;
	private List<String> types;
	private Geometry geometry;
	private List<AddressComponentsItem> addressComponents;
	private String placeId;
	private PlusCode plusCode;

	public String getFormattedAddress(){
		return formatted_address;
	}

	public List<String> getTypes(){
		return types;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public List<AddressComponentsItem> getAddressComponents(){
		return addressComponents;
	}

	public String getPlaceId(){
		return placeId;
	}

	public PlusCode getPlusCode(){
		return plusCode;
	}
}