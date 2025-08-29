package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(callSuper = true, exclude = "clave")
@XmlRootElement(name = "administrativo")
@XmlAccessorType(XmlAccessType.FIELD)

public class Administrativo extends Persona {
 

    public Administrativo(String ced, String nombre, String clave) {
        super(ced, nombre,clave);

    }
   

//como es administativo, puede administrar las listas de medicos y sus funcionalidades
    // de la misma manera conlistas de farmaceutas y pacientes
}
