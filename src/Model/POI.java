package Model;

import javafx.beans.property.*;
import sun.util.resources.LocaleData;

import java.sql.Date;
import java.sql.SQLData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by zlin on 4/15/17.
 */
public class POI {

    private final SimpleStringProperty locationName;
    private final SimpleStringProperty city;
    private final SimpleStringProperty state;
    private final SimpleStringProperty zipCode;
    private SimpleBooleanProperty flagged;
    private SimpleObjectProperty<Date> dateFlagged;

    public POI(String locationName, String city, String state, String zipCode, boolean flag, Date dateFlagged ) {
        this.locationName = new SimpleStringProperty(locationName);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.zipCode = new SimpleStringProperty(zipCode);
        this.flagged = new SimpleBooleanProperty(flag);
        this.dateFlagged = new SimpleObjectProperty<>(dateFlagged);
    }

//    public POI(List<String> poiInfo) {
//        this(poiInfo.get(0), poiInfo.get(1), poiInfo.get(2), Integer.parseInt(poiInfo.get(3)),
//                 Boolean.parseBoolean(poiInfo.get(4)), new LocalDate(poiInfo.get(5)))
//    }

    public String getLocationName() {
        return locationName.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    public String getZipCode() {
        return zipCode.get();
    }


    public final BooleanProperty selectedProperty() {
        return this.flagged;
    }


    public final boolean isFlagged() {
        return this.flagged.get();
    }
    public final void setFlagged(final boolean flagged) {
        this.selectedProperty().set(flagged);
    }

    public Date getDateFlagged() {
        return this.dateFlagged.get();
    }

    public void setDateFlagged(Date dateFlagged) {
        this.dateFlagged.set(dateFlagged);
    }
    /*

    public final BooleanProperty selectedProperty() {
        return this.selected;
    }


    public final boolean isSelected() {
        return this.selected.get();
    }
    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }
    }
     */

}
