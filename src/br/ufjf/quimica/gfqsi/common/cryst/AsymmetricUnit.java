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
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Basic motif that is repeated in 3D space by the symmetry operators of the crystallographic space group.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: AsymmetricUnit.java,v 1.7 2009-01-21 17:59:03 aryjr Exp $
 *
 */
public class AsymmetricUnit {
    
    public static final String CVS_REVISION = "$Revision: 1.7 $";
    private CrystSpaceGroup spgroup;
    private int sitesPerFullUC;
    private ArrayList<AtomicSite> sites = new ArrayList();
    private HashMap<String, Integer> speciesCount = new HashMap();

    public CrystSpaceGroup getSpgroup() {
        return spgroup;
    }

    public void setSpgroup(CrystSpaceGroup spgroup) {
        this.spgroup = spgroup;
    }

    public void addAtomicSite(AtomicSite site) {
        if (getSpeciesCount().containsKey(site.getAtomicSymbols())) {
            getSpeciesCount().put(site.getAtomicSymbols(), getSpeciesCount().get(site.getAtom().getSymbol()).intValue() + 1);
        } else {
            getSpeciesCount().put(site.getAtomicSymbols(), new Integer(1));
        }
        getSites().add(site);
    }

    public void clearAtomicSites() {
        getSites().clear();
    }

    public AtomicSite getAtomicSite(int index) {
        return getSites().get(index);
    }

    public int countSpeciesTypes() {
        return getSpeciesCount().size();
    }

    public int countAtomicSites() {
        return getSites().size();
    }

    public HashMap<String, Integer> getSpeciesCount() {
        return speciesCount;
    }

    public ArrayList<AtomicSite> getSites() {
        return sites;
    }

    public int getSitesPerFullUC() {
        return sitesPerFullUC;
    }

    protected void setSitesPerFullUC(int sitesPerFullUC) {
        this.sitesPerFullUC = sitesPerFullUC;
    }
}
/**
 * $Log: AsymmetricUnit.java,v $
 * Revision 1.7  2009-01-21 17:59:03  aryjr
 * Suporte a sitios geometricos nos adsorvatos (moleculas).
 *
 * Revision 1.6  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.5  2008-10-02 19:10:56  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */