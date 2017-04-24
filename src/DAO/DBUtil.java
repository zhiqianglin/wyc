package DAO;

/**
 * Created by JARVIS on 4/14/17.
 */
import java.sql.*;
import java.lang.*;
import java.util.*;

public class DBUtil {

    private static final String JDBC_DRIVE = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123";

    public static Connection con = null;

    public static void dbConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVE);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void dbDisconnect() throws SQLException {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static List<List<String>> dbExcuteQuery(String query) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<List<String>> result = new LinkedList<>();

        try {
            dbConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rsmd = rs.getMetaData();

            // Store result in a list
            while (rs.next()) {
                List<String> row = new LinkedList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i ++) {
                    row.add(rs.getString(i));
                }
                result.add(row);
            }
        }
//        catch (SQLException ex) {
//            // handle any errors
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
//        }
        finally {
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
            dbDisconnect();
        }
        return result;
    }

    /*
     *  Insert, Update or Delete
     */
    public static int dbExecuteUpdate(String update) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        int updatedRows = 0;
        try {
            dbConnection();
            stmt = con.createStatement();
            updatedRows = stmt.executeUpdate(update);
//        } catch (SQLException ex) {
//            // handle any errors
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore
                stmt = null;
            }
            dbDisconnect();
        }
        return updatedRows;
    }
}
