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
package br.ufjf.quimica.gfqsi.common.thermodynamics;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Thermodynamics.java,v 1.11 2009-08-17 21:08:44 aryjr Exp $
 * 
 */
public class Thermodynamics {

    public static final String CVS_REVISION = "$Revision: 1.11 $";
    /**
     * J * K-1 * mol-1
     */
    public static double GAS_CONSTANT = 8.31447215;
    /**
     * mol-1
     */
    public static double AVOGADRO_CONSTANT = 6.0221415e23;
    /**
     * J * K-1
     */
    public static double BOLTZMANN_CONSTANT = GAS_CONSTANT / AVOGADRO_CONSTANT;
    /**
     * J * s
     */
    public static double PLANCK_CONSTANT = 6.626068e-34;
    /**
     * Reduced Planck constant (also known as Dirac's constant and pronounced "h-bar")
     */
    public static double DIRAC_CONSTANT = PLANCK_CONSTANT / (2d * Math.PI);
    /**
     * 1 Ry == 2.1798741e-18 J
     */
    public static double RYDBERG_CONSTANT = 2.1798741e-18;
    /**
     * 299 792 458 m / s
     * 3E10 cm / s
     */
    public static double LIGHT_SPEED = 299792458d;
    //public static double LIGHT_SPEED = 3e8;
    /**
     * kcal.mol^-1..C^-2
     */
    //public static double ELECTROSTATIC_CONSTANT = 332.1;
    public static double ELECTROSTATIC_CONSTANT = 1.29359e40;
    /**
     * C
     */
    public static double PROTON_CHARGE = 1.60217733e-19;
    
    public static void main(String[] args) {
        System.out.println(BOLTZMANN_CONSTANT);
    }
    
}
/**
 * $Log: Thermodynamics.java,v $
 * Revision 1.11  2009-08-17 21:08:44  aryjr
 * Generalizando o grafico 2D do projeto GFQSI-Common.
 *
 * Revision 1.10  2008-12-15 12:06:51  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.9  2008-10-09 19:10:00  aryjr
 * Geral.
 *
 * Revision 1.8  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */