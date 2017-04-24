package Controller;

import java.sql.SQLException;
import java.util.*;

import DAO.CityOfficialDAO;
import DAO.DBUtil;
import Model.CityOfficial;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import DAO.CityStateDAO;
import Model.User;

import DAO.UserDAO;

import javax.swing.*;


public class RegisterController {
    @FXML
    private Pane registration;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;
    @FXML
    private ChoiceBox userType;
    @FXML
    private ChoiceBox city;
    @FXML
    private ChoiceBox state;
    @FXML
    private TextField title;
    @FXML
    private Button create;
    @FXML
    private Button cancel;

    @FXML
    private void initialize () throws SQLException, ClassNotFoundException{

        // Configuration for the state and city choice boxes.
        List<List<String>> inputState = CityStateDAO.findAllState();
        List<String> parsed_state = Helper.changeDimension(inputState);
        // Fill the state choicebox
        ObservableList stateData = FXCollections.observableList(parsed_state);
        state.getItems().clear();
        state.setItems(stateData);
        state.getSelectionModel().selectFirst();

        // Get the default city of the default state
        String curState = state.getSelectionModel().getSelectedItem().toString();
        List<List<String>> curCities = CityStateDAO.findAllCity(curState);
        // Convert query result to a 1D LinkedList
        List<String> parsed_city = Helper.changeDimension(curCities);
        // Fill the state choicebox
        ObservableList cityData = FXCollections.observableList(parsed_city);
        city.getItems().clear();
        city.setItems(cityData);
        city.getSelectionModel().selectFirst();

        // Add listener to state, change city list accordingly
        state.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String curState = state.getItems().get((Integer) newValue).toString();
                List<List<String>> curCities = null;
                try {
                    curCities = CityStateDAO.findAllCity(curState);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // Convert query result to a 1D LinkedList
                List<String> parsed_cities = Helper.changeDimension(curCities);
                // Fill the city choicebox
                ObservableList cityData = FXCollections.observableList(parsed_cities);
                city.getItems().clear();
                city.setItems(cityData);
                city.getSelectionModel().selectFirst();
            }
        });

        // Add listener to user type. Disable corresponding field if it is not "City Officials"
        userType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String curType = userType.getItems().get((Integer) newValue).toString();
                if (curType.equals("City Scientist")) {
                    city.setDisable(true);
                    state.setDisable(true);
                    title.clear();
                    title.setDisable(true);
                }
                else {
                    city.setDisable(false);
                    state.setDisable(false);
                    title.setDisable(false);
                }
            }
        });
    }

    /*
     *  The handler for the create button. Check the validity of each field and pass the
     *  new record into the database.
     */
    @FXML
    public void handleCreateClick(ActionEvent ae){

        // Check the validity of all input fields
        if (username.getText().trim().isEmpty()) {
             Helper.showAlert("Invalid input", "Username cannot be empty");
            return;
        }
        if (email.getText().trim().isEmpty()) {
            Helper.showAlert("Invalid input", "Email cannot be empty");
            return;
        }
        if (!email.getText().contains("@")) {
            Helper.showAlert("Invalid input", "Please use a valid email address");
            return;
        }
        if (password.getText().trim().isEmpty()) {
            Helper.showAlert("Invalid input", "Password cannot be empty");
            return;
        }
        if (password2.getText().trim().isEmpty()) {
            Helper.showAlert("Invalid input", "Please confirm your password");
            return;
        }
        if (!password.getText().equals(password2.getText())) {
            Helper.showAlert("Invalid input", "Passwords do not match. Please double check");
            return;
        }
        if (userType.getSelectionModel().getSelectedItem().toString().equals("City Officials") && title.getText().trim().isEmpty()) {
            Helper.showAlert("Invalid input", "Title cannot be empty for city officials");
            return;
        }

        User temp = new User(username.getText().trim(), email.getText().trim(), password.getText().trim(), userType.getSelectionModel().getSelectedItem().toString().trim());

        try {
            UserDAO.insertUser(temp);
        } catch (ClassNotFoundException e) {
            Helper.showAlert("Error", e.getMessage());
            return;

        } catch (SQLException e) {
            Helper.showAlert("Database Error", e.getMessage());
            return;
        }

        if (temp.getUserType().equals("City Official")) {
            CityOfficial cityOfficial = new CityOfficial(username.getText(), email.getText(), city.getSelectionModel().getSelectedItem().toString(), state.getSelectionModel().getSelectedItem().toString(), title.getText());
            try {
                CityOfficialDAO.insertCityOfficial(cityOfficial);
                Helper.showAlert("Congratulation", "New account registered. Please wait for approval.");
            } catch (Exception e) {
                Helper.showAlert("Database Error", e.getMessage());
                //TODO: DELETE USER INSERTED
                System.out.println("Not able to insert city_official, NEED TO DELETE USER INSERTED");
            }

        } else {
            Helper.showAlert("Congratulation", "New account registered");
        }

        Helper.changeScene(ae, this.getClass(), Helper.LOGIN);
    }

    /*
     *  The handler for the cancel button. Simply close the window.
     */
    @FXML
    public void handleCancelClick(ActionEvent e) throws SQLException, ClassNotFoundException{
        Helper.changeScene(e, this.getClass(), Helper.LOGIN);
    }
}
