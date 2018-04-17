package uniacmapview;

import java.util.ArrayList;

import acmapview.ActiveAircrafts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import messer.BasicAircraft;

//import com.lynden.gmapsfx.GoogleMapView;
//import com.lynden.gmapsfx.MapComponentInitializedListener;
//import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
//import com.lynden.gmapsfx.javascript.event.UIEventType;
//import com.lynden.gmapsfx.javascript.object.GoogleMap;
//import com.lynden.gmapsfx.javascript.object.LatLong;
//import com.lynden.gmapsfx.javascript.object.MapOptions;
//import com.lynden.gmapsfx.javascript.object.Marker;
//import com.lynden.gmapsfx.javascript.object.MarkerOptions;
//import com.lynden.gmapsfx.util.MarkerImageFactory;

 public class UniversalAcMapView //extends Application implements Observer
{
	private ActiveAircrafts activeAircrafts;
    private TableView<BasicAircraft> table = new TableView<BasicAircraft>();
    private ObservableList<BasicAircraft> aircraftList = FXCollections.observableArrayList();
    private ArrayList<String> fields;
 
    private double latitude = 48.7433425;
    private double longitude = 9.3201122;
    private static boolean haveConnection = true;
    
    public static void main(String[] args) {
		//launch(args);
    }
	// TODO: For Lab 6 copy your AcMapView code here 
}
