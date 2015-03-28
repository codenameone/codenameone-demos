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
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;





/**
 * Multiple temperature demo chart.
 */
public class MultipleTemperatureChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Temperature and sunshine";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The average temperature and hours of sunshine in Crete (line chart with multiple Y scales and axis)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "Air temperature" };
    List<double[]> x = new ArrayList<double[]>();
    x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
        13.9 });
    int[] colors = new int[] { ColorUtil.BLUE, ColorUtil.YELLOW };
    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT };
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(2);
    setRenderer(renderer, colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
      r.setLineWidth(3f);
    }
    setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0.5, 12.5, 0, 32,
        ColorUtil.LTGRAY, ColorUtil.LTGRAY);
    renderer.setXLabels(12);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Component.RIGHT);
    renderer.setYLabelsAlign(Component.RIGHT);
    renderer.setZoomButtonsVisible(true);
    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
    renderer.setZoomRate(1.05f);
    renderer.setLabelsColor(ColorUtil.WHITE);
    renderer.setXLabelsColor(ColorUtil.GREEN);
    renderer.setYLabelsColor(0, colors[0]);
    renderer.setYLabelsColor(1, colors[1]);

    renderer.setYTitle("Hours", 1);
    renderer.setYAxisAlign(Component.RIGHT, 1);
    renderer.setYLabelsAlign(Component.LEFT, 1);
    renderer.setGridColor(colors[0], 0);
    renderer.setGridColor(colors[1], 1);

    XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
    x.clear();
    x.add(new double[] { -1, 0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
    values.clear();
    values.add(new double[] { 4.3, 4.9, 5.9, 8.8, 10.8, 11.9, 13.6, 12.8, 11.4, 9.5, 7.5, 5.5 });
    addXYSeries(dataset, new String[] { "Sunshine hours" }, x, values, 1);

    CubicLineChart chart = new CubicLineChart(dataset, renderer, 0.3f);
    return wrap("Average temperature", new ChartComponent(chart));
    
  }
}
