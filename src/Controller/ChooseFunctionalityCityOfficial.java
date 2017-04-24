package Controller;

import javafx.event.ActionEvent;

/**
 * Created by zlin on 4/14/17.
 */
public class ChooseFunctionalityCityOfficial {
    public void searchPOI(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.VIEW_POI);

    }

    public void viewPOIReport(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.POI_REPORT);
    }

    public void logOut(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.LOGIN);
    }
}
