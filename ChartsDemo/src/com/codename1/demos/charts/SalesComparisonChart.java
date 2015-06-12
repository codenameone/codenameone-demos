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
import com.codename1.charts.renderers.XYSeriesRenderer.FillOutsideLine;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;




/**
 * Sales comparison demo chart.
 */
public class SalesComparisonChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sales comparison";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "Monthly sales advance for 2 years (interpolated line and area charts)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "Sales for 2008", "Sales for 2007",
        "Difference between 2008 and 2007 sales" };
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 14230, 12300, 14240, 15244, 14900, 12200, 11030, 12000, 12500, 15500,
        14600, 15000 });
    values.add(new double[] { 10230, 10900, 11240, 12540, 13500, 14200, 12530, 11200, 10500, 12500,
        11600, 13500 });
    int length = values.get(0).length;
    double[] diff = new double[length];
    for (int i = 0; i < length; i++) {
      diff[i] = values.get(0)[i] - values.get(1)[i];
    }
    values.add(diff);
    int[] colors = new int[] { ColorUtil.BLUE, ColorUtil.CYAN, ColorUtil.GREEN };
    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT, PointStyle.POINT };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    setChartSettings(renderer, "Monthly sales in the last 2 years", "Month", "Units sold", 0.75,
        12.25, -5000, 19000, ColorUtil.GRAY, ColorUtil.LTGRAY);
    renderer.setXLabels(12);
    renderer.setYLabels(10);
    renderer.setChartTitleTextFont(smallFont);
    renderer.setTextTypeface(Font.FACE_SYSTEM, Font.STYLE_BOLD);
    renderer.setLabelsTextSize(smallFont.getHeight()/2);
    renderer.setAxisTitleTextSize(smallFont.getHeight()/2);
    renderer.setLegendTextSize(smallFont.getHeight()/2);
    length = renderer.getSeriesRendererCount();

    for (int i = 0; i < length; i++) {
      XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
      if (i == length - 1) {
        FillOutsideLine fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ALL);
        fill.setColor(ColorUtil.GREEN);
        seriesRenderer.addFillOutsideLine(fill);
      }
      seriesRenderer.setLineWidth(2.5f);
      seriesRenderer.setDisplayChartValues(true);
      seriesRenderer.setChartValuesTextSize(smallFont.getHeight()/3);
    }
    
    CubicLineChart chart = new CubicLineChart(buildBarDataset(titles, values), renderer,
        0.5f);
    return wrap("", new ChartComponent(chart));
    
  }
}
