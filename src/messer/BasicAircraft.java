package messer;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicAircraft {
	@JsonProperty("VsiT")
    private String vsit;
	private String icao;
	private String operator;
	private Integer species;
	private Date posTime;
	private Coordinate coordinate;
	private Double speed;
	private Double trak;
	private Integer altitude;
	
	//TODO: Create constructor

    @JsonCreator
	public BasicAircraft(@JsonProperty("Icao") String icao, @JsonProperty("Op") String operator, @JsonProperty("Species") Integer species, @JsonProperty("PosTime") Date posTime, @JsonProperty("coord") Coordinate coordinate, @JsonProperty("Spd") Double speed, @JsonProperty("Trak") Double trak, @JsonProperty("GAlt") Integer altitude)
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

	//getter methods
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
