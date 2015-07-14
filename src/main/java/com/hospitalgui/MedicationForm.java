package com.hospitalgui;

import com.hospitalgui.controls.AlertMessages;
import com.hospitalgui.controls.IDTextField;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.persistence.DateConverter;
import com.hospitalgui.persistence.HospitalDAO;
import com.hospitalgui.persistence.HospitalDAOImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class contains the layout and actions for the
 * medication form.
 *
 *  
 */
public class MedicationForm extends StackPane {
    
    private final HospitalDAO hd;
    private MedicationBean medicationBean;
    private PatientBean patientBean;
    private ArrayList<TextField> formFields;
    private DatePicker dateOfMedPicker;
    private DateConverter dateConverter;
    private AlertMessages alertMessages;
    private final String DATE_FORMAT = "dd/mm/yyyy";
    
    public MedicationForm(PatientBean pb) {
        hd = new HospitalDAOImpl();
        formFields = new ArrayList<>();
        medicationBean = new MedicationBean();
        this.patientBean = pb;
        dateConverter = new DateConverter();
        alertMessages = new AlertMessages();
        createMedicationUI();
    }
    
    /**
     * This method contains the layout of the form.
     */    
    private void createMedicationUI() {
        
        Text sceneTitle = new Text("Medication Form");
        sceneTitle.setId("form-text");
        
        // ID Box
        HBox idBox = new HBox();
        idBox.setId("idBox");
        Label id = new Label("ID:");
        IDTextField idField = new IDTextField();
        formFields.add(idField);
        idBox.getChildren().addAll(id, idField);
        
        // PatientID Box   
        HBox patientIDBox = new HBox();
        patientIDBox.setId("patientIDBox");
        Label patientID = new Label("Patient ID:");
        TextField patientIDField = new TextField();
        formFields.add(patientIDField);
        patientIDBox.getChildren().addAll(patientID, patientIDField);
        patientIDField.setEditable(false);
        
        // Release Date
        HBox dateOfMedBox = new HBox();
        dateOfMedBox.setId("dateOfMedBox");
        Label dateOfMed = new Label("Date Of Med:");
        dateOfMedPicker = new DatePicker();
        dateOfMedPicker.setPromptText(DATE_FORMAT);
        dateOfMedBox.getChildren().addAll(dateOfMed, dateOfMedPicker);
        
        // Med Box
        HBox medBox = new HBox();
        medBox.setId("medBox");
        Label med = new Label("Med:");
        TextField medField = new TextField();
        formFields.add(medField);
        medBox.getChildren().addAll(med, medField);
        
        // Unitcost Box
        HBox unitCostBox = new HBox();
        unitCostBox.setId("unitCostBox");
        Label unitCost = new Label("Unit Cost:");
        TextField unitCostField = new TextField();
        formFields.add(unitCostField);
        unitCostBox.getChildren().addAll(unitCost, unitCostField);

        // Units Box
        HBox unitsBox = new HBox();
        unitsBox.setId("unitsBox");
        Label units = new Label("Units:");
        TextField unitsField = new TextField();
        formFields.add(unitsField);
        unitsBox.getChildren().addAll(units, unitsField);
        
        // Put all the HBoxes into a VBox
        VBox inputFields = new VBox();
        inputFields.getChildren().addAll(sceneTitle, idBox, patientIDBox, dateOfMedBox, medBox, unitCostBox, unitsBox);
        
        getChildren().add(inputFields);
        
    }

    /**
     * Obtain the medication list from the database.
     * @return
     * @throws SQLException 
     */
    public ObservableList<MedicationBean> getMedicationList() throws SQLException {
        
        ObservableList<MedicationBean> list = hd.findPatientMedications(patientBean.getPatientID());
        patientBean.setMedicationList(list);
        
        return list;
    }

    /**
     * Obtain the medication position for a specific id 
     * from the medication list
     * @return
     * @throws SQLException 
     */
    private int getMedicationPosition() throws SQLException {
        
        // Initialize inpatient position to -5 which indicates an error
        int position = -5;
        int id = 0;
        int formID = 0;
        String formText = formFields.get(0).getText();

        try {
            formID = Integer.parseInt(formText);
        } catch (NumberFormatException e) {
            alertMessages.invalidID(1);
            clear();
            formFields.get(0).setPromptText("Enter integer > 0");
        }
        
        /** Find the medication record for the id specified in the form
         *  and exit the loop when the correct match is found.
         */
        for(int i=0; i<getMedicationList().size(); i++){
            id = getMedicationList().get(i).getId();
            if(formID == id) {
                position = i;
                break;
            }
        }
        
        return position;
    }

    /**
     * Set the medication form after obtaining the proper record.
     * @param position
     * @throws SQLException 
     */    
    private void setMedicationForm(int position) {
        medicationBean = patientBean.getMedicationList().get(position); 

        formFields.get(0).setText(Integer.toString(medicationBean.getId()));
        formFields.get(1).setText(Integer.toString(medicationBean.getPatientID()));
        dateOfMedPicker.setValue(dateConverter.convertToLocalDate(medicationBean.getDateOfMed()));

        formFields.get(2).setText(medicationBean.getMed());
        formFields.get(3).setText(medicationBean.getUnitCost().toString());
        formFields.get(4).setText(medicationBean.getUnits().toString());
    }

