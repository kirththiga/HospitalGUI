package com.hospitalgui;

import com.hospitalgui.controls.AlertMessages;
import com.hospitalgui.controls.IDTextField;
import com.hospitalgui.model.InPatientBean;
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
 * inpatient form.
 * 
 *  
 */
public class InPatientForm extends StackPane {
    
    private final HospitalDAO hd;
    private PatientBean patientBean;
    private InPatientBean inPatientBean;
    private ArrayList<TextField> formFields;
    private DatePicker dateOfStayPicker;
    private DateConverter dateConverter;
    private AlertMessages alertMessages;
    private final String DATE_FORMAT = "dd/mm/yyyy";
    
    public InPatientForm(PatientBean patientBean) {
        hd = new HospitalDAOImpl();
        this.patientBean = patientBean;
        inPatientBean = new InPatientBean();
        formFields = new ArrayList<>();
        dateConverter = new DateConverter();
        alertMessages = new AlertMessages();        
        createSurgicalUI();
    }
    
    /**
     * This method contains the layout of the form.
     */
    private void createSurgicalUI() {
        
        Text sceneTitle = new Text("InPatient Form");
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
        
        // Stay Date
        HBox dateOfStayBox = new HBox();
        dateOfStayBox.setId("dateOfStayBox");
        Label dateOfStay = new Label("Date Of Stay:");
        dateOfStayPicker = new DatePicker();
        dateOfStayPicker.setPromptText(DATE_FORMAT);
        dateOfStayBox.getChildren().addAll(dateOfStay, dateOfStayPicker);
        
        // Room Number Box
        HBox roomBox = new HBox();
        roomBox.setId("roomBox");
        Label room = new Label("Room Number:");
        TextField roomField = new TextField();
        formFields.add(roomField);
        roomBox.getChildren().addAll(room, roomField);
        
        // Daily Rate Box
        HBox rateBox = new HBox();
        rateBox.setId("rateBox");
        Label rate = new Label("Daily Rate:");
        TextField rateField = new TextField();
        formFields.add(rateField);
        rateBox.getChildren().addAll(rate, rateField);
        
        // Supplies Box
        HBox suppliesBox = new HBox();
        suppliesBox.setId("suppliesBox");
        Label supplies = new Label("Supplies:");
        TextField suppliesField = new TextField();
        formFields.add(suppliesField);
        suppliesBox.getChildren().addAll(supplies, suppliesField);

        // Services Box
        HBox servicesBox = new HBox();
        servicesBox.setId("servicesBox");
        Label services = new Label("Services:");
        TextField servicesField = new TextField();
        formFields.add(servicesField);
        servicesBox.getChildren().addAll(services, servicesField);
        
        // Put all the HBoxes into a VBox
        VBox inputFields = new VBox();
        inputFields.getChildren().addAll(sceneTitle, idBox, patientIDBox, dateOfStayBox, roomBox, rateBox, suppliesBox, servicesBox);
        
        getChildren().add(inputFields);
    }
    
    /**
     * Obtain the inpatient list from the database.
     * @return
     * @throws SQLException 
     */
    public ObservableList<InPatientBean> getInPatientList() throws SQLException {
        
        ObservableList<InPatientBean> list = hd.findPatientInPatients(patientBean.getPatientID());
        patientBean.setInPatientList(list);
        
        return list;
    }
    
    /**
     * Obtain the inpatient position for a specific id 
     * from the inpatient list
     * @return
     * @throws SQLException 
     */
    private int getInPatientPosition() throws SQLException {
        
        // Initialize position to -5 which indicates an error
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
        
        /** Find the inpatient record for the id specified in the form
         *  and exit the loop when the correct match is found.
         */
        for(int i=0; i<getInPatientList().size(); i++){
            id = getInPatientList().get(i).getId();
            if(formID == id) {
                position = i;
                break;
            }
        }
        
        return position;
    }
    
    /**
     * Set the inpatient form after obtaining the proper record.
     * @param position
     * @throws SQLException 
     */
    private void setInPatientForm(int position) throws SQLException {
        // Obtain the appropriate inpatient
        inPatientBean = getInPatientList().get(position);

        // Set the form fields according to the record obtained.
        formFields.get(0).setText(Integer.toString(inPatientBean.getId()));
        formFields.get(1).setText(Integer.toString(inPatientBean.getPatientID()));
        dateOfStayPicker.setValue(dateConverter.convertToLocalDate(inPatientBean.getDateOfStay()));

        formFields.get(2).setText(inPatientBean.getRoomNumber());
        formFields.get(3).setText(inPatientBean.getDailyRate().toString());
        formFields.get(4).setText(inPatientBean.getSupplies().toString());
        formFields.get(5).setText(inPatientBean.getServices().toString());
    }
    
