/*
 *
 * The GFQSI-Common Project : Common classes for solid state applications.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/common
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
package br.ufjf.quimica.gfqsi.common.cryst;

import java.util.ArrayList;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: CrystSpaceGroup.java,v 1.4 2008-12-15 12:06:48 aryjr Exp $
 *
 */
public class CrystSpaceGroup {
    
    public static final String CVS_REVISION = "$Revision: 1.4 $";
    private int number;
    private String CNSName;
    private String PDBName;
    private ArrayList<CrystSymmetryOperation> symops = new ArrayList();

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCNSName() {
        return CNSName;
    }

    public void setCNSName(String CNSName) {
        this.CNSName = CNSName;
    }

    public String getPDBName() {
        return PDBName;
    }

    public void setPDBName(String PDBName) {
        this.PDBName = PDBName;
    }

    public void addSymmetryOperation(CrystSymmetryOperation symop) {
        symops.add(symop);
    }

    public CrystSymmetryOperation getSymmetryOperation(int ind) {
        return symops.get(ind);
    }

    public int countSymmetriOperations() {
        return symops.size();
    }
}
/**
 * $Log: CrystSpaceGroup.java,v $
 * Revision 1.4  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.3  2008-10-02 19:10:57  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */