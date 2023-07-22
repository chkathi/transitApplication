package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Stop {
	@Id
	private String id;
	private String name;
	private String lat;
	private String lon;
	
	public Stop() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

	@Override
	public String toString() {
		return "Stop [ID: " + id + ", Name: " + name + ", Latitude: " + lat + ", Longtitude: " + lon + "";
	}

}
