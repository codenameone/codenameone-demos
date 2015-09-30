package com.codename1.tests.localnotifications;


import com.codename1.notifications.LocalNotification;
import com.codename1.notifications.LocalNotificationCallback;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

public class LocalNotificationTest implements LocalNotificationCallback {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        try {
            theme = Resources.openLayered("/theme");
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
    int badgeNumber = 0;
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Hi World");
        hi.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        hi.addComponent(new Label("ID"));
        final TextField id = new TextField();
        hi.addComponent(id);
        
        hi.addComponent(new Label("Title"));
        final TextField title = new TextField();
        hi.addComponent(title);
        
        hi.addComponent(new Label("Body"));
        final TextField body = new TextField();
        hi.addComponent(body);
        
        
        hi.addComponent(new Label("Interval"));
        final ComboBox interval = new ComboBox(new Object[]{ "None", "Minute", "Hour", "Day", "Week"});
        hi.addComponent(interval);
        
        
        Button b = new Button("Send Notification");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LocalNotification n = new LocalNotification();
                n.setAlertBody(body.getText());
                n.setAlertTitle(title.getText());
                n.setId(id.getText());
                n.setBadgeNumber(badgeNumber++);

                int repeatType = LocalNotification.REPEAT_NONE;
                String selRepeat = (String)interval.getModel().getItemAt(interval.getModel().getSelectedIndex());
                if ("Minute".equals(selRepeat)) {
                    repeatType = LocalNotification.REPEAT_MINUTE;
                } else if ("Hour".equals(selRepeat)) {
                    repeatType = LocalNotification.REPEAT_HOUR;
                } else if ("Day".equals(selRepeat)) {
                    repeatType = LocalNotification.REPEAT_DAY;
                } else if( "Week".equals(selRepeat)) {
                    repeatType = LocalNotification.REPEAT_WEEK;
                }

                Display.getInstance().scheduleLocalNotification(n, System.currentTimeMillis() + 10 * 1000, repeatType);
            }
        });
        hi.addComponent(b);
        
        Button cancel = new Button("Cancel Notification");
            
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().cancelLocalNotification(id.getText());
            }
            
        });
        
        
        hi.addComponent(cancel);
        
        Button clearBadgeNumber = new Button("Clear Badge");
        clearBadgeNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().setBadgeNumber(0);
            }
        });
        hi.addComponent(clearBadgeNumber);
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    public void localNotificationReceived(String notificationId) {
        System.out.println("Received local notification "+notificationId+" in callback localNotificationReceived");
    }

}
