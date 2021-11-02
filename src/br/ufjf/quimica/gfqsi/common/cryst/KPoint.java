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
package br.ufjf.quimica.gfqsi.common.cryst;

/**
 * 
 * An K Point abstraction.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: KPoint.java,v 1.6 2009-09-28 21:28:35 aryjr Exp $
 *
 */
public class KPoint {

    public static final String CVS_REVISION = "$Revision: 1.6 $";
    private int id;
    private double x,  y,  z;
    private double[] energyValues;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double[] getEnergyValues() {
        return energyValues;
    }

    public void setEnergyValues(double[] energyValues) {
        this.energyValues = energyValues;
    }

    public int countBands() {
        return energyValues.length;
    }
}
/**
 * $Log: KPoint.java,v $
 * Revision 1.6  2009-09-28 21:28:35  aryjr
 * Adaptacoes no novo grafico 2D para a estrutura de bandas.
 *
 * Revision 1.5  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:57  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */