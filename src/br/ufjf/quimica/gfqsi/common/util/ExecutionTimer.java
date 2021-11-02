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
package br.ufjf.quimica.gfqsi.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: ExecutionTimer.java,v 1.4 2008-12-15 12:06:47 aryjr Exp $
 * 
 */
public class ExecutionTimer {

    public static final String CVS_REVISION = "$Revision: 1.4 $";
    private long start;
    private long end;

    public ExecutionTimer() {
        reset();
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public void reset() {
        start = 0;
        end = 0;
    }

    public long duration() {
        return end - start;
    }

    public String getFormatedDuration() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        int days = (int) (duration() / (24 * 60 * 60 * 1000));
        return days + " days + " + df.format(new Date(duration()));
    }
}
/**
 * $Log: ExecutionTimer.java,v $
 * Revision 1.4  2008-12-15 12:06:47  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.3  2008-12-08 16:33:59  aryjr
 * Corrigindo o tempo de duracao.
 *
 * Revision 1.2  2008-12-08 16:32:30  aryjr
 * Corrigindo o tempo de duracao.
 *
 * Revision 1.1  2008-10-10 15:44:47  aryjr
 * OK (10/10/08) - Marcar o tempo de cada calculo ab initio e o tempo total do GA.
 *
 *
 */