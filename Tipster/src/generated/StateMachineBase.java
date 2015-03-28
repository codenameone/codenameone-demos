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
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("List", com.codename1.ui.List.class);
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
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("List", com.codename1.ui.List.class);
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

    public com.codename1.ui.Label findTotal(Container root) {
        return (com.codename1.ui.Label)findByName("total", root);
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

    public com.codename1.ui.TextField findBillTotalField(Container root) {
        return (com.codename1.ui.TextField)findByName("billTotalField", root);
    }

    public com.codename1.ui.Container findContainer6(Container root) {
        return (com.codename1.ui.Container)findByName("Container6", root);
    }

    public com.codename1.ui.Container findTypeRendererSelected(Container root) {
        return (com.codename1.ui.Container)findByName("TypeRendererSelected", root);
    }

    public com.codename1.ui.Button findButton2(Container root) {
        return (com.codename1.ui.Button)findByName("Button2", root);
    }

    public com.codename1.ui.Container findContainer5(Container root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Label findUnsel(Container root) {
        return (com.codename1.ui.Label)findByName("Unsel", root);
    }

    public com.codename1.ui.Form findSplash(Container root) {
        return (com.codename1.ui.Form)findByName("Splash", root);
    }

    public com.codename1.ui.Label findLowQuality(Container root) {
        return (com.codename1.ui.Label)findByName("lowQuality", root);
    }

    public com.codename1.ui.Label findAmount(Container root) {
        return (com.codename1.ui.Label)findByName("amount", root);
    }

    public com.codename1.ui.Button findButton(Container root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.Label findLabel1(Container root) {
        return (com.codename1.ui.Label)findByName("Label1", root);
    }

    public com.codename1.ui.Label findLabel3(Container root) {
        return (com.codename1.ui.Label)findByName("Label3", root);
    }

    public com.codename1.ui.Label findTipTotal(Container root) {
        return (com.codename1.ui.Label)findByName("tipTotal", root);
    }

    public com.codename1.ui.Label findLabel2(Container root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Form findCurrency(Container root) {
        return (com.codename1.ui.Form)findByName("Currency", root);
    }

    public com.codename1.ui.RadioButton findGbp(Container root) {
        return (com.codename1.ui.RadioButton)findByName("gbp", root);
    }

    public com.codename1.ui.Container findHistoryContainer(Container root) {
        return (com.codename1.ui.Container)findByName("historyContainer", root);
    }

    public com.codename1.ui.RadioButton findInr(Container root) {
        return (com.codename1.ui.RadioButton)findByName("inr", root);
    }

    public com.codename1.ui.Label findLabel6(Container root) {
        return (com.codename1.ui.Label)findByName("Label6", root);
    }

    public com.codename1.ui.TextField findWorkEffortField(Container root) {
        return (com.codename1.ui.TextField)findByName("workEffortField", root);
    }

    public com.codename1.ui.Container findContainer10(Container root) {
        return (com.codename1.ui.Container)findByName("Container10", root);
    }

    public com.codename1.ui.Container findHistoryMonth(Container root) {
        return (com.codename1.ui.Container)findByName("HistoryMonth", root);
    }

    public com.codename1.ui.Label findServiceType(Container root) {
        return (com.codename1.ui.Label)findByName("serviceType", root);
    }

    public com.codename1.ui.Container findBubbleParent(Container root) {
        return (com.codename1.ui.Container)findByName("bubbleParent", root);
    }

    public com.codename1.ui.Label findWorkScope(Container root) {
        return (com.codename1.ui.Label)findByName("workScope", root);
    }

    public com.codename1.ui.RadioButton findChf(Container root) {
        return (com.codename1.ui.RadioButton)findByName("chf", root);
    }

    public com.codename1.ui.Slider findQualitySlider(Container root) {
        return (com.codename1.ui.Slider)findByName("qualitySlider", root);
    }

    public com.codename1.ui.Container findCurrencyContainer(Container root) {
        return (com.codename1.ui.Container)findByName("currencyContainer", root);
    }

    public com.codename1.ui.Button findButton1(Container root) {
        return (com.codename1.ui.Button)findByName("Button1", root);
    }

    public com.codename1.ui.Label findSeparator(Container root) {
        return (com.codename1.ui.Label)findByName("separator", root);
    }

    public com.codename1.ui.Label findTitleLabel(Container root) {
        return (com.codename1.ui.Label)findByName("titleLabel", root);
    }

    public com.codename1.ui.Container findContainer43(Container root) {
        return (com.codename1.ui.Container)findByName("Container43", root);
    }

    public com.codename1.ui.Container findContainer44(Container root) {
        return (com.codename1.ui.Container)findByName("Container44", root);
    }

    public com.codename1.ui.Container findContainer45(Container root) {
        return (com.codename1.ui.Container)findByName("Container45", root);
    }

    public com.codename1.ui.Container findBubbleContainer(Container root) {
        return (com.codename1.ui.Container)findByName("bubbleContainer", root);
    }

    public com.codename1.ui.RadioButton findUsd(Container root) {
        return (com.codename1.ui.RadioButton)findByName("usd", root);
    }

    public com.codename1.ui.Label findDate(Container root) {
        return (com.codename1.ui.Label)findByName("date", root);
    }

    public com.codename1.ui.Container findTypeRenderer(Container root) {
        return (com.codename1.ui.Container)findByName("TypeRenderer", root);
    }

    public com.codename1.ui.Form findMain(Container root) {
        return (com.codename1.ui.Form)findByName("Main", root);
    }

    public com.codename1.ui.RadioButton findEur(Container root) {
        return (com.codename1.ui.RadioButton)findByName("eur", root);
    }

    public com.codename1.ui.Form findHistory(Container root) {
        return (com.codename1.ui.Form)findByName("History", root);
    }

    public com.codename1.ui.Container findEntries(Container root) {
        return (com.codename1.ui.Container)findByName("entries", root);
    }

    public com.codename1.ui.Container findHistoryEntry(Container root) {
        return (com.codename1.ui.Container)findByName("HistoryEntry", root);
    }

    public com.codename1.ui.Container findContainer(Container root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.List findCarosel(Container root) {
        return (com.codename1.ui.List)findByName("carosel", root);
    }

    public com.codename1.ui.RadioButton findJpy(Container root) {
        return (com.codename1.ui.RadioButton)findByName("jpy", root);
    }

    public com.codename1.ui.Label findSel(Container root) {
        return (com.codename1.ui.Label)findByName("Sel", root);
    }

    public com.codename1.ui.Label findName(Container root) {
        return (com.codename1.ui.Label)findByName("Name", root);
    }

    public com.codename1.ui.Label findHighQuality(Container root) {
        return (com.codename1.ui.Label)findByName("highQuality", root);
    }

    public com.codename1.ui.Container findContainer42(Container root) {
        return (com.codename1.ui.Container)findByName("Container42", root);
    }

    public com.codename1.ui.Container findContainer41(Container root) {
        return (com.codename1.ui.Container)findByName("Container41", root);
    }

    public com.codename1.ui.Label findLabel(Container root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.TextField findTotalField(Container root) {
        return (com.codename1.ui.TextField)findByName("totalField", root);
    }

    public static final int COMMAND_HistoryCalculator = 4;
    public static final int COMMAND_MainExit = 1;
    public static final int COMMAND_HistoryCurrency = 3;
    public static final int COMMAND_MainTipHistory = 6;
    public static final int COMMAND_MainCurrency = 2;
    public static final int COMMAND_CurrencyTipHistory = 5;

    protected boolean onHistoryCalculator() {
        return false;
    }

    protected boolean onMainExit() {
        return false;
    }

    protected boolean onHistoryCurrency() {
        return false;
    }

    protected boolean onMainTipHistory() {
        return false;
    }

    protected boolean onMainCurrency() {
        return false;
    }

    protected boolean onCurrencyTipHistory() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_HistoryCalculator:
                if(onHistoryCalculator()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainExit:
                if(onMainExit()) {
                    ev.consume();
                }
                return;

            case COMMAND_HistoryCurrency:
                if(onHistoryCurrency()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainTipHistory:
                if(onMainTipHistory()) {
                    ev.consume();
                }
                return;

            case COMMAND_MainCurrency:
                if(onMainCurrency()) {
                    ev.consume();
                }
                return;

            case COMMAND_CurrencyTipHistory:
                if(onCurrencyTipHistory()) {
                    ev.consume();
                }
                return;

        }
    }

    protected void exitForm(Form f) {
        if("TypeRenderer".equals(f.getName())) {
            exitTypeRenderer(f);
            return;
        }

        if("Currency".equals(f.getName())) {
            exitCurrency(f);
            return;
        }

        if("History".equals(f.getName())) {
            exitHistory(f);
            return;
        }

        if("HistoryEntry".equals(f.getName())) {
            exitHistoryEntry(f);
            return;
        }

        if("Main".equals(f.getName())) {
            exitMain(f);
            return;
        }

        if("Splash".equals(f.getName())) {
            exitSplash(f);
            return;
        }

        if("HistoryMonth".equals(f.getName())) {
            exitHistoryMonth(f);
            return;
        }

        if("TypeRendererSelected".equals(f.getName())) {
            exitTypeRendererSelected(f);
            return;
        }

    }


    protected void exitTypeRenderer(Form f) {
    }


    protected void exitCurrency(Form f) {
    }


    protected void exitHistory(Form f) {
    }


    protected void exitHistoryEntry(Form f) {
    }


    protected void exitMain(Form f) {
    }


    protected void exitSplash(Form f) {
    }


    protected void exitHistoryMonth(Form f) {
    }


    protected void exitTypeRendererSelected(Form f) {
    }

    protected void beforeShow(Form f) {
        if("TypeRenderer".equals(f.getName())) {
            beforeTypeRenderer(f);
            return;
        }

        if("Currency".equals(f.getName())) {
            beforeCurrency(f);
            return;
        }

        if("History".equals(f.getName())) {
            beforeHistory(f);
            return;
        }

        if("HistoryEntry".equals(f.getName())) {
            beforeHistoryEntry(f);
            return;
        }

        if("Main".equals(f.getName())) {
            beforeMain(f);
            return;
        }

        if("Splash".equals(f.getName())) {
            beforeSplash(f);
            return;
        }

        if("HistoryMonth".equals(f.getName())) {
            beforeHistoryMonth(f);
            return;
        }

        if("TypeRendererSelected".equals(f.getName())) {
            beforeTypeRendererSelected(f);
            return;
        }

    }


    protected void beforeTypeRenderer(Form f) {
    }


    protected void beforeCurrency(Form f) {
    }


    protected void beforeHistory(Form f) {
    }


    protected void beforeHistoryEntry(Form f) {
    }


    protected void beforeMain(Form f) {
    }


    protected void beforeSplash(Form f) {
    }


    protected void beforeHistoryMonth(Form f) {
    }


    protected void beforeTypeRendererSelected(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("TypeRenderer".equals(c.getName())) {
            beforeContainerTypeRenderer(c);
            return;
        }

        if("Currency".equals(c.getName())) {
            beforeContainerCurrency(c);
            return;
        }

        if("History".equals(c.getName())) {
            beforeContainerHistory(c);
            return;
        }

        if("HistoryEntry".equals(c.getName())) {
            beforeContainerHistoryEntry(c);
            return;
        }

        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            return;
        }

        if("Splash".equals(c.getName())) {
            beforeContainerSplash(c);
            return;
        }

        if("HistoryMonth".equals(c.getName())) {
            beforeContainerHistoryMonth(c);
            return;
        }

        if("TypeRendererSelected".equals(c.getName())) {
            beforeContainerTypeRendererSelected(c);
            return;
        }

    }


    protected void beforeContainerTypeRenderer(Container c) {
    }


    protected void beforeContainerCurrency(Container c) {
    }


    protected void beforeContainerHistory(Container c) {
    }


    protected void beforeContainerHistoryEntry(Container c) {
    }


    protected void beforeContainerMain(Container c) {
    }


    protected void beforeContainerSplash(Container c) {
    }


    protected void beforeContainerHistoryMonth(Container c) {
    }


    protected void beforeContainerTypeRendererSelected(Container c) {
    }

    protected void postShow(Form f) {
        if("TypeRenderer".equals(f.getName())) {
            postTypeRenderer(f);
            return;
        }

        if("Currency".equals(f.getName())) {
            postCurrency(f);
            return;
        }

        if("History".equals(f.getName())) {
            postHistory(f);
            return;
        }

        if("HistoryEntry".equals(f.getName())) {
            postHistoryEntry(f);
            return;
        }

        if("Main".equals(f.getName())) {
            postMain(f);
            return;
        }

        if("Splash".equals(f.getName())) {
            postSplash(f);
            return;
        }

        if("HistoryMonth".equals(f.getName())) {
            postHistoryMonth(f);
            return;
        }

        if("TypeRendererSelected".equals(f.getName())) {
            postTypeRendererSelected(f);
            return;
        }

    }


    protected void postTypeRenderer(Form f) {
    }


    protected void postCurrency(Form f) {
    }


    protected void postHistory(Form f) {
    }


    protected void postHistoryEntry(Form f) {
    }


    protected void postMain(Form f) {
    }


    protected void postSplash(Form f) {
    }


    protected void postHistoryMonth(Form f) {
    }


    protected void postTypeRendererSelected(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("TypeRenderer".equals(c.getName())) {
            postContainerTypeRenderer(c);
            return;
        }

        if("Currency".equals(c.getName())) {
            postContainerCurrency(c);
            return;
        }

        if("History".equals(c.getName())) {
            postContainerHistory(c);
            return;
        }

        if("HistoryEntry".equals(c.getName())) {
            postContainerHistoryEntry(c);
            return;
        }

        if("Main".equals(c.getName())) {
            postContainerMain(c);
            return;
        }

        if("Splash".equals(c.getName())) {
            postContainerSplash(c);
            return;
        }

        if("HistoryMonth".equals(c.getName())) {
            postContainerHistoryMonth(c);
            return;
        }

        if("TypeRendererSelected".equals(c.getName())) {
            postContainerTypeRendererSelected(c);
            return;
        }

    }


    protected void postContainerTypeRenderer(Container c) {
    }


    protected void postContainerCurrency(Container c) {
    }


    protected void postContainerHistory(Container c) {
    }


    protected void postContainerHistoryEntry(Container c) {
    }


    protected void postContainerMain(Container c) {
    }


    protected void postContainerSplash(Container c) {
    }


    protected void postContainerHistoryMonth(Container c) {
    }


    protected void postContainerTypeRendererSelected(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("TypeRenderer".equals(rootName)) {
            onCreateTypeRenderer();
            return;
        }

        if("Currency".equals(rootName)) {
            onCreateCurrency();
            return;
        }

        if("History".equals(rootName)) {
            onCreateHistory();
            return;
        }

        if("HistoryEntry".equals(rootName)) {
            onCreateHistoryEntry();
            return;
        }

        if("Main".equals(rootName)) {
            onCreateMain();
            return;
        }

        if("Splash".equals(rootName)) {
            onCreateSplash();
            return;
        }

        if("HistoryMonth".equals(rootName)) {
            onCreateHistoryMonth();
            return;
        }

        if("TypeRendererSelected".equals(rootName)) {
            onCreateTypeRendererSelected();
            return;
        }

    }


    protected void onCreateTypeRenderer() {
    }


    protected void onCreateCurrency() {
    }


    protected void onCreateHistory() {
    }


    protected void onCreateHistoryEntry() {
    }


    protected void onCreateMain() {
    }


    protected void onCreateSplash() {
    }


    protected void onCreateHistoryMonth() {
    }


    protected void onCreateTypeRendererSelected() {
    }

    protected boolean setListModel(List cmp) {
        String listName = cmp.getName();
        if("carosel".equals(listName)) {
            return initListModelCarosel(cmp);
        }
        return super.setListModel(cmp);
    }

    protected boolean initListModelCarosel(List cmp) {
        return false;
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        if(rootContainerName == null) return;
        if(rootContainerName.equals("Currency")) {
            if("Button".equals(c.getName())) {
                onCurrency_ButtonAction(c, event);
                return;
            }
            if("Button1".equals(c.getName())) {
                onCurrency_Button1Action(c, event);
                return;
            }
            if("Button2".equals(c.getName())) {
                onCurrency_Button2Action(c, event);
                return;
            }
            if("usd".equals(c.getName())) {
                onCurrency_UsdAction(c, event);
                return;
            }
            if("eur".equals(c.getName())) {
                onCurrency_EurAction(c, event);
                return;
            }
            if("chf".equals(c.getName())) {
                onCurrency_ChfAction(c, event);
                return;
            }
            if("jpy".equals(c.getName())) {
                onCurrency_JpyAction(c, event);
                return;
            }
            if("inr".equals(c.getName())) {
                onCurrency_InrAction(c, event);
                return;
            }
            if("gbp".equals(c.getName())) {
                onCurrency_GbpAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("History")) {
            if("Button".equals(c.getName())) {
                onHistory_ButtonAction(c, event);
                return;
            }
            if("Button1".equals(c.getName())) {
                onHistory_Button1Action(c, event);
                return;
            }
            if("Button2".equals(c.getName())) {
                onHistory_Button2Action(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Main")) {
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
            if("billTotalField".equals(c.getName())) {
                onMain_BillTotalFieldAction(c, event);
                return;
            }
            if("workEffortField".equals(c.getName())) {
                onMain_WorkEffortFieldAction(c, event);
                return;
            }
            if("totalField".equals(c.getName())) {
                onMain_TotalFieldAction(c, event);
                return;
            }
            if("carosel".equals(c.getName())) {
                onMain_CaroselAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Splash")) {
            if("carosel".equals(c.getName())) {
                onSplash_CaroselAction(c, event);
                return;
            }
        }
    }

      protected void onCurrency_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onCurrency_Button1Action(Component c, ActionEvent event) {
      }

      protected void onCurrency_Button2Action(Component c, ActionEvent event) {
      }

      protected void onCurrency_UsdAction(Component c, ActionEvent event) {
      }

      protected void onCurrency_EurAction(Component c, ActionEvent event) {
      }

      protected void onCurrency_ChfAction(Component c, ActionEvent event) {
      }

      protected void onCurrency_JpyAction(Component c, ActionEvent event) {
      }

      protected void onCurrency_InrAction(Component c, ActionEvent event) {
      }

      protected void onCurrency_GbpAction(Component c, ActionEvent event) {
      }

      protected void onHistory_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onHistory_Button1Action(Component c, ActionEvent event) {
      }

      protected void onHistory_Button2Action(Component c, ActionEvent event) {
      }

      protected void onMain_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_Button1Action(Component c, ActionEvent event) {
      }

      protected void onMain_Button2Action(Component c, ActionEvent event) {
      }

      protected void onMain_BillTotalFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_WorkEffortFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_TotalFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_CaroselAction(Component c, ActionEvent event) {
      }

      protected void onSplash_CaroselAction(Component c, ActionEvent event) {
      }

}
