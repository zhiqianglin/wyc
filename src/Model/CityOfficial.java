package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Created by JARVIS on 4/13/17.
 */
public class CityOfficial {

    private SimpleStringProperty userName;
    private SimpleStringProperty email;
    private SimpleStringProperty city;
    private SimpleStringProperty state;
    private SimpleStringProperty title;
    private SimpleBooleanProperty approved;
    private SimpleBooleanProperty selected; //String??


    public CityOfficial(String userName, String email, String city, String state, String title){
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.title = new SimpleStringProperty(title);
        this.selected = new SimpleBooleanProperty();
        this.approved = null;
    }

    public CityOfficial(String userName, String email, String city, String state, String title, boolean approved){
        this(userName, email, city, state, title);
        this.approved = new SimpleBooleanProperty(approved);
    }



    public String getUserName() {
        return userName.get();
    }

//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getCity() {
        return city.get();
    }

//    public void setCity(String city) {
//        this.city = city;
//    }

    public String getState() {
        return state.get();
    }

//    public void setState(String state) {
//        this.state = state;
//    }

    public String getTitle() {
        return title.get();
    }

    public boolean isApproved() {
        return this.approved.get();
    }

    public final BooleanProperty selectedProperty() {
        return this.selected;
    }

    public final boolean isSelected() {
        return this.selected.get();
    }
    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }



//    public void setTitle(String title) {
//        this.title = title;
//    }

}
