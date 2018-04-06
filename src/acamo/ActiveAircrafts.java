package acamo;

import java.util.*;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import messer.*;

//TODO: create hash map and complete all operations
public class ActiveAircrafts implements Observer
{
	private HashMap<String , BasicAircraft> activeAircrafts;    	// store the basic aircraft and use its Icao as key
																	// replace K and V with the correct class names

	public ActiveAircrafts () {
	}

	public synchronized void store(String icao, BasicAircraft ac)
    {
		activeAircrafts.put(icao,ac);
	}

	public synchronized void clear()
    {
	    activeAircrafts.clear();
	}

	public synchronized BasicAircraft retrieve(String icao)
    {
		return activeAircrafts.get(icao);
	}

	public synchronized ArrayList<BasicAircraft> values ()
    {
        return new ArrayList<>(activeAircrafts.values());
	}

	public String toString () {
		return activeAircrafts.toString();
	}

	@Override
	// TODO: store arg in hashmap using the method above
	public void update(Observable o, Object arg)
    {
	    this.store();
	}
}