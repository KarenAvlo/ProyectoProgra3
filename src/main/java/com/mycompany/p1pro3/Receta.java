package com.mycompany.p1pro3;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Receta {
    private String codigo;
    private Paciente paciente;
    private List medicamentos;
    private int cantidad;
    private String dias;
    private String indicaciones;
    private String fechaEmision;
    private String fechaRetiro;
}
