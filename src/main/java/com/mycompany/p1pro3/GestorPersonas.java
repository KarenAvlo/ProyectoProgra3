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

    public void cargarTodo(GestorAdministrativos gestorAdministrativos,
                       GestorFarmaceutas gestorFarmaceutas,
                       GestorMedicos gestorMedicos) throws JAXBException, IOException {

        // 1️⃣ Cargar administrativos desde XML
        System.out.println("Cargando administrativos...");
        GestorAdministrativos ga = GestorAdministrativos.cargarDesdeXML();
        if (ga != null) {
            personas.addAll(ga.getListaAdministrativos()); // lista central
            gestorAdministrativos.getListaAdministrativos().addAll(ga.getListaAdministrativos()); // sincroniza
        }

        // 2️⃣ Cargar farmaceutas desde XML
        System.out.println("Cargando farmaceutas...");
        GestorFarmaceutas gf = GestorFarmaceutas.cargarDesdeXML();
        if (gf != null) {
            personas.addAll(gf.getListaFarmaceutas());
            gestorFarmaceutas.getListaFarmaceutas().addAll(gf.getListaFarmaceutas());
        }

        // 3️⃣ Cargar médicos desde XML
        System.out.println("Cargando medicos...");
        GestorMedicos gm = GestorMedicos.cargarDesdeXML();
        if (gm != null) {
            personas.addAll(gm.getListaMedicos());
            gestorMedicos.getListaMedicos().addAll(gm.getListaMedicos());
        }
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
