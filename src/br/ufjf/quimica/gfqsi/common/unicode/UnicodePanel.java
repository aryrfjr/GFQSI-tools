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
 * 
 */
package br.ufjf.quimica.gfqsi.common.unicode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * A panel for display the unicode character table.<br>
 * From: http://www.java2s.com/Code/Java/Development-Class/UnicodeDisplay.htm
 * 
 * @author Ary Junior <a href="mailto:aryjunior@gmail.com">aryjunior@gmail.com</a>
 * @version $Id: UnicodePanel.java,v 1.5 2008-12-15 12:06:51 aryjr Exp $
 * 
 */
public class UnicodePanel extends JPanel implements MouseListener {
    
    public static final String CVS_REVISION = "$Revision: 1.5 $";
    private char base; // What character we start the display at
    private static final int lineheight = 25;
    private static final int charspacing = 20;
    private static final int x = 10;
    private static final int y = 20;
    private int selectedLine = 0;
    private int selectedColumn = 0;
    private char selectedChar;

    public UnicodePanel() {
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, x + 16 * charspacing, y + 16 * lineheight);
        g.setColor(Color.BLACK);
        int start = (int) base & 0xFFF0; // Start on a 16-character boundary
        char[] c = new char[1];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                c[0] = (char) (start + j * 16 + i);
                g.drawChars(c, 0, 1, x + i * charspacing, y + j * lineheight);
                if (selectedLine == i && selectedColumn == j) {
                    g.drawRect(x + i * charspacing - charspacing / 4, y + j * lineheight - 3 * lineheight / 4, charspacing, lineheight);
                    selectedChar = c[0];
                }
            }
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < 16; i++) {
            if (e.getX() >= x + i * charspacing && e.getX() <= x + (i + 1) * charspacing) {
                selectedLine = i;
                break;
            }
        }
        for (int j = 0; j < 16; j++) {
            if (e.getY() >= y + (j - 1) * lineheight && e.getY() <= y + j * lineheight) {
                selectedColumn = j;
                break;
            }
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void setBase(char base) {
        this.base = base;
        repaint();
    }

    /** Custom components like this one should always have this method */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(x + 16 * charspacing, y + 16 * lineheight);
    }

    public char getSelectedChar() {
        return selectedChar;
    }
}
/**
 * $Log: UnicodePanel.java,v $
 * Revision 1.5  2008-12-15 12:06:51  aryjr
 * Atualizando cabecalho, avaliando a populacao inicial, testes de unidade e mais o novo algoritmo genetico XAS.
 *
 * Revision 1.4  2008-10-02 19:10:59  aryjr
 * Keyword Log no final dos arquivos.
 *
 *
 */