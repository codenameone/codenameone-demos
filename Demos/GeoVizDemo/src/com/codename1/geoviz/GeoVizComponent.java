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

import com.codename1.io.Log;
import com.codename1.maps.BoundingBox;
import com.codename1.maps.Coord;
import com.codename1.maps.Mercator;
import com.codename1.maps.Projection;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Stroke;
import com.codename1.ui.animations.Animation;
import com.codename1.ui.animations.Motion;
import com.codename1.ui.geom.Dimension;
import java.util.Deque;
import java.util.LinkedList;

/**
 * A Component for displaying GeoJSON data.
 * @author shannah
 */
public class GeoVizComponent extends Component {
 
    /**
     * Encapsulates information about changes to the viewport.  This
     * enables the viewport to be animated.
     */
    private class ChangeBuffer {
        private Coord center;
        private double scale;
        ViewportAnimation anim;

    }
    
    /**
     * Flag to indicate whether changes to the view port should be buffered
     * so that they can be animated in via a transition.
     */
    private boolean bufferChanges=true;
    
    /**
     * Change queue for viewport changes.  Only used if {@link bufferChanges} 
     * is true.
     */
    private Deque<ChangeBuffer> changes;
    
    /**
     * The features that are to be displayed in this component. See
     * http://geojson.org/geojson-spec.html
     */
    private FeatureCollection featureCollection;
    
    /**
     * Painter to use for painting the features.  
     */
    private FeaturePainter featurePainter;
    
    /**
     * The registered projection to use.  This can be to an arbitrary space, 
     * as it will be ultimately passed through a ViewportProjection
     * to map it to the component space.
     */
    private Projection projection;
    
    /**
     * The projection from the projection's output space to the component
     * space.
     */
    private ViewportProjection viewPortProjection;
    
    /**
     * The current bounding box to be viewed.
     */
    private BoundingBox viewport;
    
    /**
     * Flag to indicate whether the viewport has already been initialized.
     * If it hasn't been initialized yet it will just initialize to the
     * bounds of the feature collection upon first painting.
     */
    private boolean viewportInitialized;
    
    /**
     * Scale factor for scaling the viewport.  1.0 is when the bounds 
     * of the feature collection are fit inside the component (with possible
     * letterboxing to retain aspect ratio).  Increasing the scale will zoom
     * in and decreasing it will zoom out.  Zooming is always performed about
     * the specified {@link center}
     */
    private double scale=1.0;
    
    /**
     * The center coordinate.  All zooms are performed about this point.
     */
    private Coord center;
    
    /**
     * Flag to indicate whether pinch-zoom is enabled.
     */
    private boolean pinchZoomEnabled=true;
    
    /**
     * An object to keep track of the status of a pinch-zoom operation.
     */
    private PinchZoomStatus pinchZoomStatus;
    
    
    /**
     * Flag to indicate whether panning is enabled.
     */
    private boolean panEnabled=true;
    
    /**
     * An object to keep track of the status of a pan operation.
     */
    private PanStatus panStatus;
    
    private class PinchZoomStatus {
        int cx;
        int cy;
        Coord cCoord;
        
        double delta0;
        double scale0;
        
    }
    
    private class PanStatus {
        /**
         * X/Y start positions relative to parent component (in component space)
         */
        int startX;
        int startY;
        /**
         * This will store the projected coordinate as projected
         * by {@link projection} (not {@link viewPortProjection}
         * since the latter will be changed through the course
         * of a Pan so comparisons will be unreliable.
         */
        Coord projectedStartCoord;
        
        
        /**
         * Scale factors to convert between the projection
         * and the component space.
         */
        double scaleFactorX;
        double scaleFactorY;
        
        
    }
    
    
    /**
     * Creates a component to display the provided feature collection.
     * @param collection 
     */
    public GeoVizComponent(FeatureCollection collection){
        
        featureCollection = collection;
        
        
        featurePainter = new FeaturePainter();
        
        featurePainter.setStroke(new Stroke(1, Stroke.CAP_BUTT, Stroke.JOIN_BEVEL, 1));
        featurePainter.setFillAlpha(0);
        featurePainter.setFillColor(0xffffff);
        featurePainter.setStrokeColor(0x0);
        projection = new Mercator();
        viewport = projection.extent();
        viewPortProjection = new ViewportProjection();
        
        
        featurePainter.setProjection(viewPortProjection);
        
    }
    

    @Override
    public void paint(Graphics g) {
        g.setAntiAliased(true);
        updateViewport();
        super.paint(g);
        
        for (Feature feature : featureCollection.getFeatures()){
            getFeaturePainter().paint(g, feature);
        }
        g.setAntiAliased(false);
        
    }

    @Override
    public void pointerDragged(int[] x, int[] y) {
        
        super.pointerDragged(x, y); 
        if ( pinchZoomEnabled && x.length > 1){
            if (pinchZoomStatus==null){
                pinchZoomStatus = new PinchZoomStatus();
                pinchZoomStatus.cx = (x[0]+x[1])/2-getParent().getAbsoluteX();
                pinchZoomStatus.cy = (y[0]+y[1])/2-getParent().getAbsoluteY();
                pinchZoomStatus.cCoord = viewPortProjection.toWGS84(new Coord(pinchZoomStatus.cy, pinchZoomStatus.cx, true));
                pinchZoomStatus.scale0 = this.scale;
                double dx = x[1]-x[0];
                double dy = y[1]-y[0];
                pinchZoomStatus.delta0 = Math.sqrt(dx*dx+dy*dy);
            } else {
                
                double dx = x[1]-x[0];
                double dy = y[1]-y[0];
                double delta1 = Math.sqrt(dx*dx+dy*dy);
                
                // Find new scale
                double scale = delta1/pinchZoomStatus.delta0;
                this.scale = pinchZoomStatus.scale0 * scale;
                
                // Find new center
                updateViewport(); // updates with new scale... but wrong center
                Coord projPinchCenter = viewPortProjection.fromWGS84(pinchZoomStatus.cCoord);
                int offX = pinchZoomStatus.cx - (int)projPinchCenter.getLongitude();
                int offY = pinchZoomStatus.cy - (int)projPinchCenter.getLatitude();
                
                center = viewPortProjection.toWGS84(new Coord(getY()+getHeight()/2-offY, getX()+getWidth()/2-offX, true));
                updateViewport();
                repaint();
                
            }
            
            
        } else if (panEnabled) {
            if (panStatus == null){
                panStatus = new PanStatus();
                panStatus.startX = x[0] - getParent().getAbsoluteX();
                panStatus.startY = y[0] - getParent().getAbsoluteY();
                if (center == null){
                    center = new Coord(0,0);
                }
                panStatus.projectedStartCoord = projection.fromWGS84(center);
                BoundingBox projectionBbox = projection.fromWGS84(viewport);
                panStatus.scaleFactorX = Math.abs((projectionBbox.getNorthEast().getLongitude()-projectionBbox.getSouthWest().getLongitude())/((double)getWidth()));
                panStatus.scaleFactorY = Math.abs((projectionBbox.getNorthEast().getLatitude()-projectionBbox.getSouthWest().getLatitude()))/((double)getHeight());
                
            } else {
                
                int offX = x[0]-getParent().getAbsoluteX()-panStatus.startX;
                int offY = y[0]-getParent().getAbsoluteY()-panStatus.startY;
                
                int scaledOffX = (int)(((double)offX)*panStatus.scaleFactorX);
                int scaledOffY = (int)(((double)offY)*panStatus.scaleFactorY);
                
                Coord newCenter = new Coord(panStatus.projectedStartCoord.getLatitude()+scaledOffY, panStatus.projectedStartCoord.getLongitude()-scaledOffX, true);
                this.center = projection.toWGS84(newCenter);
                
                this.updateViewport();
                
                
                
                
                
            }
        }
    }

    @Override
    public void pointerReleased(int x, int y) {
        if (pinchZoomStatus != null){
            pinchZoomStatus = null;
        } else if (panStatus != null){
            panStatus = null;
        } else {
            Coord c = new Coord(y-getParent().getY(), x-getParent().getX(), true);
            Coord c2 = viewPortProjection.toWGS84(c);
            Log.p("Coordinate released "+c2);
            for (Feature feature : featureCollection.getFeatures()){
                if (feature.getGeometry().contains((float)c2.getLongitude(), (float)c2.getLatitude())){
                    featurePressed(feature, c2);
                }
            }

            //this.setCenter(new Coord(c2));
            //this.animateViewport(200);
        }
        
        super.pointerReleased(x, y); 
    }

    
    
   
    
    /**
     * Called when a feature is pressed.  This can be overridden to handle 
     * such events.
     * @param f
     * @param coord 
     */
    public void featurePressed(Feature f, Coord coord){
        //Log.p("Feature pressed "+f.properties);
    }

    /**
     * @return the featurePainter
     */
    public FeaturePainter getFeaturePainter() {
        return featurePainter;
    }

    /**
     * @param featurePainter the featurePainter to set
     */
    public void setFeaturePainter(FeaturePainter featurePainter) {
        this.featurePainter = featurePainter;
        featurePainter.setProjection(viewPortProjection);
    }

    /**
     * Gets the current projection for this component.  This can project
     * to any arbitrary 2D space.  The component will apply its own
     * additional projection to ultimately project it to the component's space.
     * @return the projection
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * @param projection the projection to set
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
        
    }
    
   
    
    
    private void updateViewport(BoundingBox bbox){
        BoundingBox sourceBounds = getProjection().fromWGS84(bbox);
        
        double width = (sourceBounds.getNorthEast().getLongitude()-sourceBounds.getSouthWest().getLongitude());
        double height = (sourceBounds.getSouthWest().getLatitude()-sourceBounds.getNorthEast().getLatitude());
        
        Coord c = center != null ? getProjection().fromWGS84(center) : null;
        if (c == null){
            c = new Coord(
                (sourceBounds.getSouthWest().getLatitude()+sourceBounds.getNorthEast().getLatitude())/2.0,
                (sourceBounds.getNorthEast().getLongitude()+sourceBounds.getSouthWest().getLongitude())/2.0,
                true
            );
        }
        
        
        double aspect = width/height;
        double targetAspect = ((double)getWidth())/((double)getHeight());
        
        if (aspect < targetAspect){
            width = height / targetAspect;
        } else {
            height = width / targetAspect;
        }
        
        Coord southWest = projection.toWGS84(new Coord(c.getLatitude()-height/2.0/scale, c.getLongitude()-width/2.0/scale, true));
        Coord northEast = projection.toWGS84(new Coord(c.getLatitude()+height/2.0/scale, c.getLongitude()+width/2.0/scale, true));
        
        
        
        this.viewport = new BoundingBox(southWest, northEast);
        
    }
    
    
    public void updateViewport(){
        
        updateViewport(featureCollection.calculateBounds());
    }
    
    
    
    
    /**
     * Internal projection that maps to the component's 2D space.
     */
    private class ViewportProjection extends Projection {
        
       
        ViewportProjection(){
            super(viewport);
        }

        @Override
        public Coord fromWGS84(Coord wgs84) {
            if (wgs84.isProjected()){
                return wgs84;
            }
            
            Coord projected = getProjection().fromWGS84(wgs84);
            return scale(projected);
            
        }
        
        private Coord scale(Coord coord){
            BoundingBox sourceBounds = getProjection().fromWGS84(viewport);
            
            double diffX = sourceBounds.getNorthEast().getLongitude()-sourceBounds.getSouthWest().getLongitude();
            double diffY = sourceBounds.getSouthWest().getLatitude()-sourceBounds.getNorthEast().getLatitude();
            
            double scaleX = ((double)getWidth())/diffX;
            double scaleY = ((double)getHeight())/diffY;
            
            double tx = getX()-sourceBounds.getSouthWest().getLongitude()*scaleX;
            double ty = getY()-sourceBounds.getNorthEast().getLatitude()*scaleY;
            
            // OK.  
            
            coord.setLatitude(coord.getLatitude()*scaleY + ty);
            coord.setLongitude(coord.getLongitude()*scaleX + tx);
            return coord;
        }

        @Override
        public Coord toWGS84(Coord proj) {
            if (!proj.isProjected()){
                return proj;
            }
            
            Coord unscaled = unscale(proj);
            return getProjection().toWGS84(unscaled);
            
        }
        
        private Coord unscale(Coord coord){
            BoundingBox destBounds = getProjection().fromWGS84(viewport);
            
            double diffX = destBounds.getNorthEast().getLongitude()-destBounds.getSouthWest().getLongitude();
            double diffY = destBounds.getSouthWest().getLatitude()-destBounds.getNorthEast().getLatitude();
            
            double scaleX = diffX/((double)getWidth());
            double scaleY = diffY/((double)getHeight());
            
            double tx = destBounds.getSouthWest().getLongitude()-((double)getX())*scaleX;
            double ty = destBounds.getNorthEast().getLatitude()-((double)getY())*scaleY;
            
            // OK.  
            //Log.p("Before unscaled "+coord);
            coord.setLatitude(coord.getLatitude()*scaleY + ty);
            coord.setLongitude(coord.getLongitude()*scaleX + tx);
            //Log.p("After unscaled "+coord);
            //Log.p("Scaled back is "+scale(coord));
            return coord;
        }
        
    }
    
    private ChangeBuffer getChangeBuffer(){
        if (changes == null){
            changes = new LinkedList<ChangeBuffer>();
            
        }
        if (changes.isEmpty()){
            changes.add(new ChangeBuffer());
        }
        ChangeBuffer buf = changes.peekLast();
        if (buf.anim != null){
            changes.addLast(new ChangeBuffer());
            return changes.peekLast();
        } else {
            return buf;
        }
    }
    
    public void setCenter(Coord center){
        if (bufferChanges && this.center != null && this.getComponentForm() != null){
            getChangeBuffer().center = center;
        } else {
            this.center = center;
        }
    }
    
    public Coord getCenter(){
        if (bufferChanges && changes != null && getChangeBuffer().center != null){
            return getChangeBuffer().center;
        } else {
            return center;
        }
    }
    
    
    public void setScale(double scale){
        if (bufferChanges && this.scale != 0 && this.getComponentForm() != null){
            this.getChangeBuffer().scale = scale;
        } else {
            this.scale = scale;
        }
    }
    
    public double getScale(){
        if (bufferChanges && changes != null && getChangeBuffer().scale != 0){
            return getChangeBuffer().scale;
        } else {
            return scale;
        }
    }

    @Override
    protected Dimension calcPreferredSize() {
        return new Dimension(
                Display.getInstance().convertToPixels(100, true),
                Display.getInstance().convertToPixels(100, false)
        );
    }
    
    public void animateViewport(int duration){
        if (bufferChanges && changes!=null){
            ChangeBuffer ch = getChangeBuffer();
            if (ch.scale == 0 && ch.center == null){
                changes.removeLast();
                return;
            }
            ViewportAnimation anim = new ViewportAnimation(ch, duration);
            ch.anim = anim;
            
            if (changes.size()==1){
                anim.start();
            }
            
            
        }
    }
    
    private class ViewportAnimation implements Animation {
        private boolean finished;
        private Motion motion;
        private ChangeBuffer changes;
        
        
        ViewportAnimation(ChangeBuffer changes, int duration){
            this.changes = changes;
            this.motion = Motion.createEaseInOutMotion(0, 100, duration);
        }
        
        public void start(){
            getComponentForm().registerAnimated(this);
            this.motion.start();
        }

        public boolean animate() {
            if (finished){
                getComponentForm().deregisterAnimated(this);
                this.cleanup();
                return false;
            } else {
                double value = motion.getValue();
                if (changes.scale != 0){
                    double tweenScale = scale + (changes.scale-scale)*value/100.0;
                    scale = tweenScale;
                }
                if (changes.center != null){
                    Coord tweenCenter = new Coord(0,0);
                    if (tweenCenter.isProjected()){
                        tweenCenter = projection.toWGS84(tweenCenter);
                    }
                    Coord oldCenter = new Coord(center);
                    if (oldCenter.isProjected()){
                        oldCenter = projection.toWGS84(oldCenter);
                    }
                    Coord newCenter = new Coord(changes.center);
                    if (newCenter.isProjected()){
                        newCenter = projection.toWGS84(newCenter);
                    }
                    
                    tweenCenter.setLatitude(oldCenter.getLatitude()+(newCenter.getLatitude()-oldCenter.getLatitude())*value/100.0);
                    tweenCenter.setLongitude(oldCenter.getLongitude()+(newCenter.getLongitude()-oldCenter.getLongitude())*value/100.0);
                    center = tweenCenter;
                }
                if (motion.isFinished()){
                    finished = true;
                }
                return true;
            }
            
        }

        public void paint(Graphics g) {
            updateViewport();
            GeoVizComponent.this.repaint();
        }
        
        public void cleanup(){
            this.changes = null;
            this.motion = null;
            Deque<ChangeBuffer> queue = GeoVizComponent.this.changes;
            queue.removeFirst();
            if (!queue.isEmpty() && queue.peekFirst().anim != null){
                queue.peekFirst().anim.start();
            }
            
        }
        
    }
    
    /*
    private class ScaledMercator extends Mercator {
        private double scale = 2.5;
        
        ScaledMercator(){
            
            
        }
        
        @Override
        public Coord fromWGS84(Coord wgs84) {
            if (wgs84.isProjected()){
                return wgs84;
            }
            Coord c = super.fromWGS84(wgs84);
            BoundingBox bbox = new BoundingBox(super.fromWGS84(this.extent().getSouthWest()), super.fromWGS84(this.extent().getNorthEast()));
            
            double diffX = bbox.getNorthEast().getLongitude()-bbox.getSouthWest().getLongitude();
            double diffY = bbox.getSouthWest().getLatitude()-bbox.getNorthEast().getLatitude();
            
            double scaleX = ((double)getWidth())/diffX*scale;
            double scaleY = ((double)getWidth())/diffY*scale;
            
            //Log.p("scale X"+scaleX+" scaleY"+scaleY+" latDiff "+diffY);
            
            double tx = getX()-bbox.getSouthWest().getLongitude()*scaleX;
            double ty = getY()-bbox.getNorthEast().getLatitude()*scaleY;
            
            // OK.  
            
            c.setLatitude(c.getLatitude()*scaleY + ty);
            c.setLongitude(c.getLongitude()*scaleX + tx);
            
            
            return c;
        }

        @Override
        public Coord toWGS84(Coord projection) {
            return super.toWGS84(projection);
        }
    }
    
    */
    
}
