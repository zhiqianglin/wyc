package Controller;

import DAO.DataPointDAO;
import DAO.DataTypeDAO;
import DAO.POIDAO;
import Model.DataPoint;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import com.jfoenix.controls.JFXDatePicker;


/**
 * Created by zlin on 4/13/17.
 */
public class AddDataPointController {

    public TextField dataValue;
    public ChoiceBox locationName;
    public ChoiceBox dataType;
    public DatePicker date;
    public JFXTimePicker time;


    public void initialize() {

        ObservableList<String> dataTypes = null;
        ObservableList<String> locationNames = null;

        try {
            locationNames = FXCollections.observableList(POIDAO.queryAllLocationNames());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve location names from database.\n" + e.getMessage());
        }

        //get the DataType Dynamically
        try {
            dataTypes = FXCollections.observableList(DataTypeDAO.queryDataType());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data types from database.\n" + e.getMessage());

        }
        dataType.setItems(dataTypes);
        dataType.getSelectionModel().selectFirst();

        locationName.setItems(locationNames);
        locationName.getSelectionModel().selectFirst();

        date.setValue(LocalDate.now());
        time.setValue(LocalTime.now());

    }

    public void submit(ActionEvent actionEvent) {
        LocalDateTime a = date.getValue().atTime(time.getValue());
//        System.out.println(a);
        int value;
        try {
            value = Integer.parseInt(dataValue.getText());
        }
        catch (Exception e){
            Helper.showAlert("Wrong value", "Please enter valid data value");
            return;
        }

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(date.getValue(), time.getValue()));

        DataPoint dp = new DataPoint(locationName.getValue().toString(), timestamp, dataType.getValue().toString(), value);
        System.out.println(dp);
        try {
            DataPointDAO.insertDataPoint(dp);
            Helper.showAlert("Success", "Data point created, please wait for approval");
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to create datapoint\n" + e.getMessage());
        }
//        Helper.changeScene(actionEvent, this.getClass(), Helper.LOGIN);
    }

    public void back(ActionEvent actionEvent) {
        System.out.println("Going back. To be implemented");
        Helper.changeScene(actionEvent, this.getClass(), Helper.LOGIN);
    }

    public void addNewLocation(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.ADD_NEW_LOCATION);
    }


}
