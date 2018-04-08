package messer;

import com.fasterxml.jackson.annotation.*;

import java.lang.reflect.Field;
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

    @JsonCreator
	public BasicAircraft(@JsonProperty("Icao") String icao, @JsonProperty("Op") String operator, @JsonProperty("Species") Integer species, @JsonProperty("PosTime") Date posTime, @JsonProperty("Lat") Double latit, @JsonProperty("Long") Double longit, @JsonProperty("Spd") Double speed, @JsonProperty("Trak") Double trak, @JsonProperty("GAlt") Integer altitude)
	{
		this.operator = operator;
		this.species = species;
		this.posTime = posTime;
		this.coordinate = new Coordinate(latit,longit);
		this.speed = speed;
		this.trak = trak;
		this.altitude = altitude;
		this.icao = icao;
	}

	//getter methods
	public String getIcao()
    {
		return icao;
	}

	public Integer getSpecies()
    {
		return species;
	}

	public Date getPosTime()
    {
		return posTime;
	}

	public Coordinate getCoordinate()
    {
		return coordinate;
	}

	public Double getSpeed()
    {
		return speed;
	}

	public Double getTrak()
    {
		return trak;
	}

	public Integer getAltitude()
    {
		return altitude;
	}

	public static ArrayList<String> getAttributesNames()
	{
		Field[] fields = BasicAircraft.class.getDeclaredFields();                       //gets the Class variables into the Field class
		ArrayList<String> variableList = new ArrayList<>();                                          //Array which is returned
		for (Field k : fields)
		{
			String fString = k.toString();                                              //convert every field into a simple string
			variableList.add(fString.substring(fString.lastIndexOf('.') + 1));      //only get the variable name instead of whole preceding namespace
		}
		return variableList;
	}

	public static ArrayList<Object> getAttributesValues(BasicAircraft ac)
	{
	    ArrayList<Object> acList = new ArrayList<>();
	    acList.add(ac);
        return acList;
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
