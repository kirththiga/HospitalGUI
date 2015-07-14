package com.hospitalgui;

import com.hospitalgui.controls.AlertMessages;
import com.hospitalgui.controls.IDTextField;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.persistence.DateConverter;
import com.hospitalgui.persistence.HospitalDAO;
import com.hospitalgui.persistence.HospitalDAOImpl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;

/**
 * This class contains the layout and actions for the
 * patient form.
 * 
 *  
 */
public class PatientForm extends StackPane {
    
    private final HospitalDAO hd;
    private PatientBean patientBean;
    private ArrayList<TextField> formFields;
    private DatePicker admissionPicker;
    private DatePicker releasePicker;
    private DateConverter dateConverter;
    private AlertMessages alertMessages;
    private final String DATE_FORMAT = "dd-mm-yyyy";
    
    public PatientForm(PatientBean pb) {
        hd = new HospitalDAOImpl();
        patientBean = pb;
        formFields = new ArrayList<>();
        dateConverter = new DateConverter();
        alertMessages = new AlertMessages();
        createPatientUI();
    }
    
    /**
     * This method contains the layout of the form.
     */    
    private void createPatientUI() {
        
        Text sceneTitle = new Text("Patient Form");
        sceneTitle.setId("form-text");
        
        // PatientID Box   
        HBox patientIDBox = new HBox();
        patientIDBox.setId("patientIDBox");
        Label patientID = new Label("Patient ID:");
        IDTextField patientIDField = new IDTextField();
        formFields.add(patientIDField);
        Bindings.bindBidirectional(patientIDField.textProperty(), patientBean.getPatientIDProperty(), new NumberStringConverter());
        patientIDBox.getChildren().addAll(patientID, patientIDField);
        
        // LastName Box     
        HBox lastNameBox = new HBox();
        lastNameBox.setId("lastNameBox");
        Label lastName = new Label("Last Name:");
        TextField lastNameField = new TextField();
        formFields.add(lastNameField);
        Bindings.bindBidirectional(lastNameField.textProperty(), patientBean.getLastNameProperty());
        lastNameBox.getChildren().addAll(lastName, lastNameField);
        
        // FirstName Box           
        HBox firstNameBox = new HBox();
        firstNameBox.setId("firstNameBox");
        Label firstName = new Label("First Name:");
        TextField firstNameField = new TextField();
        formFields.add(firstNameField);
        Bindings.bindBidirectional(firstNameField.textProperty(), patientBean.getFirstNameProperty());
        firstNameBox.getChildren().addAll(firstName, firstNameField);
        
        // Diagnosis Box
        HBox diagnosisBox = new HBox();
        diagnosisBox.setId("diagnosisBox");
        Label diagnosis = new Label("Diagnosis:");
        TextField diagnosisField = new TextField();
        formFields.add(diagnosisField);
        Bindings.bindBidirectional(diagnosisField.textProperty(), patientBean.getDiagnosisProperty());
        diagnosisBox.getChildren().addAll(diagnosis, diagnosisField);

        // Admission Date
        HBox admissionBox = new HBox();
        admissionBox.setId("admissionBox");
        Label admission = new Label("Admission Date:");
        admissionPicker = new DatePicker();
        admissionPicker.setValue(LocalDate.now());
        admissionBox.getChildren().addAll(admission, admissionPicker);
        
        // Release Date
        HBox releaseBox = new HBox();
        releaseBox.setId("releaseBox");
        Label release = new Label("Release Date:");
        releasePicker = new DatePicker();
        releasePicker.setPromptText(DATE_FORMAT);
        releaseBox.getChildren().addAll(release, releasePicker);
        
        // Put all the HBoxes into a VBox
        VBox inputFields = new VBox();
        inputFields.getChildren().addAll(sceneTitle, patientIDBox, lastNameBox, firstNameBox, diagnosisBox, admissionBox, releaseBox);
        
        getChildren().add(inputFields);
    }
    
    /**
     * Obtain the patient list from the database.
     * @return
     * @throws SQLException 
     */
    public ObservableList<PatientBean> getPatientList() throws SQLException {
        
        ObservableList<PatientBean> patientList = hd.findPatients();
        
        return patientList;
    }

    /**
     * Obtain the patient position for a specific id 
     * from the patient list
     * @return
     * @throws SQLException 
     */    
    private int getPatientPosition() throws SQLException {
        
        // Initialize inpatient position to -5 which indicates an error
        int position = -5;
        int id = 0;
        int formID = 0;
        String formText = formFields.get(0).getText();

        try {
            formID = Integer.parseInt(formText);
        } catch (NumberFormatException e) {
            alertMessages.invalidID(0);
            clear();
            formFields.get(0).setPromptText("Enter integer > 0");
        }
        
        /** Find the patient record for the id specified in the form
         *  and exit the loop when the correct match is found.
         */        
        for(int i=0; i<getPatientList().size(); i++){
            id = getPatientList().get(i).getPatientID();
            if(formID == id) {
                position = i;
                break;
            }
        }
        
        return position;
    }

