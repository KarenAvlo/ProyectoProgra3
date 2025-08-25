package com.mycompany.p1pro3;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data

public class Receta {
    private String codReceta;
    private Paciente paciente;
    private Medico med;
    private List<Indicaciones> indi; // medicamento,dia,indicacion,duracion
    private LocalDate fechaEmision; // asigna la fecha de hoy, ( si se confecciona hoy)
    private LocalDate fechaRetiro; // asi podemos verificar la fecha mas easy
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

    public void finalizarReceta() {
        this.fechaEmision= LocalDate.now();
        this.fechaRetiro = fechaEmision.plusDays(3); //tres dias despues de emitida
        this.estado = "CONFECCIONADA"; 
    }
}
