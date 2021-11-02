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
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: GeometricTransformation3D.java,v 1.6 2009-01-23 18:45:47 aryjr Exp $
 * 
 */
public class GeometricTransformation3D {
    
    public static final String CVS_REVISION = "$Revision: 1.6 $";

    /**
     * 
     * @param Tx
     * @param Ty
     * @param Tz
     * @return
     */
    public static Matrix getTranslationMatrix(double Tx, double Ty, double Tz) {
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, 1);
        m.setVAlue(0, 1, 0);
        m.setVAlue(0, 2, 0);
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, 0);
        m.setVAlue(1, 1, 1);
        m.setVAlue(1, 2, 0);
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, 0);
        m.setVAlue(2, 1, 0);
        m.setVAlue(2, 2, 1);
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, Tx);
        m.setVAlue(3, 1, Ty);
        m.setVAlue(3, 2, Tz);
        m.setVAlue(3, 3, 1);
        return m;
    }

    /**
     * 
     * @param angle
     * @return
     */
    public static Matrix getXRotationMatrix(double angle) {
        angle = Math.toRadians(angle);
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, 1);
        m.setVAlue(0, 1, 0);
        m.setVAlue(0, 2, 0);
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, 0);
        m.setVAlue(1, 1, Math.cos(angle));
        m.setVAlue(1, 2, Math.sin(angle));
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, 0);
        m.setVAlue(2, 1, -Math.sin(angle));
        m.setVAlue(2, 2, Math.cos(angle));
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, 0);
        m.setVAlue(3, 1, 0);
        m.setVAlue(3, 2, 0);
        m.setVAlue(3, 3, 1);
        return m;
    }

    /**
     * 
     * @param angle
     * @return
     */
    public static Matrix getYRotationMatrix(double angle) {
        angle = Math.toRadians(angle);
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, Math.cos(angle));
        m.setVAlue(0, 1, 0);
        m.setVAlue(0, 2, -Math.sin(angle));
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, 0);
        m.setVAlue(1, 1, 1);
        m.setVAlue(1, 2, 0);
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, Math.sin(angle));
        m.setVAlue(2, 1, 0);
        m.setVAlue(2, 2, Math.cos(angle));
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, 0);
        m.setVAlue(3, 1, 0);
        m.setVAlue(3, 2, 0);
        m.setVAlue(3, 3, 1);
        return m;
    }

    /**
     * 
     * @param angle
     * @return
     */
    public static Matrix getZRotationMatrix(double angle) {
        angle = Math.toRadians(angle);
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, Math.cos(angle));
        m.setVAlue(0, 1, Math.sin(angle));
        m.setVAlue(0, 2, 0);
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, -Math.sin(angle));
        m.setVAlue(1, 1, Math.cos(angle));
        m.setVAlue(1, 2, 0);
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, 0);
        m.setVAlue(2, 1, 0);
        m.setVAlue(2, 2, 1);
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, 0);
        m.setVAlue(3, 1, 0);
        m.setVAlue(3, 2, 0);
        m.setVAlue(3, 3, 1);
        return m;
    }

    public static Matrix getXZReflectionMatrix() {
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, 1);
        m.setVAlue(0, 1, 0);
        m.setVAlue(0, 2, 0);
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, 0);
        m.setVAlue(1, 1, -1);
        m.setVAlue(1, 2, 0);
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, 0);
        m.setVAlue(2, 1, 0);
        m.setVAlue(2, 2, 1);
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, 0);
        m.setVAlue(3, 1, 0);
        m.setVAlue(3, 2, 0);
        m.setVAlue(3, 3, 1);
        return m;
    }

    public static Matrix getXYReflectionMatrix() {
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, 1);
        m.setVAlue(0, 1, 0);
        m.setVAlue(0, 2, 0);
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, 0);
        m.setVAlue(1, 1, 1);
        m.setVAlue(1, 2, 0);
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, 0);
        m.setVAlue(2, 1, 0);
        m.setVAlue(2, 2, -1);
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, 0);
        m.setVAlue(3, 1, 0);
        m.setVAlue(3, 2, 0);
        m.setVAlue(3, 3, 1);
        return m;
    }

    public static Matrix getYZReflectionMatrix() {
        Matrix m = new Matrix(4, 4);
        m.setVAlue(0, 0, -1);
        m.setVAlue(0, 1, 0);
        m.setVAlue(0, 2, 0);
        m.setVAlue(0, 3, 0);
        m.setVAlue(1, 0, 0);
        m.setVAlue(1, 1, 1);
        m.setVAlue(1, 2, 0);
        m.setVAlue(1, 3, 0);
        m.setVAlue(2, 0, 0);
        m.setVAlue(2, 1, 0);
        m.setVAlue(2, 2, 1);
        m.setVAlue(2, 3, 0);
        m.setVAlue(3, 0, 0);
        m.setVAlue(3, 1, 0);
        m.setVAlue(3, 2, 0);
        m.setVAlue(3, 3, 1);
        return m;
    }

    /**
     * 
     * 
     * @param coord The cartesian coordinates nx3 matrix.
     * @return A 3D geometrix entity in a nx4 matrix.
     */
    public static Matrix getGeometricEntityCoordinates(double[][] coord) {
        double[][] matrix = new double[coord.length][4];
        for (int inc = 0; inc < matrix.length; inc++) {
            matrix[inc][0] = coord[inc][0];
            matrix[inc][1] = coord[inc][1];
            matrix[inc][2] = coord[inc][2];
            matrix[inc][3] = 1;
        }
        return new Matrix(matrix);
    }
}
/**
 * $Log: GeometricTransformation3D.java,v $
 * Revision 1.6  2009-01-23 18:45:47  aryjr
 * Terapia genetica ainda com uma pequena perda de precisao. Ver em AdsorbatePositionFitnessFunctionTest.java.
 *
 * Revision 1.5  2008-12-15 12:06:47  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */