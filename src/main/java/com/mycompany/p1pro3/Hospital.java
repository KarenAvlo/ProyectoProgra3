package com.mycompany.p1pro3;

//import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

//import java.util.Iterator;
//import java.util.List;
@Getter
@Setter

public class Hospital {
   private GestorPersonas gp = new GestorPersonas();
   private GestorMedicos gestorM = new GestorMedicos();
   private GestorPacientes gestorP = new GestorPacientes();
   private Farmacia Farma = new Farmacia();
   
   public void cargarDatos() throws Exception {
        gp.cargarTodo();
        gestorM = GestorMedicos.cargarDesdeXML();
        gestorP = GestorPacientes.cargarDesdeXML();
        Farma.cargarDatos();
    }
   

    
}

