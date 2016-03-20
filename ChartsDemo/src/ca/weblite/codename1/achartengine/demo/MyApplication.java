package ca.weblite.codename1.achartengine.demo;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import com.codename1.demos.charts.ChartDemosForm;
import com.codename1.ui.Toolbar;


public class MyApplication {
   
    private Form current;

    private Resources theme;
    
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        //Enable Toolbar to all Forms by default
        Toolbar.setGlobalToolbar(true);
        // Pro only feature, uncomment if you have a pro subscription
        //Log.bindCrashProtection(true);
    }

    public void start() {
        if ( current != null ){
            current.show();
            return;
        }
        
        ChartDemosForm demos = new ChartDemosForm();
        current = demos;
        demos.show();       
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }
}
