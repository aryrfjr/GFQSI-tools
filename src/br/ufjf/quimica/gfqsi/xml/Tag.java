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

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * 
 * A XML tag abstraction for.
 * 
 * @version $Id: Tag.java,v 1.7 2008-12-15 12:06:50 aryjr Exp $
 * @author <a href="mailto:aryjunior@gmail.com">Ary Junior</a>
 * 
 */
public class Tag {
    
    public static final String CVS_REVISION = "$Revision: 1.7 $";
    private HashMap attributes = new HashMap();
    private String name;
    private String content;
    private Tag father;
    private ArrayList children = new ArrayList();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public void addAttribute(TagAttribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }

    public void addAttribute(String name, String value) {
        TagAttribute attribute = new TagAttribute(name, value);
        attributes.put(name, attribute);
    }

    public TagAttribute getAttribute(String name) {
        return (TagAttribute) attributes.get(name);
    }

    public TagAttributesCollection getAttributes() {
        TagAttributesCollection ret = new TagAttributesCollection();
        Iterator chaves = attributes.keySet().iterator();
        while (chaves.hasNext()) {
            ret.addAtribute((TagAttribute) attributes.get((String) chaves.next()));
        }
        return ret;
    }

    public void setFather(Tag father) {
        this.father = father;
    }

    public Tag getFather() {
        return father;
    }

    public void addChild(Tag child) {
        child.setFather(this);
        children.add(child);
    }

    public Tag getChild(int ind) {
        return (Tag) children.get(ind);
    }

    /**
     * 
     * Returns the first named child.
     * 
     */
    public Tag getChild(String name) {
        for (int inc = 0; inc < children.size(); inc++) {
            if (((Tag) children.get(inc)).getName().equals(name)) {
                return (Tag) children.get(inc);
            }
        }
        return null;
    }

    /**
     * 
     * Returns the first named child.
     * 
     */
    public Tag getChild(String name, String attribute, String value) {
        Tag child = null;
        for (int inc = 0; inc < children.size(); inc++) {
            child = (Tag) children.get(inc);
            if (child.getName().equals(name) && child.getAttribute(attribute).getValue().equals(value)) {
                return (Tag) children.get(inc);
            }
        }
        return null;
    }

    public TagsCollection getChildren() {
        return new TagsCollection(children);
    }

    /**
     * 
     * Returns the first named child.
     *
     */
    public TagsCollection getChildren(String name) {
        TagsCollection ret = new TagsCollection();
        for (int inc = 0; inc < children.size(); inc++) {
            if (((Tag) children.get(inc)).getName().equals(name)) {
                ret.addTag((Tag) children.get(inc));
            }
        }
        return ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
/**
 * $Log: Tag.java,v $
 * Revision 1.7  2008-12-15 12:06:50  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.6  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */