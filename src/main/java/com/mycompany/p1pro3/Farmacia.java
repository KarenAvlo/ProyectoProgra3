package com.mycompany.p1pro3;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Farmacia {

     public Farmacia(GestorFarmaceutas gestorFarmaceutas) {
        this.gestorFarmaceutas = gestorFarmaceutas; // usa el mismo que Hospital
        this.gestorMedicamentos = new GestorMedicamentos();
        this.gestorRecetas = new GestorRecetas();
    }
     
    private GestorFarmaceutas gestorFarmaceutas;   // Farmaceutas que trabajan en la farmacia
    private GestorMedicamentos gestorMedicamentos; // Lista de medicamentos
    private GestorRecetas gestorRecetas;           // Lista de recetas
   
    
    
    
    public void cargarDatos() throws Exception {
        gestorMedicamentos = GestorMedicamentos.cargarDesdeXML();
        gestorRecetas = GestorRecetas.cargarDesdeXML();
    }
    
    public boolean guardarDatos() throws Exception{
        try {
            gestorFarmaceutas.guardar();
            gestorMedicamentos.guardar(); // Asumiendo que GestorPacientes tiene método guardar()
            gestorRecetas.guardar(); // Asumiendo que Farmacia tiene método guardarDatos()
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            return false;
        }
    }
    
    
//    private List<Farmaceuta> farmaceutas = new ArrayList<>();
//    private List<Medicamento> medicinas = new ArrayList<>();
//    private List<Receta> recetas = new ArrayList<>();
}
