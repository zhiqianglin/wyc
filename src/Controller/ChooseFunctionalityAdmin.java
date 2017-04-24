package Controller;

import javafx.event.ActionEvent;

/**
 * Created by zlin on 4/14/17.
 */
public class ChooseFunctionalityAdmin {
    public void viewPendingDataPoints(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.PENDING_DATA_POINTS);
    }

    public void viewPendingCityOfficialAccounts(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.PENDING_CITY_OFFICIAL);
    }

    public void logout(ActionEvent actionEvent) {
        Helper.changeScene(actionEvent, this.getClass(), Helper.LOGIN);
    }
}
