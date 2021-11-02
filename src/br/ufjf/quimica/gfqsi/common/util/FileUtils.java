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
package br.ufjf.quimica.gfqsi.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: FileUtils.java,v 1.6 2008-12-15 12:06:47 aryjr Exp $
 * 
 */
public class FileUtils implements Cloneable {
    
    public static final String CVS_REVISION = "$Revision: 1.6 $";
    private String fileName;

    public FileUtils(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> getLines() throws FileNotFoundException, IOException {
        ArrayList<String> lines = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(getFileName()));
        while (br.ready()) {
            lines.add(br.readLine());
        }
        return lines;
    }

    public ArrayList<String> getLines(String key) throws FileNotFoundException, IOException {
        ArrayList<String> lines = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(getFileName()));
        String line;
        while (br.ready()) {
            line = br.readLine();
            if (line.indexOf(key) >= 0) {
                lines.add(line);
            }
        }
        return lines;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public FileUtils clone() {
        FileUtils clone = new FileUtils(new String(fileName.getBytes().clone()));
        return clone;
    }
}
/**
 * $Log: FileUtils.java,v $
 * Revision 1.6  2008-12-15 12:06:47  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.5  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */