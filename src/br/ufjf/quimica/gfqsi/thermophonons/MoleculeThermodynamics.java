/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.quimica.gfqsi.thermophonons;

import br.ufjf.quimica.gfqsi.common.chem.AtomicSite;
import br.ufjf.quimica.gfqsi.common.chem.Molecule;
import br.ufjf.quimica.gfqsi.common.thermodynamics.Thermodynamics;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: MoleculeThermodynamics.java,v 1.2 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public class MoleculeThermodynamics extends SolidStateThermodynamics {
    protected Molecule molecule;
    private double symmetryNumber;
    protected double[] translationalInternalEnergies;// For each temperature
    protected double[] translationalEntropies;// For each temperature
    protected double[] translationalPartitionFunctions;// For each temperature
    protected double[] rotationalInternalEnergies;// For each temperature
    protected double[] rotationalEntropies;// For each temperature
    protected double[] rotationalPartitionFunctions;// For each temperature

    /**
     * @param molecule the molecule to set
     */
    public void setMolecule(AtomicSite[] sites, boolean linear) {
        molecule = new Molecule();
        molecule.setAtomicSites(sites);
        molecule.setLinear(linear);
    }

    /**
     * @return the translationalInternalEnergies
     */
    public double[] getTranslationalInternalEnergies() {
        return translationalInternalEnergies;
    }

    /**
     * @return the translationalEntropies
     */
    public double[] getTranslationalEntropies() {
        return translationalEntropies;
    }

    /**
     * @return the rotationalInternalEnergies
     */
    public double[] getRotationalInternalEnergies() {
        return rotationalInternalEnergies;
    }

    /**
     * @return the rotationalEntropies
     */
    public double[] getRotationalEntropies() {
        return rotationalEntropies;
    }

    @Override
    public void setTemperatures(String stemperatures) {
        super.setTemperatures(stemperatures);
        translationalInternalEnergies = new double[temperatures.length];
        translationalEntropies = new double[temperatures.length];
        translationalPartitionFunctions = new double[temperatures.length];
        double ts;
        double m = molecule.getTotalMass() / (Thermodynamics.AVOGADRO_CONSTANT * 1000);
        for (int inc = 0; inc < temperatures.length; inc++) {
            translationalInternalEnergies[inc] = (1.5 * Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / 1000d;
            if (temperatures[inc] == 0) {
                translationalEntropies[inc] = 0;
            } else {
                ts = Math.log(Math.pow((m * Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / (Math.pow(Thermodynamics.DIRAC_CONSTANT, 2d) * 2d * Math.PI), 1.5) * ((Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / pressure));
                ts += 1;
                ts += 1.5;
                translationalEntropies[inc] = (Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * ts) / 1000d;
            }
            translationalPartitionFunctions[inc] = Math.pow((2d * Math.PI * m * Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / Math.pow(Thermodynamics.PLANCK_CONSTANT, 2d), 1.5) * ((Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / pressure);
        }
    }

    @Override
    public double getTotalEntropy(int inct) {
        return super.getTotalEntropy(inct) + getTranslationalEntropies()[inct] + getRotationalEntropies()[inct];
    }

    @Override
    public double getTotalEnergy(int inct) {
        return super.getTotalEnergy(inct) + getTranslationalInternalEnergies()[inct] + getRotationalInternalEnergies()[inct];
    }

    @Override
    public double getTotalPartitionFunction(int inct) {
        return super.getTotalPartitionFunction(inct) * getTranslationalPartitionFunctions()[inct] * getRotationalPartitionFunctions()[inct];
    }

    /**
     * @return the symmetryNumber
     */
    public double getSymmetryNumber() {
        return symmetryNumber;
    }

    /**
     * @param symmetryNumber the symmetryNumber to set
     */
    public void setSymmetryNumber(double symmetryNumber) {
        this.symmetryNumber = symmetryNumber;
    }

    /**
     * @return the translationalPartitionFunctions
     */
    public double[] getTranslationalPartitionFunctions() {
        return translationalPartitionFunctions;
    }

    /**
     * @return the rotationalPartitionFunctions
     */
    public double[] getRotationalPartitionFunctions() {
        return rotationalPartitionFunctions;
    }

}
