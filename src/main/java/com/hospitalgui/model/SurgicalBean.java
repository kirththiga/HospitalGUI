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
 * for properties of surgical.
 * 
 *  
 */
public class SurgicalBean {
    
    private IntegerProperty id;
    private IntegerProperty patientID;
    private ObjectProperty<Timestamp> dateOfSurgery;
    private StringProperty surgery;
    private ObjectProperty<BigDecimal> roomFee;
    private ObjectProperty<BigDecimal> surgeonFee;
    private ObjectProperty<BigDecimal> supplies;

    public SurgicalBean() {
        super();
        id = new SimpleIntegerProperty();
        patientID = new SimpleIntegerProperty();
        dateOfSurgery = new SimpleObjectProperty<>();
        surgery = new SimpleStringProperty();
        roomFee = new SimpleObjectProperty<>();
        surgeonFee = new SimpleObjectProperty<>();
        supplies = new SimpleObjectProperty<>();
    
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
    
    public void setDateOfSurgery(Timestamp dateOfSurgery) {
        this.dateOfSurgery.set(dateOfSurgery);
    }
    
    public Timestamp getDateOfSurgery() {
        return dateOfSurgery.get();
    }

    public ObjectProperty<Timestamp> getDateOfSurgeryProperty() {
        return dateOfSurgery;
    }
    
    public void setSurgery(String surgery) {
        this.surgery.set(surgery);
    }
    
    public String getSurgery() {
        return surgery.get();
    }

    public StringProperty getSurgeryProperty() {
        return surgery;
    }

    public void setRoomFee(BigDecimal roomFee) {
        roomFee.setScale(2, ROUND_UP);
        this.roomFee.set(roomFee);
    }
        
    public BigDecimal getRoomFee() {
        roomFee.get().setScale(2, ROUND_UP);
        return roomFee.get();
    }

    public ObjectProperty<BigDecimal> getRoomFeeProperty() {
        return roomFee;
    }
    
    public void setSurgeonFee(BigDecimal surgeonFee) {
        surgeonFee.setScale(2, ROUND_UP);
        this.surgeonFee.set(surgeonFee);
    }
        
    public BigDecimal getSurgeonFee() {
        surgeonFee.get().setScale(2, ROUND_UP);
        return surgeonFee.get();
    }

    public ObjectProperty<BigDecimal> getSurgeonFeeProperty() {
        return surgeonFee;
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

    @Override
    public String toString() {
        return "SurgicalBean{" + "id=" + id + ", patientID=" + patientID + ", dateOfSurgery=" + dateOfSurgery + ", surgery=" + surgery + ", roomFee=" + roomFee + ", surgeonFee=" + surgeonFee + ", supplies=" + supplies + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id.get());
        hash = 97 * hash + Objects.hashCode(this.patientID.get());
        hash = 97 * hash + Objects.hashCode(this.dateOfSurgery.get());
        hash = 97 * hash + Objects.hashCode(this.surgery.get());
        hash = 97 * hash + Objects.hashCode(this.roomFee.get());
        hash = 97 * hash + Objects.hashCode(this.surgeonFee.get());
        hash = 97 * hash + Objects.hashCode(this.supplies.get());
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
        final SurgicalBean other = (SurgicalBean) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        if (!Objects.equals(this.patientID.get(), other.patientID.get())) {
            return false;
        }
        if (!Objects.equals(this.dateOfSurgery.get(), other.dateOfSurgery.get())) {
            return false;
        }
        if (!Objects.equals(this.surgery.get(), other.surgery.get())) {
            return false;
        }
        if (!Objects.equals(this.roomFee.get(), other.roomFee.get())) {
            return false;
        }
        if (!Objects.equals(this.surgeonFee.get(), other.surgeonFee.get())) {
            return false;
        }
        if (!Objects.equals(this.supplies.get(), other.supplies.get())) {
            return false;
        }
        return true;
    }

    
}
