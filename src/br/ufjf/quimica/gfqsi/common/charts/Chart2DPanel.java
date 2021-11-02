/*
 *
 * The GFQSI-Common Project : Common classes for solid state applications.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/common
 * Project Lead:  Ary Rodrigues Ferreira Junior
 *
 * (C) Copyright 2007 - 2009 by Ary Junior and GFQSI-UFJF (Grupo de Fisico Quimica de Solidos e Interfaces da UFJF).
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package br.ufjf.quimica.gfqsi.common.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * A panel for display a 2D chart.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Chart2DPanel.java,v 1.21 2010-04-08 18:29:39 aryjr Exp $
 * 
 */
public class Chart2DPanel extends JPanel implements MouseMotionListener, MouseListener {

    public static final String CVS_REVISION = "$Revision: 1.21 $";
    public static final int X_VALUE = 0;
    public static final int X_COORDINATE = 1;
    public static final int Y_VALUE = 2;
    public static final int Y_COORDINATE = 3;
    public static final int LINE_CHART = 0;
    public static final int POINT_CHART = 1;
    public static final int LINE_LINE_CHART = 2;
    public static final int SQUARE_CHART = 3;
    public static final int Y_GAP_ON_TOP = 0;
    public static final int Y_GAP_ON_BOTTOM = 1;
    public static final int Y_GAP_ON_BOTH = 2;
    private int chartType = 0;
    private Chart2DListener listener;
    private List<Double>[] series;
    private boolean updateChartArea = true;
    private boolean updateChart = false;
    private Color[] seriesColours;
    private Image offscreen;
    private double chartX, chartY, chartWidth, chartHeight, areaWidth, areaHeight;
    private String xLabel = "X LABEL";
    private String yLabel = "Y LABEL";
    private DecimalFormat xFormat = new DecimalFormat("#0.00");
    private DecimalFormat yFormat = new DecimalFormat("#0.00");
    private boolean showXAxis = true;
    private boolean showYAxis = true;
    private boolean enableMouseMotionEvents = false;
    private boolean enableMouseClickEvents = false;
    private boolean enableXAxisZoom = false;
    private String[][] optionalXAxis;
    private int mouseSerieIndex = 0;
    private SeriesLimits seriesLimits = new SeriesLimits();
    private final int RIGHT_DRAGGING = 0;
    private final int LEFT_MOVING = 1;
    private int actualMouseEventType = LEFT_MOVING;
    private int currentPointIndex = Integer.MAX_VALUE;
    private int currentMarkedPointIndex = Integer.MAX_VALUE;
    private int firstZoomPointIndex = Integer.MAX_VALUE;
    private double[] firstZoomPoint;
    private int lastZoomPointIndex = Integer.MAX_VALUE;
    private double[] lastZoomPoint;
    private double yAxisGapPercentage = 0;
    private int yAxisGapLocation = -1;
    private double horizontalDashedLineValue = Double.MAX_VALUE;
    private double horizontalDashedLineCoordinate = Double.MAX_VALUE;
    private double verticalDashedLineValue = Double.MAX_VALUE;
    private double verticalDashedLineCoordinate = Double.MAX_VALUE;
    private boolean plotSquares = false;
    private double largestX = Double.MAX_VALUE;
    private double smallerX = -Double.MAX_VALUE;
    private double largestY = Double.MAX_VALUE;
    private double smallerY = -Double.MAX_VALUE;
    private boolean invertedAbsciss = false;
    private boolean invertedOrdinate = false;

    public Chart2DPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.BLUE);
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        updateDimensions();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        // TODO Este metodo esta sendo invocado apos cada mouse moved!!!
        super.setBounds(x, y, width, height);
        updateDimensions();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        updateDimensions();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        updateDimensions();
    }

    private void updateDimensions() {
        areaWidth = getWidth() - 1;
        chartWidth = areaWidth * 0.8d;
        areaHeight = getHeight() - 1;
        chartHeight = areaHeight - 60;
        chartX = areaWidth * 0.15d;
        chartY = 0;
        //updateChartArea = true;
        //updateChart = series != null;
    }

    @Override
    public void paint(Graphics screeng) {
        Rectangle area = screeng.getClipBounds();
        // If there isn't a buffered image with the chart area
        // Or if the object was resized, create the buffer image
        if (updateChartArea) {
            offscreen = createImage(area.width, area.height);
            drawChartArea(offscreen.getGraphics());
            updateChartArea = false;
        }
        // If the series array was updated
        if (updateChart) {
            calculatePoints();
            drawAxis(offscreen.getGraphics());
            drawChart(offscreen.getGraphics());
            updateChart = false;
        }
        // Draw the buffered chart
        screeng.drawImage(offscreen, area.x, area.y, area.width, area.height, null);
        // Draw the vertical line ...
        // ... if any point were marked by a left click
        if (currentMarkedPointIndex != Integer.MAX_VALUE && actualMouseEventType == RIGHT_DRAGGING) {
            screeng.setColor(Color.BLUE);
            double xc = series[0].get(X_COORDINATE + currentMarkedPointIndex).doubleValue();
            screeng.drawLine((int) xc, (int) chartY, (int) xc, (int) chartY + (int) chartHeight);
        }
        if (actualMouseEventType == LEFT_MOVING && currentPointIndex != Integer.MAX_VALUE) {
            // ... when mouse is moving or clicked with the left button
            double xc = series[mouseSerieIndex].get(X_COORDINATE + currentPointIndex).doubleValue();
            double yc = series[mouseSerieIndex].get(Y_COORDINATE + currentPointIndex).doubleValue();
            screeng.setColor(Color.RED);
            screeng.drawLine((int) chartX, (int) yc, (int) chartX + (int) chartWidth, (int) yc);
            screeng.drawLine((int) xc, (int) chartY, (int) xc, (int) chartY + (int) chartHeight);
        } else if (actualMouseEventType == RIGHT_DRAGGING) {
            // ... when mouse dragging with the right button for zoom
            double xc;
            screeng.setColor(Color.BLUE);
            if (firstZoomPointIndex != Integer.MAX_VALUE) {
                xc = series[mouseSerieIndex].get(X_COORDINATE + firstZoomPointIndex).doubleValue();
                screeng.drawLine((int) xc, (int) chartY, (int) xc, (int) chartY + (int) chartHeight);
                screeng.drawString(xFormat.format(firstZoomPoint[Chart2DListener.X_VALUE]), (int) xc, 100);
            }
            if (lastZoomPointIndex != Integer.MAX_VALUE) {
                xc = series[mouseSerieIndex].get(X_COORDINATE + lastZoomPointIndex).doubleValue();
                screeng.drawLine((int) xc, (int) chartY, (int) xc, (int) chartY + (int) chartHeight);
                screeng.drawString(xFormat.format(lastZoomPoint[Chart2DListener.X_VALUE]), (int) xc, 100);
            }
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    private void drawChartArea(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) chartX, (int) chartY, (int) chartWidth, (int) chartHeight);
        g.setColor(new Color(217, 217, 217));
        double space;
        if (showXAxis) {
            space = chartWidth / 5d;
            for (int inc = 1; inc < 5; inc++) {
                g.drawLine((int) chartX + (inc * (int) space), (int) chartY, (int) chartX + (inc * (int) space), (int) chartY + (int) chartHeight);
            }
        }
        if (showYAxis) {
            space = chartHeight / 8d;
            for (int inc = 1; inc < 8; inc++) {
                g.drawLine((int) chartX, (int) chartY + (inc * (int) space), (int) chartX + (int) chartWidth, (int) chartY + (inc * (int) space));
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect((int) chartX, (int) chartY, (int) chartWidth, (int) chartHeight);
    }

    private void calculatePoints() {
        List<Double> serie;
        double LY = getLargest(Y_VALUE);
        double SY = getSmaller(Y_VALUE);
        double diff = LY - SY;
        if (yAxisGapLocation == Y_GAP_ON_TOP) {
            LY += diff * yAxisGapPercentage;
        } else if (yAxisGapLocation == Y_GAP_ON_BOTTOM) {
            SY -= diff * yAxisGapPercentage;
        } else if (yAxisGapLocation == Y_GAP_ON_BOTH) {
            LY += diff * yAxisGapPercentage;
            SY -= diff * yAxisGapPercentage;
        }
        double LX = getLargest(X_VALUE);
        double SX = getSmaller(X_VALUE);
        for (int inc = 0; inc < series.length; inc++) {
            if (seriesLimits.showSerie[inc]) {
                serie = series[inc];
                for (int i = seriesLimits.serieFirstPoint[inc]; i < seriesLimits.serieLastPoint[inc]; i += 4) {
                    serie.set(X_COORDINATE + i,
                            new Double(chartX + ((serie.get(X_VALUE + i).doubleValue() - SX) * chartWidth) / (LX - SX)));
                    serie.set(Y_COORDINATE + i,
                            new Double((serie.get(Y_VALUE + i).doubleValue() - SY) * chartHeight) / (LY - SY));
                    serie.set(Y_COORDINATE + i,
                            new Double(chartHeight - serie.get(Y_COORDINATE + i).doubleValue() + chartY));
                }
            }
        }
        if (horizontalDashedLineValue != Double.MAX_VALUE) {
            horizontalDashedLineCoordinate = ((horizontalDashedLineValue - SY) * chartHeight) / (LY - SY);
            horizontalDashedLineCoordinate = chartHeight - horizontalDashedLineCoordinate + chartY;
        }
        if (getVerticalDashedLineValue() != Double.MAX_VALUE) {
            verticalDashedLineCoordinate = chartX + ((getVerticalDashedLineValue() - SX) * chartWidth) / (LX - SX);
        }
    }

    private void drawAxis(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        // Drawing the labels
        Graphics2D g2d = (Graphics2D) g;
        double space;
        double interval;
        int inc;
        // The Y axis label
        g2d.translate(0, (int) areaHeight);
        g2d.rotate(0.0 - (Math.PI / 2.0));
        g2d.drawString(yLabel, ((int) areaHeight - fm.stringWidth(yLabel)) / 2, fm.getHeight());
        // Undo the transformations
        g2d.rotate(0.0 + (Math.PI / 2.0));
        g2d.translate(0, -(int) areaHeight);
        // The X axis label
        g2d.drawString(xLabel, ((int) areaWidth - fm.stringWidth(xLabel)) / 2, (int) areaHeight - fm.getHeight());
        // Drawing the values ...
        if (showXAxis) {
            // ... on optional X axis
            if (optionalXAxis != null) {
                double xopt;
                for (int i = 0; i < optionalXAxis.length; i++) {
                    if (optionalXAxis[i][1] != null && !optionalXAxis[i][1].equals("")) {
                        xopt = (int) series[0].get((i * 4) + 1).doubleValue();
                        g.drawLine((int) xopt, (int) chartY, (int) xopt, (int) chartY + (int) chartHeight);
                        g.drawString(optionalXAxis[i][1], (int) xopt - (fm.stringWidth(optionalXAxis[i][1]) / 2), (int) chartY + (int) chartHeight + (int) (fm.getHeight() * 1.2d));
                    }
                }
            } else {
                // ... on conventional X axis
                double abscissMult = invertedAbsciss ? -1 : 1;
                double LX = getLargest(X_VALUE);
                double SX = getSmaller(X_VALUE);
                interval = (LX - SX) / 5d;
                g.setColor(Color.BLACK);
                space = chartWidth / 5d;
                g.drawString(xFormat.format(abscissMult * SX), (int) chartX - (int) (fm.stringWidth(xFormat.format(SX)) / 2d), (int) chartY + (int) chartHeight + (int) (fm.getHeight() * 1.2d));
                inc = 1;
                for (; inc < 5; inc++) {
                    SX += interval;
                    g.drawString(xFormat.format(abscissMult * SX), (int) chartX + (int) (inc * space) - (fm.stringWidth(xFormat.format(SX)) / 2), (int) chartY + (int) chartHeight + (int) (fm.getHeight() * 1.2d));
                }
                g.drawString(xFormat.format(abscissMult * LX), (int) chartX + (int) (inc * space) - (fm.stringWidth(xFormat.format(LX)) / 2), (int) chartY + (int) chartHeight + (int) (fm.getHeight() * 1.2d));
            }
        }
        if (showYAxis) {
            // ... on Y axis ...
            double LY = getLargest(Y_VALUE);
            double SY = getSmaller(Y_VALUE);
            double diff = LY - SY;
            if (yAxisGapLocation == Y_GAP_ON_TOP) {
                LY += diff * yAxisGapPercentage;
            } else if (yAxisGapLocation == Y_GAP_ON_BOTTOM) {
                SY -= diff * yAxisGapPercentage;
            } else if (yAxisGapLocation == Y_GAP_ON_BOTH) {
                LY += diff * yAxisGapPercentage;
                SY -= diff * yAxisGapPercentage;
            }
            interval = (LY - SY) / 8d;
            space = chartHeight / 8;
            g.drawString(yFormat.format(LY), (int) chartX - (fm.stringWidth(yFormat.format(LY)) + 5), (int) chartY + (int) (fm.getHeight() * 0.7d));
            inc = 1;
            for (; inc < 8; inc++) {
                LY -= interval;
                g.drawString(yFormat.format(LY), (int) chartX - (fm.stringWidth(yFormat.format(LY)) + 5), (int) chartY + (int) (inc * space) + (int) (fm.getHeight() * 0.5d));
            }
            g.drawString(yFormat.format(SY), (int) chartX - (fm.stringWidth(yFormat.format(SY)) + 5), (int) chartY + (int) (inc * space) + (int) (fm.getHeight() * 0.3d));
        }
    }

    private void drawChart(Graphics g) {
        g.setColor(Color.BLACK);
        if (chartType == LINE_CHART) {
            List<Double> serie = series[0];
            int i;
            for (i = seriesLimits.serieFirstPoint[0]; i < seriesLimits.serieLastPoint[0] - 4; i += 4) {
                g.drawLine((int) serie.get(X_COORDINATE + i).doubleValue(),
                        (int) serie.get(Y_COORDINATE + i).doubleValue(),
                        (int) serie.get(X_COORDINATE + i + 4).doubleValue(),
                        (int) serie.get(Y_COORDINATE + i + 4).doubleValue());
                if (plotSquares) {
                    g.fillRect((int) serie.get(X_COORDINATE + i).doubleValue() - 3,
                            (int) serie.get(Y_COORDINATE + i).doubleValue() - 3, 6, 6);
                }
            }
            if (plotSquares) {
                g.fillRect((int) serie.get(X_COORDINATE + i).doubleValue() - 3,
                        (int) serie.get(Y_COORDINATE + i).doubleValue() - 3, 6, 6);
            }
        } else if (chartType == SQUARE_CHART) {
            List<Double> serie = series[0];
            for (int i = seriesLimits.serieFirstPoint[0]; i < seriesLimits.serieLastPoint[0]; i += 4) {
                g.fillRect((int) serie.get(X_COORDINATE + i).doubleValue() - 1,
                        (int) serie.get(Y_COORDINATE + i).doubleValue() - 1, 6, 6);
            }
        } else if (chartType == POINT_CHART) {
            List<Double> serie = series[0];
            for (int i = seriesLimits.serieFirstPoint[0]; i < seriesLimits.serieLastPoint[0]; i += 4) {
                g.drawOval((int) serie.get(X_COORDINATE + i).doubleValue() - 1,
                        (int) serie.get(Y_COORDINATE + i).doubleValue() - 1, 1, 1);
            }
        } else if (chartType == LINE_LINE_CHART) {
            List<Double> serie;
            for (int is = 0; is < series.length; is++) {
                if (seriesLimits.showSerie[is]) {
                    serie = series[is];
                    if (seriesColours != null) {
                        g.setColor(seriesColours[is]);
                    }
                    for (int i = seriesLimits.serieFirstPoint[is]; i < seriesLimits.serieLastPoint[is] - 4; i += 4) {
                        g.drawLine((int) serie.get(X_COORDINATE + i).doubleValue(),
                                (int) serie.get(Y_COORDINATE + i).doubleValue(),
                                (int) serie.get(X_COORDINATE + i + 4).doubleValue(),
                                (int) serie.get(Y_COORDINATE + i + 4).doubleValue());
                    }
                }
            }
            g.setColor(Color.BLACK);
        }
        if (horizontalDashedLineValue != Double.MAX_VALUE) {
            Graphics2D g2 = (Graphics2D) g;
            BasicStroke dash;
            float dash1[] = {2.5f};
            dash = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2.setStroke(dash);
            g2.drawLine((int) chartX, (int) horizontalDashedLineCoordinate, (int) chartX + (int) chartWidth, (int) horizontalDashedLineCoordinate);
        }
        if (getVerticalDashedLineValue() != Double.MAX_VALUE) {
            Graphics2D g2 = (Graphics2D) g;
            BasicStroke dash;
            float dash1[] = {2.5f};
            dash = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2.setStroke(dash);
            g2.drawLine((int) verticalDashedLineCoordinate, (int) chartY, (int) verticalDashedLineCoordinate, (int) chartY + (int) chartHeight);
        }
    }

    private double getLargest(int coordinate) {
        double largest = -Double.MAX_VALUE;
        if (coordinate == X_VALUE && largestX != Double.MAX_VALUE) {
            return largestX;
        } else if (coordinate == Y_VALUE && largestY != Double.MAX_VALUE) {
            return largestY;
        }
        double current;
        List<Double> serie;
        for (int inc = 0; inc < series.length; inc++) {
            if (seriesLimits.showSerie[inc]) {
                serie = series[inc];
                for (int i = seriesLimits.serieFirstPoint[inc]; i < seriesLimits.serieLastPoint[inc]; i += 4) {
                    current = serie.get(coordinate + i).doubleValue();
                    if (largest < current) {
                        largest = current;
                    }
                }
            }
        }
        return largest;
    }

    private double getSmaller(int coordinate) {
        double smaller = Double.MAX_VALUE;
        if (coordinate == X_VALUE && smallerX != -Double.MAX_VALUE) {
            return smallerX;
        } else if (coordinate == Y_VALUE && smallerY != -Double.MAX_VALUE) {
            return smallerY;
        }
        double current;
        List<Double> serie;
        for (int inc = 0; inc < series.length; inc++) {
            if (seriesLimits.showSerie[inc]) {
                serie = series[inc];
                for (int i = seriesLimits.serieFirstPoint[inc]; i < seriesLimits.serieLastPoint[inc]; i += 4) {
                    current = serie.get(coordinate + i).doubleValue();
                    if (smaller > current) {
                        smaller = current;
                    }
                }
            }
        }
        return smaller;
    }

    public void setSeries(List<Double>[] series) {
        this.series = series;
        resetSeriesLimits();
        update();
    }

    private void resetSeriesLimits() {
        seriesLimits.showSerie = new boolean[series.length];
        seriesLimits.serieFirstPoint = new int[series.length];
        seriesLimits.serieLastPoint = new int[series.length];
        for (int inc = 0; inc < series.length; inc++) {
            seriesLimits.showSerie[inc] = true;
            seriesLimits.serieFirstPoint[inc] = 0;
            seriesLimits.serieLastPoint[inc] = series[inc].size();
        }
    }

    public void update() {
        if (series == null) {
            throw new NullPointerException("You must set the series list before call this method!!!");
        }
        updateChartArea = true;
        updateChart = true;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        if (!enableMouseMotionEvents || series == null) {
            return;
        }
        actualMouseEventType = LEFT_MOVING;
        double[] pt = findPointInSerie(e);
        if (pt != null) {
            listener.mouseMoved(pt);
        }
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        if (enableMouseClickEvents && enableXAxisZoom && e.getClickCount() == 2) {
            resetSeriesLimits();
            currentPointIndex = Integer.MAX_VALUE;
            firstZoomPointIndex = Integer.MAX_VALUE;
            lastZoomPointIndex = Integer.MAX_VALUE;
            currentMarkedPointIndex = Integer.MAX_VALUE;
            update();
            return;
        }
        if (!enableMouseClickEvents || series == null || SwingUtilities.isRightMouseButton(e)) {
            return;
        }
        double[] pt = findPointInSerie(e, 5);
        if (pt != null) {
            // If is a single click the point will be marked
            currentMarkedPointIndex = (int) pt[Chart2DListener.INDEX];
            listener.mouseClicked(pt);
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        if (!enableXAxisZoom || series == null || !SwingUtilities.isRightMouseButton(e)) {
            return;
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            actualMouseEventType = RIGHT_DRAGGING;
            double[] pt = findPointInSerie(e);
            if (pt != null) {
                firstZoomPointIndex = currentPointIndex;
                firstZoomPoint = pt;
            } else {
                currentPointIndex = Integer.MAX_VALUE;
                firstZoomPointIndex = Integer.MAX_VALUE;
            }
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (!enableXAxisZoom || series == null || !SwingUtilities.isRightMouseButton(e)) {
            return;
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            double[] pt = findPointInSerie(e);
            if (pt != null && firstZoomPointIndex != currentPointIndex) {
                lastZoomPointIndex = currentPointIndex;
                lastZoomPoint = pt;
            }
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (!enableXAxisZoom || series == null || !SwingUtilities.isRightMouseButton(e)) {
            return;
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            actualMouseEventType = RIGHT_DRAGGING;
            double[] pt = findPointInSerie(e);
            if (pt != null && firstZoomPointIndex != currentPointIndex) {
                lastZoomPoint = pt;
                setXLimits(firstZoomPoint[Chart2DListener.X_VALUE], lastZoomPoint[Chart2DListener.X_VALUE]);
                currentPointIndex = Integer.MAX_VALUE;
                firstZoomPointIndex = Integer.MAX_VALUE;
                lastZoomPointIndex = Integer.MAX_VALUE;
                update();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        currentPointIndex = Integer.MAX_VALUE;
        repaint();
    }

    private double[] findPointInSerie(MouseEvent e) {
        return findPointInSerie(e, 1);
    }

    private double[] findPointInSerie(MouseEvent e, double interval) {
        if (series == null || series.length == 0) return null;
        List<Double> serie = series[mouseSerieIndex];
        double xp;
        for (int i = seriesLimits.serieFirstPoint[mouseSerieIndex]; i < seriesLimits.serieLastPoint[mouseSerieIndex]; i += 4) {
            xp = serie.get(X_COORDINATE + i).doubleValue();
            if (xp >= e.getX() - interval && xp <= e.getX() + interval) {
                currentPointIndex = i;
                if (listener != null) {
                    return new double[]{serie.get(X_VALUE + i).doubleValue(), serie.get(Y_VALUE + i).doubleValue(), i};
                }
            }
        }
        return null;
    }

    public void setChart2DListener(Chart2DListener listener) {
        this.listener = listener;
    }

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
    }

    public String getXLabel() {
        return xLabel;
    }

    public void setXLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getYLabel() {
        return yLabel;
    }

    public void setYLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public void setXFormat(String format) {
        this.xFormat = new DecimalFormat(format);
    }

    public void setYFormat(String format) {
        this.yFormat = new DecimalFormat(format);
    }

    public boolean isShowXAxis() {
        return showXAxis;
    }

    public void setShowXAxis(boolean showXAxis) {
        this.showXAxis = showXAxis;
    }

    public boolean isShowYAxis() {
        return showYAxis;
    }

    public void setShowYAxis(boolean showYAxis) {
        this.showYAxis = showYAxis;
    }

    public boolean isEnableMouseMotionEvents() {
        return enableMouseMotionEvents;
    }

    public void setEnableMouseMotionEvents(boolean enableMouseMotionEvents) {
        this.enableMouseMotionEvents = enableMouseMotionEvents;
    }

    public void setCurrentPoint(int index) {
        if (series != null) {
            currentPointIndex = index;
            repaint();
        }
    }

    /**
     * @return the seriesColours
     */
    public Color[] getSeriesColours() {
        return seriesColours;
    }

    /**
     * @param seriesColours the seriesColours to set
     */
    public void setSeriesColours(Color[] seriesColours) {
        this.seriesColours = seriesColours;
    }

    /**
     * @return the chartX
     */
    public double getChartX() {
        return chartX;
    }

    /**
     * @return the chartY
     */
    public double getChartY() {
        return chartY;
    }

    /**
     * @return the chartWidth
     */
    public double getChartWidth() {
        return chartWidth;
    }

    /**
     * @return the chartHeight
     */
    public double getChartHeight() {
        return chartHeight;
    }

    public final void exportDatFile(String filePath, String separator, int serieIndex) throws IOException {
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(filePath));
        List<Double> serie;
        if (serieIndex == -1) {
            serie = series[0];
            for (int i = seriesLimits.serieFirstPoint[0]; i < seriesLimits.serieLastPoint[0] - 4; i += 4) {
                bw.write(serie.get(X_VALUE + i).doubleValue() + separator);
                for (List<Double> s : series) {
                    if (Y_VALUE + i <= s.size()) {
                        bw.write(s.get(Y_VALUE + i).doubleValue() + separator);
                    }
                }
                bw.write("\n");
            }
        } else {
            serie = series[mouseSerieIndex];
            for (int i = seriesLimits.serieFirstPoint[mouseSerieIndex]; i < seriesLimits.serieLastPoint[mouseSerieIndex] - 4; i += 4) {
                bw.write(serie.get(X_VALUE + i).doubleValue() + separator);
                bw.write(serie.get(Y_VALUE + i).doubleValue() + separator);
                bw.write("\n");
            }
        }
        bw.close();
    }

    public final void exportDatFile(String filePath) throws IOException {
        exportDatFile(filePath, " ", -1);
    }

    public final void exportDatFile(String filePath, String separator) throws IOException {
        exportDatFile(filePath, separator, -1);
    }

    public void exportImage(String filePath, int areaWidth, int areaHeight) {
        // Adjust the dimensions to draw on image
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        BufferedImage rendImage = new BufferedImage((int) areaWidth, (int) areaHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = rendImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, (int) areaWidth, (int) areaHeight);
        chartWidth = areaWidth * 0.8d;
        chartHeight = areaHeight - 60;
        chartX = areaWidth * 0.15d;
        calculatePoints();
        drawChartArea(g2d);
        drawAxis(g2d);
        drawChart(g2d);
        // Back to the original dimensions to draw on this panel
        areaWidth = getWidth() - 1;
        chartWidth = areaWidth * 0.8d;
        areaHeight = getHeight() - 1;
        chartHeight = areaHeight - 60;
        chartX = areaWidth * 0.15d;
        chartY = 0;
        calculatePoints();
        g2d.dispose();
        try {
            File file = new File(filePath);
            ImageIO.write(rendImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportImage(String filePath) {
        exportImage(filePath, 600, 400);
    }

    public void setXLimits(double first, double last) {
        if (series == null) {
            throw new NullPointerException("You must set the series list before call this method!!!");
        }
        double xvf, xvl, xvs;
        List<Double> serie;
        for (int is = 0; is < series.length; is++) {
            serie = series[is];
            xvf = serie.get(0).doubleValue();
            xvl = serie.get(serie.size() - 4).doubleValue();
            seriesLimits.showSerie[is] = !(xvf > last || xvl < first);
            if (seriesLimits.showSerie[is]) {
                // Locate the first and last point inside each serie
                seriesLimits.serieFirstPoint[is] = Integer.MIN_VALUE;
                for (int ip = 0; ip < serie.size(); ip += 4) {
                    xvs = serie.get(X_VALUE + ip).doubleValue();
                    if (xvs >= first && xvs <= last) {
                        if (seriesLimits.serieFirstPoint[is] == Integer.MIN_VALUE) {
                            seriesLimits.serieFirstPoint[is] = ip;
                        }
                        seriesLimits.serieLastPoint[is] = ip;
                    }
                }
            }
        }
    }

    /**
     * @param mouseSerieIndex the mouseSerieIndex to set
     */
    public void setMouseSerieIndex(int mouseSerieIndex) {
        this.mouseSerieIndex = mouseSerieIndex;
    }

    /**
     * @return the enableXAxisZoom
     */
    public boolean isEnableXAxisZoom() {
        return enableXAxisZoom;
    }

    /**
     * @param enableXAxisZoom the enableXAxisZoom to set
     */
    public void setEnableXAxisZoom(boolean enableXAxisZoom) {
        this.enableXAxisZoom = enableXAxisZoom;
    }

    /**
     * @return the yAxisGapPercentage
     */
    public double getyAxisGapPercentage() {
        return yAxisGapPercentage;
    }

    /**
     * @param yAxisGapPercentage the yAxisGapPercentage to set
     */
    public void setyAxisGapPercentage(double yAxisGapPercentage) {
        this.yAxisGapPercentage = yAxisGapPercentage;
    }

    /**
     * @return the yAxisGapLocation
     */
    public int getyAxisGapLocation() {
        return yAxisGapLocation;
    }

    /**
     * @param yAxisGapLocation the yAxisGapLocation to set
     */
    public void setyAxisGapLocation(int yAxisGapLocation) {
        this.yAxisGapLocation = yAxisGapLocation;
    }

    /**
     * @return the enableMouseClickEvents
     */
    public boolean isEnableMouseClickEvents() {
        return enableMouseClickEvents;
    }

    /**
     * @param enableMouseClickEvents the enableMouseClickEvents to set
     */
    public void setEnableMouseClickEvents(boolean enableMouseClickEvents) {
        this.enableMouseClickEvents = enableMouseClickEvents;
    }

    /**
     * @param optionalXAxis the optionalXAxis to set
     */
    public void setOptionalXAxis(String[][] optionalXAxis) {
        this.optionalXAxis = optionalXAxis;
    }

    /**
     * @param horizontalDashedLineValue the horizontalDashedLineValue to set
     */
    public void setHorizontalDashedLineValue(double horizontalDashedLineValue) {
        this.horizontalDashedLineValue = horizontalDashedLineValue;
    }

    /**
     * @return the plotSquares
     */
    public boolean isPlotSquares() {
        return plotSquares;
    }

    /**
     * @param plotSquares the plotSquares to set
     */
    public void setPlotSquares(boolean plotSquares) {
        this.plotSquares = plotSquares;
    }

    /**
     * @return the largestX
     */
    public double getLargestX() {
        return largestX;
    }

    /**
     * @param largestX the largestX to set
     */
    public void setLargestX(double largestX) {
        this.largestX = largestX;
    }

    /**
     * @return the smallerX
     */
    public double getSmallerX() {
        return smallerX;
    }

    /**
     * @param smallerX the smallerX to set
     */
    public void setSmallerX(double smallerX) {
        this.smallerX = smallerX;
    }

    /**
     * @return the largestY
     */
    public double getLargestY() {
        return largestY;
    }

    /**
     * @param largestY the largestY to set
     */
    public void setLargestY(double largestY) {
        this.largestY = largestY;
    }

    /**
     * @return the smallerY
     */
    public double getSmallerY() {
        return smallerY;
    }

    /**
     * @param smallerY the smallerY to set
     */
    public void setSmallerY(double smallerY) {
        this.smallerY = smallerY;
    }

    /**
     * @param verticalDashedLineValue the verticalDashedLineValue to set
     */
    public void setVerticalDashedLineValue(double verticalDashedLineValue) {
        this.verticalDashedLineValue = verticalDashedLineValue;
    }

    /**
     * @return the verticalDashedLineValue
     */
    public double getVerticalDashedLineValue() {
        return verticalDashedLineValue;
    }

    /**
     * @return the invertedAbsciss
     */
    public boolean isInvertedAbsciss() {
        return invertedAbsciss;
    }

    /**
     * @param invertedAbsciss the invertedAbsciss to set
     */
    public void setInvertedAbsciss(boolean invertedAbsciss) {
        this.invertedAbsciss = invertedAbsciss;
    }

    /**
     * @return the invertedOrdinate
     */
    public boolean isInvertedOrdinate() {
        return invertedOrdinate;
    }

    /**
     * @param invertedOrdinate the invertedOrdinate to set
     */
    public void setInvertedOrdinate(boolean invertedOrdinate) {
        this.invertedOrdinate = invertedOrdinate;
    }

    // Inner class to control the first and last x values
    private class SeriesLimits {

        public boolean[] showSerie;
        public int[] serieFirstPoint;
        public int[] serieLastPoint;
    }
}
/**
 * $Log: Chart2DPanel.java,v $
 * Revision 1.21  2010-04-08 18:29:39  aryjr
 * 1.0.2 version, bugs when remove series.
 *
 * Revision 1.20  2010-03-23 15:20:26  aryjr
 * Correcao na termodinamica estatistica.
 *
 * Revision 1.19  2010-01-19 14:34:08  aryjr
 * N correcoes.
 *
 * Revision 1.18  2009-10-02 20:55:55  aryjr
 * Adaptacoes no novo grafico 2D para EPP do algoritmo genetico.
 *
 * Revision 1.17  2009-09-29 14:53:26  aryjr
 * Adaptacoes no novo grafico 2D para a estrutura de bandas.
 *
 * Revision 1.16  2009-09-28 21:28:35  aryjr
 * Adaptacoes no novo grafico 2D para a estrutura de bandas.
 *
 * Revision 1.15  2009-09-14 10:44:09  aryjr
 * Adaptacoes ao novo grafico 2D.
 *
 * Revision 1.14  2009-09-09 21:39:13  aryjr
 * Alteracoes no grafico 2D.
 *
 * Revision 1.13  2009-09-08 10:31:37  aryjr
 * Espectro de IR simulado pronto para ser testado.
 *
 * Revision 1.12  2009-09-04 21:03:47  aryjr
 * Implementando o espectro vibracional simulado.
 *
 * Revision 1.11  2009-08-28 21:25:13  aryjr
 * Teste para o grafico 2D e novas funcionalidades.
 *
 * Revision 1.10  2009-08-19 21:30:05  aryjr
 * Generalizando o grafico 2D.
 *
 * Revision 1.9  2009-08-18 21:16:06  aryjr
 * Generalizando o grafico 2D.
 *
 * Revision 1.8  2009-08-17 21:08:44  aryjr
 * Generalizando o grafico 2D do projeto GFQSI-Common.
 *
 * Revision 1.7  2009-01-06 17:02:09  aryjr
 * Trabalho em casa durante o final do ano.
 *
 * Revision 1.6  2008-12-15 12:06:50  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.5  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */
