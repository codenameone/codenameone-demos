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
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
        UIBuilder.registerCustomComponent("Calendar", com.codename1.ui.Calendar.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        UIBuilder.registerCustomComponent("Slider", com.codename1.ui.Slider.class);
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
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
        UIBuilder.registerCustomComponent("Calendar", com.codename1.ui.Calendar.class);
        UIBuilder.registerCustomComponent("TextField", com.codename1.ui.TextField.class);
        UIBuilder.registerCustomComponent("Slider", com.codename1.ui.Slider.class);
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

    public com.codename1.ui.Button findButton5(Container root) {
        return (com.codename1.ui.Button)findByName("Button5", root);
    }

    public com.codename1.ui.Button findButton4(Container root) {
        return (com.codename1.ui.Button)findByName("Button4", root);
    }

    public com.codename1.ui.Container findContainer6(Container root) {
        return (com.codename1.ui.Container)findByName("Container6", root);
    }

    public com.codename1.ui.Button findButton3(Container root) {
        return (com.codename1.ui.Button)findByName("Button3", root);
    }

    public com.codename1.ui.Container findContainer5(Container root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Button findSeven(Container root) {
        return (com.codename1.ui.Button)findByName("Seven", root);
    }

    public com.codename1.ui.Button findZero(Container root) {
        return (com.codename1.ui.Button)findByName("Zero", root);
    }

    public com.codename1.ui.Button findThree(Container root) {
        return (com.codename1.ui.Button)findByName("Three", root);
    }

    public com.codename1.ui.Button findClear(Container root) {
        return (com.codename1.ui.Button)findByName("Clear", root);
    }

    public com.codename1.ui.Slider findSlider(Container root) {
        return (com.codename1.ui.Slider)findByName("Slider", root);
    }

    public com.codename1.ui.Button findEight(Container root) {
        return (com.codename1.ui.Button)findByName("Eight", root);
    }

    public com.codename1.ui.Button findTwo(Container root) {
        return (com.codename1.ui.Button)findByName("Two", root);
    }

    public com.codename1.ui.Button findMultiply(Container root) {
        return (com.codename1.ui.Button)findByName("Multiply", root);
    }

    public com.codename1.ui.Button findSix(Container root) {
        return (com.codename1.ui.Button)findByName("Six", root);
    }

    public com.codename1.ui.Form findMain(Container root) {
        return (com.codename1.ui.Form)findByName("Main", root);
    }

    public com.codename1.ui.Button findButton22(Container root) {
        return (com.codename1.ui.Button)findByName("Button22", root);
    }

    public com.codename1.ui.Button findBracketL(Container root) {
        return (com.codename1.ui.Button)findByName("bracketL", root);
    }

    public com.codename1.ui.TextField findCalculatorField(Container root) {
        return (com.codename1.ui.TextField)findByName("calculatorField", root);
    }

    public com.codename1.ui.Button findFive(Container root) {
        return (com.codename1.ui.Button)findByName("Five", root);
    }

    public com.codename1.ui.Button findDivide(Container root) {
        return (com.codename1.ui.Button)findByName("Divide", root);
    }

    public com.codename1.ui.Button findOne(Container root) {
        return (com.codename1.ui.Button)findByName("One", root);
    }

    public com.codename1.ui.Container findContainer(Container root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Button findPlusMinus(Container root) {
        return (com.codename1.ui.Button)findByName("PlusMinus", root);
    }

    public com.codename1.ui.Button findPlus(Container root) {
        return (com.codename1.ui.Button)findByName("Plus", root);
    }

    public com.codename1.ui.Button findMinus(Container root) {
        return (com.codename1.ui.Button)findByName("Minus", root);
    }

    public com.codename1.ui.Button findBrecketR(Container root) {
        return (com.codename1.ui.Button)findByName("brecketR", root);
    }

    public com.codename1.ui.Calendar findCalendar(Container root) {
        return (com.codename1.ui.Calendar)findByName("Calendar", root);
    }

    public com.codename1.ui.Button findM(Container root) {
        return (com.codename1.ui.Button)findByName("m", root);
    }

    public com.codename1.ui.TextField findTextField(Container root) {
        return (com.codename1.ui.TextField)findByName("TextField", root);
    }

    public com.codename1.ui.Button findFour(Container root) {
        return (com.codename1.ui.Button)findByName("Four", root);
    }

    public com.codename1.ui.Button findDecimalPoint(Container root) {
        return (com.codename1.ui.Button)findByName("DecimalPoint", root);
    }

    public com.codename1.ui.Tabs findTabs(Container root) {
        return (com.codename1.ui.Tabs)findByName("Tabs", root);
    }

    public com.codename1.ui.Button findNine(Container root) {
        return (com.codename1.ui.Button)findByName("Nine", root);
    }

    public com.codename1.ui.Form findCalculator(Container root) {
        return (com.codename1.ui.Form)findByName("Calculator", root);
    }

    public com.codename1.ui.Button findEquals(Container root) {
        return (com.codename1.ui.Button)findByName("Equals", root);
    }

    public com.codename1.ui.list.MultiList findMultiList(Container root) {
        return (com.codename1.ui.list.MultiList)findByName("MultiList", root);
    }

    public static final int COMMAND_MainCalculator = 8;
    public static final int COMMAND_CalculatorCommand7 = 7;

    protected boolean onMainCalculator() {
        return false;
    }

    protected boolean onCalculatorCommand7() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_MainCalculator:
                if(onMainCalculator()) {
                    ev.consume();
                }
                return;

            case COMMAND_CalculatorCommand7:
                if(onCalculatorCommand7()) {
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

        if("Calculator".equals(f.getName())) {
            exitCalculator(f);
            return;
        }

    }


    protected void exitMain(Form f) {
    }


    protected void exitCalculator(Form f) {
    }

    protected void beforeShow(Form f) {
        if("Main".equals(f.getName())) {
            beforeMain(f);
            return;
        }

        if("Calculator".equals(f.getName())) {
            beforeCalculator(f);
            return;
        }

    }


    protected void beforeMain(Form f) {
    }


    protected void beforeCalculator(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            return;
        }

        if("Calculator".equals(c.getName())) {
            beforeContainerCalculator(c);
            return;
        }

    }


    protected void beforeContainerMain(Container c) {
    }


    protected void beforeContainerCalculator(Container c) {
    }

    protected void postShow(Form f) {
        if("Main".equals(f.getName())) {
            postMain(f);
            return;
        }

        if("Calculator".equals(f.getName())) {
            postCalculator(f);
            return;
        }

    }


    protected void postMain(Form f) {
    }


    protected void postCalculator(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Main".equals(c.getName())) {
            postContainerMain(c);
            return;
        }

        if("Calculator".equals(c.getName())) {
            postContainerCalculator(c);
            return;
        }

    }


    protected void postContainerMain(Container c) {
    }


    protected void postContainerCalculator(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Main".equals(rootName)) {
            onCreateMain();
            return;
        }

        if("Calculator".equals(rootName)) {
            onCreateCalculator();
            return;
        }

    }


    protected void onCreateMain() {
    }


    protected void onCreateCalculator() {
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
        if(rootContainerName.equals("Calculator")) {
            if("calculatorField".equals(c.getName())) {
                onCalculator_CalculatorFieldAction(c, event);
                return;
            }
            if("Button22".equals(c.getName())) {
                onCalculator_Button22Action(c, event);
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
    }

      protected void onMain_TextFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_Button5Action(Component c, ActionEvent event) {
      }

      protected void onMain_Button4Action(Component c, ActionEvent event) {
      }

      protected void onMain_MultiListAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_CalculatorFieldAction(Component c, ActionEvent event) {
      }

      protected void onCalculator_Button22Action(Component c, ActionEvent event) {
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

}