    /**
     * Set the patient form after obtaining the proper record.
     * @param position
     * @throws SQLException 
     */    
    private void setPatientForm(int position) throws SQLException {
        // Obtain the appropriate patient
        patientBean = getPatientList().get(position);
        
        // Set the form fields according to the record obtained.
        formFields.get(0).setText(Integer.toString(patientBean.getPatientID()));
        formFields.get(1).setText(patientBean.getLastName());
        formFields.get(2).setText(patientBean.getFirstName());
        formFields.get(3).setText(patientBean.getDiagnosis());

        admissionPicker.setValue(dateConverter.convertToLocalDate(patientBean.getAdmissionDate()));        
        releasePicker.setValue(dateConverter.convertToLocalDate(patientBean.getReleaseDate()));
        
    }
    
    /**
     * Save the bean with the inputs from the text fields, and
     * select the appropriate alert.
     * @param bean
     * @param alert
     * @throws SQLException 
     */    
    private void saveBean(PatientBean patientBean, int alert) throws SQLException {
        
        patientBean.setLastName(formFields.get(1).getText());
        patientBean.setFirstName(formFields.get(2).getText());
        patientBean.setDiagnosis(formFields.get(3).getText());
        patientBean.setAdmissionDate(dateConverter.convertToTimestamp(admissionPicker));
        patientBean.setReleaseDate(dateConverter.convertToTimestamp(releasePicker));
        
        // If the date is not in the correct format, then display an alert.
        if(patientBean.getAdmissionDate() == null || patientBean.getReleaseDate() == null) {
            alertMessages.saveAlertError(patientBean.getPatientID(), "patient", 1);
        
        // Alert the user that the patient has been successully created.    
        } else if(patientBean.getAdmissionDate() != null && patientBean.getReleaseDate() != null && alert == 0) {
            int result = hd.createPatient(patientBean);
            alertMessages.save(patientBean.getPatientID(), "new patient", 0);
            setPatientForm(getPatientList().size() -1);
        
        // Alert the user that the inpatient has been successully updated.    
        } else if(patientBean.getAdmissionDate() != null && patientBean.getReleaseDate() != null && alert == 1) {
            int result = hd.updatePatient(patientBean);
            alertMessages.save(patientBean.getPatientID(), "patient", 1);
            
        }
        
        this.patientBean = patientBean;

    }
    
    /**
     * Clear all the text fields.
     */
    public void clear() {
        formFields.stream().forEach((formField) -> {
            formField.setText("");
        });
        admissionPicker.setValue(null);
        releasePicker.setValue(null);
        admissionPicker.setPromptText(DATE_FORMAT);
        releasePicker.setPromptText(DATE_FORMAT);
    }
    
    /**
     * Find a specific patient based on the patient id.
     * @throws SQLException 
     */
    public void find() throws SQLException {
        
        // Get the position of the patient
        int position = getPatientPosition();
        
        /** Set the form fields after retrieving the patient.
         *  Otherwise, display an alert for inputting the incorrect id,
         *  and clear the form fields.
         */        
        if(position >= 0) {
            setPatientForm(position);
        } else {
            alertMessages.invalidID(0);
            clear();
        }
       
    }

    /**
     * Display the total cost for a patient.
     * @throws SQLException 
     */
    public void report() throws SQLException {
        find();
        alertMessages.report(patientBean);
    }

    /**
     * Save the patient by creating a new patient or 
     * by updating the patient record.
     * @throws SQLException 
     */    
    public void save() throws SQLException {
        /** If the id field is empty,then create a patient record.
         *  Otherwise, update the record if a valid id is specified 
         *  in the id field.
         */
        if(formFields.get(0).getText().isEmpty()) {
            saveBean(patientBean, 0);
            
        } else {

            int position = getPatientPosition();
            
            if(position >= 0) {
                patientBean = getPatientList().get(position);
                saveBean(patientBean, 1);
            }
        }
    }
    
    /**
     * Find the previous patient record.
     * @throws SQLException 
     */
    public void prev() throws SQLException {
        
        int position = getPatientPosition();
        
        /* If position = 0, then reset the position to the last patient 
         * on the list to loop through the records. 
         */
        if(position == 0) {
            // When position reaches 0, reset it.
            position = getPatientList().size() - 1;
        } else {
            // Decrement position to get the previous patient
            position--;
        }
        
        if(position >= 0) {
            setPatientForm(position);
        }
       
    }
    
    /**
     * Find the next patient record.
     * @throws SQLException 
     */
    public void next() throws SQLException {
        
        int position = getPatientPosition();
        
        // Increment position
        position++;
        
        if(position == getPatientList().size()) {
            // When position reaches the maximum size, reset it.
            position = 0;
        } 
        
        if(position >= 0){
            setPatientForm(position);
        }
       
    }

    /**
     * Delete a specific patient based on the patient id. 
     * @throws SQLException 
     */
    public void delete() throws SQLException{
        int position = getPatientPosition();
        
        if(position >= 0) {
            
            // Delete inpatient, and display an alert for successful deletion.
            patientBean = getPatientList().get(position);
            int result = hd.deletePatient(patientBean.getPatientID());
            alertMessages.delete(patientBean.getPatientID(), "patient", 0, getPatientList().size());
            
            // After deleting patient, and the patient size is 0, clear the form.
            if(getPatientList().size() == 0) {
                clear();
            
            // After deleting a patient, set the form to the previous record.    
            } else if(position > 0){
                patientBean = getPatientList().get(position - 1);
                setPatientForm(position - 1);
            } else {
                patientBean = getPatientList().get(getPatientList().size() - 1);
                setPatientForm(getPatientList().size() - 1);
            }
        } else {
            // Display this alert for entering the incorrect id.
            alertMessages.invalidID(0);
        }
        
    }
    
}
