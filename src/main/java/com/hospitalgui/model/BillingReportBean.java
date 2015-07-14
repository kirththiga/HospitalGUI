package com.hospitalgui.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class contains the setter, getter, and getter methods  
 * for properties of billing report.
 * 
 *  
 */
public class BillingReportBean {
    private IntegerProperty id;
    private IntegerProperty patientID;
    private ObjectProperty<Timestamp> dateOfBilling;
    private ObjectProperty<BigDecimal> medicationTotal;
    private ObjectProperty<BigDecimal> inPatientTotal;
    private ObjectProperty<BigDecimal> surgicalTotal;
    private ObjectProperty<BigDecimal> grandTotal;    

    public BillingReportBean() {
        super();
        id = new SimpleIntegerProperty();
        patientID = new SimpleIntegerProperty();
        dateOfBilling = new SimpleObjectProperty<>();
        medicationTotal = new SimpleObjectProperty<>();
        inPatientTotal = new SimpleObjectProperty<>();
        surgicalTotal = new SimpleObjectProperty<>();
        grandTotal = new SimpleObjectProperty<>();
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
    
    public void setDateOfBilling(Timestamp dateOfMed) {
        this.dateOfBilling.set(dateOfMed);
    }
        
    public Timestamp getDateOfBilling() {
        return dateOfBilling.get();
    }

    public ObjectProperty<Timestamp> getDateOfBillingProperty() {
        return dateOfBilling;
    }

    public void setMedicationTotal(BigDecimal unitCost) {
        this.medicationTotal.set(unitCost);
    }
    
    public BigDecimal getMedicationTotal() {
        return medicationTotal.get();
    }

    public ObjectProperty<BigDecimal> getMedicationTotalProperty() {
        return medicationTotal;
    }

    public void setInPatientTotal(BigDecimal unitCost) {
        this.inPatientTotal.set(unitCost);
    }
    
    public BigDecimal getInPatientTotal() {
        return inPatientTotal.get();
    }

    public ObjectProperty<BigDecimal> getInPatientTotalProperty() {
        return inPatientTotal;
    }

    public void setSurgicalTotal(BigDecimal unitCost) {
        this.surgicalTotal.set(unitCost);
    }
    
    public BigDecimal getSurgicalTotal() {
        return surgicalTotal.get();
    }

    public ObjectProperty<BigDecimal> getSurgicalTotalProperty() {
        return surgicalTotal;
    }    
    
    public void setGrandTotal(BigDecimal unitCost) {
        this.grandTotal.set(unitCost);
    }
    
    public BigDecimal getGrandTotal() {
        return grandTotal.get();
    }

    public ObjectProperty<BigDecimal> getGrandProperty() {
        return grandTotal;
    }

    @Override
    public String toString() {
        return "BillingReportBean{" + "id=" + id + ", patientID=" + patientID + ", dateOfBilling=" + dateOfBilling + ", medicationTotal=" + medicationTotal + ", inPatientTotal=" + inPatientTotal + ", surgicalTotal=" + surgicalTotal + ", grandTotal=" + grandTotal + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id.get());
        hash = 97 * hash + Objects.hashCode(this.patientID.get());
        hash = 97 * hash + Objects.hashCode(this.dateOfBilling.get());
        hash = 97 * hash + Objects.hashCode(this.medicationTotal.get());
        hash = 97 * hash + Objects.hashCode(this.inPatientTotal.get());
        hash = 97 * hash + Objects.hashCode(this.surgicalTotal.get());
        hash = 97 * hash + Objects.hashCode(this.grandTotal.get());
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
        final BillingReportBean other = (BillingReportBean) obj;
        if (!Objects.equals(this.id.get(), other.id.get())) {
            return false;
        }
        if (!Objects.equals(this.patientID.get(), other.patientID.get())) {
            return false;
        }
        if (!Objects.equals(this.dateOfBilling.get(), other.dateOfBilling.get())) {
            return false;
        }
        if (!Objects.equals(this.medicationTotal.get(), other.medicationTotal.get())) {
            return false;
        }
        if (!Objects.equals(this.inPatientTotal.get(), other.inPatientTotal.get())) {
            return false;
        }
        if (!Objects.equals(this.surgicalTotal, other.surgicalTotal)) {
            return false;
        }
        if (!Objects.equals(this.grandTotal.get(), other.grandTotal.get())) {
            return false;
        }
        return true;
    }
    
    
}
