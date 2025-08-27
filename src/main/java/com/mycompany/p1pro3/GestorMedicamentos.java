package com.mycompany.p1pro3;

import cr.ac.una.util.xml.XMLUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
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
@XmlRootElement(name = "medicamentos")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorMedicamentos {

    @XmlElement(name = "medicamento")
    private List<Medicamento> ListaMedicamentos = new ArrayList<>();

    public static GestorMedicamentos  cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorFarmaceutas.class.getClassLoader().getResourceAsStream("medicamentos.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró medicamentos.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorMedicamentos.class);
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
