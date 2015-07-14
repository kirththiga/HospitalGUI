package com.hospitalgui.persistence;

import com.hospitalgui.model.InPatientBean;
import com.hospitalgui.model.MedicationBean;
import com.hospitalgui.model.PatientBean;
import com.hospitalgui.model.SurgicalBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the CRUD methods
 * 
 *  
 */
public class HospitalDAOImpl implements HospitalDAO{
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    private final String url = "jdbc:mysql://localhost:3306/HOSPITALDB";
    private final String user = "TheUser";
    private final String password = "pancake";

    public HospitalDAOImpl() {
        super();
    }

    // Create Operations
   /***
    * Create a patient
    * @param patientBean
    * @return
    * @throws SQLException 
    */ 
   @Override    
    public int createPatient(PatientBean patientBean) throws SQLException {
        int result;
        String createQuery = "INSERT INTO PATIENT (LASTNAME, FIRSTNAME, DIAGNOSIS, ADMISSIONDATE, RELEASEDATE) VALUES (?,?,?,?,?)";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, patientBean.getLastName());
            ps.setString(2, patientBean.getFirstName());
            ps.setString(3, patientBean.getDiagnosis());
            ps.setTimestamp(4, patientBean.getAdmissionDate());
            ps.setTimestamp(5, patientBean.getReleaseDate());

