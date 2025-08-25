package com.mycompany.p1pro3;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@ToString(callSuper = true)

public class Medico extends Persona {

    public Medico(String cedula, String nombre, String especialidad, String clave) {
        super(cedula, nombre);
        this.especialidad = especialidad;
        this.clave = clave;
    }

    public Receta prescribirReceta(String idPaciente, String fechaEmision, List<Paciente> lp) {
        Paciente p = null;
        for (Paciente pp : lp) {
            if (pp.getCedula().equals(idPaciente)) {
                p = pp;
            }
        }

        //falta como pensar la fecha de Retiro
        Receta re = new Receta(p, this, indi, fechaEmision, "dd-mm-aa", "Inprocess");
        re.finalizarReceta(fechaEmision);

        // 🔹 Resetear lista para la siguiente receta
        indi = new ArrayList<>();

        return re;

    }

    public void CrearIndicacion(String codMed, int cant, String indicaciones, int duracion,
            List<Medicamento> medicamentosdisp) {

        //En lista de medicamentos hay que llamar a farmacia la cual los contiene
        Medicamento medicamento = null;
        for (Medicamento m : medicamentosdisp) {
            if (m.getCodigo().equals(codMed)) {
                medicamento = m;
            }
        }

        Indicaciones i = new Indicaciones(medicamento, cant, indicaciones, duracion);
        indi.add(i); // añadimos la indicacion a la lista de indicaciones
    }

    public void modificarReceta(Receta re, String codigoMedicamento, String nuevomed, int cantidad,
            String indicaciones, int duracionDias, List<Medicamento> medicamentosdisp) {

        re.ModificarIndicaciones(codigoMedicamento, nuevomed, cantidad, indicaciones, duracionDias,
                medicamentosdisp);
    }

    private String especialidad;
    private String clave;
    // hay que liberarla cada que se hace una receta
    private List<Indicaciones> indi = new ArrayList<>();
}
