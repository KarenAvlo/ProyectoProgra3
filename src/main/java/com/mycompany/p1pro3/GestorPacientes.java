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

@Data
@XmlRootElement(name = "pacientes")
@XmlAccessorType(XmlAccessType.FIELD)
public class GestorPacientes {

    @XmlElement(name = "paciente")
    private List< Paciente> ListaPacientes = new ArrayList<>();

    public static GestorPacientes cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorPacientes.class.getClassLoader().getResourceAsStream("pacientes.xml")) {
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
        //cuando se agrega un farmaceuta, la clave es igual al id
        //luego podrá cambiarla
        Paciente fa = new Paciente(id, nombre, numero, nacimiento);

        return ListaPacientes.add(fa);
    }

    public boolean BorrarPaciente(String id) {
        Paciente fa = this.buscarPorCedula(id);
        return ListaPacientes.remove(fa);
    }

    public void ConsultaPaciente(String cedula) {
        Paciente fa = this.buscarPorCedula(cedula);
        fa.toString();
        // talves sea necesario algo como su lista de medicamentos
        //o las recetas que ha tenido
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
}
