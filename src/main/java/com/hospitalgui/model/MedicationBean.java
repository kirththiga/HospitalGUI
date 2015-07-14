package com.hospitalgui.model;

import java.math.BigDecimal;
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
 * for properties of medication.
 * 
 *  
 */
public class MedicationBean {

    private IntegerProperty id;
    private IntegerProperty patientID;
    private ObjectProperty<Timestamp> dateOfMed;
    private StringProperty med;
    private ObjectProperty<BigDecimal> unitCost;
    private ObjectProperty<BigDecimal> units;

    public MedicationBean() {
        super();
        id = new SimpleIntegerProperty();
        patientID = new SimpleIntegerProperty();
        dateOfMed = new SimpleObjectProperty<>();
        med = new SimpleStringProperty();
        unitCost = new SimpleObjectProperty<>();
        units = new SimpleObjectProperty<>();      
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

    public void setDateOfMed(Timestamp dateOfMed) {
        this.dateOfMed.set(dateOfMed);
    }
        
    public Timestamp getDateOfMed() {
        return dateOfMed.get();
    }

    public ObjectProperty<Timestamp> getDateOfMedProperty() {
        return dateOfMed;
    }

    public void setMed(String med) {
        this.med.set(med);
    }
    
    public String getMed() {
        return med.get();
    }
        
    public StringProperty getMedProperty() {
        return med;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost.set(unitCost);
    }
    
    public BigDecimal getUnitCost() {
        return unitCost.get();
    }

    public ObjectProperty<BigDecimal> getUnitCostProperty() {
        return unitCost;
    }
        
    public void setUnits(BigDecimal units) {
        this.units.set(units);
    }

    public BigDecimal getUnits() {
        return units.get();
    }

    public ObjectProperty<BigDecimal> getUnitsProperty() {
        return units;
    }

    @Override
    public String toString() {
        return "MedicationBean{" + "id=" + id + ", patientID=" + patientID + ", dateOfMed=" + dateOfMed + ", med=" + med + ", unitCost=" + unitCost + ", units=" + units + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id.get());
        hash = 79 * hash + Objects.hashCode(this.patientID.get());
        hash = 79 * hash + Objects.hashCode(this.dateOfMed.get());
        hash = 79 * hash + Objects.hashCode(this.med.get());
        hash = 79 * hash + Objects.hashCode(this.unitCost.get());
        hash = 79 * hash + Objects.hashCode(this.units.get());
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
        final MedicationBean other = (MedicationBean) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        if (!Objects.equals(this.patientID.get(), other.patientID.get())) {
            return false;
        }
        if (!Objects.equals(this.dateOfMed.get(), other.dateOfMed.get())) {
            return false;
        }
        if (!Objects.equals(this.med.get(), other.med.get())) {
            return false;
        }
        if (!Objects.equals(this.unitCost.get(), other.unitCost.get())) {
            return false;
        }
        if (!Objects.equals(this.units.get(), other.units.get())) {
            return false;
        }
        return true;
    }
    
}
