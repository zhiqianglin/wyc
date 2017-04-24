package Model;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zlin on 4/15/17.
 */
public class DataPoint {

//    public TextField dataValue;
//    public ChoiceBox locationName;
//    public ChoiceBox dataType;
//    public DatePicker date;
//    public JFXTimePicker time;

    private final SimpleStringProperty locationName;
    private final SimpleObjectProperty<Timestamp> dateTime;
    private final SimpleStringProperty dataType;
    private final SimpleIntegerProperty dataValue;

    //TODO: USE STRING?
    private SimpleBooleanProperty selected; //String??
    private SimpleBooleanProperty accepted;

    public DataPoint(String locationName, Timestamp dateTime, String dataType, int dataValue) {
        this.locationName = new SimpleStringProperty(locationName);
        this.dataType = new SimpleStringProperty(dataType);
        this.dataValue = new SimpleIntegerProperty(dataValue);
        this.dateTime = new SimpleObjectProperty<>(dateTime);
        this.selected = new SimpleBooleanProperty();
        this.accepted = null;
    }

    public DataPoint(String locationName, Timestamp dateTime, String dataType, int dataValue, boolean accepted) {
        this(locationName, dateTime, dataType, dataValue);
        this.accepted = new SimpleBooleanProperty(accepted);
    }

    public String getLocationName() {
        return locationName.get();
    }

    public String getDataType() {
        return dataType.get();
    }

    public Integer getDataValue() {
        return dataValue.get();
    }

    public Timestamp getDateTime() {
        return dateTime.get();
    }


//    public String getDateTime() {
//        return dateTime.get()
//                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//    }


    public final BooleanProperty selectedProperty() {
        return this.selected;
    }


    public final boolean isSelected() {
        return this.selected.get();
    }
    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }

    public final boolean isAccepted() {
        return this.accepted.get();
    }


    public String toString() {
        String out = this.getLocationName() + ", " + this.getDateTime() + ", " + this.getDataType() + ", "
                + this.getDataValue();
        return out;
    }


}
