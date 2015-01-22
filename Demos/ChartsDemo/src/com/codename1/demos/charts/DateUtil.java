/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codename1.demos.charts;

import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author shannah
 * @deprecated
 */
public class DateUtil {

    public static int getTimezoneOffset(Date date) {
        return 0;
    }
    
    public static Date date(int y, int m, int d){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y+1900);
        c.set(Calendar.MONTH, m);
        c.set(Calendar.DAY_OF_MONTH, d);
        return c.getTime();
    }
    
}
