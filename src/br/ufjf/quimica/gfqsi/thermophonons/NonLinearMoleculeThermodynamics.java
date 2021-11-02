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
 * @version $Id: NonLinearMoleculeThermodynamics.java,v 1.2 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public class NonLinearMoleculeThermodynamics extends MoleculeThermodynamics implements ThermodynamicsInformation, MoleculeThermodynamicsInformation {
    private double rotationalTemperatureX;
    private double rotationalTemperatureY;
    private double rotationalTemperatureZ;

    /**
     * @return the rotationalTemperatureX
     */
    public double getRotationalTemperatureX() {
        return rotationalTemperatureX;
    }

    /**
     * @return the rotationalTemperatureY
     */
    public double getRotationalTemperatureY() {
        return rotationalTemperatureY;
    }

    /**
     * @return the rotationalTemperatureZ
     */
    public double getRotationalTemperatureZ() {
        return rotationalTemperatureZ;
    }

    @Override
    public void setMolecule(AtomicSite[] sites, boolean linear) {
        super.setMolecule(sites, linear);
        double mi = molecule.getMomentOfInertiaXX() / (Thermodynamics.AVOGADRO_CONSTANT * 1e23);
        rotationalTemperatureX = Math.pow(Thermodynamics.DIRAC_CONSTANT, 2d) / (2 * mi * Thermodynamics.BOLTZMANN_CONSTANT);
        mi = molecule.getMomentOfInertiaYY() / (Thermodynamics.AVOGADRO_CONSTANT * 1e23);
        rotationalTemperatureY = Math.pow(Thermodynamics.DIRAC_CONSTANT, 2d) / (2 * mi * Thermodynamics.BOLTZMANN_CONSTANT);
        mi = molecule.getMomentOfInertiaZZ() / (Thermodynamics.AVOGADRO_CONSTANT * 1e23);
        rotationalTemperatureZ = Math.pow(Thermodynamics.DIRAC_CONSTANT, 2d) / (2 * mi * Thermodynamics.BOLTZMANN_CONSTANT);
    }

    @Override
    public void setTemperatures(String stemperatures) {
        super.setTemperatures(stemperatures);
        rotationalInternalEnergies = new double[temperatures.length];
        rotationalEntropies = new double[temperatures.length];
        rotationalPartitionFunctions = new double[temperatures.length];
        double rs = 0d;
        for (int inc = 0; inc < temperatures.length; inc++) {
            rotationalInternalEnergies[inc] = (1.5 * Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * temperatures[inc]) / 1000d;
            rs = Math.log(((Math.sqrt(Math.PI) / getSymmetryNumber()) * (Math.sqrt(Math.pow(temperatures[inc], 3d)) / Math.sqrt(getRotationalTemperatureX() + getRotationalTemperatureY() + getRotationalTemperatureZ()))) + 1.5);
            rotationalEntropies[inc] = (Thermodynamics.AVOGADRO_CONSTANT * Thermodynamics.BOLTZMANN_CONSTANT * rs) / 1000d;
            rotationalPartitionFunctions[inc] = ((Math.sqrt(Math.PI) / getSymmetryNumber()) * (Math.sqrt(Math.pow(temperatures[inc], 3d)) / Math.sqrt(getRotationalTemperatureX() + getRotationalTemperatureY() + getRotationalTemperatureZ())));
        }
    }

    public Molecule getMolecule() {
        return molecule;
    }

}
