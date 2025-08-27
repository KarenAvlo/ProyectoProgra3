
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
@ToString
@XmlRootElement(name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)


public class Paciente{

    @XmlElement(name = "telefono")
    private String telefono;
    @XmlElement(name = "fechaNacimiento")
    private String fechaNacimiento;
    @XmlElement(name = "cedula")
    private String cedula;
    @XmlElement(name = "nombre")
    private String nombre;
    
   
    
}




