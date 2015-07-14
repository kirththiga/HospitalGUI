package com.hospitalgui.model;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;
import java.sql.Timestamp;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class contains the setter, getter, and getter methods  
 * for properties of inpatient.
 * 
 *  
 */
public class InPatientBean {

    private IntegerProperty id;
    private IntegerProperty patientID;
    private ObjectProperty<Timestamp> dateOfStay;
    private StringProperty roomNumber;
    private ObjectProperty<BigDecimal> dailyRate;
    private ObjectProperty<BigDecimal> supplies;
    private ObjectProperty<BigDecimal> services;

    public InPatientBean() {
        super();
        id = new SimpleIntegerProperty();
        patientID = new SimpleIntegerProperty();
        dateOfStay = new SimpleObjectProperty<>();
        roomNumber = new SimpleStringProperty();
        dailyRate = new SimpleObjectProperty<>();
        supplies = new SimpleObjectProperty<>();
        services = new SimpleObjectProperty<>();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void setPatientID(int patientID) {
        this.patientID.set(patientID);
    }
    
    public int getPatientID() {
        return patientID.get();
    }
    
    public IntegerProperty getPatientIDProperty() {
        return patientID;
    }

    public void setDateOfStay(Timestamp dateOfStay) {
        this.dateOfStay.set(dateOfStay);
    }

    public Timestamp getDateOfStay() {
        return dateOfStay.get();
    }

    public ObjectProperty<Timestamp> getDateOfStayProperty() {
        return dateOfStay;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public StringProperty getRoomNumberProperty() {
        return roomNumber;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        dailyRate.setScale(2, ROUND_UP);
        this.dailyRate.set(dailyRate);
    }

    public BigDecimal getDailyRate() {
        dailyRate.get().setScale(2, ROUND_UP);
        return dailyRate.get();
    }

    public ObjectProperty<BigDecimal> getDailyRateProperty() {
        return dailyRate;
    }

    public void setSupplies(BigDecimal supplies) {
        supplies.setScale(2, ROUND_UP);
        this.supplies.set(supplies);
    }

    public BigDecimal getSupplies() {
        supplies.get().setScale(2, ROUND_UP);
        return supplies.get();
    }

    public ObjectProperty<BigDecimal> getSuppliesProperty() {
        return supplies;
    }

    public void setServices(BigDecimal services) {
        services.setScale(2, ROUND_UP);
        this.services.set(services);
    }
 
    public BigDecimal getServices() {
        services.get().setScale(2, ROUND_UP);
        return services.get();
    }

    public ObjectProperty<BigDecimal> getServicesProperty() {
        return services;
    }

    @Override
    public String toString() {
        return "InPatientBean{" + "id=" + id + ", patientID=" + patientID + ", dateOfStay=" + dateOfStay + ", roomNumber=" + roomNumber + ", dailyRate=" + dailyRate + ", supplies=" + supplies + ", services=" + services + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id.get());
        hash = 97 * hash + Objects.hashCode(this.patientID.get());
        hash = 97 * hash + Objects.hashCode(this.dateOfStay.get());
        hash = 97 * hash + Objects.hashCode(this.roomNumber.get());
        hash = 97 * hash + Objects.hashCode(this.dailyRate.get());
        hash = 97 * hash + Objects.hashCode(this.supplies.get());
        hash = 97 * hash + Objects.hashCode(this.services.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InPatientBean other = (InPatientBean) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        if (!Objects.equals(this.patientID.get(), other.patientID.get())) {
            return false;
        }
        if (!Objects.equals(this.dateOfStay.get(), other.dateOfStay.get())) {
            return false;
        }
        if (!Objects.equals(this.roomNumber.get(), other.roomNumber.get())) {
            return false;
        }
        if (!Objects.equals(this.dailyRate.get(), other.dailyRate.get())) {
            return false;
        }
        if (!Objects.equals(this.supplies.get(), other.supplies.get())) {
            return false;
        }
        if (!Objects.equals(this.services.get(), other.services.get())) {
            return false;
        }
        return true;
    }

    
}
