/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Graphics;
import java.util.LinkedList;
import modelo.Automata;
import controlador.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import vista.*;

/**
 *
 * @author Juan Camilo
 */
public class Panel extends javax.swing.JPanel {

    MetodosDeCreacionLogica mcl=new MetodosDeCreacionLogica();
    Automata automata = new Automata();
    ImageIcon  derechaIma= new ImageIcon(getClass().getResource("../imagenes/trianguloDer.png"));
    ImageIcon izquierdaIma= new ImageIcon(getClass().getResource("../imagenes/trianguloIzquier.png"));
    

    public void recibirMCL(MetodosDeCreacionLogica mtcl) {
        System.out.println("recibi MCL");
        this.mcl=mtcl;
    }

    public void setAutomata(Automata automata) {
        System.out.println("recibi automata");
        this.automata = automata;
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

         Graphics2D g2 = (Graphics2D)g;    
        //grosor de pincel
         g2.setStroke(new BasicStroke(2.0f));   
         //color de pincel
         g2.setColor(new Color(0,0,200));

        if (automata.getNombre() != null) 
        {
            graficarEstados(g);
            this.graficarTransicones(g2);
            
        }
        

       repaint();
    }  
    

    public void graficarEstados(Graphics g) {
       int a=0;
        
        for (int i = 0; i < mcl.getAutomatas().size(); i++)
        {
            if (mcl.getAutomatas().get(i).getNombre().equals(automata.getNombre())) 
            {
                a=i;
            }
        }
        
        ////agregar X Y a los estados y a las transiciones
        
        //para los estados 
        if (mcl.getAutomatas().get(a).getEstados().size()!=0) 
        {
            //if (mcl.getAutomatas().get(a).getEstados().get(0).getX()==0&&mcl.getAutomatas().get(a).getEstados().get(0).getY()==0) 
          //  {
                for (int i = 0; i < mcl.getAutomatas().get(a).getEstados().size(); i++) 
                {
                    if(mcl.getAutomatas().get(a).getEstados().get(i).isEsInicial())
                    {
                       mcl.getAutomatas().get(a).getEstados().get(i).setX(20);
                       mcl.getAutomatas().get(a).getEstados().get(i).setY(150); 
                    }
                    else
                    {
                        mcl.getAutomatas().get(a).getEstados().get(i).setX(60*(i+1));
                        mcl.getAutomatas().get(a).getEstados().get(i).setY(150);
                    }                    
                }
           // }
        }
       
        //para las transiciones
        for (int i = 0; i < mcl.getAutomatas().get(a).getTransiciones().size(); i++)
        {
            //para estados A
            for (int j = 0; j < mcl.getAutomatas().get(a).getEstados().size(); j++) 
            {
                if (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getNombre().equals(mcl.getAutomatas().get(a).getEstados().get(j).getNombre())) 
                {
                    mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().setX(mcl.getAutomatas().get(a).getEstados().get(j).getX());
                    mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().setY(mcl.getAutomatas().get(a).getEstados().get(j).getY());
                }
            }
            
            ///para estados B
            
            for (int j = 0; j < mcl.getAutomatas().get(a).getEstados().size() ; j++) 
            {
                 if (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getNombre().equals(mcl.getAutomatas().get(a).getEstados().get(j).getNombre())) 
                {
                    mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().setX(mcl.getAutomatas().get(a).getEstados().get(j).getX());
                    mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().setY(mcl.getAutomatas().get(a).getEstados().get(j).getY());
                }
            }
        }
        
        ////pintarlos 
        
        for(int i = 0; i < mcl.getAutomatas().get(a).getEstados().size(); i++) 
        {
            if (mcl.getAutomatas().get(a).getEstados().get(i).isEsAceptador()==true) 
            {
                if (mcl.getAutomatas().get(a).getEstados().get(i).isEsInicial()==true) 
                {   
                    g.drawLine(mcl.getAutomatas().get(a).getEstados().get(i).getX()-10, mcl.getAutomatas().get(a).getEstados().get(i).getY()+10, mcl.getAutomatas().get(a).getEstados().get(i).getX(),mcl.getAutomatas().get(a).getEstados().get(i).getY()+20);
                    g.drawLine(mcl.getAutomatas().get(a).getEstados().get(i).getX()-20, mcl.getAutomatas().get(a).getEstados().get(i).getY()+20, mcl.getAutomatas().get(a).getEstados().get(i).getX(), mcl.getAutomatas().get(a).getEstados().get(i).getY()+20);
                    g.drawLine(mcl.getAutomatas().get(a).getEstados().get(i).getX()-10, mcl.getAutomatas().get(a).getEstados().get(i).getY()+30,mcl.getAutomatas().get(a).getEstados().get(i).getX(), mcl.getAutomatas().get(a).getEstados().get(i).getY()+20);
                    g.setColor(Color.green);
                    g.drawOval(mcl.getAutomatas().get(a).getEstados().get(i).getX()+5,mcl.getAutomatas().get(a).getEstados().get(i).getY()+5, 30, 30);
                    g.setColor(Color.red);
                    g.drawOval(mcl.getAutomatas().get(a).getEstados().get(i).getX(),mcl.getAutomatas().get(a).getEstados().get(i).getY(), 40, 40);
                    g.drawString(mcl.getAutomatas().get(a).getEstados().get(i).getNombre()+"", mcl.getAutomatas().get(a).getEstados().get(i).getX()+15, mcl.getAutomatas().get(a).getEstados().get(i).getY()+23);
                } 
            }
            if (mcl.getAutomatas().get(a).getEstados().get(i).isEsAceptador()==true) 
            {
                if (mcl.getAutomatas().get(a).getEstados().get(i).isEsInicial()==false) 
                {   
                    g.setColor(Color.red);
                    g.drawOval(mcl.getAutomatas().get(a).getEstados().get(i).getX()+5,mcl.getAutomatas().get(a).getEstados().get(i).getY()+5, 30, 30);
                    g.setColor(Color.red);
                    g.drawOval(mcl.getAutomatas().get(a).getEstados().get(i).getX(),mcl.getAutomatas().get(a).getEstados().get(i).getY(), 40, 40);
                    g.drawString(mcl.getAutomatas().get(a).getEstados().get(i).getNombre()+"", mcl.getAutomatas().get(a).getEstados().get(i).getX()+15, mcl.getAutomatas().get(a).getEstados().get(i).getY()+23);
                } 
            }
            if (mcl.getAutomatas().get(a).getEstados().get(i).isEsAceptador()==false) 
            {
                if (mcl.getAutomatas().get(a).getEstados().get(i).isEsInicial()==true) 
                {   
                    g.drawLine(mcl.getAutomatas().get(a).getEstados().get(i).getX()-10, mcl.getAutomatas().get(a).getEstados().get(i).getY()+10, mcl.getAutomatas().get(a).getEstados().get(i).getX(),mcl.getAutomatas().get(a).getEstados().get(i).getY()+20);
                    g.drawLine(mcl.getAutomatas().get(a).getEstados().get(i).getX()-20, mcl.getAutomatas().get(a).getEstados().get(i).getY()+20, mcl.getAutomatas().get(a).getEstados().get(i).getX(), mcl.getAutomatas().get(a).getEstados().get(i).getY()+20);
                    g.drawLine(mcl.getAutomatas().get(a).getEstados().get(i).getX()-10, mcl.getAutomatas().get(a).getEstados().get(i).getY()+30,mcl.getAutomatas().get(a).getEstados().get(i).getX(), mcl.getAutomatas().get(a).getEstados().get(i).getY()+20);
                    g.setColor(Color.green);
                    g.drawOval(mcl.getAutomatas().get(a).getEstados().get(i).getX(),mcl.getAutomatas().get(a).getEstados().get(i).getY(), 40, 40);
                    g.drawString(mcl.getAutomatas().get(a).getEstados().get(i).getNombre()+"", mcl.getAutomatas().get(a).getEstados().get(i).getX()+15, mcl.getAutomatas().get(a).getEstados().get(i).getY()+23);
                } 
            }
            if (mcl.getAutomatas().get(a).getEstados().get(i).isEsAceptador()==false) 
            {
                if (mcl.getAutomatas().get(a).getEstados().get(i).isEsInicial()==false) 
                {   
                    g.setColor(Color.blue);
                    g.drawOval(mcl.getAutomatas().get(a).getEstados().get(i).getX(),mcl.getAutomatas().get(a).getEstados().get(i).getY(), 40, 40);
                    g.drawString(mcl.getAutomatas().get(a).getEstados().get(i).getNombre()+"", mcl.getAutomatas().get(a).getEstados().get(i).getX()+15, mcl.getAutomatas().get(a).getEstados().get(i).getY()+23);
                } 
            } 
        }
        
        
    }

    public void graficarTransicones(Graphics2D g) 
    {
         int a=0;
         
        
        for (int i = 0; i < mcl.getAutomatas().size(); i++)
        {
            if (mcl.getAutomatas().get(i).getNombre().equals(automata.getNombre())) 
            {
                a=i;
            }
        }
        
            int canCurva[]=new int[mcl.getAutomatas().get(a).getLenguaje().size()];
            
            for (int j = 0; j < mcl.getAutomatas().get(a).getLenguaje().size(); j++) 
            {
                if (j==0) 
                {
                  canCurva[j]=5;   
                }
                if (j!=0) 
                {
                   canCurva[j]=canCurva[j-1]+40; 
                }
                
            }
            
            
            
        
        for (int i = 0; i < mcl.getAutomatas().get(a).getTransiciones().size(); i++) 
        {            
            // un vector cada posicion del  es una posicion en la lista de simbolos
            //luego recorro eso 
            for (int j = 0; j <mcl.getAutomatas().get(a).getLenguaje().size(); j++) 
            {
                if (mcl.getAutomatas().get(a).getLenguaje().get(j).equals(mcl.getAutomatas().get(a).getTransiciones().get(i).getSimbolo())) 
                {
                    
                    // create new QuadCurve2D.Float
                    QuadCurve2D q = new QuadCurve2D.Float();
                    // draw QuadCurve2D.Float with set coordinates
                    q.setCurve(mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()+40, mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getY()+20, (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()+(mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()+40))/2,canCurva[j], mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX(),  mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getY()+20);
                    g.setColor(Color.BLACK);
                    g.draw(q);
                    g.drawString(mcl.getAutomatas().get(a).getTransiciones().get(i).getSimbolo()+"", (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()+(mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()+40))/2, canCurva[j]+75);
                    
                    if (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()<mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()) 
                    {
                        
                         g.drawImage(derechaIma.getImage(),mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()-10 , mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getY()+10, 10, 10, this); 
                    }
                    if (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()>mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()) 
                    {
                        g.drawImage(izquierdaIma.getImage(),mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()+3 , mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getY()+10, 10, 10, this); 
                    }
                    if (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()==mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()) 
                    {
                        g.drawImage(derechaIma.getImage(),mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()-10 , mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getY()+10, 10, 10, this); 
                    }
                    
                   //g.drawArc(mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()+40, mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getY()+20, (mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoB().getX()-(mcl.getAutomatas().get(a).getTransiciones().get(i).getEstadoA().getX()+40)),canCurva[j],180,180); 
                }
            }
            
            
       
        }
        
        
        
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(0, 0, 0)));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        
      
        
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
