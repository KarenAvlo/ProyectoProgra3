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

@Data
@XmlRootElement(name = "recetas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GestorRecetas {

 
    // Cargar desde XML
    public static GestorRecetas cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorRecetas.class.getClassLoader().getResourceAsStream("recetas.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró recetas.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorRecetas.class);
        }
    }

    // Guardar en XML
    public void guardar() throws Exception {
        String ruta = "src/main/resources/recetas.xml";
        try (PrintWriter salida = new PrintWriter(ruta)) {
            salida.println(XMLUtils.toXMLString(this));
        }
    }

    // Buscar receta por código
    public Receta buscarPorCodigo(String codReceta) {
        for (Receta r : listaRecetas) {
            if (r.getCodReceta().equals(codReceta)) {
                return r;
            }
        }
        return null;
    }

    // Incluir una nueva receta
    public boolean agregarReceta(Receta r) {
        return listaRecetas.add(r);
    }

    // Borrar receta
    public boolean borrarReceta(String codReceta) {
        Receta r = buscarPorCodigo(codReceta);
        return r != null && listaRecetas.remove(r);
    }

    // Modificar una receta (ejemplo: cambiar estado)
    public void modificarEstadoReceta(String codReceta, String nuevoEstado) {
        Receta r = buscarPorCodigo(codReceta);
        if (r != null) {
            r.setEstado(nuevoEstado);
        }
    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder();
        for (Receta r : listaRecetas) {
            salida.append(r.toString()).append("\n");
        }
        return salida.toString();
    }
    
    @XmlElement(name = "receta")
    private List<Receta> listaRecetas = new ArrayList<>();
    
}
