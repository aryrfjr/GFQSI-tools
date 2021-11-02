/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.quimica.gfqsi.thermophonons;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: ThermodynamicsInformation.java,v 1.2 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public interface ThermodynamicsInformation {

    public void setElectronicEnergy(double electronicEnergy);
    public double getElectronicEnergy();
    public double getZeroPointEnergy();
    public void setVibrationalModesWaveNumbers(double[] vibrationalModesWaveNumbers);
    public void setPressure(double pressure);
    public void setTemperatures(String stemperatures);
    public double[] getTemperatures();
    public double[] getVibrationalInternalEnergies();
    public double[] getVibrationalEntropies();
    public double[] getVibrationalTemperatures();
    public double[] getVibrationalPartitionFunctions();
    public double getTotalEntropy(int inct);
    public double getTotalEnergy(int inct);
    public double getTotalPartitionFunction(int inct);
    
}
