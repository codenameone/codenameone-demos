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
import com.codename1.charts.views.TimeChart;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.util.MathHelper;
import com.codename1.ui.Display;





/**
 * Temperature sensor demo chart.
 */
public class SensorValuesChart extends AbstractDemoChart {
  private static final long HOUR = 3600 * 1000;

  private static final long DAY = HOUR * 24;

  private static final int HOURS = 24;

  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sensor data";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The temperature, as read from an outside and an inside sensors";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "Inside", "Outside" };
    long now = Math.round(new Date().getTime() / DAY) * DAY;
    List<Date[]> x = new ArrayList<Date[]>();
    for (int i = 0; i < titles.length; i++) {
      Date[] dates = new Date[HOURS];
      for (int j = 0; j < HOURS; j++) {
        dates[j] = new Date(now - (HOURS - j) * HOUR);
      }
      x.add(dates);
    }
    List<double[]> values = new ArrayList<double[]>();

    values.add(new double[] { 21.2, 21.5, 21.7, 21.5, 21.4, 21.4, 21.3, 21.1, 20.6, 20.3, 20.2,
        19.9, 19.7, 19.6, 19.9, 20.3, 20.6, 20.9, 21.2, 21.6, 21.9, 22.1, 21.7, 21.5 });
    values.add(new double[] { 1.9, 1.2, 0.9, 0.5, 0.1, -0.5, -0.6, MathHelper.NULL_VALUE,
        MathHelper.NULL_VALUE, -1.8, -0.3, 1.4, 3.4, 4.9, 7.0, 6.4, 3.4, 2.0, 1.5, 0.9, -0.5,
        MathHelper.NULL_VALUE, -1.9, -2.5, -4.3 });

    int[] colors = new int[] { ColorUtil.GREEN, ColorUtil.BLUE };
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    setChartSettings(renderer, "Sensor temperature", "Hour", "Celsius degrees",
        x.get(0)[0].getTime(), x.get(0)[HOURS - 1].getTime(), -5, 30, ColorUtil.LTGRAY, ColorUtil.LTGRAY);
    
    int strWidth = smallFont.stringWidth("00:00:00 PM")/2;
    
    int numXLabels = Display.getInstance().getDisplayWidth() / (strWidth + 20);
    
    renderer.setXLabels(numXLabels);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Component.CENTER);
    renderer.setYLabelsAlign(Component.RIGHT);
    
    TimeChart chart = new TimeChart(buildDateDataset(titles, x, values),
        renderer);
    return wrap("h:mm a", new ChartComponent(chart));
    

}
}
