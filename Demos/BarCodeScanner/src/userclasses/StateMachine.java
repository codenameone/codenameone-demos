/**
 * Your application code goes here
 */
package userclasses;

import com.codename1.codescan.CodeScanner;
import com.codename1.codescan.ScanResult;
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
     * this method should be used to initialize variables instead of the
     * constructor/class scope to avoid race conditions
     */
    protected void initVars() {
    }

    @Override
    protected void onMain_ButtonAction(Component c, ActionEvent event) {

        Form f = c.getComponentForm();
        
        RadioButton rb = findQrcode(f);
        final Container cnt = findComponentGroup(f);

        if(rb.isSelected()){
            CodeScanner.getInstance().scanQRCode(new ScanResult() {

                public void scanCompleted(String contents, String formatName, byte[] rawBytes) {
                    //barCode.setText("Bar: " + contents);
                    cnt.addComponent(new Label(contents));
                    cnt.revalidate();
                }

                public void scanCanceled() {
                    System.out.println("cancelled");
                }

                public void scanError(int errorCode, String message) {
                    System.out.println("err " + message);
                }
            });
        }else{
            CodeScanner.getInstance().scanBarCode(new ScanResult() {

                public void scanCompleted(String contents, String formatName, byte[] rawBytes) {
                    //barCode.setText("Bar: " + contents);
                    cnt.addComponent(new Label(contents));
                    cnt.revalidate();
                }

                public void scanCanceled() {
                    System.out.println("cancelled");
                }

                public void scanError(int errorCode, String message) {
                    System.out.println("err " + message);
                }
            });        
        }


    }

    protected boolean onMainExit() {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        boolean val = super.onMainExit();
        
        return val;
    }
}
