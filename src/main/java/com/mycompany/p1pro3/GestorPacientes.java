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
import java.io.PrintWriter;
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
@Data
@XmlRootElement(name = "pacientes")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorPacientes {

    public static GestorPacientes cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorPacientes.class.getClassLoader().getResourceAsStream("Pacientes.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró pacientes.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorPacientes.class);
        }
    }

    public void guardar() throws Exception {
        String ruta = "src/main/resources/pacientes.xml";
        try (PrintWriter salida = new PrintWriter(ruta)) {
            salida.println(XMLUtils.toXMLString(this));
        }
    }

    public Paciente buscarPorCedula(String cedula) {
        Paciente f1 = null;
        for (Paciente f : ListaPacientes) {
            if (f.getCedula().equals(cedula)) {
                f1 = f;
            }
        }
        return f1;
    }

    public Paciente buscarNombre(String nombre) {
        Paciente f1 = null;
        for (Paciente f : ListaPacientes) {
            if (f.getNombre().equals(nombre)) {
                f1 = f;
            }
        }
        return f1;
    }

    public boolean InclusionPaciente(String id, String nombre, String nacimiento, String numero) {
        if (!existePaciente(id)) {
            Paciente fa = new Paciente(numero, nacimiento, id, nombre);
            return ListaPacientes.add(fa);
        }
        return false;
    }

    public boolean existePaciente(String ced) {
        return buscarPorCedula(ced) != null;
    }

    public boolean BorrarPaciente(String id) {
        Paciente fa = this.buscarPorCedula(id);
        return ListaPacientes.remove(fa);
    }

    public void ConsultaPaciente(String cedula) {
        Paciente fa = this.buscarPorCedula(cedula);
        fa.toString();
    }

    public void ModificarIdPaciente(String id, String nuevoId) {
        Paciente fa = this.buscarPorCedula(id);
        fa.setCedula(nuevoId);
    }

    @Override
    public String toString() {
        String salida = "";
        for (Paciente f : ListaPacientes) {
            salida += f.toString() + "\n ";
        }
        return salida;

    }

    @XmlElement(name = "paciente")
    private List< Paciente> ListaPacientes = new ArrayList<>();
}
