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

import br.ufjf.quimica.gfqsi.common.math.Matrix;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * A 3D atomic cluster abstraction.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: NanoCluster.java,v 1.1 2010-02-05 09:28:34 aryjr Exp $
 *
 */
public class NanoCluster implements Cloneable {

    public static final String CVS_REVISION = "$Revision: 1.1 $";
    private String name;
    private ArrayList<AtomicSite> atomicSites = new ArrayList();

    public NanoCluster() {

    }

    public NanoCluster(String plainText) {
        StringTokenizer stkl = new StringTokenizer(plainText, "\n");
        String tk;
        while (stkl.hasMoreTokens()) {
            tk = stkl.nextToken();
            if (tk.trim().equals("")) {
                break;
            }
            atomicSites.add(AtomicSite.siteFromStream(tk));
        }
    }

    public void addAtomicSite(AtomicSite site) {
        getAtomicSites().add(site);
    }

    public AtomicSite getAtomicSite(int index) {
        return getAtomicSites().get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AtomicSite> getAtomicSites(String element) {
        ArrayList<AtomicSite> filteredSites = new ArrayList();
        for (AtomicSite s: atomicSites) {
            if (s.getAtom().getSymbol().equals(element)) {
                filteredSites.add(s);
            }
        }
        return filteredSites;
    }

    public ArrayList<AtomicSite> getAtomicSites() {
        return atomicSites;
    }

    public int countAtomicSites() {
        return atomicSites.size();
    }

    public void setAtomicSites(ArrayList<AtomicSite> atomicSites) {
        this.atomicSites = atomicSites;
    }

    public Matrix getCoordinates() {
        Matrix coord = new Matrix(atomicSites.size(), 4);
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            coord.setVAlue(inc, 0, atomicSites.get(inc).getX());
            coord.setVAlue(inc, 1, atomicSites.get(inc).getY());
            coord.setVAlue(inc, 2, atomicSites.get(inc).getZ());
            coord.setVAlue(inc, 3, 1.0);
        }
        return coord;
    }

    public void setCoordinates(Matrix coord) {
        for (int inc = 0; inc < coord.countLines(); inc++) {
            atomicSites.get(inc).setX(coord.getValue(inc, 0));
            atomicSites.get(inc).setY(coord.getValue(inc, 1));
            atomicSites.get(inc).setZ(coord.getValue(inc, 2));
        }
    }

    @Override
    public Object clone() {
        NanoCluster clone = new NanoCluster();
        for (int i = 0; i < atomicSites.size(); i++) {
            clone.addAtomicSite((AtomicSite) atomicSites.get(i).clone());
        }
        return clone;
    }

    @Override
    public String toString() {
        String str = name == null ? "" : "name = " + name + "\n";
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            str += atomicSites.get(inc).getAtomicSymbols() + " " + atomicSites.get(inc).getX() + " " + atomicSites.get(inc).getY() + " " + atomicSites.get(inc).getZ() + "\n";
        }
        return str.substring(0, str.length() - 1);
        //return str;
    }
}
