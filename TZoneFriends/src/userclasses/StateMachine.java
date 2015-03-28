/*
 * Copyright © 2008, 2010, Oracle and/or its affiliates. All rights reserved
 */

package userclasses;

import generated.StateMachineBase;
import com.codename1.ui.*;
import com.codename1.ui.animations.Animation;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.util.*;
import com.codename1.ui.events.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import java.util.Date;
import java.util.Hashtable;

/**
 * Demo code for the t-zone application, most of the code in this class is related to timezone handling. This
 * class contains the entire code of the application
 *
 * @author Shai Almog
 */
public class StateMachine extends StateMachineBase {
    private boolean civilianTime;
    private boolean removeMode;
    private Image sunImage;
    private Image moonImage;
    private boolean friendsMode = true;
    private static final String[] DAYS = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] MONTHS = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static final int[] TIMEZONE_OFFSETS = new int[] {
        -12 * 60,
        -11 * 60,
        -10 * 60,
        -95 * 6,
        -9 * 60,
        -8 * 60,
        -7 * 60,
        -6 * 60,
        -5 * 60,
        -45 * 6,
        -4 * 60,
        -35 * 6,
        -3 * 60,
        -2 * 60,
        - 60,
        0,
        60,
        2 * 60,
        3 * 60,
        35 * 6,
        4 * 60,
        45 * 6,
        5 * 60,
        55 * 6,
        4 * 60,
        345,
        6 * 60,
        65 * 6,
        7 * 60,
        8 * 60,
        525,
        9 * 60,
        95 * 6,
        10 * 60,
        630,
        660,
        690,
        720,
        765,
        780,
        840
    };
    private static final String[] TIMEZONES = new String[] {
        "UTC-12:00",
        "UTC-11:00",
        "UTC-10:00",
        "UTC-09:30",
        "UTC-09:00",
        "UTC-08:00",
        "UTC-07:00",
        "UTC-06:00",
        "UTC-05:00",
        "UTC-04:30",
        "UTC-04:00",
        "UTC-03:30",
        "UTC-03:00",
        "UTC-02:00",
        "UTC-01:00",
        "UTC",
        "UTC+01:00",
        "UTC+02:00",
        "UTC+03:00",
        "UTC+03:30",
        "UTC+04:00",
        "UTC+04:30",
        "UTC+05:00",
        "UTC+05:30",
        "UTC+05:45",
        "UTC+06:00",
        "UTC+06:30",
        "UTC+07:00",
        "UTC+08:00",
        "UTC+08:45",
        "UTC+09:00",
        "UTC+09:30",
        "UTC+10:00",
        "UTC+10:30",
        "UTC+11:00",
        "UTC+11:30",
        "UTC+12:00",
        "UTC+12:45",
        "UTC+13:00",
        "UTC+14:00"
    };

    private static final Hashtable[] TIMEZONE_ENTRIES = new Hashtable[TIMEZONES.length];
    private static final Hashtable[] FRIENDS = new Hashtable[6];
    static {
        for(int iter = 0 ; iter < TIMEZONES.length ; iter++) {
            TIMEZONE_ENTRIES[iter] = new Hashtable();
            TIMEZONE_ENTRIES[iter].put("title", TIMEZONES[iter]);
            TIMEZONE_ENTRIES[iter].put("zone", new Integer(iter));
            if(TIMEZONE_OFFSETS[iter] == 0) {
                TIMEZONE_ENTRIES[iter].put("selected", "true");
            }
        }
        FRIENDS[0] = new Hashtable();
        FRIENDS[0].put("title", "Chen");
        FRIENDS[0].put("description", "LWUIT Daddy");
        FRIENDS[0].put("selected", "true");
        FRIENDS[0].put("zone", new Integer(4));
        FRIENDS[1] = new Hashtable();
        FRIENDS[1].put("title", "Shai");
        FRIENDS[1].put("description", "Isn't that obvious?");
        FRIENDS[1].put("selected", "true");
        FRIENDS[1].put("zone", new Integer(10));
        FRIENDS[2] = new Hashtable();
        FRIENDS[2].put("title", "Ofir");
        FRIENDS[2].put("description", "Would you like some HTML with that?");
        FRIENDS[2].put("zone", new Integer(12));
        FRIENDS[3] = new Hashtable();
        FRIENDS[3].put("title", "Yaniv");
        FRIENDS[3].put("description", "Can you explain that again?");
        FRIENDS[3].put("zone", new Integer(16));
        FRIENDS[4] = new Hashtable();
        FRIENDS[4].put("title", "Meirav");
        FRIENDS[4].put("description", "All that and on high heels");
        FRIENDS[4].put("zone", new Integer(20));
        FRIENDS[5] = new Hashtable();
        FRIENDS[5].put("title", "Martin");
        FRIENDS[5].put("description", "Everything that looks decent here is mine");
        FRIENDS[5].put("zone", new Integer(23));
    }

    private Animation timeUpdateAnimation = new Animation() {
        private long lastRun;
        public boolean animate() {
            long t = System.currentTimeMillis();
            if(t - lastRun > 2500) {
                Form f = Display.getInstance().getCurrent();
                Label subtitle = findSubtitle(f);
                if(subtitle != null) {
                    lastRun = t;
                    updateTimes(f);
                }
            }
            return false;
        }

        public void paint(Graphics grphcs) {
        }
    };

    private void updateTimes(Form f) {
        Label subtitle = findSubtitle(f);
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        String s = "My Time: " + timeAndDate(c);
        subtitle.setText(s);

        Container friends = findFriendsRoot(f);
        for(int iter = 0 ; iter < friends.getComponentCount() ; iter++) {
            Component fr = friends.getComponentAt(iter);
            if(fr instanceof Container) {
                updateFriendTime((Container)fr);
            }
        }
    }

    private String timeAndDate(java.util.Calendar c) {
        return formatTime(c.get(java.util.Calendar.HOUR_OF_DAY), c.get(java.util.Calendar.MINUTE)) + " " +
                            DAYS[c.get(java.util.Calendar.DAY_OF_WEEK) - 1] + ", " + c.get(java.util.Calendar.DAY_OF_MONTH) + " " + MONTHS[c.get(java.util.Calendar.MONTH)];
    }

    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
        Resources res = fetchResourceFile();
        FRIENDS[0].put("icon", res.getImage("chen.jpg"));
        FRIENDS[1].put("icon", res.getImage("shai.jpg"));
        FRIENDS[2].put("icon", res.getImage("ofir.jpg"));
        FRIENDS[3].put("icon", res.getImage("yaniv.jpg"));
        FRIENDS[4].put("icon", res.getImage("merav.jpg"));
        FRIENDS[5].put("icon", res.getImage("martin.jpg"));

        sunImage = res.getImage("sun.png");
        moonImage = res.getImage("moon.png");
    }

    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {
    }

    protected boolean initListModelTimeSlider(final List cmp) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.initListModelTimeSlider(cmp);
        Hashtable[] data = new Hashtable[672];
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new Date());
        String[] sevenDays = new String[7];
        int dow = cal.get(java.util.Calendar.DAY_OF_WEEK) - java.util.Calendar.SUNDAY;
        for(int iter = 0 ; iter < 7 ; iter++) {
            cal.setTime(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));
            sevenDays[iter] = DAYS[dow] + ", " + cal.get(java.util.Calendar.DAY_OF_MONTH) + " " + MONTHS[cal.get(java.util.Calendar.MONTH)];
            dow++;
            if(dow == DAYS.length) {
                dow = 0;
            }
        }
        Resources res = fetchResourceFile();
        Image longTick;
        Image shortTick = res.getImage("short_tick.png");

        // every little bit counts in a small screen
        if(Display.getInstance().getDeviceDensity() <= Display.DENSITY_LOW) {
            longTick = shortTick;
        } else {
            longTick = res.getImage("long_tick.png");
        }
        for(int iter = 0 ; iter < data.length ; iter++) {
            data[iter] = new Hashtable();
            data[iter].put("day", sevenDays[iter / 96]);
            int time = iter % 96 * 15;
            int minutes = time % 60;
            data[iter].put("time", twoDigits(time / 60) + ":" + twoDigits(minutes));
            if(minutes == 0) {
                data[iter].put("tick", longTick);
                data[iter].put("tickLabel", "" + (time / 60));
            } else {
                data[iter].put("tick", shortTick);
                if(time % 30 == 0) {
                    data[iter].put("tickLabel", "1/2");
                }
            }
        }
        final ListModel m = new DefaultListModel(data);
        m.setSelectedIndex(50);
        cmp.setModel(m);
        m.addSelectionListener(new SelectionListener() {
            public void selectionChanged(int i, int i1) {
                Hashtable h = (Hashtable)m.getItemAt(i1);
                if(i1 > -1) {
                    findTimeSliderPosition(cmp.getParent()).setText((String)h.get("day"));
                    findSliderTimeTab(cmp.getParent()).setText((String)h.get("time"));
                }
            }
        });
        return true;
    }

    private String twoDigits(int i) {
        if(i < 10) {
            return "0" + i;
        }
        return "" + i;
    }

    protected void beforeMainUI(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeMainUI(f);
        f.registerAnimated(timeUpdateAnimation);
        addAllSelectedFriends(f);
        if(Display.getInstance().getDeviceDensity() <= Display.DENSITY_LOW) {
                findRemoveModeButton(f).setText("");
                findAddEntriesButton(f).setText("");
                findExitButton(f).setText("");
                findSettingsButton(f).setText("");
        } else {
            if(removeMode) {
                findRemoveModeButton(f).setText("Finish");
            }
        }
    }

    private void addAllSelectedFriends(Form f) {
        for(int iter = 0 ; iter < FRIENDS.length ; iter++) {
            String s = (String)FRIENDS[iter].get("selected");
            if("true".equals(s)) {
                addHashtable(f, FRIENDS[iter]);
            }
        }
        for(int iter = 0 ; iter < TIMEZONE_ENTRIES.length ; iter++) {
            String s = (String)TIMEZONE_ENTRIES[iter].get("selected");
            if("true".equals(s)) {
                addHashtable(f, TIMEZONE_ENTRIES[iter]);
            }
        }
        updateTimes(f);
    }

    private void updateFriendTime(Container friend) {
        Hashtable h = (Hashtable)friend.getClientProperty("data");
        int zone = ((Integer)h.get("zone")).intValue();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis() + (TIMEZONE_OFFSETS[zone] * 60000)));
        String currently = " currently: ";
        if(Display.getInstance().getDeviceDensity() <= Display.DENSITY_LOW) {
            currently = "";
        }
        findCurrentTimeAndDate(friend).setText(TIMEZONES[zone] + currently + timeAndDate(cal));
        int selection = findTimeSlider(friend.getParent()).getSelectedIndex();
        
        // convert selection to minutes and add the offset
        selection = selection * 15 + TIMEZONE_OFFSETS[zone];
        int dayOffset = selection / (24 * 60);
        int hourOffset = selection % (24 * 60 ) / 60;
        int minuteOffset = selection % 60;
        long today = System.currentTimeMillis();
        today = today - (today % (24 * 60 * 60000)) + (dayOffset * 60000 * 60 * 24) + (hourOffset * 60000 * 60) + (minuteOffset * 60000);
        cal.setTime(new Date(today));
        int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = cal.get(java.util.Calendar.MINUTE);
        int dow = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int dom = cal.get(java.util.Calendar.DAY_OF_MONTH);
        int month = cal.get(java.util.Calendar.MONTH);
        Label dayOrNight = findDayOrNight(friend);

        if(hour > 18 || hour < 7) {
            dayOrNight.setIcon(moonImage);
        } else {
            dayOrNight.setIcon(sunImage);
        }
        Label dowLabel = findDayOfWeek(friend);
        dowLabel.setText(DAYS[dow - 1]);
        Label dateLabel = findDate(friend);
        dateLabel.setText(MONTHS[month] + " " + dom);
        Label timeOfDayLabel = findTimeOfDay(friend);
        timeOfDayLabel.setText(formatTime(hour, minute));
        
        // not enough room for the labels, clear room for them, we don't revalidate all the time since the performance would suffer
        if(timeOfDayLabel.shouldTickerStart() || dateLabel.shouldTickerStart() || dowLabel.shouldTickerStart()) {
            friend.revalidate();
        }
    }

    private void addHashtable(Form f, final Hashtable h) {
        addHashtable(f, h, -1);
    }

    private void addHashtable(Form f, final Hashtable h, int offset) {
        Resources res = fetchResourceFile();
        final Container friend = createContainer(res, "Friend");
        Button remove = findRemoveFriend(friend);
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                h.put("selected", "false");
                Label empty = new Label();
                empty.setPreferredSize(friend.getPreferredSize());
                Container parent = friend.getParent();
                parent.replaceAndWait(friend, empty, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
                parent.removeComponent(empty);
                parent.animateLayout(300);
            }
        });
        if(removeMode) {
            friend.setFocusable(false);
        } else {
            remove.setPreferredSize(new Dimension(0, 0));
        }
        friend.putClientProperty("data", h);
        Label friendName = findFriendName(friend);
        friendName.setText((String)h.get("title"));
        Image picture = (Image)h.get("icon");
        if(picture != null) {
            friendName.setIcon(picture);
        } else {
            friendName.setIcon(res.getImage("add_zone_world_icon.png"));
        }
        Container root = findFriendsRoot(f);
        if(offset < 0) {
            root.addComponent(friend);
        } else {
            root.addComponent(offset, friend);
        }
        updateFriendTime(friend);
    }

    protected boolean initListModelAddZoneList(List cmp) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.initListModelAddZoneList(cmp);
        if(friendsMode) {
            cmp.setModel(new DefaultListModel(FRIENDS));
        } else {
            cmp.setModel(new DefaultListModel(TIMEZONE_ENTRIES));
        }
        return true;
    }

    protected void onAddZone_WorldZoneAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onAddZone_WorldZoneAction(c, event);
        friendsMode = false;
        initListModelAddZoneList(findAddZoneList(c.getParent()));
    }

    protected void onAddZone_FriendZoneAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onAddZone_FriendZoneAction(c, event);
        friendsMode = true;
        initListModelAddZoneList(findAddZoneList(c.getParent()));
    }

    protected void onSettings_CivilianTimeCheckboxAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onSettings_CivilianTimeCheckboxAction(c, event);
        civilianTime = ((CheckBox)c).isSelected();
    }

    private String formatTime(int hours, int minutes) {
        if(civilianTime) {
            if(hours < 12) {
                if(hours == 0) {
                    return "12:" + twoDigits(minutes) + "AM";
                } else {
                    return twoDigits(hours) + ":" + twoDigits(minutes) + "AM";
                }
            } else {
                return hours + ":" + twoDigits(minutes) + "PM";
            }
        } else {
            return twoDigits(hours) + ":" + twoDigits(minutes);
        }
    }

    protected void exitSettings(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.exitSettings(f);

        // if we have more settings in the future, this is the place to update them
    }

    protected void exitAddZone(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.exitAddZone(f);
        
        // the upcoming form is the main form
        f = Display.getInstance().getCurrent();
        Container friendRoot = findFriendsRoot(f);
        for(int iter = 0 ; iter < friendRoot.getComponentCount() ; iter++) {
            Component c = friendRoot.getComponentAt(iter);
            Hashtable friendHash = (Hashtable)c.getClientProperty("data");
            if(!"true".equals(friendHash.get("selected"))) {
                friendRoot.removeComponent(c);
            }
        }
        for(int iter = 0 ; iter < TIMEZONE_ENTRIES.length ; iter++) {
            String s = (String)TIMEZONE_ENTRIES[iter].get("selected");
            if("true".equals(s) && !checkIfAddedAlready(friendRoot, TIMEZONE_ENTRIES[iter])) {
                addHashtable(f, TIMEZONE_ENTRIES[iter], 0);
            }
        }

        for(int iter = 0 ; iter < FRIENDS.length ; iter++) {
            String s = (String)FRIENDS[iter].get("selected");
            if("true".equals(s) && !checkIfAddedAlready(friendRoot, FRIENDS[iter])) {
                addHashtable(f, FRIENDS[iter], 0);
            }
        }
        updateTimes(f);
        friendRoot.animateLayout(1200);
    }

    private boolean checkIfAddedAlready(Container friendRoot, Object friendKey) {
        for(int iter = 0 ; iter < friendRoot.getComponentCount() ; iter++) {
            Component c = friendRoot.getComponentAt(iter);
            Hashtable friendHash = (Hashtable)c.getClientProperty("data");
            if(friendHash == friendKey) {
                return true;
            }
        }
        return false;
    }

    protected void beforeSettings(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeSettings(f);
        findCivilianTimeCheckbox(f).setSelected(civilianTime);
    }

    protected void onMainUI_RemoveModeButtonAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onMainUI_RemoveModeButtonAction(c, event);
        removeMode = !removeMode;
        Container friendRoot = findFriendsRoot(c.getParent());
        Dimension size = null;
        if(removeMode) {
            if(Display.getInstance().getDeviceDensity() > Display.DENSITY_LOW) {
                findRemoveModeButton(c.getParent()).setText("Finish");
            }
        } else {
            size = new Dimension(0, 0);
            if(Display.getInstance().getDeviceDensity() > Display.DENSITY_LOW) {
                findRemoveModeButton(c.getParent()).setText("Remove");
            }
        }
        for(int iter = 0 ; iter < friendRoot.getComponentCount() ; iter++) {
            Container currentFriend = (Container)friendRoot.getComponentAt(iter);
            currentFriend.setShouldCalcPreferredSize(true);
            currentFriend.setFocusable(!removeMode);
            findRemoveFriend(currentFriend).setPreferredSize(size);
            currentFriend.animateLayout(800);
        }
    }

    private Container splashTitle;
    private Label dummyTitle;
    protected void beforeSplash(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeSplash(f);

        splashTitle = findTitleArea(f);

        // create a "slide in" effect for the title
        dummyTitle = new Label();
        dummyTitle.setPreferredSize(splashTitle.getPreferredSize());
        f.replace(splashTitle, dummyTitle, null);
    }

    protected void postSplash(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.postSplash(f);

        f.replace(dummyTitle, splashTitle, CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL, true, 1000));
        splashTitle = null;
        dummyTitle = null;
    }
}
