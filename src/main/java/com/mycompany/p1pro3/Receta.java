package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@XmlRootElement(name = "receta")
@XmlAccessorType(XmlAccessType.FIELD)

public class Receta {
    @XmlElement
    private String codReceta;
    @XmlElement
    private Paciente paciente;
    @XmlElement
    private Medico medico;
    @XmlElement(name = "indicaciones")
    private List<Indicaciones> indicaciones; // medicamento,dia,indicacion,duracion
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaEmision; // asigna la fecha de hoy, ( si se confecciona hoy)
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaRetiro; // asi podemos verificar la fecha mas easy
    @XmlElement
    private String estado; //Confeccionada
    
    
  
    public void ModificarIndicaciones(String codigoMedicamento, String nuevomed, int cantidad,
            String indicaciones, int duracionDias, List<Medicamento> medicamentosdisp) {

        Medicamento nuevoMedicamento = null;
        for (Medicamento m : medicamentosdisp) {
            if (m.getCodigo().equals(nuevomed)) {
                nuevoMedicamento = m;
            }
        }
        
        for (Indicaciones i : this.indicaciones) {
            
            if (i.getMedicamento().getCodigo().equals(codigoMedicamento)) { // buscamos el medicamento por codigo el que deseamos cambiar
                i.setMedicamento(nuevoMedicamento);
                i.setCantidad(cantidad);
                i.setIndicaciones(indicaciones);
                i.setDuracion(duracionDias);
            }
        }

    }

    public void finalizarReceta() {
        this.fechaEmision= LocalDate.now();
        this.fechaRetiro = fechaEmision.plusDays(3); //tres dias despues de emitida
        this.estado = "CONFECCIONADA"; 
    }
    
    public void agregarMedicamento(Medicamento medicamento, int cantidad, String indicacionesTexto, int duracionDias) {
        Indicaciones nuevaIndicacion = new Indicaciones(medicamento, cantidad, indicacionesTexto, duracionDias);
        this.indicaciones.add(nuevaIndicacion);
    }
    
    
}

