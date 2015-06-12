/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.charts;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.BarChart.Type;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;




/**
 * Sales demo bar chart.
 */
public class MetricsStackedBarChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Metrics stacked bar chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "System Health and Compliance";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
    String[] titles = new String[] { "Gnrl", "Gnr Pvt", "Conc.", "Pvt", "VA", "S3"};
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 33, 22, 30, 15}); // Gnrl
    values.add(new double[] { 12, 32, 10, 35}); // Gnr Pvt
    values.add(new double[] { 12, 21, 20, 10}); // Conc.
    values.add(new double[] { 6, 12, 20, 10}); // Pvt
    values.add(new double[] { 21, 12, 22, 23}); // VA
    values.add(new double[] { 11, 12, 20, 21}); // S3
    int[] colors = new int[]{ColorUtil.rgb(0, 76, 153), ColorUtil.rgb(0, 102, 204), ColorUtil.rgb(0, 128, 255), ColorUtil.rgb(51, 153, 255)
                            , ColorUtil.rgb(102, 178, 255), ColorUtil.rgb(153, 204, 255)};
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    setChartSettings(renderer, "Dispensary Revenue By Day", "", "Prescription Type Revenue", 0.5,
        4.5, 0, 50, ColorUtil.GRAY, ColorUtil.LTGRAY);

    renderer.setXLabels(0);
    renderer.setYLabels(0);
    renderer.setXLabelsAlign(Component.LEFT);
    renderer.setYLabelsAlign(Component.LEFT);
    renderer.setPanEnabled(true, false);
    // renderer.setZoomEnabled(false);
    renderer.setZoomRate(1.1f);
    renderer.setBarSpacing(0.5f);
      renderer.setApplyBackgroundColor(true);
      renderer.setBackgroundColor(ColorUtil.WHITE);
      renderer.setMarginsColor(ColorUtil.WHITE);
      renderer.setBarWidth(70);
      renderer.addXTextLabel(1, "TODAY");
      renderer.addXTextLabel(2, "YEST.");
      renderer.addXTextLabel(3, "PREV DAY");
      renderer.addXTextLabel(4, "7 DAY AVG");
//      renderer.setChartTitleTextSize(50);
//      renderer.setChartTitleTextSize(50);
//      renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
      int length = renderer.getSeriesRendererCount();
     // System.out.println("Series len "+length);
      for (int i = 0; i < length; i++) {
          XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
          seriesRenderer.setDisplayChartValues(true);
          seriesRenderer.setChartValuesTextFont(largeFont);
//            seriesRenderer.setChartValuesFormat(new ICRNumerFormat());
      }
      
      BarChart chart = new BarChart(buildBarDataset(titles, values), renderer,
        Type.STACKED);
      return wrap("", new ChartComponent(chart));
   
  }

}
