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
package com.codename1.demos.geoviz;


import com.codename1.geoviz.GeoVizComponent;
import com.codename1.geoviz.FeatureCollection;
import com.codename1.geoviz.FeaturePainter;
import com.codename1.geoviz.GeoJSONLoader;
import com.codename1.geoviz.Feature;
import com.codename1.demos.geoviz.PopulationData.RegionData;

import com.codename1.io.Log;
import com.codename1.maps.Coord;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.RadioButton;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.GeneralPath;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

public class GeoVizDemo {

    private Form current;
    private Feature selectedFeature;
    private int currentYear;
    private static final int MODE_POPULATION=0;
    private static final int MODE_DENSITY=1;
    private PopulationData popData;
    private double minDensity;
    private double maxDensity;
    private double minPopulation;
    private double maxPopulation;
    
    private int mode = MODE_POPULATION;
    private DensityChart densityChart;
    GeoVizComponent geoVizComponent;
    

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch(IOException e){
            e.printStackTrace();
        }
       
    }
    
    public void start() throws Throwable{
        if(current != null){
            current.show();
            return;
        }
        
        //Load all of the population data from CSV file
        popData = new PopulationData();
        popData.load(Display.getInstance().getResourceAsStream(null, "/pop_density.csv"));
        
        // Record min and max values in data for generating ranges of colors later
        minDensity = popData.getMinDensity();
        maxDensity = popData.getMaxDensity();
        minPopulation = popData.getMinPopulation();
        maxPopulation = popData.getMaxPopulation();
        
        
        // Density Chart
        //--------------
        // A chart to show the population and density of a selected state
        // at various points in the last century.
        densityChart = new DensityChart(popData){

            /**
             * Implement the yearPressed callback so that we can update the 
             * geoViz component when a year is selected on the density chart.
             * @param year 
             */
            @Override
            protected void yearPressed(int year) {
                setCurrentYear(year);
                geoVizComponent.repaint();
            }
            
        };
        
        
        
        
        densityChart.setRegion("Alaska");
        
        
        // Load the GEO JSON Data from the accompanying JSON file.
        // This contains all of the US state contours.
        GeoJSONLoader loader = new GeoJSONLoader();
        
        FeatureCollection coll = loader.loadJSON(Display.getInstance().getResourceAsStream(null, "/us-states.json"), "UTF-8");
        GeoVizComponent comp = new GeoVizComponent(coll){

            /**
             * Implement the featurePressed callback so that we can update the
             * density chart when a state is selected.
             * @param f The feature that was pressed
             * @param coord The coordinate that was pressed.
             */
            @Override
            public void featurePressed(Feature f, Coord coord) {
                super.featurePressed(f, coord);
                selectedFeature = f;
                densityChart.setRegion((String)f.getProperties().get("name"));
            }
            
        };
        
        // Set the center of the map to somewhere in the mid-USA
        comp.setCenter(new Coord(49, -109));
        
        // Zoom in a bit
        comp.setScale(2.0);
        
        
        // Set the preferred size of the respective
        if (Display.getInstance().isPortrait()){
            comp.setPreferredSize(new Dimension(
                    Display.getInstance().getDisplayWidth(),
                    Display.getInstance().getDisplayHeight()/2
            ));
        } else {
            comp.setPreferredSize(new Dimension(
                    Display.getInstance().getDisplayWidth()/2,
                    Display.getInstance().getDisplayHeight()
            ));
        }
        
        geoVizComponent = comp;
        
        // Add a custom feature painter so that we can paint states different
        // colors depending on the data in our CSV file.
        comp.setFeaturePainter(new FeaturePainter(){

            /**
             * Callback to fill a feature (State).  We implement this
             * so that we can fill selected states with red and other states
             * a color based on the currently selected year.
             * @param g The graphics context
             * @param feature The feature to paint
             * @param path The shape that is to be filled.
             */
            @Override
            protected void fill(Graphics g, Feature feature, GeneralPath path) {
                
                int oldColor = this.getFillColor();
                int oldAlpha = this.getFillAlpha();
                if (feature == selectedFeature){
                    this.setFillColor(0xff0000);
                } else {
                    RegionData[] regionData = popData.getRegionData((String)feature.getProperties().get("name"));
                    if (currentYear > 0){
                        if (mode==MODE_POPULATION){
                            for (RegionData d : regionData){
                                if (d.year == currentYear){
                                    if (d.pop != 0){
                                        this.setFillColor(getColor(1.0, 0, 0, d.pop, minPopulation, maxPopulation));
                                        this.setFillAlpha(getAlpha(d.pop, minPopulation, maxPopulation));
                                    }
                                    break;
                                }
                            }
                        } else if (mode==MODE_DENSITY){
                            for (RegionData d : regionData){
                                if (d.year == currentYear){
                                    if (d.density != 0){
                                        this.setFillColor(getColor(0, 1.0, 0, d.density, minDensity, maxDensity));
                                        this.setFillAlpha(getAlpha(d.density, minDensity, maxDensity));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                super.fill(g, feature, path);
                this.setFillColor(oldColor);
                this.setFillAlpha(oldAlpha);
            }
            
        });
                
        final Form hi = new Form("Geoviz Demo");
        current = hi;
        hi.setLayout(new BorderLayout());
        hi.addComponent(BorderLayout.CENTER, comp);
        hi.addComponent(BorderLayout.SOUTH, densityChart);
        
        
        // Add a radio button to select whether to view population density in
        // the chart or just number of people.
        RadioButton densityBtn = new RadioButton("Density");
        densityBtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                if (mode != MODE_DENSITY){
                    mode = MODE_DENSITY;
                    updateViews();
                }
            }
            
        });
        
        RadioButton popBtn = new RadioButton("Population");
        popBtn.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                if (mode != MODE_POPULATION){
                    mode = MODE_POPULATION;
                    updateViews();
                }
            }
            
        });
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(popBtn);
        btnGroup.add(densityBtn);
        Container north = new Container();
        north.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        north.addComponent(densityBtn);
        north.addComponent(popBtn);
        hi.addComponent(BorderLayout.NORTH, north);
        updateComponentSizes();
        
        hi.show();
        
        // Add an orientation listener to the form so that we can reformat
        // the map and chart to fit nicely.
        hi.addOrientationListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                updateComponentSizes();
                hi.animateLayout(200);
            }
            
        });
    }

    /**
     * Recalculate the map and chart preferred sizes when the viewport is changed.
     */
    private void updateComponentSizes(){
        current.removeComponent(geoVizComponent);
        current.removeComponent(densityChart);
        if (Display.getInstance().isPortrait()){
            Log.p("In portrait");
            current.addComponent(BorderLayout.CENTER, geoVizComponent);
            current.addComponent(BorderLayout.SOUTH, densityChart);
            geoVizComponent.setPreferredSize(new Dimension(
                    Display.getInstance().getDisplayWidth(),
                    Display.getInstance().getDisplayHeight()/2
            ));
            densityChart.setPreferredSize(new Dimension(
                    Display.getInstance().getDisplayWidth(),
                    Display.getInstance().getDisplayHeight()/2
            ));
        } else {
            current.addComponent(BorderLayout.EAST, geoVizComponent);
            current.addComponent(BorderLayout.CENTER, densityChart);
            
            geoVizComponent.setPreferredSize(new Dimension(
                    Display.getInstance().getDisplayWidth()/2,
                    Display.getInstance().getDisplayHeight()
            ));
            densityChart.setPreferredSize(new Dimension(
                    Display.getInstance().getDisplayWidth()/2,
                    Display.getInstance().getDisplayHeight()
            ));
        }
        
        
        
    }
    
    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    
    private void updateViews(){
        if (selectedFeature != null){
            switch (mode){
                case MODE_POPULATION:
                    densityChart.showPopulation((String)selectedFeature.getProperties().get("name"));
                    break;
                case MODE_DENSITY:
                    densityChart.showDensity((String)selectedFeature.getProperties().get("name"));
                    break;
            }
        }
    }
    
    /**
     * Generates a color in a spectrum, using the base rgb values as a mask,
     * and min and max values as the range from "black" to "rgb".
     * @param r
     * @param g
     * @param b
     * @param val
     * @param min
     * @param max
     * @return 
     */
    private static int getColor(double r, double g, double b, double val, double min, double max){
        //min = MathUtil.log(min);
        //max = MathUtil.log(max);
        //val = MathUtil.log(val);
        //Log.p("getColor "+r+","+g+","+b+" "+val+" min:"+min+" max:"+max);
        double relativeVal = (Math.min(val,max)-min)/(max-min);
        
        r = r * (1.0-relativeVal);
        g = g * (1.0-relativeVal);
        b = b * (1.0-relativeVal);
        
        int rgb = (int)(255.0*r);
        rgb = (rgb << 8) | (int)(255.0*g);
        rgb = (rgb << 8) | (int)(255.0*b);
        //Log.p("Relative: "+relativeVal+" r:"+r+" g:"+g+" b:"+b+" rgb:"+rgb);
        return rgb;
        
    }
    
    
    
    private static int getAlpha(double val, double min, double max){
        //min = MathUtil.log(min);
        //max = MathUtil.log(max);
        //val = MathUtil.log(val);
        double relativeVal = (Math.min(val,max)-min)/(max-min);
        int out= (int)(255*relativeVal);
        //Log.p("Alpha is "+out);
        return out;
        //return 0xff;
    }
    
    private void setCurrentYear(int year){
        this.currentYear = year;
        minDensity = popData.getMinDensity(year);
        maxDensity = popData.getMaxDensity(year);
        minPopulation = popData.getMinPopulation(year);
        maxPopulation = popData.getMaxPopulation(year);
    }
}
