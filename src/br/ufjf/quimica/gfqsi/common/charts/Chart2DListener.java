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
package br.ufjf.quimica.gfqsi.common.charts;

/**
 *
 * A listener for events in a chart 2D.
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: Chart2DListener.java,v 1.9 2009-09-08 10:31:37 aryjr Exp $
 * 
 */
public interface Chart2DListener {

    public static final int X_VALUE = 0;
    public static final int Y_VALUE = 1;
    public static final int INDEX = 2;
    public static final String CVS_REVISION = "$Revision: 1.9 $";

    public void mouseMoved(double[] point);

    public void mouseClicked(double[] point);
}
/**
 * $Log: Chart2DListener.java,v $
 * Revision 1.9  2009-09-08 10:31:37  aryjr
 * Espectro de IR simulado pronto para ser testado.
 *
 * Revision 1.8  2009-08-28 21:25:13  aryjr
 * Teste para o grafico 2D e novas funcionalidades.
 *
 * Revision 1.7  2008-12-15 12:06:50  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.6  2008-10-02 19:10:58  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */
