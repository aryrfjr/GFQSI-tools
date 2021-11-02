/*
 *
 * The GFQSI-ExternalCodeGlue Project : Classes to read and write external softwares files.
 *
 * Project Info:  http://gfqsi.quimica.ufjf.br/ecglue
 * Project Lead:  Ary Rodrigues Ferreira Junior
 *
 * (C) Copyright 2007 - 2010 by Ary Junior and GFQSI-UFJF (Grupo de Fisico Quimica de Solidos e Interfaces da UFJF).
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
package br.ufjf.quimica.gfqsi.ecglue;

/**
 *
 * General exception for inconsistent data when open files.
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: InconsistentDataException.java,v 1.1 2010-04-20 19:36:44 aryjr Exp $
 */
public class InconsistentDataException extends Exception {

    public InconsistentDataException() {
    }

    public InconsistentDataException(Throwable cause) {
        super(cause);
    }

    public InconsistentDataException(String message) {
        super(message);
    }

    public InconsistentDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
