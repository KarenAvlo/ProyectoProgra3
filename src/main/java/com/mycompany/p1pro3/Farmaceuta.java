package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = "clave")
@XmlRootElement(name = "farmaceuta")
@XmlAccessorType(XmlAccessType.FIELD)

public class Farmaceuta extends Persona {

    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre);
        this.clave = clave;
    }

    public boolean ProcesarReceta(String idPaciente, String codRec, List<Paciente> p, List<Receta> re) {

        Receta receta = null;
        for (Receta r : re) {
            if (r.getCodReceta().equals(codRec)) {
                receta = r;
            }
        }

        //si la receta tiene un estado de confeccionada, y su fecha de retiro es el mismo dia
        //de emitida o 3 dias posteriores, entonces ahora la ponemos en proceso
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

        //si está procesada, alistamos medicamentos y ponemos lista
        if (ProcesarReceta(idPaciente, codRec, p, re)) {
            //no se si hay que hacer algo con los medicamentos xd
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

    //------Atributos--------------
    @XmlElement(name = "clave")
    private String clave;
}
