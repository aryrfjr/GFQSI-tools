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
 * A 3D vector.<br><br>
 * 
 * http://ressim.berlios.de/<br>
 * https://vecmath.dev.java.net/<br>
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Vector3D.java,v 1.5 2008-12-15 12:06:47 aryjr Exp $
 * 
 */
public class Vector3D implements Cloneable {
    
    public static final String CVS_REVISION = "$Revision: 1.5 $";
    public static Vector3D i = new Vector3D(1d, 0d, 0d);
    public static Vector3D j = new Vector3D(0d, 1d, 0d);
    public static Vector3D k = new Vector3D(0d, 0d, 1d);
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * 
     * The directional angle, or the angle between this vector and i vector.
     * 
     * @return
     */
    public double getDirectionAlpha() {
        return Vector3D.getAngleBetween(this, i);
    }

    /**
     * 
     * The directional angle, or the angle between this vector and j vector.
     * 
     * @return
     */
    public double getDirectionBeta() {
        return Vector3D.getAngleBetween(this, j);
    }

    /**
     * 
     * The directional angle, or the angle between this vector and k vector.
     * 
     * @return
     */
    public double getDirectionGamma() {
        return Vector3D.getAngleBetween(this, k);
    }

    /**
     * 
     * The length or magnitude or norm of the vector a is denoted by ||a|| or, less commonly, |a|, which is not to be confused with the absolute value (a scalar "norm").
     * 
     * @return
     */
    public double getNorm() {
        return Math.sqrt(Math.pow(x, 2d) + Math.pow(y, 2d) + Math.pow(z, 2d));
    }

    public boolean isOrthogonal(Vector3D aVector) {
        // In 2- or 3-dimensional Euclidean space, two vectors are orthogonal if their dot product is zero
        return Vector3D.getDotProduct(this, aVector) == 0d;
    }

    public boolean isParalel(Vector3D aVector) {
        // The cross product of parallel vectors is the null vector
        return Vector3D.getCrossProduct(this, aVector).isNull();
    }

    public boolean isNull() {
        return x == y && y == z && z == 0d;
    }

    public static double getAngleBetween(Vector3D v1, Vector3D v2) {
        double angv = Vector3D.getDotProduct(v1, v2) / (v1.getNorm() * v2.getNorm());
        return (Math.acos(angv) * 180d) / Math.PI;
    }

    /**
     * 
     * The dot product of two vectors a and b (sometimes called the inner product, or, since its result is a scalar, the scalar product) is denoted by a ? b.
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static double getDotProduct(Vector3D v1, Vector3D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    /**
     * The cross product (also called the vector product or outer product) differs from the dot product primarily in that the result of the cross product of two vectors is a vector.<br>
     * The cross product, denoted a x b, is a vector perpendicular to both a and b.
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static Vector3D getCrossProduct(Vector3D v1, Vector3D v2) {
        Vector3D newV = new Vector3D(0d, 0d, 0d);
        // By Sarrus' rule for two 3D vectors
        newV.setX(v1.getY() * v2.getZ() - v1.getZ() * v2.getY());
        newV.setY(v1.getZ() * v2.getX() - v1.getX() * v2.getZ());
        newV.setZ(v1.getX() * v2.getY() - v1.getY() * v2.getX());
        return newV;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    public static void main(String[] args) {
        Vector3D vm = Vector3D.getCrossProduct(i, j);
        Vector3D vml = new Vector3D(2, 3, 2.5);
        System.out.println(Vector3D.getAngleBetween(vml, vm));
        System.out.println(Vector3D.getAngleBetween(vml, k));
    }

    @Override
    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }
}
/**
 * $Log: Vector3D.java,v $
 * Revision 1.5  2008-12-15 12:06:47  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */