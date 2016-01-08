/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codename1.demos.charts;

import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.MultipleCategorySeries;
import com.codename1.charts.models.TimeSeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import java.util.Date;
import java.util.List;




/**
 * An abstract class for the demo charts to extend. It contains some methods for
 * building datasets and renderers.
 */
public abstract class AbstractDemoChart implements IDemoChart {

    
   private boolean drawOnMutableImage;
   Font smallFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
   Font medFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_MEDIUM, Font.STYLE_PLAIN);
   Font largeFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_LARGE, Font.STYLE_PLAIN);
    
  /**
   * Builds an XY multiple dataset using the provided values.
   * 
   * @param titles the series titles
   * @param xValues the values for the X axis
   * @param yValues the values for the Y axis
   * @return the XY multiple dataset
   */
  protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    addXYSeries(dataset, titles, xValues, yValues, 0);
    return dataset;
  }

  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
      List<double[]> yValues, int scale) {
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      XYSeries series = new XYSeries(titles[i], scale);
      double[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
  }

  /**
   * Builds an XY multiple series renderer.
   * 
   * @param colors the series rendering colors
   * @param styles the series point styles
   * @return the XY multiple series renderers
   */
  protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    setRenderer(renderer, colors, styles);
    return renderer;
  }

  
  
  protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
    renderer.setAxisTitleTextSize(smallFont.getHeight()/2);
    renderer.setChartTitleTextSize(smallFont.getHeight());
    renderer.setLabelsTextSize(smallFont.getHeight()/2);
    renderer.setLegendTextSize(smallFont.getHeight()/2);
    renderer.setPointSize(5f);
    renderer.setMargins(new int[] { medFont.getHeight(), medFont.getHeight(), 15, medFont.getHeight() });
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = new XYSeriesRenderer();
      r.setColor(colors[i]);
      r.setPointStyle(styles[i]);
      renderer.addSeriesRenderer(r);
    }
  }

  /**
   * Sets a few of the series renderer settings.
   * 
   * @param renderer the renderer to set the properties to
   * @param title the chart title
   * @param xTitle the title for the X axis
   * @param yTitle the title for the Y axis
   * @param xMin the minimum value on the X axis
   * @param xMax the maximum value on the X axis
   * @param yMin the minimum value on the Y axis
   * @param yMax the maximum value on the Y axis
   * @param axesColor the axes color
   * @param labelsColor the labels color
   */
  protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
      int labelsColor) {
    renderer.setChartTitle(title);
    renderer.setXTitle(xTitle);
    renderer.setYTitle(yTitle);
    renderer.setXAxisMin(xMin);
    renderer.setXAxisMax(xMax);
    renderer.setYAxisMin(yMin);
    renderer.setYAxisMax(yMax);
    renderer.setAxesColor(axesColor);
    renderer.setLabelsColor(labelsColor);
  }

  /**
   * Builds an XY multiple time dataset using the provided values.
   * 
   * @param titles the series titles
   * @param xValues the values for the X axis
   * @param yValues the values for the Y axis
   * @return the XY multiple time dataset
   */
  protected XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      TimeSeries series = new TimeSeries(titles[i]);
      Date[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
    return dataset;
  }

  /**
   * Builds a category series using the provided values.
   * 
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (double value : values) {
      series.add("Project " + ++k, value);
    }

    return series;
  }

  /**
   * Builds a multiple category series using the provided values.
   * 
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  protected MultipleCategorySeries buildMultipleCategoryDataset(String title,
      List<String[]> titles, List<double[]> values) {
    MultipleCategorySeries series = new MultipleCategorySeries(title);
    int k = 0;
    for (double[] value : values) {
      series.add(2007 + k + "", titles.get(k), value);
      k++;
    }
    return series;
  }

  /**
   * Builds a category renderer to use the provided colors.
   * 
   * @param colors the colors
   * @return the category renderer
   */
  protected DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(smallFont.getHeight()/2);
    renderer.setLegendTextSize(smallFont.getHeight()/2);
    renderer.setMargins(new int[] { medFont.getHeight(), medFont.getHeight(), medFont.getHeight(), medFont.getHeight() });
    for (int color : colors) {
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(color);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }

  /**
   * Builds a bar multiple series dataset using the provided values.
   * 
   * @param titles the series titles
   * @param values the values
   * @return the XY multiple bar dataset
   */
  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      CategorySeries series = new CategorySeries(titles[i]);
      double[] v = values.get(i);
      int seriesLength = v.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(v[k]);
      }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }

  /**
   * Builds a bar multiple series renderer to use the provided colors.
   * 
   * @param colors the series renderers colors
   * @return the bar multiple series renderer
   */
  protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(smallFont.getHeight()/2);
    renderer.setChartTitleTextFont(smallFont);
    renderer.setLabelsTextSize(smallFont.getHeight()/2);
    renderer.setLegendTextSize(smallFont.getHeight()/2);
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = new XYSeriesRenderer();
      r.setColor(colors[i]);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }
  
  protected Form wrap(String title, Component c){
      c.getStyle().setBgColor(0xff0000);
      Form f = new Form(title);
      f.setLayout(new BorderLayout());
      if (isDrawOnMutableImage()) {
          int dispW = Display.getInstance().getDisplayWidth();
          int dispH = Display.getInstance().getDisplayHeight();
          Image img = Image.createImage((int)(dispW * 0.8), (int)(dispH * 0.8), 0x0);
          Graphics g = img.getGraphics();
          c.setWidth((int)(dispW * 0.8));
          c.setHeight((int)(dispH * 0.8));
          c.paint(g);
          f.addComponent(BorderLayout.CENTER, new Label(img));
      } else {
        f.addComponent(BorderLayout.CENTER, c);
      }
      
      return f;
  }

  public void setDrawOnMutableImage(boolean b) {
      this.drawOnMutableImage = b;
  }
  
  public boolean isDrawOnMutableImage() {
      return this.drawOnMutableImage;
  }
  
}
