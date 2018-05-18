package com.company;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class Main {
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel emptyLabel = new JLabel("Add document");
        emptyLabel.setOpaque(true);
        emptyLabel.setVerticalAlignment(JLabel.TOP);
        emptyLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        emptyLabel.setBorder(BorderFactory.createLineBorder(Color.blue));
        emptyLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        emptyLabel.setPreferredSize(new Dimension(500, 500));
        JTextField text = new JTextField(10);
        text.setSize(150,100);
        //text.setBounds(50,50);
        frame.add(text);
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }

    public static void main(String[] args){
        Catalog catalog = new Catalog();
        try{
            catalog.add (new Book("The Art of Computer Programming", "d:\\books\\programming\\tacp.ps",2017, "Donald E. Knuth"));
            catalog.add (new Article("Mastering the Game of Go without Human Knowledge", "d:\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
            catalog.add (new Article("How to learn Java", "d:\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
        }catch(RuntimeException e){
            throw new RuntimeException("Eroare la crearea obiectelor");
        }
       
	catalog.list();
        System.out.println("\n");
        catalog.save();
        System.out.println("\n");
        catalog.load();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
        
        
        
        
     
    }


}
