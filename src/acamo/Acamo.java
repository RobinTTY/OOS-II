package acamo;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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
import javafx.scene.control.SelectionMode;
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
 
    private double latitude = 48.7433425;
    private double longitude = 9.3201122;
    private static boolean haveConnection = true;
    private int selectedIndex;
    
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

		//activeAircrafts and Acamo needs to observe messer
        messer.addObserver(activeAircrafts);
        messer.addObserver(this);
        fields = BasicAircraft.getAttributesNames();

        //fill column header using the attribute names from BasicAircraft
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

        //create layout of table and pane for selected aircraft
        HBox root = new HBox(50);                                           //HBox object as root
        root.setPadding(new Insets(20));
        root.getChildren().add(table);                                              //add table

        GridPane gPan = new GridPane();
        gPan.setPadding(new Insets(20));                            //GridPane for plane info
        gPan.setHgap(3);                                                             //gap between elements
        gPan.setVgap(3);

        int attrIndex = 1;
        ArrayList<String> attributesNames = BasicAircraft.getAttributesNames();
        Label header = new Label("Aircraft Information");
        gPan.getChildren().add(header);
        for(String attr : attributesNames)
        {
            gPan.add(new Text(attr + ":"),0, attrIndex);
            attrIndex++;
        }
        root.getChildren().add(gPan);

        //handle mouse click event
        table.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                selectedIndex = table.getSelectionModel().getSelectedIndex();       //get selected row
                table.getSelectionModel().select(selectedIndex);                    //select selected row
                BasicAircraft ua = table.getSelectionModel().getSelectedItem();     //get selected item
                ArrayList<Object> uaValues = null;                                  //set gathered values to null initially
                try {
                    uaValues = BasicAircraft.getAttributesValues(ua);               //gather values trough BasicAircraft function
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) { e.printStackTrace(); }
                int valIndex = 1;
                try{gPan.getChildren().remove(9,17);} catch(java.lang.IndexOutOfBoundsException e){}    //remove existing content
                for (Object o : uaValues)
                    try {
                        gPan.add(new Text(o.toString()),1, valIndex);                       //display values
                        valIndex++;                                                                     //index moves next element 1 row down
                    } catch(java.lang.NullPointerException e) {}
            }
        });

        //Scene
		Scene scene = new Scene(root,1200,900, Color.CYAN);
        stage.setScene(scene);
        stage.setTitle("Acamo");
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    // TODO: When messer updates Acamo (and activeAircrafts) the aircraftList must be updated as well
    @Override
    public void update(Observable o, Object arg)
    {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        aircraftList.clear();                                //clear observable list
        aircraftList.addAll(activeAircrafts.values());       //add all from ActiveAircrafts Object
    }
}
