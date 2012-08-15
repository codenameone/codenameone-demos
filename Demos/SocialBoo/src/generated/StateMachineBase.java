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
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("MultiButton", com.codename1.components.MultiButton.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextArea", com.codename1.ui.TextArea.class);
        UIBuilder.registerCustomComponent("Slider", com.codename1.ui.Slider.class);
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
            return showForm("Splash", null);
        } else {
            Form f = (Form)createContainer(resPath, "Splash");
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("MultiButton", com.codename1.components.MultiButton.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextArea", com.codename1.ui.TextArea.class);
        UIBuilder.registerCustomComponent("Slider", com.codename1.ui.Slider.class);
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
        return createContainer(resPath, "Splash");
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

    public com.codename1.ui.Container findContainer4(Container root) {
        return (com.codename1.ui.Container)findByName("Container4", root);
    }

    public com.codename1.ui.Container findContainer3(Container root) {
        return (com.codename1.ui.Container)findByName("Container3", root);
    }

    public com.codename1.ui.Container findContainer2(Container root) {
        return (com.codename1.ui.Container)findByName("Container2", root);
    }

    public com.codename1.ui.Container findContainer1(Container root) {
        return (com.codename1.ui.Container)findByName("Container1", root);
    }

    public com.codename1.ui.Container findContainer8(Container root) {
        return (com.codename1.ui.Container)findByName("Container8", root);
    }

    public com.codename1.ui.Container findContainer7(Container root) {
        return (com.codename1.ui.Container)findByName("Container7", root);
    }

    public com.codename1.ui.Container findContainer6(Container root) {
        return (com.codename1.ui.Container)findByName("Container6", root);
    }

    public com.codename1.ui.Container findContainer5(Container root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Button findButton2(Container root) {
        return (com.codename1.ui.Button)findByName("Button2", root);
    }

    public com.codename1.ui.Label findLabel111(Container root) {
        return (com.codename1.ui.Label)findByName("Label111", root);
    }

    public com.codename1.ui.TextArea findTextArea(Container root) {
        return (com.codename1.ui.TextArea)findByName("TextArea", root);
    }

    public com.codename1.ui.Container findContainer9(Container root) {
        return (com.codename1.ui.Container)findByName("Container9", root);
    }

    public com.codename1.ui.Form findSplash(Container root) {
        return (com.codename1.ui.Form)findByName("Splash", root);
    }

    public com.codename1.ui.Slider findSlider(Container root) {
        return (com.codename1.ui.Slider)findByName("Slider", root);
    }

    public com.codename1.ui.Label findPersonName(Container root) {
        return (com.codename1.ui.Label)findByName("personName", root);
    }

    public com.codename1.ui.Button findButton21(Container root) {
        return (com.codename1.ui.Button)findByName("Button21", root);
    }

    public com.codename1.ui.Button findButton(Container root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.Form findMain(Container root) {
        return (com.codename1.ui.Form)findByName("Main", root);
    }

    public com.codename1.ui.Container findFriends(Container root) {
        return (com.codename1.ui.Container)findByName("friends", root);
    }

    public com.codename1.ui.Label findLabel1(Container root) {
        return (com.codename1.ui.Label)findByName("Label1", root);
    }

    public com.codename1.ui.Label findLabel3(Container root) {
        return (com.codename1.ui.Label)findByName("Label3", root);
    }

    public com.codename1.ui.Label findLabel2(Container root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Container findContainer(Container root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Label findLabel17(Container root) {
        return (com.codename1.ui.Label)findByName("Label17", root);
    }

    public com.codename1.ui.Label findLabel16(Container root) {
        return (com.codename1.ui.Label)findByName("Label16", root);
    }

    public com.codename1.ui.Label findLabel15(Container root) {
        return (com.codename1.ui.Label)findByName("Label15", root);
    }

    public com.codename1.ui.Label findLabel10(Container root) {
        return (com.codename1.ui.Label)findByName("Label10", root);
    }

    public com.codename1.ui.Label findLabel6(Container root) {
        return (com.codename1.ui.Label)findByName("Label6", root);
    }

    public com.codename1.ui.Label findLabel7(Container root) {
        return (com.codename1.ui.Label)findByName("Label7", root);
    }

    public com.codename1.ui.Label findLabel4(Container root) {
        return (com.codename1.ui.Label)findByName("Label4", root);
    }

    public com.codename1.ui.Label findLabel5(Container root) {
        return (com.codename1.ui.Label)findByName("Label5", root);
    }

    public com.codename1.ui.Label findLabel14(Container root) {
        return (com.codename1.ui.Label)findByName("Label14", root);
    }

    public com.codename1.ui.Label findAvatar(Container root) {
        return (com.codename1.ui.Label)findByName("avatar", root);
    }

    public com.codename1.ui.TextField findTextField(Container root) {
        return (com.codename1.ui.TextField)findByName("TextField", root);
    }

    public com.codename1.ui.Label findLabel12(Container root) {
        return (com.codename1.ui.Label)findByName("Label12", root);
    }

    public com.codename1.ui.Label findLabel8(Container root) {
        return (com.codename1.ui.Label)findByName("Label8", root);
    }

    public com.codename1.ui.Label findLabel11(Container root) {
        return (com.codename1.ui.Label)findByName("Label11", root);
    }

    public com.codename1.ui.Label findLabel9(Container root) {
        return (com.codename1.ui.Label)findByName("Label9", root);
    }

    public com.codename1.ui.Button findButton11(Container root) {
        return (com.codename1.ui.Button)findByName("Button11", root);
    }

    public com.codename1.ui.Label findLabel1111(Container root) {
        return (com.codename1.ui.Label)findByName("Label1111", root);
    }

    public com.codename1.ui.Tabs findTabs(Container root) {
        return (com.codename1.ui.Tabs)findByName("Tabs", root);
    }

    public com.codename1.components.MultiButton findMultiButton(Container root) {
        return (com.codename1.components.MultiButton)findByName("MultiButton", root);
    }

    public com.codename1.ui.Label findLabel(Container root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.Button findButton1(Container root) {
        return (com.codename1.ui.Button)findByName("Button1", root);
    }

    public com.codename1.ui.Container findPersonRootPanel(Container root) {
        return (com.codename1.ui.Container)findByName("personRootPanel", root);
    }

    public com.codename1.ui.Form findPerson(Container root) {
        return (com.codename1.ui.Form)findByName("Person", root);
    }

    public static final int COMMAND_PersonHttpWwwCodenameoneCom = 2;
    public static final int COMMAND_MainCommand1 = 1;
    public static final int COMMAND_PersonEdit = 3;

    protected boolean onPersonHttpWwwCodenameoneCom() {
        return false;
    }

    protected boolean onMainCommand1() {
        return false;
    }

    protected boolean onPersonEdit() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_PersonHttpWwwCodenameoneCom:
                if(onPersonHttpWwwCodenameoneCom()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainCommand1:
                if(onMainCommand1()) {
                    ev.consume();
                }
                return;

            case COMMAND_PersonEdit:
                if(onPersonEdit()) {
                    ev.consume();
                }
                return;

        }
    }

    protected void exitForm(Form f) {
        if("Splash".equals(f.getName())) {
            exitSplash(f);
            return;
        }

        if("Main".equals(f.getName())) {
            exitMain(f);
            return;
        }

        if("Person".equals(f.getName())) {
            exitPerson(f);
            return;
        }

    }


    protected void exitSplash(Form f) {
    }


    protected void exitMain(Form f) {
    }


    protected void exitPerson(Form f) {
    }

    protected void beforeShow(Form f) {
        if("Splash".equals(f.getName())) {
            beforeSplash(f);
            return;
        }

        if("Main".equals(f.getName())) {
            beforeMain(f);
            return;
        }

        if("Person".equals(f.getName())) {
            beforePerson(f);
            return;
        }

    }


    protected void beforeSplash(Form f) {
    }


    protected void beforeMain(Form f) {
    }


    protected void beforePerson(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("Splash".equals(c.getName())) {
            beforeContainerSplash(c);
            return;
        }

        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            return;
        }

        if("Person".equals(c.getName())) {
            beforeContainerPerson(c);
            return;
        }

    }


    protected void beforeContainerSplash(Container c) {
    }


    protected void beforeContainerMain(Container c) {
    }


    protected void beforeContainerPerson(Container c) {
    }

    protected void postShow(Form f) {
        if("Splash".equals(f.getName())) {
            postSplash(f);
            return;
        }

        if("Main".equals(f.getName())) {
            postMain(f);
            return;
        }

        if("Person".equals(f.getName())) {
            postPerson(f);
            return;
        }

    }


    protected void postSplash(Form f) {
    }


    protected void postMain(Form f) {
    }


    protected void postPerson(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Splash".equals(c.getName())) {
            postContainerSplash(c);
            return;
        }

        if("Main".equals(c.getName())) {
            postContainerMain(c);
            return;
        }

        if("Person".equals(c.getName())) {
            postContainerPerson(c);
            return;
        }

    }


    protected void postContainerSplash(Container c) {
    }


    protected void postContainerMain(Container c) {
    }


    protected void postContainerPerson(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Splash".equals(rootName)) {
            onCreateSplash();
            return;
        }

        if("Main".equals(rootName)) {
            onCreateMain();
            return;
        }

        if("Person".equals(rootName)) {
            onCreatePerson();
            return;
        }

    }


    protected void onCreateSplash() {
    }


    protected void onCreateMain() {
    }


    protected void onCreatePerson() {
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
            if("MultiButton".equals(c.getName())) {
                onMain_MultiButtonAction(c, event);
                return;
            }
            if("TextField".equals(c.getName())) {
                onMain_TextFieldAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onMain_ButtonAction(c, event);
                return;
            }
            if("Button1".equals(c.getName())) {
                onMain_Button1Action(c, event);
                return;
            }
            if("Button2".equals(c.getName())) {
                onMain_Button2Action(c, event);
                return;
            }
            if("Button11".equals(c.getName())) {
                onMain_Button11Action(c, event);
                return;
            }
            if("Button21".equals(c.getName())) {
                onMain_Button21Action(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Person")) {
            if("TextArea".equals(c.getName())) {
                onPerson_TextAreaAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onPerson_ButtonAction(c, event);
                return;
            }
        }
    }

      protected void onMain_MultiButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_TextFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_Button1Action(Component c, ActionEvent event) {
      }

      protected void onMain_Button2Action(Component c, ActionEvent event) {
      }

      protected void onMain_Button11Action(Component c, ActionEvent event) {
      }

      protected void onMain_Button21Action(Component c, ActionEvent event) {
      }

      protected void onPerson_TextAreaAction(Component c, ActionEvent event) {
      }

      protected void onPerson_ButtonAction(Component c, ActionEvent event) {
      }

}
