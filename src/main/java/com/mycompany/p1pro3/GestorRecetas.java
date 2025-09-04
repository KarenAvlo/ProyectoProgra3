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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
//Del gráfico
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PiePlot;

@Data
@XmlRootElement(name = "recetas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GestorRecetas {

 
    // Cargar desde XML
    public static GestorRecetas cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorRecetas.class.getClassLoader().getResourceAsStream("Recetas.xml")) {
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
    
    public int cantidadRecetas(){
        return listaRecetas.size();
    }

    // Modificar una receta (ejemplo: cambiar estado)
    public void modificarEstadoReceta(String codReceta, String nuevoEstado) {
        Receta r = buscarPorCodigo(codReceta);
        if (r != null) {
            r.setEstado(nuevoEstado);
        }
    }
    
    
    //Gráfica de pastel
    
    public JFreeChart crearGraficoPastelRecetasPorEstado() {
        PieDataset dataset = crearDatasetRecetasPorEstado();
        JFreeChart chart = ChartFactory.createPieChart(
                "Recetas por estado", // título
                dataset,
                true,                 // leyenda
                true,                 // tooltips
                false                 // URLs
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setNoDataMessage("No hay recetas registradas");
        plot.setCircular(true);
        plot.setSimpleLabels(true); // etiquetas más limpias

        // Etiquetas: Nombre = cantidad (porcentaje)
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} = {1} ({2})",
                new DecimalFormat("0"),      // cantidad
                new DecimalFormat("0.0%")    // porcentaje
        ));

        return chart;
    }
    
    private PieDataset crearDatasetRecetasPorEstado() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        if (listaRecetas == null || listaRecetas.isEmpty()) {
            // JFreeChart mostrará "No hay recetas registradas", pero
            // dejamos el dataset vacío para que ese mensaje aparezca.
            return dataset;
        }

        // Normalizamos a String (funciona si es enum o String)
        Map<String, Long> conteo = listaRecetas.stream()
                .collect(Collectors.groupingBy(
                        r -> {
                            Object e = r.getEstado();
                            return (e == null) ? "Sin estado" : e.toString();
                        },
                        Collectors.counting()
                ));
        // Llenar el dataset
        conteo.forEach(dataset::setValue);
        return dataset;
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
