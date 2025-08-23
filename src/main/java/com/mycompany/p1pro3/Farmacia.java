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
    
    
    
    private List<Farmaceuta> farmaceutas = new ArrayList<>();
    private List<Medicamento> medicinas = new ArrayList<>();
    private List<Receta> recetas = new ArrayList<>();
}
