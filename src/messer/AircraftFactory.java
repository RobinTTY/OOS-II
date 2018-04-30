package messer;
import senser.AircraftSentence;
import java.io.IOException;
import com.fasterxml.jackson.databind.*;

class AircraftFactory {
	BasicAircraft fromAircraftSentence(AircraftSentence sentence) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();	//jackson mapper
		return mapper.readValue("{" + sentence.toString() + "}", BasicAircraft.class);
	}
}
