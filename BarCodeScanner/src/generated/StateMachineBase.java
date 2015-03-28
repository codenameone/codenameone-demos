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
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {}

    public StateMachineBase(Resources res, String resPath, boolean loadTheme) {
        startApp(res, resPath, loadTheme);
    }

    public Container startApp(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("ComponentGroup", com.codename1.ui.ComponentGroup.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("RadioButton", com.codename1.ui.RadioButton.class);
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
            return showForm("Main", null);
        } else {
            Form f = (Form)createContainer(resPath, "Main");
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("ComponentGroup", com.codename1.ui.ComponentGroup.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("RadioButton", com.codename1.ui.RadioButton.class);
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

    public com.codename1.ui.Form findMain(Container root) {
        return (com.codename1.ui.Form)findByName("Main", root);
    }

    public com.codename1.ui.Form findMain() {
        return (com.codename1.ui.Form)findByName("Main", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.ComponentGroup findComponentGroup(Container root) {
        return (com.codename1.ui.ComponentGroup)findByName("ComponentGroup", root);
    }

    public com.codename1.ui.ComponentGroup findComponentGroup() {
        return (com.codename1.ui.ComponentGroup)findByName("ComponentGroup", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Button findButton(Container root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.Button findButton() {
        return (com.codename1.ui.Button)findByName("Button", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer1(Container root) {
        return (com.codename1.ui.Container)findByName("Container1", root);
    }

    public com.codename1.ui.Container findContainer1() {
        return (com.codename1.ui.Container)findByName("Container1", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.RadioButton findQrcode(Container root) {
        return (com.codename1.ui.RadioButton)findByName("qrcode", root);
    }

    public com.codename1.ui.RadioButton findQrcode() {
        return (com.codename1.ui.RadioButton)findByName("qrcode", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.RadioButton findBarcode(Container root) {
        return (com.codename1.ui.RadioButton)findByName("barcode", root);
    }

    public com.codename1.ui.RadioButton findBarcode() {
        return (com.codename1.ui.RadioButton)findByName("barcode", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer(Container root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Container findContainer() {
        return (com.codename1.ui.Container)findByName("Container", Display.getInstance().getCurrent());
    }

    public static final int COMMAND_MainExit = 1;

    protected boolean onMainExit() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_MainExit:
                if(onMainExit()) {
                    ev.consume();
                }
                return;

        }
    }

    protected void exitForm(Form f) {
        if("Main".equals(f.getName())) {
            exitMain(f);
            return;
        }

    }


    protected void exitMain(Form f) {
    }

    protected void beforeShow(Form f) {
        if("Main".equals(f.getName())) {
            beforeMain(f);
            return;
        }

    }


    protected void beforeMain(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            return;
        }

    }


    protected void beforeContainerMain(Container c) {
    }

    protected void postShow(Form f) {
        if("Main".equals(f.getName())) {
            postMain(f);
            return;
        }

    }


    protected void postMain(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Main".equals(c.getName())) {
            postContainerMain(c);
            return;
        }

    }


    protected void postContainerMain(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Main".equals(rootName)) {
            onCreateMain();
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
            if("qrcode".equals(c.getName())) {
                onMain_QrcodeAction(c, event);
                return;
            }
            if("barcode".equals(c.getName())) {
                onMain_BarcodeAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onMain_ButtonAction(c, event);
                return;
            }
        }
    }

      protected void onMain_QrcodeAction(Component c, ActionEvent event) {
      }

      protected void onMain_BarcodeAction(Component c, ActionEvent event) {
      }

      protected void onMain_ButtonAction(Component c, ActionEvent event) {
      }

}
