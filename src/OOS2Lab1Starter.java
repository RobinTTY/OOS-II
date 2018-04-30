import jsonstream.*;
import senser.Senser;

class OOS2Lab1Starter
{
    private static final double latitude = 48.7433425;
    private static final double longitude = 9.3201122;
    private static final boolean haveConnection = true;

	public static void main(String[] args)
	{
		String urlString = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json";
		PlaneDataServer server;
		
		if(haveConnection)
			server = new PlaneDataServer(urlString, latitude, longitude, 50);
		else
			server = new PlaneDataServer();
		
		Senser senser = new Senser(server);
		new Thread(server).start();
		new Thread(senser).start();
	}
}