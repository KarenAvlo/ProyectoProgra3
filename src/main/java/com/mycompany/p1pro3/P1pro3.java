package com.mycompany.p1pro3;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;

public class P1pro3 {

    public static void main(String[] args) throws IOException, JAXBException, Exception {

//        GestorPersonas gf = new GestorPersonas();
//        gf.cargarTodo();
//        System.out.println("personas cargadas:");
//        System.out.print(gf.toString());
    GestorAdministrativos ga = GestorAdministrativos.cargarDesdeXML();
    System.out.println(ga.toString());
    
        
        

    }
}
