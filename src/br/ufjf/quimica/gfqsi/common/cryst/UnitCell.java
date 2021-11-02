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

import br.ufjf.quimica.gfqsi.common.chem.AtomicSite;
import br.ufjf.quimica.gfqsi.common.chem.InvalidElementException;
import br.ufjf.quimica.gfqsi.common.math.GeometricSite;
import br.ufjf.quimica.gfqsi.common.math.GeometricTransformation3D;
import br.ufjf.quimica.gfqsi.common.math.Matrix;
import br.ufjf.quimica.gfqsi.common.math.MatrixBoundsException;
import br.ufjf.quimica.gfqsi.common.math.MatrixMultiplicationException;
import br.ufjf.quimica.gfqsi.common.math.Vector3D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * An unit cell abstraction.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: UnitCell.java,v 1.26 2010-03-23 15:20:26 aryjr Exp $
 *
 */
public class UnitCell implements Cloneable {

    public static final String CVS_REVISION = "$Revision: 1.26 $";
    public static String INTERNAL_COORDINATES = "INTERNAL";
    public static String CARTESIAN_COORDINATES = "CARTESIAN";
    public static String BOHR_SCALE = "BOHR";
    public static String ANGSTROM_SCALE = "ANGSTROM";
    public static String LATTICE_FROM_RPRIM = "LATTICE_FROM_RPRIM";// On the first setRprim
    public static String LATTICE_FROM_PARAM = "LATTICE_FROM_PARAM";// On the first setA
    private String coordType = CARTESIAN_COORDINATES;
    private String coordScale = ANGSTROM_SCALE;
    private AsymmetricUnit asymmetricUnit;
    private ArrayList<AtomicSite> sitesCollection;
    private HashMap<String, Integer> speciesCount = new HashMap();
    private double a;// The a vector norm
    private double b;// The b vector norm
    private double c;// The c vector norm
    private double alpha;// The angle between b and c vectors
    private double beta;// The angle between a and c vectors
    private double gamma;// The angle between a and b vectors
    private Matrix rprim;// The component vectors matrix
    private Matrix xcart;// The cartesian coordinates matrix
    private String latticeData = "";

    /**
     * 
     * Build the unit cell based on asymmetric unit.
     * 
     */
    public void build() {
        if (asymmetricUnit == null) {
            return;
        }
        HashMap<String, AtomicSite> hashSites = GeometryBuilder.build(this);
        // sitesCollection is initialized here if the cell have an asymmetric unit
        sitesCollection = new ArrayList(hashSites.values());
        asymmetricUnit.setSitesPerFullUC(hashSites.size());
    }

    /**
     * 
     * Return a new collection of atomic sites for a supercell.
     * 
     * @param x
     * @param y
     * @param z
     * @return
     */
    public ArrayList<AtomicSite> getPropagatedCell(int x, int y, int z) throws InvalidElementException, MatrixMultiplicationException, MatrixBoundsException {
        ArrayList<AtomicSite> newSites = new ArrayList();
        AtomicSite site;
        // get the original atoms and add it to the array with the new sites
        Matrix xcart = new Matrix(countGeometricSites(), 4, getCartesianCoordinates());
        for (int inc = 0; inc < xcart.countLines(); inc++) {
            site = new AtomicSite(sitesCollection.get(inc).getAtomicSymbols());
            site.setRelax(sitesCollection.get(inc).isRelax());
            site.setX(xcart.getValue(inc, 0));
            site.setY(xcart.getValue(inc, 1));
            site.setZ(xcart.getValue(inc, 2));
            xcart.setVAlue(inc, 3, 1d);
            newSites.add(site);
        }
        // Now propagate in the A vector direction with a translation matrix
        Matrix mTrans, nXcart;
        Vector3D av = getVectorA();
        int isite = 0;// To get the atomic symbol
        for (int ix = 1; ix < x; ix++) {
            // set the translation matrix
            mTrans = GeometricTransformation3D.getTranslationMatrix(av.getX() * ix, av.getY() * ix, av.getZ() * ix);
            nXcart = new Matrix(Matrix.matricialProduct(xcart, mTrans));
            for (int inc = 0; inc < nXcart.countLines(); inc++) {
                site = new AtomicSite(sitesCollection.get(isite).getAtomicSymbols());
                site.setRelax(sitesCollection.get(isite++).isRelax());
                site.setX(nXcart.getValue(inc, 0));
                site.setY(nXcart.getValue(inc, 1));
                site.setZ(nXcart.getValue(inc, 2));
                newSites.add(site);
                if (isite == sitesCollection.size()) {
                    isite = 0;
                }
            }
        }
        // Update the xcart matrix
        xcart = new Matrix(newSites.size(), 4);
        for (int inc = 0; inc < newSites.size(); inc++) {
            site = newSites.get(inc);
            xcart.setVAlue(inc, 0, site.getX());
            xcart.setVAlue(inc, 1, site.getY());
            xcart.setVAlue(inc, 2, site.getZ());
            xcart.setVAlue(inc, 3, 1d);
        }
        // Now propagate in the B vector direction with a translation matrix
        Vector3D bv = getVectorB();
        for (int iy = 1; iy < y; iy++) {
            // set the translation matrix
            mTrans = GeometricTransformation3D.getTranslationMatrix(bv.getX() * iy, bv.getY() * iy, bv.getZ() * iy);
            nXcart = new Matrix(Matrix.matricialProduct(xcart, mTrans));
            for (int inc = 0; inc < nXcart.countLines(); inc++) {
                site = new AtomicSite(sitesCollection.get(isite).getAtomicSymbols());
                site.setRelax(sitesCollection.get(isite++).isRelax());
                site.setX(nXcart.getValue(inc, 0));
                site.setY(nXcart.getValue(inc, 1));
                site.setZ(nXcart.getValue(inc, 2));
                newSites.add(site);
                if (isite == sitesCollection.size()) {
                    isite = 0;
                }
            }
        }
        // Update the xcart matrix again
        xcart = new Matrix(newSites.size(), 4);
        for (int inc = 0; inc < newSites.size(); inc++) {
            site = newSites.get(inc);
            xcart.setVAlue(inc, 0, site.getX());
            xcart.setVAlue(inc, 1, site.getY());
            xcart.setVAlue(inc, 2, site.getZ());
            xcart.setVAlue(inc, 3, 1d);
        }
        // Now propagate in the C vector direction with a translation matrix
        Vector3D cv = getVectorC();
        for (int iz = 1; iz < z; iz++) {
            // set the translation matrix
            mTrans = GeometricTransformation3D.getTranslationMatrix(cv.getX() * iz, cv.getY() * iz, cv.getZ() * iz);
            nXcart = new Matrix(Matrix.matricialProduct(xcart, mTrans));
            for (int inc = 0; inc < nXcart.countLines(); inc++) {
                site = new AtomicSite(sitesCollection.get(isite).getAtomicSymbols());
                site.setRelax(sitesCollection.get(isite++).isRelax());
                site.setX(nXcart.getValue(inc, 0));
                site.setY(nXcart.getValue(inc, 1));
                site.setZ(nXcart.getValue(inc, 2));
                newSites.add(site);
                if (isite == sitesCollection.size()) {
                    isite = 0;
                }
            }
        }
        return newSites;
    }

    public void countSpecies() {
        getSpeciesCount().clear();
        AtomicSite site;
        for (int inc = 0; inc < getSites().size(); inc++) {
            site = getSites().get(inc);
            if (getSpeciesCount().containsKey(site.getAtomicSymbols())) {
                getSpeciesCount().put(site.getAtomicSymbols(), getSpeciesCount().get(site.getAtomicSymbols()).intValue() + 1);
            } else {
                getSpeciesCount().put(site.getAtomicSymbols(), new Integer(1));
            }
        }
    }

    public void clearSites() {
        getSites().clear();
    }

