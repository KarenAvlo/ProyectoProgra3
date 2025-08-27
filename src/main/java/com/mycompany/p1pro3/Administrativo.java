package com.mycompany.p1pro3;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Administrativo {
    private String clave;
    private String cedula;
    private String nombre;
//como es administativo, puede administrar las listas de medicos y sus funcionalidades
    // de la misma manera conlistas de farmaceutas y pacientes
    
  
}
