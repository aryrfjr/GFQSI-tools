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

import br.ufjf.quimica.gfqsi.ecglue.InconsistentDataException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * Reader for dos.x and projwfc.x from Quantum Espresso software (http://www.pwscf.org).
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: DOSOutputReader.java,v 1.2 2010-04-20 19:36:45 aryjr Exp $
 */
public class DOSOutputReader {

    private ArrayList<double[]>  lines;

    public void loadMultipleFiles(File[] files) throws FileNotFoundException, IOException {
        lines = new ArrayList<double[]>();
        BufferedReader br;
        String line;
        StringTokenizer stk;
        double[] dataLine;
        int icol, ilin;
        boolean firstFile = true;
        // First check wich file have the major number of columns
        int maxcols = 0;
        for (File file : files) {
            br = new BufferedReader(new FileReader(file.getAbsolutePath()));
            // Ignore the first line
            if (br.ready()) {
                br.readLine();
                line = br.readLine();
                stk = new StringTokenizer(line, " ");
                if (stk.countTokens() > maxcols) {
                    maxcols = stk.countTokens();
                }
            }
        }
        // Now load the data
        for (File file : files) {
            br = new BufferedReader(new FileReader(file.getAbsolutePath()));
            // Ignore the first line
            if (br.ready()) br.readLine();
            ilin = 0;
            while (br.ready()) {
                line = br.readLine();
                stk = new StringTokenizer(line, " ");
                // Reading and sum data
                icol = 0;
                if (firstFile) {
                    dataLine = new double[maxcols];
                    while (stk.hasMoreTokens()) {
                        dataLine[icol++] = Double.parseDouble(stk.nextToken());
                    }
                    lines.add(dataLine);
                } else {
                    dataLine = lines.get(ilin++);
                    stk.nextToken();// Don't sum the energies
                    icol++;// Don't sum the energies
                    while (stk.hasMoreTokens()) {
                        dataLine[icol++] += Double.parseDouble(stk.nextToken());
                    }
                }
            }
            firstFile = false;
        }
    }

    public void loadFile(String path)  throws FileNotFoundException, IOException {
        lines = new ArrayList<double[]>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        double[] dataLine;
        StringTokenizer stk;
        // Ignore the first line
        if (br.ready()) br.readLine();
        int icol = 0;
        while (br.ready()) {
            line = br.readLine();
            stk = new StringTokenizer(line, " ");
            dataLine = new double[stk.countTokens()];
            while (stk.hasMoreTokens()) {
                dataLine[icol++] = Double.parseDouble(stk.nextToken());
            }
            icol = 0;
            lines.add(dataLine);
        }
    }

    public double[] getEnergies() {
        return getColumn(0);
    }

    public double[] getColumn(int index) {
        double[] c = new double[lines.size()];
        int i = 0;
        for (double[] d : lines) {
            c[i++] = d[index];
        }
        return c;
    }

    /**
     *
     * Return one column besides the energy column.
     *
     * @param index
     * @return
     */
    public double[][] getSerie(int index1) {
        double[][] c = new double[lines.size()][2];
        int i = 0;
        for (double[] d : lines) {
            c[i][0] = d[0];
            c[i++][1] = d[index1];
        }
        return c;
    }

    /**
     *
     * Return two columns besides the energy column.
     *
     * @param index1
     * @param index2
     * @return
     */
    public double[][] getSerie(int index1, int index2) {
        double[][] c = new double[lines.size()][3];
        int i = 0;
        for (double[] d : lines) {
            c[i][0] = d[0];
            c[i][1] = d[index1];
            c[i++][2] = d[index2];
        }
        return c;
    }

    public int countLines() {
        return lines.size();
    }

    public int countColumns() {
        if (lines.size() == 0) {
            return 0;
        } else {
            return lines.get(0).length;
        }
    }
}
