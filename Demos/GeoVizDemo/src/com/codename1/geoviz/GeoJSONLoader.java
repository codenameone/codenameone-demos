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
package com.codename1.geoviz;

import com.codename1.io.JSONParser;
import com.codename1.ui.geom.GeneralPath;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Loads GeoJSON data from an InputStream and outputs a {@link FeatureCollection} object.
 * @author shannah
 */
public class GeoJSONLoader {
    public FeatureCollection loadJSON(InputStream input, String encoding) throws IOException {
        JSONParser parser = new JSONParser();
        Map<String,Object> data = parser.parseJSON(new InputStreamReader(input, encoding));
        return createFeatureCollection(data);
    }
    
    private FeatureCollection createFeatureCollection(Map data){
        FeatureCollection collection = new FeatureCollection();
        List<Map> features = (List<Map>)data.get("features");
        for (Map featureMap : features){
            Feature feature = createFeature(featureMap);
            collection.addFeature(feature);
        }
        return collection;
    }
    
    private Feature createFeature(Map data){
        Feature feature = new Feature();
        Map<String,Object> properties = (Map)data.get("properties");
        if (properties != null){
            for (String key : properties.keySet()){
                feature.addProperty(key, properties.get(key));
            }
        }
        
        Map<String,Object> geometry = (Map)data.get("geometry");
        if (geometry != null){
            GeneralPath path = new GeneralPath();
            
            String type = (String)geometry.get("type");
            readCoordinates(type, path, (List)geometry.get("coordinates"));
            
            feature.setGeometry(path);
        }
    
    
        return feature;
    }
    
    private void readCoordinates(String type, GeneralPath path, List coordinates){
        if ("Polygon".equals(type)){
            readPolygon(path, coordinates);
        } else if ("MultiPolygon".equals(type)){
            readMultiPolygon(path, coordinates);
        } else if ("LineString".equals(type)){
            readLineString(path, coordinates);
        }
    }
    
    private void readPolygon(GeneralPath path, List coordinates){
        for (Object ring : coordinates){
            readLinearRing(path, (List)ring);
        }
    }
    
    private void readMultiPolygon(GeneralPath path, List coordinates){
        for (Object poly : coordinates){
            readPolygon(path, (List)poly);
        }
    }
    
    private void readLineString(GeneralPath path, List coordinates){
        boolean first = true;
        for (Object coord : coordinates){
            List<Double> c = (List<Double>)coord;
            if (first){
                path.moveTo(c.get(0), c.get(1));
                first = false;
            } else {
                path.lineTo(c.get(0), c.get(1));
            }
        }
    }
    private void readLinearRing(GeneralPath path, List coordinates){
        readLineString(path, coordinates);
        path.closePath();
    }
}
