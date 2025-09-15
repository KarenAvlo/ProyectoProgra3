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
@XmlRootElement(name = "farmaceutas")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorFarmaceutas {

    public static GestorFarmaceutas cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorFarmaceutas.class.getClassLoader().getResourceAsStream("farmaceutas.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró farmaceutas.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorFarmaceutas.class);
        }
    }

    public void guardar() throws Exception {
        String ruta = "src/main/resources/farmaceutas.xml";
        try (PrintWriter salida = new PrintWriter(ruta)) {
            salida.println(XMLUtils.toXMLString(this));
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

    public Farmaceuta buscarNombre(String nombre) {
        Farmaceuta f1 = null;

        for (Farmaceuta f : ListaFarmaceutas) {
            if (f.getNombre().equals(nombre)) {
                f1 = f;
            }
        }
        return f1;
    }

    public boolean InclusionFarmaceuta(String id, String nombre) {
        if (!existeFarma(id)) {
            Farmaceuta fa = new Farmaceuta(id, nombre, id);
            return ListaFarmaceutas.add(fa);
        }
        return false;
    }

    public boolean existeFarma(String ced) {
        return buscarPorCedula(ced) != null;
    }

    public boolean BorrarFarmaceuta(String id) {
        Farmaceuta fa = this.buscarPorCedula(id);
        return ListaFarmaceutas.remove(fa);
    }

    public void ConsultaFarmaceuta(String cedula) {
        Farmaceuta fa = this.buscarPorCedula(cedula);
        fa.toString();
    }

    public void ModificarIdFarmaceuta(String id, String nuevoId) {
        Farmaceuta fa = this.buscarPorCedula(id);
        fa.setCedula(nuevoId);
    }

    public void ModificarClaveFarmaceuta(String id, String clave) {
        Farmaceuta fa = this.buscarPorCedula(id);
        fa.setClave(clave);
    }

    @Override
    public String toString() {
        String salida = "";
        for (Farmaceuta f : ListaFarmaceutas) {
            salida += f.toString() + "\n ";
        }
        return salida;

    }

    @XmlElement(name = "farmaceuta")
    private List<Farmaceuta> ListaFarmaceutas = new ArrayList<>();
}
