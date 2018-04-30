package senser;

public class AircraftSentence
{
    private final String aircraftJson;

    AircraftSentence(String token)
    {
        this.aircraftJson = token;
    }

	private String getAircraftJson()
    {
        return aircraftJson;
    }

    public String toString()
    {
        return getAircraftJson();
    }
}
