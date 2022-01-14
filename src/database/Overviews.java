package database;

import userdata.CertificateAndStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Overviews {

    public static String amountOfCompletions(String course) {


        //These are the settings for the connection.
        String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;";

        //Connection controls information about the connection to the database.
        Connection con = null;

        //Statement lets us use SQL query's.
        Statement stmt = null;

        //ResultSet is the table we get from the database.
        //We can iterate through the rows.
        ResultSet rs = null;

        String amountOfCertificatedBySelectedStudent = "0";


        try {
            //Importing driver...
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Connecting to the database...
            con = DriverManager.getConnection(connectionUrl);

            //Making a SQL query.
            String SQL = "SELECT Count(*) AS Aantal FROM Certificaat AS C JOIN Inschrijving AS I ON C.CertificaatId = I.FK_Certificaat JOIN Cursus ON I.FK_Cursus = Cursus.Naam WHERE Cursus.Naam = '" + course + "'";

            stmt = con.createStatement();
            //Executing the query in the database
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                amountOfCertificatedBySelectedStudent = rs.getString("Aantal");

            }

        }

        //Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }

        return amountOfCertificatedBySelectedStudent;
    }

    public static List<String> mostViewedWebcasts() {

        //These are the settings for the connection.
        String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;";

        //Connection controls information about the connection to the database.
        Connection con = null;

        //Statement lets us use SQL query's.
        Statement stmt = null;

        //ResultSet is the table we get from the database.
        //We can iterate through the rows.
        ResultSet rs = null;

        // In this list will the top 3 most viewed webcasts be stored
        List<String> listOfMostViewedWebcasts = new ArrayList<>();


        try {
            //Importing driver...
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Connecting to the database...
            con = DriverManager.getConnection(connectionUrl);

            // The query which requests the top 3 most viewed webcasts
            String SQL = "SELECT Titel FROM ContentItem WHERE ContentItemId IN (SELECT TOP 3 FK_ContentItem FROM Webcast ORDER BY Aantal_weergaven DESC)";

            stmt = con.createStatement();
            //Executing the query in the database
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                String title = rs.getString("Titel");
                // Add the top 3 most viewed webcasts one by one into the list
                listOfMostViewedWebcasts.add(title);
            }
        }

        //Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }

        return listOfMostViewedWebcasts;
    }
}