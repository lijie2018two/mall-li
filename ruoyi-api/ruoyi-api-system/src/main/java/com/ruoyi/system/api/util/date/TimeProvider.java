package com.ruoyi.system.api.util.date;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hyyt
 *
 */
@Component
public class TimeProvider implements Serializable {

    private static final long serialVersionUID = -3301695478208950415L;
    
    /**
     * getCurrentDate
     * @return Date
     */
    public static Date now() {
    	long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis);
    }
    /**
     * getCurrentTimestamp
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
    	long currentMillis = System.currentTimeMillis();
    	return new Timestamp(currentMillis);
    }
    
    /**
     * getCurrentTimeInt
     * @return Timestamp
     */
    public static long getCurrentTimeInt() {
    	long currentMillis = System.currentTimeMillis();
    	return currentMillis;
    }
    
    /**
     * getCurrentTimeInt
     * @return Timestamp
     */
    public static long getCurrentMonthFirstDay() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH, 0);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);  
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    	return calendar.getTimeInMillis();
    }
    
    /**
     * getCurrentTimeInt
     * @return Timestamp
     */
    public static long getCurrentMonthLastDay() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH, 1);  
    	calendar.set(Calendar.DAY_OF_MONTH, 0);
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    	return calendar.getTimeInMillis();
    }
    
    /**
     * getCurrentTimeInt 00:00:00
     * @return Timestamp
     */
    public static long getCurrentDateInt() {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date zero = calendar.getTime();
        return zero.getTime();
    }
}
