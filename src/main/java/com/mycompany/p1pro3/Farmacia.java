package com.mycompany.p1pro3;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Farmacia {
    private GestorFarmaceutas gestorFarmaceutas;   // Farmaceutas que trabajan en la farmacia
    private GestorMedicamentos gestorMedicamentos; // Lista de medicamentos
    private GestorRecetas gestorRecetas;           // Lista de recetas
   
    public void cargarDatos() throws Exception {
        gestorFarmaceutas = GestorFarmaceutas.cargarDesdeXML();
        gestorMedicamentos = GestorMedicamentos.cargarDesdeXML();
        gestorRecetas = GestorRecetas.cargarDesdeXML();
    }
    
//    private List<Farmaceuta> farmaceutas = new ArrayList<>();
//    private List<Medicamento> medicinas = new ArrayList<>();
//    private List<Receta> recetas = new ArrayList<>();
}
