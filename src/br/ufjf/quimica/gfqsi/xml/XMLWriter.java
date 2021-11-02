/*
 * 
 * The GFQSI-XML Project : An easy way to manipulate XML files.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/xml
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
 * 
 */
package br.ufjf.quimica.gfqsi.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * A basic XML writer.
 * 
 * @version $Id: XMLWriter.java,v 1.4 2008-12-15 12:06:50 aryjr Exp $
 * @author <a href="mailto:aryjunior@gmail.com">Ary Junior</a>
 *
 */
public class XMLWriter {

    public static final String CVS_REVISION = "$Revision: 1.4 $";

    /**
     * 
     * Write a xml document based on a root tag.
     * 
     * @param root
     * @param fileName
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public void write(Tag root, String fileName) throws ParserConfigurationException, FileNotFoundException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        XMLSerializer xmlSer;
        OutputFormat out;
        // Try to create the document builder
        db = dbf.newDocumentBuilder();
        doc = db.newDocument();
        // Create the root tag by recursion
        doc.appendChild(createTag(root, doc));
        // Write out to file using the serializer
        out = new OutputFormat(doc);
        out.setIndenting(true);
        xmlSer = new XMLSerializer(new FileOutputStream(new File(fileName)), out);
        xmlSer.serialize(doc);
    }

    private Element createTag(Tag tag, Document doc) {
        // A recursive method to create tags with all children and attributes
        Element el = doc.createElement(tag.getName());
        // The atributes
        TagAttributesCollection tac = tag.getAttributes();
        TagAttribute ta;
        for (int inc = 0; inc < tac.atributesCount(); inc++) {
            ta = tac.getAtribute(inc);
            el.setAttribute(ta.getName(), ta.getValue());
        }
        // The tag content
        el.appendChild(doc.createTextNode(tag.getContent()));
        // The tag children by recursion
        TagsCollection tc = tag.getChildren();
        for (int inc = 0; inc < tc.countTags(); inc++) {
            el.appendChild(createTag(tc.getTag(inc), doc));
        }
        return el;
    }
}
/**
 * $Log: XMLWriter.java,v $
 * Revision 1.4  2008-12-15 12:06:50  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.3  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */