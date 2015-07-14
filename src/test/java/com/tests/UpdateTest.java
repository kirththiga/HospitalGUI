package com.tests;

import com.hospitalgui.persistence.HospitalDAOImpl;
import com.hospitalgui.model.InPatientBean;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import com.hospitalgui.persistence.HospitalDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_UP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static java.util.Calendar.MAY;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javafx.collections.ObservableList;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the update methods in the HospitalDAOImpl.
 * 
 *  
 */
public class UpdateTest {
    
    // This is my local MySQL server
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String password = "concordia";

    /**
     * Test of updatePatient method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdatePatient() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        PatientBean patientBean = new PatientBean();
        patientBean.setPatientID(1);
        patientBean.setLastName("Wales");
        patientBean.setFirstName("John");
        patientBean.setDiagnosis("Asthma");
        LocalDateTime admissionDateTime = LocalDateTime.of(2015, MAY, 11, 10, 30, 15);
        Timestamp admission = Timestamp.valueOf(admissionDateTime);
        LocalDateTime releaseDateTime = LocalDateTime.of(2015, MAY, 11, 10, 30, 15);
        Timestamp release = Timestamp.valueOf(releaseDateTime);
        patientBean.setAdmissionDate(admission);
        patientBean.setReleaseDate(release);
        
        hd.updatePatient(patientBean);
        
        List <PatientBean> list = hd.findPatients();
        PatientBean p = list.get(0);
        
        assertTrue(patientBean.equals(p));
    }

    /**
     * Test of updateMedication method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateMedication() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        MedicationBean medicationBean = new MedicationBean();
        medicationBean.setId(1);
        medicationBean.setPatientID(1);
        LocalDateTime medDateTime = LocalDateTime.of(2015, MAY, 11, 10, 30, 15);
        Timestamp med = Timestamp.valueOf(medDateTime);
        medicationBean.setDateOfMed(med);
        medicationBean.setMed("KitKat");
        medicationBean.setUnitCost(BigDecimal.valueOf(7.23));
        medicationBean.setUnits(BigDecimal.valueOf(3.00).setScale(2, ROUND_UP));
        hd.updateMedication(medicationBean);
        
        ObservableList<MedicationBean> m = hd.findPatientMedications(medicationBean.getPatientID());
        
        assertTrue(medicationBean.equals(m.get(0)));
    }

    /**
     * Test of updateInPatient method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateInPatient() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        InPatientBean inPatientBean = new InPatientBean();
        inPatientBean.setId(2);
        inPatientBean.setPatientID(1);
        LocalDateTime stayDateTime = LocalDateTime.of(2015, MAY, 11, 10, 30, 15);
        Timestamp stay = Timestamp.valueOf(stayDateTime);
        inPatientBean.setDateOfStay(stay);
        inPatientBean.setRoomNumber("A3");
        inPatientBean.setDailyRate(BigDecimal.valueOf(150.00).setScale(2, ROUND_UP));
        inPatientBean.setSupplies(BigDecimal.valueOf(120.23));
        inPatientBean.setServices(BigDecimal.valueOf(87.05));
        hd.updateInPatient(inPatientBean);
       
        ObservableList<InPatientBean> p = hd.findPatientInPatients(inPatientBean.getPatientID());
        assertTrue(inPatientBean.equals(p.get(1)));
        
    }

    /**
     * Test of updateSurgical method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateSurgical() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        SurgicalBean surgicalBean = new SurgicalBean();
        surgicalBean.setId(1);
        surgicalBean.setPatientID(1);
        LocalDateTime surgeryDateTime = LocalDateTime.of(2015, MAY, 11, 10, 30, 15);
        Timestamp surgery = Timestamp.valueOf(surgeryDateTime);
        surgicalBean.setDateOfSurgery(surgery);
        surgicalBean.setSurgery("Lung Transplant");
        surgicalBean.setRoomFee(BigDecimal.valueOf(2500.12));
        surgicalBean.setSurgeonFee(BigDecimal.valueOf(4200.00).setScale(2,ROUND_UP));
        surgicalBean.setSupplies(BigDecimal.valueOf(934.23));
        hd.updateSurgical(surgicalBean);
       
        ObservableList<SurgicalBean> s = hd.findPatientSurgicals(surgicalBean.getPatientID());
        assertTrue(surgicalBean.equals(s.get(0)));
        
    }

    /**
     * This routine recreates the database for every test. This makes sure that
     * a destructive test will not interfere with any other test.
     *
     * This routine is courtesy of Bartosz Majsak, an Arquillian developer at
     * JBoss
     */
    @After
    public void seedDatabase() {
        final String seedDataScript = loadAsString("hospitaldb.sql");
        try (Connection connection = DriverManager.getConnection(url, user,
                password);) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
}
