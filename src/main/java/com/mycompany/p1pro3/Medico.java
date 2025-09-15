package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
/* -------------------------------------------------------------------+
*                                                                     |
* (c) 2025                                                            |
* EIF206 - Programación 3                                             |
* 2do ciclo 2025                                                      |
* NRC 51189 – Grupo 05                                                |
* Proyecto 1                                                          |
*                                                                     |
* 2-0816-0954; Avilés López, Karen Minards                            |
* 4-0232-0641; Zárate Hernández, Nicolas Alfredo                      |
*                                                                     |
* versión 1.0.0 13-09-2005                                            |
*                                                                     |
* --------------------------------------------------------------------+
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true) 
@XmlRootElement(name = "medico")
@XmlAccessorType(XmlAccessType.FIELD)

public class Medico extends Persona {

    public Medico(String cedula, String nombre, String especialidad, String clave) {
        super(cedula, nombre,clave);
        this.especialidad = especialidad;
    }

    public Receta prescribirReceta(String codReceta, String idPaciente,
            List<Paciente> lp, List<Receta> lre) {
        Paciente p = null;
        for (Paciente pp : lp) {
            if (pp.getCedula() != null && pp.getCedula().equals(idPaciente)) {
                p = pp;
            }
        }
        Receta re = new Receta(codReceta, p, this, new ArrayList<>(), null, null, "Inprocess");
        re.finalizarReceta();
        lre.add(re);
        return re;
    }

    public void CrearIndicacion(Receta receta, String codMed, int cant,
            String indicaciones, int duracion,
            List<Medicamento> medicamentosdisp) {
        Medicamento medicamento = null;
        for (Medicamento m : medicamentosdisp) {
            if (m.getCodigo().equals(codMed)) {
                medicamento = m;
            }
        }
        if (medicamento != null) {
            Indicaciones i = new Indicaciones(medicamento, cant, indicaciones, duracion);
            receta.getIndicaciones().add(i);
        }
    }

    public void modificarReceta(String codReceta, String codigoMedicamento, String nuevomed, int cantidad,
            String indicaciones, int duracionDias, List<Medicamento> medicamentosdisp,
            List<Receta> recetas) {
        Receta re = null;
        for (Receta r : recetas) {
            if (r.getCodReceta().equals(codReceta)) {
                re = r;
            }
        }
        if (re != null) {
            re.ModificarIndicaciones(codigoMedicamento, nuevomed, cantidad, indicaciones, duracionDias,
                    medicamentosdisp);
        }
    }

    @XmlElement(name = "especialidad")
    private String especialidad;
}
