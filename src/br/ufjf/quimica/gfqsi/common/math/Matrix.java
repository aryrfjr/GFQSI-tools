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
package br.ufjf.quimica.gfqsi.common.math;

/**
 *
 * An nxm matrix.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Matrix.java,v 1.10 2010-01-19 14:34:08 aryjr Exp $
 * 
 */
public class Matrix implements Cloneable {

    public static final String CVS_REVISION = "$Revision: 1.10 $";
    private double[][] values;

    public Matrix(int lines, int columns) {
        values = new double[lines][columns];
    }

    public Matrix(double[][] values) {
        this.values = values;
    }

    public Matrix(int lines, int columns, Matrix another) throws MatrixBoundsException {
        if (lines < another.countLines()) {
            throw new MatrixBoundsException("The argument lines must be lower than the another matrix lines.");
        }
        if (columns < another.countColumns()) {
            throw new MatrixBoundsException("The argument columns must be lower than the another matrix columns.");
        }
        values = new double[lines][columns];
        for (int il = 0; il < another.countLines(); il++) {
            for (int ic = 0; ic < another.countColumns(); ic++) {
                values[il][ic] = another.getValue(il, ic);
            }
        }
    }

    /**
     * 
     * In mathematics, matrix multiplication is the operation of multiplying a matrix with either a scalar or another matrix.<br>
     * In this case m1 x m2.
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static double[][] matricialProduct(Matrix m1, Matrix m2) throws MatrixMultiplicationException {
        if (m1.countColumns() != m2.countLines()) {
            throw new MatrixMultiplicationException("The number of m1 columns must be equal to the number of m2 lines");
        }
        double[][] mv1 = m1.getValues();
        double[][] mv2 = m2.getValues();
        double[][] result = new double[m1.countLines()][m2.countColumns()];
        for (int inc = 0; inc < m1.countLines(); inc++) {
            for (int i = 0; i < m2.countColumns(); i++) {
                for (int j = 0; j < m1.countColumns(); j++) {
                    result[inc][i] += mv1[inc][j] * mv2[j][i];
                }
            }
        }
        return result;
    }

    public Matrix getTranspose() {
        Matrix trans = new Matrix(countColumns(), countLines());
        for (int inl = 0; inl < trans.countLines(); inl++) {
            for (int inc = 0; inc < trans.countColumns(); inc++) {
                trans.setVAlue(inl, inc, values[inc][inl]);
            }
        }
        return trans;
    }

    public double[][] getValues() {
        return values;
    }

    public void setValues(double[][] values) {
        this.values = values;
    }

    public void setVAlue(int line, int column, double value) {
        values[line][column] = value;
    }

    public double getValue(int line, int column) {
        return values[line][column];
    }

    public int countLines() {
        return values.length;
    }

    public int countColumns() {
        return values[0].length;
    }

    @Override
    public String toString() {
        String str = "";
        for (int incl = 0; incl < countLines(); incl++) {
            for (int incc = 0; incc < countColumns(); incc++) {
                str += values[incl][incc] + " ";
            }
            if (incl < countLines() - 1) {
                str += "\n";
            }
        }
        return str;
    }

    @Override
    public Matrix clone() {
        double[][] newValues = new double[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                newValues[i][j] = values[i][j];
            }
        }
        return new Matrix(newValues);
    }
}
/**
 * $Log: Matrix.java,v $
 * Revision 1.10  2010-01-19 14:34:08  aryjr
 * N correcoes.
 *
 * Revision 1.9  2008-12-15 12:06:47  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.8  2008-11-14 17:10:10  aryjr
 * Dando uma geral no simetrizador.
 *
 * Revision 1.7  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */