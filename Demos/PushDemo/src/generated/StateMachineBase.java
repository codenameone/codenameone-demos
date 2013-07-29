/**
 * This class contains generated code from the Codename One Designer, DO NOT MODIFY!
 * This class is designed for subclassing that way the code generator can overwrite it
 * anytime without erasing your changes which should exist in a subclass!
 * For details about this file and how it works please read this blog post:
 * http://codenameone.blogspot.com/2010/10/ui-builder-class-how-to-actually-use.html
*/
package generated;

import com.codename1.ui.*;
import com.codename1.ui.util.*;
import com.codename1.ui.plaf.*;
import com.codename1.ui.events.*;

public abstract class StateMachineBase extends UIBuilder {
    private Container aboutToShowThisContainer;
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    /**
    * @deprecated use the version that accepts a resource as an argument instead
    
**/
    protected void initVars() {}

    protected void initVars(Resources res) {}

    public StateMachineBase(Resources res, String resPath, boolean loadTheme) {
        startApp(res, resPath, loadTheme);
    }

    public Container startApp(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    if(resPath.endsWith(".res")) {
                        res = Resources.open(resPath);
                        System.out.println("Warning: you should construct the state machine without the .res extension to allow theme overlays");
                    } else {
                        res = Resources.openLayered(resPath);
                    }
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        if(res != null) {
            setResourceFilePath(resPath);
            setResourceFile(res);
            initVars(res);
            return showForm("Main", null);
        } else {
            Form f = (Form)createContainer(resPath, "Main");
            initVars(fetchResourceFile());
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.openLayered(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        return createContainer(resPath, "Main");
    }

    protected void initTheme(Resources res) {
            String[] themes = res.getThemeResourceNames();
            if(themes != null && themes.length > 0) {
                UIManager.getInstance().setThemeProps(res.getTheme(themes[0]));
            }
    }

    public StateMachineBase() {
    }

    public StateMachineBase(String resPath) {
        this(null, resPath, true);
    }

    public StateMachineBase(Resources res) {
        this(res, null, true);
    }

    public StateMachineBase(String resPath, boolean loadTheme) {
        this(null, resPath, loadTheme);
    }

    public StateMachineBase(Resources res, boolean loadTheme) {
        this(res, null, loadTheme);
    }

    public com.codename1.ui.TextField findDestinationDevice(Component root) {
        return (com.codename1.ui.TextField)findByName("destinationDevice", root);
    }

    public com.codename1.ui.TextField findDestinationDevice() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("destinationDevice", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("destinationDevice", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findDeviceKey(Component root) {
        return (com.codename1.ui.Label)findByName("deviceKey", root);
    }

    public com.codename1.ui.Label findDeviceKey() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("deviceKey", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("deviceKey", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findGoogleServerKey(Component root) {
        return (com.codename1.ui.TextField)findByName("googleServerKey", root);
    }

    public com.codename1.ui.TextField findGoogleServerKey() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("googleServerKey", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("googleServerKey", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findSendPush(Component root) {
        return (com.codename1.ui.Button)findByName("sendPush", root);
    }

    public com.codename1.ui.Button findSendPush() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("sendPush", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("sendPush", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel1(Component root) {
        return (com.codename1.ui.Label)findByName("Label1", root);
    }

    public com.codename1.ui.Label findLabel1() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findGoogleProjectId(Component root) {
        return (com.codename1.ui.TextField)findByName("googleProjectId", root);
    }

    public com.codename1.ui.TextField findGoogleProjectId() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("googleProjectId", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("googleProjectId", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel3(Component root) {
        return (com.codename1.ui.Label)findByName("Label3", root);
    }

    public com.codename1.ui.Label findLabel3() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel2(Component root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Label findLabel2() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer(Component root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Container findContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findProductionEnvironement(Component root) {
        return (com.codename1.ui.CheckBox)findByName("productionEnvironement", root);
    }

    public com.codename1.ui.CheckBox findProductionEnvironement() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("productionEnvironement", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("productionEnvironement", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findBbAppId(Component root) {
        return (com.codename1.ui.TextField)findByName("bbAppId", root);
    }

    public com.codename1.ui.TextField findBbAppId() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("bbAppId", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("bbAppId", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findDeregisterForPush(Component root) {
        return (com.codename1.ui.Button)findByName("deregisterForPush", root);
    }

    public com.codename1.ui.Button findDeregisterForPush() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("deregisterForPush", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("deregisterForPush", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel6(Component root) {
        return (com.codename1.ui.Label)findByName("Label6", root);
    }

    public com.codename1.ui.Label findLabel6() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label6", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label6", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findBbPort(Component root) {
        return (com.codename1.ui.TextField)findByName("bbPort", root);
    }

    public com.codename1.ui.TextField findBbPort() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("bbPort", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("bbPort", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel4(Component root) {
        return (com.codename1.ui.Label)findByName("Label4", root);
    }

    public com.codename1.ui.Label findLabel4() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label4", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label4", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel5(Component root) {
        return (com.codename1.ui.Label)findByName("Label5", root);
    }

    public com.codename1.ui.Label findLabel5() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label5", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label5", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findBbPushURL(Component root) {
        return (com.codename1.ui.TextField)findByName("bbPushURL", root);
    }

    public com.codename1.ui.TextField findBbPushURL() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("bbPushURL", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("bbPushURL", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findBbPassword(Component root) {
        return (com.codename1.ui.TextField)findByName("bbPassword", root);
    }

    public com.codename1.ui.TextField findBbPassword() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("bbPassword", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("bbPassword", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findRegisterForPush(Component root) {
        return (com.codename1.ui.Button)findByName("registerForPush", root);
    }

    public com.codename1.ui.Button findRegisterForPush() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("registerForPush", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("registerForPush", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLbl(Component root) {
        return (com.codename1.ui.Label)findByName("lbl", root);
    }

    public com.codename1.ui.Label findLbl() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("lbl", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("lbl", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findIosCert(Component root) {
        return (com.codename1.ui.TextField)findByName("iosCert", root);
    }

    public com.codename1.ui.TextField findIosCert() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("iosCert", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("iosCert", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findIosPassword(Component root) {
        return (com.codename1.ui.TextField)findByName("iosPassword", root);
    }

    public com.codename1.ui.TextField findIosPassword() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("iosPassword", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("iosPassword", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel(Component root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.Label findLabel() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findPushMessage(Component root) {
        return (com.codename1.ui.TextField)findByName("pushMessage", root);
    }

    public com.codename1.ui.TextField findPushMessage() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("pushMessage", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("pushMessage", aboutToShowThisContainer);
        }
        return cmp;
    }

    protected void exitForm(Form f) {
        if("Main".equals(f.getName())) {
            exitMain(f);
            aboutToShowThisContainer = null;
            return;
        }

    }


    protected void exitMain(Form f) {
    }

    protected void beforeShow(Form f) {
    aboutToShowThisContainer = f;
        if("Main".equals(f.getName())) {
            beforeMain(f);
            aboutToShowThisContainer = null;
            return;
        }

    }


    protected void beforeMain(Form f) {
    }

    protected void beforeShowContainer(Container c) {
    aboutToShowThisContainer = c;
        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

    }


    protected void beforeContainerMain(Container c) {
    }

    protected void postShow(Form f) {
        if("Main".equals(f.getName())) {
            postMain(f);
            aboutToShowThisContainer = null;
            return;
        }

    }


    protected void postMain(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Main".equals(c.getName())) {
            postContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

    }


    protected void postContainerMain(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Main".equals(rootName)) {
            onCreateMain();
            aboutToShowThisContainer = null;
            return;
        }

    }


    protected void onCreateMain() {
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        if(c.getParent().getLeadParent() != null) {
            c = c.getParent().getLeadParent();
        }
        if(rootContainerName == null) return;
        if(rootContainerName.equals("Main")) {
            if("registerForPush".equals(c.getName())) {
                onMain_RegisterForPushAction(c, event);
                return;
            }
            if("deregisterForPush".equals(c.getName())) {
                onMain_DeregisterForPushAction(c, event);
                return;
            }
            if("sendPush".equals(c.getName())) {
                onMain_SendPushAction(c, event);
                return;
            }
            if("pushMessage".equals(c.getName())) {
                onMain_PushMessageAction(c, event);
                return;
            }
            if("destinationDevice".equals(c.getName())) {
                onMain_DestinationDeviceAction(c, event);
                return;
            }
            if("googleProjectId".equals(c.getName())) {
                onMain_GoogleProjectIdAction(c, event);
                return;
            }
            if("googleServerKey".equals(c.getName())) {
                onMain_GoogleServerKeyAction(c, event);
                return;
            }
            if("productionEnvironement".equals(c.getName())) {
                onMain_ProductionEnvironementAction(c, event);
                return;
            }
            if("iosCert".equals(c.getName())) {
                onMain_IosCertAction(c, event);
                return;
            }
            if("iosPassword".equals(c.getName())) {
                onMain_IosPasswordAction(c, event);
                return;
            }
            if("bbPushURL".equals(c.getName())) {
                onMain_BbPushURLAction(c, event);
                return;
            }
            if("bbAppId".equals(c.getName())) {
                onMain_BbAppIdAction(c, event);
                return;
            }
            if("bbPassword".equals(c.getName())) {
                onMain_BbPasswordAction(c, event);
                return;
            }
            if("bbPort".equals(c.getName())) {
                onMain_BbPortAction(c, event);
                return;
            }
        }
    }

      protected void onMain_RegisterForPushAction(Component c, ActionEvent event) {
      }

      protected void onMain_DeregisterForPushAction(Component c, ActionEvent event) {
      }

      protected void onMain_SendPushAction(Component c, ActionEvent event) {
      }

      protected void onMain_PushMessageAction(Component c, ActionEvent event) {
      }

      protected void onMain_DestinationDeviceAction(Component c, ActionEvent event) {
      }

      protected void onMain_GoogleProjectIdAction(Component c, ActionEvent event) {
      }

      protected void onMain_GoogleServerKeyAction(Component c, ActionEvent event) {
      }

      protected void onMain_ProductionEnvironementAction(Component c, ActionEvent event) {
      }

      protected void onMain_IosCertAction(Component c, ActionEvent event) {
      }

      protected void onMain_IosPasswordAction(Component c, ActionEvent event) {
      }

      protected void onMain_BbPushURLAction(Component c, ActionEvent event) {
      }

      protected void onMain_BbAppIdAction(Component c, ActionEvent event) {
      }

      protected void onMain_BbPasswordAction(Component c, ActionEvent event) {
      }

      protected void onMain_BbPortAction(Component c, ActionEvent event) {
      }

}
