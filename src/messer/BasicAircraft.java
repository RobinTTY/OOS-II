package messer;

import com.fasterxml.jackson.annotation.*;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicAircraft
{
	private final String icao;
	private final String operator;
	private final Integer species;
	private final Date posTime;
	private final Coordinate coordinate;
	private final Double speed;
	private final Double trak;
	private final Integer altitude;

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

	public String getOperator() {
		return operator;
	}

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
		ArrayList<String> variableList = new ArrayList<>();                             //Array which is returned
		for (Field k : fields)
		{
			String fString = k.toString();                                              //convert every field into a simple string
			variableList.add(fString.substring(fString.lastIndexOf('.') + 1));      //only get the variable name instead of whole preceding namespace
		}
        Collections.sort(variableList);                                                 //sort so order is same as AttributeValues
		return variableList;
	}

	public static ArrayList<Object> getAttributesValues(BasicAircraft ac) {
	    ArrayList<Object> acList = new ArrayList<>();
	    try
		{
			BeanInfo info = Introspector.getBeanInfo(ac.getClass(), Object.class);  	 //get info about ac.class (BasicAircraft)
        	PropertyDescriptor[] props = info.getPropertyDescriptors();            		 //get PropertyDescriptors from info
        	for (PropertyDescriptor pd : props)
       		 {                                 											 //operate for each gathered PropertyDescriptor
       		     Method getter = pd.getReadMethod();                                	 //get getter Method for Property
                 Object o = getter.invoke(ac);
                 if(o == null) o = "[no data provided]";                                //if AircraftSentence lacks particular value, provide one -> keeps GridPane Info consistent
                 acList.add(o);                                     		            //invoke getter Method and add the stored value to ArrayList
       		 }
		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException e){ e.printStackTrace(); }
		return acList;
	}

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
