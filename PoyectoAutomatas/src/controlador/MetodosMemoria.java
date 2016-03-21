/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import modelo.Automata;
import modelo.Estado;
import modelo.Transicion;
import vista.Frame;

/**
 *
 * @author Samael
 */
public class MetodosMemoria implements java.io.Serializable
{   
    MetodosDeCreacionLogica mtc=new MetodosDeCreacionLogica();

    public MetodosMemoria(MetodosDeCreacionLogica mcl) 
    {
        this.mtc=mcl;
    }

    public void escritura() throws IOException
    {
            LinkedList<Automata> aut= new LinkedList<>();
            aut=mtc.getAutomatas();
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("listaDeAutomatas.obj"));
            salida.writeObject(aut);
            salida.close();      
    }
    
    public  void lectura() throws IOException, ClassNotFoundException
    {
      ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("listaDeAutomatas.obj"));
      LinkedList<Automata> aut=new LinkedList<>();        
      aut=(LinkedList<Automata>)entrada.readObject();
      mtc.setAutomatas(aut);     
      entrada.close();
       
            
     
    }
    
    
    
    
    
}
