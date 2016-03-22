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

    public void nombre() {
        System.out.println("El nombre del automata desde el panel es: " + automata.getNombre());
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

        if (automata.getNombre() != null) {
            graficarUno(g);
        }
        //g.drawArc(WIDTH, WIDTH, WIDTH, HEIGHT, contador, cadaCuanto);

        repaint();
    }

    public void graficarUno(Graphics g) {
        int cadaCuanto = 0;
        int contador = 5;
        for (int i = 0; i < automata.getEstados().size(); i++) {
            automata.getEstados().get(i).setX(contador += cadaCuanto + 5);
            automata.getEstados().get(i).setY(400);
            g.drawOval(automata.getEstados().get(i).getX(), automata.getEstados().get(i).getY(), 30, 30);
            g.drawString(automata.getEstados().get(i).getNombre(), contador + 15, 415);
            cadaCuanto = (500 / automata.getEstados().size());
        }
        
        
        graficarTransicones(g);
    }

    public void graficarTransicones(Graphics g) {
        int contador = (int)(automata.getEstados().size()/automata.getLenguaje().size());
        for (int i = 0; i < automata.getTransiciones().size(); i++) {

            //System.out.println("Aqui: "+ automata.getEstados().indexOf(automata.getTransiciones().get(i).getEstadoB())+" la i esta en"+i+" Se busco el estado : "+automata.getTransiciones().get(i).getEstadoB().getNombre());
            if (automata.getEstados().indexOf(automata.getTransiciones().get(i).getEstadoA()) < automata.getEstados().indexOf(automata.getTransiciones().get(i).getEstadoB())) {
                int x1 = automata.getTransiciones().get(i).getEstadoA().getX() + 30;
                int y1 = automata.getTransiciones().get(i).getEstadoA().getY() + 15;
                int x2 = automata.getTransiciones().get(i).getEstadoB().getX() - 15;
                int y2 = automata.getTransiciones().get(i).getEstadoB().getY() + 15;
                g.drawLine(x1, y1, x2, y2);

                int x[] = {x2, x2, x2 + 10};
                int y[] = {y2 - 10, y2 + 10, y2};
                g.drawPolygon(x, y, 3);
            } else {
                int x1 = automata.getTransiciones().get(i).getEstadoA().getX();
                int y1 = automata.getTransiciones().get(i).getEstadoA().getY() + 15;
                int x2 = automata.getTransiciones().get(i).getEstadoB().getX() + 35;
                int y2 = automata.getTransiciones().get(i).getEstadoB().getY() + 15;
                g.drawLine(x1, y1, x2, y2);

                int x[] = {x2 + 10, x2 + 10, x2};
                int y[] = {y2 - 10, y2 + 10, y2};
                g.drawPolygon(x, y, 3);
            }
        }

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
