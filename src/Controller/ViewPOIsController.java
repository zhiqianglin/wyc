package Controller;

import DAO.CityStateDAO;
import DAO.POIDAO;
import Model.POI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import Model.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by zlin on 4/14/17.
 */
public class ViewPOIsController {
    public TextField zipCode;
    public ChoiceBox locationName;
    public ChoiceBox city;
    public ChoiceBox state;
    public CheckBox flagged;
    public DatePicker dataFlaggedMin;
    public DatePicker dataFlaggedMax;
    public TableColumn locationNameCol;
    public TableColumn cityCol;
    public TableColumn stateCol;
    public TableColumn zipCodeCol;
    public TableColumn flaggedCol;
    public TableColumn dateFlaggedCol;
    public TableView filteredTable;
    ObservableList<POI> data;


    public void initialize() throws SQLException, ClassNotFoundException {
        List<String> POINames = POIDAO.queryAllLocationNames();
        POINames.add(0, "Not selected");
        // Fill the state choicebox
        ObservableList POIData = FXCollections.observableList(POINames);
        locationName.getItems().clear();
        locationName.setItems(POIData);
        locationName.getSelectionModel().selectFirst();

        List<String> allStates = CityStateDAO.queryAllState();
        allStates.add(0, "Not selected");
        ObservableList stateData = FXCollections.observableList(allStates);
        state.getItems().clear();
        state.setItems(stateData);
        state.getSelectionModel().selectFirst();

        List<String> allCities = CityStateDAO.queryAllCity();
        allCities.add(0, "Not selected");
        ObservableList cityData = FXCollections.observableList(allCities);
        city.getItems().clear();
        city.setItems(cityData);
        city.getSelectionModel().selectFirst();

//        Timestamp min = Timestamp.valueOf(dataFlaggedMin.getValue().atStartOfDay());
//        Timestamp max = Timestamp.valueOf(dataFlaggedMax.getValue().atStartOfDay());
        dataFlaggedMin.setDisable(true);
        dataFlaggedMax.setDisable(true);
        flagged.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (new_val){
                    dataFlaggedMin.setDisable(false);
                    dataFlaggedMax.setDisable(false);
                }
                else {
                    dataFlaggedMin.setDisable(true);
                    dataFlaggedMax.setDisable(true);
                }
            }
        });

        locationNameCol.setCellValueFactory(new PropertyValueFactory<POI, String>("locationName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<POI, String>("city"));
        stateCol.setCellValueFactory(new PropertyValueFactory<POI, String>("state"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<POI, Integer>("zipCode"));
        flaggedCol.setCellValueFactory(new PropertyValueFactory<POI, Boolean>("flagged"));
        dateFlaggedCol.setCellValueFactory(new PropertyValueFactory<POI, Date>("data flagged"));

        filteredTable.setItems(data);

        filteredTable.setRowFactory(tv -> {
            TableRow<POI> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    String para = row.getItem().getLocationName();
                    Stage stage = new Stage(StageStyle.DECORATED);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(Helper.PATH + Helper.POI_DETAIL));
                    // Create a controller instance
                    POIDetailController controller = new POIDetailController(para);
                    // Set it in the FXMLLoader
                    loader.setController(controller);
                    try {
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            });
            return row;
        });
    }

    //TODO: Communicate with the database
    public void applyFilter(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        Timestamp min = dataFlaggedMin.getValue() == null ? Timestamp.valueOf("1000-01-01 00:00:00") : Timestamp.valueOf(dataFlaggedMin.getValue().atStartOfDay());
        Timestamp max = dataFlaggedMax.getValue() == null ? Timestamp.valueOf("2100-01-01 00:00:00") : Timestamp.valueOf(dataFlaggedMax.getValue().atStartOfDay());

        if (min.after(max)) {
            Helper.showAlert("Invalid time input", "Please enter the dates in correct order");
        }
        else {
            POIFilter f = new POIFilter(locationName.getSelectionModel().getSelectedItem().toString().trim(),
                    city.getSelectionModel().getSelectedItem().toString().trim(),
                    state.getSelectionModel().getSelectedItem().toString().trim(),
                    zipCode.getText().toString().trim(),
                    flagged.isSelected(),
                    min, max);

        data = FXCollections.observableList(POIDAO.filterPOI(f));
        filteredTable.setItems(data);
        }
    }

    public void resetFilter(ActionEvent actionEvent) {
        dataFlaggedMin.setValue(null);
        dataFlaggedMax.setValue(null);
        locationName.setValue("Not selected");
        city.setValue("Not selected");
        state.setValue("Not selected");
        zipCode.clear();
        flagged.setSelected(false);
    }

    public void back(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_CITY_OFFICIAL);
    }
}
