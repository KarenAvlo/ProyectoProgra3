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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@ToString(exclude = {"clave", "indi"}) // esto es para que no salga la clave el medico
// en el toString, ni las indicaciones
@XmlRootElement(name = "medico")
@XmlAccessorType(XmlAccessType.FIELD)

public class Medico {   
    
    /*
    public Medico(String cedula, String nombre, String especialidad, String clave) {
        super(cedula, nombre);
        this.especialidad = especialidad;
        this.clave = clave;
    }
    */
    public Receta prescribirReceta(String codReceta, String idPaciente, 
                               List<Paciente> lp, List<Receta> lre) {
        Paciente p = null;
        for (Paciente pp : lp) {
            if (pp.getCedula() != null && pp.getCedula().equals(idPaciente)) {
                p = pp;
            }
        }

        // Se crea la receta con una lista de indicaciones vacía
        Receta re = new Receta(codReceta, p, this, new ArrayList<>(), null, null, "Inprocess");
        re.finalizarReceta();

        lre.add(re);
        return re;

    }

    public void CrearIndicacion(Receta receta, String codMed, int cant, 
                            String indicaciones, int duracion,
                            List<Medicamento> medicamentosdisp)  {

        Medicamento medicamento = null;
        for (Medicamento m : medicamentosdisp) {
            if (m.getCodigo().equals(codMed)) {
                medicamento = m;
            }
        }

        if (medicamento != null) {
            Indicaciones i = new Indicaciones(medicamento, cant, indicaciones, duracion);
            receta.getIndi().add(i); // ahora se agregan directamente a la receta
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
    
    
    @XmlElement(name = "cedula")
    private String cedula;
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "especialidad")
    private String especialidad;
    @XmlElement(name = "clave")
    private String clave;
    
    
    
}
