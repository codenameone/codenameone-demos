/**
 * Your application code goes here
 */

package userclasses;

import com.codename1.components.MultiButton;
import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {    
    private int selectedOffset = 0;
    private static final String[] C_NAMES = {
        "Jessica White",
        "Beatrice Black",
        "John Bono",
        "Beier Mark"
    };
    private static final String[] C_DATE = {
        "14-Jun-2012",
        "12-May-2012",
        "11-Aug-2011",
        "10-Apr-2010"
    };
    
    private static final String[] C_LINE1 = {
        "Lorem ipsm bla bla",
        "Lorem ipsm bla bla",
        "Lorem ipsm bla bla",
        "Lorem ipsm bla bla"
    };
    
    private static final String[] C_LINE2 = {
        "Some more lorem ipsm",
        "Some more lorem ipsm",
        "Some more lorem ipsm",
        "Some more lorem ipsm"
    };
    
    private static final String[] C_AVATAR = {
        "avatar-1a.jpg",
        "avatar-2a.jpg",
        "avatar-3a.jpg",
        "avatar-4a.jpg"
    };

    private static final String[] C_AVATAR_LARGE = {
        "avatar-1b.jpg",
        "avatar-2b.jpg",
        "avatar-3b.jpg",
        "avatar-4b.jpg"
    };
    
    private boolean dialogShowing;
    
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
    public Container createContainer(Resources res, String resourceName) {
        if(Display.getInstance().isTablet() && resourceName.equals("Main")) {
            return super.createContainer(res, "Person");
        }
        return super.createContainer(res, resourceName);
    }    

    private void updatePersonForm(Form f) {
        findAvatar(f).setIcon(fetchResourceFile().getImage(C_AVATAR_LARGE[selectedOffset]));
        findPersonName(f).setText(C_NAMES[selectedOffset]);
    }
    
    @Override
    protected void beforePerson(final Form f) {
        updatePersonForm(f);
        if(Display.getInstance().isTablet()) {
            f.setTransitionOutAnimator(CommonTransitions.createEmpty());
            Container listOfFriends = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            listOfFriends.setHideInPortrait(true);
            addPeople(listOfFriends);
            f.addComponent(BorderLayout.WEST, listOfFriends);
            listOfFriends.setScrollableY(true);
            final Command peopleCommand = new Command("People") {
                public void actionPerformed(ActionEvent ev) {
                    Dialog dlg = new Dialog("People");
                    dlg.setDialogUIID("Container");
                    dlg.getContentPane().setUIID("Form");
                    dlg.getTitleComponent().setUIID("TitleArea");
                    dlg.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                    dlg.setTransitionInAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
                    dlg.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 250));
                    addPeople(dlg);
                    dlg.setDisposeWhenPointerOutOfBounds(true);
                    dialogShowing = true;
                    dlg.showStetched(BorderLayout.WEST, true);
                    dialogShowing = false;
                }
            };
            if(Display.getInstance().isPortrait()) {
                f.addCommand(peopleCommand, 1);
            }
            f.addOrientationListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(dialogShowing) {
                        Form f = Display.getInstance().getCurrent();
                        if(f instanceof Dialog) {
                            ((Dialog)f).dispose();
                        }
                    }
                    if(Display.getInstance().isPortrait()) {
                        f.addCommand(peopleCommand, 1);
                    } else {
                        f.removeCommand(peopleCommand);
                        f.revalidate();
                    }
                }
            });
        }
    }
    
    // adds constant people to the given container
    private void addPeople(Container c) {
        c.removeAll();
        Resources r = fetchResourceFile();
        for(int iter = 0 ; iter < C_NAMES.length ; iter++) {
            MultiButton mb = new MultiButton();
            mb.setEmblem(null);
            mb.setHorizontalLayout(true);
            mb.setTextLine1(C_NAMES[iter]);
            mb.setTextLine2(C_DATE[iter]);
            mb.setTextLine3(C_LINE1[iter]);
            mb.setTextLine4(C_LINE2[iter]);
            mb.setMaskName("maskImage");
            mb.setIconUIID("Avatar");
            mb.setIcon(r.getImage(C_AVATAR[iter]));
            final int current = iter;
            mb.setCommand(new Command("") {
                public void actionPerformed(ActionEvent ev) {
                    selectedOffset = current;
                    showForm("Person", null);
                }
            });
            c.addComponent(mb);
        }
    }

    @Override
    protected void beforeMain(Form f) {
        addPeople(findFriends(f));
    }
}
