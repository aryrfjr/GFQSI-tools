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
import br.ufjf.quimica.gfqsi.common.math.GeometricSite;
import java.util.ArrayList;

/**
 *
 * A 3D molecule abstraction.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Molecule.java,v 1.12 2010-02-05 09:28:34 aryjr Exp $
 * 
 */
public class Molecule implements Cloneable {

    public static final String CVS_REVISION = "$Revision: 1.12 $";
    private String name;
    private boolean linear = false;
    private ArrayList<AtomicSite> atomicSites = new ArrayList();

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

    public ArrayList<AtomicSite> getAtomicSites() {
        return atomicSites;
    }
    
    public int countAtomicSites() {
        return atomicSites.size();
    }

    public void setAtomicSites(ArrayList<AtomicSite> atomicSites) {
        this.atomicSites = atomicSites;
    }

    public void setAtomicSites(AtomicSite[] atomicSites) {
        this.atomicSites = new ArrayList();
        for (AtomicSite atomicSite : atomicSites) {
            this.atomicSites.add(atomicSite);
        }
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

    public double getTotalMass() {
        double totalMass = 0d;
        AtomicSite site;
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            site = atomicSites.get(inc);
            totalMass += site.getAtom().getElement().getWeight();
        }
        return totalMass;
    }

    public GeometricSite getCenterOfMass() {
        double sumRx = 0d, sumRy = 0d, sumRz = 0d;
        double totalMass = 0d;
        AtomicSite site;
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            site = atomicSites.get(inc);
            sumRx += site.getAtom().getElement().getWeight() * site.getX();
            sumRy += site.getAtom().getElement().getWeight() * site.getY();
            sumRz += site.getAtom().getElement().getWeight() * site.getZ();
            totalMass += site.getAtom().getElement().getWeight();
        }
        return new GeometricSite(sumRx / totalMass, sumRy / totalMass, sumRz / totalMass);
    }

    public double getMomentOfInertia() {
        double moi = 0d;
        AtomicSite site;
        GeometricSite cm = getCenterOfMass();
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            site = atomicSites.get(inc);
            moi += (site.getAtom().getElement().getWeight() * Math.pow(site.getDistanceFrom(cm), 2d));
        }
        return moi;
    }

    public double getMomentOfInertiaXX() {
        double moi = 0d;
        AtomicSite site;
        GeometricSite cm = getCenterOfMass();
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            site = atomicSites.get(inc);
            moi += (site.getAtom().getElement().getWeight() * (Math.pow(site.getY() - cm.getY(), 2d) + Math.pow(site.getZ() - cm.getZ(), 2d)));
        }
        return moi;
    }

    public double getMomentOfInertiaYY() {
        double moi = 0d;
        AtomicSite site;
        GeometricSite cm = getCenterOfMass();
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            site = atomicSites.get(inc);
            moi += (site.getAtom().getElement().getWeight() * (Math.pow(site.getX() - cm.getX(), 2d) + Math.pow(site.getZ() - cm.getZ(), 2d)));
        }
        return moi;
    }

    public double getMomentOfInertiaZZ() {
        double moi = 0d;
        AtomicSite site;
        GeometricSite cm = getCenterOfMass();
        for (int inc = 0; inc < atomicSites.size(); inc++) {
            site = atomicSites.get(inc);
            moi += (site.getAtom().getElement().getWeight() * (Math.pow(site.getX() - cm.getX(), 2d) + Math.pow(site.getY() - cm.getY(), 2d)));
        }
        return moi;
    }

    @Override
    public Object clone() {
        Molecule clone = new Molecule();
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
    }

    /**
     * @return the linear
     */
    public boolean isLinear() {
        return linear;
    }

    /**
     * @param linear the linear to set
     */
    public void setLinear(boolean linear) {
        this.linear = linear;
    }
}
/**
 * $Log: Molecule.java,v $
 * Revision 1.12  2010-02-05 09:28:34  aryjr
 * Termoquimica para moleculas agora tambem!!!
 *
 * Revision 1.11  2009-01-21 17:59:03  aryjr
 * Suporte a sitios geometricos nos adsorvatos (moleculas).
 *
 * Revision 1.10  2008-12-15 12:06:49  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.9  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */