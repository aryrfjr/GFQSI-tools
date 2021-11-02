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

import br.ufjf.quimica.gfqsi.xml.Tag;
import br.ufjf.quimica.gfqsi.xml.TagsCollection;
import br.ufjf.quimica.gfqsi.xml.XMLDOMParser;
import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * The chemical elements.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: ChemistryElements.java,v 1.1 2009-01-06 17:02:08 aryjr Exp $
 *
 */
public class ChemistryElements {
    
    public static final String CVS_REVISION = "$Revision: 1.1 $";
    public static HashMap<String, ChemistryElement> collectionMap = new HashMap();
    public static ArrayList<ChemistryElement> collectionList = new ArrayList();
    private static HashMap<String, Color> colors = new HashMap();
    private static Color[] theColors = new Color[]{Color.BLUE, Color.RED, Color.WHITE, Color.GREEN, Color.CYAN, Color.GRAY, Color.MAGENTA, Color.ORANGE, Color.YELLOW};
    private static int incColor = 0;
    

    static {
        try {
            XMLDOMParser xmlParser = new XMLDOMParser();
            InputStream elXml = Thread.currentThread().getContextClassLoader().getResourceAsStream("elements.xml");
            xmlParser.parse(elXml);
            Tag root = xmlParser.getRoot();
            TagsCollection elements = root.getChildren();
            Tag tag;
            ChemistryElement e;
            for (int inc = 0; inc < elements.countTags(); inc++) {
                tag = elements.getTag(inc);
                e = new ChemistryElement();
                e.setName(tag.getAttribute("name").getValue());
                e.setAtomicNumber(Integer.parseInt(tag.getAttribute("z").getValue()));
                e.setSymbol(tag.getAttribute("symbol").getValue());
                e.setWeight(Double.parseDouble(tag.getAttribute("weight").getValue()));
                collectionMap.put(e.getSymbol(), e);
                collectionList.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChemistryElement> getCollectionList() {
        return collectionList;
    }

    public static Color getColor(String element) {
        if (colors.get(element) == null) {
            colors.put(element, theColors[incColor++]);
        }
        return colors.get(element);
    }
}
/**
 * $Log: ChemistryElements.java,v $
 * Revision 1.1  2009-01-06 17:02:08  aryjr
 * Trabalho em casa durante o final do ano.
 *
 * Revision 1.5  2008-12-15 12:06:49  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */