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
import com.codename1.ui.geom.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Represents a FeatureCollection from a GEOJSON data set
 * http://geojson.org/geojson-spec.html#feature-collection-objects
 * @author shannah
 */
public class FeatureCollection {
    private List<Feature> features=new ArrayList<Feature>();
    
    /**
     * Adds a feature to this feature collection.
     * @param feature 
     */
    public void addFeature(Feature feature){
        features.add(feature);
    }
    
    /**
     * Gets a list of features in this collection.
     * @return 
     */
    public List<Feature> getFeatures(){
        return Collections.unmodifiableList(features);
    }
    
    /**
     * Calculates a bounding box for this feature collection.
     * @return 
     */
    public BoundingBox calculateBounds(){
        Rectangle bds = null;
        for (Feature f : features){
            Rectangle r = f.getGeometry().getBounds();
            if (bds == null){
                bds = new Rectangle(r);
            } else {
                if (r.getX()<bds.getX()){
                    bds.setWidth(bds.getX()+bds.getWidth()-r.getX());
                    bds.setX(r.getX());
                }
                if (r.getY()<bds.getY()){
                    bds.setHeight(bds.getY()+bds.getHeight()-r.getY());
                    bds.setY(r.getY()); 
                }
                if (r.getX()+r.getWidth()>bds.getX()+bds.getWidth()){
                    bds.setWidth(r.getX()+r.getWidth()-bds.getX());
                }
                if (r.getY()+r.getHeight()>bds.getY()+bds.getHeight()){
                    bds.setHeight(r.getY()+r.getHeight()-bds.getY());
                }
                
            }
        }
        if (bds == null){
            bds = new Rectangle(0,0,0,0);
        }
        //Log.p("Bounds calculated "+bds);
        return new BoundingBox(new Coord(bds.getY()+bds.getHeight(), bds.getX()), new Coord(bds.getY(), bds.getX()+bds.getWidth()));
    }
}
