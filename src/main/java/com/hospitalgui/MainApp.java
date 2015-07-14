package com.hospitalgui;

import com.hospitalgui.controls.ButtonPanel;
import com.hospitalgui.model.PatientBean;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * The main class to run this application.
 *  
 */
public class MainApp extends Application {
    
    private PatientBean patientBean;
    private PatientForm patientForm;
    private MedicationForm medicationForm;
    private InPatientForm inPatientForm;
    private SurgicalForm surgicalForm;
    private StackPane centerPane;
    private ButtonPanel buttonPanel;
    private boolean[] selection;
    
    public MainApp() {
        patientBean = new PatientBean();
        patientForm = new PatientForm(patientBean);
        medicationForm = new MedicationForm(patientBean);
        inPatientForm = new InPatientForm(patientBean);
        surgicalForm = new SurgicalForm(patientBean);
        selection = new boolean[4];
        selection[0] = true;
        selection[1] = false;
        selection[2] = false;
        selection[3] = false;
        buttonPanel = new ButtonPanel(patientForm, medicationForm, inPatientForm, surgicalForm, selection);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Set Window's Title
        stage.setTitle("Hospital GUI");
        Scene scene = new Scene(createUI(), 400, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/Form.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create the user interface in the root
     *
     * @return
     */  
    private BorderPane createUI() {

        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        
        centerPane = new StackPane();
        root.setCenter(centerPane);
        centerPane.getChildren().add(patientForm);
        centerPane.getChildren().add(medicationForm);
        centerPane.getChildren().add(inPatientForm);
        centerPane.getChildren().add(surgicalForm);
        
        patientForm.setVisible(true);
        medicationForm.setVisible(false);        
        surgicalForm.setVisible(false);
        inPatientForm.setVisible(false);
        
        patientForm.toFront();
        
        buttonPanel.setId("buttonPanel");
        buttonPanel.setMinHeight(95);
        root.setBottom(buttonPanel);

        return root;
    }
  
    private MenuBar createMenuBar() {
        // MenuBar with Menus and their MenuItems
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().add(exit);
        Menu forms = new Menu("Forms");
        MenuItem patient = new MenuItem("Patient");
        MenuItem medication = new MenuItem("Medication");
        MenuItem inPatient = new MenuItem("InPatient");
        MenuItem surgical = new MenuItem("Surgical");
        forms.getItems().addAll(patient, medication, inPatient, surgical);
        menuBar.getMenus().addAll(file, forms);
        
        /** 
         * The actions performed on the menu items.
         */
        
        // Exit
        exit.setOnAction(event -> System.exit(0));
        
        // Actions that occur when the patient form is selected.
        patient.setOnAction((ActionEvent e) -> {
            // Only the patient form actions should be performed.
            buttonPanel.setSelection(0, 1);
            
            // Disable buttons that cannot be used.
            try {
                                
                if(patientForm.getPatientList().size() < 2) {
                    buttonPanel.getReportBtn().setDisable(true);
                    
                    if(patientForm.getPatientList().size() > 0) {
                        buttonPanel.getReportBtn().setDisable(false);
                        buttonPanel.getReportBtn().setVisible(true);
                    }
                    buttonPanel.getPrevBtn().setDisable(true);
                    buttonPanel.getNextBtn().setDisable(true);
                } else {
                    buttonPanel.getReportBtn().setVisible(true);
                    buttonPanel.getPrevBtn().setDisable(false);
                    buttonPanel.getNextBtn().setDisable(false);
                }
               
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // The patient form was selected.
            selectPane("patient");
        });
        
        // Actions that occur when the medication form is selected.
        medication.setOnAction((ActionEvent e) -> {
           // Only the medication form actions should be performed. 
           buttonPanel.setSelection(1, 0);
           
           // Disable buttons that cannot be used.
            try {
                if(medicationForm.getMedicationList().size() < 2) {
                    buttonPanel.getReportBtn().setVisible(false);
                    buttonPanel.getPrevBtn().setDisable(true);
                    buttonPanel.getNextBtn().setDisable(true);
                } else {
                    buttonPanel.getReportBtn().setVisible(false);
                    buttonPanel.getPrevBtn().setDisable(false);
                    buttonPanel.getNextBtn().setDisable(false);
                }
                
                // Set the form fields.
                medicationForm.setFormFields();
                
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
         
           // The medication form was selected. 
           selectPane("medication");
        });
        
        // Actions that occur when the inpatient form is selected.
        inPatient.setOnAction((ActionEvent e) -> {
           // Only the inpatient form actions should be performed. 
           buttonPanel.setSelection(2, 0); 
           
           // Disable buttons that cannot be used.
            try {
                if(inPatientForm.getInPatientList().size() < 2) {
                    buttonPanel.getReportBtn().setVisible(false);
                    buttonPanel.getPrevBtn().setDisable(true);
                    buttonPanel.getNextBtn().setDisable(true);
                } else {
                    buttonPanel.getReportBtn().setVisible(false);
                    buttonPanel.getPrevBtn().setDisable(false);
                    buttonPanel.getNextBtn().setDisable(false);
                }
                
                // Set the form fields.
                inPatientForm.setFormFields();
                
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            } 
           
           // The inpatient form was selected. 
           selectPane("inPatient");
        });
        
        // Actions that occur when the surgical form is selected.
        surgical.setOnAction((ActionEvent e) -> {
           // Only the surgical form actions should be performed. 
           buttonPanel.setSelection(3, 0);
           
           // Disable buttons that cannot be used.
           try {
                if(surgicalForm.getSurgicalList().size() < 2) {
                    buttonPanel.getReportBtn().setVisible(false);
                    buttonPanel.getPrevBtn().setDisable(true);
                    buttonPanel.getNextBtn().setDisable(true);
                } else {
                    buttonPanel.getReportBtn().setVisible(false);
                    buttonPanel.getPrevBtn().setDisable(false);
                    buttonPanel.getNextBtn().setDisable(false);
                }
                
                // Set the form fields.
                surgicalForm.setFormFields();
                
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
           
           // The surgical form was selected.
           selectPane("surgical");
        });
        
        return menuBar;
    }  
    
    /**
     * Selects which form should be visible to the user.
     * @param form 
     */
    private void selectPane(String form) {
                
        switch (form) {
            case "patient":
                patientForm.setVisible(true);
                medicationForm.setVisible(false);
                surgicalForm.setVisible(false);
                inPatientForm.setVisible(false);
                patientForm.toFront();
                break;
            case "medication":
                patientForm.setVisible(false);                
                medicationForm.setVisible(true);
                surgicalForm.setVisible(false);
                inPatientForm.setVisible(false);                
                medicationForm.toFront();
                break;
            case "inPatient":
                patientForm.setVisible(false);
                medicationForm.setVisible(false);
                surgicalForm.setVisible(false);
                inPatientForm.setVisible(true);
                inPatientForm.toFront();
                break;
            case "surgical":
                patientForm.setVisible(false);                
                medicationForm.setVisible(false);
                surgicalForm.setVisible(true);
                inPatientForm.setVisible(false);
                surgicalForm.toFront();
                break;                
            default:
                medicationForm.setVisible(false);
                patientForm.setVisible(true);
                surgicalForm.setVisible(false);
                inPatientForm.setVisible(false);                
                patientForm.toFront();
                break;
        }
        
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
