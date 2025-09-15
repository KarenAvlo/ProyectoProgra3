package com.mycompany.p1pro3;

import cr.ac.una.util.xml.XMLUtils;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Getter;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
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
@Getter
@XmlRootElement(name = "recetas")
@XmlAccessorType(XmlAccessType.FIELD)

public class GestorRecetas {

    public static GestorRecetas cargarDesdeXML() throws IOException, JAXBException {
        try (InputStream is = GestorRecetas.class.getClassLoader().getResourceAsStream("Recetas.xml")) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró recetas.xml en resources");
            }
            return XMLUtils.loadFromXML(is, GestorRecetas.class);
        }
    }

    public void guardar() throws Exception {
        String ruta = "src/main/resources/recetas.xml";
        try (PrintWriter salida = new PrintWriter(ruta)) {
            salida.println(XMLUtils.toXMLString(this));
        }
    }

    public Receta buscarPorCodigo(String codReceta) {
        for (Receta r : listaRecetas) {
            if (r.getCodReceta().equals(codReceta)) {
                return r;
            }
        }
        return null;
    }

    public boolean agregarReceta(Receta r) {
        return listaRecetas.add(r);
    }

    public boolean borrarReceta(String codReceta) {
        Receta r = buscarPorCodigo(codReceta);
        return r != null && listaRecetas.remove(r);
    }
    
    public int cantidadRecetas(){
        return listaRecetas.size();
    }

    public void modificarEstadoReceta(String codReceta, String nuevoEstado) {
        Receta r = buscarPorCodigo(codReceta);
        if (r != null) {
            r.setEstado(nuevoEstado);
        }
    }

    public JFreeChart crearGraficoPastelRecetasPorEstado(LocalDate fechaInicio, LocalDate fechaFin) {
        PieDataset dataset = crearDatasetRecetasPorEstado(fechaInicio, fechaFin);
        JFreeChart chart = ChartFactory.createPieChart(
                "Recetas (" + fechaInicio + " a " + fechaFin + ")",
                dataset,
                true,                 // leyenda
                true,                 // tooltips
                false                 // URLs
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setNoDataMessage("No hay recetas registradas");
        plot.setCircular(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} = {1} ({2})",
                new DecimalFormat("0"),    
                new DecimalFormat("0.0%")    
        ));
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        plot.setSimpleLabels(false); 
        plot.setLabelGap(0.02);     
        plot.setInteriorGap(0.04);  
        plot.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator("Cantidad: {1}"));
        return chart;
    }
    
    private PieDataset crearDatasetRecetasPorEstado(LocalDate fechaInicio, LocalDate fechaFin) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        if (listaRecetas == null || listaRecetas.isEmpty()) {
            return dataset;
        }
        Map<String, Long> conteo = listaRecetas.stream()
                .filter(r -> {
                    LocalDate fecha = r.getFechaEmision();
                    return fecha != null
                            && (fecha.isEqual(fechaInicio) || fecha.isAfter(fechaInicio))
                            && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin));
                })
                .collect(Collectors.groupingBy(
                        r -> {
                            Object e = r.getEstado();
                            return (e == null) ? "Sin estado" : e.toString();
                        },
                        Collectors.counting()
                ));
        conteo.forEach(dataset::setValue);
        return dataset;
    }
    
    public TimeSeriesCollection crearDatasetMedicamentosPorMes(
            LocalDate fechaInicio, LocalDate fechaFin, List<String> medicamentosSeleccionados, List<Receta> listaRecetas) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (String nombreMed : medicamentosSeleccionados) {
            TimeSeries serie = new TimeSeries(nombreMed);
            LocalDate fecha = fechaInicio.withDayOfMonth(1);
            while (!fecha.isAfter(fechaFin)) {
                int cantidad = 0;
                for (Receta r : listaRecetas) {
                    LocalDate fechaEmision = r.getFechaEmision();
                    if ((fechaEmision.getYear() == fecha.getYear()) && (fechaEmision.getMonthValue() == fecha.getMonthValue())) {
                        for (Indicaciones i : r.getIndicaciones()) {
                            if (i.getMedicamento().getNombre().equals(nombreMed)) {
                                cantidad += i.getCantidad();
                            }
                        }
                    }
                }
                serie.add(new Month(fecha.getMonthValue(), fecha.getYear()), cantidad);
                fecha = fecha.plusMonths(1);
            }
            dataset.addSeries(serie);
        }
        return dataset;
    }
    
    public JFreeChart crearGraficoLineaMedicamentos(
            LocalDate inicio, LocalDate fin, List<String> seleccionados, List<Receta> listaRecetas) {
        TimeSeriesCollection dataset = crearDatasetMedicamentosPorMes(inicio, fin, seleccionados, listaRecetas);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Medicamentos despachados por mes",
                "Mes",
                "Cantidad",
                dataset,
                true,
                true,
                false
        );
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setDefaultShapesVisible(true);
        renderer.setDefaultShapesFilled(true);
        plot.setRenderer(renderer);
        return chart;
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
