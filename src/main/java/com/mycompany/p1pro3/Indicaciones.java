package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;
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
