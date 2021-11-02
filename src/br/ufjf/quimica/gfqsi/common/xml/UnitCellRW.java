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
package br.ufjf.quimica.gfqsi.common.xml;

import br.ufjf.quimica.gfqsi.common.chem.AtomicSite;
import br.ufjf.quimica.gfqsi.common.chem.InvalidElementException;
import br.ufjf.quimica.gfqsi.common.cryst.AsymmetricUnit;
import br.ufjf.quimica.gfqsi.common.cryst.CrystSpaceGroups;
import br.ufjf.quimica.gfqsi.common.cryst.UnitCell;
import br.ufjf.quimica.gfqsi.common.math.Matrix;
import br.ufjf.quimica.gfqsi.xml.Tag;
import br.ufjf.quimica.gfqsi.xml.TagsCollection;
import br.ufjf.quimica.gfqsi.xml.XMLDOMParser;
import br.ufjf.quimica.gfqsi.xml.XMLDOMParserException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aryjr
 */
public class UnitCellRW {

    public static UnitCell loadUnitCellFromXML(String path) {
        UnitCell unitCell = null;
        try {
            XMLDOMParser xmlParser = new XMLDOMParser();
            xmlParser.parse(path);
            Tag uct = xmlParser.getRoot();
            unitCell = new UnitCell();
            unitCell.setLatticeData(uct.getAttribute("lattice-data").getValue());
            if (unitCell.getLatticeData().equals(UnitCell.LATTICE_FROM_PARAM)) {
                unitCell.setA(Double.parseDouble(uct.getAttribute("a").getValue()));
                unitCell.setB(Double.parseDouble(uct.getAttribute("b").getValue()));
                unitCell.setC(Double.parseDouble(uct.getAttribute("c").getValue()));
                unitCell.setAlpha(Double.parseDouble(uct.getAttribute("alpha").getValue()));
                unitCell.setBeta(Double.parseDouble(uct.getAttribute("beta").getValue()));
                unitCell.setGamma(Double.parseDouble(uct.getAttribute("gamma").getValue()));
                unitCell.calculateRprim();
            } else {
                unitCell.setRprim(loadRprim(uct.getChild("rprim").getContent()));
                unitCell.calculateLatticeParameters();
            }
            unitCell.setCoordScale(uct.getAttribute("scale").getValue());
            if (uct.getChild("asymmetric-unit") != null) {
                Tag aut = uct.getChild("asymmetric-unit");
                unitCell.setCoordType(UnitCell.INTERNAL_COORDINATES);
                AsymmetricUnit au = new AsymmetricUnit();
                au.setSpgroup(CrystSpaceGroups.collectionMap.get(aut.getAttribute("spgroup").getValue()));
                TagsCollection sitest = aut.getChild("atomic-sites").getChildren("site");
                Tag sitet;
                AtomicSite as;
                for (int inc = 0; inc < sitest.countTags(); inc++) {
                    sitet = sitest.getTag(inc);
                    as = new AtomicSite(sitet.getAttribute("symbol").getValue());
                    as.setId(inc);
                    as.setX(Double.parseDouble(sitet.getAttribute("x").getValue()));
                    as.setY(Double.parseDouble(sitet.getAttribute("y").getValue()));
                    as.setZ(Double.parseDouble(sitet.getAttribute("z").getValue()));
                    as.setRelax(sitet.getAttribute("relax") != null && sitet.getAttribute("relax").getValue().equals("1"));
                    au.addAtomicSite(as);
                }
                unitCell.setAsymmetricUnit(au);
                unitCell.build();
            } else {
                unitCell.setCoordType(uct.getChild("atomic-sites").getAttribute("coordinate-system").getValue());
                TagsCollection sitest = uct.getChild("atomic-sites").getChildren("site");
                Tag sitet;
                AtomicSite as;
                for (int inc = 0; inc < sitest.countTags(); inc++) {
                    sitet = sitest.getTag(inc);
                    as = new AtomicSite(sitet.getAttribute("symbol").getValue());
                    as.setId(inc);
                    as.setX(Double.parseDouble(sitet.getAttribute("x").getValue()));
                    as.setY(Double.parseDouble(sitet.getAttribute("y").getValue()));
                    as.setZ(Double.parseDouble(sitet.getAttribute("z").getValue()));
                    as.setRelax(sitet.getAttribute("relax") != null && sitet.getAttribute("relax").getValue().equals("1"));
                    unitCell.addSite(as);
                }
            }
        } catch (InvalidElementException ex) {
            Logger.getLogger(UnitCellRW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UnitCellRW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLDOMParserException ex) {
            Logger.getLogger(UnitCellRW.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unitCell;
    }

    public static Matrix loadRprim(String rprimstr) {
        StringTokenizer stkl = new StringTokenizer(rprimstr, "\n");
        StringTokenizer stk;
        Matrix rprim = new Matrix(3, 3);
        for (int inc = 0; stkl.hasMoreTokens(); inc++) {
            stk = new StringTokenizer(stkl.nextToken(), " ");
            rprim.setVAlue(inc, 0, Double.parseDouble(stk.nextToken()));
            rprim.setVAlue(inc, 1, Double.parseDouble(stk.nextToken()));
            rprim.setVAlue(inc, 2, Double.parseDouble(stk.nextToken()));
        }
        return rprim;
    }
}
