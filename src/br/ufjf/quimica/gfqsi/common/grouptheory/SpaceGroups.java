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
package br.ufjf.quimica.gfqsi.common.grouptheory;

import br.ufjf.quimica.gfqsi.xml.Tag;
import br.ufjf.quimica.gfqsi.xml.TagsCollection;
import br.ufjf.quimica.gfqsi.xml.XMLDOMParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: SpaceGroups.java,v 1.4 2008-12-15 12:06:48 aryjr Exp $
 *
 */
public class SpaceGroups {

    public static final String CVS_REVISION = "$Revision: 1.4 $";
    public static HashMap<String, SpaceGroup> collectionMap = new HashMap();
    public static HashMap<String, SpaceGroup> collectionMapByNumber = new HashMap();
    public static SpaceGroup[] collection;
    

    static {
        try {
            XMLDOMParser xmlParser = new XMLDOMParser();
            InputStream spgrpXml = Thread.currentThread().getContextClassLoader().getResourceAsStream("cryst-space-groups.xml");
            xmlParser.parse(spgrpXml);
            Tag root = xmlParser.getRoot();
            TagsCollection groups = root.getChildren();
            Tag tag;
            SpaceGroup sg;
            collection = new SpaceGroup[groups.countTags()];
            for (int inc = 0; inc < groups.countTags(); inc++) {
                tag = groups.getTag(inc);
                sg = new SpaceGroup();
                sg.setNumber(Integer.parseInt(tag.getAttribute("number").getValue()));
                sg.setCNSName(tag.getChild("CNS-name").getContent());
                sg.setPDBName(tag.getChild("PDB-name").getContent());
                loadSymmetryOperations(sg);
                collectionMap.put(sg.getCNSName(), sg);
                collectionMapByNumber.put(String.valueOf(sg.getNumber()), sg);
                collection[inc] = sg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadSymmetryOperations(SpaceGroup spaceGroup) throws IOException {
        InputStream symopis = Thread.currentThread().getContextClassLoader().getResourceAsStream("symop/symop." + spaceGroup.getNumber());
        BufferedReader bf = new BufferedReader(new InputStreamReader(symopis));
        String line, token;
        StringTokenizer stk;
        SymmetryOperation symop;
        if (bf.ready()) {
            bf.readLine();
        }
        while (bf.ready()) {
            line = bf.readLine();
            stk = new StringTokenizer(line, ",");
            symop = new SymmetryOperation();
            token = stk.nextToken();
            symop.setX(token.substring(1));
            token = stk.nextToken();
            symop.setY(token);
            token = stk.nextToken();
            symop.setZ(token.substring(0, token.length() - 1));
            spaceGroup.addSymmetryOperation(symop);
        }
    }
}
/**
 * $Log: SpaceGroups.java,v $
 * Revision 1.4  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.3  2008-10-02 19:10:57  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */