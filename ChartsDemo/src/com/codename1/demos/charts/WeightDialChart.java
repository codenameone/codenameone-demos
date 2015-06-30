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
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DialRenderer;
import com.codename1.charts.renderers.DialRenderer.Type;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.DialChart;
import com.codename1.ui.Form;

import com.codename1.charts.util.ColorUtil;





/**
 * Budget demo pie chart.
 */
public class WeightDialChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * @return the chart name
   */
  public String getName() {
    return "Weight chart";
  }
  
  /**
   * Returns the chart description.
   * @return the chart description
   */
  public String getDesc() {
    return "The weight indicator (dial chart)";
  }
  
  /**
   * Executes the chart demo.
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    CategorySeries category = new CategorySeries("Weight indic");
    category.add("Current", 75);
    category.add("Minimum", 65);
    category.add("Maximum", 90);
    DialRenderer renderer = new DialRenderer();
    renderer.setChartTitleTextFont(largeFont);
    renderer.setLabelsTextFont(medFont);
    renderer.setLegendTextFont(medFont);
    renderer.setMargins(new int[] {20, 30, 15, 0});
    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    r.setColor(ColorUtil.BLUE);
    renderer.addSeriesRenderer(r);
    r = new SimpleSeriesRenderer();
    r.setColor(ColorUtil.rgb(0, 150, 0));
    renderer.addSeriesRenderer(r);
    r = new SimpleSeriesRenderer();
    r.setColor(ColorUtil.GREEN);
    renderer.addSeriesRenderer(r);
    renderer.setLabelsTextSize(smallFont.getHeight()/2);
    renderer.setLabelsColor(ColorUtil.WHITE);
    renderer.setShowLabels(true);
    renderer.setVisualTypes(new DialRenderer.Type[] {Type.ARROW, Type.NEEDLE, Type.NEEDLE});
    renderer.setMinValue(0);
    renderer.setMaxValue(150);
    
    DialChart chart = new DialChart(category, renderer);
    return wrap("Weight indicator", new ChartComponent(chart));
    
  }

}
