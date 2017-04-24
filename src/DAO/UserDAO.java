package DAO;

/**
 * Created by JARVIS on 4/14/17.
 */
import Controller.Helper;
import Controller.RegisterController;
import Model.User;

import java.sql.SQLException;
import java.util.*;

public class UserDAO {

    private static final String USER_TABLE = "user";
    private static final String USER_NAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER_TYPE = "usertype";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String TITLE = "title";

    /*
     *  Search user by username
     */
    public static void findByUsername (String username) throws SQLException, ClassNotFoundException {
        String queryStatement = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = " + username;

        try {
            List<List<String>> result = DBUtil.dbExcuteQuery(queryStatement);
            // Convert result to a 1D LinkedList
            ListIterator<List<String>> iter1 = result.listIterator();
            while(iter1.hasNext()) {
                ListIterator<String> iter2 = iter1.next().listIterator();
                while(iter2.hasNext())
                    System.out.println(iter2.next());
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static User findUserByUsername(String username) {
        String queryStatement = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = '" + username + "'";
        System.out.println("find user by name: " + queryStatement);
        User user = null;
        try {
            List<List<String>> result = DBUtil.dbExcuteQuery(queryStatement);
            if (result.size() == 0) {
                System.out.println("Not found");
            } else {
                List<String> userInfo = result.get(0);
//                System.out.println(userInfo.size());
                user = new User(userInfo);
            }
        }
        catch (Exception e) {
            Helper.showAlert("Error", e.getMessage());
//            return user;
        }
        return user;
    }

    public static boolean hasUser(String userName) {
        String queryStatement = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = '" + userName + "'";
        try {
            List<List<String>> result = DBUtil.dbExcuteQuery(queryStatement);
            if (result.size() == 0) {
                return false;
            } else {
                List<String> userInfo = result.get(0);
                for (String info : userInfo) {
                    System.out.println(info);
                }
                return true;
            }
        }
        catch (Exception e) {
            Helper.showAlert("Error", e.getMessage());
        }
        return false;
    }
//
//    /*
//     *  Insert a new user
//     */
//    public static String insertUser(User newUser) throws SQLException, ClassNotFoundException {
//        String insertStatement = "INSERT INTO " + USER_TABLE + "( " +  USER_NAME + ", " + EMAIL + ", " +
//                            PASSWORD + ", " + USER_TYPE + ", " + CITY + ", " + STATE + ", " + TITLE +
//                            ") VALUES ('" + newUser.getUserName() + "', '" + newUser.getEmail() + "', '" +
//                            newUser.getPassword() + "', '" + newUser.getUserType() + "', '" + newUser.getCity()
//                            + "', '" + newUser.getState() + "', '" + newUser.getTitle() + "')";
//        int updatedRows = 0;
//
//        try {
//            updatedRows = DBUtil.dbExecuteUpdate(insertStatement);
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//
//        if (updatedRows != 0)
//            return "New record added";
//        else
//            return "The username has been registered. Please choose another one.";
//    }



    /*
     *  Insert a new user /NEW
     */
    public static boolean insertUser(User newUser) throws ClassNotFoundException, SQLException{
        String insertStatement = "INSERT INTO " + USER_TABLE + "(" +  USER_NAME + ", " + EMAIL + ", " +
                PASSWORD + ", " + USER_TYPE +
                ") VALUES ('" + newUser.getUserName() + "', '" + newUser.getEmail() + "', '" +
                newUser.getPassword() + "', '" + newUser.getUserType() +  "')";

        System.out.println("insert new user: " + insertStatement);
        int updatedRows = 0;

        updatedRows = DBUtil.dbExecuteUpdate(insertStatement);

        if (updatedRows != 0)
            return true;
        else
            return false;
    }

    public static boolean deleteUser(User user){
        //
        return false;
    }
}
