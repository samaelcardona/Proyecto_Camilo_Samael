/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Automata;
import modelo.Estado;
import modelo.Transicion;
import vista.Frame;


/*
 Esta clase esta creada para manejar la construccion del automata, con todos sus atributos.

 */
/**
 *
 * @author Samael
 */
public class MetodosDeCreacionLogica implements java.io.Serializable {

    ////codigo inicio de MetodosDeCreacionLogica
    //lista de automatas esta para guardar los automatas creados, tiene un get en la parte final de la clase
    //para extraer todos los automatas creados.
    LinkedList<Automata> automatas = new LinkedList<>();

    ///metodo para separar las cadenas de string y volverlas una lista, este recibe una cadena y el tipo de la cadena 
    //para saber de que tipo devolver la lista.
    public LinkedList separarCadenas(String cadena, String TipoDividir) {
        String cadenaSeparada[] = cadena.split(",");

        LinkedList<Object> lista = new LinkedList();

        if (!cadenaSeparada[0].equals("")) {
            if ("estado".equals(TipoDividir)) {
                for (int i = 0; i < cadenaSeparada.length; i++) {
                    lista.add(new Estado(cadenaSeparada[i], false, false, 0, 0));
                }
            }

            if ("string".equals(TipoDividir)) {
                for (int i = 0; i < cadenaSeparada.length; i++) {
                    lista.add(cadenaSeparada[i]);
                }
            }
        }

        return lista;
    }

    //verifica que todos los estados aceptadores esten en la lista de estados si no lo estan muestra un mensaje 
    //y no crea el automata
    public boolean verificarexistencia(LinkedList<Estado> listadeestadosaceptadores, LinkedList<Estado> listadeestados, Estado estadoini) {

        boolean primeraVerificacion = true;
        boolean segundaVerificacion = false;
        LinkedList<Estado> copiaListaEstadosAceptadores = new LinkedList();
        //copiaListaEstadosAceptadores=listadeestadosaceptadores;

        for (int i = 0; i < listadeestadosaceptadores.size(); i++) {
            copiaListaEstadosAceptadores.add(listadeestadosaceptadores.get(i));
        }

        for (int i = 0; i < listadeestados.size(); i++) {
            for (int j = 0; j < copiaListaEstadosAceptadores.size(); j++) {
                if (listadeestados.get(i).getNombre().equals(copiaListaEstadosAceptadores.get(j).getNombre())) {
                    copiaListaEstadosAceptadores.remove(j);
                }

            }

        }
        if (copiaListaEstadosAceptadores.size() > 0) {
            primeraVerificacion = false;
            System.out.println("verifique sus estados");
        }

        if (primeraVerificacion == true) {
            for (int i = 0; i < listadeestados.size(); i++) {
                if (listadeestados.get(i).getNombre().equals(estadoini.getNombre())) {
                    segundaVerificacion = true;
                }
            }

            if (segundaVerificacion == true) {
                return true;
            } else {
                System.out.println("Verifique su estado inicial");
            }
        }

        return false;
    }

    ///Este metodo es utilizado para crear un automata finito determinista o automata finito no determinista
    //recibe todos los parametros en una cadena, esta cadena llama al metodo separarCadenas para crear las listas 
    //necesarias para el automata y llama al metodo de verificarExistencia para que este verifique si concuerdan
    //los estados aceptadores y el estado inicial con la lista de estados agregada, por ultimo crea un automata
    //y lo agrega en la lista de automatas.
    public void crearAutomaatAFD_O_AFN(String nombre, String tipoAutomata, String estados, String lenguaje, String estadoinicial, String estadosaceptadores) {
        boolean variable = false;

        if (!automatas.isEmpty()) {
            for (int i = 0; i < automatas.size(); i++) {
                if (automatas.get(i).getNombre().equals(nombre)) {
                    variable = true;
                }
            }
        }

        if (variable == true) {
            System.out.println("El automata ya existe, verifique nombre");
        } else {

            LinkedList<Estado> listadeestados = separarCadenas(estados, "estado");
            LinkedList<String> listalenguaje = separarCadenas(lenguaje, "string");
            LinkedList<Estado> listadeestadosaceptadores = separarCadenas(estadosaceptadores, "estado");

            //cambia el tipo del estado a aceptador en la lista de estados y en la lista de aceptadores
            for (int i = 0; i < listadeestadosaceptadores.size(); i++) {
                for (int j = 0; j < listadeestados.size(); j++) {
                    if (listadeestados.get(j).getNombre().equals(listadeestadosaceptadores.get(i).getNombre())) {
                        listadeestados.get(j).setEsAceptador(true);
                        listadeestadosaceptadores.get(i).setEsAceptador(true);
                    }
                }
            }

            //cambia de tipo en la lista de estados a inicial para el estado que es inicial
            Estado estadoini = new Estado();

            for (int i = 0; i < listadeestados.size(); i++) {
                if (listadeestados.get(i).getNombre().equals(estadoinicial)) {
                    listadeestados.get(i).setEsInicial(true);
                    estadoini = listadeestados.get(i);
                }
            }

            if (verificarexistencia(listadeestadosaceptadores, listadeestados, estadoini) == true) {
                Automata automataNuevo = new Automata(nombre, tipoAutomata, listadeestados, listalenguaje, estadoini, listadeestadosaceptadores);
                automatas.add(automataNuevo);
                System.out.println("El automata se creo exitosamente");
            }
        }

    }

    //metodo para crear automatas finitos no deterministas con transiciones E
    //recibe todos los parametros en una cadena, esta cadena llama al metodo separarCadenas para crear las listas 
    //necesarias para el automata en la lista de lenguaje se le agrega la transicion E para que se identifique 
    //como un AFN_E luego de agregar esto se llama al metodo de verificarExistencia para que este verifique si concuerdan
    //los estados aceptadores y el estado inicial con la lista de estados agregada, por ultimo crea un automata
    //y lo agrega en la lista de automatas.
    public void crearAutomaatAFN_E(String nombre, String tipoAutomata, String estados, String lenguaje, String estadoinicial, String estadosaceptadores) {
        boolean variable = false;

        if (!automatas.isEmpty()) {
            for (int i = 0; i < automatas.size(); i++) {
                if (automatas.get(i).getNombre().equals(nombre)) {
                    variable = true;
                }
            }
        }

        if (variable == true) {
            System.out.println("El automata ya existe, verifique nombre");
        } else {

            LinkedList<Estado> listadeestados = separarCadenas(estados, "estado");
            LinkedList<String> listalenguaje = separarCadenas(lenguaje, "string");
            listalenguaje.add("E");
            LinkedList<Estado> listadeestadosaceptadores = separarCadenas(estadosaceptadores, "estado");

            //cambia el tipo del estado a aceptador en la lista de estados y en la lista de aceptadores
            for (int i = 0; i < listadeestadosaceptadores.size(); i++) {
                for (int j = 0; j < listadeestados.size(); j++) {
                    if (listadeestados.get(j).getNombre().equals(listadeestadosaceptadores.get(i).getNombre())) {
                        listadeestados.get(j).setEsAceptador(true);
                        listadeestadosaceptadores.get(i).setEsAceptador(true);
                    }
                }
            }

            //cambia de tipo en la lista de estados a inicial para el estado que es inicial
            Estado estadoini = new Estado();

            for (int i = 0; i < listadeestados.size(); i++) {
                if (listadeestados.get(i).getNombre().equals(estadoinicial)) {
                    listadeestados.get(i).setEsInicial(true);
                    estadoini = listadeestados.get(i);
                }
            }

            if (verificarexistencia(listadeestadosaceptadores, listadeestados, estadoini) == true) {
                Automata automataNuevo = new Automata(nombre, tipoAutomata, listadeestados, listalenguaje, estadoini, listadeestadosaceptadores);
                automatas.add(automataNuevo);
                System.out.println("El automata se creo exitosamente");
            }
        }
    }

