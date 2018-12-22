package messer;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

import senser.AircraftSentence;

public class Messer extends Observable implements Observer, Runnable
{
    private final LinkedBlockingQueue<AircraftSentence> queue;
    private boolean lab2 = false;
	
	public Messer()
	{
		this.queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		queue.add((AircraftSentence) arg1);
	}

	public void run()
	{
		AircraftFactory factory = new AircraftFactory();
		AircraftDisplay display = new AircraftDisplay();

		while(true)
		{
			AircraftSentence sentence = null;
			try {
				sentence = queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BasicAircraft msg = null;
			try {
				msg = factory.fromAircraftSentence(sentence);
			} catch (IOException e) {
				e.printStackTrace();
			}



            if (lab2) display.display(msg);
			//Notify all observers
			setChanged();
			notifyObservers(msg);
		}
	}
}
