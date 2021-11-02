/*
 *
 * The GFQSI-ExternalCodeGlue Project : Classes to read and write external softwares files.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/ecglue
 * Project Lead:  Ary Rodrigues Ferreira Junior
 *
 * (C) Copyright 2007 - 2010 by Ary Junior and GFQSI-UFJF (Grupo de Fisico Quimica de Solidos e Interfaces da UFJF).
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
package br.ufjf.quimica.gfqsi.ecglue.qespresso;

import br.ufjf.quimica.gfqsi.common.chem.AtomicSite;
import br.ufjf.quimica.gfqsi.common.chem.InvalidElementException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Reader for pw.x from Quantum Espresso software (http://www.pwscf.org).
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: PWOutputReader.java,v 1.2 2010-03-06 12:51:41 aryjr Exp $
 */
public class PWOutputReader {

    private int nat;
    private ArrayList<AtomicSite[]> sites = new ArrayList<AtomicSite[]>();
    private ArrayList<Double> energies = new ArrayList<Double>();

    public void loadFile(String path)  throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        StringTokenizer stk;
        AtomicSite[] sitesc;
        AtomicSite site;
        while (br.ready()) {
            line = br.readLine();
            if (line.indexOf("number of atoms/cell") >= 0) {
                stk = new StringTokenizer(line, " ");
                stk.nextToken(); // number
                stk.nextToken(); // of
                stk.nextToken(); // atoms/cell
                stk.nextToken(); // =
                nat = Integer.valueOf(stk.nextToken());
            } else if (line.indexOf("site n.     atom") >= 0) {
                sitesc = new AtomicSite[getNat()];
                try {
                    for (int inc = 0; inc < getNat(); inc++) {
                            line = br.readLine();
                            stk = new StringTokenizer(line, " ");
                            // 1           Al  tau(  1) = (   3.2847625   6.3105781   6.0802579  )
                            stk.nextToken(); // n
                            site = new AtomicSite(stk.nextToken()); // symbol
                            if (stk.nextToken().equals("tau(")) {
                                stk.nextToken(); // (
                            }
                            stk.nextToken(); // =
                            stk.nextToken(); // (
                            site.setX(Double.parseDouble(stk.nextToken()));
                            site.setY(Double.parseDouble(stk.nextToken()));
                            site.setZ(Double.parseDouble(stk.nextToken()));
                            sitesc[inc] = site;
                    }
                    sites.add(sitesc);
                } catch (InvalidElementException ex) {
                    Logger.getLogger(PWOutputReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (line.indexOf("ATOMIC_POSITIONS") >= 0) {
                sitesc = new AtomicSite[getNat()];
                try {
                    for (int inc = 0; inc < getNat(); inc++) {
                            line = br.readLine();
                            stk = new StringTokenizer(line, " ");
                            site = new AtomicSite(stk.nextToken());
                            site.setX(Double.parseDouble(stk.nextToken()));
                            site.setY(Double.parseDouble(stk.nextToken()));
                            site.setZ(Double.parseDouble(stk.nextToken()));
                            sitesc[inc] = site;
                    }
                    sites.add(sitesc);
                } catch (InvalidElementException ex) {
                    Logger.getLogger(PWOutputReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (line.indexOf("!") == 0) {
                stk = new StringTokenizer(line, " ");
                // !    total energy              =   -2135.81691036 Ry
                stk.nextToken(); // !
                stk.nextToken(); // total
                stk.nextToken(); // energy
                stk.nextToken(); // =
                energies.add(new Double(stk.nextToken()));
            }
        }
    }

    public double getLastEnergy() {
        return energies.get(energies.size() - 1).doubleValue();
    }

    public AtomicSite[] getLastCoordinates() {
        return sites.get(sites.size() - 1);
    }

    /**
     * @return the nat
     */
    public int getNat() {
        return nat;
    }
}
