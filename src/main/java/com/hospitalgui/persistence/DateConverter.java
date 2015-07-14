package com.hospitalgui.persistence;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javafx.scene.control.DatePicker;

/**
 * This class validates the date entered by the user, and 
 * converts the date to timestamp or local date.
 * 
 *  
 */
public class DateConverter {
    
    private final String DATE_FORMAT = "dd-mm-yyyy";   
    
    public DateConverter() {
    }
    
    /**
     * Convert local date into timestamp.
     * @param d
     * @return 
     */
    public Timestamp convertToTimestamp(DatePicker d) {
        
        boolean check = isValidDate(d);
        
        // Initialize timestamp to null which indicates error.
        Timestamp s = null;
        
        /* If the local date is in the correct format, then determine 
         * the timestamp value.
         */
        if(check == true) {
            LocalDate ld = d.getValue();
            Instant i = ld.atTime(10, 0, 0, 0).toInstant(ZoneOffset.UTC);
            LocalDateTime ldt = LocalDateTime.ofInstant(i, ZoneOffset.UTC);
        
            String ldt2 = ldt.toString().replace("T", "");

            s = Timestamp.valueOf(ldt);
        }
        
        return s;
    }
    
    /**
     * COnvert timestamp into local date.
     * @param s
     * @return 
     */
    public LocalDate convertToLocalDate(Timestamp s) {
        LocalDate ld= s.toLocalDateTime().toLocalDate();

        return ld;
    }
    
    /**
     * Validate that the local date is in the correct format.
     * @param d
     * @return 
     */
    public boolean isValidDate(DatePicker d) {
        boolean check = true;
        
        LocalDate ld = null;
        
        try {
            ld = d.getValue();

        } catch (NullPointerException e) {
            check = false;
        }
        
        return check;
    }
}
