package Controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;



/**
 * Created by zlin on 4/13/17.
 */
public class Helper {

    public final static String PATH = "../fxml/";
    public static final String REGISTER = "registration.fxml";
    public static final String LOGIN = "login.fxml";
    public static final String CITY_SCIENTIST = "addNewDataPoint.fxml";
    public static final String ADD_NEW_LOCATION = "addNewPOILocation.fxml";
    public static final String PENDING_CITY_OFFICIAL = "pendingCityOfficial.fxml";
    public static final String PENDING_DATA_POINTS = "pendingDataPoints.fxml";
    public static final String CHOOSE_FUNCTIONALITY_ADMIN = "ChooseFunctionalityAdmin.fxml";
    public static final String CHOOSE_FUNCTIONALITY_CITY_OFFICIAL = "ChooseFunctionalityCityOfficial.fxml";
    public static final String VIEW_POI = "viewPOI.fxml";
    public static final String POI_DETAIL = "POIDetail.fxml";
    public static final String POI_REPORT = "POIReport.fxml";

    public static void changeScene(ActionEvent actionEvent, Class c, String view) {
        try {
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/registration.fxml"));
            Parent root = FXMLLoader.load(c.getResource(PATH + view)); // Path

            Scene scene = new Scene(root);

            stageTheEventSourceNodeBelongs.setScene(scene);
            stageTheEventSourceNodeBelongs.show();
        }
        catch (IOException e) {
            showAlert("Error", "View not found");
        }
    }

    public static void changeSpecificScene(MouseEvent actionEvent, Class c, String view, String para) throws IOException{

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(c.getResource(PATH + view));
        Pane root = loader.load();
        POIDetailController pc = loader.getController();
        pc.setPOILocation(para);
        Scene scene = new Scene(root);
        stageTheEventSourceNodeBelongs.setScene(scene);
        stageTheEventSourceNodeBelongs.show();
    }


    /*
     *  Show alert
     */
    public static void showAlert(String title, String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);

        alert.showAndWait();
        return;
    }

    /*
     *  Convert a 2D list to a 1D list
     */
    public static List<String> changeDimension(List<List<String>> input){
        List<String> parsed = new LinkedList<>();
        ListIterator<List<String>> iterOut = input.listIterator();
        while (iterOut.hasNext()) {
            ListIterator<String> iterIn = iterOut.next().listIterator();
            while (iterIn.hasNext()){
                parsed.add(iterIn.next());
            }
        }
        return parsed;
    }
}
