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

import com.codename1.charts.ChartComponent;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;





/**
 * Scatter demo chart.
 */
public class ScatterChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Scatter chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "Randomly generated values for the scatter chart";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "Series 1", "Series 2", "Series 3", "Series 4", "Series 5" };
    List<double[]> x = new ArrayList<double[]>();
    List<double[]> values = new ArrayList<double[]>();
    int count = 20;
    int length = titles.length;
    Random r = new Random();
    for (int i = 0; i < length; i++) {
      double[] xValues = new double[count];
      double[] yValues = new double[count];
      for (int k = 0; k < count; k++) {
        xValues[k] = k + r.nextInt() % 10;
        yValues[k] = k * 2 + r.nextInt() % 10;
      }
      x.add(xValues);
      values.add(yValues);
    }
    int[] colors = new int[] { ColorUtil.BLUE, ColorUtil.CYAN, ColorUtil.MAGENTA, ColorUtil.LTGRAY, ColorUtil.GREEN };
    PointStyle[] styles = new PointStyle[] { PointStyle.X, PointStyle.DIAMOND, PointStyle.TRIANGLE,
        PointStyle.SQUARE, PointStyle.CIRCLE };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    setChartSettings(renderer, "Scatter chart", "X", "Y", -10, 30, -10, 51, ColorUtil.GRAY,
        ColorUtil.LTGRAY);
    renderer.setXLabels(10);
    renderer.setYLabels(10);
    length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    
    com.codename1.charts.views.ScatterChart chart = new com.codename1.charts.views.ScatterChart(buildDataset(titles, x, values), renderer);
    return wrap("", new ChartComponent(chart));
    
  }

}
