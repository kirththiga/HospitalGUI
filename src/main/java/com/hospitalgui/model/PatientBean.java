package com.hospitalgui.model;

import java.sql.Timestamp;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains the setter, getter, and getter methods  
 * for properties of patient.
 * 
 *  
 */
public class PatientBean {
    
    private IntegerProperty patientID;
    private StringProperty lastName;
    private StringProperty firstName;
    private StringProperty diagnosis;
    private ObjectProperty<Timestamp> admissionDate;
    private ObjectProperty<Timestamp> releaseDate;
    private ObservableList<MedicationBean> medicationList;
    private ObservableList<InPatientBean> inPatientList;
    private ObservableList<SurgicalBean> surgicalList;

    public PatientBean() {
        super();
        patientID = new SimpleIntegerProperty();
        lastName = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        diagnosis = new SimpleStringProperty();
        admissionDate = new SimpleObjectProperty<>();
        releaseDate = new SimpleObjectProperty<>();
        medicationList = FXCollections.observableArrayList();
        inPatientList = FXCollections.observableArrayList();
        surgicalList = FXCollections.observableArrayList();
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
    
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public String getLastName() {
        return lastName.get();
    }
        
    public StringProperty getLastNameProperty() {
        return lastName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public String getFirstName() {
        return firstName.get();
    }
        
    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }
        
    public String getDiagnosis() {
        return diagnosis.get();
    }
        
    public StringProperty getDiagnosisProperty() {
        return diagnosis;
    }

    public void setAdmissionDate(Timestamp admissionDate) {
        this.admissionDate.set(admissionDate);
    }
        
    public Timestamp getAdmissionDate() {
        return admissionDate.get();
    }

    public ObjectProperty<Timestamp> getAdmissionDateProperty() {
        return admissionDate;
    }
        
    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public Timestamp getReleaseDate() {
        return releaseDate.get();
    }

    public ObjectProperty<Timestamp> getReleaseDateProperty() {
        return releaseDate;
    }

    public void setMedicationList(ObservableList<MedicationBean> medicationList) {
        this.medicationList = medicationList;
    }
    
    public ObservableList<MedicationBean> getMedicationList() {
        return medicationList;
    }
    
    public void setInPatientList(ObservableList<InPatientBean> inPatientList) {
        this.inPatientList = inPatientList;
    }

    public ObservableList<InPatientBean> getInPatientList() {
        return inPatientList;
    }

    public void setSurgicalList(ObservableList<SurgicalBean> surgicalList) {
        this.surgicalList = surgicalList;
    }

    public ObservableList<SurgicalBean> getSurgicalList() {
        return surgicalList;
    }

    @Override
    public String toString() {
        return "PatientBean{" + "patientID=" + patientID + ", lastName=" + lastName + ", firstName=" + firstName + ", diagnosis=" + diagnosis + ", admissionDate=" + admissionDate + ", releaseDate=" + releaseDate + ", medicationList=" + medicationList + ", inPatientList=" + inPatientList + ", surgicalList=" + surgicalList + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.patientID.get());
        hash = 17 * hash + Objects.hashCode(this.lastName.get());
        hash = 17 * hash + Objects.hashCode(this.firstName.get());
        hash = 17 * hash + Objects.hashCode(this.diagnosis.get());
        hash = 17 * hash + Objects.hashCode(this.admissionDate.get());
        hash = 17 * hash + Objects.hashCode(this.releaseDate.get());
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
        final PatientBean other = (PatientBean) obj;
        if (!Objects.equals(this.patientID.get(), other.patientID.get())) {
            return false;
        }
        if (!Objects.equals(this.lastName.get(), other.lastName.get())) {
            return false;
        }
        if (!Objects.equals(this.firstName.get(), other.firstName.get())) {
            return false;
        }
        if (!Objects.equals(this.diagnosis.get(), other.diagnosis.get())) {
            return false;
        }
        if (!Objects.equals(this.admissionDate.get(), other.admissionDate.get())) {
            return false;
        }
        if (!Objects.equals(this.releaseDate.get(), other.releaseDate.get())) {
            return false;
        }
        return true;
    }
   
    
    
}
