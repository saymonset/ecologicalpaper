/*
 * ServicioLogger.java
 *
 * Created on July 11, 2007, 12:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.util;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 *
 * @author simon
 */
public class ServicioLogger {
     
    private static final String LOGGER_NAME = "TESLog";
    private static final String LOG_FILE = "/ecological" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + Calendar.getInstance().get(Calendar.MONTH)+"_"+Calendar.getInstance().get(Calendar.YEAR) + ".log";
    private static final Level LOG_LEVEL = Level.FINE;
    
    /** Creates a new instance of ServicioLogger */
    public ServicioLogger() {
    }
    
    
    
    
    
    public static Logger logger = Logger.getLogger(LOGGER_NAME);
    
    static {
        try {
            FileHandler fileLog = new FileHandler(System.getProperties().getProperty("user.home")+LOG_FILE, true);
            fileLog.setFormatter(new SimpleFormatter());
            logger.addHandler(fileLog);
            logger.setLevel(LOG_LEVEL);
        } catch (Exception e) {
            System.err.println("["+LOGGER_NAME+"] ERROR: LogService: No se pudo inicializar el servicio de log: " + e);
        }
    }
}
