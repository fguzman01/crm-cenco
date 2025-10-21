package cencosud.util;

import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  

public class DateFile {

    public String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    
}
