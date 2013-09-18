/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.demos.maps;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import com.codename1.maps.Coord;
import com.codename1.maps.MapComponent;
import com.codename1.maps.MapComponent;
import com.codename1.maps.layers.ArrowLinesLayer;
import com.codename1.maps.layers.LinesLayer;
import com.codename1.maps.layers.PointLayer;
import com.codename1.maps.layers.PointsLayer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * This is a simple demo that demonstrates how to use the MapComponent and how
 * to show POI on a map using google location API's
 * Make sure to get a key from https://developers.google.com/maps/documentation/places/
 * to run the 'Find Resturants' sub demo
 * 
 * @author Chen
 */
public class MapsDemo {

    private Form main;
    private Coord lastLocation;

    public void init(Object context) {
        System.out.println("init");
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        main = new Form("Maps Demo");
        main.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Button b = new Button("Where am I?");
        main.addComponent(b);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                showMeOnMap();
            }
        });
        b = new Button("Find Resturants");
        main.addComponent(b);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                InfiniteProgress inf = new InfiniteProgress();
                Dialog progress = inf.showInifiniteBlocking();

                showResturantsOnMap(progress);
            }
        });

        main.show();
    }

    private void showMeOnMap() {
        Form map = new Form("Map");
        map.setLayout(new BorderLayout());
        map.setScrollable(false);
        final MapComponent mc = new MapComponent();

        putMeOnMap(mc);
        mc.zoomToLayers();

        map.addComponent(BorderLayout.CENTER, mc);
        map.addCommand(new MapsDemo.BackCommand());
        map.setBackCommand(new MapsDemo.BackCommand());
        map.show();

    }

    private void showResturantsOnMap(final Dialog progress) {
        try {
            final Form map = new Form("Map");
            map.setLayout(new BorderLayout());
            map.setScrollable(false);
            final MapComponent mc = new MapComponent();
            Location loc = LocationManager.getLocationManager().getCurrentLocation();
            putMeOnMap(mc);
            map.addComponent(BorderLayout.CENTER, mc);
            map.addCommand(new MapsDemo.BackCommand());
            map.setBackCommand(new MapsDemo.BackCommand());

            ConnectionRequest req = new ConnectionRequest() {
                private Coord firstPlace;

                protected void readResponse(InputStream input) throws IOException {
                    JSONParser p = new JSONParser();
                    Hashtable h = p.parse(new InputStreamReader(input));
                    // "status" : "REQUEST_DENIED"
                    String response = (String)h.get("status");
                    if(response.equals("REQUEST_DENIED")){
                        System.out.println("make sure to obtain a key from "
                                + "https://developers.google.com/maps/documentation/places/");
                        progress.dispose();
                        Dialog.show("Info", "make sure to obtain an application key from "
                                + "google places api's"
                                , "Ok", null);
                        return;
                    }
                        
                    final Vector v = (Vector) h.get("results");

                    Image im = Image.createImage("/red_pin.png");
                    PointsLayer pl = new PointsLayer();
                    pl.setPointIcon(im);
                    pl.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            PointLayer p = (PointLayer) evt.getSource();
                            System.out.println("pressed " + p);

                            Dialog.show("Details", "" + p.getName() + "\n" + p.getLatitude() + "|" + p.getLongitude(), "Ok", null);
                        }
                    });

                    for (int i = 0; i < v.size(); i++) {
                        Hashtable entry = (Hashtable) v.elementAt(i);
                        Hashtable geo = (Hashtable) entry.get("geometry");
                        Hashtable loc = (Hashtable) geo.get("location");
                        Double lat = (Double) loc.get("lat");
                        Double lng = (Double) loc.get("lng");
                        PointLayer point = new PointLayer(new Coord(lat.doubleValue(), lng.doubleValue()),
                                (String) entry.get("name"), null);
                        pl.addPoint(point);
                        if (i == 0) {
                            firstPlace = new Coord(lat.doubleValue(), lng.doubleValue());
                            map.addComponent(BorderLayout.SOUTH, new Button(new Command("Go to " + point.getName() + " ?"){

                                public void actionPerformed(ActionEvent evt) {
                                    try {
                                        ArrayList l = decodePoly(getDirections(lastLocation, firstPlace));
                                        Coord p0, p1 = lastLocation;
                                        LinesLayer line = new LinesLayer();
                                        Iterator points = l.iterator();
                                        while (points.hasNext()) {
                                            p0 = p1;
                                            p1 = (Coord) points.next();
                                            line.addLineSegment(new Coord[]{p0, p1});
                                        }
                                        mc.addLayer(line);
                                    } catch (Exception ex) {
                                        System.out.println("Failed to get directions.");
                                    }
                                }
                                
                            }));
                        }
                    }
                    progress.dispose();
                    
                    mc.addLayer(pl);
                    map.show();
                    mc.zoomToLayers();

                }
            };
            req.setUrl("https://maps.googleapis.com/maps/api/place/search/json");
            req.setPost(false);
            req.addArgument("location", "" + loc.getLatitude() + "," + loc.getLongtitude());
            req.addArgument("radius", "500");
            req.addArgument("types", "food");
            req.addArgument("sensor", "false");
            
            //get your own key from https://developers.google.com/maps/documentation/places/
            //and replace it here.
            String key = "AddYourOwnKeyHere";
                        
            req.addArgument("key", key);

            NetworkManager.getInstance().addToQueue(req);
        } //https://maps.googleapis.com/maps/api/place/search/json?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor=false&key=AIzaSyDdCsmiS9AT6MfFEWi_Vy87LJ0B2khZJME
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void putMeOnMap(MapComponent map) {
        try {
            Location loc = LocationManager.getLocationManager().getCurrentLocation();
            lastLocation = new Coord(loc.getLatitude(), loc.getLongtitude());
            Image i = Image.createImage("/blue_pin.png");
            PointsLayer pl = new PointsLayer();
            pl.setPointIcon(i);
            PointLayer p = new PointLayer(lastLocation, "You Are Here", i);
            p.setDisplayName(true);
            pl.addPoint(p);
            pl.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    PointLayer p = (PointLayer) evt.getSource();
                    System.out.println("pressed " + p);

                    Dialog.show("Details", "You Are Here" + "\n" + p.getLatitude() + "|" + p.getLongitude(), "Ok", null);
                }
            });
            map.addLayer(pl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void stop() {
        System.out.println("stopped");
    }

    public void destroy() {
        System.out.println("destroyed");

    }

    class BackCommand extends Command {

        public BackCommand() {
            super("Back");
        }

        public void actionPerformed(ActionEvent evt) {
            main.showBack();
        }
    }
    
    
        private ArrayList decodePoly(String encoded) {
        ArrayList poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
 
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
 
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
 
            Coord p = new Coord(lat/1E5, lng/1E5);
            poly.add(p);
        }
 
        return poly;
    }
        
    private String getDirections(Coord origin, Coord destination) throws IOException {
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://maps.googleapis.com/maps/api/directions/json");
        req.setUserAgent("Opera/8.0 (Windows NT 5.1; U; en)");
        req.setPost(false);
        req.addArgument("origin", "" + origin.getLatitude() + " " + origin.getLongitude());
        req.addArgument("destination", "" + destination.getLatitude() + " " + destination.getLongitude());
        req.addArgument("mode", "walking");
        req.addArgument("sensor", "false");
        NetworkManager.getInstance().addToQueueAndWait(req);
        JSONParser p = new JSONParser();
        Hashtable h = p.parse(new InputStreamReader(new ByteArrayInputStream(req.getResponseData())));
        System.out.println(h.toString());
        return ((Hashtable)((Hashtable)((Vector) h.get("routes")).firstElement()).get("overview_polyline")).get("points").toString();
    }
}

