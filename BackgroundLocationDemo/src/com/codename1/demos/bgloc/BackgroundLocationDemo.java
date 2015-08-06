package com.codename1.demos.bgloc;


import com.codename1.io.Log;
import com.codename1.location.Location;
import com.codename1.location.LocationListener;
import com.codename1.location.LocationManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.notifications.LocalNotificationCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundLocationDemo implements LocalNotificationCallback {

    private Form current;

    public void init(Object context) {
        Log.p("In init");
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch(IOException e){
            e.printStackTrace();
        }
        // Pro users - uncomment this code to get crash reports sent to you automatically
        /*Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });*/
    }
    
    public void start() {
        Log.p("In start");
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Hi World");
        hi.addComponent(new Label("Hi World"));
        hi.addComponent(new Button(new Command("Start monitoring") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                LocationManager.getLocationManager().setLocationListener(new LocationListener() {

                    public void locationUpdated(Location location) {
                        System.out.println("Location updated "+location);
                    }

                    public void providerStateChanged(int newState) {
                        
                    }
                    
                });
                //LocationManager.getLocationManager().setLocationListener(null);
                LocationManager.getLocationManager().startMonitoringBackgroundChanges();
            }
            
        }));
        
        hi.addComponent(new Button(new Command("Stop monitoring") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                LocationManager.getLocationManager().stopMonitoringBackgroundChanges();
                LocationManager.getLocationManager().setLocationListener(null);
            }
            
        }));
        
        hi.addComponent(new Button(new Command("Send Local Notification") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                LocalNotification n = new LocalNotification();
                n.setId("Hello");
                n.setAlertBody("Hello World");
                n.setAlertTitle("Look Here");
                n.setFireDate(System.currentTimeMillis() + 5000);
                n.setRepeatType(LocalNotification.REPEAT_MINUTE);
                n.setAlertSound("beep-01a.mp3");
                Display.getInstance().sendLocalNotification(n);
            }

            
        }));
        
        hi.addComponent(new Button(new Command("Cancel Notifications") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().cancelAllLocalNotifications();
            }

            
        }));
        
    
        hi.show();
        
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                System.out.println("Inside the timer task "+System.currentTimeMillis());
            }
            
        };
        //t.schedule(tt, 0, 1000);
                
    }

    public void stop() {
        Log.p("In stop");
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    public void localNotificationReceived(String notificationId) {
        System.out.println("Received local notification "+notificationId);
    }

    

}
