/*
 *
 * The GFQSI-ExternalCodeGlue Project : Classes to read and write external softwares files.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/ecglue
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

package br.ufjf.quimica.gfqsi.ecglue.qespresso;

import br.ufjf.quimica.gfqsi.ecglue.VibrationalOutPutReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * Reader for dynmat.x from Quantum Espresso software (http://www.pwscf.org).
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: DynMatOutputReader.java,v 1.5 2010-03-06 12:51:41 aryjr Exp $
 */
public class DynMatOutputReader implements VibrationalOutPutReader {

    private ArrayList<double[]> vmodes;
    private int readRule = -1;

    public void loadFile(String path) throws FileNotFoundException, IOException {
        vmodes = new ArrayList<double[]>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        boolean readingModes = false;
        String line;
        double[] vmode;
        StringTokenizer stk;
        while (br.ready()) {
            line = br.readLine();
            if (readingModes) {
                vmode = new double[3];
                stk = new StringTokenizer(line, " ");
                stk.nextToken();// Mode index
                vmode[0] = Double.parseDouble(stk.nextToken());// Wavenumber
                vmode[1] = Double.parseDouble(stk.nextToken());// Frequency
                vmode[2] = Double.parseDouble(stk.nextToken());// Intensity
                if ((readRule == IGNORE_NEGATIVE_FREQUENCIES && vmode[0] >= 0)
                        || readRule == IGNORE_NULL_INTENSITIES && vmode[2] > 0
                        || readRule == IGNORE_NULL_FREQUENCIES && vmode[0] > 0
                        || readRule == IGNORE_BOTH && vmode[0] >= 0 && vmode[2] > 0
                        || readRule == -1) {
                    getVmodes().add(vmode);
                }
            } else if (line.indexOf("#") > -1) {
                readingModes = true;
            }
        }
    }

    public int countVibrationalModes() {
        return getVmodes().size();
    }

    public double getVibrationalModeWaveNumber(int index) {
        return getVmodes().get(index)[0];
    }

    public double[] getVibrationalModesWaveNumbers() {
        double[] vmwn = new double[vmodes.size()];
        int inc = 0;
        for (Iterator<double[]> it = vmodes.iterator(); it.hasNext();) {
            vmwn[inc++] = it.next()[0];
        }
        return vmwn;
    }

    public double getVibrationalModeFrequency(int index) {
        return getVmodes().get(index)[1];
    }

    public double getVibrationalModeIntensity(int index) {
        return getVmodes().get(index)[2];
    }

    /**
     * @return the readRule
     */
    public int getReadRule() {
        return readRule;
    }

    /**
     * @param readRule the readRule to set
     */
    public void setReadRule(int readRule) {
        this.readRule = readRule;
    }

    /**
     * @return the vmodes
     */
    public ArrayList<double[]> getVmodes() {
        return vmodes;
    }

}
