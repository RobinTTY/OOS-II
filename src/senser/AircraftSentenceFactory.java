package senser;

import java.util.ArrayList;

class AircraftSentenceFactory
{
	ArrayList<AircraftSentence> fromAircraftJson(String jsonAircraftString)
	{
		ArrayList<AircraftSentence>  jsonAircraftList = new ArrayList<>();		                //jsonAircraftList shall contain AircraftSentences //explicit type argument
		jsonAircraftString = jsonAircraftString.substring(1,jsonAircraftString.length() - 1);	//create new substring to get rid of first and last curly brace
		String[] token = jsonAircraftString.split("},\\{");								//split aircraft string into individual planes

        for (String str : token)
		{
			AircraftSentence sentence = new AircraftSentence(str);
			jsonAircraftList.add(sentence);														//add each aircraft (token) to the jsonAircraftList (Array List)
		}

		return jsonAircraftList;
	}
}
