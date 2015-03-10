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

import com.codename1.maps.Projection;
import com.codename1.ui.Graphics;
import com.codename1.ui.Stroke;
import com.codename1.ui.geom.GeneralPath;

/**
 * A base class that encapsulates the painting for features of a GeoVizComponent.
 * Subclasses can override how features are filled and stroked, then register
 * themselves as the painter for a GeoVizComponent.
 * @author shannah
 */
public class FeaturePainter  {

    
    private Stroke stroke;
    private int strokeColor=0x0;
    private int fillColor=0xffffff;
    private int strokeAlpha=255;
    private int fillAlpha=255;
    private Projection projection;
    
    GeneralPath projectedPath = null;

    public FeaturePainter() {
        stroke = new Stroke(1, Stroke.CAP_BUTT, Stroke.JOIN_BEVEL, 1);
    }
    
    
    /**
     * Paints the specified feature onto the given graphics context.  This will
     * call fill() and stroke() internally.
     * @param g The graphics context to paint onto
     * @param feature The feature to be painted.
     */
    public final void paint(Graphics g, Feature feature){
        if (feature.getGeometry() == null){
            return;
        }
        if (projectedPath == null){
            projectedPath = new GeneralPath();
        }
        projectedPath.reset();
        projectedPath.append(new FromWGS84PathIterator(feature.getGeometry().getPathIterator(), projection), true);
        fill(g, feature);
        stroke(g, feature);
        
    }
    
    /**
     * Fills the specified feature on the graphics context
     * @param g
     * @param feature 
     */
    public final void fill(Graphics g, Feature feature){
        fill(g, feature, projectedPath);
        
    }
    
    /**
     * Strokes the outline for a specified feature in the given graphics context.
     * @param g The graphics context to stroke onto.
     * @param feature The feature to be painted.
     */
    public final void stroke(Graphics g, Feature feature){
        stroke(g, feature, projectedPath);
    }
    
    /**
     * Strokes the specified feature outline on the provided graphics context.  This
     * method also receives a {@link GeneralPath} object that has been pre-transformed
     * using applied projections so that you can paint directly onto the graphics
     * context using g.drawShape().  That said, it may simply be preferable
     * to call super.stroke(g,feature,path) from the subclass after applying
     * the desired settings using setColor(), etc...
     * @param g The graphics context.
     * @param feature The feature to be painted.
     * @param path The precalculated path to stroke.
     */
    protected void stroke(Graphics g, Feature feature, GeneralPath path){
        g.setColor(strokeColor);
        g.setAlpha(strokeAlpha);
        g.drawShape(path, stroke);
    }
    
    
    /**
     * Fills the specified feature on the provided graphics context.  This
     * method also receives a {@link GeneralPath} object that has been pre-transformed
     * using applied projections so that you can paint directly onto the graphics
     * context using g.drawShape().  That said, it may simply be preferable
     * to call super.fill(g,feature,path) from the subclass after applying
     * the desired settings using setColor(), etc...
     * @param g The graphics context.
     * @param feature The feature to be painted.
     * @param path The precalculated path to stroke.
     */
    protected void fill(Graphics g, Feature feature, GeneralPath path){
        g.setColor(fillColor);
        g.setAlpha(fillAlpha);
        g.fillShape(path);
    }
    

    /**
     * Gets the stroke that is currently set to be used for stroking features.
     * @return the stroke
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Sets the stroke that should be used for stroking features.
     * @param stroke the stroke to set
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    /**
     * Gets the stroke color that is currently set for stroking features.
     * @return the strokeColor 
     */
    public int getStrokeColor() {
        return strokeColor;
    }

    /**
     * Sets the color to be used for stroking features.
     * @param strokeColor the strokeColor to set
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    /**
     * Gets the color to be used for filling features.
     * @return the fillColor
     */
    public int getFillColor() {
        return fillColor;
    }

    /**
     * Sets the color to be used for filling features.
     * @param fillColor the fillColor to set
     */
    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Gets the alpha (0-255) transparency to use for stroking features.
     * @return the strokeAlpha
     */
    public int getStrokeAlpha() {
        return strokeAlpha;
    }

    /**
     * Sets the alpha (0-255) transparency to use for stroking features.
     * @param strokeAlpha the strokeAlpha to set
     */
    public void setStrokeAlpha(int strokeAlpha) {
        this.strokeAlpha = strokeAlpha;
    }

    /**
     * Gets the alpha (0-255) transparency to use for filling feature shapes.
     * @return the fillAlpha
     */
    public int getFillAlpha() {
        return fillAlpha;
    }

    /**
     * Sets the alpha (0-255) transparency to use for filling feature shapes.
     * @param fillAlpha the fillAlpha to set
     */
    public void setFillAlpha(int fillAlpha) {
        this.fillAlpha = fillAlpha;
    }

    /**
     * Gets the projection that should be applied to features.
     * @return the projection
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * Sets the projection that should be applied to features
     * @param projection the projection to set
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }
    
    
    
}
