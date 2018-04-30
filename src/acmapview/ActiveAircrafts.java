package acmapview;
import java.util.*;
import messer.*;

public class ActiveAircrafts implements Observer
{
	private final HashMap<String , BasicAircraft> activeAircrafts;		//string = icao

	public ActiveAircrafts ()
	{
		activeAircrafts = new HashMap<>();
	}

	private synchronized void store(String icao, BasicAircraft ac)
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