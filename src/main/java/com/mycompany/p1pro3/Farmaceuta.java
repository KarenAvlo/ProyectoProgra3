package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = "clave")
@XmlRootElement(name = "farmaceuta")
@XmlAccessorType(XmlAccessType.FIELD)

public class Farmaceuta extends Persona {

    @XmlElement(name = "clave")
    private String clave;

    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre);
        this.clave = clave;
    }

}
