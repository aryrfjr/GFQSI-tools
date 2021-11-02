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

import br.ufjf.quimica.gfqsi.common.chem.InvalidElementException;
import br.ufjf.quimica.gfqsi.common.chem.AtomicSite;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: AtomicSitesTableModel.java,v 1.8 2009-01-21 17:59:04 aryjr Exp $
 *
 */
public class AtomicSitesTableModel extends AbstractTableModel {

    public static final String CVS_REVISION = "$Revision: 1.8 $";
    private ArrayList<AtomicSite> sites = new ArrayList();
    private JDialog frame;

    public void setJDialog(JDialog frame) {
        this.frame = frame;
    }

    public ArrayList<AtomicSite> getAll() {
        return sites;
    }

    @Override
    public String getColumnName(int colIdx) {
        switch (colIdx) {
            case 0:
                return "Atomic symbol";
            case 1:
                return "X coordinate";
            case 2:
                return "Y coordinate";
            case 3:
                return "Z coordinate";
            case 4:
                return "Relax";
            default:
                return "";
        }
    }

    public int getColumnCount() {
        return 5;
    }

    public int getRowCount() {
        return sites.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return sites.get(rowIndex).getAtomicSymbols();
            case 1:
                return sites.get(rowIndex).getX();
            case 2:
                return sites.get(rowIndex).getY();
            case 3:
                return sites.get(rowIndex).getZ();
            case 4:
                return new Boolean(sites.get(rowIndex).isRelax());
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // All cells are editable
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            AtomicSite site = sites.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    site.setAtoms((String) aValue);
                    break;
                case 1:
                    site.setX((Double) aValue);
                    break;
                case 2:
                    site.setY((Double) aValue);
                    break;
                case 3:
                    site.setZ((Double) aValue);
                    break;
                case 4:
                    site.setRelax(((Boolean) aValue).booleanValue());
                    break;
            }
        } catch (NumberFormatException nfex) {
            Logger.getLogger(AtomicSitesTableModel.class.getName()).log(Level.SEVERE, null, nfex);
            JOptionPane.showMessageDialog(frame, "Please, only numeric values on site coordinates.");
        } catch (InvalidElementException ieex) {
            Logger.getLogger(AtomicSitesTableModel.class.getName()).log(Level.SEVERE, null, ieex);
            JOptionPane.showMessageDialog(frame, ieex.getMessage());
        }
    }

    public void addRow(AtomicSite site) {
        sites.add(site);
    }

    public void addRows(ArrayList<AtomicSite> sites) {
        this.sites.addAll(sites);
    }

    public void removeRow(int index) {
        sites.remove(index);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void clear() {
//        sites.clear();
        sites = new ArrayList();
    }
}
/**
 * $Log: AtomicSitesTableModel.java,v $
 * Revision 1.8  2009-01-21 17:59:04  aryjr
 * Suporte a sitios geometricos nos adsorvatos (moleculas).
 *
 * Revision 1.7  2008-12-15 12:06:51  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.6  2008-10-31 16:38:34  aryjr
 * Bug com tabela de sitios atomicos.
 *
 * Revision 1.5  2008-10-29 12:23:38  aryjr
 * Suporte a relaxacao de coordenadas dos atomos e terapia genetica.
 *
 * Revision 1.4  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */