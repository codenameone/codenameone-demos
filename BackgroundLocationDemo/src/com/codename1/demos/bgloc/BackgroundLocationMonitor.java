/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.bgloc;

import com.codename1.location.Location;
import com.codename1.location.LocationListener;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Display;

/**
 *
 * @author shannah
 */
public class BackgroundLocationMonitor  implements LocationListener {

    public void locationUpdated(Location location) {
        System.out.println("Background location updated "+location);
        
        LocalNotification n = new LocalNotification();
        n.setId("Some notification "+System.currentTimeMillis());
        n.setAlertBody("New Location "+location);
        n.setAlertTitle("Location updated");
        n.setBadgeNumber(2);
        n.setFireDate(System.currentTimeMillis());
        
        Display.getInstance().sendLocalNotification(n);
        
        
    }

    public void providerStateChanged(int newState) {
        System.out.println("Background provider state changed "+newState);
                
    }
    
}