    ///verifica que no haya una repeticion de estados o simbolos al agregarlos con el metodo agregarAlAutomata
    //recibe nombre del automata en String una cadena que es el nombre del estado y el tipo que puede ser un estado o un 
    //simbolo
    public boolean verificarRepetidosAgregacion(String nombreAt, String cadena, String tipo) {
        boolean retorna = true;
        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals(nombreAt)) {
                if (tipo.equals("estado")) {
                    for (int j = 0; j < automatas.get(i).getEstados().size(); j++) {
                        if (automatas.get(i).getEstados().get(j).getNombre().equals(cadena)) {
                            retorna = false;
                        }
                    }

                }

                if (tipo.equals("simbolo")) {
                    for (int j = 0; j < automatas.get(i).getLenguaje().size(); j++) {
                        if (automatas.get(i).getLenguaje().get(j).equals(cadena)) {
                            retorna = false;
                        }
                    }
                }

                if (tipo.equals("estadoAceptador")) {
                    for (int j = 0; j < automatas.get(i).getEstadoAceptador().size(); j++) {
                        if (automatas.get(i).getEstadoAceptador().get(j).getNombre().equals(cadena)) {
                            retorna = false;
                        }
                    }

                }
            }
        }

        return retorna;

    }

    ///agregar lo que llegue a un automata para dejarlo intercambiable
    public void agregarAlAutomata(String nombre, String estados, String lenguaje, String estadoinicial, String estadosaceptadores) {
        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals("" + nombre)) {
                //agregar estados 
                if (!estados.equals("")) {
                    String cadenaSeparada[] = estados.split(",");

                    for (int j = 0; j < cadenaSeparada.length; j++) {
                        Estado estadonuevo = new Estado(cadenaSeparada[j] + "", false, false, 0, 0);

                        if (this.verificarRepetidosAgregacion(automatas.get(i).getNombre() + "", cadenaSeparada[j] + "", "estado") == true) {
                            automatas.get(i).getEstados().add(estadonuevo);
                        }
                    }
                }

                ///agregar simbolos al lenguaje
                if (!lenguaje.equals("")) {
                    String cadenaSeparada[] = lenguaje.split(",");

                    for (int j = 0; j < cadenaSeparada.length; j++) {
                        if (this.verificarRepetidosAgregacion(automatas.get(i).getNombre() + "", cadenaSeparada[j] + "", "simbolo") == true) {
                            automatas.get(i).getLenguaje().add(cadenaSeparada[j] + "");
                        }

                    }
                }

                //para el estado inicial
                if (!estadoinicial.equals("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "El automata ya tiene un estado inicial");
                }

                //para los estado aceptadores
                if (!estadosaceptadores.equals("")) {
                    String cadenaSeparada[] = estadosaceptadores.split(",");
                    LinkedList<Estado> aceptadoresnueva = new LinkedList();
                    LinkedList<Estado> aceptadorescopia = new LinkedList();

                    for (int j = 0; j < cadenaSeparada.length; j++) {
                        Estado nuevoestado = new Estado(cadenaSeparada[j] + "", false, true, 0, 0);

                        aceptadoresnueva.add(nuevoestado);
                        aceptadorescopia.add(nuevoestado);

                    }

                    for (int j = 0; j < automatas.get(i).getEstados().size(); j++) {
                        for (int k = 0; k < aceptadorescopia.size(); k++) {
                            if (automatas.get(i).getEstados().get(j).getNombre().equals(aceptadorescopia.get(k).getNombre() + "")) {
                                aceptadorescopia.remove(k);
                            }
                        }
                    }

                    if (aceptadorescopia.size() > 0) {
                        JOptionPane.showMessageDialog(new JFrame(), "Verifique estados aceptadores a ingresar");
                    } else {
                        for (int j = 0; j < aceptadoresnueva.size(); j++) {
                            for (int k = 0; k < automatas.get(i).getEstados().size(); k++) {
                                if (automatas.get(i).getEstados().get(k).getNombre().equals(aceptadoresnueva.get(j).getNombre() + "")) {
                                    if (this.verificarRepetidosAgregacion(automatas.get(i).getNombre() + "", automatas.get(i).getEstados().get(k).getNombre() + "", "estadoAceptador") == true) {

                                        automatas.get(i).getEstados().get(k).setEsAceptador(true);
                                        automatas.get(i).getEstadoAceptador().add(automatas.get(i).getEstados().get(k));
                                    }

                                }
                            }
                        }

                    }
                }
            }
        }
    }

    ///devuelve el estado de un automata recicibiendo el nombre del estado como una cadena o String y se busca en el automata
    //que tambien se recibe como parametro
    public Estado devolverEstado(Automata automata, String nombreEstado) {
        for (int i = 0; i < automata.getEstados().size(); i++) {
            if (automata.getEstados().get(i).getNombre().equals(nombreEstado)) {
                return automata.getEstados().get(i);
            }
        }
        return null;
    }

    ///metodo para verificar si un estado existe en una lista de estados.
    //este recie la lista de estados del automata y el estado a verificar 
    //retorna un true si el estado si existe en la lista de estados
    public boolean verificarEstadoTransicion(LinkedList<Estado> listadeEstados, Estado estado) {
        boolean primeraVerificacion = false;

        for (int i = 0; i < listadeEstados.size(); i++) {
            if (listadeEstados.get(i).getNombre().equals(estado.getNombre())) {
                primeraVerificacion = true;
            }
        }

        if (primeraVerificacion == true) {
            return true;
        } else {
            System.out.println("Verifique su estado inicial");
        }
        return false;
    }

    ///verifica si el simbolo si existe en la lista de simbolos de un automata
    //recibe el automata y el simbolo retorna un true para decir que si existe 
    public boolean verificarSimbolo(Automata automata, String simbolo) {
        for (int i = 0; i < automata.getLenguaje().size(); i++) {
            if (automata.getLenguaje().get(i).equals(simbolo)) {
                return true;
            }
        }

        return false;
    }

    ///metodo para la verificacion de afd esto nos ayuda a manterner un estado con un simbolo no duplicado, es decir:
    //este vela por que el automata sea finito de determinista.
    //recibe e automata al cual le voy a agregar la transicion el estadoA y el simbolo para hacer la verificacion 
    //si no hay duplicacion de simbolo  retorna un true de lo contrario false
    public boolean verificarTransicionAFD(Automata automata, String estadoA, String simbolo) {

        for (int i = 0; i < automata.getTransiciones().size(); i++) {
            if (automata.getTransiciones().get(i).getEstadoA().getNombre().equals(estadoA)) {
                if (automata.getTransiciones().get(i).getSimbolo().equals(simbolo)) {
                    return false;
                }
            }
        }

        return true;

    }

    ///Este metodo es para agregar una  transicion recibe el nombre del automata al cual se le va a agregar 
    //la transicion el estadoA con el simbolo al cual va al estadoB ya despues este llamo al metodo verificarEstadoTransicion
    //este devuelve un true o false para saber si el estado al cual me refiero existe, luego llamo al metodo verificarSimbolo
    //este verifica que el simbolo que menciona si exista en la lista de simbolos de dicho automata 
    //tambien se utiliza el metodo devolverEstado este para recolectar el estado al cual me estoy refiriendo 
    //para agregar la transicion y por ultimo llama al metodo verificarTransicionAFD este para verificar la transicion
    // ya que este metodo es para automatas finitos deterministas y no se puede agregar los estados con simbolos repetidos
