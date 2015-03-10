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

import com.codename1.maps.Coord;
import com.codename1.maps.Projection;
import com.codename1.ui.geom.PathIterator;

/**
 * A PathIterator that can be used to apply a given projection to the points of
 * a shape.  This iterator converts points from (lng,lat) pairs into (x,y) coordinates
 * of the projection.
 * @author shannah
 */
public class FromWGS84PathIterator implements PathIterator {
    
    private final PathIterator source;
    private final Projection projection;
    
    public FromWGS84PathIterator(PathIterator source, Projection projection){
        this.source = source;
        this.projection = projection;
    }
    
    public int getWindingRule() {
        return source.getWindingRule();
    }

    public boolean isDone() {
        return source.isDone();
    }

    public void next() {
        source.next();
    }

    public int currentSegment(float[] coords) {
        int out = source.currentSegment(coords);
        //Log.p("Before transform "+Arrays.toString(coords));
        Coord tmp = projection.fromWGS84(new Coord(coords[1], coords[0]));
        coords[0] = (float)tmp.getLongitude();
        coords[1] = (float)tmp.getLatitude();
        //Log.p("After transform "+Arrays.toString(coords));
        return out;
    }

    public int currentSegment(double[] coords) {
        int out = source.currentSegment(coords);
        Coord tmp = projection.fromWGS84(new Coord(coords[1], coords[0]));
        coords[0] = tmp.getLongitude();
        coords[1] = tmp.getLatitude();
        return out;
    }
    
}
