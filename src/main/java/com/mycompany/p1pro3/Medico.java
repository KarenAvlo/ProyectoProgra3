package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
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
@ToString(callSuper = true, exclude = {"clave", "indi"}) // esto es para que no salga la clave el medico
// en el toString, ni las indicaciones
@XmlRootElement(name = "medico")
@XmlAccessorType(XmlAccessType.FIELD)

public class Medico extends Persona {

    public Medico(String cedula, String nombre, String especialidad, String clave) {
        super(cedula, nombre);
        this.especialidad = especialidad;
        this.clave = clave;
    }

    public Receta prescribirReceta(String codReceta, String idPaciente, List<Paciente> lp,
            List<Receta> lre) {
        Paciente p = null;
        for (Paciente pp : lp) {
            if (pp.getCedula() != null && pp.getCedula().equals(idPaciente)) {
                p = pp;
            }
        }

        Receta re = new Receta(codReceta, p, this, indi, null, null, "Inprocess");
        re.finalizarReceta();

        lre.add(re); // añadimos a la lista general de recetas
        
        
        //  Resetear lista para la siguiente receta
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
    @XmlElement(name = "clave")
    private String clave;
    // hay que liberarla cada que se hace una receta
    private List<Indicaciones> indi = new ArrayList<>();
}
