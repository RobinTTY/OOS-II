package senser;

public class AircraftSentence
{
    public String aircraftJson;

    AircraftSentence(String token)
    {
        this.aircraftJson = token;
    }

	public String getAircraftJson()
    {
        return aircraftJson;
    }

    public String toString()
    {
        return getAircraftJson();
    }
}
