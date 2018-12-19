package senser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import jsonstream.*;

public class Senser extends Observable implements Runnable
{
	private static final boolean lab1 = false;
	private final PlaneDataServer server;
	public boolean acInRange = false;

	public Senser(PlaneDataServer server)
	{
		this.server = server;
	}

	private String getSentence()
	{
		String list = server.getPlaneListAsString();
		if (lab1) System.out.println(list);
		return list;
	}
	
	public void run()
	{
		ArrayList<AircraftSentence> jsonAircraftList;
		AircraftSentenceDisplay display = new AircraftSentenceDisplay();
		AircraftSentenceFactory factory = new AircraftSentenceFactory();
		
		while (true)
		{
			String aircraftList = getSentence();
			
			if(aircraftList == null || aircraftList.length() == 0)
				continue;
			
			//get aircraft list from factory and display plane json
			jsonAircraftList = factory.fromAircraftJson(aircraftList);

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
			if (acInRange) System.out.println(sdf.format(new Date(System.currentTimeMillis())) + ": Current Aircrafts in range " + jsonAircraftList.size());
			for(AircraftSentence sentence : jsonAircraftList)
			{
				if (lab1) display.display(sentence);
				//Notify all observers
				setChanged();
				notifyObservers(sentence);
			}
			if (lab1) System.out.println();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) { e.printStackTrace(); }
			
		}		
	}
}

