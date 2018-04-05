package messer;
import senser.AircraftSentence;
import java.io.IOException;
import java.util.Date;
import com.fasterxml.jackson.databind.*;

public class AircraftFactory {

	public BasicAircraft fromAircraftSentence (AircraftSentence sentence ) throws IOException {
		String icao = null;
		String operator = null;
		int species = 0;
		Date posTime = null;
		double longitude = 0;
		double latitude = 0;
		double speed = 0;
		double trak = 0;
		int altitude = 0;


		ObjectMapper mapper = new ObjectMapper();	//jackson mapper
		return mapper.readValue("{" + sentence.toString() + "}", BasicAircraft.class);
	}
}
