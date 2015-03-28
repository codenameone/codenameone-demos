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
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("RadioButton", com.codename1.ui.RadioButton.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("Dialog", com.codename1.ui.Dialog.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("List", com.codename1.ui.List.class);
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
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
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("RadioButton", com.codename1.ui.RadioButton.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("Dialog", com.codename1.ui.Dialog.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("List", com.codename1.ui.List.class);
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

    public com.codename1.ui.Container findTitleArea(Container root) {
        return (com.codename1.ui.Container)findByName("titleArea", root);
    }

    public com.codename1.ui.Container findContainer3(Container root) {
        return (com.codename1.ui.Container)findByName("Container3", root);
    }

    public com.codename1.ui.Container findContainer2(Container root) {
        return (com.codename1.ui.Container)findByName("Container2", root);
    }

    public com.codename1.ui.Label findSliderTimeTab(Container root) {
        return (com.codename1.ui.Label)findByName("sliderTimeTab", root);
    }

    public com.codename1.ui.Container findContainer1(Container root) {
        return (com.codename1.ui.Container)findByName("Container1", root);
    }

    public com.codename1.ui.Container findContainer8(Container root) {
        return (com.codename1.ui.Container)findByName("Container8", root);
    }

    public com.codename1.ui.Button findRemoveModeButton(Container root) {
        return (com.codename1.ui.Button)findByName("removeModeButton", root);
    }

    public com.codename1.ui.Container findContainer7(Container root) {
        return (com.codename1.ui.Container)findByName("Container7", root);
    }

    public com.codename1.ui.Label findTickLabel(Container root) {
        return (com.codename1.ui.Label)findByName("tickLabel", root);
    }

    public com.codename1.ui.Container findContainer6(Container root) {
        return (com.codename1.ui.Container)findByName("Container6", root);
    }

    public com.codename1.ui.Label findTimeSliderPosition(Container root) {
        return (com.codename1.ui.Label)findByName("timeSliderPosition", root);
    }

    public com.codename1.ui.Container findContainer5(Container root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Button findExitButton(Container root) {
        return (com.codename1.ui.Button)findByName("exitButton", root);
    }

    public com.codename1.ui.Button findSettingsButton(Container root) {
        return (com.codename1.ui.Button)findByName("settingsButton", root);
    }

    public com.codename1.ui.Label findDate(Container root) {
        return (com.codename1.ui.Label)findByName("date", root);
    }

    public com.codename1.ui.Form findSplash(Container root) {
        return (com.codename1.ui.Form)findByName("Splash", root);
    }

    public com.codename1.ui.Container findFriendsRoot(Container root) {
        return (com.codename1.ui.Container)findByName("friendsRoot", root);
    }

    public com.codename1.ui.Label findFriendName(Container root) {
        return (com.codename1.ui.Label)findByName("friendName", root);
    }

    public com.codename1.ui.List findTimeSlider(Container root) {
        return (com.codename1.ui.List)findByName("timeSlider", root);
    }

    public com.codename1.ui.Container findRenderer(Container root) {
        return (com.codename1.ui.Container)findByName("Renderer", root);
    }

    public com.codename1.ui.Label findDayOrNight(Container root) {
        return (com.codename1.ui.Label)findByName("dayOrNight", root);
    }

    public com.codename1.ui.Button findButton(Container root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.CheckBox findSelected(Container root) {
        return (com.codename1.ui.CheckBox)findByName("selected", root);
    }

    public com.codename1.ui.Label findTitle(Container root) {
        return (com.codename1.ui.Label)findByName("title", root);
    }

    public com.codename1.ui.Dialog findAddZone(Container root) {
        return (com.codename1.ui.Dialog)findByName("AddZone", root);
    }

    public com.codename1.ui.Label findLabel1(Container root) {
        return (com.codename1.ui.Label)findByName("Label1", root);
    }

    public com.codename1.ui.Container findZoneRenderer(Container root) {
        return (com.codename1.ui.Container)findByName("ZoneRenderer", root);
    }

    public com.codename1.ui.Label findDescription(Container root) {
        return (com.codename1.ui.Label)findByName("description", root);
    }

    public com.codename1.ui.Label findLabel2(Container root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Form findMainUI(Container root) {
        return (com.codename1.ui.Form)findByName("MainUI", root);
    }

    public com.codename1.ui.Label findTimeOfDay(Container root) {
        return (com.codename1.ui.Label)findByName("timeOfDay", root);
    }

    public com.codename1.ui.Button findRemoveFriend(Container root) {
        return (com.codename1.ui.Button)findByName("removeFriend", root);
    }

    public com.codename1.ui.Label findDayOfWeek(Container root) {
        return (com.codename1.ui.Label)findByName("dayOfWeek", root);
    }

    public com.codename1.ui.Container findContainer(Container root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.RadioButton findWorldZone(Container root) {
        return (com.codename1.ui.RadioButton)findByName("worldZone", root);
    }

    public com.codename1.ui.Label findIcon(Container root) {
        return (com.codename1.ui.Label)findByName("icon", root);
    }

    public com.codename1.ui.Dialog findSettings(Container root) {
        return (com.codename1.ui.Dialog)findByName("Settings", root);
    }

    public com.codename1.ui.Container findFriend(Container root) {
        return (com.codename1.ui.Container)findByName("Friend", root);
    }

    public com.codename1.ui.RadioButton findFriendZone(Container root) {
        return (com.codename1.ui.RadioButton)findByName("friendZone", root);
    }

    public com.codename1.ui.List findAddZoneList(Container root) {
        return (com.codename1.ui.List)findByName("addZoneList", root);
    }

    public com.codename1.ui.Label findCurrentTimeAndDate(Container root) {
        return (com.codename1.ui.Label)findByName("currentTimeAndDate", root);
    }

    public com.codename1.ui.Label findTick(Container root) {
        return (com.codename1.ui.Label)findByName("tick", root);
    }

    public com.codename1.ui.Button findAddEntriesButton(Container root) {
        return (com.codename1.ui.Button)findByName("addEntriesButton", root);
    }

    public com.codename1.ui.Label findSubtitle(Container root) {
        return (com.codename1.ui.Label)findByName("subtitle", root);
    }

    public com.codename1.ui.Container findContainer41(Container root) {
        return (com.codename1.ui.Container)findByName("Container41", root);
    }

    public com.codename1.ui.Label findLabel(Container root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.CheckBox findCivilianTimeCheckbox(Container root) {
        return (com.codename1.ui.CheckBox)findByName("civilianTimeCheckbox", root);
    }

    public static final int COMMAND_MainUISettings = 2;
    public static final int COMMAND_SettingsOK = 5;
    public static final int COMMAND_MainUIAdd = 3;
    public static final int COMMAND_AddZoneOK = 4;
    public static final int COMMAND_MainUIExit = 1;

    protected boolean onMainUISettings() {
        return false;
    }

    protected boolean onSettingsOK() {
        return false;
    }

    protected boolean onMainUIAdd() {
        return false;
    }

    protected boolean onAddZoneOK() {
        return false;
    }

    protected boolean onMainUIExit() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_MainUISettings:
                if(onMainUISettings()) {
                    ev.consume();
                }
                return;

            case COMMAND_SettingsOK:
                if(onSettingsOK()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainUIAdd:
                if(onMainUIAdd()) {
                    ev.consume();
                }
                return;

            case COMMAND_AddZoneOK:
                if(onAddZoneOK()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainUIExit:
                if(onMainUIExit()) {
                    ev.consume();
                }
                return;

        }
    }

    protected void exitForm(Form f) {
        if("Friend".equals(f.getName())) {
            exitFriend(f);
            return;
        }

        if("ZoneRenderer".equals(f.getName())) {
            exitZoneRenderer(f);
            return;
        }

        if("MainUI".equals(f.getName())) {
            exitMainUI(f);
            return;
        }

        if("Splash".equals(f.getName())) {
            exitSplash(f);
            return;
        }

        if("AddZone".equals(f.getName())) {
            exitAddZone(f);
            return;
        }

        if("Renderer".equals(f.getName())) {
            exitRenderer(f);
            return;
        }

        if("Settings".equals(f.getName())) {
            exitSettings(f);
            return;
        }

    }


    protected void exitFriend(Form f) {
    }


    protected void exitZoneRenderer(Form f) {
    }


    protected void exitMainUI(Form f) {
    }


    protected void exitSplash(Form f) {
    }


    protected void exitAddZone(Form f) {
    }


    protected void exitRenderer(Form f) {
    }


    protected void exitSettings(Form f) {
    }

    protected void beforeShow(Form f) {
        if("Friend".equals(f.getName())) {
            beforeFriend(f);
            return;
        }

        if("ZoneRenderer".equals(f.getName())) {
            beforeZoneRenderer(f);
            return;
        }

        if("MainUI".equals(f.getName())) {
            beforeMainUI(f);
            return;
        }

        if("Splash".equals(f.getName())) {
            beforeSplash(f);
            return;
        }

        if("AddZone".equals(f.getName())) {
            beforeAddZone(f);
            return;
        }

        if("Renderer".equals(f.getName())) {
            beforeRenderer(f);
            return;
        }

        if("Settings".equals(f.getName())) {
            beforeSettings(f);
            return;
        }

    }


    protected void beforeFriend(Form f) {
    }


    protected void beforeZoneRenderer(Form f) {
    }


    protected void beforeMainUI(Form f) {
    }


    protected void beforeSplash(Form f) {
    }


    protected void beforeAddZone(Form f) {
    }


    protected void beforeRenderer(Form f) {
    }


    protected void beforeSettings(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("Friend".equals(c.getName())) {
            beforeContainerFriend(c);
            return;
        }

        if("ZoneRenderer".equals(c.getName())) {
            beforeContainerZoneRenderer(c);
            return;
        }

        if("MainUI".equals(c.getName())) {
            beforeContainerMainUI(c);
            return;
        }

        if("Splash".equals(c.getName())) {
            beforeContainerSplash(c);
            return;
        }

        if("AddZone".equals(c.getName())) {
            beforeContainerAddZone(c);
            return;
        }

        if("Renderer".equals(c.getName())) {
            beforeContainerRenderer(c);
            return;
        }

        if("Settings".equals(c.getName())) {
            beforeContainerSettings(c);
            return;
        }

    }


    protected void beforeContainerFriend(Container c) {
    }


    protected void beforeContainerZoneRenderer(Container c) {
    }


    protected void beforeContainerMainUI(Container c) {
    }


    protected void beforeContainerSplash(Container c) {
    }


    protected void beforeContainerAddZone(Container c) {
    }


    protected void beforeContainerRenderer(Container c) {
    }


    protected void beforeContainerSettings(Container c) {
    }

    protected void postShow(Form f) {
        if("Friend".equals(f.getName())) {
            postFriend(f);
            return;
        }

        if("ZoneRenderer".equals(f.getName())) {
            postZoneRenderer(f);
            return;
        }

        if("MainUI".equals(f.getName())) {
            postMainUI(f);
            return;
        }

        if("Splash".equals(f.getName())) {
            postSplash(f);
            return;
        }

        if("AddZone".equals(f.getName())) {
            postAddZone(f);
            return;
        }

        if("Renderer".equals(f.getName())) {
            postRenderer(f);
            return;
        }

        if("Settings".equals(f.getName())) {
            postSettings(f);
            return;
        }

    }


    protected void postFriend(Form f) {
    }


    protected void postZoneRenderer(Form f) {
    }


    protected void postMainUI(Form f) {
    }


    protected void postSplash(Form f) {
    }


    protected void postAddZone(Form f) {
    }


    protected void postRenderer(Form f) {
    }


    protected void postSettings(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Friend".equals(c.getName())) {
            postContainerFriend(c);
            return;
        }

        if("ZoneRenderer".equals(c.getName())) {
            postContainerZoneRenderer(c);
            return;
        }

        if("MainUI".equals(c.getName())) {
            postContainerMainUI(c);
            return;
        }

        if("Splash".equals(c.getName())) {
            postContainerSplash(c);
            return;
        }

        if("AddZone".equals(c.getName())) {
            postContainerAddZone(c);
            return;
        }

        if("Renderer".equals(c.getName())) {
            postContainerRenderer(c);
            return;
        }

        if("Settings".equals(c.getName())) {
            postContainerSettings(c);
            return;
        }

    }


    protected void postContainerFriend(Container c) {
    }


    protected void postContainerZoneRenderer(Container c) {
    }


    protected void postContainerMainUI(Container c) {
    }


    protected void postContainerSplash(Container c) {
    }


    protected void postContainerAddZone(Container c) {
    }


    protected void postContainerRenderer(Container c) {
    }


    protected void postContainerSettings(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Friend".equals(rootName)) {
            onCreateFriend();
            return;
        }

        if("ZoneRenderer".equals(rootName)) {
            onCreateZoneRenderer();
            return;
        }

        if("MainUI".equals(rootName)) {
            onCreateMainUI();
            return;
        }

        if("Splash".equals(rootName)) {
            onCreateSplash();
            return;
        }

        if("AddZone".equals(rootName)) {
            onCreateAddZone();
            return;
        }

        if("Renderer".equals(rootName)) {
            onCreateRenderer();
            return;
        }

        if("Settings".equals(rootName)) {
            onCreateSettings();
            return;
        }

    }


    protected void onCreateFriend() {
    }


    protected void onCreateZoneRenderer() {
    }


    protected void onCreateMainUI() {
    }


    protected void onCreateSplash() {
    }


    protected void onCreateAddZone() {
    }


    protected void onCreateRenderer() {
    }


    protected void onCreateSettings() {
    }

    protected boolean setListModel(List cmp) {
        String listName = cmp.getName();
        if("timeSlider".equals(listName)) {
            return initListModelTimeSlider(cmp);
        }
        if("addZoneList".equals(listName)) {
            return initListModelAddZoneList(cmp);
        }
        return super.setListModel(cmp);
    }

    protected boolean initListModelTimeSlider(List cmp) {
        return false;
    }

    protected boolean initListModelAddZoneList(List cmp) {
        return false;
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        if(rootContainerName == null) return;
        if(rootContainerName.equals("Friend")) {
            if("removeFriend".equals(c.getName())) {
                onFriend_RemoveFriendAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("ZoneRenderer")) {
            if("selected".equals(c.getName())) {
                onZoneRenderer_SelectedAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("MainUI")) {
            if("timeSlider".equals(c.getName())) {
                onMainUI_TimeSliderAction(c, event);
                return;
            }
            if("addEntriesButton".equals(c.getName())) {
                onMainUI_AddEntriesButtonAction(c, event);
                return;
            }
            if("removeModeButton".equals(c.getName())) {
                onMainUI_RemoveModeButtonAction(c, event);
                return;
            }
            if("settingsButton".equals(c.getName())) {
                onMainUI_SettingsButtonAction(c, event);
                return;
            }
            if("exitButton".equals(c.getName())) {
                onMainUI_ExitButtonAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("AddZone")) {
            if("worldZone".equals(c.getName())) {
                onAddZone_WorldZoneAction(c, event);
                return;
            }
            if("friendZone".equals(c.getName())) {
                onAddZone_FriendZoneAction(c, event);
                return;
            }
            if("selected".equals(c.getName())) {
                onAddZone_SelectedAction(c, event);
                return;
            }
            if("addZoneList".equals(c.getName())) {
                onAddZone_AddZoneListAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onAddZone_ButtonAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Settings")) {
            if("Button".equals(c.getName())) {
                onSettings_ButtonAction(c, event);
                return;
            }
            if("civilianTimeCheckbox".equals(c.getName())) {
                onSettings_CivilianTimeCheckboxAction(c, event);
                return;
            }
        }
    }

      protected void onFriend_RemoveFriendAction(Component c, ActionEvent event) {
      }

      protected void onZoneRenderer_SelectedAction(Component c, ActionEvent event) {
      }

      protected void onMainUI_TimeSliderAction(Component c, ActionEvent event) {
      }

      protected void onMainUI_AddEntriesButtonAction(Component c, ActionEvent event) {
      }

      protected void onMainUI_RemoveModeButtonAction(Component c, ActionEvent event) {
      }

      protected void onMainUI_SettingsButtonAction(Component c, ActionEvent event) {
      }

      protected void onMainUI_ExitButtonAction(Component c, ActionEvent event) {
      }

      protected void onAddZone_WorldZoneAction(Component c, ActionEvent event) {
      }

      protected void onAddZone_FriendZoneAction(Component c, ActionEvent event) {
      }

      protected void onAddZone_SelectedAction(Component c, ActionEvent event) {
      }

      protected void onAddZone_AddZoneListAction(Component c, ActionEvent event) {
      }

      protected void onAddZone_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onSettings_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onSettings_CivilianTimeCheckboxAction(Component c, ActionEvent event) {
      }

}
