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
@XmlRootElement(name = "medicos")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorMedicos {

    public static GestorMedicos cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorMedicos.class.getClassLoader().getResourceAsStream("medicos.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró medicos.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorMedicos.class);
        }
    }

    public void guardar() throws Exception {
        String ruta = "src/main/resources/medicos.xml";
        try (PrintWriter salida = new PrintWriter(ruta)) {
            salida.println(XMLUtils.toXMLString(this));
        }
    }

    public Medico buscarPorCedula(String cedula) {
        Medico m1 = null;
        for (Medico m : ListaMedicos) {
            if (m.getCedula().equals(cedula)) {
                m1 = m;
            }
        }
        return m1;
    }

    public Medico buscarNombre(String nombre) {
        Medico f1 = null;
        for (Medico f : ListaMedicos) {
            if (f.getNombre().equals(nombre)) {
                f1 = f;
            }
        }
        return f1;
    }

    public boolean InclusionMedico(String id, String nombre, String especialidad) {
        if(!existeMedico(id)){
        Medico fa = new Medico(id, nombre, especialidad, id);

        return ListaMedicos.add(fa);
        }
        return false;
    }
    
    public boolean existeMedico(String ced){
    return buscarPorCedula(ced)!=null;
    }

    public boolean BorrarMedico(String id) {
        Medico fa = this.buscarPorCedula(id);
        return ListaMedicos.remove(fa);
    }

    public void ConsultaMedico(String cedula) {
        Medico fa = this.buscarPorCedula(cedula);
        fa.toString();
    }

    public void ModificarIdMedico(String id, String nuevoId) {
        Medico fa = this.buscarPorCedula(id);
        fa.setCedula(nuevoId);
    }

    public void ModificarClaveMedico(String id, String clave) {
        Medico fa = this.buscarPorCedula(id);
        fa.setClave(clave);
    }

    @Override
    public String toString() {
        String salida = "";
        for (Medico f : ListaMedicos) {
            salida += f.toString() + "\n ";
        }
        return salida;

    }

    @XmlElement(name = "medico")
    private List<Medico> ListaMedicos = new ArrayList<>();
}
