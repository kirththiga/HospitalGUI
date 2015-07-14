package com.hospitalgui.persistence;

import com.hospitalgui.model.InPatientBean;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Interface for CRUD methods
 * 
 *  
 */
public interface HospitalDAO {
    
    // Create
    public int createPatient(PatientBean patientBean) throws SQLException;
    public int createMedication(MedicationBean medicationBean) throws SQLException;
    public int createInPatient(InPatientBean inPatientBean) throws SQLException;
    public int createSurgical(SurgicalBean surgicalBean) throws SQLException;
    
    // Read
    public ObservableList<PatientBean> findPatients() throws SQLException;
    public void findPatientID(PatientBean patientBean) throws SQLException;
    public ObservableList<MedicationBean> findPatientMedications(int patientId) throws SQLException;
    public ObservableList<InPatientBean> findPatientInPatients(int patientId) throws SQLException;
    public ObservableList<SurgicalBean> findPatientSurgicals(int patientId) throws SQLException;
    public ObservableList<MedicationBean> findMedications() throws SQLException;
    public ObservableList<InPatientBean> findInPatients() throws SQLException;
    public ObservableList<SurgicalBean> findSurgicals() throws SQLException;
    
    // Update
    public int updatePatient(PatientBean patientBean) throws SQLException;
    public int updateMedication(MedicationBean medicationBean) throws SQLException;
    public int updateInPatient(InPatientBean inPatientBean) throws SQLException;
    public int updateSurgical(SurgicalBean surgicalBean) throws SQLException;
    
    // Delete
    public int deletePatient(int patientId) throws SQLException;
    public int deleteMedication(int patientId) throws SQLException;
    public int deletePatientMedication(int patientId, int id) throws SQLException;
    public int deleteInPatient(int patientId) throws SQLException;
    public int deletePatientInPatient(int patientId, int id) throws SQLException;
    public int deleteSurgical(int patientId) throws SQLException;
    public int deletePatientSurgical(int patientId, int id) throws SQLException;
}
