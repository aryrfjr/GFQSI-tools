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

import br.ufjf.quimica.gfqsi.common.chem.Molecule;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: MoleculesComboBoxModel.java,v 1.5 2008-12-15 12:06:51 aryjr Exp $
 * 
 */
public class MoleculesComboBoxModel implements ComboBoxModel {
    
    public static final String CVS_REVISION = "$Revision: 1.5 $";
    private Molecule selected;
    private ArrayList<Molecule> molecules;

    public MoleculesComboBoxModel() {
    }

    public void removeListDataListener(ListDataListener l) {
    }

    public void addListDataListener(ListDataListener l) {
    }

    public Object getElementAt(int index) {
        return molecules.get(index).getName();
    }

    public Molecule getSelected() {
        return selected;
    }

    public Object getSelectedItem() {
        return selected == null ? "" : selected.getName();
    }

    public void setSelectedItem(Object item) {
        for (int inc = 0; inc < molecules.size(); inc++) {
            if (molecules.get(inc).getName().equals(item)) {
                selected = molecules.get(inc);
                break;
            }
        }
    }

    public int getSize() {
        return molecules == null ? 0 : molecules.size();
    }

    public ArrayList<Molecule> getAdsorbates() {
        if (molecules == null) {
            molecules = new ArrayList();
        }
        return molecules;
    }

    public void setMolecules(ArrayList<Molecule> molecules) {
        this.molecules = molecules;
    }
}
/**
 * $Log: MoleculesComboBoxModel.java,v $
 * Revision 1.5  2008-12-15 12:06:51  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */