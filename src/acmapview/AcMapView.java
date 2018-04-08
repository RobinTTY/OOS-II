package acmapview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import acamo.ActiveAircrafts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import jsonstream.PlaneDataServer;
import messer.BasicAircraft;
import messer.*;
import senser.Senser;

//import com.lynden.gmapsfx.GoogleMapView;
//import com.lynden.gmapsfx.MapComponentInitializedListener;
//import com.lynden.gmapsfx.javascript.object.GoogleMap;
//import com.lynden.gmapsfx.javascript.object.LatLong;
//import com.lynden.gmapsfx.javascript.object.MapOptions;
//import com.lynden.gmapsfx.javascript.object.Marker;
//import com.lynden.gmapsfx.javascript.object.MarkerOptions;
//import com.lynden.gmapsfx.util.MarkerImageFactory;

public class AcMapView //extends Application implements Observer
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

	// TODO: For Lab 5 copy your Acamo code here 
}
