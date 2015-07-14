package com.hospitalgui.persistence;

import com.hospitalgui.model.BillingReportBean;
import com.hospitalgui.model.PatientBean;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The interface for the billing report.
 * 
 *  
 */
public interface BillingReportDAO {
    
    public BillingReportBean totalCost(int id, PatientBean patientBean, Timestamp dateOfBilling) throws SQLException;
    
}
