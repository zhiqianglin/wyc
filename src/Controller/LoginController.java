package Controller;

import DAO.CityOfficialDAO;
import DAO.DBUtil;
import DAO.UserDAO;
import Model.CityOfficial;
import Model.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by zlin on 4/13/17.
 */
public class LoginController {

    public TextField username;
    public PasswordField password;

    public void login(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        if (username.getText().equals("") || password.getText().equals("")) {
            Helper.showAlert("Invalid credentials", "Please enter your username and password.\n\n " +
                    "If you don't have a account, click \"Register\" button");
            return;
        }
        User currUser = UserDAO.findUserByUsername(username.getText());
        if (currUser == null) {
            Helper.showAlert("Error", "No account found.");
            return;
        }
        if (!currUser.getPassword().equals((password.getText()))) {
            Helper.showAlert("Error", "Password does not match the username! Please enter again");
            return;
        }
        if (currUser.getUserType().equals("City Official")) {
            if (CityOfficialDAO.hasAcceptUser(currUser))
                Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_CITY_OFFICIAL);
            else {
                Helper.showAlert("Error", "This city official has not been approved by the admin.");
                return;
            }
        }

        if (currUser.getUserType().equals("Administrator")) {
            Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_ADMIN);
        }

        if (currUser.getUserType().equals("City Scientist")) {
            Helper.changeScene(actionEvent, this.getClass(), Helper.CITY_SCIENTIST);
        }
    }

    /*
     *  Register button event handler
     */
    public void register(ActionEvent actionEvent) throws IOException {
        Helper.changeScene(actionEvent, this.getClass(), Helper.REGISTER);
    }
}
