/*
 *
 * The SST Project : A free Solid State Toolkit by Ary Junior and GFQSI-UFJF.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/sst
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
 *
 */
package br.ufjf.quimica.gfqsi.common.statistics;

import java.util.ArrayList;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: SampleDataSet.java,v 1.1 2009-01-06 17:02:09 aryjr Exp $
 *
 */
public class SampleDataSet {

    public static final String CVS_REVISION = "$Revision: 1.1 $";
    private ArrayList<Double> dataSet = new ArrayList();

    public void add(double value) {
        dataSet.add(new Double(value));
    }

    public double get(int ind) {
        return dataSet.get(ind).doubleValue();
    }

    public int getNunberOfValues() {
        return dataSet.size();
    }

    public double getArithmeticMean() {
        double mean = 0d;
        for (int inc = 0; inc < dataSet.size(); inc++) {
            mean += dataSet.get(inc).doubleValue();
        }
        return mean / dataSet.size();
    }

    public double getVariance() {
        double mean = getArithmeticMean();
        double var = 0d;
        for (int inc = 0; inc < dataSet.size(); inc++) {
            var += Math.pow(dataSet.get(inc).doubleValue() - mean, 2d);
        }
        return var / (dataSet.size() - 1);
    }

    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }
}
/**
 * $Log: SampleDataSet.java,v $
 * Revision 1.1  2009-01-06 17:02:09  aryjr
 * Trabalho em casa durante o final do ano.
 *
 *
 */