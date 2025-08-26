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
@XmlRootElement(name = "medicamentos")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorMedicamentos {

    @XmlElement(name = "medicamento")
    private List<Medicamento> ListaMedicamentos = new ArrayList<>();

    public void cargarDesdeXML() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("medicamentos.xml");
            if (input == null) {
                System.out.println("No se encontró el archivo XML");
                return;
            }

            
            JAXBContext context = JAXBContext.newInstance(GestorMedicamentos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            GestorMedicamentos wrapper = (GestorMedicamentos) unmarshaller.unmarshal(input);
            this.ListaMedicamentos = wrapper.getListaMedicamentos();

            System.out.println("Medicamentos cargados correctamente: " + ListaMedicamentos.size());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Medicamento buscarPorCodigo(String cod) {
        Medicamento m1 = null;

        for (Medicamento m : ListaMedicamentos) {
            if (m.getCodigo().equals(cod)) {
                m1 = m;
            }
        }
        return m1;
    }
}
