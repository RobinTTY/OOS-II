package senser;

import java.util.ArrayList;
import java.util.Observable;

import jsonstream.*;

public class Senser extends Observable implements Runnable
{
	private static boolean lab1 = true;
	PlaneDataServer server;

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
		String aircraftList;
		
		//Create factory and display object
		AircraftSentenceDisplay display = new AircraftSentenceDisplay();
		AircraftSentenceFactory factory = new AircraftSentenceFactory();
		
		while (true)
		{
			aircraftList = getSentence();
			
			if(aircraftList == null || aircraftList.length() == 0)
				continue;
			
			//get aircraft list from factory and display plane jsons
			jsonAircraftList = factory.fromAircraftJson(aircraftList);

			if (lab1) System.out.println("Current Aircrafts in range " + jsonAircraftList.size());
			for(AircraftSentence sentence : jsonAircraftList)
			{
				// Display the sentence in Lab 1; disable for other labs
				if (lab1) display.display(sentence);
				
				// Notify all observers
				setChanged();
				notifyObservers(sentence);
			}
			if (lab1) System.out.println();
			if (lab1) try {
				Thread.sleep(5000);
			} catch (InterruptedException e) { }
			
		}		
	}
}

