/*
 * Copyright © 2008, 2010, Oracle and/or its affiliates. All rights reserved
 */

package userclasses;

import generated.StateMachineBase;
import com.codename1.ui.*;
import com.codename1.ui.animations.Animation;
import com.codename1.ui.util.*;
import com.codename1.ui.events.*;
import com.codename1.io.Storage;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Main class of the tipster demo
 *
 * @author Shai Almog
 */
public class StateMachine extends StateMachineBase {
    private Hashtable data;
    private static final String[] MONTHS = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private DataChangedListener sliderListener = new DataChangedListener() {
        public void dataChanged(int type, int index) {
            updateTotal(Display.getInstance().getCurrent());
        }
    };

    private SelectionListener carouselListener = new SelectionListener() {
        public void selectionChanged(int old, int index) {
            Form frm = Display.getInstance().getCurrent();
            Hashtable h = (Hashtable)findCarosel(frm).getSelectedItem();
            String name = (String)h.get("Name");
            Label scope = findWorkScope(frm);
            Label low = findLowQuality(frm);
            Label high = findHighQuality(frm);
            Label serviceType = findServiceType(frm);
            Label tipTotal = findTipTotal(frm);
            if(name.equals("Server")) {
                high.setText("Excellent");
                low.setText("Poor");
                serviceType.setText("Serice Quality");
                scope.setText("Number Of Diners");
                tipTotal.setText("Tip Per Diner");
                high.getParent().revalidate();
                return;
            }
            if(name.equals("Mover")) {
                high.setText("Excellent");
                low.setText("Poor");
                scope.setText("Work Hours");
                serviceType.setText("Service Quality");
                tipTotal.setText("Tip");
                high.getParent().revalidate();
                return;
            }
            if(name.equals("Driver")) {
                high.setText("Enjoyable");
                low.setText("Irritating");
                scope.setText("Minutes");
                serviceType.setText("Experience");
                tipTotal.setText("Tip");
                high.getParent().revalidate();
                return;
            }
        }
    };

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
        data = (Hashtable)Storage.getInstance().readObject("data");
        if(data == null) {
            data = new Hashtable();
            data.put("currency", "usd");
            addHistory();
        }
    }

    private void save() {
        Storage.getInstance().writeObject("data", data);
    }

    protected void beforeSplash(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeSplash(f);
        final List l = findCarosel(f);
        f.registerAnimated(new Animation() {
            private long lastTime = System.currentTimeMillis();
            public boolean animate() {
                long t = System.currentTimeMillis();
                if(t - lastTime >= 200) {
                    int i = l.getSelectedIndex();
                    i++;
                    if(i >= l.getModel().getSize()) {
                        i = 0;
                    }
                    l.setSelectedIndex(i);
                    lastTime = t;
                }
                return false;
            }

            public void paint(Graphics g) {
            }
        });
    }

    protected void postMain(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.postMain(f);
        findCarosel(f).addSelectionListener(carouselListener);
        findQualitySlider(f).addDataChangedListener(sliderListener);
    }

    private int getTextFieldInt(TextField f) {
        try {
            return Integer.parseInt(f.getText());
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    private String formatFloat(float val) {
        float fraction = val - ((int)val);
        fraction *= 100;
        String t = ((int)val) + "." + ((int)fraction);
        return t;
    }

    private void updateTotalValue(float val, Container c) {
        TextField total = findTotalField(c);
        String t = formatFloat(val);
        total.setText(t);
        data.put("total", t);
        save();
    }

    private void updateTotal(Container parent) {
        List carosel = findCarosel(parent);
        // can happen when the VKB is showing
        if(carosel == null) {
            return;
        }
        Hashtable h = (Hashtable)carosel.getSelectedItem();
        String name = (String)h.get("Name");
        float billTotal = getTextFieldInt(findBillTotalField(parent));
        float workEffort = getTextFieldInt(findWorkEffortField(parent));
        float qualityOfWorkFactor = (((float)findQualitySlider(parent).getProgress()) / 100.0f * 4 + 8) / 100;
        if(name.equals("Server")) {
            billTotal = billTotal / workEffort * qualityOfWorkFactor;
            updateTotalValue(billTotal, parent);
            return;
        }
        if(name.equals("Mover")) {
            billTotal = billTotal * qualityOfWorkFactor;
            updateTotalValue(billTotal, parent);
            return;
        }
        if(name.equals("Driver")) {
            billTotal = billTotal * qualityOfWorkFactor;
            updateTotalValue(billTotal, parent);
            return;
        }
    }

    protected void onMain_WorkEffortFieldAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onMain_WorkEffortFieldAction(c, event);
        updateTotal(c.getParent());
    }

    protected void onMain_BillTotalFieldAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onMain_BillTotalFieldAction(c, event);
        updateTotal(c.getParent());
    }

    protected void exitMain(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.exitMain(f);
        data.put("billTotal", findBillTotalField(f).getText());
        data.put("dinerCount", findWorkEffortField(f).getText());
        data.put("quality", new Integer(findQualitySlider(f).getProgress()));
        data.put("carouselSelection", new Integer(findCarosel(f).getSelectedIndex()));
        save();
    }

    protected void beforeMain(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeMain(f);
        if(data.containsKey("billTotal")) {
            findBillTotalField(f).setText((String)data.get("billTotal"));
            findWorkEffortField(f).setText((String)data.get("dinerCount"));
            findQualitySlider(f).setProgress(((Integer)data.get("quality")).intValue());
            findCarosel(f).setSelectedIndex(((Integer)data.get("carouselSelection")).intValue());
            updateTotal(f);
        }
    }

    protected void beforeCurrency(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeCurrency(f);
        if(data.containsKey("currency")) {
            ((RadioButton)findByName((String)data.get("currency"), f)).setSelected(true);
        }
        f.revalidate();
        Container c = findCurrencyContainer(f);
        for(int iter = 0 ; iter < c.getComponentCount() ; iter++) {
            Component current = c.getComponentAt(iter);
            if(iter % 2 == 0) {
                current.setX(-current.getWidth());
            } else {
                current.setX(current.getWidth());
            }
        }
        c.setShouldCalcPreferredSize(true);
        c.animateLayout(1000);
    }

    private void saveCurrency(String c) {
        data.put("currency", c);
        save();
    }

    protected void onCurrency_UsdAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCurrency_UsdAction(c, event);
        saveCurrency(c.getName());
    }

    protected void onCurrency_EurAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCurrency_EurAction(c, event);
        saveCurrency(c.getName());
    }

    protected void onCurrency_ChfAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCurrency_ChfAction(c, event);
        saveCurrency(c.getName());
    }

    protected void onCurrency_JpyAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCurrency_JpyAction(c, event);
        saveCurrency(c.getName());
    }

    protected void onCurrency_InrAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCurrency_InrAction(c, event);
        saveCurrency(c.getName());
    }

    protected void onCurrency_GbpAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCurrency_GbpAction(c, event);
        saveCurrency(c.getName());
    }

    protected void beforeHistory(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeHistory(f);
        Container historyGroup = findHistoryContainer(f);
        historyGroup.removeAll();
        Vector history = (Vector)data.get("history");
        if(history != null) {
            for(int iter = 0 ; iter < history.size() ; iter++) {
                Vector month = (Vector)history.elementAt(iter);
                historyGroup.addComponent(createMonthContainer(month));
            }
            f.revalidate();
            for(int iter = 0 ; iter < historyGroup.getComponentCount() ; iter++) {
                Component c = historyGroup.getComponentAt(iter);
                c.setY(-c.getHeight());
                historyGroup.setShouldCalcPreferredSize(true);
            }
            historyGroup.animateLayout(1000);
        }
    }

   private Container createMonthContainer(Vector month) {
        Date d = (Date)month.elementAt(0);
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(d);
        Container historyMonth = createContainer(fetchResourceFile(), "HistoryMonth");
        findTitleLabel(historyMonth).setText(MONTHS[c.get(java.util.Calendar.MONTH)]);
        Container entries = findEntries(historyMonth);
        entries.removeAll();
        float totalValue = 0;
        for(int iter = 1 ; iter < month.size() ; iter++) {
            Hashtable hash = (Hashtable)month.elementAt(iter);
            Date current = (Date)hash.get("date");
            String price = (String)hash.get("price");
            Container monthEntry = createContainer(fetchResourceFile(), "HistoryEntry");
            c.setTime(current);
            findDate(monthEntry).setText(formatDate(c));
            totalValue += Float.parseFloat(price);
            findAmount(monthEntry).setText(price);
            entries.addComponent(monthEntry);
        }
        findTotal(historyMonth).setText(formatFloat(totalValue) + " " + data.get("currency"));
        return historyMonth;
    }

    private String formatDate(java.util.Calendar c) {
        return c.get(java.util.Calendar.DAY_OF_MONTH) + "-" + MONTHS[c.get(java.util.Calendar.MONTH)] + " " + c.get(java.util.Calendar.YEAR);
    }

    public void addHistory() {
        Vector history = (Vector)data.get("history");
        if(history == null || history.size() == 0) {
            history = new Vector();
            data.put("history", history);
            Vector firstEntry = new Vector();
            history.addElement(firstEntry);
            firstEntry.addElement(new Date());
            Hashtable hash = new Hashtable();
            firstEntry.addElement(hash);
            Object t = data.get("total");
            if(t == null) {
                t = "100";
            }
            hash.put("date", new Date());
            hash.put("price", t);

            // adding dummy data to begin with something for the demo
            Vector dummyVec = new Vector();
            history.addElement(dummyVec);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
            cal.set(java.util.Calendar.YEAR, 2011);
            dummyVec.addElement(cal.getTime());
            hash = new Hashtable();
            dummyVec.addElement(hash);
            hash.put("date", cal.getTime());
            hash.put("price", "5.55");
            hash = new Hashtable();
            dummyVec.addElement(hash);
            hash.put("date", cal.getTime());
            hash.put("price", "6.66");
            return;
        }

        Vector first = (Vector)history.elementAt(0);
        Date d = (Date)first.elementAt(0);
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new Date());
        int thisMonth = cal.get(java.util.Calendar.MONTH);
        int thisYear = cal.get(java.util.Calendar.YEAR);
        cal.setTime(d);
        if(!(thisMonth == cal.get(java.util.Calendar.MONTH) && thisYear == cal.get(java.util.Calendar.YEAR))) {
            // we should use a new vector
            first = new Vector();
            first.addElement(new Date());
            history.insertElementAt(first, 0);
        }
        Hashtable hash = new Hashtable();
        first.addElement(hash);
        hash.put("date", new Date());
        Object t = data.get("total");
        if (t == null) {
            t = "100";
        }
        hash.put("price", t);


    }

    protected boolean onMainExit() {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        boolean val = super.onMainExit();
        addHistory();
        save();
        return val;
    }
}
