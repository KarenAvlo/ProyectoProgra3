package com.mycompany.p1pro3;

import cr.ac.una.util.xml.XMLUtils;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
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
@XmlRootElement(name = "administrativos")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorAdministrativos {

    public static GestorAdministrativos cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorAdministrativos.class.getClassLoader().getResourceAsStream("administrativos.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró administrativos.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorAdministrativos.class);
        }
    }

    public void guardar() throws Exception {
        String ruta = "src/main/resources/administrativos.xml";
        try (PrintWriter salida = new PrintWriter(ruta)) {
            salida.println(XMLUtils.toXMLString(this));
        }
    }

    public Administrativo buscarPorCedula(String cedula) {
        Administrativo f1 = null;

        for (Administrativo f : ListaAdministrativos) {
            if (f.getCedula().equals(cedula)) {
                f1 = f;
            }
        }
        return f1;
    }

    public Administrativo buscarNombre(String nombre) {
        Administrativo f1 = null;

        for (Administrativo f : ListaAdministrativos) {
            if (f.getNombre().equals(nombre)) {
                f1 = f;
            }
        }
        return f1;
    }

    public boolean InclusionAdministrativo(String id, String nombre) {
        //cuando se agrega un administrativo, la clave es igual al id
        //luego podrá cambiarla
        if(!existeAdmi(id)){
         Administrativo fa= new Administrativo(id, nombre, id);
         return ListaAdministrativos.add(fa);
        }
        return false;        
    }

    public boolean existeAdmi(String ced) {
        return buscarPorCedula(ced) != null;
    }

    public boolean BorrarAdministrativo(String id) {
        Administrativo fa = this.buscarPorCedula(id);
        return ListaAdministrativos.remove(fa);
    }

    public void ConsultaAdministrativo(String cedula) {
        Administrativo fa = this.buscarPorCedula(cedula);
        fa.toString();
    }

    public void ModificarIdAdministrativo(String id, String nuevoId) {
        Administrativo fa = this.buscarPorCedula(id);
        fa.setCedula(nuevoId);
    }

    public void ModificarClaveAdministrativo(String id, String clave) {
        Administrativo fa = this.buscarPorCedula(id);
        fa.setClave(clave);
    }

    @Override
    public String toString() {
        String salida = "";
        for (Administrativo f : ListaAdministrativos) {
            salida += f.toString() + "\n ";
        }
        return salida;

    }
    //===============Atributos=============
     @XmlElement(name = "administrativo")
    private List<Administrativo> ListaAdministrativos = new ArrayList<>();

}
