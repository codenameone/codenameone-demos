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

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.SeriesSelection;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.transitions.XYMultiSeriesTransition;
import com.codename1.charts.views.ClickableArea;
import com.codename1.charts.views.LineChart;
import com.codename1.demos.geoviz.PopulationData.RegionData;
import com.codename1.io.Log;

import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author shannah
 */
public class DensityChart extends Container {

    ChartComponent chart;
    PopulationData data;
    XYMultipleSeriesDataset dataSet;
    XYMultipleSeriesRenderer renderer;
    private String currentRegion;
    private int[] colors;
    
    private static final int VIEW_MODE_POPULATION=0;
    private static final int VIEW_MODE_DENSITY=1;
    private int viewMode = VIEW_MODE_POPULATION;
    
    
    private static final int POPULATION=0;
    private static final int DENSITY=1;
    private static final int RANK=2;
    
    public DensityChart(PopulationData data) {
        
        
        colors = new int[]{0x660000, 0x006600};
        dataSet = new XYMultipleSeriesDataset();
        XYSeries[] series = new XYSeries[]{
            new XYSeries("Population")
        };
        
        
        renderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer[] renderers = new XYSeriesRenderer[]{
            new XYSeriesRenderer(),
        };
        
        for (XYSeriesRenderer r : renderers){
            r.setLineWidth(2);
            r.setColor(0xffff0000);
            r.setFillBelowLine(true);
            r.setFillBelowLineColor(0x33ff0000);
            renderer.addSeriesRenderer(r);
            
        }
        renderer.setBackgroundColor(0xff000000);
        renderer.setInitialRange(new double[]{1900, 2010, 0, 800000});
        renderer.setXAxisMin(1910);
        renderer.setMarginsColor(0xffffff);
        renderer.setAxesColor(0x0);
        renderer.setXLabelsColor(0x0);
        renderer.setYLabelsColor(0, 0x0);
        renderer.setShowGridX(true);
        renderer.setGridColor(0xcccccc);
        
        
        dataSet.addAllSeries(Arrays.asList(series));
        
        
        LineChart lc = new LineChart(dataSet, renderer){

            @Override
            protected ClickableArea[] clickableAreasForPoints(List<Float> points, List<Double> values, float yAxisValue, int seriesIndex, int startIndex) {
                ClickableArea[] hotspots = super.clickableAreasForPoints(points, values, yAxisValue, seriesIndex, startIndex); 
                for (ClickableArea area : hotspots){
                    area.getRect().setY(0);
                    area.getRect().setHeight(10000);
                }
                return hotspots;
            }
            
            
            
        };
        
        chart = new ChartComponent(lc){

            @Override
            protected void seriesPressed(SeriesSelection sel) {
                super.seriesPressed(sel);
                yearPressed((int)sel.getXValue());
            }

            
            
            
            
        };
        
        this.setLayout(new BorderLayout());
        this.addComponent(BorderLayout.CENTER, chart);
        this.data = data;
        
        
    
        
        
    }

    
    
    
    
    protected void yearPressed(int year){
        Log.p("Year pressed "+year);
    }
    
    public void setRegion(String region){
        this.currentRegion = region;
        renderer.setChartTitle(region);
        renderer.getSeriesRendererAt(0).setColor(colors[viewMode]);
        ((XYSeriesRenderer)renderer.getSeriesRendererAt(0)).setFillBelowLineColor(0x33000000 | colors[viewMode]);
        if (getComponentForm() != null){
            
            XYMultiSeriesTransition t = new XYMultiSeriesTransition(chart, dataSet);
            RegionData[] rdArr = data.getRegionData(region);
            for (RegionData rd : rdArr){
                switch (viewMode){
                    case VIEW_MODE_POPULATION:
                        dataSet.getSeriesAt(0).setTitle("Population");
                        t.getBuffer().getSeriesAt(POPULATION).add(rd.year, rd.pop);
                        break;
                    case VIEW_MODE_DENSITY:
                        dataSet.getSeriesAt(0).setTitle("Density (People per square mile)");
                        t.getBuffer().getSeriesAt(POPULATION).add(rd.year, rd.density);
                        break;
                    default:
                        throw new RuntimeException("Illegal view mode.");
                }
                
                //t.getBuffer().getSeriesAt(DENSITY).add(rd.year, rd.density);
                //t.getBuffer().getSeriesAt(RANK).add(rd.year, rd.rank);
            }
            t.animateChart();
        } else {
            RegionData[] rdArr = data.getRegionData(region);
            renderer.getSeriesRendererAt(0).setColor(colors[viewMode]);
            for (RegionData rd : rdArr){
                
                switch (viewMode){
                    
                    case VIEW_MODE_POPULATION:
                        dataSet.getSeriesAt(POPULATION).add(rd.year, rd.pop);
                        dataSet.getSeriesAt(0).setTitle("Population");
                        
                        break;
                    case VIEW_MODE_DENSITY:
                        dataSet.getSeriesAt(0).setTitle("Density (People per square mile)");
                        dataSet.getSeriesAt(POPULATION).add(rd.year, rd.density);
                        break;
                    default:
                        throw new RuntimeException("Illegal view mode");
                }
                
            }
            Log.p("Finished adding pop data");
        }
        
    }
    
    public void showPopulation(){
        showPopulation(currentRegion);
    }
    
    public void showPopulation(String region){
        this.viewMode = VIEW_MODE_POPULATION;
        setRegion(region);
    }
    public void showDensity(String region){
        this.viewMode = VIEW_MODE_DENSITY;
        setRegion(region);
    }
    
    public void showDensity(){
        showDensity(currentRegion);
    }
    
}
