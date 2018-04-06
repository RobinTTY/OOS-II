package acamo;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import messer.*;

//TODO: create hash map and complete all operations
public class ActiveAircrafts implements Observer
{
	private HashMap<String , ArrayList<BasicAircraft>> activeAircrafts;    	// store the basic aircraft and use its Icao as key
                                                                            // replace K and V with the correct class names

	public ActiveAircrafts () {
	}

	public synchronized void store(String icao, BasicAircraft ac) {
	}

	public synchronized void clear() {
	}

	public synchronized BasicAircraft retrieve(String icao) {
		return ac;
	}

	public synchronized ArrayList<BasicAircraft> values () {
	}

	public String toString () {
		return activeAircrafts.toString();
	}

	@Override
	// TODO: store arg in hashmap using the method above
	public void update(Observable o, Object arg) {
	}
}