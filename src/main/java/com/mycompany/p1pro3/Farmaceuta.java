package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Setter
@Getter
@AllArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "farmaceuta")
@XmlAccessorType(XmlAccessType.FIELD)

public class Farmaceuta extends Persona {

    public Farmaceuta(String cedula, String nombre, String clave) {
        super(cedula, nombre, clave);
    }

    public boolean ProcesarReceta(String idPaciente, String codRec, List<Paciente> p, List<Receta> re) {

        Receta receta = null;
        for (Receta r : re) {
            if (r.getCodReceta().equals(codRec)) {
                receta = r;
            }
        }
        if (receta != null && receta.getEstado().equals("CONFECCIONADA")
                && (receta.getFechaRetiro().equals(LocalDate.now())
                || receta.getFechaRetiro().equals(LocalDate.now().plusDays(1))
                || receta.getFechaRetiro().equals(LocalDate.now().plusDays(2))
                || receta.getFechaRetiro().equals(LocalDate.now().plusDays(3)))) {
            receta.setEstado("En Proceso");
            return true;
        }
        return false;
    }

    public void enlistarReceta(String idPaciente, String codRec, List<Paciente> p, List<Receta> re) {
        if (ProcesarReceta(idPaciente, codRec, p, re)) {
            Receta receta = null;
            for (Receta r : re) {
                if (r.getCodReceta().equals(codRec)) {
                    receta = r;
                }
            }
            if (receta != null) {
                receta.setEstado("Lista");
            }
        }
    }

    public void DespacharReceta(String idPaciente, String codRec, List<Paciente> p, List<Receta> re) {
        Receta receta = null;
        for (Receta r : re) {
            if (r.getCodReceta().equals(codRec)) {
                receta = r;
            }
        }
        if (receta != null && receta.getEstado().equals("Lista")) {
            receta.setEstado("Entregada");
        }
    }
}
