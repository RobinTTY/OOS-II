package acmapview;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messer.BasicAircraft;
import messer.Coordinate;
import org.intellij.lang.annotations.Language;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Database implements Observer {
    private Connection connection;
    private Statement statement;
    @Language("TSQL")
    private String sqlQuery;
    ResultSet resultSet;

    public boolean connect(String connectionUrl){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);
            boolean status = testConnection();
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<BasicAircraft> GetCurrentAircrafts(){
        ObservableList<BasicAircraft> acList = FXCollections.observableArrayList();
        sqlQuery = "SELECT * FROM romuit02_Planes";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while(resultSet.next()){
                BasicAircraft ac = new BasicAircraft(resultSet.getString(2),resultSet.getString(3), resultSet.getInt(4), resultSet.getTimestamp(5), resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9), resultSet.getInt(10));
                acList.add(ac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acList;
    }

    public void disconnect(){
        if (connection != null) try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception while closing con");
        }
    }

    private boolean testConnection(){
        String testSqlQuery = "SELECT * FROM dbo.romuit02_Planes";
        Statement testStatement;
        try {
            testStatement = connection.createStatement();
            testStatement.executeQuery(testSqlQuery);
            System.out.println("Connection to database established.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void CloseStatement() {
        if (statement != null) try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception while closing stmt");
        }
    }

    private void CloseResultSet() {
        if (resultSet != null) try {
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Exception while closing rs");
            e.printStackTrace();
        }
    }

    private void AddPlaneToDb(BasicAircraft ac) {
        ArrayList<String> results = new ArrayList<>();
        try {
            // Check if plane already in database
            sqlQuery = "SELECT icao FROM dbo.romuit02_Planes";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            while(resultSet.next()){
                results.add(resultSet.getString(1));
            }
            if(results.contains(ac.getIcao()))
                this.updatePlaneInDb(ac);
            else{
                this.StoreInDb(ac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseResultSet();
            CloseStatement();
        }
    }

    private int getMaxId(){
        try {
            sqlQuery = "SELECT MAX(id) FROM dbo.romuit02_Planes";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CloseResultSet();
        CloseStatement();
        return 0;
    }

    private void StoreInDb(BasicAircraft ac) {
        sqlQuery = "INSERT INTO romuit02_Planes VALUES (" + (getMaxId()+ 1) + ",'" + ac.getIcao() + "','" + ac.getOperator() + "'," + ac.getSpecies() + ",'" + new Timestamp(ac.getPosTime().getTime()) + "'," + ac.getCoordinate().getLatitude() + "," + ac.getCoordinate().getLongitude() + "," + ac.getSpeed() + "," + ac.getTrak() + "," + ac.getAltitude() + ")";
        this.executeSqlStatement();
    }

    private void updatePlaneInDb(BasicAircraft ac){
        sqlQuery = "UPDATE romuit02_Planes SET posTime='" + new Timestamp(ac.getPosTime().getTime()) + "',latitude=" + ac.getCoordinate().getLatitude() + ",longitude=" + ac.getCoordinate().getLongitude() +",speed=" + ac.getSpeed() + ",trak=" + ac.getTrak() + ",altitude=" + ac.getAltitude() + " WHERE icao='" + ac.getIcao() + "'";
        this.executeSqlStatement();
    }

    private void executeSqlStatement(){
        try {
            statement = connection.createStatement();
            statement.execute(sqlQuery);
            System.out.println(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CloseStatement();
    }

    @Override
    public void update(Observable o, Object arg) {
        BasicAircraft ac = (BasicAircraft) arg;
        this.AddPlaneToDb(ac);
        this.GetCurrentAircrafts();
    }
}
