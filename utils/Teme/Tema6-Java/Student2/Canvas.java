/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Eduard
 */

public class Canvas extends JFrame {
    private JFrame mainMap;
    private Polygon poly;

    public Canvas(){

        initComponents();
    
}
    
    private void initComponents(){

        mainMap = new JFrame();
        mainMap.setSize(800, 600);
        mainMap.setResizable(false);

        mainMap.setVisible(true);
        mainMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int xPoly[] = {150, 250, 325, 375, 450, 275, 100};
        int yPoly[] = {150, 100, 125, 225, 250, 375, 300};

        poly = new Polygon(xPoly, yPoly, xPoly.length);
        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.drawPolygon(poly);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }
        };
        mainMap.add(p);
        mainMap.pack();
        mainMap.setVisible(true);
        
        MouseAdapter ma;
        ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                if (poly.contains(me.getPoint())) {
                    System.out.println("Clicked polygon");
                    
                    
                }
                
                
                
            }
            
            
        };
        p.addMouseListener(ma);
        mainMap.add(p);
        mainMap.pack();
        mainMap.setVisible(true);

        
    }
}




