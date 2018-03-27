package messer;

import java.lang.reflect.Field;
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

	//TODO: Create relevant getter methods

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
}
