import Controller.Helper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
         String PATH = "fxml/";
//        Parent root = FXMLLoader.load(getClass().getResource(PATH + Helper.CHOOSE_FUNCTIONALITY_ADMIN));
//        Parent root = FXMLLoader.load(getClass().getResource(PATH + Helper.PENDING_DATA_POINTS));
//
//        Parent root = FXMLLoader.load(getClass().getResource(PATH + Helper.CHOOSE_FUNCTIONALITY_CITY_OFFICIAL));
//        Parent root = FXMLLoader.load(getClass().getResource(PATH + Helper.LOGIN));
//        Parent root = FXMLLoader.load(getClass().getResource(PATH + Helper.CITY_SCIENTIST));
//        Parent root = FXMLLoader.load(getClass().getResource(PATH + Helper.REGISTER));

//        Parent root = FXMLLoader.load(getClass().getResource("fxml/registration.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/addNewDataPoint.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/pendingDataPoints.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("./fxml/viewPOI.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/POIDetailController.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/POIReport.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/pendingDataPoints.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/pendingDataPoints.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/viewPOI.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("./fxml/POIDetail.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
