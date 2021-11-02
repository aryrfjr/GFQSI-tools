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

import br.ufjf.quimica.gfqsi.common.chem.Atom;
import br.ufjf.quimica.gfqsi.common.chem.AtomicSite;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 
 * Builde unit cell geometry based on space group.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: GeometryBuilder.java,v 1.10 2010-01-19 14:34:08 aryjr Exp $
 *
 */
public class GeometryBuilder {

    public static final String CVS_REVISION = "$Revision: 1.10 $";

    /**
     * 
     * @param uc
     * @return
     */
    public static HashMap<String, AtomicSite> build(UnitCell uc) {
        HashMap<String, AtomicSite> hashSites = new HashMap();
        AtomicSite site, assymSite;
        CrystSymmetryOperation symop;
        HashMap<String, Double> assymSiteCoord = new HashMap();
        DecimalFormat fmt = new DecimalFormat("0.000");
        try {
            for (int inc = 0; inc < uc.getAsymmetricUnit().getSpgroup().countSymmetriOperations(); inc++) {
                symop = uc.getAsymmetricUnit().getSpgroup().getSymmetryOperation(inc);
                for (int i = 0; i < uc.getAsymmetricUnit().countAtomicSites(); i++) {
                    assymSite = uc.getAsymmetricUnit().getAtomicSite(i);
                    site = new AtomicSite(new Atom(assymSite.getAtomicSymbols()));
                    site.setId(inc);
                    // The coordinates of the assymetric unit for apply the operations over it
                    assymSiteCoord.put("X", assymSite.getX());
                    assymSiteCoord.put("Y", assymSite.getY());
                    assymSiteCoord.put("Z", assymSite.getZ());
                    // for X
                    site.setX(evaluateSymmetryOperation(symop.getX(), assymSiteCoord));
                    System.out.println(site.getX());
                    if (fmt.format(site.getX()).equals("-0.000")) {
                        // @todo -0 != 0 ???
                        site.setX(0.0);
                    }
                    if (site.getX() < 0.0) {
                        // Remove the negative coordinates
                        site.setX(site.getX() + 1d);
                    }
                    if (site.getX() >= 1.0) {
                        // Remove the coordinates >= 1.0
                        site.setX(site.getX() - 1d);
                    }
                    // for Y
                    site.setY(evaluateSymmetryOperation(symop.getY(), assymSiteCoord));
                    if (fmt.format(site.getY()).equals("-0.000")) {
                        // @todo -0 != 0 ???
                        site.setY(0.0);
                    }
                    if (site.getY() < 0.0) {
                        // Remove the negative coordinates
                        site.setY(site.getY() + 1d);
                    }
                    if (site.getY() >= 1.0) {
                        // Remove the coordinates >= 1.0
                        site.setY(site.getY() - 1d);
                    }
                    // for Z
                    site.setZ(evaluateSymmetryOperation(symop.getZ(), assymSiteCoord));
                    if (fmt.format(site.getZ()).equals("-0.000")) {
                        // @todo -0 != 0 ???
                        site.setZ(0.0);
                    }
                    if (site.getZ() < 0.0) {
                        // Remove the negative coordinates
                        site.setZ(site.getZ() + 1d);
                    }
                    if (site.getZ() >= 1.0) {
                        // Remove the coordinates >= 1.0
                        site.setZ(site.getZ() - 1d);
                    }
                    // Add to hash map to eliminate the hashSites with the same key
                    hashSites.put(site.getAtomicSymbols() + fmt.format(site.getX()) + fmt.format(site.getY()) + fmt.format(site.getZ()), site);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashSites;
    }

    // Evaluate the symmetry operations
    private static double evaluateSymmetryOperation(String symop, HashMap<String, Double> assymSiteCoord) {
        double value = Double.MAX_VALUE;
        StringTokenizer stk;
        String token;
        if (symop.indexOf("+") > 0) {
            stk = new StringTokenizer(symop, "+");
            while (stk.hasMoreTokens()) {
                token = stk.nextToken();
                value = value == Double.MAX_VALUE ? evaluateSymmetryOperation(token, assymSiteCoord) : value + evaluateSymmetryOperation(token, assymSiteCoord);
            }
        } else if (symop.indexOf("-", 1) > 0) {
            stk = new StringTokenizer(symop, "-");
            while (stk.hasMoreTokens()) {
                token = stk.nextToken();
                value = value == Double.MAX_VALUE ? evaluateSymmetryOperation(token, assymSiteCoord) : value - evaluateSymmetryOperation(token, assymSiteCoord);
            }
        } else if (symop.indexOf("-") == 0) {
            value = -assymSiteCoord.get(symop.substring(1)).doubleValue();
        } else if (symop.indexOf("/") > 0) {
            double num = Double.parseDouble(symop.substring(0, 1));
            double den = Double.parseDouble(symop.substring(2));
            value = num / den;
        } else {
            value = assymSiteCoord.get(symop).doubleValue();
        }
        return value;
    }
}
/**
 * $Log: GeometryBuilder.java,v $
 * Revision 1.10  2010-01-19 14:34:08  aryjr
 * N correcoes.
 *
 * Revision 1.9  2009-01-21 17:59:03  aryjr
 * Suporte a sitios geometricos nos adsorvatos (moleculas).
 *
 * Revision 1.8  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.7  2008-11-14 17:10:11  aryjr
 * Dando uma geral no simetrizador.
 *
 * Revision 1.6  2008-10-02 19:10:56  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */