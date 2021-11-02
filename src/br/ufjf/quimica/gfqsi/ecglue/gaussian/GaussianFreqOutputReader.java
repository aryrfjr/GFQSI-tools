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

package br.ufjf.quimica.gfqsi.ecglue.gaussian;

import br.ufjf.quimica.gfqsi.ecglue.VibrationalOutPutReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * Reader for Gaussian (http://www.gaussian.com) output with Freq key word.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: GaussianFreqOutputReader.java,v 1.1 2010-01-19 14:35:05 aryjr Exp $
 */
public class GaussianFreqOutputReader implements VibrationalOutPutReader {

    private ArrayList<double[]> vmodes;
    private int readRule = -1;

    public void loadFile(String path) throws FileNotFoundException, IOException {
        vmodes = new ArrayList<double[]>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        double[][] vmode;
        StringTokenizer stk;
        while (br.ready()) {
            line = br.readLine();
            if (line.indexOf(" Frequencies --") >= 0) {
                vmode = new double[3][4];
                // The frequencies
                stk = new StringTokenizer(line, " ");
                stk.nextToken();// Frequencies
                stk.nextToken();// --
                vmode[0][0] = Double.parseDouble(stk.nextToken());
                vmode[1][0] = Double.parseDouble(stk.nextToken());
                vmode[2][0] = Double.parseDouble(stk.nextToken());
                // The reduced masses
                stk = new StringTokenizer(br.readLine(), " ");
                stk.nextToken();// Red.
                stk.nextToken();// masses
                stk.nextToken();// --
                vmode[0][1] = Double.parseDouble(stk.nextToken());
                vmode[1][1] = Double.parseDouble(stk.nextToken());
                vmode[2][1] = Double.parseDouble(stk.nextToken());
                // The force constants
                stk = new StringTokenizer(br.readLine(), " ");
                stk.nextToken();// Frc
                stk.nextToken();// consts
                stk.nextToken();// --
                vmode[0][2] = Double.parseDouble(stk.nextToken());
                vmode[1][2] = Double.parseDouble(stk.nextToken());
                vmode[2][2] = Double.parseDouble(stk.nextToken());
                // The intensities
                stk = new StringTokenizer(br.readLine(), " ");
                stk.nextToken();// IR
                stk.nextToken();// Inten
                stk.nextToken();// --
                vmode[0][3] = Double.parseDouble(stk.nextToken());
                vmode[1][3] = Double.parseDouble(stk.nextToken());
                vmode[2][3] = Double.parseDouble(stk.nextToken());
                if (checkVibMode(vmode[0])) {
                    vmodes.add(vmode[0]);
                }
                if (checkVibMode(vmode[1])) {
                    vmodes.add(vmode[1]);
                }
                if (checkVibMode(vmode[2])) {
                    vmodes.add(vmode[2]);
                }
            }
        }
    }

    private boolean checkVibMode(double[] vmode) {
        return ((readRule == IGNORE_NEGATIVE_FREQUENCIES && vmode[0] >= 0)
                || readRule == IGNORE_NULL_INTENSITIES && vmode[3] > 0
                || readRule == IGNORE_BOTH && vmode[0] >= 0 && vmode[3] > 0
                || readRule == -1);
    }

    public int countVibrationalModes() {
        return vmodes.size();
    }

    public double getVibrationalModeWaveNumber(int index) {
        return (vmodes.get(index)[0]);
    }

    public double getVibrationalModeFrequency(int index) {
        return vmodes.get(index)[0];
    }

    public double getVibrationalModeIntensity(int index) {
        return vmodes.get(index)[3];
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

}
