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
import java.util.Hashtable;
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
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        UIBuilder.registerCustomComponent("Slider", com.codename1.ui.Slider.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Calendar", com.codename1.ui.Calendar.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
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
            return showForm(getFirstFormName(), null);
        } else {
            Form f = (Form)createContainer(resPath, getFirstFormName());
            initVars(fetchResourceFile());
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    protected String getFirstFormName() {
        return "Main";
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        UIBuilder.registerCustomComponent("Slider", com.codename1.ui.Slider.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Calendar", com.codename1.ui.Calendar.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
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

    public com.codename1.ui.Button findBracketL(Component root) {
        return (com.codename1.ui.Button)findByName("bracketL", root);
    }

    public com.codename1.ui.Button findBracketL() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("bracketL", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("bracketL", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findSix(Component root) {
        return (com.codename1.ui.Button)findByName("Six", root);
    }

    public com.codename1.ui.Button findSix() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Six", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Six", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Slider findSlider(Component root) {
        return (com.codename1.ui.Slider)findByName("Slider", root);
    }

    public com.codename1.ui.Slider findSlider() {
        com.codename1.ui.Slider cmp = (com.codename1.ui.Slider)findByName("Slider", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Slider)findByName("Slider", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findOne(Component root) {
        return (com.codename1.ui.Button)findByName("One", root);
    }

    public com.codename1.ui.Button findOne() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("One", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("One", aboutToShowThisContainer);
        }
        return cmp;
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

    public com.codename1.ui.Container findContainer4(Component root) {
        return (com.codename1.ui.Container)findByName("Container4", root);
    }

    public com.codename1.ui.Container findContainer4() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container4", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container4", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer1(Component root) {
        return (com.codename1.ui.Container)findByName("Container1", root);
    }

    public com.codename1.ui.Container findContainer1() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer2(Component root) {
        return (com.codename1.ui.Container)findByName("Container2", root);
    }

    public com.codename1.ui.Container findContainer2() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer5(Component root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Container findContainer5() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container5", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container5", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findClear(Component root) {
        return (com.codename1.ui.Button)findByName("Clear", root);
    }

    public com.codename1.ui.Button findClear() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Clear", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Clear", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer6(Component root) {
        return (com.codename1.ui.Container)findByName("Container6", root);
    }

    public com.codename1.ui.Container findContainer6() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container6", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container6", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findPlusMinus(Component root) {
        return (com.codename1.ui.Button)findByName("PlusMinus", root);
    }

    public com.codename1.ui.Button findPlusMinus() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("PlusMinus", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("PlusMinus", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findDivide(Component root) {
        return (com.codename1.ui.Button)findByName("Divide", root);
    }

    public com.codename1.ui.Button findDivide() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Divide", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Divide", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findEight(Component root) {
        return (com.codename1.ui.Button)findByName("Eight", root);
    }

    public com.codename1.ui.Button findEight() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Eight", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Eight", aboutToShowThisContainer);
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

    public com.codename1.ui.Button findMultiply(Component root) {
        return (com.codename1.ui.Button)findByName("Multiply", root);
    }

    public com.codename1.ui.Button findMultiply() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Multiply", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Multiply", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findTextField(Component root) {
        return (com.codename1.ui.TextField)findByName("TextField", root);
    }

    public com.codename1.ui.TextField findTextField() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("TextField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("TextField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findPlus(Component root) {
        return (com.codename1.ui.Button)findByName("Plus", root);
    }

    public com.codename1.ui.Button findPlus() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Plus", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Plus", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findBrecketR(Component root) {
        return (com.codename1.ui.Button)findByName("brecketR", root);
    }

    public com.codename1.ui.Button findBrecketR() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("brecketR", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("brecketR", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findZero(Component root) {
        return (com.codename1.ui.Button)findByName("Zero", root);
    }

    public com.codename1.ui.Button findZero() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Zero", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Zero", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextField findCalculatorField(Component root) {
        return (com.codename1.ui.TextField)findByName("calculatorField", root);
    }

    public com.codename1.ui.TextField findCalculatorField() {
        com.codename1.ui.TextField cmp = (com.codename1.ui.TextField)findByName("calculatorField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextField)findByName("calculatorField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findFour(Component root) {
        return (com.codename1.ui.Button)findByName("Four", root);
    }

    public com.codename1.ui.Button findFour() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Four", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Four", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Calendar findCalendar(Component root) {
        return (com.codename1.ui.Calendar)findByName("Calendar", root);
    }

    public com.codename1.ui.Calendar findCalendar() {
        com.codename1.ui.Calendar cmp = (com.codename1.ui.Calendar)findByName("Calendar", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Calendar)findByName("Calendar", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findMultiList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("MultiList", root);
    }

    public com.codename1.ui.list.MultiList findMultiList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("MultiList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("MultiList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findSeven(Component root) {
        return (com.codename1.ui.Button)findByName("Seven", root);
    }

    public com.codename1.ui.Button findSeven() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Seven", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Seven", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findM(Component root) {
        return (com.codename1.ui.Button)findByName("m", root);
    }

    public com.codename1.ui.Button findM() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("m", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("m", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findTwo(Component root) {
        return (com.codename1.ui.Button)findByName("Two", root);
    }

    public com.codename1.ui.Button findTwo() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Two", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Two", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findThree(Component root) {
        return (com.codename1.ui.Button)findByName("Three", root);
    }

    public com.codename1.ui.Button findThree() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Three", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Three", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findEquals(Component root) {
        return (com.codename1.ui.Button)findByName("Equals", root);
    }

    public com.codename1.ui.Button findEquals() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Equals", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Equals", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findFive(Component root) {
        return (com.codename1.ui.Button)findByName("Five", root);
    }

    public com.codename1.ui.Button findFive() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Five", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Five", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findNine(Component root) {
        return (com.codename1.ui.Button)findByName("Nine", root);
    }

    public com.codename1.ui.Button findNine() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Nine", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Nine", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton5(Component root) {
        return (com.codename1.ui.Button)findByName("Button5", root);
    }

    public com.codename1.ui.Button findButton5() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button5", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button5", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findDecimalPoint(Component root) {
        return (com.codename1.ui.Button)findByName("DecimalPoint", root);
    }

    public com.codename1.ui.Button findDecimalPoint() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("DecimalPoint", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("DecimalPoint", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton4(Component root) {
        return (com.codename1.ui.Button)findByName("Button4", root);
    }

    public com.codename1.ui.Button findButton4() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button4", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button4", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Tabs findTabs(Component root) {
        return (com.codename1.ui.Tabs)findByName("Tabs", root);
    }

    public com.codename1.ui.Tabs findTabs() {
        com.codename1.ui.Tabs cmp = (com.codename1.ui.Tabs)findByName("Tabs", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Tabs)findByName("Tabs", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton3(Component root) {
        return (com.codename1.ui.Button)findByName("Button3", root);
    }

    public com.codename1.ui.Button findButton3() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findMinus(Component root) {
        return (com.codename1.ui.Button)findByName("Minus", root);
    }

    public com.codename1.ui.Button findMinus() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Minus", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Minus", aboutToShowThisContainer);
        }
        return cmp;
    }

    protected void exitForm(Form f) {
        if("Calculator".equals(f.getName())) {
            exitCalculator(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            exitMain(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void exitCalculator(Form f) {
    }


    protected void exitMain(Form f) {
    }

    protected void beforeShow(Form f) {
    aboutToShowThisContainer = f;
        if("Calculator".equals(f.getName())) {
            beforeCalculator(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            beforeMain(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeCalculator(Form f) {
    }


    protected void beforeMain(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        aboutToShowThisContainer = c;
        if("Calculator".equals(c.getName())) {
            beforeContainerCalculator(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeContainerCalculator(Container c) {
    }


    protected void beforeContainerMain(Container c) {
    }

    protected void postShow(Form f) {
        if("Calculator".equals(f.getName())) {
            postCalculator(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            postMain(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postCalculator(Form f) {
    }


    protected void postMain(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Calculator".equals(c.getName())) {
            postContainerCalculator(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(c.getName())) {
            postContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postContainerCalculator(Container c) {
    }


    protected void postContainerMain(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Calculator".equals(rootName)) {
            onCreateCalculator();
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(rootName)) {
            onCreateMain();
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void onCreateCalculator() {
    }


    protected void onCreateMain() {
    }

    protected Hashtable getFormState(Form f) {
        Hashtable h = super.getFormState(f);
        if("Calculator".equals(f.getName())) {
            getStateCalculator(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Main".equals(f.getName())) {
            getStateMain(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

            return h;
    }


    protected void getStateCalculator(Form f, Hashtable h) {
    }


    protected void getStateMain(Form f, Hashtable h) {
    }

    protected void setFormState(Form f, Hashtable state) {
        super.setFormState(f, state);
        if("Calculator".equals(f.getName())) {
            setStateCalculator(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            setStateMain(f, state);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void setStateCalculator(Form f, Hashtable state) {
    }


    protected void setStateMain(Form f, Hashtable state) {
    }

    protected boolean setListModel(List cmp) {
        String listName = cmp.getName();
        if("Calendar".equals(listName)) {
            return initListModelCalendar(cmp);
        }
        if("MultiList".equals(listName)) {
            return initListModelMultiList(cmp);
        }
        return super.setListModel(cmp);
    }

    protected boolean initListModelCalendar(List cmp) {
        return false;
    }

    protected boolean initListModelMultiList(List cmp) {
        return false;
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        Container leadParentContainer = c.getParent().getLeadParent();
        if(leadParentContainer != null && leadParentContainer.getClass() != Container.class) {
            c = c.getParent().getLeadParent();
        }
        if(rootContainerName == null) return;
        if(rootContainerName.equals("Calculator")) {
            if("calculatorField".equals(c.getName())) {
                onCalculator_CalculatorFieldAction(c, event);
                return;
            }
            if("m".equals(c.getName())) {
                onCalculator_MAction(c, event);
                return;
            }
            if("bracketL".equals(c.getName())) {
                onCalculator_BracketLAction(c, event);
                return;
            }
            if("brecketR".equals(c.getName())) {
                onCalculator_BrecketRAction(c, event);
                return;
            }
            if("Button3".equals(c.getName())) {
                onCalculator_Button3Action(c, event);
                return;
            }
            if("Seven".equals(c.getName())) {
                onCalculator_SevenAction(c, event);
                return;
            }
            if("Eight".equals(c.getName())) {
                onCalculator_EightAction(c, event);
                return;
            }
            if("Nine".equals(c.getName())) {
                onCalculator_NineAction(c, event);
                return;
            }
            if("Divide".equals(c.getName())) {
                onCalculator_DivideAction(c, event);
                return;
            }
            if("Four".equals(c.getName())) {
                onCalculator_FourAction(c, event);
                return;
            }
            if("Five".equals(c.getName())) {
                onCalculator_FiveAction(c, event);
                return;
            }
            if("Six".equals(c.getName())) {
                onCalculator_SixAction(c, event);
                return;
            }
            if("Multiply".equals(c.getName())) {
                onCalculator_MultiplyAction(c, event);
                return;
            }
            if("One".equals(c.getName())) {
                onCalculator_OneAction(c, event);
                return;
            }
            if("Two".equals(c.getName())) {
                onCalculator_TwoAction(c, event);
                return;
            }
            if("Three".equals(c.getName())) {
                onCalculator_ThreeAction(c, event);
                return;
            }
            if("Minus".equals(c.getName())) {
                onCalculator_MinusAction(c, event);
                return;
            }
            if("Zero".equals(c.getName())) {
                onCalculator_ZeroAction(c, event);
                return;
            }
            if("DecimalPoint".equals(c.getName())) {
                onCalculator_DecimalPointAction(c, event);
                return;
            }
            if("PlusMinus".equals(c.getName())) {
                onCalculator_PlusMinusAction(c, event);
                return;
            }
            if("Plus".equals(c.getName())) {
                onCalculator_PlusAction(c, event);
                return;
            }
            if("Clear".equals(c.getName())) {
                onCalculator_ClearAction(c, event);
                return;
            }
            if("Equals".equals(c.getName())) {
                onCalculator_EqualsAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Main")) {
            if("Calendar".equals(c.getName())) {
                onMain_CalendarAction(c, event);
                return;
            }
            if("TextField".equals(c.getName())) {
                onMain_TextFieldAction(c, event);
                return;
            }
            if("Button5".equals(c.getName())) {
                onMain_Button5Action(c, event);
                return;
            }
            if("Button4".equals(c.getName())) {
                onMain_Button4Action(c, event);
                return;
            }
            if("MultiList".equals(c.getName())) {
                onMain_MultiListAction(c, event);
                return;
            }
        }
    }

      protected void onCalculator_CalculatorFieldAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_MAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_BracketLAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_BrecketRAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_Button3Action(Component c, ActionEvent event) {
      }

      protected void onCalculator_SevenAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_EightAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_NineAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_DivideAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_FourAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_FiveAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_SixAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_MultiplyAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_OneAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_TwoAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_ThreeAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_MinusAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_ZeroAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_DecimalPointAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_PlusMinusAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_PlusAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_ClearAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_EqualsAction(Component c, ActionEvent event) {
      }

      protected void onMain_CalendarAction(Component c, ActionEvent event) {
      }

      protected void onMain_TextFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_Button5Action(Component c, ActionEvent event) {
      }

      protected void onMain_Button4Action(Component c, ActionEvent event) {
      }

      protected void onMain_MultiListAction(Component c, ActionEvent event) {
      }

}
