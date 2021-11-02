/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.quimica.gfqsi.thermophonons;

import br.ufjf.quimica.gfqsi.common.chem.Molecule;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: MoleculeThermodynamicsInformation.java,v 1.2 2010-10-22 14:57:09 aryjr Exp $
 *
 */
public interface MoleculeThermodynamicsInformation {

    public Molecule getMolecule();
    public double[] getTranslationalInternalEnergies();
    public double[] getTranslationalEntropies();
    public double[] getTranslationalPartitionFunctions();
    public double[] getRotationalInternalEnergies();
    public double[] getRotationalEntropies();
    public double[] getRotationalPartitionFunctions();

}
