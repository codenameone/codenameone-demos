/**
 * Your application code goes here
 */

package userclasses;

import com.codename1.push.Push;
import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.events.*;
import com.codename1.ui.util.Resources;
import java.util.Hashtable;

/**
 * State machine for the push demo sends a push message
 *
 * @author Shai Almog
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
    protected void initVars(Resources res) {
    }


    @Override
    protected void onMain_RegisterForPushAction(Component c, ActionEvent event) {
        Hashtable meta = new Hashtable();
        meta.put(com.codename1.push.Push.GOOGLE_PUSH_KEY, findGoogleProjectId(c));        
        Display.getInstance().registerPush(meta, true);
    }

    @Override
    protected void onMain_DeregisterForPushAction(Component c, ActionEvent event) {
        Display.getInstance().deregisterPush();
    }

    @Override
    protected void onMain_SendPushAction(Component c, ActionEvent event) {
        String dest = findDestinationDevice(c).getText();
        if(dest.equals("")) {
            dest = null;
        }
        boolean prod = findProductionEnvironement(c).isSelected();
        String googleServerKey = findGoogleServerKey(c).getText();
        String iOSCertURL = findIosCert(c).getText();
        String iOSCertPassword = findIosPassword(c).getText();
        String bbPushURL = findBbPushURL(c).getText();
        String bbAppId = findBbAppId(c).getText();
        String bbPassword = findBbPassword(c).getText();
        String bbPort = findBbPort(c).getText();
        Push.sendPushMessage(findPushMessage(c).getText(), dest, prod, googleServerKey, iOSCertURL, iOSCertPassword, bbPushURL, bbAppId, bbPassword, bbPort);    
    }

    @Override
    protected void beforeMain(Form f) {
        String k = Push.getDeviceKey();
        if(k != null) {
            findDeviceKey(f).setText("Device Key: " + k);
        }
    }
}