    /**
     * Save the bean with the inputs from the text fields, and
     * select the appropriate alert.
     * @param bean
     * @param alert
     * @throws SQLException 
     */    
    private void saveBean(MedicationBean medicationBean, int alert) throws SQLException {
        
        double unitCost = 0;
        double units = 0;
        boolean valid = true;
        
        try {
            unitCost = Double.parseDouble(formFields.get(3).getText());
            units = Double.parseDouble(formFields.get(4).getText());
        } catch (NumberFormatException e) {
            alertMessages.invalidNumeric("Unit Cost", "Units", "");
            valid = false;
        }

        /**
         * If the fields have values entered in the correct format, then 
         * save the information into the bean.
         */        
        if(valid) {
            medicationBean.setPatientID(patientBean.getPatientID());
            medicationBean.setDateOfMed(dateConverter.convertToTimestamp(dateOfMedPicker));
            medicationBean.setMed(formFields.get(2).getText());
            medicationBean.setUnitCost(BigDecimal.valueOf(unitCost));
            medicationBean.setUnits(BigDecimal.valueOf(units));

            // If the date is not in the correct format, then display an alert.            
            if(medicationBean.getDateOfMed() == null) {
                alertMessages.saveAlertError(medicationBean.getId(), "medication", 0);
                
            // Alert the user that the medication has been successully created.                
            } else if(medicationBean.getDateOfMed() != null && alert == 0) {
                int result = hd.createMedication(medicationBean);
                alertMessages.save(medicationBean.getId(), "new medication", 0);
                formFields.get(0).setText(Integer.toString(medicationBean.getId()));
                
            // Alert the user that the medication has been successully updated.                
            } else if(medicationBean.getDateOfMed() != null && alert == 1) {
                int result = hd.updateMedication(medicationBean);
                alertMessages.save(medicationBean.getId(), "medication", 1);
               
            }

            this.medicationBean = medicationBean;
        }
        
    }

    /**
     * Set the form fields when the inpatient form is selected.
     * @throws SQLException 
     */    
    public void setFormFields() throws SQLException {
        // Obtain the list of medications.
        getMedicationList();
        
        /** The form is blank if there is no medication. Otherwise, the form 
         * is filled with the first medication from the list.
         */        
        if(patientBean.getMedicationList().size() == 0) {
            for(int i=0; i<formFields.size(); i++) {
                this.formFields.get(i).setText("");
            }
            formFields.get(1).setText(Integer.toString(patientBean.getPatientID()));
            dateOfMedPicker.setValue(null);
            dateOfMedPicker.setPromptText(DATE_FORMAT);
        } else {
            setMedicationForm(0);
        }
        
    }
    
    /**
     * Clear all the fields.
     */        
    public void clear() {
        formFields.stream().forEach((formField) -> {
            formField.setText("");
        });
        dateOfMedPicker.setValue(null);
        dateOfMedPicker.setPromptText(DATE_FORMAT);
    }
    
    /**
     * Find a specific medication record based on the id.
     * @throws SQLException 
     */    
    public void find() throws SQLException {
        
        // Get the position of the medication
        int position = getMedicationPosition();
        
        /** Set the form fields after retrieving the medication.
         *  Otherwise, display an alert for inputting the incorrect id,
         *  and clear the form fields.
         */
        if(position >= 0) {
            setMedicationForm(position);
        } else {
            alertMessages.invalidID(1);
            clear();
        }
       
    }
    
    /**
     * Save the medication by creating a new medication or 
     * by updating the medication record.
     * @throws SQLException 
     */    
    public void save() throws SQLException {
        /** If the id field is empty,then create a medication record.
         *  Otherwise, update the record if a valid id is specified 
         *  in the id field.
         */
        if(formFields.get(0).getText().isEmpty()) {
            saveBean(medicationBean, 0);

        } else {

            int position = getMedicationPosition();
            
            if(position >= 0) {
                medicationBean = getMedicationList().get(position);
                saveBean(medicationBean, 1);
            }
        }
        
    }

    /**
     * Obtain the previous medication record.
     * @throws SQLException 
     */    
    public void prev() throws SQLException {

        int position = getMedicationPosition();
        
        /* If position = 0, then reset the position to the last medication 
         * on the list to loop through the records. 
         */
        if(position == 0) {
            // When position reaches 0, reset it.
            position = getMedicationList().size() - 1;
        } else {
            // Decrement position to get the previous medication
            position--;
        }
        
        if(position >= 0) {
            setMedicationForm(position);
        }
      
    }
    
   /**
     * Obtain the next medication record.
     * @throws SQLException 
     */    
    public void next() throws SQLException {
        
        int position = getMedicationPosition();
        // Increment position
        position++;
        
        if(position == getMedicationList().size()) {
            // When position reaches the maximum size, reset it.
            position = 0;
        } 
        
        if(position >= 0){
            setMedicationForm(position);
        }
        
    }

    /**
     * Delete a specific medication record
     * @throws SQLException 
     */    
    public void delete() throws SQLException {
        
        int position = getMedicationPosition();
        
        if(position >= 0) {
            medicationBean = getMedicationList().get(position);
            
            // Delete medication, and display an alert for successful deletion.
            int result = hd.deletePatientMedication(medicationBean.getPatientID(), medicationBean.getId());
            alertMessages.delete(medicationBean.getId(), "medication", 1, getMedicationList().size());

            // After deleting an medication, and the medication size is 0, clear the form.
            if(getMedicationList().size() == 0) {
                clear();
                formFields.get(1).setText(Integer.toString(patientBean.getPatientID()));
                
            // After deleting an medication, set the form to the previous record.    
            } else if(position > 0){
                medicationBean = getMedicationList().get(position - 1);
                setMedicationForm(position - 1);
            } else {
                medicationBean = getMedicationList().get(getMedicationList().size() - 1);
                setMedicationForm(getMedicationList().size() - 1);
            }
        }
       
    }
    
}