            result = ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                patientBean.setPatientID(rs.getInt(1));
            }
            
        }
        log.info("# of records created : " + result + "\n" + patientBean.toString());
        return result;
    }
    
    /***
     * Create a medication
     * @param medicationBean
     * @return
     * @throws SQLException 
     */
    @Override    
    public int createMedication(MedicationBean medicationBean) throws SQLException {
        int result;
        String createQuery = "INSERT INTO MEDICATION (PATIENTID, DATEOFMED, MED, UNITCOST, UNITS) VALUES (?,?,?,?,?)";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, medicationBean.getPatientID());
            ps.setTimestamp(2, medicationBean.getDateOfMed());
            ps.setString(3, medicationBean.getMed());
            ps.setBigDecimal(4, medicationBean.getUnitCost());
            ps.setBigDecimal(5, medicationBean.getUnits());

            result = ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                medicationBean.setId(rs.getInt(1));
            }
        }
        log.info("# of records created : " + result + "\n" + medicationBean.toString());
        return result;
    }
    
    /***
     * Create an inpatient
     * @param inPatientBean
     * @return
     * @throws SQLException 
     */
    @Override
    public int createInPatient(InPatientBean inPatientBean) throws SQLException {
        int result;
        String createQuery = "INSERT INTO INPATIENT (PATIENTID, DATEOFSTAY, ROOMNUMBER, DAILYRATE, SUPPLIES, SERVICES) VALUES (?,?,?,?,?,?)";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, inPatientBean.getPatientID());
            ps.setTimestamp(2, inPatientBean.getDateOfStay());
            ps.setString(3, inPatientBean.getRoomNumber());
            ps.setBigDecimal(4, inPatientBean.getDailyRate());
            ps.setBigDecimal(5, inPatientBean.getSupplies());
            ps.setBigDecimal(6, inPatientBean.getServices());

            result = ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                inPatientBean.setId(rs.getInt(1));
            }
            
        }
        log.info("# of records created : " + result + "\n" + inPatientBean.toString());
        return result;
    }
    
    /***
     * Create a surgical
     * @param surgicalBean
     * @return
     * @throws SQLException 
     */
    @Override
    public int createSurgical(SurgicalBean surgicalBean) throws SQLException {
        int result;
        String createQuery = "INSERT INTO SURGICAL (PATIENTID, DATEOFSURGERY, SURGERY, ROOMFEE, SURGEONFEE, SUPPLIES) VALUES (?,?,?,?,?,?)";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, surgicalBean.getPatientID());
            ps.setTimestamp(2, surgicalBean.getDateOfSurgery());
            ps.setString(3, surgicalBean.getSurgery());
            ps.setBigDecimal(4, surgicalBean.getRoomFee());
            ps.setBigDecimal(5, surgicalBean.getSurgeonFee());
            ps.setBigDecimal(6, surgicalBean.getSupplies());

            result = ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                surgicalBean.setId(rs.getInt(1));
            }
            
        }
        log.info("# of records created : " + result + "\n" + surgicalBean.toString());
        return result;        
    }

    // Read Operations
    /***
     * Finding all the patients within the database
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<PatientBean> findPatients() throws SQLException {
        ObservableList<PatientBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT PATIENTID, LASTNAME, FIRSTNAME, DIAGNOSIS, ADMISSIONDATE, RELEASEDATE " +
                "FROM PATIENT";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PatientBean patientBean = new PatientBean();
                patientBean.setPatientID(rs.getInt("PATIENTID"));
                patientBean.setLastName(rs.getString("LASTNAME"));
                patientBean.setFirstName(rs.getString("FIRSTNAME"));
                patientBean.setDiagnosis(rs.getString("DIAGNOSIS"));
                patientBean.setAdmissionDate(rs.getTimestamp("ADMISSIONDATE"));
                patientBean.setReleaseDate(rs.getTimestamp("RELEASEDATE"));
                patientBean.setMedicationList(findPatientMedications(patientBean.getPatientID()));
                patientBean.setInPatientList(findPatientInPatients(patientBean.getPatientID()));
                patientBean.setSurgicalList(findPatientSurgicals(patientBean.getPatientID()));
                rows.add(patientBean);
            }
        }
        
        log.info("# of records found : " + rows.size());
        return rows;
    }
    
    /***
     * Find the patient id for a specific patient
     * @param patientBean
     * @throws SQLException 
     */
    @Override
    public void findPatientID(PatientBean patientBean) throws SQLException {
        
        //PatientBean patientBean = new PatientBean();
  
        String selectQuery = "SELECT LASTNAME, FIRSTNAME, DIAGNOSIS, ADMISSIONDATE, RELEASEDATE " +
                "FROM PATIENT WHERE PATIENTID = ?";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery)) {
            ps.setInt(1, patientBean.getPatientID());
            try (
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    patientBean.setPatientID(patientBean.getPatientID());
                    patientBean.setLastName(rs.getString("LASTNAME"));
                    patientBean.setFirstName(rs.getString("FIRSTNAME"));
                    patientBean.setDiagnosis(rs.getString("DIAGNOSIS"));
                    patientBean.setAdmissionDate(rs.getTimestamp("ADMISSIONDATE"));
                    patientBean.setReleaseDate(rs.getTimestamp("RELEASEDATE"));
                }
            }
        }
        patientBean.setMedicationList(findPatientMedications(patientBean.getPatientID()));
        patientBean.setInPatientList(findPatientInPatients(patientBean.getPatientID()));
        patientBean.setSurgicalList(findPatientSurgicals(patientBean.getPatientID()));
       
    }
    
    /***
     * Find the total number of medications for a specific patient.
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<MedicationBean> findPatientMedications(int patientId) throws SQLException {
        ObservableList<MedicationBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT ID, PATIENTID, DATEOFMED, MED, UNITCOST, UNITS FROM MEDICATION WHERE PATIENTID = ?";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery)) {
            ps.setInt(1, patientId);
            try (
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedicationBean medicationBean = new MedicationBean();
                    medicationBean.setId(rs.getInt("ID"));
                    medicationBean.setPatientID(patientId);
                    medicationBean.setDateOfMed(rs.getTimestamp("DATEOFMED"));
                    medicationBean.setMed(rs.getString("MED"));
                    medicationBean.setUnitCost(rs.getBigDecimal("UNITCOST"));
                    medicationBean.setUnits(rs.getBigDecimal("UNITS"));
                    rows.add(medicationBean);
                }
            }
            log.info("# of records found : " + rows.size());
            return rows;

        }

    }
    
    /***
     * Find the total number of inpatients for a specific patient.
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<InPatientBean> findPatientInPatients(int patientId) throws SQLException {
        ObservableList<InPatientBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT ID, PATIENTID, DATEOFSTAY, ROOMNUMBER, DAILYRATE, SUPPLIES, SERVICES " +
                "FROM INPATIENT WHERE PATIENTID = ?";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery)) {
            ps.setInt(1, patientId);
            try (
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InPatientBean inPatientBean = new InPatientBean();
                    inPatientBean.setId(rs.getInt("ID"));
                    inPatientBean.setPatientID(patientId);
                    inPatientBean.setDateOfStay(rs.getTimestamp("DATEOFSTAY"));
                    inPatientBean.setRoomNumber(rs.getString("ROOMNUMBER"));
                    inPatientBean.setDailyRate(rs.getBigDecimal("DAILYRATE"));
                    inPatientBean.setSupplies(rs.getBigDecimal("SUPPLIES"));
                    inPatientBean.setServices(rs.getBigDecimal("SERVICES"));
                    rows.add(inPatientBean);
                }
            }
            log.info("# of records found : " + rows.size());
            return rows;

        }
    }
    
    /***
     * Find the total number of surgicals for a specific patient.
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<SurgicalBean> findPatientSurgicals(int patientId) throws SQLException {
         ObservableList<SurgicalBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT ID, PATIENTID, DATEOFSURGERY, SURGERY, ROOMFEE, SURGEONFEE, SUPPLIES " +
                "FROM SURGICAL WHERE PATIENTID = ?";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery)) {
            ps.setInt(1, patientId);
            try (
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SurgicalBean surgicalBean = new SurgicalBean();
                    surgicalBean.setId(rs.getInt("ID"));
                    surgicalBean.setPatientID(patientId);
                    surgicalBean.setDateOfSurgery(rs.getTimestamp("DATEOFSURGERY"));
                    surgicalBean.setSurgery(rs.getString("SURGERY"));
                    surgicalBean.setRoomFee(rs.getBigDecimal("ROOMFEE"));
                    surgicalBean.setSurgeonFee(rs.getBigDecimal("SURGEONFEE"));
                    surgicalBean.setSupplies(rs.getBigDecimal("SUPPLIES"));
                    rows.add(surgicalBean);
                }
            }
            log.info("# of records found : " + rows.size());
            return rows;

        }       
    }
    
    /***
     * Find the total number of medications in the database.
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<MedicationBean> findMedications() throws SQLException {
        ObservableList<MedicationBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT ID, PATIENTID, DATEOFMED, MED, UNITCOST, UNITS FROM MEDICATION";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MedicationBean medicationBean = new MedicationBean();
                medicationBean.setId(rs.getInt("ID"));
                medicationBean.setPatientID(rs.getInt("PATIENTID"));
                medicationBean.setDateOfMed(rs.getTimestamp("DATEOFMED"));
                medicationBean.setMed(rs.getString("MED"));
                medicationBean.setUnitCost(rs.getBigDecimal("UNITCOST"));
                medicationBean.setUnits(rs.getBigDecimal("UNITS"));
                rows.add(medicationBean);
            }
        }
        log.info("# of records found : " + rows.size());
        return rows;
    }
    
    /***
     * Find the total number of inpatients in the database.
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<InPatientBean> findInPatients() throws SQLException {
        ObservableList<InPatientBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT ID, PATIENTID, DATEOFSTAY, ROOMNUMBER, DAILYRATE, SUPPLIES, SERVICES FROM INPATIENT";

        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                InPatientBean inPatientBean = new InPatientBean();
                inPatientBean.setId(rs.getInt("ID"));
                inPatientBean.setPatientID(rs.getInt("PATIENTID"));
                inPatientBean.setDateOfStay(rs.getTimestamp("DATEOFSTAY"));
                inPatientBean.setRoomNumber(rs.getString("ROOMNUMBER"));
                inPatientBean.setDailyRate(rs.getBigDecimal("DAILYRATE"));
                inPatientBean.setSupplies(rs.getBigDecimal("SUPPLIES"));
                inPatientBean.setServices(rs.getBigDecimal("SERVICES"));
                rows.add(inPatientBean);
            }
        }
            log.info("# of records found : " + rows.size());
            return rows;
    }
    
    /***
     * Find the total number of surgicals in the database.
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<SurgicalBean> findSurgicals() throws SQLException {
        ObservableList<SurgicalBean> rows = FXCollections.observableArrayList();

        String selectQuery = "SELECT ID, PATIENTID, DATEOFSURGERY, SURGERY, ROOMFEE, SURGEONFEE, SUPPLIES FROM SURGICAL";
        
        // Using Java 1.7 try with resources
        // This ensures that the objects in the parenthesis () will be closed
        // when block ends. In this case the Connection, PreparedStatement and
        // the ResultSet will all be closed.
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(selectQuery);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SurgicalBean surgicalBean = new SurgicalBean();
                surgicalBean.setId(rs.getInt("ID"));
                surgicalBean.setPatientID(rs.getInt("PATIENTID"));
                surgicalBean.setDateOfSurgery(rs.getTimestamp("DATEOFSURGERY"));
                surgicalBean.setSurgery(rs.getString("SURGERY"));
                surgicalBean.setRoomFee(rs.getBigDecimal("ROOMFEE"));
                surgicalBean.setSurgeonFee(rs.getBigDecimal("SURGEONFEE"));
                surgicalBean.setSupplies(rs.getBigDecimal("SUPPLIES"));
                rows.add(surgicalBean);
            }
        }
            log.info("# of records found : " + rows.size());
            return rows;
    }
    
    // Update Operations
    /**
     * Update a patient record in the database.
     * @param patientBean
     * @return
     * @throws SQLException 
     */
    public int updatePatient(PatientBean patientBean) throws SQLException {
        
        int result;
        
        String updateQuery = "UPDATE PATIENT SET LASTNAME=?, FIRSTNAME=?, DIAGNOSIS=?, ADMISSIONDATE=?, RELEASEDATE=? WHERE PATIENTID = ?";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(updateQuery);) {
                ps.setString(1, patientBean.getLastName());
                ps.setString(2, patientBean.getFirstName());
                ps.setString(3, patientBean.getDiagnosis());
                ps.setTimestamp(4, patientBean.getAdmissionDate());
                ps.setTimestamp(5, patientBean.getReleaseDate());
                ps.setInt(6, patientBean.getPatientID());
                
                result = ps.executeUpdate();
        }
        
        log.info("# of records updated : " + result);
        return result;
    }
    
    /**
     * Update a medication record in the database.
     * @param medicationBean
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateMedication(MedicationBean medicationBean) throws SQLException {
        
        int result;
        
        String updateQuery = "UPDATE MEDICATION SET PATIENTID=?, DATEOFMED=?, MED=?, UNITCOST=?, UNITS=? WHERE ID = ?";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(updateQuery);) {
                ps.setInt(1, medicationBean.getPatientID());
                ps.setTimestamp(2, medicationBean.getDateOfMed());
                ps.setString(3, medicationBean.getMed());
                ps.setBigDecimal(4, medicationBean.getUnitCost());
                ps.setBigDecimal(5, medicationBean.getUnits());
                ps.setInt(6, medicationBean.getId());
                
                result = ps.executeUpdate();
        }
        
        log.info("# of records updated : " + result);
        return result;
    }
    
    /**
     * Update an inpatient record in the database.
     * @param inPatientBean
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateInPatient(InPatientBean inPatientBean) throws SQLException {
        
        int result;
        
        String updateQuery = "UPDATE INPATIENT SET PATIENTID=?, DATEOFSTAY=?, ROOMNUMBER=?, DAILYRATE=?, SUPPLIES=?, SERVICES=? WHERE ID = ?";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(updateQuery);) {
            ps.setInt(1, inPatientBean.getPatientID());
            ps.setTimestamp(2, inPatientBean.getDateOfStay());
            ps.setString(3, inPatientBean.getRoomNumber());
            ps.setBigDecimal(4, inPatientBean.getDailyRate());
            ps.setBigDecimal(5, inPatientBean.getSupplies());
            ps.setBigDecimal(6, inPatientBean.getServices());
            ps.setInt(7, inPatientBean.getId());
                
                result = ps.executeUpdate();
        }
        
        log.info("# of records updated : " + result);
        return result;
    }

    /**
     * Update a surgical record in the database.
     * @param surgicalBean
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateSurgical(SurgicalBean surgicalBean) throws SQLException {
        
        int result;
        
        String updateQuery = "UPDATE SURGICAL SET PATIENTID=?, DATEOFSURGERY=?, SURGERY=?, ROOMFEE=?, SURGEONFEE=?, SUPPLIES=? WHERE ID = ?";
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against SQL
                // Injection
                PreparedStatement ps = connection.prepareStatement(updateQuery);) {
            ps.setInt(1, surgicalBean.getPatientID());
            ps.setTimestamp(2, surgicalBean.getDateOfSurgery());
            ps.setString(3, surgicalBean.getSurgery());
            ps.setBigDecimal(4, surgicalBean.getRoomFee());
            ps.setBigDecimal(5, surgicalBean.getSurgeonFee());
            ps.setBigDecimal(6, surgicalBean.getSupplies());
            ps.setInt(7, surgicalBean.getId());
                
                result = ps.executeUpdate();
        }
        
        log.info("# of records updated : " + result);
        return result;
    }
    
    // Delete Operations
    /***
     * Delete a patient from the database
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public int deletePatient(int patientId) throws SQLException {
        int result;
        
        int result2 = deleteMedication(patientId);
        result2 = deleteInPatient(patientId);
        result2 = deleteSurgical(patientId);
        
        String deleteQuery = "DELETE FROM PATIENT WHERE PATIENTID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }
    
    /***
     * Delete all medications associated to a patient
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteMedication(int patientId) throws SQLException {
        int result;

        String deleteQuery = "DELETE FROM MEDICATION WHERE PATIENTID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }

    /***
     * Delete a medication associated to a patient
     * @param patientId
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deletePatientMedication(int patientId, int id) throws SQLException {
        int result;

        String deleteQuery = "DELETE FROM MEDICATION WHERE PATIENTID = ? AND ID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }
    
    /***
     * Delete all inpatients for a specific patient
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteInPatient(int patientId) throws SQLException {
        int result;

        String deleteQuery = "DELETE FROM INPATIENT WHERE PATIENTID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }

    /***
     * Delete an inpatient for a specific patient
     * @param patientId
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deletePatientInPatient(int patientId, int id) throws SQLException {
        int result;

        String deleteQuery = "DELETE FROM INPATIENT WHERE PATIENTID = ? AND ID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }
    
    /***
     * Delete all surgicals for a specific patient
     * @param patientId
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteSurgical(int patientId) throws SQLException {
        int result;

        String deleteQuery = "DELETE FROM SURGICAL WHERE PATIENTID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }
    
    /***
     * Delete a surgical for a specific patient
     * @param patientId
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deletePatientSurgical(int patientId, int id) throws SQLException {
        int result;

        String deleteQuery = "DELETE FROM SURGICAL WHERE PATIENTID = ? AND ID = ?";
        
        // Connection is only open for the operation and then immediately closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, patientId);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        }
        
        log.info("# of records deleted : " + result);
        return result;
    }
    
}
