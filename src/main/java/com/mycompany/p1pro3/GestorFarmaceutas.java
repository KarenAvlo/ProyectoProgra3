
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
@XmlRootElement(name = "farmaceutas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GestorFarmaceutas {

    @XmlElement(name = "farmaceuta")
    private List<Farmaceuta> ListaFarmaceutas = new ArrayList<>();

    public void cargarDesdeXML() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("farmaceutas.xml");
            if (input == null) {
                System.out.println("No se encontró el archivo XML");
                return;
            }

            // ✅ Aquí usamos GestordePacientes.class
            JAXBContext context = JAXBContext.newInstance(GestorFarmaceutas.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            GestorFarmaceutas wrapper = (GestorFarmaceutas) unmarshaller.unmarshal(input);
            this.ListaFarmaceutas = wrapper.getListaFarmaceutas();

            System.out.println("Pacientes cargados correctamente: " + ListaFarmaceutas.size());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    public Farmaceuta buscarPorCedula(String cedula) {
        Farmaceuta f1 = null;

        for (Farmaceuta f : ListaFarmaceutas) {
            if (f.getCedula().equals(cedula)) {
                f1 = f;
            }
        }
        return f1;
    }
}

