package messer;

public class Coordinate {
	private double latitude;
	private double longitude;
	
	//TODO: Constructor, Getter/Setter and toString()

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

	public Coordinate(double latitude, double longitude) {
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