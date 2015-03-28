/**
 * Your application code goes here
 */

package userclasses;

import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.events.*;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {
    private double calcValue = 0;
    private static final int DIVIDE = 1;
    private static final int MULTIPLY = 2;
    private static final int SUBTRACT = 3;
    private static final int ADD = 4;
    private int pendingOp;
    
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {
    }


    private void append(Component c, int v) {
        TextField t = findCalculatorField(c.getParent());
        t.setText(t.getText() + v);
    }
    
    @Override
    protected void onCalculator_SevenAction(Component c, ActionEvent event) {
        append(c, 7);
    }

    @Override
    protected void onCalculator_EightAction(Component c, ActionEvent event) {
        append(c, 8);
    }

    @Override
    protected void onCalculator_NineAction(Component c, ActionEvent event) {
        append(c, 9);
    }

    private double getCurrentCalcValue(Component c) {
        return Double.parseDouble(findCalculatorField(c.getParent()).getText());
    }
    
    private void performOp(Component c, int upcoming, boolean reset) {
        double val = getCurrentCalcValue(c);
        switch(pendingOp) {
            case DIVIDE:
                calcValue /= val;
                break;
            case MULTIPLY:
                calcValue *= val;
                break;
            case ADD:
                calcValue += val;
                break;
            case SUBTRACT:
                calcValue -= val;
                break;
            default:
                calcValue = val;
                break;
        }
        pendingOp = upcoming;
        if(reset) {
            findCalculatorField(c.getParent()).setText("");
        }
    }
        
    @Override
    protected void onCalculator_DivideAction(Component c, ActionEvent event) {
        performOp(c, DIVIDE, true);
    }

    @Override
    protected void onCalculator_FourAction(Component c, ActionEvent event) {
        append(c, 4);
    }

    @Override
    protected void onCalculator_FiveAction(Component c, ActionEvent event) {
        append(c, 5);
    }

    @Override
    protected void onCalculator_SixAction(Component c, ActionEvent event) {
        append(c, 6);
    }

    @Override
    protected void onCalculator_MultiplyAction(Component c, ActionEvent event) {
        performOp(c, MULTIPLY, true);
    }

    @Override
    protected void onCalculator_OneAction(Component c, ActionEvent event) {
        append(c, 1);
    }

    @Override
    protected void onCalculator_TwoAction(Component c, ActionEvent event) {
        append(c, 2);
    }

    @Override
    protected void onCalculator_ThreeAction(Component c, ActionEvent event) {
        append(c, 3);
    }

    @Override
    protected void onCalculator_MinusAction(Component c, ActionEvent event) {
        performOp(c, SUBTRACT, true);
    }

    @Override
    protected void onCalculator_ZeroAction(Component c, ActionEvent event) {
        append(c, 0);
    }

    @Override
    protected void onCalculator_DecimalPointAction(Component c, ActionEvent event) {
        TextField t = findCalculatorField(c.getParent());
        if(t.getText().indexOf('.') < 0) {
            t.setText(t.getText() + '.');
        }
    }

    @Override
    protected void onCalculator_PlusMinusAction(Component c, ActionEvent event) {
        TextField t = findCalculatorField(c.getParent());
        double d = Double.parseDouble(t.getText()) * -1;
        t.setText("" + calcValueAsString(d));
    }

    @Override
    protected void onCalculator_PlusAction(Component c, ActionEvent event) {
        performOp(c, ADD, true);
    }

    @Override
    protected void onCalculator_ClearAction(Component c, ActionEvent event) {
        findCalculatorField(c.getParent()).setText("");
        calcValue = 0;
        pendingOp = 0;
    }

    @Override
    protected void onCalculator_EqualsAction(Component c, ActionEvent event) {
        performOp(c, 0, false);
        findCalculatorField(c.getParent()).setText("" + calcValueAsString(calcValue));
    }

    @Override
    protected void beforeCalculator(Form f) {
        findCalculatorField(f).setText("" + calcValueAsString(calcValue));
    }
    
    private String calcValueAsString(double d) {
        long v = (long)d;
        if(((double)v) == d) {
            return "" + v;
        }
        return "" + d;
    }
}
