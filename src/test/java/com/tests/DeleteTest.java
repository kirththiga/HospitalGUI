package com.tests;

import com.hospitalgui.model.InPatientBean;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import com.hospitalgui.persistence.HospitalDAO;
import com.hospitalgui.persistence.HospitalDAOImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for the delete methods in the HospitalDAOImpl.
 * 
 *  
 */
public class DeleteTest {
    
    // This is my local MySQL server
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String password = "concordia";
    
    /**
     * Test of deletePatient method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeletePatient() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        hd.deletePatient(1);
        
        List <PatientBean> p = hd.findPatients();
        
        assertEquals("testDeletePatient: ", 4, p.size());
    }

    /**
     * Test of deleteMedication method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteMedication() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        List <MedicationBean> m = hd.findPatientMedications(5);
        hd.deleteMedication(m.get(0).getPatientID());
        
        m = hd.findMedications();
        
        assertEquals("testDeleteMedication: ", 4, m.size());
    }

    /**
     * Test of deleteInPatient method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteInPatient() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        List<InPatientBean> p = hd.findPatientInPatients(5);
        hd.deleteInPatient(p.get(0).getPatientID());
        
        p = hd.findInPatients();
        
        assertEquals("testDeleteInPatient: ", 16, p.size());
    }

    /**
     * Test of deleteSurgical method, of class HospitalDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteSurgical() throws Exception {
        HospitalDAO hd = new HospitalDAOImpl();
        List<SurgicalBean> s = hd.findPatientSurgicals(5);
        hd.deleteSurgical(s.get(0).getPatientID());
        
        s = hd.findSurgicals();
        
        assertEquals("testDeleteSurgical: ", 4, s.size());
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
