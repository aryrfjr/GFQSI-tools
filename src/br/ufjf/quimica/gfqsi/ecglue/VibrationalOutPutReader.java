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

package br.ufjf.quimica.gfqsi.ecglue;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * Interface for vibrational spectra output files reader.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: VibrationalOutPutReader.java,v 1.2 2010-03-06 12:52:31 aryjr Exp $
 */
public interface VibrationalOutPutReader {

    public static final int WAVENUMBER = 0;
    public static final int INTENSITY = 1;
    public static int IGNORE_NEGATIVE_FREQUENCIES = 0;
    public static int IGNORE_NULL_INTENSITIES = 1;
    public static int IGNORE_BOTH = 2;
    public static int IGNORE_NULL_FREQUENCIES = 3;

    public void loadFile(String path) throws FileNotFoundException, IOException;
    public int countVibrationalModes();
    public double getVibrationalModeWaveNumber(int index);
    public double getVibrationalModeFrequency(int index);
    public double getVibrationalModeIntensity(int index);
    public int getReadRule();
    public void setReadRule(int readRule);
}
