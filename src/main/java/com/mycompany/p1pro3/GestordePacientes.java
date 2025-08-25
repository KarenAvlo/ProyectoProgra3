package com.mycompany.p1pro3;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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

    public void cargarDesdeXML() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("pacientes.xml");
            if (input == null) {
                System.out.println("No se encontró el archivo XML");
                return;
            }

            // ✅ Aquí usamos GestordePacientes.class
            JAXBContext context = JAXBContext.newInstance(GestordePacientes.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            GestordePacientes wrapper = (GestordePacientes) unmarshaller.unmarshal(input);
            this.ListaPacientes = wrapper.getListaPacientes();

            System.out.println("Pacientes cargados correctamente: " + ListaPacientes.size());

        } catch (JAXBException e) {
            e.printStackTrace();
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
