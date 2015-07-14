package com.hospitalgui.controls;

import com.hospitalgui.InPatientForm;
import com.hospitalgui.MainApp;
import com.hospitalgui.MedicationForm;
import com.hospitalgui.PatientForm;
import com.hospitalgui.SurgicalForm;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class contains the layout and actions performed
 * on the buttons.
 * 
 *  
 */
public class ButtonPanel extends VBox{
    
    private PatientForm patientForm;
    private MedicationForm medicationForm;
    private InPatientForm inPatientForm;
    private SurgicalForm surgicalForm;
    private boolean[] selection;
    private Button reportBtn;
    private Button prevBtn;
    private Button nextBtn;

    public ButtonPanel(PatientForm patientForm, MedicationForm medicationForm, InPatientForm inPatientForm, SurgicalForm surgicalForm, boolean[] selection) {
        this.patientForm = patientForm;
        this.medicationForm = medicationForm;
        this.inPatientForm = inPatientForm;
        this.surgicalForm = surgicalForm;
        this.selection = selection;
        reportBtn = new Button("Report");
        prevBtn = new Button("Previous");
        nextBtn = new Button("Next");
        buttonBox();
    }
    
    /**
     * Getters for the report, previous, and next button.
     * @return 
     */
    public Button getReportBtn() {
        return reportBtn;
    }

    public Button getPrevBtn() {
        return prevBtn;
    }

    public Button getNextBtn() {
        return nextBtn;
    }
    
    /**
     * To select which actions should be performed, 
     * depending on the form that is currently active.
     * @param t
     * @param f
     * @return 
     */
    public boolean[] setSelection(int t, int f) {
        
        for(int i=1; i<4; i++) {
            selection[i] = false;
        }
        
        selection[t] = true;
        selection[f] = false;
        return selection;
    }
    
    /**
     * Layout and actions for the buttons
     */
    private void buttonBox() {
        // Buttons to perform the necessary actions 
        Button clearBtn = new Button("Clear");
        Button findBtn = new Button("Find");
        Button saveBtn = new Button("Save");
        Button deleteBtn = new Button("Delete");
        
        HBox btnRow1 = new HBox();
        btnRow1.setId("btnRow1");
        HBox btnRow2 = new HBox();
        btnRow2.setId("btnRow2");
        btnRow1.getChildren().addAll(clearBtn, findBtn, saveBtn);
        btnRow2.getChildren().addAll(prevBtn, nextBtn, deleteBtn, reportBtn);
        
        getChildren().addAll(new Separator(), btnRow1, btnRow2);
        
        // Actions that need to be performed for each button in different forms.
        clearBtn.setOnAction((ActionEvent e) -> {
            if(selection[0] == true) {
                patientForm.clear();
            } else if(selection[1] == true) {
                medicationForm.clear();
            } else if(selection[2] == true) {
                inPatientForm.clear();
            } else if(selection[3] == true) {
                surgicalForm.clear();
            }
        });
        
        findBtn.setOnAction((ActionEvent e) -> {
            try {
                if(selection[0] == true) {
                    patientForm.find();
                } else if(selection[1] == true) {
                    medicationForm.find();
                } else if(selection[2] == true) {
                    inPatientForm.find();
                } else if(selection[3] == true) {
                    surgicalForm.find();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });      
        
        saveBtn.setOnAction((ActionEvent e) -> {
            try {
                if(selection[0] == true) {
                    patientForm.save();
                } else if(selection[1] == true) {
                    medicationForm.save();
                } else if(selection[2] == true) {
                    inPatientForm.save();
                } else if(selection[3] == true) {
                    surgicalForm.save();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        reportBtn.setOnAction((ActionEvent e) -> {
            try {
                patientForm.report();
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        prevBtn.setOnAction((ActionEvent e) -> {
            try {
                if(selection[0] == true) {
                    patientForm.prev();
                } else if(selection[1] == true) {
                    medicationForm.prev();
                } else if(selection[2] == true) {
                    inPatientForm.prev();
                } else if(selection[3] == true) {
                    surgicalForm.prev();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
                
        nextBtn.setOnAction((ActionEvent e) -> {
            try {
                if(selection[0] == true) {
                    patientForm.next();
                } else if(selection[1] == true) {
                    medicationForm.next();
                } else if(selection[2] == true) {
                    inPatientForm.next();
                } else if(selection[3] == true) {
                    surgicalForm.next();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        deleteBtn.setOnAction((ActionEvent e) -> {
            try {
                if(selection[0] == true) {
                    patientForm.delete();
                } else if(selection[1] == true) {
                    medicationForm.delete();
                } else if(selection[2] == true) {
                    inPatientForm.delete();
                } else if(selection[3] == true) {
                    surgicalForm.delete();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });           
    } 
    
}
