package messer;

import java.util.ArrayList;
import java.util.Date;

public class BasicAircraft {
	private String icao;
	private String operator;
	private Integer species;
	private Date posTime;
	private Coordinate coordinate;
	private Double speed;
	private Double trak;
	private Integer altitude;
	
	//TODO: Create constructor

	public BasicAircraft( String icao, String operator, Integer species, Date posTime, Coordinate coordinate, Double speed, Double trak, Integer altitude)
	{
		this.operator = operator;
		this.species = species;
		this.posTime = posTime;
		this.coordinate = coordinate;
		this.speed = speed;
		this.trak = trak;
		this.altitude = altitude;
		this.icao = icao;
	}

	//TODO: Create relevant getter methods
	public String getIcao() {
		return icao;
	}

	public Integer getSpecies() {
		return species;
	}

	public Date getPosTime() {
		return posTime;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public Double getSpeed() {
		return speed;
	}

	public Double getTrak() {
		return trak;
	}

	public Integer getAltitude() {
		return altitude;
	}


	//TODO: Lab 4-6 return attribute names and values for table
	public static ArrayList<String> getAttributesNames()
	{
		return null;
	}

	public static ArrayList<Object> getAttributesValues(BasicAircraft ac)
	{
		return null;
	}

	//TODO: Overwrite toString() method to print fields

    @Override
    public String toString() {
        return "BasicAircraft{" +
                "operator='" + operator + '\'' +
                ", species=" + species +
                ", posTime=" + posTime +
                ", coordinate=" + coordinate +
                ", speed=" + speed +
                ", trak=" + trak +
                ", altitude=" + altitude +
                ", icao='" + icao + '\'' +
                '}';
    }
}
