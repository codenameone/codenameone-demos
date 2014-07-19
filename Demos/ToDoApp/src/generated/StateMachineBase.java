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
        UIBuilder.registerCustomComponent("ComponentGroup", com.codename1.ui.ComponentGroup.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("MultiButton", com.codename1.components.MultiButton.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
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
        UIBuilder.registerCustomComponent("ComponentGroup", com.codename1.ui.ComponentGroup.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("MultiButton", com.codename1.components.MultiButton.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
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

    public com.codename1.ui.Container findContainer3(Component root) {
        return (com.codename1.ui.Container)findByName("Container3", root);
    }

    public com.codename1.ui.Container findContainer3() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findImportXML(Component root) {
        return (com.codename1.ui.Button)findByName("importXML", root);
    }

    public com.codename1.ui.Button findImportXML() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("importXML", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("importXML", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.ComponentGroup findComponentGroup1(Component root) {
        return (com.codename1.ui.ComponentGroup)findByName("ComponentGroup1", root);
    }

    public com.codename1.ui.ComponentGroup findComponentGroup1() {
        com.codename1.ui.ComponentGroup cmp = (com.codename1.ui.ComponentGroup)findByName("ComponentGroup1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.ComponentGroup)findByName("ComponentGroup1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Tabs findTabs1(Component root) {
        return (com.codename1.ui.Tabs)findByName("Tabs1", root);
    }

    public com.codename1.ui.Tabs findTabs1() {
        com.codename1.ui.Tabs cmp = (com.codename1.ui.Tabs)findByName("Tabs1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Tabs)findByName("Tabs1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findDescriptionField(Component root) {
        return (com.codename1.ui.TextField)findByName("descriptionField", root);
    }

    public com.codename1.ui.TextField findDescriptionField() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("descriptionField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("descriptionField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findImportJSON(Component root) {
        return (com.codename1.ui.Button)findByName("importJSON", root);
    }

    public com.codename1.ui.Button findImportJSON() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("importJSON", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("importJSON", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findNewContainer(Component root) {
        return (com.codename1.ui.Container)findByName("newContainer", root);
    }

    public com.codename1.ui.Container findNewContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("newContainer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("newContainer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findSqlData(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("sqlData", root);
    }

    public com.codename1.ui.list.MultiList findSqlData() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("sqlData", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("sqlData", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findToggleTheme(Component root) {
        return (com.codename1.ui.Button)findByName("toggleTheme", root);
    }

    public com.codename1.ui.Button findToggleTheme() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("toggleTheme", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("toggleTheme", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.ComponentGroup findComponentGroup(Component root) {
        return (com.codename1.ui.ComponentGroup)findByName("ComponentGroup", root);
    }

    public com.codename1.ui.ComponentGroup findComponentGroup() {
        com.codename1.ui.ComponentGroup cmp = (com.codename1.ui.ComponentGroup)findByName("ComponentGroup", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.ComponentGroup)findByName("ComponentGroup", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findTitleField(Component root) {
        return (com.codename1.ui.TextField)findByName("titleField", root);
    }

    public com.codename1.ui.TextField findTitleField() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("titleField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("titleField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.components.MultiButton findMultiButton5(Component root) {
        return (com.codename1.components.MultiButton)findByName("MultiButton5", root);
    }

    public com.codename1.components.MultiButton findMultiButton5() {
        com.codename1.components.MultiButton cmp = (com.codename1.components.MultiButton)findByName("MultiButton5", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.components.MultiButton)findByName("MultiButton5", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findTasksContainer(Component root) {
        return (com.codename1.ui.Container)findByName("tasksContainer", root);
    }

    public com.codename1.ui.Container findTasksContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("tasksContainer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("tasksContainer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findCaptureButton(Component root) {
        return (com.codename1.ui.Button)findByName("captureButton", root);
    }

    public com.codename1.ui.Button findCaptureButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("captureButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("captureButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.ComponentGroup findComponentGroup3(Component root) {
        return (com.codename1.ui.ComponentGroup)findByName("ComponentGroup3", root);
    }

    public com.codename1.ui.ComponentGroup findComponentGroup3() {
        com.codename1.ui.ComponentGroup cmp = (com.codename1.ui.ComponentGroup)findByName("ComponentGroup3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.ComponentGroup)findByName("ComponentGroup3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findAddTaskButton(Component root) {
        return (com.codename1.ui.Button)findByName("addTaskButton", root);
    }

    public com.codename1.ui.Button findAddTaskButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("addTaskButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("addTaskButton", aboutToShowThisContainer);
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

    protected boolean setListModel(List cmp) {
        String listName = cmp.getName();
        if("sqlData".equals(listName)) {
            return initListModelSqlData(cmp);
        }
        return super.setListModel(cmp);
    }

    protected boolean initListModelSqlData(List cmp) {
        return false;
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
            if("MultiButton5".equals(c.getName())) {
                onMain_MultiButton5Action(c, event);
                return;
            }
            if("titleField".equals(c.getName())) {
                onMain_TitleFieldAction(c, event);
                return;
            }
            if("descriptionField".equals(c.getName())) {
                onMain_DescriptionFieldAction(c, event);
                return;
            }
            if("captureButton".equals(c.getName())) {
                onMain_CaptureButtonAction(c, event);
                return;
            }
            if("addTaskButton".equals(c.getName())) {
                onMain_AddTaskButtonAction(c, event);
                return;
            }
            if("importJSON".equals(c.getName())) {
                onMain_ImportJSONAction(c, event);
                return;
            }
            if("importXML".equals(c.getName())) {
                onMain_ImportXMLAction(c, event);
                return;
            }
            if("toggleTheme".equals(c.getName())) {
                onMain_ToggleThemeAction(c, event);
                return;
            }
            if("sqlData".equals(c.getName())) {
                onMain_SqlDataAction(c, event);
                return;
            }
        }
    }

      protected void onMain_MultiButton5Action(Component c, ActionEvent event) {
      }

      protected void onMain_TitleFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_DescriptionFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_CaptureButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_AddTaskButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_ImportJSONAction(Component c, ActionEvent event) {
      }

      protected void onMain_ImportXMLAction(Component c, ActionEvent event) {
      }

      protected void onMain_ToggleThemeAction(Component c, ActionEvent event) {
      }

      protected void onMain_SqlDataAction(Component c, ActionEvent event) {
      }

}
