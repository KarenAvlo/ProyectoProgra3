package com.mycompany.p1pro3;

import lombok.Getter;
import lombok.Setter;

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
       Farma.cargarDatos(); 
       gestorPersonas.cargarTodo(gestorAdministrativos, gestorFarmaceutas, gestorMedicos);
       gestorP = GestorPacientes.cargarDesdeXML();
        
   }
   
   public boolean guardarDatos() {
        try {
            gestorMedicos.guardar();
            gestorP.guardar();
            Farma.guardarDatos();
            gestorAdministrativos.guardar();
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            return false;
        }
    }
   
       
}

