package com.hospitalgui.persistence;

import com.hospitalgui.model.BillingReportBean;
import com.hospitalgui.model.InPatientBean;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the methods in the billing report interface.
 * 
 *  
 */
public class BillingReportDAOImpl implements BillingReportDAO {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    private HospitalDAO hd;
    private BillingReportBean report;

    public BillingReportDAOImpl() {
        super();
        hd = new HospitalDAOImpl();
        report = new BillingReportBean();
    }
    
    /***
     * Calculating the various totals for each patient and 
     * storing it in a billing report bean.
     * @param id
     * @param patientBean
     * @param dateOfBilling
     * @return
     * @throws SQLException 
     */
    @Override
    public BillingReportBean totalCost(int id, PatientBean patientBean, Timestamp dateOfBilling) throws SQLException {
        ObservableList<MedicationBean> medicationBean = patientBean.getMedicationList();
        ObservableList<InPatientBean> inPatientBean = patientBean.getInPatientList();
        ObservableList<SurgicalBean> surgicalBean = patientBean.getSurgicalList();
        
        BigDecimal medicationTotal = BigDecimal.valueOf(0.00);
        BigDecimal inPatientTotal = BigDecimal.valueOf(0.00);
        BigDecimal inPatientTotal2 = BigDecimal.valueOf(0.00);
        BigDecimal inPatientTotal3 = BigDecimal.valueOf(0.00);
        BigDecimal surgicalTotal = BigDecimal.valueOf(0.00);
        BigDecimal surgicalTotal2 = BigDecimal.valueOf(0.00);
        BigDecimal surgicalTotal3 = BigDecimal.valueOf(0.00);
        BigDecimal grandTotal = BigDecimal.valueOf(0.00);
        
        // Calculated the total for medication
        for(int i=0; i<medicationBean.size(); i++) {
            BigDecimal unitCost = medicationBean.get(i).getUnitCost().setScale(2, ROUND_UP);
            BigDecimal units = medicationBean.get(i).getUnits().setScale(2, ROUND_UP);
            medicationTotal = medicationTotal.add(unitCost.multiply(units));
        }
        
        // Calculated the total for inpatient
        for(int i=0; i<inPatientBean.size(); i++) {
            BigDecimal dailyRate = inPatientBean.get(i).getDailyRate().setScale(2, ROUND_UP);
            BigDecimal supplies = inPatientBean.get(i).getSupplies().setScale(2, ROUND_UP);
            BigDecimal services = inPatientBean.get(i).getServices().setScale(2, ROUND_UP);
            inPatientTotal = inPatientTotal.add(dailyRate);
            inPatientTotal2 = inPatientTotal2.add(supplies);
            inPatientTotal3 = inPatientTotal3.add(services);
        }
        
        inPatientTotal = inPatientTotal.add(inPatientTotal2.add(inPatientTotal3));
        
        // Calculated the total for surgical
        for(int i=0; i<surgicalBean.size(); i++) {
            BigDecimal roomFee = surgicalBean.get(i).getRoomFee().setScale(2, ROUND_UP);
            BigDecimal surgeonFee = surgicalBean.get(i).getSurgeonFee().setScale(2, ROUND_UP);            
            BigDecimal supplies = surgicalBean.get(i).getSupplies().setScale(2, ROUND_UP);
            surgicalTotal = surgicalTotal.add(roomFee);
            surgicalTotal2 = surgicalTotal2.add(surgeonFee);            
            surgicalTotal3 = surgicalTotal3.add(supplies);
        }
        
        surgicalTotal = surgicalTotal.add(surgicalTotal2.add(surgicalTotal3));
        
        // Calculated the grand total by adding the other totals
        grandTotal = medicationTotal.add(inPatientTotal.add(surgicalTotal));
        
        // Setting the billing report information.
        report.setId(id);
        report.setPatientID(patientBean.getPatientID());
        report.setDateOfBilling(dateOfBilling);
        report.setMedicationTotal(medicationTotal.setScale(2, ROUND_UP));
        report.setInPatientTotal(inPatientTotal.setScale(2, ROUND_UP));
        report.setSurgicalTotal(surgicalTotal.setScale(2, ROUND_UP));
        report.setGrandTotal(grandTotal.setScale(2, ROUND_UP));
        
        log.info("\nReport: " + report.toString());
        return report;
    }
    
}