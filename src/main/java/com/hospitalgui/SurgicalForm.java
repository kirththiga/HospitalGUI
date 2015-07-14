package com.hospitalgui;

import com.hospitalgui.controls.AlertMessages;
import com.hospitalgui.controls.IDTextField;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import com.hospitalgui.persistence.DateConverter;
import com.hospitalgui.persistence.HospitalDAO;
import com.hospitalgui.persistence.HospitalDAOImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
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

/**
 * This class contains the layout and actions for the
 * surgical form.
 * 
 *  
 */
public class SurgicalForm extends StackPane {
    
    private final HospitalDAO hd;
    private PatientBean patientBean;
    private SurgicalBean surgicalBean;
    private ArrayList<TextField> formFields;
    private DatePicker dateOfSurgeryPicker;
    private DateConverter dateConverter;
    private AlertMessages alertMessages;
    private final String DATE_FORMAT = "dd/mm/yyyy";
    
    public SurgicalForm(PatientBean patientBean) {
        hd = new HospitalDAOImpl();
        this.patientBean = patientBean;
        surgicalBean = new SurgicalBean();
        formFields = new ArrayList<>();
        dateConverter = new DateConverter();
        alertMessages = new AlertMessages();
        createSurgicalUI();
    }

    /**
     * This method contains the layout of the form.
     */    
    private void createSurgicalUI() {
        
        Text sceneTitle = new Text("Surgical Form");
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
 
        // Surgery Date
        HBox dateOfSurgeryBox = new HBox();
        dateOfSurgeryBox.setId("dateOfSurgeryBox");
        Label dateOfStay = new Label("Date Of Surgery:");
        dateOfSurgeryPicker = new DatePicker();
        dateOfSurgeryPicker.setPromptText(DATE_FORMAT);
        dateOfSurgeryBox.getChildren().addAll(dateOfStay, dateOfSurgeryPicker);
        
        // Surgery Box
        HBox surgeryBox = new HBox();
        surgeryBox.setId("surgeryBox");
        Label surgery = new Label("Surgery:");
        TextField surgeryField = new TextField();
        formFields.add(surgeryField);
        Bindings.bindBidirectional(surgeryField.textProperty(), surgicalBean.getSurgeryProperty());
        surgeryBox.getChildren().addAll(surgery, surgeryField);
        
        // Room Fee Box
        HBox roomFeeBox = new HBox();
        roomFeeBox.setId("roomFeeBox");
        Label room = new Label("Room Fee:");
        TextField roomField = new TextField();
        formFields.add(roomField);
        roomFeeBox.getChildren().addAll(room, roomField);
        
        // Surgeon Fee Box
        HBox surgeonBox = new HBox();
        surgeonBox.setId("surgeonBox");
        Label surgeon = new Label("Surgeon Fee:");
        TextField surgeonField = new TextField();
        formFields.add(surgeonField);
        surgeonBox.getChildren().addAll(surgeon, surgeonField);
        
        // Supplies Box
        HBox suppliesBox = new HBox();
        suppliesBox.setId("suppliesBox");
        Label supplies = new Label("Supplies:");
        TextField suppliesField = new TextField();
        formFields.add(suppliesField);
        suppliesBox.getChildren().addAll(supplies, suppliesField);
        
        // Put all the HBoxes into a VBox
        VBox inputFields = new VBox();
        inputFields.getChildren().addAll(sceneTitle, idBox, patientIDBox, dateOfSurgeryBox, surgeryBox, roomFeeBox, surgeonBox, suppliesBox);
        
        getChildren().add(inputFields);
    }
 
    /**
     * Obtain the surgical list from the database.
     * @return
     * @throws SQLException 
     */
    public ObservableList<SurgicalBean> getSurgicalList() throws SQLException {
        
        ObservableList<SurgicalBean> list = hd.findPatientSurgicals(patientBean.getPatientID());
        patientBean.setSurgicalList(list);
        
        return list;
    }

    /**
     * Obtain the surgical position for a specific id 
     * from the surgical list
     * @return
     * @throws SQLException 
     */    
    private int surgicalPosition() throws SQLException {
        
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
        
        /** Find the surgical record for the id specified in the form
         *  and exit the loop when the correct match is found.
         */
        for(int i=0; i<getSurgicalList().size(); i++){
            id = getSurgicalList().get(i).getId();
            if(formID == id) {
                position = i;
                break;
            }
        }
        
        return position;
    }
    
    /**
     * Set the surgical form after obtaining the proper record.
     * @param position
     * @throws SQLException 
     */    
    private void setSurgicalForm(int position) throws SQLException {
        // Obtain the appropriate surgical
        surgicalBean = getSurgicalList().get(position);

        // Set the form fields according to the record obtained.
        formFields.get(0).setText(Integer.toString(surgicalBean.getId()));
        formFields.get(1).setText(Integer.toString(surgicalBean.getPatientID()));
        dateOfSurgeryPicker.setValue(dateConverter.convertToLocalDate(surgicalBean.getDateOfSurgery()));

        formFields.get(2).setText(surgicalBean.getSurgery());
        formFields.get(3).setText(surgicalBean.getRoomFee().toString());
        formFields.get(4).setText(surgicalBean.getSurgeonFee().toString());
        formFields.get(5).setText(surgicalBean.getSupplies().toString());
    }

