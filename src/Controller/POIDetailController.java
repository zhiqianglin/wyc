package Controller;

import DAO.DataTypeDAO;
import Model.DataPoint;
import Model.DataPointFilter;
import Model.POIFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import DAO.*;

/**
 * Created by zlin on 4/14/17.
 */
public class POIDetailController {


    public ChoiceBox dataType;
    public TextField dataValueMin;
    public TextField dataValueMax;
    public DatePicker dateMin;
    public DatePicker dateMax;
    public TableView dataTable;
    public String POILocation;
    public TableColumn dataTypeCol;
    public TableColumn dataValueCol;
    public TableColumn dateTimeCol;
    ObservableList<DataPoint> data;
    public String param;

    public POIDetailController(String param) {
        this.param = param;
    }

    public void initialize() throws SQLException, ClassNotFoundException{

        // for TEST ONLY!!!!
        POILocation = param;

        System.out.println(POILocation);
        List<String> dataTypeResult = DataTypeDAO.queryDataType();
        dataTypeResult.add(0, "Not selected");
        ObservableList dt = FXCollections.observableList(dataTypeResult);
        dataType.getItems().clear();
        dataType.setItems(dt);
        dataType.getSelectionModel().selectFirst();

        DataPointFilter d = new DataPointFilter(POILocation, dataType.getSelectionModel().getSelectedItem().toString().trim(),
                dataValueMin.getText().toString().trim(),
                dataValueMax.getText().toString().trim(), Timestamp.valueOf("1000-01-01 00:00:00"), Timestamp.valueOf("2100-01-01 00:00:00"));
        data = FXCollections.observableList(DataPointDAO.filterDataPoint(d));

        //TODO: GET DYNAMIC DATA
        dataType.getSelectionModel().selectFirst();
        dataTypeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("dataType"));
        dataValueCol.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("dataValue"));
        //TODO: UPDATE DATETIME FORMAT
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<DataPoint, LocalDateTime>("dateTime"));
        dataTable.setItems(data);

        System.out.println(POILocation);

    }
    public void applyFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        Timestamp min = dateMin.getValue() == null ? Timestamp.valueOf("1000-01-01 00:00:00") : Timestamp.valueOf(dateMin.getValue().atStartOfDay());
        Timestamp max = dateMax.getValue() == null ? Timestamp.valueOf("2100-01-01 00:00:00") : Timestamp.valueOf(dateMax.getValue().atStartOfDay());

        if (min.after(max)) {
            Helper.showAlert("Invalid time input", "Please enter the dates in correct order");
        } else {
            System.out.println(dataValueMax.getText().toString().trim());
            DataPointFilter d = new DataPointFilter(POILocation, dataType.getSelectionModel().getSelectedItem().toString().trim(),
                    dataValueMin.getText().toString().trim(),
                    dataValueMax.getText().toString().trim(), min, max);
            data = FXCollections.observableList(DataPointDAO.filterDataPoint(d));
            dataTable.setItems(data);
        }
    }

    public void resetFilter(ActionEvent actionEvent) {
        dataType.setValue("Not selected");
        dataValueMin.clear();
        dataValueMax.clear();
        dateMin.setValue(null);
        dateMax.setValue(null);
    }

    public void back(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_CITY_OFFICIAL);
    }

    public void flag(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        boolean result = POIDAO.updateFlag(POILocation);
//        System.out.println(result);
    }

    public String getPOILocation() {
        return POILocation;
    }

    public void setPOILocation(String POILocation) {
        this.POILocation = POILocation;
    }
}
