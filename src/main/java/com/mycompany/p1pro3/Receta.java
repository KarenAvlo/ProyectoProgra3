package com.mycompany.p1pro3;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Receta {

//    private String codigo;
    private Paciente paciente;
    private Medico med;
//    private List medicamentos;
//    private int cantidad;
//    private String dias;
    private List<Indicaciones> indi; // medicamento,dia,indicacion,duracion
    private String fechaEmision;
    private String fechaRetiro;
//    private String estado; //pendiente-entregado

    public void AgregarIndicaciones(Indicaciones i) {
        indi.add(i);
    }

    public void EliminarIndicacion(String codMedicamento) {
        indi.removeIf(i -> i.getMedicamento().getCodigo().equals(codMedicamento));
    }

    public void ModificarIndicaciones(String codigoMedicamento, String nuevomed, int cantidad,
            String indicaciones, int duracionDias) {
        for (Indicaciones i : indi) {
            if (i.getMedicamento().getCodigo().equals(codigoMedicamento)) { // buscamos el medicamento por codigo el que deseamos cambiar
                i.setMedicamento(medicamento);
                i.setCantidad(cantidad);
                i.setIndicaciones(indicaciones);
                i.setDuracion(duracionDias);
            }
        }

    }

    public void finalizarReceta(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
//        this.estado = "CONFECCIONADA"; // aqui debe ser como entregada o algo así xd
    }
}
