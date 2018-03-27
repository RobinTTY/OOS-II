import jsonstream.*;
import messer.Messer;
import senser.Senser;

public class OOS2Lab2Starter
{
    private static double latitude = 48.7433425;
    private static double longitude = 9.3201122;
    private static boolean haveConnection = true;

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
		
		Messer messer = new Messer();
		senser.addObserver(messer);
		new Thread(messer).start();
	}
}