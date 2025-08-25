package com.mycompany.p1pro3;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Receta {
    private Paciente paciente;
    private Medico med;
    private List<Indicaciones> indi; // medicamento,dia,indicacion,duracion
    private String fechaEmision;
    private String fechaRetiro;
    private String estado; //Confeccionada

  
    public void ModificarIndicaciones(String codigoMedicamento, String nuevomed, int cantidad,
            String indicaciones, int duracionDias, List<Medicamento> medicamentosdisp) {

        Medicamento nuevoMedicamento = null;
        for (Medicamento m : medicamentosdisp) {
            if (m.getCodigo().equals(nuevomed)) {
                nuevoMedicamento = m;
            }
        }
        
        for (Indicaciones i : indi) {
            if (i.getMedicamento().getCodigo().equals(codigoMedicamento)) { // buscamos el medicamento por codigo el que deseamos cambiar
                i.setMedicamento(nuevoMedicamento);
                i.setCantidad(cantidad);
                i.setIndicaciones(indicaciones);
                i.setDuracion(duracionDias);
            }
        }

    }

    public void finalizarReceta(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
        this.estado = "CONFECCIONADA"; // aqui debe ser como entregada o algo así xd
    }
}
