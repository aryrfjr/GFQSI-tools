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

/**
 *
 * Exception reported when an invalid symbol of a space group is used.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: InvalidCrystSpaceGroupException.java,v 1.4 2008-12-15 12:06:48 aryjr Exp $
 *
 */
public class InvalidCrystSpaceGroupException extends Exception {

    public static final String CVS_REVISION = "$Revision: 1.4 $";

    public InvalidCrystSpaceGroupException(final String message) {
        super(message);
    }
}
/**
 * $Log: InvalidCrystSpaceGroupException.java,v $
 * Revision 1.4  2008-12-15 12:06:48  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.3  2008-10-02 19:10:56  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */