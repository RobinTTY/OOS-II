package acmapview;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jsonstream.PlaneDataServer;
import messer.*;
import de.saring.leafletmap.*;
import senser.Senser;

public class AcMapView extends Application implements Observer
{
    private final ActiveAircrafts activeAircrafts = new ActiveAircrafts();
    private final TableView<BasicAircraft> table = new TableView<>();
    private final ObservableList<BasicAircraft> aircraftList = FXCollections.observableArrayList();
    private final HashMap<String, Marker> markerList = new HashMap<>();

    private final double latitude = 48.7433425;
    private final double longitude = 9.3201122;
    private final boolean haveConnection = true;
    private int selectedIndex;

    private CompletableFuture<Worker.State> loadState;
    private LeafletMapView lm;



    public static void main(String[] args) {
		launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        String urlString = "https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json";
        PlaneDataServer server;
        if(haveConnection)
            server = new PlaneDataServer(urlString, latitude, longitude, 250);
        else
            server = new PlaneDataServer();

        new Thread(server).start();                           //Server start
        Senser senser = new Senser(server);                   //new Senser
        senser.acInRange = true;                              //show console output aircraft in range
        new Thread(senser).start();                           //Senser start
        Messer messer = new Messer();                         //new Messer
        senser.addObserver(messer);                           //let Messer observe Senser
        new Thread(messer).start();                           //start Messer

        //activeAircrafts and Acamo needs to observe messer
        messer.addObserver(activeAircrafts);
        messer.addObserver(this);

        //fill table column headers
        ArrayList<String> attributeNames = BasicAircraft.getAttributesNames();
        aircraftList.addAll(activeAircrafts.values());
        for (String field : attributeNames) {
            TableColumn<BasicAircraft, String> column = new TableColumn<>(field);   //get column headers
            column.setCellValueFactory(new PropertyValueFactory<>(field));          //get column values
            table.getColumns().add(column);                                         //add columns
        }

        //configure table
        table.setItems(aircraftList);
        table.setEditable(false);
        table.autosize();

        //create layout of table and pane for selected aircraft
        HBox root = new HBox(50);
        root.setPadding(new Insets(20));
        root.getChildren().add(table);

        //vBox containing AircraftInformation + Map
        VBox vB = new VBox();
        root.getChildren().add(vB);

        //GridPane for Aircraft information
        GridPane gPan = new GridPane();
        gPan.setPadding(new Insets(20));                            //GridPane for plane info
        gPan.setHgap(3);                                                             //horizontal gap between elements
        gPan.setVgap(3);                                                             //vertical gap
        Font gFont = Font.font("Calibri Light", 18);                     //font used in gPan

        //fill AircraftInfo
        Label header = new Label("Aircraft Information");                       //Header (Aircraft Information)
        header.setFont(gFont);
        gPan.add(header,0,0,2,1);              //span header across the two columns of gPan
        for(int i = 0; i < attributeNames.size(); i++)                              //fill attribute names
        {
            Text tmp = new Text(attributeNames.get(i) + ":");
            tmp.setFont(gFont);
            gPan.add(tmp,0, i + 1);
        }
        vB.getChildren().add(gPan);

        //Add Leafletmap
        lm = new LeafletMapView();
        lm.setLayoutX(0);
        lm.setLayoutY(0);
        lm.setMaxWidth(640);
        List<MapLayer> config = new LinkedList<>();
        config.add(MapLayer.MAPBOX);
        vB.getChildren().add(lm);

        //record the load state of the Map
        loadState = lm.displayMap(new MapConfig(config, new ZoomControlConfig(), new ScaleControlConfig(), new LatLong(latitude, longitude)));

        //markers can only be added when the map is loaded
        loadState.whenComplete((state, throwable) -> {
            Marker homeMarker = new Marker(new LatLong(latitude, longitude), "home", "home", 0);
            lm.addCustomMarker("home", "planeIcons/basestationlarge.png");                              //create home marker
            lm.addMarker(homeMarker);                                                                                      //add to map and remember the returned String

            //create plane icons
            for(int i = 0; i <= 24; i++)
            {
                String number = String.format("%02d", i);
                lm.addCustomMarker("plane" + number, "planeIcons/plane" + number + ".png");
            }
        });

        //handle mouse click event on table (show info, select row)
        table.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                selectedIndex = table.getSelectionModel().getSelectedIndex();       //get selected row
                BasicAircraft ua = table.getSelectionModel().getSelectedItem();     //get selected item
                ArrayList<Object> uaValues;                                         //set gathered values to null initially
                uaValues = BasicAircraft.getAttributesValues(ua);                   //gather values trough BasicAircraft function
                int valIndex = 1;

                try{gPan.getChildren().remove(9,gPan.getChildren().size());}                              //indexes to be removed
                catch(IndexOutOfBoundsException | IllegalArgumentException e){ e.printStackTrace(); }     //remove existing content
                for (Object o : uaValues)
                    try {
                        Text tmp = new Text(o.toString());
                        tmp.setFont(gFont);                                          //enlarge text, change font
                        gPan.add(tmp,1, valIndex);                       //display values in column 1 of GridPane
                        valIndex++;                                                  //index moves next element 1 row down
                    } catch(java.lang.NullPointerException e) { System.out.println("gPan exception"); e.printStackTrace(); }
            }
        });

        Scene scene = new Scene(root,1350,900, Color.CYAN);
        stage.setScene(scene);
        stage.setTitle("Acamo");
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    private String acIconPicker(double track)
    {
        int iTrack = (int) Math.round(track / 15);                //360 Degrees, 24 PlaneIcons -> 360/24=15 - double value is required to round correctly
        return "plane" + String.format("%02d", iTrack);           //select right aircraft icon
    }

    //When messer updates Acamo (and activeAircrafts) the aircraftList must be updated as well
    @Override
    public void update(Observable o, Object arg) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //update ac in observable list
            aircraftList.clear();                                //clear observable list
            aircraftList.addAll(activeAircrafts.values());       //add all from ActiveAircrafts Object
            table.getSelectionModel().select(selectedIndex);     //keep selected row highlighted

        //update acIcons on map
            Platform.runLater(() -> {                            //updating a JavaFX thread -> runLater()
                for (BasicAircraft ac : aircraftList) {
                    LatLong latlong = new LatLong(ac.getCoordinate().getLatitude(), ac.getCoordinate().getLongitude());
                    String icao = ac.getIcao();
                    String planeIcon = acIconPicker(ac.getTrak());                                 //pass track to picker to get correct icon name
                    if (markerList.containsKey(icao)) {                                            //ICAO is in hashmap
                        Marker acMarker = markerList.get(icao);                                    //update marker position and icon
                        acMarker.move(latlong);
                        acMarker.changeIcon(planeIcon);
                    } else {
                        loadState.whenComplete((state, throwable) -> {                             //ICAO isn't in HashMap
                            Marker p = new Marker(latlong, icao, planeIcon, 0);        //add new marker
                            markerList.put(icao, p);
                            lm.addMarker(p);
                        });
                    }
                }
            });
    }
}