    /**
     * Save the bean with the inputs from the text fields, and
     * select the appropriate alert.
     * @param bean
     * @param alert
     * @throws SQLException 
     */    
    private void saveBean(SurgicalBean bean, int alert) throws SQLException {
        
        double roomFee = 0;
        double surgeonFee = 0;
        double supplies = 0;
        boolean valid = true;
        
        try {
            roomFee = Double.parseDouble(formFields.get(3).getText());
            surgeonFee = Double.parseDouble(formFields.get(4).getText());            
            supplies = Double.parseDouble(formFields.get(5).getText());
        } catch (NumberFormatException e) {
            alertMessages.invalidNumeric("Room Fee", "Surgeon Fee", "Supplies");
            valid = false;
        }
        
        /**
         * If the fields have values entered in the correct format, then 
         * save the information into the bean.
         */        
        if(valid) {
            bean.setPatientID(patientBean.getPatientID());
            bean.setDateOfSurgery(dateConverter.convertToTimestamp(dateOfSurgeryPicker));
            bean.setSurgery(formFields.get(2).getText());
            bean.setRoomFee(BigDecimal.valueOf(roomFee));
            bean.setSurgeonFee(BigDecimal.valueOf(surgeonFee));
            bean.setSupplies(BigDecimal.valueOf(supplies));

            // If the date is not in the correct format, then display an alert.            
            if(bean.getDateOfSurgery() == null) {
                alertMessages.saveAlertError(bean.getId(), "surgical", alert);
                
            // Alert the user that the surgical has been successully created.                
            } else if(bean.getDateOfSurgery() != null && alert == 0) {
                int result = hd.createSurgical(bean);
                alertMessages.save(bean.getId(), "new surgical", 0);
                formFields.get(0).setText(Integer.toString(bean.getId()));
                
            // Alert the user that the surgical has been successully updated.    
            } else if(bean.getDateOfSurgery() != null && alert == 1) {
                int result = hd.updateSurgical(bean);
                alertMessages.save(bean.getId(), "surgical", 1);
                
            }

            this.surgicalBean = bean;
        }
        
    }

    /**
     * Set the form fields when the surgical form is selected.
     * @throws SQLException 
     */    
    public void setFormFields() throws SQLException {
        // Obtain the list of surgicals.
        getSurgicalList();
       
        /** The form is blank if there is no surgical. Otherwise, the form 
         * is filled with the first surgical from the list.
         */        
        if(patientBean.getSurgicalList().size() == 0) {
            for(int i=0; i<formFields.size(); i++) {
                this.formFields.get(i).setText("");
            }
            formFields.get(1).setText(Integer.toString(patientBean.getPatientID()));
            dateOfSurgeryPicker.setValue(null);
            dateOfSurgeryPicker.setPromptText(DATE_FORMAT);
        } else {
            setSurgicalForm(0);
        }
        
    }

    /**
     * Clear all the fields.
     */    
    public void clear() {
        formFields.stream().forEach((formField) -> {
            formField.setText("");
        });
        dateOfSurgeryPicker.setValue(null);
        dateOfSurgeryPicker.setPromptText(DATE_FORMAT);
    }

    /**
     * Find a specific surgical record based on the id.
     * @throws SQLException 
     */    
    public void find() throws SQLException {
        // Get the position of the surgical
        int position = surgicalPosition();
        
        /** Set the form fields after retrieving the surgical.
         *  Otherwise, display an alert for inputting the incorrect id,
         *  and clear the form fields.
         */
        if(position >= 0) {
            setSurgicalForm(position);
        } else {
            alertMessages.invalidID(1);
            clear();
        }
        
    }

    /**
     * Save the surgical by creating a new surgical or 
     * by updating the surgical record.
     * @throws SQLException 
     */    
    public void save() throws SQLException {
        /** If the id field is empty,then create a surgical record.
         *  Otherwise, update the record if a valid id is specified 
         *  in the id field.
         */
        if(formFields.get(0).getText().isEmpty()) {
            saveBean(surgicalBean, 0);

        } else {

            int position = surgicalPosition();
            
            if(position >= 0) {
                surgicalBean = getSurgicalList().get(position);
                saveBean(surgicalBean, 1);
            }
            
        }
        
    }

    /**
     * Obtain the previous surgical record.
     * @throws SQLException 
     */    
    public void prev() throws SQLException {

        int position = surgicalPosition();
        
        /* If position = 0, then reset the position to the last surgical
         * on the list to loop through the records. 
         */
        if(position == 0) {
            // When position reaches 0, reset it.
            position = getSurgicalList().size() - 1;
        } else {
            // Decrement position to get the previous surgical
            position--;
        }
        
        if(position >= 0) {
            setSurgicalForm(position);
        }
        
    }
    
    /**
     * Obtain the next surgical record.
     * @throws SQLException 
     */    
    public void next() throws SQLException {
        
        int position = surgicalPosition();
        // Increment position
        position++;
        
        if(position == getSurgicalList().size()) {
            // When position reaches the maximum size, reset it.
            position = 0;
        } 
        
        if(position >= 0){
            setSurgicalForm(position);
        }
        
    }
    
    /**
     * Delete a specific surgical record
     * @throws SQLException 
     */
    public void delete() throws SQLException {
        
        int position = surgicalPosition();
        
        if(position >= 0) {
            surgicalBean = getSurgicalList().get(position);
            
            // Delete surgical, and display an alert for successful deletion.
            int result = hd.deletePatientSurgical(surgicalBean.getPatientID(), surgicalBean.getId());
            alertMessages.delete(surgicalBean.getId(), "surgical", 1, getSurgicalList().size());
            
            // After deleting a surgical, and the surgical size is 0, clear the form.
            if(getSurgicalList().size() == 0) {
                clear();
                formFields.get(1).setText(Integer.toString(patientBean.getPatientID()));
                
            // After deleting a surgical, set the form to the previous record.    
            } else if(position > 0){
                surgicalBean = getSurgicalList().get(position - 1);
                setSurgicalForm(position - 1);
            } else {
                surgicalBean = getSurgicalList().get(getSurgicalList().size() - 1);
                setSurgicalForm(getSurgicalList().size() - 1);
            }
        } 
       
    }
        
}
