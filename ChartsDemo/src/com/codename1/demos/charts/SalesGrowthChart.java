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
import com.codename1.charts.views.PointStyle;
import com.codename1.charts.views.TimeChart;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.codename1.charts.util.ColorUtil;
import java.util.Calendar;



/**
 * Sales growth demo chart.
 */
public class SalesGrowthChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sales growth";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The sales growth across several years (time chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "Sales growth January 1995 to December 2000" };
    List<Date[]> dates = new ArrayList<Date[]>();
    List<double[]> values = new ArrayList<double[]>();
    Date[] dateValues = new Date[] { DateUtil.date(95, 0, 1), DateUtil.date(95, 3, 1), DateUtil.date(95, 6, 1),
        DateUtil.date(95, 9, 1), DateUtil.date(96, 0, 1), DateUtil.date(96, 3, 1), DateUtil.date(96, 6, 1),
        DateUtil.date(96, 9, 1), DateUtil.date(97, 0, 1), DateUtil.date(97, 3, 1), DateUtil.date(97, 6, 1),
        DateUtil.date(97, 9, 1), DateUtil.date(98, 0, 1), DateUtil.date(98, 3, 1), DateUtil.date(98, 6, 1),
        DateUtil.date(98, 9, 1), DateUtil.date(99, 0, 1), DateUtil.date(99, 3, 1), DateUtil.date(99, 6, 1),
        DateUtil.date(99, 9, 1), DateUtil.date(100, 0, 1), DateUtil.date(100, 3, 1), DateUtil.date(100, 6, 1),
        DateUtil.date(100, 9, 1), DateUtil.date(100, 11, 1) };
    dates.add(dateValues);

    values.add(new double[] { 4.9, 5.3, 3.2, 4.5, 6.5, 4.7, 5.8, 4.3, 4, 2.3, -0.5, -2.9, 3.2, 5.5,
        4.6, 9.4, 4.3, 1.2, 0, 0.4, 4.5, 3.4, 4.5, 4.3, 4 });
    int[] colors = new int[] { ColorUtil.BLUE };
    PointStyle[] styles = new PointStyle[] { PointStyle.POINT };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    setChartSettings(renderer, "Sales growth", "Date", "%", dateValues[0].getTime(),
        dateValues[dateValues.length - 1].getTime(), -4, 11, ColorUtil.GRAY, ColorUtil.LTGRAY);
    renderer.setYLabels(10);
    renderer.setXRoundedLabels(false);
    XYSeriesRenderer xyRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
    FillOutsideLine fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ABOVE);
    fill.setColor(ColorUtil.GREEN);
    xyRenderer.addFillOutsideLine(fill);
    fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_BELOW);
    fill.setColor(ColorUtil.MAGENTA);
    xyRenderer.addFillOutsideLine(fill);
    fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ABOVE);
    fill.setColor(ColorUtil.argb(255, 0, 200, 100));
    fill.setFillRange(new int[] {10, 19});
    xyRenderer.addFillOutsideLine(fill);

    TimeChart chart = new TimeChart(buildDateDataset(titles, dates, values),
        renderer);
    return wrap("MMM yyyy", new ChartComponent(chart));
    
  }
  
  public static class DateUtil {

    public static int getTimezoneOffset(Date date) {
        return 0;
    }
    
    public static Date date(int y, int m, int d){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y + 1900);
        c.set(Calendar.MONTH, m);
        c.set(Calendar.DAY_OF_MONTH, d);
        return c.getTime();
    }
    
}

}
