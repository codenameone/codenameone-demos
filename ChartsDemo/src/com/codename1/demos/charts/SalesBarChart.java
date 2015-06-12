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
import com.codename1.charts.renderers.XYMultipleSeriesRenderer.Orientation;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.BarChart.Type;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;




/**
 * Sales demo bar chart.
 */
public class SalesBarChart extends AbstractDemoChart {

  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sales horizontal bar chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The monthly sales for the last 2 years (horizontal bar chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "2007", "2008" };
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 5230, 7300, 9240, 10540, 7900, 9200, 12030, 11200, 9500, 10500,
        11600, 13500 });
    values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200, 22030, 21200, 19500, 15500,
        12600, 14000 });
    int[] colors = new int[] { ColorUtil.CYAN, ColorUtil.BLUE };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    renderer.setOrientation(Orientation.HORIZONTAL);
    setChartSettings(renderer, "Monthly sales in the last 2 years", "Month", "Units sold", 0.5,
        12.5, 0, 24000, ColorUtil.GRAY, ColorUtil.LTGRAY);
    renderer.setXLabels(1);
    renderer.setYLabels(10);
    renderer.addXTextLabel(1, "Jan");
    renderer.addXTextLabel(3, "Mar");
    renderer.addXTextLabel(5, "May");
    renderer.addXTextLabel(7, "Jul");
    renderer.addXTextLabel(10, "Oct");
    renderer.addXTextLabel(12, "Dec");
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
      seriesRenderer.setDisplayChartValues(true);
    }
    
    BarChart chart = new BarChart(buildBarDataset(titles, values), renderer,
        Type.DEFAULT);
    return wrap("", new ChartComponent(chart));
    
  }

}