    /**
     * Save the bean with the inputs from the text fields, and
     * select the appropriate alert.
     * @param bean
     * @param alert
     * @throws SQLException 
     */
    private void saveBean(InPatientBean bean, int alert) throws SQLException {
        
        double dailyRate = 0;
        double supplies = 0;
        double services = 0;
        boolean valid = true;
        
        try {
            dailyRate = Double.parseDouble(formFields.get(3).getText());
            supplies = Double.parseDouble(formFields.get(4).getText());
            services = Double.parseDouble(formFields.get(5).getText());
        } catch (NumberFormatException e) {
            alertMessages.invalidNumeric("Daily Rate", "Supplies", "Services");
            valid = false;
        }
        
        /**
         * If the fields have values entered in the correct format, then 
         * save the information into the bean.
         */
        if(valid) {
            bean.setPatientID(patientBean.getPatientID());
            bean.setDateOfStay(dateConverter.convertToTimestamp(dateOfStayPicker));
            bean.setRoomNumber(formFields.get(2).getText());
            bean.setDailyRate(BigDecimal.valueOf(dailyRate));
            bean.setSupplies(BigDecimal.valueOf(supplies));        
            bean.setServices(BigDecimal.valueOf(services));
            
            // If the date is not in the correct format, then display an alert.
            if(bean.getDateOfStay() == null) {
                alertMessages.saveAlertError(bean.getId(), "inpatient", alert);
            
            // Alert the user that the inpatient has been successully created.
            } else if(bean.getDateOfStay() != null && alert == 0) {
                int result = hd.createInPatient(bean);
                alertMessages.save(bean.getId(), "new inpatient", 0);
                formFields.get(0).setText(Integer.toString(bean.getId()));
                
            // Alert the user that the inpatient has been successully updated.    
            } else if(bean.getDateOfStay() != null && alert == 1) {
                int result = hd.updateInPatient(bean);
                alertMessages.save(bean.getId(), "inpatient", 1);
              
            }

            this.inPatientBean = bean;
        }
    }
    
    /**
     * Set the form fields when the inpatient form is selected.
     * @throws SQLException 
     */
    public void setFormFields() throws SQLException {
        // Obtain the list of inpatients.
        getInPatientList();
        
        /** The form is blank if there is no inpatient. Otherwise, the form 
         * is filled with the first inpatient from the list.
         */
        if(patientBean.getInPatientList().size() == 0) {
            for(int i=0; i<formFields.size(); i++) {
                this.formFields.get(i).setText("");
            }
            formFields.get(1).setText(Integer.toString(patientBean.getPatientID()));
            dateOfStayPicker.setValue(null);
            dateOfStayPicker.setPromptText(DATE_FORMAT);
        } else {
            setInPatientForm(0);
        }
        
    }
    
    /**
     * Clear all the fields.
     */
    public void clear() {
        formFields.stream().forEach((formField) -> {
            formField.setText("");
        });
        dateOfStayPicker.setValue(null);
        dateOfStayPicker.setPromptText(DATE_FORMAT);
    }
    
    /**
     * Find a specific inpatient record based on the id.
     * @throws SQLException 
     */
    public void find() throws SQLException {
        
        // Get the position of the inpatient
        int position = getInPatientPosition();
        
        /** Set the form fields after retrieving the inpatient.
         *  Otherwise, display an alert for inputting the incorrect id,
         *  and clear the form fields.
         */
        if(position >= 0) {
            setInPatientForm(position);
        } else {
            alertMessages.invalidID(1);
            clear();
        }
        
    }
    
    /**
     * Save the inpatient by creating a new inpatient or 
     * by updating the inpatient record.
     * @throws SQLException 
     */
    public void save() throws SQLException {
        /** If the id field is empty,then create an inpatient record.
         *  Otherwise, update the record if a valid id is specified 
         *  in the id field.
         */
        if(formFields.get(0).getText().isEmpty()) {
            saveBean(inPatientBean, 0);
 
        } else {
            
            int position = getInPatientPosition();
            
            if(position >= 0) {
                inPatientBean = getInPatientList().get(position);
                saveBean(inPatientBean, 1);
            }
        }
        
    }
    
    /**
     * Obtain the previous inpatient record.
     * @throws SQLException 
     */
    public void prev() throws SQLException {

        int position = getInPatientPosition();
        
        /* If position = 0, then reset the position to the last inpatient 
         * on the list to loop through the records. 
         */
        if(position == 0) {
            // When position reaches 0, reset it.
            position = getInPatientList().size() - 1;
        } else {
            // Decrement position to get the previous inpatient
            position--;
        }
        
        if(position >= 0) {
            setInPatientForm(position);
        }
       
    }

    /**
     * Obtain the next inpatient record.
     * @throws SQLException 
     */
    public void next() throws SQLException {
        
        int position = getInPatientPosition();
        // Increment position
        position++;
        
        if(position == getInPatientList().size()) {
            // When position reaches the maximum size, reset it.
            position = 0;
        } 
        
        if(position >= 0){
            setInPatientForm(position);
        }
        
    }    
    
    /**
     * Delete a specific inpatient record
     * @throws SQLException 
     */
    public void delete() throws SQLException {
        
        int position = getInPatientPosition();
        
        if(position >= 0) {
            inPatientBean = getInPatientList().get(position);
            
            // Delete inpatient, and display an alert for successful deletion.
            int result = hd.deletePatientInPatient(inPatientBean.getPatientID(), inPatientBean.getId());
            alertMessages.delete(inPatientBean.getId(), "inpatient", 1, getInPatientList().size());
            
            // After deleting an inpatient, and the inpatient size is 0, clear the form.
            if(getInPatientList().size() == 0) {
                clear();
                formFields.get(1).setText(Integer.toString(patientBean.getPatientID()));
                
            // After deleting an inpatient, set the form to the previous record.    
            } else if(position > 0){
                inPatientBean = getInPatientList().get(position - 1);
                setInPatientForm(position - 1);
            } else {
                inPatientBean = getInPatientList().get(getInPatientList().size() - 1);
                setInPatientForm(getInPatientList().size() - 1);
            }
        } 
       
    }
        
}
