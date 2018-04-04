package messer;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import senser.AircraftSentence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Get the following data fields out of the JSON sentence using Regex and String methods
 * and return a BasicAircraft
 * 
 * For Lab3 replace use JSO parsing instead
 * 
 * "Icao":"3C5467", matches; first part is Icao, second part is 3C5467
 * "Op":"Lufthansa matches; first part is Op, second part is Lufthansa
 * "Species":1, matches; first part is Species, second part is 1
 * "PosTime":1504179914003, matches; first part is PosTime, second part is 1504179914003
 * "Lat":49.1912, matches; first part is Lat, second part is 49.1912
 * "Long":9.3915, matches; first part is Long, second part is 9.3915
 * "Spd":420.7, matches; first part is Spd, second part is 420.7
 * "Trak":6.72, matches; first part is Trak, second part is 6.72
 * "GAlt":34135, matches; first part is GAlt, second part is 34135
 */

public class AircraftFactory {

	public BasicAircraft fromAircraftSentence (AircraftSentence sentence ) {
		String icao = null;
		String operator = null;
		int species = 0;
		Date posTime = null;
		double longitude = 0;
		double latitude = 0;
		double speed = 0;
		double trak = 0;
		int altitude = 0;

		// TODO: Your code goes here
        Pattern pArr[] = { Pattern.compile("(?:cao\":\")(\\d\\w*)"), Pattern.compile("(?:GAlt\":)(\\d*)"), Pattern.compile("(?:Lat\":)(\\d*\\.\\d*)"), Pattern.compile("(?:Long\":)(\\d*\\.\\d*)"), Pattern.compile("(?:PosTime\":)(\\d*)"), Pattern.compile("(?:Spd\":)(\\d*)"), Pattern.compile("(?:Trak\":)(\\d*\\.\\d*)"), Pattern.compile("(?:Op\":)\"([^\"]*)\""), Pattern.compile("(?:Species\":)([\\d])") };
		Matcher m = pArr[0].matcher(sentence.toString());
        for (int i = 0; i < 9; i++)
        {
            m.usePattern(pArr[i]); m.reset();		//each cycle update Pattern and reset matcher position
			if (m.find()) {                         //Attempts to find the next subsequence of the input sequence that matches the pattern
				switch (i) {
					case 0:
						icao = m.group(1);
						break;
					case 1:
						altitude = Integer.parseInt(m.group(1));
						break;
					case 2:
						latitude = Float.parseFloat(m.group(1));
						break;
					case 3:
						longitude = Float.parseFloat(m.group(1));
						break;
					case 4:
						posTime = new Date(Long.parseLong(m.group(1)));
						break;
					case 5:
						speed = Integer.parseInt(m.group(1));
						break;
					case 6:
						trak = Float.parseFloat(m.group(1));
						break;
					case 7:
						operator = m.group(1);
						break;
					case 8:
						species = Integer.parseInt(m.group(1));
						break;
				}
			}
        }

		BasicAircraft msg = new BasicAircraft(icao, operator, species, posTime, new Coordinate(latitude, longitude), speed, trak, altitude);
		return msg;
	}
}
