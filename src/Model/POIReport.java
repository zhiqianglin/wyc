package Model;

import javafx.beans.property.*;

/**
 * Created by zlin on 4/15/17.
 */
public class POIReport {

    private final SimpleStringProperty locationName;
    private final SimpleStringProperty city;
    private final SimpleStringProperty state;
    private final SimpleStringProperty moldMin;
    private final SimpleStringProperty moldAvg;
    private final SimpleStringProperty moldMax;
    private final SimpleStringProperty aqMin;
    private final SimpleStringProperty aqAvg;
    private final SimpleStringProperty aqMax;
    private final SimpleIntegerProperty numOfDataPoint;
    //TODO: USE STRING?
    private SimpleBooleanProperty flagged;

    public POIReport(String locationName, String city, String state,
                       String moldMin, String moldAvg, String moldMax,
                       String AQMin, String AQAvg, String AQMax, int numOfDataPoint,
                       boolean flagged) {
        this.locationName = new SimpleStringProperty(locationName);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.moldMin = new SimpleStringProperty(moldMin);
        this.moldAvg = new SimpleStringProperty(moldAvg);
        this.moldMax = new SimpleStringProperty(moldMax);
        this.aqMin = new SimpleStringProperty(AQMin);
        this.aqAvg = new SimpleStringProperty(AQAvg);
        this.aqMax = new SimpleStringProperty(AQMax);
        this.numOfDataPoint = new SimpleIntegerProperty(numOfDataPoint);
        this.flagged = new SimpleBooleanProperty(flagged);
    }


    public String getLocationName() {
        return locationName.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public String getMoldMin() {
        return moldMin.get();
    }
    public String getMoldAvg() {
        return moldAvg.get();
    }
    public String getMoldMax() {
        return moldMax.get();
    }
    public String getAqMin() {
        return aqMin.get();
    }
    public String getAqAvg() {
        return aqAvg.get();
    }
    public String getAqMax(){
        return aqMax.get();
    }

    public Integer getNumOfDataPoint() {
        return this.numOfDataPoint.get();
    }

    public final boolean isFlagged() {
        return this.flagged.get();
    }

}
