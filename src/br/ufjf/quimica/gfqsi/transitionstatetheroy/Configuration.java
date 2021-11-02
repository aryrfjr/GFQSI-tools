/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufjf.quimica.gfqsi.transitionstatetheroy;

import br.ufjf.quimica.gfqsi.common.thermodynamics.Thermodynamics;
import br.ufjf.quimica.gfqsi.ecglue.VibrationalOutPutReader;
import br.ufjf.quimica.gfqsi.ecglue.qespresso.DynMatOutputReader;
import br.ufjf.quimica.gfqsi.ecglue.qespresso.PWOutputReader;
import br.ufjf.quimica.gfqsi.thermophonons.LinearMoleculeThermodynamics;
import br.ufjf.quimica.gfqsi.thermophonons.MoleculeThermodynamicsInformation;
import br.ufjf.quimica.gfqsi.thermophonons.NonLinearMoleculeThermodynamics;
import br.ufjf.quimica.gfqsi.thermophonons.SolidStateThermodynamics;
import br.ufjf.quimica.gfqsi.thermophonons.ThermodynamicsInformation;
import br.ufjf.quimica.gfqsi.thermophonons.view.ThermoPhononsView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Configuration.java,v 1.1 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public class Configuration {

    private String pwscfEnergyFile;
    private String pwscfPhononsFile;
    private boolean molecule;
    private boolean polyatomicMolceule;
    private boolean linearMolceule;
    private double moleculeSymmetryNumber;
    private SolidStateThermodynamics sst = new SolidStateThermodynamics();
    private LinearMoleculeThermodynamics lmt = new LinearMoleculeThermodynamics();
    private NonLinearMoleculeThermodynamics nlmt = new NonLinearMoleculeThermodynamics();
    private ThermodynamicsInformation thermo;
    private MoleculeThermodynamicsInformation thermom;

    private void loadTotalEnergyFile() {
        try {
            PWOutputReader pwout = new PWOutputReader();
            pwout.loadFile(pwscfEnergyFile);
            // Set the informations on objects
            // If it is a molecule
            if (molecule) {
                if (linearMolceule) {
                    lmt.setMolecule(pwout.getLastCoordinates(), true);
                    lmt.setSymmetryNumber(moleculeSymmetryNumber);
                    thermo = lmt;
                    thermom = lmt;
                } else {
                    nlmt.setMolecule(pwout.getLastCoordinates(), false);
                    nlmt.setSymmetryNumber(moleculeSymmetryNumber);
                    thermo = nlmt;
                    thermom = nlmt;
                }
            } else {
                thermo = sst;
                thermom = null;
            }
            thermo.setElectronicEnergy((pwout.getLastEnergy() * Thermodynamics.RYDBERG_CONSTANT * Thermodynamics.AVOGADRO_CONSTANT) / 1000d);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThermoPhononsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThermoPhononsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPhononsFile() {
        try {
            DynMatOutputReader dynout = new DynMatOutputReader();
            dynout.setReadRule(VibrationalOutPutReader.IGNORE_NULL_FREQUENCIES);
            dynout.loadFile(pwscfPhononsFile);
            thermo.setVibrationalModesWaveNumbers(dynout.getVibrationalModesWaveNumbers());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThermoPhononsView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThermoPhononsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the pwscfEnergyFile
     */
    public String getPwscfEnergyFile() {
        return pwscfEnergyFile;
    }

    /**
     * @param pwscfEnergyFile the pwscfEnergyFile to set
     */
    public void setPwscfEnergyFile(String pwscfEnergyFile) {
        this.pwscfEnergyFile = pwscfEnergyFile;
    }

    /**
     * @return the pwscfPhononsFile
     */
    public String getPwscfPhononsFile() {
        return pwscfPhononsFile;
    }

    /**
     * @param pwscfPhononsFile the pwscfPhononsFile to set
     */
    public void setPwscfPhononsFile(String pwscfPhononsFile) {
        this.pwscfPhononsFile = pwscfPhononsFile;
    }

    /**
     * @return the molecule
     */
    public boolean isMolecule() {
        return molecule;
    }

    /**
     * @param molecule the molecule to set
     */
    public void setMolecule(boolean molecule) {
        this.molecule = molecule;
    }

    /**
     * @return the polyatomicMolceule
     */
    public boolean isPolyatomicMolceule() {
        return polyatomicMolceule;
    }

    /**
     * @param polyatomicMolceule the polyatomicMolceule to set
     */
    public void setPolyatomicMolceule(boolean polyatomicMolceule) {
        this.polyatomicMolceule = polyatomicMolceule;
    }

    /**
     * @return the linearMolceule
     */
    public boolean isLinearMolceule() {
        return linearMolceule;
    }

    /**
     * @param linearMolceule the linearMolceule to set
     */
    public void setLinearMolceule(boolean linearMolceule) {
        this.linearMolceule = linearMolceule;
    }

    /**
     * @return the moleculeSymmetryNumber
     */
    public double getMoleculeSymmetryNumber() {
        return moleculeSymmetryNumber;
    }

    /**
     * @param moleculeSymmetryNumber the moleculeSymmetryNumber to set
     */
    public void setMoleculeSymmetryNumber(double moleculeSymmetryNumber) {
        this.moleculeSymmetryNumber = moleculeSymmetryNumber;
    }
    
}
