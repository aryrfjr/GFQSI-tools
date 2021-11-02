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

import br.ufjf.quimica.gfqsi.common.math.GeometricSite;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * An atomic site abstraction.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: AtomicSite.java,v 1.15 2009-09-28 21:28:35 aryjr Exp $
 *
 */
public class AtomicSite extends GeometricSite implements Cloneable {

    public static final String CVS_REVISION = "$Revision: 1.15 $";
    private int id;
    private Atom atom;
    private ArrayList<Atom> atoms = new ArrayList();
    public static String POINT_LABEL = "Point";
    private boolean relax = false;// @todo Sugere uma classe derivada

    public AtomicSite(String symbols) throws InvalidElementException {
        setAtoms(symbols);
        setLabel(symbols);
    }

    public AtomicSite(Atom atom) {
        this.atom = atom;
        atoms.add(atom);
        setLabel(atom == null ? "" : atom.getSymbol());
    }

    public static AtomicSite siteFromStream(String stream) {
        try {
            StringTokenizer stk = new StringTokenizer(stream, " ");
            AtomicSite site = new AtomicSite(new Atom(stk.nextToken().trim()));
            site.setX(Double.parseDouble(stk.nextToken().trim()));
            site.setY(Double.parseDouble(stk.nextToken().trim()));
            site.setZ(Double.parseDouble(stk.nextToken().trim()));
            return site;
        } catch (InvalidElementException ex) {
            Logger.getLogger(AtomicSite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setAtoms(String symbols) throws InvalidElementException {
        if (symbols.equals(POINT_LABEL)) {
            return; // Nothing to do
        }
        atoms.clear();
        if (symbols.indexOf(",") >= 0) {
            StringTokenizer stk = new StringTokenizer(symbols, ",");
            while (stk.hasMoreTokens()) {
                atom = new Atom(stk.nextToken());
                atoms.add(atom);
            }
        } else {
            atom = new Atom(symbols);
            atoms.add(atom);
        }
    }

    /**
     * 
     * 
     * @param site String like 'Mg       2.108500000   2.108500000   0.000000000'
     */
    public void updateCoordinates(String site) throws InvalidElementException {
        StringTokenizer stk = new StringTokenizer(site, " ");
        setLabel(stk.nextToken());
        setAtoms(getLabel());
        setX(Double.parseDouble(stk.nextToken()));
        setY(Double.parseDouble(stk.nextToken()));
        setZ(Double.parseDouble(stk.nextToken()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Atom getAtom() {
        return atom;
    }

    public String getAtomicSymbols() {
        if (atom == null) {// it's just a reference point
            return POINT_LABEL;
        }
        String atomss = "";
        for (int inc = 0; inc < atoms.size(); inc++) {
            atomss += "," + atoms.get(inc).getSymbol();
        }
        return atomss.length() == 0 ? "" : atomss.substring(1);
    }

    public void setAtom(Atom atom) {
        this.atom = atom;
        setLabel(atom.getSymbol());
    }

    @Override
    public AtomicSite clone() {
        AtomicSite clone = null;
        try {
            if (atom == null) {
                clone = new AtomicSite(POINT_LABEL);
            } else {
                clone = new AtomicSite(atom.getSymbol());
                clone.setId(id);
                clone.setRelax(relax);
                clone.setX(getX());
                clone.setY(getY());
                clone.setZ(getZ());
            }
        } catch (InvalidElementException ex) {
            Logger.getLogger(AtomicSite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clone;
    }

    @Override
    public String toString() {
        return getLabel() + " " + getX() + " " + getY() + " " + getZ() + (isRelax() ? "1" : "0");
    }

    public boolean isRelax() {
        return relax;
    }

    public void setRelax(boolean relax) {
        this.relax = relax;
    }
}
/**
 * $Log: AtomicSite.java,v $
 * Revision 1.15  2009-09-28 21:28:35  aryjr
 * Adaptacoes no novo grafico 2D para a estrutura de bandas.
 *
 * Revision 1.14  2009-01-26 12:03:07  aryjr
 * Corrigindo pequeno bug com os labels dos sitios geometricos.
 *
 * Revision 1.13  2009-01-22 19:47:35  aryjr
 * Testes com terapia genetica.
 *
 * Revision 1.12  2009-01-21 17:59:03  aryjr
 * Suporte a sitios geometricos nos adsorvatos (moleculas).
 *
 * Revision 1.11  2008-12-18 12:56:38  aryjr
 * Suporte ao calculo de relaxacao (busca local) do PWscf nos algoritmos geneticos ok!!!
 *
 * Revision 1.10  2008-12-15 12:06:49  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.9  2008-12-08 18:04:26  aryjr
 * Contanto o numero de vizinhos, verificando distancias minimas, nova fitness function...
 *
 * Revision 1.8  2008-10-29 12:23:37  aryjr
 * Suporte a relaxacao de coordenadas dos atomos e terapia genetica.
 *
 * Revision 1.7  2008-10-16 17:54:16  aryjr
 * + Terapia genetica (AdsorbatePositionFitnessFunction).
 *
 * Revision 1.6  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */