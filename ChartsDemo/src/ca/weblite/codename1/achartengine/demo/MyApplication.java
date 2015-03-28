package ca.weblite.codename1.achartengine.demo;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import com.codename1.demos.charts.ChartDemosForm;


public class MyApplication {
   
    private Form current;

    public void init(Object context) {
        try{
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
            //UIManager.getInstance().setThemeProps(theme.getTheme("Theme 1"));
       }catch(IOException e){
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
