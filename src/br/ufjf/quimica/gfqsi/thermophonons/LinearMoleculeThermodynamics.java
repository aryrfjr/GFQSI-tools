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
 * @version $Id: LinearMoleculeThermodynamics.java,v 1.2 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public class LinearMoleculeThermodynamics extends MoleculeThermodynamics implements ThermodynamicsInformation, MoleculeThermodynamicsInformation {
    private double rotationalTemperature;

    /**
     * @return the rotationalTemperature
     */
    public double getRotationalTemperature() {
        return rotationalTemperature;
    }

    @Override
    public void setMolecule(AtomicSite[] sites, boolean linear) {
        super.setMolecule(sites, linear);
        double mi = molecule.getMomentOfInertia() / (Thermodynamics.AVOGADRO_CONSTANT * 1e23);
        rotationalTemperature = Math.pow(Thermodynamics.DIRAC_CONSTANT, 2d) / (2 * mi * Thermodynamics.BOLTZMANN_CONSTANT);
    }

    @Override
    public void setTemperatures(String stemperatures) {
        super.setTemperatures(stemperatures);
        rotationalInternalEnergies = new double[temperatures.length];
        rotationalEntropies = new double[temperatures.length];
        rotationalPartitionFunctions = new double[temperatures.length];
        double rs = 0d;
        for (int inc = 0; inc < temperatures.length; inc++) {
            rotationalInternalEnergies[inc] = (Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / 1000d;
            rs = Math.log(((1d / getSymmetryNumber()) * (temperatures[inc] / getRotationalTemperature())) + 1);
            rotationalEntropies[inc] = (Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * rs) / 1000d;
            rotationalPartitionFunctions[inc] = (1d / getSymmetryNumber()) * (temperatures[inc] / getRotationalTemperature());
        }
    }

    public Molecule getMolecule() {
        return molecule;
    }

}
