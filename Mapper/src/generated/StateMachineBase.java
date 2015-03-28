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
        UIBuilder.registerCustomComponent("MapComponent", com.codename1.maps.MapComponent.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextArea", com.codename1.ui.TextArea.class);
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
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("MapComponent", com.codename1.maps.MapComponent.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("TextArea", com.codename1.ui.TextArea.class);
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

    public com.codename1.ui.Container findSummary(Container root) {
        return (com.codename1.ui.Container)findByName("summary", root);
    }

    public com.codename1.ui.Container findSummary() {
        return (com.codename1.ui.Container)findByName("summary", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer4(Container root) {
        return (com.codename1.ui.Container)findByName("Container4", root);
    }

    public com.codename1.ui.Container findContainer4() {
        return (com.codename1.ui.Container)findByName("Container4", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer3(Container root) {
        return (com.codename1.ui.Container)findByName("Container3", root);
    }

    public com.codename1.ui.Container findContainer3() {
        return (com.codename1.ui.Container)findByName("Container3", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer2(Container root) {
        return (com.codename1.ui.Container)findByName("Container2", root);
    }

    public com.codename1.ui.Container findContainer2() {
        return (com.codename1.ui.Container)findByName("Container2", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmDistance(Container root) {
        return (com.codename1.ui.Label)findByName("atmDistance", root);
    }

    public com.codename1.ui.Label findAtmDistance() {
        return (com.codename1.ui.Label)findByName("atmDistance", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer5(Container root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Container findContainer5() {
        return (com.codename1.ui.Container)findByName("Container5", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmPaidOrFree(Container root) {
        return (com.codename1.ui.Label)findByName("atmPaidOrFree", root);
    }

    public com.codename1.ui.Label findAtmPaidOrFree() {
        return (com.codename1.ui.Label)findByName("atmPaidOrFree", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.TextArea findTextArea(Container root) {
        return (com.codename1.ui.TextArea)findByName("TextArea", root);
    }

    public com.codename1.ui.TextArea findTextArea() {
        return (com.codename1.ui.TextArea)findByName("TextArea", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Form findMaps(Container root) {
        return (com.codename1.ui.Form)findByName("Maps", root);
    }

    public com.codename1.ui.Form findMaps() {
        return (com.codename1.ui.Form)findByName("Maps", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel41(Container root) {
        return (com.codename1.ui.Label)findByName("Label41", root);
    }

    public com.codename1.ui.Label findLabel41() {
        return (com.codename1.ui.Label)findByName("Label41", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Button findButton(Container root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.Button findButton() {
        return (com.codename1.ui.Button)findByName("Button", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Form findMain(Container root) {
        return (com.codename1.ui.Form)findByName("Main", root);
    }

    public com.codename1.ui.Form findMain() {
        return (com.codename1.ui.Form)findByName("Main", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel1(Container root) {
        return (com.codename1.ui.Label)findByName("Label1", root);
    }

    public com.codename1.ui.Label findLabel1() {
        return (com.codename1.ui.Label)findByName("Label1", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findFullListContent(Container root) {
        return (com.codename1.ui.Container)findByName("fullListContent", root);
    }

    public com.codename1.ui.Container findFullListContent() {
        return (com.codename1.ui.Container)findByName("fullListContent", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel411(Container root) {
        return (com.codename1.ui.Label)findByName("Label411", root);
    }

    public com.codename1.ui.Label findLabel411() {
        return (com.codename1.ui.Label)findByName("Label411", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel3(Container root) {
        return (com.codename1.ui.Label)findByName("Label3", root);
    }

    public com.codename1.ui.Label findLabel3() {
        return (com.codename1.ui.Label)findByName("Label3", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel2(Container root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Label findLabel2() {
        return (com.codename1.ui.Label)findByName("Label2", Display.getInstance().getCurrent());
    }

    public com.codename1.maps.MapComponent findMapEntry(Container root) {
        return (com.codename1.maps.MapComponent)findByName("mapEntry", root);
    }

    public com.codename1.maps.MapComponent findMapEntry() {
        return (com.codename1.maps.MapComponent)findByName("mapEntry", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findEntries(Container root) {
        return (com.codename1.ui.Container)findByName("entries", root);
    }

    public com.codename1.ui.Container findEntries() {
        return (com.codename1.ui.Container)findByName("entries", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmMainPaid(Container root) {
        return (com.codename1.ui.Label)findByName("atmMainPaid", root);
    }

    public com.codename1.ui.Label findAtmMainPaid() {
        return (com.codename1.ui.Label)findByName("atmMainPaid", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmMainDistance(Container root) {
        return (com.codename1.ui.Label)findByName("atmMainDistance", root);
    }

    public com.codename1.ui.Label findAtmMainDistance() {
        return (com.codename1.ui.Label)findByName("atmMainDistance", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findContainer(Container root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Container findContainer() {
        return (com.codename1.ui.Container)findByName("Container", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.TextArea findAtmMainText(Container root) {
        return (com.codename1.ui.TextArea)findByName("atmMainText", root);
    }

    public com.codename1.ui.TextArea findAtmMainText() {
        return (com.codename1.ui.TextArea)findByName("atmMainText", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmMainUnit(Container root) {
        return (com.codename1.ui.Label)findByName("atmMainUnit", root);
    }

    public com.codename1.ui.Label findAtmMainUnit() {
        return (com.codename1.ui.Label)findByName("atmMainUnit", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findEntry(Container root) {
        return (com.codename1.ui.Container)findByName("Entry", root);
    }

    public com.codename1.ui.Container findEntry() {
        return (com.codename1.ui.Container)findByName("Entry", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Container findMapOverlay(Container root) {
        return (com.codename1.ui.Container)findByName("mapOverlay", root);
    }

    public com.codename1.ui.Container findMapOverlay() {
        return (com.codename1.ui.Container)findByName("mapOverlay", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmMainZip(Container root) {
        return (com.codename1.ui.Label)findByName("atmMainZip", root);
    }

    public com.codename1.ui.Label findAtmMainZip() {
        return (com.codename1.ui.Label)findByName("atmMainZip", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel4(Container root) {
        return (com.codename1.ui.Label)findByName("Label4", root);
    }

    public com.codename1.ui.Label findLabel4() {
        return (com.codename1.ui.Label)findByName("Label4", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Form findFullList(Container root) {
        return (com.codename1.ui.Form)findByName("FullList", root);
    }

    public com.codename1.ui.Form findFullList() {
        return (com.codename1.ui.Form)findByName("FullList", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel5(Container root) {
        return (com.codename1.ui.Label)findByName("Label5", root);
    }

    public com.codename1.ui.Label findLabel5() {
        return (com.codename1.ui.Label)findByName("Label5", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel31(Container root) {
        return (com.codename1.ui.Label)findByName("Label31", root);
    }

    public com.codename1.ui.Label findLabel31() {
        return (com.codename1.ui.Label)findByName("Label31", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.TextField findTextField(Container root) {
        return (com.codename1.ui.TextField)findByName("TextField", root);
    }

    public com.codename1.ui.TextField findTextField() {
        return (com.codename1.ui.TextField)findByName("TextField", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmMainTitle(Container root) {
        return (com.codename1.ui.Label)findByName("atmMainTitle", root);
    }

    public com.codename1.ui.Label findAtmMainTitle() {
        return (com.codename1.ui.Label)findByName("atmMainTitle", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel311(Container root) {
        return (com.codename1.ui.Label)findByName("Label311", root);
    }

    public com.codename1.ui.Label findLabel311() {
        return (com.codename1.ui.Label)findByName("Label311", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findAtmUnit(Container root) {
        return (com.codename1.ui.Label)findByName("atmUnit", root);
    }

    public com.codename1.ui.Label findAtmUnit() {
        return (com.codename1.ui.Label)findByName("atmUnit", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Button findAtmTitle(Container root) {
        return (com.codename1.ui.Button)findByName("atmTitle", root);
    }

    public com.codename1.ui.Button findAtmTitle() {
        return (com.codename1.ui.Button)findByName("atmTitle", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.Label findLabel(Container root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.Label findLabel() {
        return (com.codename1.ui.Label)findByName("Label", Display.getInstance().getCurrent());
    }

    public com.codename1.ui.TextArea findAtmLine2(Container root) {
        return (com.codename1.ui.TextArea)findByName("atmLine2", root);
    }

    public com.codename1.ui.TextArea findAtmLine2() {
        return (com.codename1.ui.TextArea)findByName("atmLine2", Display.getInstance().getCurrent());
    }

    public static final int COMMAND_MapsList = 3;
    public static final int COMMAND_MainSearch = 1;
    public static final int COMMAND_EntryRoyalBankOfScotland = 4;

    protected boolean onMapsList() {
        return false;
    }

    protected boolean onMainSearch() {
        return false;
    }

    protected boolean onEntryRoyalBankOfScotland() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_MapsList:
                if(onMapsList()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainSearch:
                if(onMainSearch()) {
                    ev.consume();
                }
                return;

            case COMMAND_EntryRoyalBankOfScotland:
                if(onEntryRoyalBankOfScotland()) {
                    ev.consume();
                }
                return;

        }
    }

    protected void exitForm(Form f) {
        if("FullList".equals(f.getName())) {
            exitFullList(f);
            return;
        }

        if("Main".equals(f.getName())) {
            exitMain(f);
            return;
        }

        if("Entry".equals(f.getName())) {
            exitEntry(f);
            return;
        }

        if("Maps".equals(f.getName())) {
            exitMaps(f);
            return;
        }

    }


    protected void exitFullList(Form f) {
    }


    protected void exitMain(Form f) {
    }


    protected void exitEntry(Form f) {
    }


    protected void exitMaps(Form f) {
    }

    protected void beforeShow(Form f) {
        if("FullList".equals(f.getName())) {
            beforeFullList(f);
            return;
        }

        if("Main".equals(f.getName())) {
            beforeMain(f);
            return;
        }

        if("Entry".equals(f.getName())) {
            beforeEntry(f);
            return;
        }

        if("Maps".equals(f.getName())) {
            beforeMaps(f);
            return;
        }

    }


    protected void beforeFullList(Form f) {
    }


    protected void beforeMain(Form f) {
    }


    protected void beforeEntry(Form f) {
    }


    protected void beforeMaps(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("FullList".equals(c.getName())) {
            beforeContainerFullList(c);
            return;
        }

        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            return;
        }

        if("Entry".equals(c.getName())) {
            beforeContainerEntry(c);
            return;
        }

        if("Maps".equals(c.getName())) {
            beforeContainerMaps(c);
            return;
        }

    }


    protected void beforeContainerFullList(Container c) {
    }


    protected void beforeContainerMain(Container c) {
    }


    protected void beforeContainerEntry(Container c) {
    }


    protected void beforeContainerMaps(Container c) {
    }

    protected void postShow(Form f) {
        if("FullList".equals(f.getName())) {
            postFullList(f);
            return;
        }

        if("Main".equals(f.getName())) {
            postMain(f);
            return;
        }

        if("Entry".equals(f.getName())) {
            postEntry(f);
            return;
        }

        if("Maps".equals(f.getName())) {
            postMaps(f);
            return;
        }

    }


    protected void postFullList(Form f) {
    }


    protected void postMain(Form f) {
    }


    protected void postEntry(Form f) {
    }


    protected void postMaps(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("FullList".equals(c.getName())) {
            postContainerFullList(c);
            return;
        }

        if("Main".equals(c.getName())) {
            postContainerMain(c);
            return;
        }

        if("Entry".equals(c.getName())) {
            postContainerEntry(c);
            return;
        }

        if("Maps".equals(c.getName())) {
            postContainerMaps(c);
            return;
        }

    }


    protected void postContainerFullList(Container c) {
    }


    protected void postContainerMain(Container c) {
    }


    protected void postContainerEntry(Container c) {
    }


    protected void postContainerMaps(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("FullList".equals(rootName)) {
            onCreateFullList();
            return;
        }

        if("Main".equals(rootName)) {
            onCreateMain();
            return;
        }

        if("Entry".equals(rootName)) {
            onCreateEntry();
            return;
        }

        if("Maps".equals(rootName)) {
            onCreateMaps();
            return;
        }

    }


    protected void onCreateFullList() {
    }


    protected void onCreateMain() {
    }


    protected void onCreateEntry() {
    }


    protected void onCreateMaps() {
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        if(c.getParent().getLeadParent() != null) {
            c = c.getParent().getLeadParent();
        }
        if(rootContainerName == null) return;
        if(rootContainerName.equals("FullList")) {
            if("TextArea".equals(c.getName())) {
                onFullList_TextAreaAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Main")) {
            if("TextField".equals(c.getName())) {
                onMain_TextFieldAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onMain_ButtonAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Entry")) {
            if("atmTitle".equals(c.getName())) {
                onEntry_AtmTitleAction(c, event);
                return;
            }
            if("atmLine2".equals(c.getName())) {
                onEntry_AtmLine2Action(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Maps")) {
            if("atmMainText".equals(c.getName())) {
                onMaps_AtmMainTextAction(c, event);
                return;
            }
        }
    }

      protected void onFullList_TextAreaAction(Component c, ActionEvent event) {
      }

      protected void onMain_TextFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onEntry_AtmTitleAction(Component c, ActionEvent event) {
      }

      protected void onEntry_AtmLine2Action(Component c, ActionEvent event) {
      }

      protected void onMaps_AtmMainTextAction(Component c, ActionEvent event) {
      }

}
