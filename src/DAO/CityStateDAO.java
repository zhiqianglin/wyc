package DAO;

/**
 * Created by JARVIS on 4/14/17.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CityStateDAO {

    private static final String CITY_STATE_TABLE = "city_state";
    private static final String ID = "id";
    private static final String CITY = "city";
    private static final String STATE = "state";

    /*
     *  Retrieve all state
     */
    public static List<List<String>> findAllState() throws SQLException, ClassNotFoundException {
        String query = "SELECT DISTINCT " + STATE + " FROM " + CITY_STATE_TABLE + " ORDER BY " + STATE;
        System.out.println("get all states :" + query);
        List<List<String>> result = new LinkedList<>();
        try {
            result = DBUtil.dbExcuteQuery(query);
        } catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }

    /*
     *  Retrieve all city in a given state
     */
    public static List<List<String>> findAllCity(String state) throws SQLException, ClassNotFoundException {
        String query = "SELECT " + CITY + " FROM " + CITY_STATE_TABLE + " WHERE " + STATE + " = '" + state + "' " +
                        "ORDER BY " + CITY;

        System.out.println("get all cities based on given state :" + query);
        List<List<String>> result = new LinkedList<>();
        try {
            result = DBUtil.dbExcuteQuery(query);
        } catch (SQLException e){
            System.out.println(e);
        }
        return result;
    }

    public static List<String> queryAllCity() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + CITY_STATE_TABLE + " ORDER BY " + CITY;
        System.out.println("get all cities :" + query);
        System.out.println(query);
        Statement stmt = null;
        ResultSet rs = null;

        List<String> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(query);

        // Store result in a list
        while (rs.next()) {
            result.add(rs.getString("city"));
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

    public static List<String> queryAllState() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM " + CITY_STATE_TABLE + " ORDER BY " + STATE;
        System.out.println("get all states :" + query);
        System.out.println(query);
        Statement stmt = null;
        ResultSet rs = null;

        List<String> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(query);

        // Store result in a list
        while (rs.next()) {
            result.add(rs.getString("state"));
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


}
