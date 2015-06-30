/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.charts;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.RangeCategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.models.XYValueSeries;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.BubbleChart;
import com.codename1.charts.views.CombinedXYChart;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.LineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.charts.views.RangeBarChart;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;


/**
 *
 * @author shannah
 */
public class ChartsInBoxLayout {

    Font smallFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
   Font medFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_MEDIUM, Font.STYLE_PLAIN);
   Font largeFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_LARGE, Font.STYLE_PLAIN);
   
    Form form;

    public ChartsInBoxLayout() {
        form = new Form("Charts in Box Layout");
        form.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Component chart1 = getTemperatureChart();
        chart1.getStyle().setBgColor(0x0);
        //chart1.setHeight(300);
        //chart1.setWidth(300);
        chart1.setPreferredH(500);
        
        form.addComponent(chart1);
        Component chart2 = getCombinedTemperatureChart();
        chart2.getStyle().setBgColor(0x0);
        chart2.setPreferredH(500);
        //chart2.setHeight(300);
        //chart2.setWidth(300);
        form.setScrollableY(true);
        form.addComponent(chart2);
        
        
        

    }
    
    public Form getForm(){
        return form;
    }

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

    private Component getTemperatureChart() {
        double[] minValues = new double[]{-24, -19, -10, -1, 7, 12, 15, 14, 9, 1, -11, -16};
        double[] maxValues = new double[]{7, 12, 24, 28, 33, 35, 37, 36, 28, 19, 11, 4};

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        RangeCategorySeries series = new RangeCategorySeries("Temperature");
        int length = minValues.length;
        for (int k = 0; k < length; k++) {
            series.add(minValues[k], maxValues[k]);
        }
        dataset.addSeries(series.toXYSeries());
        int[] colors = new int[]{ColorUtil.CYAN};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        setChartSettings(renderer, "Monthly temperature range", "Month", "Celsius degrees", 0.5, 12.5,
                -30, 45, ColorUtil.GRAY, ColorUtil.LTGRAY);
        renderer.setBarSpacing(0.5);
        renderer.setXLabels(0);
        renderer.setYLabels(10);
        renderer.addXTextLabel(1, "Jan");
        renderer.addXTextLabel(3, "Mar");
        renderer.addXTextLabel(5, "May");
        renderer.addXTextLabel(7, "Jul");
        renderer.addXTextLabel(10, "Oct");
        renderer.addXTextLabel(12, "Dec");
        renderer.addYTextLabel(-25, "Very cold");
        renderer.addYTextLabel(-10, "Cold");
        renderer.addYTextLabel(5, "OK");
        renderer.addYTextLabel(20, "Nice");
        renderer.setMargins(new int[]{30, 70, 10, 0});
        renderer.setYLabelsAlign(Component.RIGHT);
        XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(smallFont.getHeight()/2);
        r.setChartValuesSpacing(3);
        r.setGradientEnabled(true);
        r.setGradientStart(-20, ColorUtil.BLUE);
        r.setGradientStop(20, ColorUtil.GREEN);
        RangeBarChart chart = new RangeBarChart(dataset, renderer, BarChart.Type.DEFAULT);
        return new ChartComponent(chart);
        
    }

    private Component getCombinedTemperatureChart() {
        String[] titles = new String[]{"Crete Air Temperature", "Skiathos Air Temperature"};
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        }
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[]{12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
            13.9});
        values.add(new double[]{9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10});
        int[] colors = new int[]{ColorUtil.GREEN, ColorUtil.rgb(200, 150, 0)};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND};
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        renderer.setPointSize(5.5f);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            r.setLineWidth(5);
            r.setFillPoints(true);
        }
        setChartSettings(renderer, "Weather data", "Month", "Temperature", 0.5, 12.5, 0, 40,
                ColorUtil.LTGRAY, ColorUtil.LTGRAY);

        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Component.RIGHT);
        renderer.setYLabelsAlign(Component.RIGHT);
        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[]{-10, 20, -10, 40});
        renderer.setZoomLimits(new double[]{-10, 20, -10, 40});

        XYValueSeries sunSeries = new XYValueSeries("Sunshine hours");
        sunSeries.add(1f, 35, 4.3);
        sunSeries.add(2f, 35, 4.9);
        sunSeries.add(3f, 35, 5.9);
        sunSeries.add(4f, 35, 8.8);
        sunSeries.add(5f, 35, 10.8);
        sunSeries.add(6f, 35, 11.9);
        sunSeries.add(7f, 35, 13.6);
        sunSeries.add(8f, 35, 12.8);
        sunSeries.add(9f, 35, 11.4);
        sunSeries.add(10f, 35, 9.5);
        sunSeries.add(11f, 35, 7.5);
        sunSeries.add(12f, 35, 5.5);
        XYSeriesRenderer lightRenderer = new XYSeriesRenderer();
        lightRenderer.setColor(ColorUtil.YELLOW);

        XYSeries waterSeries = new XYSeries("Crete Water Temperature");
        waterSeries.add(1, 16);
        waterSeries.add(2, 15);
        waterSeries.add(3, 16);
        waterSeries.add(4, 17);
        waterSeries.add(5, 20);
        waterSeries.add(6, 23);
        waterSeries.add(7, 25);
        waterSeries.add(8, 25.5);
        waterSeries.add(9, 26.5);
        waterSeries.add(10, 24);
        waterSeries.add(11, 22);
        waterSeries.add(12, 18);
        XYSeries waterSeries2 = new XYSeries("Skiathos Water Temperature");
        waterSeries2.add(1, 15);
        waterSeries2.add(2, 14);
        waterSeries2.add(3, 14);
        waterSeries2.add(4, 15);
        waterSeries2.add(5, 18);
        waterSeries2.add(6, 22);
        waterSeries2.add(7, 24);
        waterSeries2.add(8, 25);
        waterSeries2.add(9, 24);
        waterSeries2.add(10, 21);
        waterSeries2.add(11, 18);
        waterSeries2.add(12, 16);
        renderer.setBarSpacing(0.3);
        XYSeriesRenderer waterRenderer1 = new XYSeriesRenderer();
        waterRenderer1.setColor(0xff0099cc);
        waterRenderer1.setChartValuesTextAlign(Component.CENTER);
        XYSeriesRenderer waterRenderer2 = new XYSeriesRenderer();
        waterRenderer2.setColor(0xff9933cc);
        waterRenderer2.setChartValuesTextAlign(Component.RIGHT);

        XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
        dataset.addSeries(0, sunSeries);
        dataset.addSeries(0, waterSeries);
        dataset.addSeries(0, waterSeries2);
        renderer.addSeriesRenderer(0, lightRenderer);
        renderer.addSeriesRenderer(0, waterRenderer1);
        renderer.addSeriesRenderer(0, waterRenderer2);
        waterRenderer1.setDisplayChartValues(true);
        waterRenderer1.setChartValuesTextSize(smallFont.getHeight()/2);
        waterRenderer2.setDisplayChartValues(true);
        waterRenderer2.setChartValuesTextSize(smallFont.getHeight()/2);

        CombinedXYChart.XYCombinedChartDef[] types = new CombinedXYChart.XYCombinedChartDef[]{
            new CombinedXYChart.XYCombinedChartDef(BarChart.TYPE, 0, 1), new CombinedXYChart.XYCombinedChartDef(BubbleChart.TYPE, 2),
            new CombinedXYChart.XYCombinedChartDef(LineChart.TYPE, 3), new CombinedXYChart.XYCombinedChartDef(CubicLineChart.TYPE, 4)};

        CombinedXYChart chart = new CombinedXYChart(dataset, renderer, types);
        return new ChartComponent(chart);
        

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
        renderer.setMargins(new int[]{20, 30, 15, 20});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
    }

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

}
