package DAO;

/**
 * Created by JARVIS on 4/14/17.
 */

import Model.CityOfficial;
import Model.POIFilter;
import Model.POI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class POIDAO {

    private static final String TABLE = "POI";
    private static final String LOCATION_NAME = "LocationName";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String ZIP_CODE = "ZipCode";
    private static final String FLAG =  "flag";
    private static final String DATE_FLAGGED = "dateFlagged";


    /*
     *  Insert a new cityOfficial tuple
     */
    public static boolean insertPOI(CityOfficial cityOfficial) throws SQLException, ClassNotFoundException{
        String insertStatement = "INSERT INTO " + TABLE + "( " +  LOCATION_NAME + ", " + CITY + ", " +
                STATE + ", " + ZIP_CODE + ") VALUES ('" + cityOfficial.getUserName() + "', '" + cityOfficial.getCity() + "', '" +
                cityOfficial.getState() + "', '" + cityOfficial.getTitle() +  "')";
        int updatedRows = 0;


        updatedRows = DBUtil.dbExecuteUpdate(insertStatement);

        if (updatedRows != 0)
            return true;
        else
            return false;
    }


//    public static POI findPOI(String locationName) {
//        String queryStatement = "SELECT * FROM " + TABLE + " WHERE " + LOCATION_NAME + " = '" + locationName + "'";
//        POI curr = null;
//        try {
//            List<List<String>> result = DBUtil.dbExcuteQuery(queryStatement);
//            if (result.size() == 0) {
//                System.out.println("Not found");
//            } else {
//                List<String> userInfo = result.get(0);
//                System.out.println(userInfo.size());
//                for (String info : userInfo) {
//                    System.out.println(info);
//                }
//
//            }
//        }
//        catch (Exception e) {
//            Helper.showAlert("Error", e.getMessage());
//            return curr;
//        }
//
//        return curr;
//    }


    public static POI queryPOI(String locationName) throws SQLException, ClassNotFoundException {
        String queryStatement = "SELECT * FROM " + TABLE + " WHERE " + LOCATION_NAME + " = '" + locationName + "'";
        Statement stmt = null;
        ResultSet rs = null;
//        ResultSetMetaData rsmd = null;
        List<POI> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(queryStatement);
//            rsmd = rs.getMetaData();

        // Store result in a list
        while (rs.next()) {
            POI curr = new POI(rs.getString("LocationName"), rs.getString("City"),
                    rs.getString("State"), rs.getString("zipCode"), rs.getBoolean("flag"),
                    rs.getDate("dateFlagged"));
//                System.out.println(curr.getLocationName() + "," + curr.getCity() + curr.getState() + curr.getZipCode() + curr.getDateFlagged() + curr.isFlagged());
            result.add(curr);
        }

        // it is a good idea to release resources in a finally{} block in reverse-order of their creation
        // if they are no-longer needed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore
            stmt = null;
        }
        DBUtil.dbDisconnect();

        return null;
    }


    public static List<POI> filterPOI(POIFilter input) throws SQLException, ClassNotFoundException {
        String queryStatement = "SELECT * FROM " + TABLE + " WHERE " + LOCATION_NAME + " LIKE '" + input.getPOIName() + "' AND "
                + CITY + " LIKE '" + input.getCity() + "' AND " + STATE + " LIKE '" + input.getState() + "' AND " + ZIP_CODE
                + " LIKE '" + input.getZipCode() + "' AND " +  FLAG + " = " + input.getFlagged();
        if (input.getFlagged() == 1) {
            queryStatement = queryStatement + " AND " + DATE_FLAGGED + " BETWEEN " + "'" + input.getStart() + "' AND '" + input.getEnd() + "'";
        }

        System.out.println("Retrieve POI locations based on the filter: " + queryStatement);
        Statement stmt = null;
        ResultSet rs = null;
        List<POI> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(queryStatement);

        // Store result in a list
        while (rs.next()) {
            POI curr = new POI(rs.getString("LocationName"), rs.getString("City"),
                    rs.getString("State"), rs.getString("zipCode"), rs.getBoolean("flag"),
                    rs.getDate("dateFlagged"));
            System.out.println(curr.getLocationName() + "," + curr.getCity() + "," + curr.getState() + "," + curr.getZipCode() + "," + curr.getDateFlagged() + "," + curr.isFlagged());
            result.add(curr);
        }

        // it is a good idea to release resources in a finally{} block in reverse-order of their creation
        // if they are no-longer needed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore
            stmt = null;
        }
        DBUtil.dbDisconnect();

        return result;
    }


    public static List<POI> findAllPOI() throws SQLException, ClassNotFoundException {
        String queryStatement = "SELECT * FROM " + TABLE;
        System.out.println("find all POI location: " + queryStatement);
        Statement stmt = null;
        ResultSet rs = null;
        List<POI> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(queryStatement);

        // Store result in a list
        while (rs.next()) {
            POI curr = new POI(rs.getString("LocationName"), rs.getString("City"),
                    rs.getString("State"), rs.getString("zipCode"), rs.getBoolean("flag"),
                    rs.getDate("dateFlagged"));
//            System.out.println(curr.getLocationName() + "," + curr.getCity() + "," + curr.getState() + "," + curr.getZipCode() + "," + curr.getDateFlagged() + "," + curr.isFlagged());
            result.add(curr);
        }

        // it is a good idea to release resources in a finally{} block in reverse-order of their creation
        // if they are no-longer needed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore
            stmt = null;
        }
        DBUtil.dbDisconnect();

        return result;
    }



    public static List<String> queryAllLocationNames() throws SQLException, ClassNotFoundException {
        String query = "SELECT LocationName FROM " + TABLE + ";";

        System.out.println("get all POT LOcation name: " + query);
        Statement stmt = null;
        ResultSet rs = null;
        List<String> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(query);

        // Store result in a list
        while (rs.next()) {
            result.add(rs.getString("LocationName"));
        }

        // it is a good idea to release resources in a finally{} block in reverse-order of their creation
        // if they are no-longer needed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore
            stmt = null;
        }
        DBUtil.dbDisconnect();

        return result;
    }


    public static boolean insertLocation(POI curr) throws SQLException, ClassNotFoundException{
        String insertStatement = "INSERT INTO " + TABLE + "( " +  LOCATION_NAME + ", " + CITY + ", " +
                STATE + ", " + ZIP_CODE + "," + FLAG + "," + DATE_FLAGGED + ") VALUES ('" + curr.getLocationName() + "', '" + curr.getCity() + "', '" +
                curr.getState() + "', '" + curr.getZipCode() + "'," + curr.isFlagged() +  "," + curr.getDateFlagged() + ")";

        System.out.println("insert a new POI location: " + insertStatement);
        int updatedRows = 0;


        updatedRows = DBUtil.dbExecuteUpdate(insertStatement);

        if (updatedRows != 0)
            return true;
        else
            return false;
    }

    public static boolean updateFlag(String POILocation) throws SQLException, ClassNotFoundException {
        String queryStatement = "SELECT Flag from POI WHERE LocationName = '" + POILocation + "'";
        System.out.println("change the flag status of a given POI: " + queryStatement);
        Statement stmt = null;
        ResultSet rs = null;

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(queryStatement);
        boolean curFlag = false;

        if (rs.next()) {
            curFlag = rs.getBoolean("flag");
            System.out.println(curFlag);
        }

        if (curFlag == true) {
            System.out.println("cao");
            queryStatement = "UPDATE " + TABLE + " SET " + FLAG + " = False, "  + DATE_FLAGGED + " = null WHERE LocationName = '" + POILocation + "'";
        }
        else {
            Date today = new Date();
            Timestamp curTime = new Timestamp(today.getTime());
            System.out.println(curTime);
            queryStatement = "UPDATE " + TABLE + " SET " + FLAG + " = True, " + DATE_FLAGGED + " = '" + curTime + "' WHERE LocationName = '" + POILocation + "'";
        }
        System.out.println(queryStatement);
        int updatedRow = stmt.executeUpdate(queryStatement);

        // Store result in a list
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
            } // ignore
            stmt = null;
        }
        DBUtil.dbDisconnect();

        if (updatedRow > 0) {
            return true;
        } else {
            return false;
        }
    }
}
