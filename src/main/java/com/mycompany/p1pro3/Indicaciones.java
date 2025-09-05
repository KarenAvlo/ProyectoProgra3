package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)  
public class Indicaciones {

    @XmlElement
    private Medicamento medicamento;
    @XmlElement
    private int cantidad;
    @XmlElement
    private String indicaciones;
    @XmlElement
    private int duracion;

    
}
