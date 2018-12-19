package acmapview;
import java.util.*;
import messer.*;

public class ActiveAircrafts extends Observable implements Observer, Runnable
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

	public boolean contains(String key){
		return activeAircrafts.containsKey(key);
	}


	@Override
	//store arg in Hashmap using the method above
	public void update(Observable o, Object arg)
    {
		BasicAircraft ac = (BasicAircraft) arg;
		this.store(ac.getIcao(), ac);
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setChanged();
			notifyObservers();
		}
	}
}