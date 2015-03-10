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

import com.codename1.maps.BoundingBox;
import com.codename1.maps.Coord;
import com.codename1.ui.geom.GeneralPath;
import com.codename1.ui.geom.Rectangle;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates a "Feature" from a GEOJSON dataset.
 * http://geojson.org/geojson-spec.html#feature-objects
 * @author shannah
 */
public class Feature {
    Map properties = new HashMap();
    GeneralPath geometry;
    
    /**
     * Adds a general property to the feature.
     * @param name The property name
     * @param value The property value.
     */
    public void addProperty(String name, Object value){
        properties.put(name, value);
    }
    
    /**
     * Sets the geometry of the feature.  Shape coordinates should be expressed
     * in (lng,lat) pairs.
     * @param path The path to represent the geometry of this feature.
     */
    public void setGeometry(GeneralPath path){
        this.geometry = path;
    }
    
    /**
     * Gets the geometry of this features.  Shape coordinates are expressed in
     * (lng,lat) pairs.
     * @return 
     */
    public GeneralPath getGeometry(){
        return geometry;
    }
    
    /**
     * Gets the properties associated with this feature.
     * @return A Map of properties associated with this feature.
     */
    public Map getProperties(){
        return Collections.unmodifiableMap(properties);
    }
    
    /**
     * Calculates a bounding box for this feature.
     * @return A bounding box for this feature.
     */
    public BoundingBox calculateBounds(){
        Rectangle bds = getGeometry().getBounds();
        return new BoundingBox(new Coord(bds.getY()+bds.getHeight(), bds.getX()), new Coord(bds.getY(), bds.getX()+bds.getWidth()));
    }
}
