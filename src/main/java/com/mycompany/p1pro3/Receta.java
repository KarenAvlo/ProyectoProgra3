package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
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
@ToString
@Getter
@Setter
@XmlRootElement(name = "receta")
@XmlAccessorType(XmlAccessType.FIELD)

public class Receta {

    public void ModificarIndicaciones(String codigoMedicamento, String nuevomed, int cantidad,
            String indicaciones, int duracionDias, List<Medicamento> medicamentosdisp) {
        Medicamento nuevoMedicamento = null;
        for (Medicamento m : medicamentosdisp) {
            if (m.getCodigo().equals(nuevomed)) {
                nuevoMedicamento = m;
            }
        }
        for (Indicaciones i : this.indicaciones) {
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
    
    public void agregarIndicaciones(Indicaciones nuevaIndicacion) {
        this.indicaciones.add(nuevaIndicacion);
    }

    @XmlElement(name = "codReceta")
    private String codReceta;
    @XmlElement(name = "paciente")
    private Paciente paciente;
    @XmlElement(name = "medico")
    private Medico medico;
    @XmlElement(name = "indicaciones")
    private List<Indicaciones> indicaciones = new ArrayList<>();
    @XmlElement(name = "fechaEmision")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaEmision;
    @XmlElement(name = "fechaRetiro")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaRetiro;
    @XmlElement(name = "estado")
    private String estado;
}

