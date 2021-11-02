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

import java.util.ArrayList;

/**
 * 
 * 
 * @author <a hrefTagsCollectionr@valoriza.com.br">Ary Junior</a>
 * @version $Id: TagsCollection.java,v 1.5 2008-12-15 12:06:50 aryjr Exp $
 * 
 */
public class TagsCollection {
    
    public static final String CVS_REVISION = "$Revision: 1.5 $";
    private ArrayList tags = null;

    public TagsCollection() {
        tags = new ArrayList();
    }

    public TagsCollection(ArrayList tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public Tag getTag(int ind) {
        return (Tag) tags.get(ind);
    }

    public int countTags() {
        return tags.size();
    }
}
/**
 * $Log: TagsCollection.java,v $
 * Revision 1.5  2008-12-15 12:06:50  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */