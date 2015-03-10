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
 */package com.codename1.demos.geoviz;

import com.codename1.io.CSVParser;
import com.codename1.io.Log;
import com.codename1.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shannah
 */
public class PopulationData {
    public class RegionData {
        String region;
        int year;
        int pop;
        double density;
        int rank;
    }
    
    Map<String,RegionData[]> data = new HashMap<String,RegionData[]>();
    
    public void load(InputStream is) throws IOException {
        CSVParser parser = new CSVParser();
        String[][] pdata = parser.parse(is);
        String[] headings = null;
        int len=0;
        List<RegionData> tempRegionData = new ArrayList<RegionData>();
        Map<String,RegionData> tempYearRegionData = new HashMap<String,RegionData>();
        for (String[] row : pdata){
            if (headings == null){
                headings = row;
                len = headings.length;
            } else {
                Log.p("Processing row "+Arrays.toString(row));
                if ( row.length == 0){
                    continue;
                }
                tempRegionData.clear();
                tempYearRegionData.clear();
                String region = row[0];
                
                
                for (int i=1; i<len; i++){
                    RegionData d = null;
                    
                    List<String> parts = StringUtil.tokenize(headings[i], '_');
                    
                    int year = Integer.parseInt(parts.get(0));
                    
                    if (tempYearRegionData.containsKey(""+year)){
                        d = tempYearRegionData.get(""+year);
                    } else {
                        d = new RegionData();
                        d.region = region;
                        d.year = year;
                        tempYearRegionData.put(""+year, d);
                    }
                    
                    String type = parts.get(1);
                    row[i] = StringUtil.replaceAll(row[i], ",", "");
                    if (!"".equals(row[i])){
                        if ("POPULATION".equals(type)){
                            d.pop = Integer.parseInt(row[i]);
                        } else if ("DENSITY".equals(type)){
                            d.density = Double.parseDouble(row[i]);
                        } else if ("RANK".equals(type)){
                            d.rank = Integer.parseInt(row[i]);
                        } else {
                            throw new RuntimeException("Unexpected col heading.  Expecting POPULATION, DENSITY, or RANK, but found "+type);
                        }
                        
                    }
                    if (!tempRegionData.contains(d)){
                        tempRegionData.add(d);
                    }
                    
                }
                data.put(region, tempRegionData.toArray(new RegionData[tempRegionData.size()]));
            }
        }
    }
    
    public RegionData[] getRegionData(String region){
        return data.get(region);
    }
    
    public String[] getRegions(){
        return data.keySet().toArray(new String[data.size()]);
    }
    
    public double getMaxDensity(){
        double max = 0;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.density>max && !"United States".equals(d.region)){
                    max = d.density;
                }
            }
        }
        return max;
    }
    
    public double getMinDensity(){
        double min = Double.POSITIVE_INFINITY;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.density<min && !"United States".equals(d.region)){
                    min = d.density;
                }
            }
        }
        return min;
    }
    
    public int getMinPopulation(){
        int min = (int)Float.POSITIVE_INFINITY;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.pop<min && !"United States".equals(d.region)){
                    min = d.pop;
                }
            }
        }
        return min;
    }
    
    public int getMaxPopulation(){
        int max = 0;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.pop>max && !"United States".equals(d.region)){
                    max = d.pop;
                }
            }
        }
        return max;
    }
    
    public double getMaxDensity(int year){
        double max = 0;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.year==year && d.density>max && !"United States".equals(d.region)){
                    max = d.density;
                }
            }
        }
        return max;
    }
    
    public double getMinDensity(int year){
        double min = Double.POSITIVE_INFINITY;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.year==year && d.density<min && !"United States".equals(d.region)){
                    min = d.density;
                }
            }
        }
        return min;
    }
    
    public int getMinPopulation(int year){
        int min = (int)Float.POSITIVE_INFINITY;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.year==year && d.pop<min && !"United States".equals(d.region)){
                    min = d.pop;
                }
            }
        }
        return min;
    }
    
    public int getMaxPopulation(int year){
        int max = 0;
        for (RegionData[] dataArr : data.values()){
            for (RegionData d : dataArr){
                if (d.year==year && d.pop>max && !"United States".equals(d.region)){
                    max = d.pop;
                }
            }
        }
        return max;
    }
}
