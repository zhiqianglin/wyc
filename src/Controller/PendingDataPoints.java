package Controller;

import DAO.DataPointDAO;
import Model.DataPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zlin on 4/14/17.
 */
public class PendingDataPoints {

    public TableView<DataPoint> table;
    public TableColumn<DataPoint, Boolean> selected;
    public TableColumn locationName;
    public TableColumn dataType;
    public TableColumn dataValue;
    public TableColumn dateTime;
    ObservableList<DataPoint> data;



    public void initialize() {

//        data.add(new DataPoint("Atlanta", LocalDateTime.now(), "mold", 123));
//        data.add(new DataPoint("Roswell", LocalDateTime.of(2013, Month.JUNE, 15, 11, 32)
//                , "mole", 456));
//        data.add(new DataPoint("Dunwoody", LocalDateTime.of(2013, Month.APRIL, 29, 10, 39)
//                , "air", 456));
//        data.add(new DataPoint("Marietta", LocalDateTime.of(2017, Month.JANUARY, 13, 9, 40)
//                , "air", 456));
        try {
            data = FXCollections.observableList(DataPointDAO.queryAllDataPoints());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data point from database.\n" + e.getMessage());
        }
        table.setEditable(true);
        locationName.setCellValueFactory(
                new PropertyValueFactory<DataPoint, String>("locationName"));
        dataType.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("dataType"));
        dataValue.setCellValueFactory(new PropertyValueFactory<DataPoint, Integer>("dataValue"));


//        column.setCellValueFactory(param -> {
//            T value = param.getValue();
//            return new SimpleStringProperty(value.getName());
//        });
        dateTime.setCellValueFactory(new PropertyValueFactory<DataPoint, LocalDateTime>("dateTime"));

//        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>> {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> cd) {
//                Appointment a  = cd.getValue();
//
//                return Bindings.createStringBinding(() -> "the year: " + a.getDate().getYear(), a.dateProperty());
//            }

        selected.setCellValueFactory( new PropertyValueFactory<DataPoint,Boolean>( "selected" ) );

//        selected.setCellFactory( CheckBoxTableCell.forTableColumn( selected ) );

        selected.setCellFactory( new Callback<TableColumn<DataPoint,Boolean>, TableCell<DataPoint,Boolean>>()
        {
            @Override
            public TableCell<DataPoint,Boolean> call(TableColumn<DataPoint,Boolean> param )
            {
                return new CheckBoxTableCell<DataPoint,Boolean>()
                {
//                    {
//                        setAlignment( Pos.CENTER );
//                    }
                    @Override
                    public void updateItem( Boolean item, boolean empty )
                    {
                        if ( ! empty )
                        {
                            TableRow row = getTableRow();

                            if ( row != null )
                            {
                                int rowNo = row.getIndex();
                                TableView.TableViewSelectionModel sm = getTableView().getSelectionModel();

                                if ( item )  sm.select( rowNo );
                                else  sm.clearSelection( rowNo );
                            }
                        }

                        super.updateItem( item, empty );
                    }
                };
            }
        } );
        selected.setEditable( true );
        table.setItems(data);

    }
    public void accept(ActionEvent actionEvent) {

        for (DataPoint d : data) {
            if (d.isSelected()) {
                try {
                    DataPointDAO.acceptDataPoint(d);
                }
                catch (Exception e) {
                    Helper.showAlert("Error", "Unable to update data point:" + d.getLocationName() + " " + d.getDateTime() + "\n" + e.getMessage());
                }
            }
        }
        try {
            data = FXCollections.observableList(DataPointDAO.queryAllDataPoints());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data point from database.\n" + e.getMessage());
        }
        table.setItems(data);
    }

    public void reject(ActionEvent actionEvent) {
        for (DataPoint d : data) {
            if (d.isSelected()) {
                try {
                    DataPointDAO.rejectDataPoint(d);
                }
                catch (Exception e) {
                    Helper.showAlert("Error", "Unable to update data point:" + d.getLocationName() + " " + d.getDateTime() + "\n" + e.getMessage());
                }
            }
        }
        try {
            data = FXCollections.observableList(DataPointDAO.queryAllDataPoints());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data point from database.\n" + e.getMessage());
        }
        table.setItems(data);
    }

    public void back(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_ADMIN);
    }
}
