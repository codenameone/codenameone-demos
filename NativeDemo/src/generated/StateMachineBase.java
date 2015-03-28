/**
 * This class contains generated code from the Codename One Designer, DO NOT MODIFY!
 * This class is designed for subclassing that way the code generator can overwrite it
 * anytime without erasing your changes which should exist in a subclass!
 * For details about this file and how it works please read this blog post:
 * http://lwuit.blogspot.com/2010/10/ui-builder-class-how-to-actually-use.html
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
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("ComponentGroup", com.codename1.ui.ComponentGroup.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.openLayered(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        if(res != null) {
            setResourceFilePath(resPath);
            setResourceFile(res);
            return showForm("GUI 1", null);
        } else {
            Form f = (Form)createContainer(resPath, "GUI 1");
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("ComponentGroup", com.codename1.ui.ComponentGroup.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.openLayered(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        return createContainer(resPath, "GUI 1");
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

    public com.codename1.ui.Form findGUI1(Container root) {
        return (com.codename1.ui.Form)findByName("GUI 1", root);
    }

    public com.codename1.ui.ComponentGroup findComponentGroup(Container root) {
        return (com.codename1.ui.ComponentGroup)findByName("ComponentGroup", root);
    }

    public com.codename1.ui.Label findLabel(Container root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.Button findAddNativeButton(Container root) {
        return (com.codename1.ui.Button)findByName("addNativeButton", root);
    }

    public com.codename1.ui.TextField findNameForNativeButton(Container root) {
        return (com.codename1.ui.TextField)findByName("nameForNativeButton", root);
    }

    protected void exitForm(Form f) {
        if("GUI 1".equals(f.getName())) {
            exitGUI1(f);
            return;
        }

    }


    protected void exitGUI1(Form f) {
    }

    protected void beforeShow(Form f) {
        if("GUI 1".equals(f.getName())) {
            beforeGUI1(f);
            return;
        }

    }


    protected void beforeGUI1(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("GUI 1".equals(c.getName())) {
            beforeContainerGUI1(c);
            return;
        }

    }


    protected void beforeContainerGUI1(Container c) {
    }

    protected void postShow(Form f) {
        if("GUI 1".equals(f.getName())) {
            postGUI1(f);
            return;
        }

    }


    protected void postGUI1(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("GUI 1".equals(c.getName())) {
            postContainerGUI1(c);
            return;
        }

    }


    protected void postContainerGUI1(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("GUI 1".equals(rootName)) {
            onCreateGUI1();
            return;
        }

    }


    protected void onCreateGUI1() {
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        if(rootContainerName == null) return;
        if(rootContainerName.equals("GUI 1")) {
            if("nameForNativeButton".equals(c.getName())) {
                onGUI1_NameForNativeButtonAction(c, event);
                return;
            }
            if("addNativeButton".equals(c.getName())) {
                onGUI1_AddNativeButtonAction(c, event);
                return;
            }
        }
    }

      protected void onGUI1_NameForNativeButtonAction(Component c, ActionEvent event) {
      }

      protected void onGUI1_AddNativeButtonAction(Component c, ActionEvent event) {
      }

}
