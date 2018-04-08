package messer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
	private double latitude;
	private double longitude;

	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@JsonCreator
	public Coordinate(@JsonProperty("Lat") Double latitude,@JsonProperty("Long") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

	@Override
	public String toString() {
		return "Coordinate{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}