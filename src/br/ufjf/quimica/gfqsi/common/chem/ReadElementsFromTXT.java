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
package br.ufjf.quimica.gfqsi.common.chem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: ReadElementsFromTXT.java,v 1.5 2008-12-15 12:06:49 aryjr Exp $
 * 
 */
public class ReadElementsFromTXT {
    
    public static final String CVS_REVISION = "$Revision: 1.5 $";

    public static void main(String[] args) {
        try {
            FileReader fr = null;
            fr = new FileReader("c:\\dev\\GFQSI-Common\\src\\elements.txt");
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                StringTokenizer stk = new StringTokenizer(br.readLine(), "	");
                System.out.println("<element name=\"" + stk.nextToken() + "\" z=\"" + stk.nextToken() + "\" symbol=\"" + stk.nextToken() + "\" weight=\"" + stk.nextToken() + "\" />");
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadElementsFromTXT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
/**
 * $Log: ReadElementsFromTXT.java,v $
 * Revision 1.5  2008-12-15 12:06:49  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */