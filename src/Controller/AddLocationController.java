package Controller;

import DAO.CityStateDAO;
import DAO.POIDAO;
import Model.POI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sun.reflect.annotation.ExceptionProxy;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zlin on 4/14/17.
 */
public class AddLocationController {


    public TextField locationName;
    public ChoiceBox city;
    public ChoiceBox state;
    public TextField zipCode;

    public void initialize() throws SQLException, ClassNotFoundException{
        // Configuration for the state and city choice boxes.
        List<List<String>> inputState = CityStateDAO.findAllState();
        // Convert query result to a 1D LinkedList
        List<String> parsed_state = new LinkedList<>();
        ListIterator<List<String>> iter1 = inputState.listIterator();
        while (iter1.hasNext()) {
            ListIterator<String> iter2 = iter1.next().listIterator();
            while (iter2.hasNext()){
                parsed_state.add(iter2.next());
            }
        }
        // Fill the state choicebox
        ObservableList stateData = FXCollections.observableList(parsed_state);
        state.getItems().clear();
        state.setItems(stateData);
        state.getSelectionModel().selectFirst();

        // Get the default city of the default state
        String curState = state.getSelectionModel().getSelectedItem().toString();
        List<List<String>> curCities = CityStateDAO.findAllCity(curState);
        // Convert query result to a 1D LinkedList
        List<String> parsed_cities = new LinkedList<>();
        iter1 = curCities.listIterator();
        while (iter1.hasNext()) {
            ListIterator<String> iter2 = iter1.next().listIterator();
            while (iter2.hasNext()){
                parsed_cities.add(iter2.next());
            }
        }
        // Fill the state choicebox
        ObservableList cityData = FXCollections.observableList(parsed_cities);
        city.getItems().clear();
        city.setItems(cityData);
        city.getSelectionModel().selectFirst();

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
                List<String> parsed_cities = new LinkedList<>();
                ListIterator<List<String>> iter1 = curCities.listIterator();
                while (iter1.hasNext()) {
                    ListIterator<String> iter2 = iter1.next().listIterator();
                    while (iter2.hasNext()){
                        parsed_cities.add(iter2.next());
                    }
                }
                // Fill the state choicebox
                ObservableList cityData = FXCollections.observableList(parsed_cities);
                city.getItems().clear();
                city.setItems(cityData);
                city.getSelectionModel().selectFirst();
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.CITY_SCIENTIST);
    }

    public void submit(ActionEvent actionEvent) {
        if (locationName.getText().isEmpty()) {
            Helper.showAlert("Error", "Please enter location name");
            return;
        }

        if (zipCode.getText().isEmpty()) {
            Helper.showAlert("Error", "Please enter zipcode");
            return;
        }

        POI curr = new POI(locationName.getText(), city.getSelectionModel().getSelectedItem().toString(),
                state.getSelectionModel().getSelectedItem().toString(), zipCode.getText(), false, null);
        try {
            POIDAO.insertLocation(curr);
            Helper.showAlert("Success", "New POI Location created");
        } catch (Exception e) {
            Helper.showAlert("Error", e.getMessage());
        }
    }
}
