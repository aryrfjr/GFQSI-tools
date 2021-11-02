/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.quimica.gfqsi.thermophonons;

import br.ufjf.quimica.gfqsi.common.thermodynamics.Thermodynamics;
import java.util.StringTokenizer;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: SolidStateThermodynamics.java,v 1.2 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public class SolidStateThermodynamics implements ThermodynamicsInformation {
    protected double[] temperatures;
    protected double pressure;// Constant for all temperatures
    protected double electronicEnergy;// Constant for all temperatures
    protected double[] vibrationalTemperatures;// This array is constant for all temperatures
    protected double[] vibrationalInternalEnergies;// For each temperature
    protected double[] vibrationalEntropies;// For each temperature
    private double[] vibrationalPartitionFunctions;// For each temperature
    protected double zeroPointEnergy;// Constant for all temperatures

    /**
     * @return the pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set in ATM.
     */
    public void setPressure(double pressure) {
        this.pressure = pressure * 101325;
    }

    /**
     * @return the electronicEnergy
     */
    public double getElectronicEnergy() {
        return electronicEnergy;
    }

    /**
     * @param electronicEnergy the electronicEnergy to set
     */
    public void setElectronicEnergy(double electronicEnergy) {
        this.electronicEnergy = electronicEnergy;
    }

    /**
     * @return the vibrationalTemperatures
     */
    public double[] getVibrationalTemperatures() {
        return vibrationalTemperatures;
    }

    /**
     * @param vibrationalTemperatures the vibrationalTemperatures to set
     */
    public void setVibrationalModesWaveNumbers(double[] vibrationalModesWaveNumbers) {
        if (vibrationalTemperatures == null) vibrationalTemperatures = new double[vibrationalModesWaveNumbers.length];
        double sum = 0d;// For ZPE
        for (int inc = 0; inc < vibrationalModesWaveNumbers.length; inc++) {
            vibrationalTemperatures[inc] = (2 * Math.PI * Thermodynamics.LIGHT_SPEED * vibrationalModesWaveNumbers[inc] * 100 * Thermodynamics.DIRAC_CONSTANT) / Thermodynamics.BOLTZMANN_CONSTANT;
            sum += vibrationalTemperatures[inc];
        }
        // Calculating the ZPE
        zeroPointEnergy = ((Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * sum) / 2) / 1000d;
    }

    /**
     * @return the zeroPointEnergy
     */
    public double getZeroPointEnergy() {
        return zeroPointEnergy;
    }

    /**
     * @return the vibrationalInternalEnergies
     */
    public double[] getVibrationalInternalEnergies() {
        return vibrationalInternalEnergies;
    }

    /**
     * @return the vibrationalEntropies
     */
    public double[] getVibrationalEntropies() {
        return vibrationalEntropies;
    }

    /**
     * @return the temperatures
     */
    public double[] getTemperatures() {
        return temperatures;
    }

    /**
     * @param temperatures the temperatures to set in a comma separated string.
     */
    public void setTemperatures(String stemperatures) {
        StringTokenizer stk = new StringTokenizer(stemperatures, ",");
        temperatures = new double[stk.countTokens()];
        vibrationalInternalEnergies = new double[temperatures.length];
        vibrationalEntropies = new double[temperatures.length];
        vibrationalPartitionFunctions = new double[temperatures.length];
        for (int inc = 0; inc < temperatures.length; inc++) {
            temperatures[inc] = Double.parseDouble(stk.nextToken());
            vibrationalPartitionFunctions[inc] = 1;
            for (int it = 0; it < vibrationalTemperatures.length; it++) {
                if (temperatures[inc] > 0) {
                    // Calculating the internal energies for each temperature
                    vibrationalInternalEnergies[inc] += vibrationalTemperatures[it] / Math.expm1(vibrationalTemperatures[it] / temperatures[inc]);
                    // Calculating the entropy for each temperature
                    vibrationalEntropies[inc] += ((vibrationalTemperatures[it] / temperatures[inc]) / (Math.expm1(vibrationalTemperatures[it] / temperatures[inc]))) - Math.log(1d - Math.exp(-vibrationalTemperatures[it] / temperatures[inc]));
                    // Calculating the partition function for each temperature
                    vibrationalPartitionFunctions[inc] *= (1 / (1d - Math.exp(-vibrationalTemperatures[it] / temperatures[inc])));
                }
            }
            vibrationalInternalEnergies[inc] = (Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * vibrationalInternalEnergies[inc]) / 1000d;
            vibrationalEntropies[inc] = (Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * vibrationalEntropies[inc]) / 1000d;
        }
    }

    public double getTotalEntropy(int inct) {
        return getVibrationalEntropies()[inct];
    }

    public double getTotalEnergy(int inct) {
        return electronicEnergy + getZeroPointEnergy() + getVibrationalInternalEnergies()[inct];
    }

    /**
     * @return the vibrationalPartitionFunctions
     */
    public double[] getVibrationalPartitionFunctions() {
        return vibrationalPartitionFunctions;
    }

    public double getTotalPartitionFunction(int inct) {
        return getVibrationalPartitionFunctions()[inct];
    }

}
