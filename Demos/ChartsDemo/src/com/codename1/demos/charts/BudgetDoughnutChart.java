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
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.views.DoughnutChart;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;





/**
 * Budget demo pie chart.
 */
public class BudgetDoughnutChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Budget chart for several years";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The budget per project for several years (doughnut chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 12, 14, 11, 10, 19 });
    values.add(new double[] { 10, 9, 14, 20, 11 });
    List<String[]> titles = new ArrayList<String[]>();
    titles.add(new String[] { "P1", "P2", "P3", "P4", "P5" });
    titles.add(new String[] { "Project1", "Project2", "Project3", "Project4", "Project5" });
    int[] colors = new int[] { ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN };

    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setApplyBackgroundColor(true);
    renderer.setBackgroundColor(ColorUtil.rgb(222, 222, 200));
    renderer.setLabelsColor(ColorUtil.GRAY);
    
    DoughnutChart chart = new DoughnutChart(buildMultipleCategoryDataset("Project budget", titles, values), renderer);
    ChartComponent c = new ChartComponent(chart);
    return wrap("Doughnut Chart Demo", c);
    
  }

}
