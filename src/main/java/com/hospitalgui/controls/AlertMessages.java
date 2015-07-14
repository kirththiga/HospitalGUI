package com.hospitalgui.controls;

import com.hospitalgui.model.BillingReportBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.persistence.BillingReportDAO;
import com.hospitalgui.persistence.BillingReportDAOImpl;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;

/**
 * This class contains all the alert messages
 * for the actions.
 * 
 *  
 */
public class AlertMessages {

    public AlertMessages() {
    
    }
    
    /**
     * Alert for entering an invalid ID.
     * @param header 
     */
    public void invalidID(int header) {
        Alert save = new Alert(Alert.AlertType.ERROR);
        save.setTitle("Invalid ID Alert");
        
        if(header == 0) {
            save.setHeaderText("Invalid Patient ID or Patient does not exist!");
        } else {
            save.setHeaderText("Invalid ID or ID does not exist!");
        }
        
        save.setContentText("Invalid entry for ID! \nEnter an appropriate value.");
        save.showAndWait();
    }
    
    /**
     * Alert for entering wrong format in the BigDecimal fields. 
     * @param r
     * @param s
     * @param t 
     */
    public void invalidNumeric(String r, String s, String t) {
        Alert save = new Alert(Alert.AlertType.ERROR);
        save.setTitle("Invalid Numeric");
        
        save.setHeaderText("Invalid Values! " );
        
        save.setContentText("Enter appropriate values for: \n" + r + "\n" + s + "\n" + t);
        save.showAndWait();        
    }
    
    /**
     * Alert to display the report for the patient.
     * @param patientBean
     * @throws SQLException 
     */
    public void report(PatientBean patientBean) throws SQLException {
        Alert report = new Alert(Alert.AlertType.INFORMATION);

        BillingReportDAO bd = new BillingReportDAOImpl();        
        LocalDateTime ldt = LocalDateTime.now();
        Timestamp s = Timestamp.valueOf(ldt);
        BillingReportBean reportBean = bd.totalCost(patientBean.getPatientID(), patientBean, s);
        
        LocalDate d = ldt.toLocalDate();
        report.setTitle("Patient Report");
        report.setHeaderText("Report for Patient " + patientBean.getPatientID() + " on " + d.toString());
        report.setContentText(reportBean.getGrandTotal().toString());
        report.show();
    }
    
    /**
     * Alert for saving a record by entering information in
     * the wrong format.
     * @param id
     * @param recordType
     * @param header 
     */
    public void saveAlertError(int id, String recordType, int header) {
        Alert save = new Alert(Alert.AlertType.ERROR);
        save.setTitle("Save " + recordType + " information");
        
        if(header == 0) {
            save.setHeaderText("Could not create new " + recordType + " record");
        } else {
            save.setHeaderText("Could not update " + recordType + " " + id + " record");
        }
        
        save.setContentText("Error in date format! \nEnter an appropriate date. ");
        save.showAndWait();
    }

    /**
     * Alert for saving a record.
     * @param id
     * @param recordType
     * @param header 
     */
    public void save(int id, String recordType, int header) {
        Alert save = new Alert(Alert.AlertType.INFORMATION);
        save.setTitle("Saved " + recordType + " information");
        
        if(header == 0) {
            save.setHeaderText("Successfully created " + recordType);
            save.setContentText("Created " + recordType + " " + id + " record");

        } else {
            save.setHeaderText("Successfully updated " + recordType);
            save.setContentText("Updated " + recordType + " " + id + " record");
        }
        
        save.showAndWait();
    }
    
    /**
     * Alert for deleting a record successfully, or for entering information
     * in the wrong format when deleting.
     * @param patientID
     * @param recordType
     * @param context
     * @param size 
     */
    public void delete(int patientID, String recordType, int context, int size) {
        Alert delete = new Alert(Alert.AlertType.WARNING);
        delete.setTitle("Deleting Records");
        delete.setHeaderText("Deleting specific records");
        
        String s = "Deleted patient " + patientID + " " + recordType;
        String t = "No records in " + recordType + " for patient " + patientID;
        
        if(context == 0) {
            delete.setContentText("Deleted patient " + patientID + " and all it's associated records.");
        } else if(size == 0){
            delete.setContentText(s + "\n" + t);
        } else {
            delete.setContentText(s);
        }
        
        delete.showAndWait();
    }
   
}
