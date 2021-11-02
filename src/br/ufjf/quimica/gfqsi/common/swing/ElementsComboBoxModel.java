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
package br.ufjf.quimica.gfqsi.common.swing;

import br.ufjf.quimica.gfqsi.common.chem.ChemistryElement;
import br.ufjf.quimica.gfqsi.common.chem.ChemistryElements;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: ElementsComboBoxModel.java,v 1.1 2010-02-05 09:28:34 aryjr Exp $
 * 
 */
public class ElementsComboBoxModel implements ComboBoxModel {
    
    public static final String CVS_REVISION = "$Revision: 1.1 $";
    private ChemistryElement selected;

    public ElementsComboBoxModel() {
    }

    public void removeListDataListener(ListDataListener l) {
    }

    public void addListDataListener(ListDataListener l) {
    }

    public Object getElementAt(int index) {
        return ChemistryElements.collectionList.get(index).getName() + " ( " + ChemistryElements.collectionList.get(index).getSymbol() + " )";
    }

    public ChemistryElement getSelected() {
        return selected;
    }

    public Object getSelectedItem() {
        return selected == null ? "" : selected.getName() + " ( " + selected.getSymbol() + " )";
    }

    public void setSelectedItem(Object item) {
        String s;
        for (int inc = 0; inc < ChemistryElements.collectionList.size(); inc++) {
            s = ChemistryElements.collectionList.get(inc).getName() + " ( " + ChemistryElements.collectionList.get(inc).getSymbol() + " )";
            if (s.equals(item)) {
                selected = ChemistryElements.collectionList.get(inc);
                break;
            }
        }
    }

    public int getSize() {
        return ChemistryElements.collectionList == null ? 0 : ChemistryElements.collectionList.size();
    }

    public ArrayList<ChemistryElement> getElements() {
        return ChemistryElements.collectionList;
    }

}
/**
 * $Log: ElementsComboBoxModel.java,v $
 * Revision 1.1  2010-02-05 09:28:34  aryjr
 * Termoquimica para moleculas agora tambem!!!
 *
 *
 */