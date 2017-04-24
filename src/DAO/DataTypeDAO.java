package DAO;

/**
 * Created by JARVIS on 4/14/17.
 */

import Model.CityOfficial;
import Model.DataPoint;
import Model.POI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataTypeDAO {

    private static final String TABLE = "Data_Type";

    public static List<String> queryDataType() throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM " + TABLE;
        System.out.println("get all data type: " + query);
        Statement stmt = null;
        ResultSet rs = null;

        List<String> result = new LinkedList<>();

        DBUtil.dbConnection();
        stmt = DBUtil.con.createStatement();
        rs = stmt.executeQuery(query);

        // Store result in a list
        while (rs.next()) {
            result.add(rs.getString("DataType"));
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
