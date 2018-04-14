package acamo;


import java.util.*;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import jsonstream.PlaneDataServer;
import messer.BasicAircraft;
import messer.*;
import senser.Senser;

public class Acamo extends Application implements Observer
{
	private ActiveAircrafts activeAircrafts = new ActiveAircrafts();
    private TableView<BasicAircraft> table = new TableView<>();
    private ObservableList<BasicAircraft> aircraftList = FXCollections.observableArrayList();
    private ArrayList<String> fields;
    private int activeAircraftsOld = activeAircrafts.values().size();
 
    private double latitude = 48.7433425;
    private double longitude = 9.3201122;
    private static boolean haveConnection = true;
    
    public static void main(String[] args) {
		launch(args);        //calls Application -> JavaFX start()
    }
 
    @Override
    public void start(Stage stage)
    {
		String urlString = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json";
		PlaneDataServer server;
		BasicAircraft.getAttributesNames();
		if(haveConnection)
			server = new PlaneDataServer(urlString, latitude, longitude, 50);
		else
			server = new PlaneDataServer();
		
		new Thread(server).start();                           //Server start
		Senser senser = new Senser(server);                   //new Senser
		new Thread(senser).start();                           //Senser start
		Messer messer = new Messer();                         //new Messer
		senser.addObserver(messer);                           //let Messer observe Senser
		new Thread(messer).start();                           //start Messer

		// TODO: activeAircrafts and Acamo needs to observe messer
        messer.addObserver(activeAircrafts);
        messer.addObserver(this);
        fields = BasicAircraft.getAttributesNames();

        // TODO: Fill column header using the attribute names from BasicAircraft
        aircraftList.addAll(activeAircrafts.values());
		for(int i = 0;i < fields.size();i++)
		{
		    try {
                TableColumn<BasicAircraft, String> column = new TableColumn<>(fields.get(i));       //get column headers
                column.setCellValueFactory(new PropertyValueFactory<>(fields.get(i)));              //get column values
                table.getColumns().add(column);                                                     //add columns
            } catch(IndexOutOfBoundsException e){ e.getMessage(); }                                 //some aircrafts have values missing
		}
		table.setItems(aircraftList);
		table.setEditable(false);
        table.autosize();

        // TODO: Create layout of table and pane for selected aircraft
        HBox root = new HBox(50);
        root.setPadding(new Insets(20));
        root.getChildren().add(table);

        // TODO: Add event handler for selected aircraft
        VBox vBox = new VBox();
        ArrayList<String> attributesNames = BasicAircraft.getAttributesNames();
        Label header = new Label("Aircraft Information");
        vBox.getChildren().add(header);
        for(String attr : attributesNames)
        {
            vBox.getChildren().add(1, new Text(attr + ":"));
        }
        root.getChildren().add(vBox);

        //Scene
		Scene scene = new Scene(root,1900,900, Color.CYAN);
        stage.setScene(scene);
        stage.setTitle("Acamo");
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    class clickHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            //if(!aircraftList.contains(activeAircrafts.values().get(0)))
            //activeAircrafts.values().contains()
        }
    }

    public void outOfRangeCheck(){

    }


    // TODO: When messer updates Acamo (and activeAircrafts) the aircraftList must be updated as well
    @Override
    public void update(Observable o, Object arg)
    {
        aircraftList.clear();                                //clear observable list
        aircraftList.addAll(activeAircrafts.values());       //add all from ActiveAircrafts Object
        if(activeAircraftsOld != activeAircrafts.values().size()) outOfRangeCheck();
    }
}
