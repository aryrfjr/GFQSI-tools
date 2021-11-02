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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * An atom in a crystal unit cell.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Atom.java,v 1.8 2009-01-26 12:03:07 aryjr Exp $
 *
 */
public class Atom implements Cloneable {
    
    public static final String CVS_REVISION = "$Revision: 1.8 $";
    private ChemistryElement element;
    private int charge;

    public Atom(String symbol) throws InvalidElementException {
        setElement(ChemistryElements.collectionMap.get(symbol));
        if (getElement() == null) {
            throw new InvalidElementException("A chemistry element with symbol '" + symbol + "' do not existis.");
        }
    }

    public String getSymbol() {
        return getElement().getSymbol();
    }

    public ChemistryElement getElement() {
        return element;
    }

    public void setElement(ChemistryElement element) {
        this.element = element;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public Atom clone() {
        try {
            Atom clone = new Atom(element.getSymbol());
            clone.setCharge(charge);
            return clone;
        } catch (InvalidElementException ex) {
            Logger.getLogger(Atom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
/**
 * $Log: Atom.java,v $
 * Revision 1.8  2009-01-26 12:03:07  aryjr
 * Corrigindo pequeno bug com os labels dos sitios geometricos.
 *
 * Revision 1.7  2009-01-06 17:02:09  aryjr
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