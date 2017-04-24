package Model;

import java.sql.Timestamp;

/**
 * Created by JARVIS on 4/22/17.
 */
public class POIFilter {
    private String POIName;
    private String city;
    private String state;
    private String zipCode;
    private int flagged;
    private Timestamp start;
    private Timestamp end;

    public POIFilter(String POIName, String city, String state, String zipCode, Boolean flagged, Timestamp start, Timestamp end){
        this.POIName = POIName.equals("Not selected") ? "%" : POIName;
        this.city = city.equals("Not selected") ? "%" : city;
        this.state = state.equals("Not selected") ? "%" : state;
        this.zipCode = (zipCode == null || zipCode.isEmpty()) ? "%" : zipCode;
        this.flagged = flagged ? 1 : 0;
        this.start = start;
        this.end = end;
    }

    public String getPOIName() {
        return POIName;
    }

    public void setPOIName(String POIName) {
        this.POIName = POIName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getFlagged() {
        return flagged;
    }

    public void setFlagged(int flagged) {
        this.flagged = flagged;
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


}
