package Controller;

import DAO.DataPointDAO;
import DAO.DataTypeDAO;
import DAO.POIDAO;
import Model.DataPoint;
import Model.POI;
import Model.POIReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zlin on 4/14/17.
 */
public class POIReportController {
    public TableColumn locationNameCol;
    public TableColumn cityCol;
    public TableColumn stateCol;
    public TableColumn moldMinCol;
    public TableColumn moldAvgCol;
    public TableColumn moldMaxCol;
    public TableColumn aqMinCol;
    public TableColumn aqAvgCol;
    public TableColumn aqMaxCol;
    public TableColumn numDataPointCol;
    public TableColumn flaggedCol;
    public TableView reportTable;
    ObservableList<POIReport> report = FXCollections.observableArrayList();


    public void initialize() throws SQLException, ClassNotFoundException{
        //TODO: get the values of the locations DYNAMICALLY, UPDATE report
        List<POI> allPOI = POIDAO.findAllPOI();

        for (POI p : allPOI) {
            List<DataPoint> allDP = DataPointDAO.querySpecificDataPoints(p);
            String moldMin = "null", moldMax = "null";
            float moldTemp = 0, numMold = 0;
            String AQMin = "null", AQMax = "null";
            float AQTemp = 0, numAQ = 0;
//            System.out.println(allDP);
            for (DataPoint dp : allDP) {
//                System.out.println(dp.getDateTime());
                if (dp.getDataType().equals("Mold")) {
                    moldMin = (moldMin.equals("null") || Integer.parseInt(moldMin) >= dp.getDataValue()) ? dp.getDataValue().toString() : moldMin;
                    moldMax = (moldMax.equals("null") || Integer.parseInt(moldMax) <= dp.getDataValue()) ? dp.getDataValue().toString() : moldMax;
                    moldTemp += dp.getDataValue();
                    numMold ++;
                }
                else {
                    AQMin = (AQMin.equals("null") || Integer.parseInt(AQMin) >= dp.getDataValue()) ? dp.getDataValue().toString() : AQMin;
                    AQMax = (AQMax.equals("null") || Integer.parseInt(AQMax) <= dp.getDataValue()) ? dp.getDataValue().toString() : AQMax;
                    AQTemp += dp.getDataValue();
                    numAQ ++;
                    System.out.println(AQTemp);
                }
            }
            String moldAvg = numMold == 0 ? "null" : Float.toString(moldTemp / numMold);
            String AQAvg = numAQ == 0 ? "null" : Float.toString(AQTemp / numAQ);

            Boolean flagged = p.isFlagged();
            int numDP = allDP.size();

            POIReport tem = new POIReport(p.getLocationName(), p.getCity(), p.getState(), moldMin, moldAvg, moldMax, AQMin,
                    AQAvg, AQMax, numDP, flagged);
            report.add(tem);
        }

        locationNameCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("locationName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("city"));
        stateCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("state"));
        flaggedCol.setCellValueFactory(new PropertyValueFactory<POIReport, Boolean>("flagged"));
        moldMinCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("moldMin"));
        moldMaxCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("moldMax"));
        moldAvgCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("moldAvg"));
        aqMinCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("aqMin"));
        aqMaxCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("aqMax"));
        aqAvgCol.setCellValueFactory(new PropertyValueFactory<POIReport, String>("aqAvg"));
        numDataPointCol.setCellValueFactory(new PropertyValueFactory<POIReport, Integer>("numOfDataPoint"));
        reportTable.setItems(report);
    }

    public void back(ActionEvent actionEvent) {

        Helper.changeScene(actionEvent, this.getClass(), Helper.CHOOSE_FUNCTIONALITY_CITY_OFFICIAL);
    }
}