///a otros estados.
    public void agregarTransicionAFD(String nombreAutomata, String estadoA, String simbolo, String estadoB) {
        //esto es para verificar a que automata le voy a agregar la transicion

        int posicionAutomata = 0;
        boolean verificacionExistenciaAutomata = false;

        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals(nombreAutomata)) {
                posicionAutomata = i;
                verificacionExistenciaAutomata = true;
            }
        }

        ///aca ya inicio a agregar 
        if (verificacionExistenciaAutomata == true) {
            if (verificarEstadoTransicion(automatas.get(posicionAutomata).getEstados(), devolverEstado(automatas.get(posicionAutomata), estadoA)) == true) {
                if (verificarEstadoTransicion(automatas.get(posicionAutomata).getEstados(), devolverEstado(automatas.get(posicionAutomata), estadoB)) == true) {
                    if (verificarSimbolo(automatas.get(posicionAutomata), simbolo) == true) {
                        ///verificar si no hay una transicion con ese simbolo 

                        if (automatas.get(posicionAutomata).getTransiciones().size() == 0) {
                            Transicion nuevaTransicion = new Transicion(devolverEstado(automatas.get(posicionAutomata), estadoA), simbolo, devolverEstado(automatas.get(posicionAutomata), estadoB));

                            automatas.get(posicionAutomata).getTransiciones().add(nuevaTransicion);

                        } else if (verificarTransicionAFD(automatas.get(posicionAutomata), estadoA, simbolo) == true) {
                            Transicion nuevaTransicion = new Transicion(devolverEstado(automatas.get(posicionAutomata), estadoA), simbolo, devolverEstado(automatas.get(posicionAutomata), estadoB));

                            automatas.get(posicionAutomata).getTransiciones().add(nuevaTransicion);
                        } else {
                            System.out.println("verifique transicion Para AFD");
                        }

                    }

                }
            }
        }

    }

    ///Este metodo es para agregar una  transicion para un AFN y un AFN_E recibe el nombre del automata al cual se le va a agregar 
    //la transicion el estadoA con el simbolo al cual va al estadoB ya despues este llamo al metodo verificarEstadoTransicion
    //este devuelve un true o false para saber si el estado al cual me refiero existe, luego llamo al metodo verificarSimbolo
    //este verifica que el simbolo que menciona si exista en la lista de simbolos de dicho automata 
    //tambien se utiliza el metodo devolverEstado este para recolectar el estado al cual me estoy refiriendo 
    //para agregar la transicion.
    public void agregarTransicionAFN_O_AFNE(String nombreAutomata, String estadoA, String simbolo, String estadoB) {
        //esto es para verificar a que automata le voy a agregar la transicion

        int posicionAutomata = 0;
        boolean verificacionExistenciaAutomata = false;

        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals(nombreAutomata)) {
                posicionAutomata = i;
                verificacionExistenciaAutomata = true;
            }
        }

        ///aca ya inicio a agregar 
        if (verificacionExistenciaAutomata == true) {
            if (verificarEstadoTransicion(automatas.get(posicionAutomata).getEstados(), devolverEstado(automatas.get(posicionAutomata), estadoA)) == true) {
                if (verificarEstadoTransicion(automatas.get(posicionAutomata).getEstados(), devolverEstado(automatas.get(posicionAutomata), estadoB)) == true) {
                    if (verificarSimbolo(automatas.get(posicionAutomata), simbolo) == true) {
                        ///verificar si no hay una transicion con ese simbolo 

                        if (automatas.get(posicionAutomata).getTransiciones().size() == 0) {
                            Transicion nuevaTransicion = new Transicion(devolverEstado(automatas.get(posicionAutomata), estadoA), simbolo, devolverEstado(automatas.get(posicionAutomata), estadoB));

                            automatas.get(posicionAutomata).getTransiciones().add(nuevaTransicion);

                        } else {
                            Transicion nuevaTransicion = new Transicion(devolverEstado(automatas.get(posicionAutomata), estadoA), simbolo, devolverEstado(automatas.get(posicionAutomata), estadoB));

                            automatas.get(posicionAutomata).getTransiciones().add(nuevaTransicion);

                        }

                    }

                }
            }
        }

    }

    ///metodo para verificar si con una cadena acepta o no 
    //este meotodo recorre estado por estado segun la cadena que nos da.
    public boolean lectorDeCadena(String nombreAutomata, String cadena) {

        LinkedList<Estado> rutaCadena = new LinkedList<>();

        for (int i = 0; i < this.automatas.size(); i++) {
            ///saco el automata que me dieron en el string 
            if (automatas.get(i).getNombre().equals(nombreAutomata)) {
                if (automatas.get(i).getTipoAutomata().equals("AFD")) {
                    if (this.verificarLenguaje(automatas.get(i), cadena) == true) {
                        //ruta cadena es la ruta que toma con la cadena dada 
                        rutaCadena = recorridoEvaluarCadena(automatas.get(i), rutaCadena, automatas.get(i).getEstadoInicial(), cadena);

                        //aca tomo la ultima posicion en la que quedo por el recorrido de la cadena
                        //y verifico si es aceptador o no 
                        if (rutaCadena.size() != 0) {
                            if (rutaCadena.getLast().isEsAceptador() == true) {
                                return true;
                            }

                            if (rutaCadena.getLast().isEsAceptador() == false) {
                                return false;
                            }
                        } else if (automatas.get(i).getEstadoInicial().isEsAceptador() == true) {
                            return true;
                        } else {
                            return false;
                        }

                    } else {
                        System.out.println("verifique lenguaje");
                    }
                }
            }
        }
        return false;
    }

    //verificar que la cadena si este en el lenguaje si no esta no sigue el metodo lectorDeCadena este retorna un true o un false 
    //true si la cadena toda es del lenguaje
    public boolean verificarLenguaje(Automata automata, String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            if (!automata.getLenguaje().contains("" + cadena.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    ///metodo para complementar lectorDeCadena retorna la lista de estados del recorrido que dejo la cadena
    public LinkedList<Estado> recorridoEvaluarCadena(Automata automata, LinkedList<Estado> rutaCadena, Estado estadoN, String cadena) {

        if (cadena.length() != 0) {
            for (int k = 0; k < automata.getTransiciones().size(); k++) {

                if (automata.getTransiciones().get(k).getEstadoA().getNombre().equals(estadoN.getNombre())
                        && automata.getTransiciones().get(k).getSimbolo().equals(cadena.charAt(0) + "")) {
                    System.out.println("" + estadoN.getNombre() + "Ace" + estadoN.isEsAceptador());
                    estadoN = automata.getTransiciones().get(k).getEstadoB();
                    rutaCadena.add(estadoN);
                    return recorridoEvaluarCadena(automata, rutaCadena, estadoN, cadena.substring(1));
                }
            }

        }
        return rutaCadena;
    }

    // complementoAutomata
    //Se crea un automataReversaTemporal que va ser el complemento
    //se recorre todo el LinkedList de automatas, buscando el nombre que se envio
    //Cuando se encuentra se setea todas los tributos para que automataReversaTemporal quede como nombreAutomata
    //primero voy a recorrero los estado para cambiar:
    //si aceptador= true pasarlo false
    //si aceptador= false pasarlo a true 
    // Se crean dos for para verificar que si esten bien los estados y las transiciones
    //Finalmente agregamos a la lsita de automatas asi: automatas.add(automataReversaTemporal);
    public void complementoAutomata(String nombreAutomata) {

        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equalsIgnoreCase(nombreAutomata)) {
                String estadosAceptadoresNueva = "";
                String estadosNuevos = "";
                String lenguajeNuevo = "";

                for (int j = 0; j < automatas.get(i).getEstados().size(); j++) {
                    if (automatas.get(i).getEstados().get(j).isEsAceptador() == false) {
                        estadosAceptadoresNueva += automatas.get(i).getEstados().get(j).getNombre() + ",";
                    }

                    estadosNuevos += automatas.get(i).getEstados().get(j).getNombre() + ",";
                }
                for (int j = 0; j < automatas.get(i).getLenguaje().size(); j++) {
                    lenguajeNuevo += automatas.get(i).getLenguaje().get(j) + ",";
                }

                crearAutomaatAFD_O_AFN(nombreAutomata + "_Complemento", automatas.get(i).getTipoAutomata(), estadosNuevos, lenguajeNuevo, automatas.get(i).getEstadoInicial().getNombre(), estadosAceptadoresNueva);

                for (int j = 0; j < automatas.size(); j++) {
                    if (automatas.get(j).getNombre().equals(nombreAutomata + "_Complemento")) {
                        automatas.get(j).setTransiciones(automatas.get(i).getTransiciones());
                    }
                }

            }

        }

    }

    ///minimizar
    ///verificar marcacnion para minimizar
    public LinkedList<Estado> verificarMarcacion(Automata atver, Estado[] vec1, Estado[] vec2, int arribai, int abajoi) {
        ////posiciones para las transiciones

        System.out.println("vec1 " + vec1[arribai].getNombre());

        System.out.println("vec2 " + vec2[abajoi].getNombre() + "\n" + "-----" + "\n");

        ////esta lista se crea para que guarde los estados por cada simbolo que encuentre
        LinkedList<Estado> listaporsimbolos = new LinkedList<Estado>();

        ///// aca verifico por cada simbolo si con el mismo simbolo 
        ////salen los dos estados ... verifico si estan vacios
        for (int j = 0; j < atver.getLenguaje().size(); j++) {
            int posiciontrans1 = -5;
            int posiciontrans2 = -5;

            for (int i = 0; i < atver.getTransiciones().size(); i++) {
                if (vec1[arribai].getNombre().equals(atver.getTransiciones().get(i).getEstadoA().getNombre()) && atver.getTransiciones().get(i).getSimbolo().equals(atver.getLenguaje().get(j))) {

                    posiciontrans1 = i;
                }

            }

            for (int i = 0; i < atver.getTransiciones().size(); i++) {
                if (vec2[abajoi].getNombre().equals(atver.getTransiciones().get(i).getEstadoA().getNombre()) && atver.getTransiciones().get(i).getSimbolo().equals(atver.getLenguaje().get(j))) {
                    posiciontrans2 = i;
                }

            }

            if (posiciontrans1 != -5 && posiciontrans2 != -5) {
                listaporsimbolos.add(atver.getTransiciones().get(posiciontrans1).getEstadoB());
                listaporsimbolos.add(atver.getTransiciones().get(posiciontrans2).getEstadoB());
            }

        }

        return listaporsimbolos;
    }

    ///minimizacion
    ///crear dos arreglos y una matriz
    ///llenar arreglo arriba 
    ///llenar arreglo abajo "lateral"
    ///llenar matriz con un simbolo para senalar parte que no se utiliza media matriz "0" 
    ///llenar media  matriz con otro simbolo para senalar la media matriz
    ///Marcar aceptadores Arriba y abajo se verifican los dos 
    //llamar metodo verificar marcacion 
    // marcar la lista que devolvio 
    public void minimizar(Automata automatax) 
    {
        Automata automata=new Automata(automatax.getNombre(),automatax.getTipoAutomata(), automatax.getEstados(), automatax.getLenguaje(), automatax.getEstadoInicial(), automatax.getEstadoAceptador());

      automata.setTransiciones(automatax.getTransiciones());
      
        
      
           
        
       ///eliminar inhalcanzables
        automata.setNombre("copia");
        
        
        
        
        System.out.println("nombre cc:"+automata.getNombre());
       
       Estado [] arriba=new Estado[(automata.getEstados().size())-1];
       Estado [] lateral=new Estado[(automata.getEstados().size())-1];
       String  [][] matriz=new String[(automata.getEstados().size())-1][(automata.getEstados().size())-1];
       
       
            for (int i = 0; i < arriba.length; i++) 
            {
                    arriba[i]=automata.getEstados().get(i);
            }

            for (int i = 0; i < arriba.length; i++) 
            {
                    lateral[(lateral.length-1)-i]=automata.getEstados().get(i+1);
            }
       
       
       
          ////marcar N para toda la matriz 
            for(int i=0; i<arriba.length;i++)
            {
                
                for (int j = 0; j < lateral.length; j++)
                { 
                    matriz[i][j] = "N";
                }   
            }




            ////marca 0 para la parte que se utiliza 
            for (int i = 0; i < arriba.length; i++)
            {
                for (int j = 0; j < lateral.length; j++)
                {
                    if (i == 0)
                    {
                        matriz[i][j] = "0";
                    }
                    if (i != 0 && j <(arriba.length)-i )
                    {
                        matriz[i][j] = "0";   
                    }
                }
            }
            
            
            ///para variables de arriba .. arriba hacia abajo marca X
            for (int i = 0; i < arriba.length; i++)
            {
                for (int j = 0; j < automata.getEstadoAceptador().size(); j++)
                {
                    if (automata.getEstadoAceptador().get(j).getNombre().equals(arriba[i].getNombre()))
                    {
                         for (int x= 0; x < arriba.length; x++) 
                         {
                             if (!matriz[x][i].equals("N"))
                             {
                                 
                                 matriz[x][i]="X";
                             }
                             
                         }
                    }
                }
            }
            
            
             ////para las variable de abajo o las de lado 
            for (int i = 0; i < lateral.length; i++)
            {
                for (int j = 0; j < automata.getEstadoAceptador().size(); j++)
                {
                    if (automata.getEstadoAceptador().get(j).getNombre().equals(lateral[i].getNombre()))
                    {
                        for (int x = 0; x < lateral.length; x++)
                        {
                            if (!matriz[i][x].equals("N"))
                            {
                                matriz[i][x] = "X";
                            }
                           
                        }
                    }
                }
            }
            
            ///ELIMINAR MARCACION SI LOS DOS SON ACEPTADORES
            
            for (int y = 0; y < arriba.length; y++) 
            {
                for (int x = 0; x < lateral.length; x++) 
                {
                    if (!arriba[y].getNombre().equals(lateral[x].getNombre()+"")) 
                    {
                        if (arriba[y].isEsAceptador()==true&&lateral[x].isEsAceptador()==true) 
                        {
                            if (!matriz[x][y].equals("N")) 
                            {
                                matriz[x][y]="0";
                            }
                            
                        }
                    }
                }
           
            }
            
            ////marcar verificando
            for (int ite = 0; ite < 5;ite++)
            {
                    for (int i = 0; i < arriba.length; i++)
                    {
                        for (int j = 0; j < lateral.length; j++)
                        {
                            if (matriz[i][j].equals("0"))
                            {

                                LinkedList<Estado> listaDeverificarMarcardo = new LinkedList<Estado>();

                                listaDeverificarMarcardo = verificarMarcacion(automata, arriba,lateral, j, i);
                                
                                
                             

                                for (int x = 0; x < listaDeverificarMarcardo.size(); x++)
                                {
                                    if (listaDeverificarMarcardo.get(x) != null)
                                    {
                                       
                                        int marcadoposarriba = -5;
                                        int marcadoposabajo = -5;
                                        int marcadoposarriba2= -5;
                                        int marcadoposabajo2 = -5;
                                        ////verifica la posicion matriz arriba y abajo para ubicarlo 
                                        //// en la matriz de minimizacion y verificar que no sea x o 0 

                                        for (int y = 0; y < arriba.length; y++)
                                        {
                                            if (listaDeverificarMarcardo.get(0).getNombre().equals(arriba[y].getNombre()))
                                            {
                                                marcadoposarriba = y;
                                            }
                                            if (listaDeverificarMarcardo.get(1).getNombre().equals(lateral[y].getNombre()))
                                            {
                                                marcadoposabajo = y;
                                            }
                                            if (listaDeverificarMarcardo.get(2).getNombre().equals(arriba[y].getNombre()))
                                            {
                                                marcadoposarriba2= y;
                                            }
                                            if (listaDeverificarMarcardo.get(3).getNombre().equals(lateral[y].getNombre()))
                                            {
                                                marcadoposabajo2= y;
                                            }
                                            

                                        }

                                        
                                        if(marcadoposabajo!=-5&&marcadoposarriba!=-5&&marcadoposabajo2!=-5&&marcadoposarriba2!=-5)
                                        {
                                            if (matriz[marcadoposabajo][marcadoposarriba].equals("X") && !matriz[marcadoposabajo][marcadoposarriba].equals("N"))
                                             {                                          
                                                  matriz[i][j] = "X";
                                                  x = listaDeverificarMarcardo.size(); 
                                             }
                                            
                                            if (matriz[marcadoposabajo2][marcadoposarriba2].equals("X") && !matriz[marcadoposabajo2][marcadoposarriba2].equals("N"))
                                             {                                          
                                                  matriz[i][j] = "X";
                                                  x = listaDeverificarMarcardo.size(); 
                                             }
                                        }
                                    }

                                }
                            }
                            
                        }
             
                }
            }
            
         ///hacer agrupaciones
            
            
            //mostrar matriz
             for (int i = 0; i < arriba.length; i++)
            {   String linea="";
                for (int j = 0; j < lateral.length; j++)
                {
                
                   linea+=matriz[i][j];
                }
                System.out.println(linea+"\n");
            }
    }

    //Se recorre el automata con el nombre que se le envio para crearle la revera cuando se encuentra:
    //como creamos un nuevo automata mandando los parametros, tenemos que recorrer el LinkedList de estados aceptadores
    //estados y el lenguaje
    //1-Se empieza con el caso mas sencillo que es cuando el automata tine solo un aceptador o sea  
    //if (automatas.get(i).getEstadoAceptador().size() == 1) se hace que el estado inicial sea acptador, el estado aceptador inical
    // y se envia los parametos para que se cree el automata
    //posteriormente nos encargamos de las trnasmiciones, se llama el metodo agregarTransicionAFN_O_AFNE por cada transicion que el
    //automata tiene, intercambiando A= ya va a ser el B  el simbolo el mismo B= ya va a ser A
    //2- se sigue con el caso mas complicado que es cuando un automata tiene dos o mas acetadores, esto es porque 
    //los estados aceptadores pasan a ser inicial,  cambian sentido de transiciones y el inicial pasa a ser aceptador
    //se  cre un automata con los estados aceptadores nuevos, ls estados nuevos y el lenguaje nuevo
    //se tiene que crear un estado nuevo y se hace de la siguiente forma estadosNuevos += "q";
    //cuando ya se tiene el automata creado se hacen las tranciciones
    // finalmente,como los estados aceptadores pasana a ser iniciales  solo se puede uno se manda una transicion e a q(El nuevo estado)
    public void metodoRversa(String nombreAutomata) {
        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals(nombreAutomata)) {

                String estadosAceptadoresNueva = "";
                String estadosNuevos = "";
                String lenguajeNuevo = "";

                for (int c = 0; c < automatas.get(i).getEstados().size(); c++) {
                    estadosNuevos += automatas.get(i).getEstados().get(c).getNombre() + ",";
                }
                for (int v = 0; v < automatas.get(i).getLenguaje().size(); v++) {
                    if (!automatas.get(i).getLenguaje().get(v).equalsIgnoreCase("E")) {
                        lenguajeNuevo += automatas.get(i).getLenguaje().get(v) + ",";
                    }
                }

                if (automatas.get(i).getEstadoAceptador().size() == 1) {
                    String estadoInicial = automatas.get(i).getEstadoAceptador().get(0).getNombre();

                    crearAutomaatAFN_E(automatas.get(i).getNombre() + "_Reversa", "AFND", estadosNuevos, lenguajeNuevo, estadoInicial, automatas.get(i).getEstadoInicial().getNombre());

                    //para la lista de transiciones
                    for (int t = 0; t < automatas.get(i).getTransiciones().size(); t++) {
                        agregarTransicionAFN_O_AFNE(automatas.get(i).getNombre() + "_Reversa", automatas.get(i).getTransiciones().get(t).getEstadoB().getNombre(), automatas.get(i).getTransiciones().get(t).getSimbolo(), automatas.get(i).getTransiciones().get(t).getEstadoA().getNombre());
                    }

                } else {
                    for (int j = 0; j < automatas.get(i).getEstadoAceptador().size(); j++) {
                        estadosAceptadoresNueva += automatas.get(i).getEstadoAceptador().get(j).getNombre() + ",";
                    }
                    estadosNuevos += "q";
                    System.out.println("Estados Nuevos: " + estadosNuevos);
                    crearAutomaatAFN_E(automatas.get(i).getNombre() + "_Reversa", "AFND", estadosNuevos, lenguajeNuevo, "q", automatas.get(i).getEstadoInicial().getNombre());

                    for (int g = 0; g < automatas.get(i).getTransiciones().size(); g++) {
                        agregarTransicionAFN_O_AFNE(automatas.get(i).getNombre() + "_Reversa", automatas.get(i).getTransiciones().get(g).getEstadoB().getNombre(), automatas.get(i).getTransiciones().get(g).getSimbolo(), automatas.get(i).getTransiciones().get(g).getEstadoA().getNombre());
                    }
                    for (int h = 0; h < automatas.get(i).getEstadoAceptador().size(); h++) {
                        //agregarTransicionAFN_O_AFNE(automatas.get(i).getNombre() + "_Reversa", automatas.get(i).getEstadoAceptador().get(h).getNombre(), "E", "q");
                        agregarTransicionAFN_O_AFNE(automatas.get(i).getNombre() + "_Reversa", "q", "E", automatas.get(i).getEstadoAceptador().get(h).getNombre());
                    }
                }

            }

        }
    }

    // Metodo para que me devuelva el automata segun el nombre
    public Automata devolverAutomata(String nombreaAutomata) {
        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals(nombreaAutomata)) {
                return automatas.get(i);
            }
        }
        return null;
    }

    //Metodo para devolver true si los lenguajes son iguales o folse si no lo son
    public boolean sonIgualesLenguajes(LinkedList<String> lenguajeUno, LinkedList<String> lenguajeDos) {
        if (lenguajeUno.size() == lenguajeDos.size()) {
            for (int i = 0; i < lenguajeUno.size(); i++) {
                for (int j = 0; j < lenguajeDos.size(); j++) {
                    if (!lenguajeDos.contains(lenguajeUno.get(i))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean contiene(LinkedList<Estado> aceptadores, String estado) {
        for (int i = 0; i < aceptadores.size(); i++) {
            if (aceptadores.get(i).getNombre().equals(estado)) {
                return true;
            }
        }
        return false;
    }

    //Metodo para cambiar los nombres de los estados
    //  ejemplo automataA: -> A---o-->B--1-->D   automataB: -> E---o-->F--1-->G
    //  resultado -> A0---o-->B1--1-->D2   automataB: -> E3---o-->F4--1-->G5
    // y se agregan a la lista de automatas 
    public void cambiarNombres(Automata automataA, Automata automataB, String operacion) {

        String estadosNuevos = "";
        String estadosAceptadoresNueva = "";
        String lenguajeNuevo = "";
        int inicioSegundoFor = 0;
        int contador = 0;
        String nombre = operacion;

        for (int h = 0; h < automataA.getLenguaje().size(); h++) {
            lenguajeNuevo += automataA.getLenguaje().get(h) + ",";
        }

        //Para los estados
        for (int j = 0; j < automataA.getEstados().size(); j++) {
            estadosNuevos += automataA.getEstados().get(j).getNombre() + j + ",";
            if (contiene(automataA.getEstadoAceptador(), automataA.getEstados().get(j).getNombre())) {
                estadosAceptadoresNueva += automataA.getEstados().get(j).getNombre() + j + ",";
            }
            inicioSegundoFor = j + 1;
        }

        crearAutomaatAFD_O_AFN(automataA.getNombre() + nombre, "AFD", estadosNuevos, lenguajeNuevo, automataA.getEstadoInicial().getNombre() + "" + automataA.getEstados().indexOf(automataA.getEstadoInicial()), estadosAceptadoresNueva);

        for (int i = 0; i < automataA.getTransiciones().size(); i++) {
            agregarTransicionAFD(automataA.getNombre() + nombre, automataA.getTransiciones().get(i).getEstadoA().getNombre() + automataA.getEstados().indexOf(automataA.getTransiciones().get(i).getEstadoA()), automataA.getTransiciones().get(i).getSimbolo(), automataA.getTransiciones().get(i).getEstadoB().getNombre() + automataA.getEstados().indexOf(automataA.getTransiciones().get(i).getEstadoB()));
        }

        estadosNuevos = "";
        estadosAceptadoresNueva = "";
        contador = inicioSegundoFor;

        //Para automataB
        for (int j = 0; j < automataB.getEstados().size(); j++) {
            estadosNuevos += automataB.getEstados().get(j).getNombre() + contador + ",";
            if (contiene(automataB.getEstadoAceptador(), automataB.getEstados().get(j).getNombre())) {
                estadosAceptadoresNueva += automataB.getEstados().get(j).getNombre() + contador + ",";
            }
            contador += 1;
            //inicioSegundoFor += 1;
        }

        crearAutomaatAFD_O_AFN(automataB.getNombre() + nombre, "AFD", estadosNuevos, lenguajeNuevo, automataB.getEstadoInicial().getNombre() + "" + (automataB.getEstados().indexOf(automataB.getEstadoInicial()) + inicioSegundoFor), estadosAceptadoresNueva);

        for (int i = 0; i < automataB.getTransiciones().size(); i++) {
            agregarTransicionAFD(automataB.getNombre() + nombre, automataB.getTransiciones().get(i).getEstadoA().getNombre() + (automataB.getEstados().indexOf(automataB.getTransiciones().get(i).getEstadoA()) + inicioSegundoFor), automataB.getTransiciones().get(i).getSimbolo(), automataB.getTransiciones().get(i).getEstadoB().getNombre() + (automataB.getEstados().indexOf(automataB.getTransiciones().get(i).getEstadoB()) + inicioSegundoFor));
        }

    }

    //Metodo para remver de la lista
    public void eliminarAutomata(String automata) {
        for (int i = 0; i < automatas.size(); i++) {
            if (automatas.get(i).getNombre().equals(automata)) {
                automatas.remove(i);
            }
        }
    }

    //Metodo de la concatenacion Thompson
    //primero se  buscan el automataA y el automataB que se mandaron desde el fram en los combox
    // se verifica que los enguajes sean iguales para poder realizar la concatenacion de lo contraio no se podria
    // se cambian los nombre de los estados de los automatas, añadiendole el indice cambiarNombres(automataA, automataB);
    //se adquieren la lista de los estados los aceptadores
    //lenguajeAFD_E en el caso de este es por si el automata es afd_e para no agregarle el elemento e ya que se repitiria si se hace
    // ya que al momento de decir que lo cree este  ya se lo agrega
    // y se toman dvarios casos si es crearAutomaatAFD_O_AFN o si es crearAutomaatAFN_E
    // tambien se evalua si tiene dos aceptadores  automatas.get(i).getEstadoAceptador().size() == 1 de ser cierto, se crea un nuevo 
    //estado con sus transiciones E
    public void metodoConcatenacion(String automataUno, String automataDos) {
        //System.out.println("Automata uno: "+automataUno+" Automata Dos: "+automataDos);
        Automata automataA = devolverAutomata(automataUno);
        Automata automataB = devolverAutomata(automataDos);
        String estadosNuevos = "";
        String estadosAceptadoresNueva = "";
        String lenguajeNuevo = "";
        String estadoInicial = "";
        String estadoFinal = "";
        String estadoTransitivo = "";
        String lenguajeAFD_E = "";

        if (sonIgualesLenguajes(automataA.getLenguaje(), automataB.getLenguaje())) {
            cambiarNombres(automataA, automataB, "_ComplementoP");

            for (int h = 0; h < automataA.getLenguaje().size(); h++) {
                if (!automataA.getLenguaje().get(h).equalsIgnoreCase("E")) {
                    lenguajeNuevo += automataA.getLenguaje().get(h) + ",";
                }
            }
            lenguajeAFD_E = lenguajeNuevo;
            lenguajeNuevo += "E";

            for (int z = 0; z < automatas.size(); z++) {
                if ((automatas.get(z).getNombre().equals(automataB.getNombre() + "_ComplementoP"))) {
                    estadoTransitivo = automatas.get(z).getEstadoInicial().getNombre();
                    break;
                }
            }

            for (int i = 0; i < automatas.size(); i++) {
                if ((automatas.get(i).getNombre().equals(automataA.getNombre() + "_ComplementoP")) || (automatas.get(i).getNombre().equals(automataB.getNombre() + "_ComplementoP"))) {
                    for (int j = 0; j < automatas.get(i).getEstados().size(); j++) {
                        if (estadosNuevos == "") {
                            estadoInicial = automatas.get(i).getEstados().get(j).getNombre();
                        }
                        estadosNuevos += automatas.get(i).getEstados().get(j).getNombre() + ",";
                        if (contiene(automatas.get(i).getEstadoAceptador(), automatas.get(i).getEstados().get(j).getNombre())) {
                            estadosAceptadoresNueva += automatas.get(i).getEstados().get(j).getNombre() + ",";
                        }
                        estadoFinal = automatas.get(i).getEstados().get(j).getNombre();
                    }

                }
            }

            for (int i = 0; i < automatas.size(); i++) {
                if ((automatas.get(i).getNombre().equals(automataB.getNombre() + "_ComplementoP"))) {
                    if (automatas.get(i).getEstadoAceptador().size() == 1) {
                        if ((automataA.getTipoAutomata().equalsIgnoreCase("AFD"))) {
                            crearAutomaatAFD_O_AFN(automataA.getNombre() + automataB.getNombre() + "_Complemento", "AFD", estadosNuevos, lenguajeNuevo, estadoInicial, estadoFinal);
                        } else if (automataA.getTipoAutomata().equalsIgnoreCase("AFN")) {
                            crearAutomaatAFD_O_AFN(automataA.getNombre() + automataB.getNombre() + "_Complemento", "AFN", estadosNuevos, lenguajeNuevo, estadoInicial, estadoFinal);
                        } else {
                            crearAutomaatAFN_E(automataA.getNombre() + automataB.getNombre() + "_Complemento", "AFN_E", estadosNuevos, lenguajeAFD_E, estadoInicial, estadoFinal);
                        }
                        for (int l = 0; l < automatas.size(); l++) {
                            if ((automatas.get(l).getNombre().equals(automataA.getNombre() + "_ComplementoP")) || (automatas.get(l).getNombre().equals(automataB.getNombre() + "_ComplementoP"))) {
                                for (int t = 0; t < automatas.get(l).getTransiciones().size(); t++) {
                                    agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Complemento", automatas.get(l).getTransiciones().get(t).getEstadoA().getNombre(), automatas.get(l).getTransiciones().get(t).getSimbolo(), automatas.get(l).getTransiciones().get(t).getEstadoB().getNombre());
                                }
                            }
                        }
                        for (int l = 0; l < automatas.size(); l++) {
                            if (automatas.get(l).getNombre().equals(automataA.getNombre() + "_ComplementoP")) {
                                for (int t = 0; t < automatas.get(l).getEstadoAceptador().size(); t++) {
                                    //System.out.println("A: " + automatas.get(l).getEstadoAceptador().get(t).getNombre() + " Lenguaje: E" + " B: " + estadoTransitivo);
                                    agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Complemento", automatas.get(l).getEstadoAceptador().get(t).getNombre(), "E", estadoTransitivo);
                                }
                            }
                        }
                    } else {
                        estadosNuevos += "q";
                        if ((automataA.getTipoAutomata().equalsIgnoreCase("AFD"))) {
                            crearAutomaatAFD_O_AFN(automataA.getNombre() + automataB.getNombre() + "_Complemento", "AFD", estadosNuevos, lenguajeNuevo, estadoInicial, "q");
                        }
                        if ((automataA.getTipoAutomata().equalsIgnoreCase("AFN"))) {
                            crearAutomaatAFD_O_AFN(automataA.getNombre() + automataB.getNombre() + "_Complemento", "AFN", estadosNuevos, lenguajeNuevo, estadoInicial, "q");
                        } else {
                            crearAutomaatAFN_E(automataA.getNombre() + automataB.getNombre() + "_Complemento", "AFN_E", estadosNuevos, lenguajeAFD_E, estadoInicial, "q");
                        }

                        for (int l = 0; l < automatas.size(); l++) {
                            if ((automatas.get(l).getNombre().equals(automataA.getNombre() + "_ComplementoP")) || (automatas.get(l).getNombre().equals(automataB.getNombre() + "_ComplementoP"))) {
                                for (int t = 0; t < automatas.get(l).getTransiciones().size(); t++) {
                                    agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Complemento", automatas.get(l).getTransiciones().get(t).getEstadoA().getNombre(), automatas.get(l).getTransiciones().get(t).getSimbolo(), automatas.get(l).getTransiciones().get(t).getEstadoB().getNombre());
                                }
                            }
                        }
                        for (int l = 0; l < automatas.size(); l++) {
                            if (automatas.get(l).getNombre().equals(automataA.getNombre() + "_ComplementoP")) {
                                for (int t = 0; t < automatas.get(l).getEstadoAceptador().size(); t++) {
                                    //System.out.println("A: " + automatas.get(l).getEstadoAceptador().get(t).getNombre() + " Lenguaje: E" + " B: " + estadoTransitivo);
                                    agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Complemento", automatas.get(l).getEstadoAceptador().get(t).getNombre(), "E", estadoTransitivo);
                                }
                            }
                        }
                        for (int l = 0; l < automatas.size(); l++) {
                            if (automatas.get(l).getNombre().equals(automataB.getNombre() + "_ComplementoP")) {
                                for (int t = 0; t < automatas.get(l).getEstadoAceptador().size(); t++) {
                                    agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Complemento", automatas.get(l).getEstadoAceptador().get(t).getNombre(), "E", "q");
                                    //agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Complemento", "q", "E", automatas.get(l).getEstadoAceptador().get(t).getNombre() );
                                }
                            }
                        }

                        System.out.println("Tine mas de un aceptador");
                    }
                }
            }
            eliminarAutomata(automataA.getNombre() + "_ComplementoP");
            eliminarAutomata(automataB.getNombre() + "_ComplementoP");

        } //crearAutomaatAFD_O_AFN(automataA.getNombre()+automataB.getNombre()+ "_Complemento", "AFD", estadosNuevos, lenguajeNuevo, estadoInicial, estadosAceptadoresNueva);
        else {
            System.out.println("NO se puede realizar la concatenacion por que No son iguales los lenguajes");
        }
    }

    //Metodo Union
    public void metodoUnion(String automataUno, String automataDos) {

        Automata automataA = devolverAutomata(automataUno);
        Automata automataB = devolverAutomata(automataDos);
        String estadosNuevos = "";
        String estadosAceptadoresNueva = "";
        String lenguajeNuevo = "";
        String estadoInicial = "";
        String estadoFinal = "";
        String estadoTransitivo = "";
        String lenguajeAFD_E = "";

        if (sonIgualesLenguajes(automataA.getLenguaje(), automataB.getLenguaje())) {
            cambiarNombres(automataA, automataB, "_Union");

            for (int h = 0; h < automataA.getLenguaje().size(); h++) {
                if (!automataA.getLenguaje().get(h).equalsIgnoreCase("E")) {
                    lenguajeNuevo += automataA.getLenguaje().get(h) + ",";
                }
            }
            lenguajeAFD_E = lenguajeNuevo;
            lenguajeNuevo += "E";

            for (int z = 0; z < automatas.size(); z++) {
                if ((automatas.get(z).getNombre().equals(automataB.getNombre() + "_Union"))) {
                    estadoTransitivo = automatas.get(z).getEstadoInicial().getNombre();
                    break;
                }
            }
            for (int i = 0; i < automatas.size(); i++) {
                if ((automatas.get(i).getNombre().equals(automataA.getNombre() + "_Union")) || (automatas.get(i).getNombre().equals(automataB.getNombre() + "_Union"))) {
                    for (int j = 0; j < automatas.get(i).getEstados().size(); j++) {
                        if (estadosNuevos == "") {
                            estadoInicial = automatas.get(i).getEstados().get(j).getNombre();
                        }
                        estadosNuevos += automatas.get(i).getEstados().get(j).getNombre() + ",";
                        if (contiene(automatas.get(i).getEstadoAceptador(), automatas.get(i).getEstados().get(j).getNombre())) {
                            estadosAceptadoresNueva += automatas.get(i).getEstados().get(j).getNombre() + ",";
                        }
                    }

                }
            }
            estadosNuevos += "q0,q1";
            if ((automataA.getTipoAutomata().equalsIgnoreCase("AFD"))) {
                crearAutomaatAFD_O_AFN(automataA.getNombre() + automataB.getNombre() + "_Union", "AFD", estadosNuevos, lenguajeNuevo, "q0", "q1");
            } else if (automataA.getTipoAutomata().equalsIgnoreCase("AFN")) {
                crearAutomaatAFD_O_AFN(automataA.getNombre() + automataB.getNombre() + "_Union", "AFN", estadosNuevos, lenguajeNuevo, "q0", "q1");
            } else {
                crearAutomaatAFN_E(automataA.getNombre() + automataB.getNombre() + "_Union", "AFN_E", estadosNuevos, lenguajeAFD_E, "q0", "q1");
            }
            for (int l = 0; l < automatas.size(); l++) {
                if ((automatas.get(l).getNombre().equals(automataA.getNombre() + "_Union")) || (automatas.get(l).getNombre().equals(automataB.getNombre() + "_Union"))) {
                    for (int t = 0; t < automatas.get(l).getTransiciones().size(); t++) {
                        agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Union", automatas.get(l).getTransiciones().get(t).getEstadoA().getNombre(), automatas.get(l).getTransiciones().get(t).getSimbolo(), automatas.get(l).getTransiciones().get(t).getEstadoB().getNombre());
                    }
                }
            }
            for (int l = 0; l < automatas.size(); l++) {
                if ((automatas.get(l).getNombre().equals(automataA.getNombre() + "_Union")) || (automatas.get(l).getNombre().equals(automataB.getNombre() + "_Union"))) {
                    for (int t = 0; t < automatas.get(l).getEstadoAceptador().size(); t++) {
                        agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Union", automatas.get(l).getEstadoAceptador().get(t).getNombre(), "E", "q1");
                    }
                }
            }

            agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Union", "q0", "E", estadoInicial);
            agregarTransicionAFN_O_AFNE(automataA.getNombre() + automataB.getNombre() + "_Union", "q0", "E", estadoTransitivo);

            eliminarAutomata(automataA.getNombre() + "_Union");
            eliminarAutomata(automataB.getNombre() + "_Union");

        } else {
            System.out.println("No son iguales");
        }

        System.out.println("AutomataA: " + automataUno + " AutomataB: " + automataDos);
    }

    //Metodo de Cierre de Kleene A*
    //se acceden a los estados, al lenguaje y a los estados aceptadores
    // se hacen las transiciones requeridas para hacer Kleene
    public void metodoKleene(String automataUno) {
        Automata automata = devolverAutomata(automataUno);

        String estadosNuevos = "";
        String estadosAceptadoresNueva = "";
        String lenguajeNuevo = "";
        String estadoInicial = "";
        String estadoFinal = "";
        String lenguajeAFD_E = "";

        for (int c = 0; c < automata.getEstados().size(); c++) {
            if (estadosNuevos == "") {
                estadoInicial = automata.getEstados().get(c).getNombre();
                System.out.println("aqui: " + estadoInicial);
            }
            estadosNuevos += automata.getEstados().get(c).getNombre() + ",";
            estadoFinal = automata.getEstados().get(c).getNombre();
        }
        estadosNuevos += "q0,q1";

        for (int h = 0; h < automata.getLenguaje().size(); h++) {
            if (!automata.getLenguaje().get(h).equalsIgnoreCase("E")) {
                lenguajeNuevo += automata.getLenguaje().get(h) + ",";
            }
        }
        lenguajeAFD_E = lenguajeNuevo;
        lenguajeNuevo += "E";

        for (int j = 0; j < automata.getEstadoAceptador().size(); j++) {
            estadosAceptadoresNueva += automata.getEstadoAceptador().get(j).getNombre() + ",";
        }

        if ((automata.getTipoAutomata().equalsIgnoreCase("AFD"))) {
            crearAutomaatAFD_O_AFN(automata.getNombre() + "_Kleene", "AFD", estadosNuevos, lenguajeNuevo, "q0", "q1");
        } else if (automata.getTipoAutomata().equalsIgnoreCase("AFN")) {
            crearAutomaatAFD_O_AFN(automata.getNombre() + "_Kleene", "AFN", estadosNuevos, lenguajeNuevo, "q0", "q1");
        } else {
            crearAutomaatAFD_O_AFN(automata.getNombre() + "_Kleene", "AFN_E", estadosNuevos, lenguajeNuevo, "q0", "q1");
        }

        for (int t = 0; t < automata.getTransiciones().size(); t++) {
            agregarTransicionAFN_O_AFNE(automata.getNombre() + "_Kleene", automata.getTransiciones().get(t).getEstadoA().getNombre(), automata.getTransiciones().get(t).getSimbolo(), automata.getTransiciones().get(t).getEstadoB().getNombre());

        }

        agregarTransicionAFN_O_AFNE(automata.getNombre() + "_Kleene", estadoFinal, "E", estadoInicial);
        agregarTransicionAFN_O_AFNE(automata.getNombre() + "_Kleene", estadoFinal, "E", "q1");
        agregarTransicionAFN_O_AFNE(automata.getNombre() + "_Kleene", "q0", "E", estadoInicial);
        agregarTransicionAFN_O_AFNE(automata.getNombre() + "_Kleene", "q0", "E", "q1");

    }
    
    
    
    public void prueba(){
        
        
    }

///fin de la clase MetodosDeCreacionLogica
    public void setAutomatas(LinkedList<Automata> automatas) {
        this.automatas = automatas;
    }

    public LinkedList<Automata> getAutomatas() {
        return automatas;
    }
}