    public void addSite(AtomicSite site) {
        getSites().add(site);
    }

    public void removeSite(int index) {
        getSites().remove(index);
    }

    /**
     * Check if is there any pair of atoms with distance under the parameter. 
     * 
     * @param distance
     * @return
     */
    public boolean checkPBCMinimalDistance(double distance) {
        GeometricSite siteA, siteB;
        ArrayList<AtomicSite> sites = getAsymmetricUnit() == null ? sitesCollection : getSitesInCartesianCoordinates();
        double pbcDistance;
        for (int i = 0; i < sites.size(); i++) {
            siteA = sites.get(i);
            for (int j = i + 1; j < sites.size(); j++) {
                siteB = sites.get(j);
                pbcDistance = getPBCDistance(siteA, siteB);
                if (!siteA.getLabel().equals(AtomicSite.POINT_LABEL) && !siteB.getLabel().equals(AtomicSite.POINT_LABEL) && pbcDistance <= distance) {
                    System.out.println("Unacceptable " + pbcDistance + " angstrom distance between the atomic sites:");
                    System.out.println(siteA.toString());
                    System.out.println(siteB.toString());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Only for non asymmetric unit and cartesian coordinates.
     * 
     * @param distance
     * @return
     */
    public int countPBCNeighbors(int siteID, double minimalDistance) {
        GeometricSite siteA, siteB;
        int neighbors = 0;
        siteA = sitesCollection.get(siteID);
        for (int inc = 0; inc < sitesCollection.size(); inc++) {
            siteB = sitesCollection.get(inc);
            if (siteID != inc && getPBCDistance(siteA, siteB) <= minimalDistance && siteB.getLabel().equals("O")) {
                neighbors++;
            }
        }
        return neighbors;
    }

    /**
     *
     * Get the periodic boundary conditions distance between two atomic sites
     *
     * @param i
     * @param j
     */
    public double getPBCDistance(int i, int j) {
        return getPBCDistance(getAtomicSite(i), getAtomicSite(j));
    }

    private double getPBCDistance(GeometricSite siteA, GeometricSite siteB) {
        double dx = Math.abs(siteA.getX() - siteB.getX());
        double lx = Math.abs(getVectorA().getX() + getVectorB().getX() + getVectorC().getX());
        if (dx > lx / 2) {
            dx = dx - lx;
        }
        double dy = Math.abs(siteA.getY() - siteB.getY());
        double ly = Math.abs(getVectorA().getY() + getVectorB().getY() + getVectorC().getY());
        if (dy > ly / 2) {
            dy = dy - ly;
        }
        double dz = Math.abs(siteA.getZ() - siteB.getZ());
        double lz = Math.abs(getVectorA().getZ() + getVectorB().getZ() + getVectorC().getZ());
        if (dz > lz / 2) {
            dz = dz - lz;
        }
        return Math.sqrt(Math.pow(dx, 2d) + Math.pow(dy, 2d) + Math.pow(dz, 2d));
    }

    /**
     *
     * Get the distance between two atomic sites
     *
     * @param i
     * @param j
     */
    public double getDistance(int i, int j) {
        return getDistance(getAtomicSite(i), getAtomicSite(j));
    }

    private double getDistance(GeometricSite siteA, GeometricSite siteB) {
        double dx = siteA.getX() - siteB.getX();
        double dy = siteA.getY() - siteB.getY();
        double dz = siteA.getZ() - siteB.getZ();
        return Math.sqrt(Math.pow(dx, 2d) + Math.pow(dy, 2d) + Math.pow(dz, 2d));
    }

    /**
     * 
     * Return a nx3 matrix with cartesian coordinates.<br>
     * Xcart is the matrix:<br>
     * | X1 Y1 Z1 |<br>
     * | X2 Y2 Z2 |<br>
     * | X3 Y3 Z3 |<br>
     * ...<br>
     * 
     * @return
     */
    public Matrix getCartesianCoordinates() {
        AtomicSite aSite;
        xcart = new Matrix(sitesCollection.size(), 3);
        for (int inc = 0; inc < sitesCollection.size(); inc++) {
            aSite = sitesCollection.get(inc);
            xcart.setVAlue(inc, 0, aSite.getX());
            xcart.setVAlue(inc, 1, aSite.getY());
            xcart.setVAlue(inc, 2, aSite.getZ());
        }
        if (coordType.equals(UnitCell.INTERNAL_COORDINATES)) {
            try {
                return new Matrix(Matrix.matricialProduct(xcart, rprim));
            } catch (MatrixMultiplicationException ex) {
                Logger.getLogger(UnitCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return xcart;
    }

    public void setCartesianCoordinates(Matrix xcart) {
        this.xcart = xcart;
        if (asymmetricUnit != null) {
            // @todo setCartesianCoordinates with asymmetric unit
        } else {
            if (!coordType.equals(UnitCell.INTERNAL_COORDINATES)) {
                for (int inc = 0; inc < xcart.countLines(); inc++) {
                    sitesCollection.get(inc).setX(xcart.getValue(inc, 0));
                    sitesCollection.get(inc).setY(xcart.getValue(inc, 1));
                    sitesCollection.get(inc).setZ(xcart.getValue(inc, 2));
                }
            }
        }
    }

    public int countAtomicSites() {
        int count = 0;
        for (int inc = 0; inc < getSites().size(); inc++) {
            if (!getSites().get(inc).getAtomicSymbols().equals(AtomicSite.POINT_LABEL)) {
                count++;
            }
        }
        return count;
    }

    public int countGeometricSites() {
        return getSites().size();
    }

    public int countSpeciesTypes() {
        return getSpeciesCount().size();
    }

    public AtomicSite getAtomicSite(int index) {
        return getSites().get(index);
    }

    public HashMap<String, Integer> getSpeciesCount() {
        return speciesCount;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public AsymmetricUnit getAsymmetricUnit() {
        return asymmetricUnit;
    }

    public void setAsymmetricUnit(AsymmetricUnit asymmetricUnit) {
        this.asymmetricUnit = asymmetricUnit;
    }

    public String getCoordType() {
        return coordType;
    }

    public void setCoordType(String coordType) {
        this.coordType = coordType;
    }

    public ArrayList<AtomicSite> getSites() {
        if (sitesCollection == null) {
            sitesCollection = new ArrayList();
        }
        return sitesCollection;
    }

    public ArrayList<AtomicSite> getSites(String element) {
        if (sitesCollection == null) {
            sitesCollection = new ArrayList();
            return new ArrayList();
        } else {
            ArrayList<AtomicSite> filteredSites = new ArrayList();
            for (AtomicSite s: sitesCollection) {
                if (s.getAtom().getSymbol().equals(element)) {
                    filteredSites.add(s);
                }
            }
            return filteredSites;
        }
    }

    /**
     * 
     * 
     * @return
     */
    public ArrayList<AtomicSite> getSitesInCartesianCoordinates() {
        ArrayList<AtomicSite> xcartSitesCollection = new ArrayList();
        AtomicSite site;
        Matrix mXcart = getCartesianCoordinates();
        for (int inc = 0; inc < mXcart.countLines(); inc++) {
            site = sitesCollection.get(inc).clone();
            site.setX(mXcart.getValue(inc, 0));
            site.setY(mXcart.getValue(inc, 1));
            site.setZ(mXcart.getValue(inc, 2));
            xcartSitesCollection.add(site);
        }
        return xcartSitesCollection;
    }

    public void setSites(ArrayList<AtomicSite> sitesCollection) {
        this.sitesCollection = sitesCollection;
    }

    public String getCoordScale() {
        return coordScale;
    }

    public void setCoordScale(String coordScale) {
        this.coordScale = coordScale;
    }

    public Matrix getUnitRprim() {
        Matrix unitRprim = new Matrix(3, 3);
        unitRprim.setVAlue(0, 0, rprim.getValue(0, 0) / getVectorA().getNorm());
        unitRprim.setVAlue(0, 1, rprim.getValue(0, 1) / getVectorA().getNorm());
        unitRprim.setVAlue(0, 2, rprim.getValue(0, 2) / getVectorA().getNorm());
        unitRprim.setVAlue(1, 0, rprim.getValue(1, 0) / getVectorB().getNorm());
        unitRprim.setVAlue(1, 1, rprim.getValue(1, 1) / getVectorB().getNorm());
        unitRprim.setVAlue(1, 2, rprim.getValue(1, 2) / getVectorB().getNorm());
        unitRprim.setVAlue(2, 0, rprim.getValue(2, 0) / getVectorC().getNorm());
        unitRprim.setVAlue(2, 1, rprim.getValue(2, 1) / getVectorC().getNorm());
        unitRprim.setVAlue(2, 2, rprim.getValue(2, 2) / getVectorC().getNorm());
        return unitRprim;
    }

    /**
     * 
     * Rprim is the matrix:<br>
     * | Xa Ya Za |<br>
     * | Xb Yb Zb |<br>
     * | Xc Yc Zc |<br>
     * 
     * @return
     */
    public Matrix getRprim() {
        return rprim;
    }

    public void setRprim(Matrix rprim) {
        this.rprim = rprim;
    }

    /**
     * calculate the matricial product for the transformation matrix frprim[3][3] 
     * for this cell with a = b = c 1.01 and the acell.
     * From abinit source code: /opt/abinit-5.3.4/src/13iovars/ingeo.F90
     * 
     * @return
     */
    public void calculateRprim() {
        // One or more vectors will be paralel to i, inc or k
        Vector3D va = new Vector3D(a, 0d, 0d); // paralel to i
        double xb = b * Math.cos(Math.toRadians(gamma));
        double yb = Math.sqrt(Math.pow(b, 2d) - Math.pow(xb, 2d));
        Vector3D vb = new Vector3D(xb, yb, 0d); // on xy plan
        double xc = c * Math.cos(Math.toRadians(beta));
        double yc = ((b * Math.cos(Math.toRadians(alpha))) - (xb * xc)) / yb;
        double zc = Math.sqrt(Math.pow(c, 2d) - Math.pow(xc, 2d) - Math.pow(yc, 2d));
        Vector3D vc = new Vector3D(xc, yc, zc); // on xyz space
        // The rprim matrix
        Matrix nRprim = new Matrix(3, 3);
        nRprim.setVAlue(0, 0, va.getX());
        nRprim.setVAlue(0, 1, va.getY());
        nRprim.setVAlue(0, 2, va.getZ());
        nRprim.setVAlue(1, 0, vb.getX());
        nRprim.setVAlue(1, 1, vb.getY());
        nRprim.setVAlue(1, 2, vb.getZ());
        nRprim.setVAlue(2, 0, vc.getX());
        nRprim.setVAlue(2, 1, vc.getY());
        nRprim.setVAlue(2, 2, vc.getZ());
        setRprim(nRprim);
    }

    /**
     * calculate the lattice parameters based on rprim.
     * 
     * @return
     */
    public void calculateLatticeParameters() {
        Vector3D va = new Vector3D(rprim.getValue(0, 0), rprim.getValue(0, 1), rprim.getValue(0, 2));
        setA(va.getNorm());
        Vector3D vb = new Vector3D(rprim.getValue(1, 0), rprim.getValue(1, 1), rprim.getValue(1, 2));
        setB(vb.getNorm());
        Vector3D vc = new Vector3D(rprim.getValue(2, 0), rprim.getValue(2, 1), rprim.getValue(2, 2));
        setC(vc.getNorm());
        setAlpha(Vector3D.getAngleBetween(vb, vc));
        setBeta(Vector3D.getAngleBetween(va, vc));
        setGamma(Vector3D.getAngleBetween(va, vb));
    }

    public Vector3D getVectorA() {
        return new Vector3D(rprim.getValue(0, 0), rprim.getValue(0, 1), rprim.getValue(0, 2));
    }

    public Vector3D getVectorB() {
        return new Vector3D(rprim.getValue(1, 0), rprim.getValue(1, 1), rprim.getValue(1, 2));
    }

    public Vector3D getVectorC() {
        return new Vector3D(rprim.getValue(2, 0), rprim.getValue(2, 1), rprim.getValue(2, 2));
    }

    public String getLatticeData() {
        return latticeData;
    }

    public void setLatticeData(String latticeData) {
        this.latticeData = latticeData;
    }

    @Override
    public UnitCell clone() {
        UnitCell uc = new UnitCell();
        uc.setA(a);
        uc.setB(b);
        uc.setC(c);
        uc.setAlpha(alpha);
        uc.setBeta(beta);
        uc.setGamma(gamma);
        // @todo asymmetric unit
        uc.setCoordScale(coordScale);
        uc.setCoordType(coordType);
        uc.setLatticeData(new String(latticeData.getBytes().clone()));
        uc.setRprim(rprim.clone());
        ArrayList<AtomicSite> newSites = new ArrayList();
        for (int inc = 0; inc < sitesCollection.size(); inc++) {
            newSites.add(sitesCollection.get(inc).clone());
        }
        uc.setSites(newSites);
        uc.setCartesianCoordinates(xcart.clone());
        return uc;
    }
}
/**
 * $Log: UnitCell.java,v $
 * Revision 1.26  2010-03-23 15:20:26  aryjr
 * Correcao na termodinamica estatistica.
 *
 * Revision 1.25  2010-03-06 12:51:40  aryjr
 * Termoquimica para moleculas agora tambem!!!
 *
 * Revision 1.24  2010-02-05 09:28:34  aryjr
 * Termoquimica para moleculas agora tambem!!!
 *
 * Revision 1.23  2009-08-17 21:08:44  aryjr
 * Generalizando o grafico 2D do projeto GFQSI-Common.
 *
 * Revision 1.22  2009-01-26 12:03:07  aryjr
 * Corrigindo pequeno bug com os labels dos sitios geometricos.
 *
 * Revision 1.21  2009-01-21 17:59:03  aryjr
 * Suporte a sitios geometricos nos adsorvatos (moleculas).
 *
 * Revision 1.20  2009-01-06 17:02:09  aryjr
 * Trabalho em casa durante o final do ano.
 *
 * Revision 1.19  2008-12-18 11:16:03  aryjr
 * Notificando na saida os sites com distancia menor do q a minima.
 *
 * Revision 1.18  2008-12-17 19:19:34  aryjr
 * Corrigindo alguns bugs antes de iniciar com o suporte a relaxacao.
 *
 * Revision 1.17  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.16  2008-12-08 18:04:27  aryjr
 * Contanto o numero de vizinhos, verificando distancias minimas, nova fitness function...
 *
 * Revision 1.15  2008-11-28 18:19:48  aryjr
 * Sincronizando.
 *
 * Revision 1.14  2008-11-18 18:31:36  aryjr
 * Generalizando o calculo do rprim.
 *
 * Revision 1.13  2008-11-18 15:41:30  aryjr
 * Agora trabalhando com propagacao.
 *
 * Revision 1.12  2008-11-14 19:12:37  aryjr
 * Agora trabalhando com propagacao.
 *
 * Revision 1.11  2008-11-14 17:10:11  aryjr
 * Dando uma geral no simetrizador.
 *
 * Revision 1.10  2008-11-06 15:12:12  aryjr
 * OK (10/10/08) - Definir uma pre-avaliacao antes de rodar o pw.x (AdsorbatePositionFitnessFunction).
 *
 * Revision 1.9  2008-10-17 16:09:46  aryjr
 * + Terapia genetica (AdsorbatePositionFitnessFunction).
 *
 * Revision 1.8  2008-10-02 19:10:57  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */