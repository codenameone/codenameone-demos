/**
 * Your application code goes here
 */

package userclasses;

import com.codename1.system.NativeLookup;
import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.events.*;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {
    }


    protected void onGUI1_ScanAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onGUI1_ScanAction(c, event);
        System.out.println("onGUI1_ScanAction");
        final ZXingNativeCalls zx = (ZXingNativeCalls)NativeLookup.create(ZXingNativeCalls.class);
        if(zx != null && zx.isSupported()) {
            final Form f = Display.getInstance().getCurrent();
            System.out.println("zx is running");
            new Thread() {
                public void run() {
                    System.out.println("scan invoked");
                    zx.scan();
                    while(zx.getStatus() == ZXingNativeCalls.PENDING) {
                        try {
                            sleep(300);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(zx.getStatus() == ZXingNativeCalls.ERROR) {
                        f.addComponent(new Label("Got error from zxing"));
                    } else {
                        f.addComponent(new TextArea("Format: " + zx.getType()));
                        f.addComponent(new TextArea("Content: " + zx.getResult()));
                    }
                    f.animateLayout(800);
                }
            }.start();
        }
    }
}
