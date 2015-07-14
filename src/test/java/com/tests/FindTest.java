package com.tests;


import com.hospitalgui.model.InPatientBean;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import com.hospitalgui.persistence.HospitalDAO;
import com.hospitalgui.persistence.HospitalDAOImpl;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for the find methods in the HospitalDAOImpl.
 * 
 *  
 */
public class FindTest {

    /**
     * Test to get the number of patients I know are in the table
     * 
     * @throws SQLException
     */
    @Test
    public void testFindPatients() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<PatientBean> lhd = hd.findPatients();
        
        assertEquals("testFindPatients: ", 5, lhd.size());
    }

    /**
     * Test to get the medication for a specific patient
     * 
     * @throws SQLException
     */    
    @Test
    public void testFindPatientMedications() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<MedicationBean> m = hd.findPatientMedications(1);
        
        assertEquals("testFindPatientMedications: ", 1, m.size());
    }
    
    /**
     * Test to get the inpatients for a specific patient
     * 
     * @throws SQLException
     */    
    @Test
    public void testFindPatientInPatients() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<InPatientBean> p = hd.findPatientInPatients(1);
        
        assertEquals("testFindPatientInPatients: ", 3, p.size());
    }
    
    /**
     * Test to get the surgicals for a specific patient
     * 
     * @throws SQLException
     */    
    @Test
    public void testFindPatientSurgicals() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<SurgicalBean> s = hd.findPatientSurgicals(1);
        
        assertEquals("testFindPatientSurgicals: ", 1, s.size());
    }
    
    /**
     * Test to get all the medication records
     * 
     * @throws SQLException
     */    
    @Test
    public void testFindMedications() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<MedicationBean> m = hd.findMedications();
        
        assertEquals("testFindMedications: ", 5, m.size());
    }
    
    /**
     * Test to get all the inpatient records
     * 
     * @throws SQLException
     */    
    @Test
    public void testFindInPatients() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<InPatientBean> p = hd.findInPatients();
        
        assertEquals("testFindInPatients: ", 20, p.size());
    }
    
    /**
     * Test to get all the surgicals records
     * 
     * @throws SQLException
     */    
    @Test
    public void testFindSurgicals() throws SQLException {
        HospitalDAO hd = new HospitalDAOImpl();
        List<SurgicalBean> s = hd.findSurgicals();
        
        assertEquals("testFindSurgicals: ", 5, s.size());
    }
}
