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

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * A basic XML DOM parser.
 * 
 * @version $Id: XMLDOMParser.java,v 1.5 2009-03-02 13:16:08 aryjr Exp $
 * @author <a href="mailto:aryjunior@gmail.com">Ary Junior</a>
 *
 */
public class XMLDOMParser {
    
    public static final String CVS_REVISION = "$Revision: 1.5 $";
    private Tag root = null;

    public void parse(String file) throws IOException, XMLDOMParserException {
        parse(new FileInputStream(file));
    }

    public void parse(InputStream file) throws IOException, XMLDOMParserException {
        DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource((InputStream) file));
            Document doc = parser.getDocument();
            root = createTag(doc.getDocumentElement());
            loadTag(doc.getDocumentElement().getChildNodes(), root);
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new XMLDOMParserException("Exception when parse.\n" + e.toString());
        }
    }

    public Tag getRoot() {
        return root;
    }

    private void loadTag(NodeList children, Tag father) throws IOException, XMLDOMParserException {
        Tag tag = null;
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                tag = createTag(children.item(i));
                father.addChild(tag);
                loadTag(children.item(i).getChildNodes(), tag);
            } else if (children.item(i).getNodeType() == Node.TEXT_NODE && !children.item(i).getNodeValue().trim().equals("") && children.item(i).getParentNode() != null && children.item(i).getParentNode().getNodeName().equalsIgnoreCase("fragmento-sql")) {
                father.setContent(children.item(i).getNodeValue() == null ? "" : children.item(i).getNodeValue().trim());
            }
        }
    }

    private Tag createTag(Node tag) {
        Tag ret = new Tag();
        ret.setName(tag.getNodeName());
        ret.setContent(tag.getFirstChild() == null || tag.getFirstChild().getNodeValue() == null ? "" : tag.getFirstChild().getNodeValue().trim());
        loadTagAttributes(ret, tag);
        return ret;
    }

    private void loadTagAttributes(Tag tag, Node node) {
        NamedNodeMap attributes = node.getAttributes();
        for (int inc = 0; inc < attributes.getLength(); inc++) {
            tag.addAttribute(new TagAttribute(attributes.item(inc).getNodeName(), attributes.item(inc).getNodeValue()));
        }
    }
}
/**
 * $Log: XMLDOMParser.java,v $
 * Revision 1.5  2009-03-02 13:16:08  aryjr
 * Testes de unidade com o algoritmo hibrido.
 *
 * Revision 1.4  2008-12-15 12:06:50  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.3  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */