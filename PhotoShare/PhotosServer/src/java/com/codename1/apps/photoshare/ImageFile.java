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

package com.codename1.apps.photoshare;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Shai Almog
 */
public class ImageFile {
    private String[] array;
    private byte[] imageData;
    private List<String> likedBy = new ArrayList<String>();
    private Map<Dimension, byte[]> thumbs = new HashMap<Dimension, byte[]>();
    
    private static Map<Long, ImageFile> images = new HashMap<Long, ImageFile>();
    
    public static ImageFile getImageFile(long id) {
        return images.get(id);
    }
    
    public static byte[] getImage(long id) {
        return images.get(id).imageData;
    }

    public static byte[] getThumb(long id, int width, int height) {
        return images.get(id).getThumbData(width, height);
    }
    
    public synchronized static long[] listPhotos() {
        long[] val = new long[images.size()];
        Iterator<Long> il = images.keySet().iterator();
        for(int iter = 0 ; iter < val.length ; iter++) {
            val[iter] = il.next();
        }
        return val;
    }
    
    public static String[] getPhotoDetails(long id) {
        return images.get(id).array;
    }
    
    public static int getLikeCount(long id) {
        return images.get(id).likedBy.size();
    }
    
    public static void like(long id, String email) {
        ImageFile f = images.get(id);
        if(!f.likedBy.contains(email)) {
            f.likedBy.add(email);
        }
    }
    
    private static long counter = 0;
    public static synchronized long createImageFile(String[] array, byte[] image) {
        counter++;
        images.put(counter, new ImageFile(array, image));
        return counter;
    }
    
    private ImageFile(String[] array, byte[] image) {
        this.array = array;
        this.imageData = image;
    }
    
    /**
     * @return the array
     */
    public String[] getArray() {
        return array;
    }

    /**
     * @return the imageData
     */
    public byte[] getImageData() {
        return imageData;
    }

    /**
     * @return the thumbData
     */
    public byte[] getThumbData(int width, int height) {
        Dimension d = new Dimension(width, height);
        byte[] b = thumbs.get(d);
        if(b == null) {
            try {
                BufferedImage icon = ImageIO.read(new ByteArrayInputStream(imageData));
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ImageIO.write(getScaledUnevenInstance(icon, width, height), "jpeg", bo);
                byte[] bb = bo.toByteArray();
                thumbs.put(d, bb);
                return bb;
            } catch(IOException err) {
                err.printStackTrace();
            }
        }
        return b;
    }
    
    protected BufferedImage getScaledUnevenInstance(BufferedImage img,
                                           int width,
                                           int height) {
        int iW = img.getWidth();
        int iH = img.getHeight();
        float r = Math.max(((float)width) / ((float)iW), ((float)height) / ((float)iH));
        int bwidth = (int)(((float)iW) * r);
        int bheight = (int)(((float)iH) * r);
            
        BufferedImage b2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = b2.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(img, (width - bwidth) / 2, (height - bheight) / 2, bwidth, bheight, null);
        g.dispose();
        return b2;
    }
    
}
