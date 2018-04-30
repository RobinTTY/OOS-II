package uniacmapview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import acmapview.ActiveAircrafts;
import de.saring.leafletmap.LeafletMapView;
import de.saring.leafletmap.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.control.TableView;
import messer.BasicAircraft;

 class UniversalAcMapView //extends Application implements Observer
{
    private ActiveAircrafts activeAircrafts = new ActiveAircrafts();
    private TableView<BasicAircraft> table = new TableView<>();
    private ObservableList<BasicAircraft> aircraftList = FXCollections.observableArrayList();
    private ArrayList<String> fields;

    private double latitude = 48.7433425;
    private double longitude = 9.3201122;
    private static boolean haveConnection = true;
    private int selectedIndex;

    private CompletableFuture<Worker.State> loadState;
    private LeafletMapView lm;
    private HashMap<String, Marker> markerList = new HashMap<>();
    
    public static void main(String[] args) {
		//launch(args);
    }
	// TODO: For Lab 6 copy your AcMapView code here
}
