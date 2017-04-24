package Controller;

import DAO.CityOfficialDAO;
import Model.CityOfficial;
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

/**
 * Created by zlin on 4/14/17.
 */
public class PendingCityOfficial {

    public TableColumn<CityOfficial, Boolean> selected;
    public TableColumn username;
    public TableColumn email;
    public TableColumn city;
    public TableColumn state;
    public TableColumn title;
    public TableView<CityOfficial> table;
    ObservableList<CityOfficial> data;

    public void initialize() {
        table.setEditable(true);
        try {
            data = FXCollections.observableList(CityOfficialDAO.queryAllPendingAccounts());
            System.out.println(data);
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data point from database.\n" + e.getMessage());
        }
        username.setCellValueFactory(
                new PropertyValueFactory<CityOfficial, String>("userName"));
        email.setCellValueFactory(new PropertyValueFactory<CityOfficial, String>("email"));
        city.setCellValueFactory(new PropertyValueFactory<CityOfficial, String>("city"));
        state.setCellValueFactory(new PropertyValueFactory<CityOfficial, String>("state"));
        title.setCellValueFactory(new PropertyValueFactory<CityOfficial, String>("title"));
        selected.setCellValueFactory( new PropertyValueFactory<CityOfficial,Boolean>( "selected" ));

        selected.setCellFactory( new Callback<TableColumn<CityOfficial,Boolean>, TableCell<CityOfficial,Boolean>>()
        {
            @Override
            public TableCell<CityOfficial,Boolean> call(TableColumn<CityOfficial,Boolean> param )
            {
                return new CheckBoxTableCell<CityOfficial,Boolean>()
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
    public void back(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_ADMIN);
    }

    public void reject(ActionEvent actionEvent) {

        for (CityOfficial d : data) {
            if (d.isSelected()) {
                try {
                    CityOfficialDAO.rejectCityOfficialAccount(d);
                }
                catch (Exception e) {
                    Helper.showAlert("Error", "Unable to accept data point:" + d.getUserName() + "\n" + e.getMessage());
                }
            }
        }
        try {
            data = FXCollections.observableList(CityOfficialDAO.queryAllPendingAccounts());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data point from database.\n" + e.getMessage());
        }
        table.setItems(data);
    }

    public void accept(ActionEvent actionEvent) {
        for (CityOfficial d : data) {
            System.out.println(d.isSelected());
            if (d.isSelected()) {
                try {
                    CityOfficialDAO.acceptCityOfficialAccount(d);
                }
                catch (Exception e) {
                    Helper.showAlert("Error", "Unable to accept data point:" + d.getUserName() + "\n" + e.getMessage());
                }
            }
        }
        try {
            data = FXCollections.observableList(CityOfficialDAO.queryAllPendingAccounts());
        } catch (Exception e) {
            Helper.showAlert("Error", "Unable to retrieve data point from database.\n" + e.getMessage());
        }
        table.setItems(data);
    }
}
