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
package br.ufjf.quimica.gfqsi.common.chem;

/**
 *
 * A chemical elements.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: ChemistryElement.java,v 1.1 2009-01-06 17:02:09 aryjr Exp $
 *
 */
public class ChemistryElement implements Cloneable {

    public static final String CVS_REVISION = "$Revision: 1.1 $";
    private String name;
    private int atomicNumber;
    private String symbol;
    private double weight;

    public ChemistryElement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public ChemistryElement clone() {
        ChemistryElement clone = new ChemistryElement();
        clone.setName(new String(name.getBytes().clone()));
        clone.setAtomicNumber(atomicNumber);
        clone.setSymbol(new String(symbol.getBytes().clone()));
        clone.setWeight(weight);
        return clone;
    }

    @Override
    public String toString() {
        return "(" + symbol + ") " + name;
    }
}
/**
 * $Log: ChemistryElement.java,v $
 * Revision 1.1  2009-01-06 17:02:09  aryjr
 * Trabalho em casa durante o final do ano.
 *
 * Revision 1.6  2008-12-15 12:06:49  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.5  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */