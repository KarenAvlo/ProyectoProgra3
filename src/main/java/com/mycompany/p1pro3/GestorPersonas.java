package com.mycompany.p1pro3;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GestorPersonas {

    private List<Persona> personas = new ArrayList<>();

    public void cargarTodo() throws IOException, JAXBException {
        GestorAdministrativos gp = GestorAdministrativos.cargarDesdeXML();
        personas.addAll(gp.getListaAdministrativos());

        GestorFarmaceutas gf = GestorFarmaceutas.cargarDesdeXML();
        personas.addAll(gf.getListaFarmaceutas());

        GestorMedicos gm = GestorMedicos.cargarDesdeXML();
        personas.addAll(gm.getListaMedicos());

    }

    public Persona login(String cedula, String clave) {
        for (Persona p : personas) {
            if (p.getCedula().equals(cedula) && p.getClave().equals(clave)) {
                return p;
            }
        }
        return null; // login fallido
    }

    public void distribuirEnGestores(GestorMedicos gm, GestorFarmaceutas gf, GestorAdministrativos ga) {
        for (Persona p : personas) {
            if (p instanceof Medico m) {
                gm.getListaMedicos().add(m);
            } else if (p instanceof Farmaceuta f) {
                gf.getListaFarmaceutas().add(f);
            } else if (p instanceof Administrativo a) {
                ga.getListaAdministrativos().add(a);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Persona f : personas) {
            sb.append(f.toString()).append("\n");
        }
        return sb.toString();

    }

}
