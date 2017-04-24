package Model;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by JARVIS on 4/22/17.
 */
public class DataPointFilter {

    private String POILocation;
    private String dataType;
    private String minValue;
    private String maxValue;
    private Timestamp start;
    private Timestamp end;

    public DataPointFilter(String POILocation, String dataType, String minValue, String maxValue, Timestamp start, Timestamp end) {
        this.POILocation = POILocation;
        this.dataType = dataType == "Not selected" ? "%" : dataType;
        this.minValue = (minValue == null || minValue.isEmpty()) ? "0" : minValue;
        this.maxValue = (maxValue == null || maxValue.isEmpty()) ? "1000000" : maxValue;
        this.start = start;
        this.end = end;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getPOILocation() {
        return POILocation;
    }

    public void setPOILocation(String POILocation) {
        this.POILocation = POILocation;
    }
}
