package com.mycompany.p1pro3;

//import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

//import java.util.Iterator;
//import java.util.List;
@Getter
@Setter

public class Hospital {
   private GestorPersonas gestorPersonas = new GestorPersonas();
   private GestorMedicos gestorMedicos = new GestorMedicos();
   private GestorPacientes gestorP = new GestorPacientes();
   private GestorAdministrativos gestorAdministrativos = new GestorAdministrativos();
    private GestorFarmaceutas gestorFarmaceutas = new GestorFarmaceutas();
   private Farmacia Farma = new Farmacia(gestorFarmaceutas);
   
   public void cargarDatos() throws Exception {
        gestorPersonas.cargarTodo(gestorAdministrativos, gestorFarmaceutas, gestorMedicos);
        gestorP = GestorPacientes.cargarDesdeXML();
        Farma.cargarDatos();
   }
   
   public boolean guardarDatos() {
        try {
            gestorMedicos.guardar();
            gestorP.guardar();
            Farma.guardarDatos();
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            return false;
        }
    }
   
       
}

