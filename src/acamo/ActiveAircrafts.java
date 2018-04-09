package acamo;
import java.util.*;
import messer.*;

//TODO: create hash map and complete all operations
public class ActiveAircrafts implements Observer
{
	private HashMap<String , BasicAircraft> activeAircrafts;    	// store the basic aircraft and use its Icao as key
																	// replace K and V with the correct class names

	public ActiveAircrafts ()
	{
		activeAircrafts = new HashMap<>();
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
	//store arg in Hashmap using the method above
	public void update(Observable o, Object arg)
    {
		BasicAircraft ac = (BasicAircraft) arg;
    	this.store(ac.getIcao(), ac);
	}
}