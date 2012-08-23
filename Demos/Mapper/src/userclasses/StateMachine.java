/**
 * Your application code goes here
 */

package userclasses;

import com.codename1.components.MasterDetail;
import com.codename1.maps.BoundingBox;
import com.codename1.maps.Coord;
import com.codename1.maps.MapComponent;
import com.codename1.maps.layers.PointLayer;
import com.codename1.maps.layers.PointsLayer;
import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;

/**
 * Demo mapping application
 *
 * @author Shai Almog
 */
public class StateMachine extends StateMachineBase {
    static class ATMData {
        String name;
        String location;
        String zip;
        String distance;
        String metric;
        String paid;
        double lat;
        double lon;
        
        ATMData(String name,
            String location,
            String zip,
            String distance,
            String metric,
            String paid,
            double lat,
            double lon) {
            this.name = name;
            this.location = location;
            this.zip = zip;
            this.distance = distance;
            this.metric = metric;
            this.paid = paid;
            this.lat = lat;
            this.lon = lon;
        }
    }
    
    private static final ATMData[] DATA = new ATMData[] {
        new ATMData("HSBC", "14 Mandeville Way, Bolton", "BN2 0AB, Buckinghamshire", "1.2", "km", "paid", 52.0333, -0.7),
        new ATMData("Royal Bank of Scotland", "12 Nationwide Building Society, Costco, Milton Keynes", "MK10 0AB, Buckinghamshire", "0.43", "km", "free", 51.500, -0.126),
        new ATMData("LLoyds TSB", "50 St John Boulevard, Camden Town", "SE34 9AB, Bedfordshire", "3.45", "km", "free", 52.0333, -0.126),
        new ATMData("Nationwide Building Soc.", "34 Malverton Drive, Fishermead", "MK3 2NB, Herford", "2.43", "km", "free", 51.5000, -0.34),
        new ATMData("Royal Bank of Scotland", "12 Nationwide Building Society, Costco, Milton Keynes", "MK10 0AB, Buckinghamshire", "0.43", "km", "free", 52.045, -0.145),
        new ATMData("Barclays Bank PLC", "Riveter Lane, 54 Moullen Road, Bath", "BA2 0AB, Bath", "3.21", "km", "free", 52.0321, -0.293),
    };

    
    private ATMData current = DATA[0];
    
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


    @Override
    protected void beforeMaps(Form f) {
        Container entriesContainer = findEntries(f);
        ((BorderLayout)entriesContainer.getLayout()).setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELLOW);
        MapComponent map = findMapEntry(f);
        entriesContainer.removeComponent(map);
        entriesContainer.addComponent(0, BorderLayout.CENTER, map);
        if(Display.getInstance().isTablet()) {
            Container listATMs = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            listATMs.setHideInPortrait(true);
            addATMs(listATMs, false);
            Container portraitListATMs = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            addATMs(portraitListATMs, false);            
            MasterDetail.bindTabletLandscapeMaster(f, f, listATMs, portraitListATMs, "ATM's", null);
            entriesContainer.removeComponent(findMapOverlay(f));
        } else {
            findAtmMainTitle(f).setText(current.name);
            findAtmMainDistance(f).setText(current.distance);
            findAtmMainZip(f).setText(current.zip);
            findAtmMainText(f).setText(current.location);
            findAtmMainUnit(f).setText(current.metric);
            findAtmMainPaid(f).setText(current.paid);
        }
        updateFormMap(f);
    }
    
    private void addATMs(Container c, boolean forceback) {
        c.setScrollableY(true);
        for(ATMData d : DATA) {
            c.addComponent(createATM(d, forceback));
        }
    }
    
    private void updateFormMap(Form root) {
        MapComponent m = findMapEntry(root);
        
        Coord crd = new Coord(current.lat,current.lon);
        m.zoomTo(crd, 16);
        PointLayer lay = new PointLayer(crd, current.name, fetchResourceFile().getImage("pinhead.png"));
        PointsLayer points = new PointsLayer(current.name);
        points.addPoint(lay);
        m.addLayer(points);
    }
    
    private Container createATM(final ATMData d, final boolean forceBack) {
        Resources res = fetchResourceFile();
        Container c = createContainer(res, "Entry");
        Button title = findAtmTitle(c);
        title.setCommand(new Command(d.name) {
            public void actionPerformed(ActionEvent ev) {
                current = d;
                if(forceBack || !Display.getInstance().isTablet()) {
                    back();
                } else {
                    Form currentForm = Display.getInstance().getCurrent();
                    if(Display.getInstance().isPortrait()) {
                        ((Dialog)currentForm).dispose();
                        updateFormMap(Display.getInstance().getCurrent());
                    } else {
                        updateFormMap(currentForm);
                    }
                }
            }
        });
        findAtmLine2(c).setText(d.location + "\n" + d.zip);
        findAtmUnit(c).setText(d.metric);
        findAtmDistance(c).setText(d.distance);
        findAtmPaidOrFree(c).setText(d.paid);
        return c;
    }
    
    protected boolean allowBackTo(String formName) {
        // prevent back on a tablet to the first form which looks bad in iOS
        return !Display.getInstance().isTablet() || !formName.equals("Main");
    }

    @Override
    protected void beforeFullList(Form f) {
        Container c = findFullListContent(f);
        c.removeAll();
        addATMs(c, true);
    }
}
