/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Graphics;
import java.util.LinkedList;
import modelo.Automata;
    
    


/**
 *
 * @author Juan Camilo
 */
public class Panel extends javax.swing.JPanel {

    Automata automata = new Automata();

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }

    

    public void nombre(){
        System.out.println("El nombre del automata desde el panel es: "+automata.getNombre());
    }
    
    /**
     * Creates new form Panel
     */
    public Panel() {
        initComponents();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        g.drawRect(5, 310, 540, 300);
        
        if(automata.getNombre()!=null){
            int cadaCuanto=0;
            int contador=5;
            for (int i = 0; i < automata.getEstados().size(); i++) {
                g.drawOval(contador+=cadaCuanto+5, 400, 30, 30);
                g.drawString(automata.getEstados().get(i).getNombre(), contador+15, 415);
                cadaCuanto=(500/automata.getEstados().size());
            }
        }
        
        
        repaint();
    }

    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 153, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
