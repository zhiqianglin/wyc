package DAO;

/**
 * Created by JARVIS on 4/14/17.
 */

import Controller.Helper;
import Model.*;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DataPointDAO {

    private static final String TABLE = "data_Point";
    private static final String POI_LOCATION_NAME = "POILocationName";
    private static final String DATE_Time = "dateTime";
    private static final String DATA_TYPE = "dataType";
    private static final String DATA_VALUE = "dataValue";
    private static final String ACCEPTED = "accepted";

    /*
     *  Insert a new user /NEW
     */
    public static boolean insertDataPoint(DataPoint dp) throws ClassNotFoundException, SQLException{
        String insertStatement = "INSERT INTO " + TABLE + "(" + POI_LOCATION_NAME  + ", " + DATE_Time + ", " +
                DATA_TYPE + ", " + DATA_VALUE + ", " + ACCEPTED +
                ") VALUES('" + dp.getLocationName() + "', '" + dp.getDateTime() + "', '" +
                dp.getDataType() + "', " + dp.getDataValue() + ", " + null +  ");";

        System.out.println("insert data point:" + insertStatement);

        int updatedRows = 0;

        updatedRows = DBUtil.dbExecuteUpdate(insertStatement);

        if (updatedRows != 0)
            return true;
        else
            return false;
    }

    public static List<DataPoint> queryAllDataPoints() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + TABLE + " WHERE accepted is NULL";

        System.out.println("find all data points: " + query);

        Statement stmt = null;
        ResultSet rs = null;
        List<DataPoint> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(query);

        // Store result in a list
        while (rs.next()) {
            DataPoint temp = new DataPoint(rs.getString("POILocationName"), rs.getTimestamp("DateTime"),
                    rs.getString("DataType"), rs.getInt("DataValue"));
            System.out.println(temp);
            result.add(temp);
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

    public static List<DataPoint> querySpecificDataPoints(POI input) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + TABLE + " WHERE accepted = 1 AND POILocationName = '" + input.getLocationName() + "'";
        System.out.println("Get all data points of a given POI: " + query);

        Statement stmt = null;
        ResultSet rs = null;
        List<DataPoint> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(query);

        // Store result in a list
        while (rs.next()) {
            DataPoint temp = new DataPoint(rs.getString("POILocationName"), rs.getTimestamp("DateTime"),
                    rs.getString("DataType"), rs.getInt("DataValue"));
//            System.out.println(temp);
            result.add(temp);
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

    public static List<DataPoint> filterDataPoint(DataPointFilter input) throws SQLException, ClassNotFoundException {
        String queryStatement = "SELECT * FROM " + TABLE + " WHERE " + POI_LOCATION_NAME + " = '" + input.getPOILocation() + "' AND " +
                DATA_TYPE + " LIKE '" + input.getDataType() + "' AND "
                + DATA_VALUE + " BETWEEN " + input.getMinValue() + " AND " + input.getMaxValue() + " AND " + DATE_Time + " BETWEEN " + "'"
                + input.getStart() + "' AND '" + input.getEnd() + "' AND Accepted = 1";
//        String queryStatement = "SELECT * FROM data_Point WHERE dataType LIKE 'Mold' AND dataValue BETWEEN 0 AND 1000000";
        System.out.println("Return all data points based on the filter: " + queryStatement);
        Statement stmt = null;
        ResultSet rs = null;
        List<DataPoint> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(queryStatement);

        // Store result in a list
        while (rs.next()) {
            DataPoint curr = new DataPoint(rs.getString("POILocationName"), rs.getTimestamp("dateTime"),
                    rs.getString("dataType"), rs.getInt("dataValue"), rs.getBoolean("accepted"));
            System.out.println(curr.getLocationName() + "," + curr.getDateTime() + "," + curr.getDataType() + "," + curr.getDataValue());
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

    public static boolean acceptDataPoint(DataPoint dp) throws SQLException, ClassNotFoundException {
//        UPDATE table_name SET field1=new-value1, field2=new-value2
//                [WHERE Clause]
        String update = "UPDATE " + TABLE + " SET ACCEPTED = TRUE WHERE POILocationName = '" + dp.getLocationName()
                + "' AND DateTime = '" + dp.getDateTime() + "';";
        System.out.println("Accept data point: " + update);
        Statement stmt = null;
        int updatedRow = 0;


        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        updatedRow = stmt.executeUpdate(update);

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


    public static boolean deleteUser(User user){
        //
        return false;
    }

    public static boolean rejectDataPoint(DataPoint dp) throws SQLException, ClassNotFoundException {
        //        UPDATE table_name SET field1=new-value1, field2=new-value2
//                [WHERE Clause]
        String update = "UPDATE " + TABLE + " SET ACCEPTED = FALSE WHERE POILocationName = '" + dp.getLocationName()
                + "' AND DateTime = '" + dp.getDateTime() + "';";
        System.out.println("reject data point: " + update);
        Statement stmt = null;
        int updatedRow = 0;


        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        updatedRow = stmt.executeUpdate(update);

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
