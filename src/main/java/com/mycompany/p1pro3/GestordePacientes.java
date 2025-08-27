package com.mycompany.p1pro3;

import cr.ac.una.util.xml.XMLUtils;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.Data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "pacientes")
@XmlAccessorType(XmlAccessType.FIELD)
public class GestordePacientes {

    @XmlElement(name = "paciente")
    private List<Paciente> ListaPacientes = new ArrayList<>();

    public static GestordePacientes cargarDesdeXML() throws IOException, JAXBException {
       try (InputStream is = GestorFarmaceutas.class.getClassLoader().getResourceAsStream("pacientes.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró pacientes.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestordePacientes.class);
        }
    }

    public void agregarPaciente(Paciente p) {
        ListaPacientes.add(p);
    }

    public Paciente buscarPorCedula(String cedula) {
        Paciente p1 = null;

        for (Paciente p : ListaPacientes) {
            if (p.getCedula().equals(cedula)) {
                p1 = p;
            }
        }
        return p1;
    }
}
