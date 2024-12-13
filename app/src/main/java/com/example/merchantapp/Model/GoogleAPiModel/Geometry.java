package com.example.merchantapp.Model.GoogleAPiModel;

public class Geometry{
	private Viewport viewport;
	private Bounds bounds;
	private Location location;
	private String locationType;

	public Viewport getViewport(){
		return viewport;
	}

	public Bounds getBounds(){
		return bounds;
	}

	public Location getLocation(){
		return location;
	}

	public String getLocationType(){
		return locationType;
	}
}
