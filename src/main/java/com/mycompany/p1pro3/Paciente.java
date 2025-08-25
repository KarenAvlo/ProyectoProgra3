
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
@ToString(callSuper = true)
@XmlRootElement(name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)


public class Paciente extends Persona {

    @XmlElement(name = "telefono")
    private String telefono;

    @XmlElement(name = "fechaNacimiento")
    private String fechaNacimiento;

    public Paciente(String cedula, String nombre, String telefono, String fechaNacimiento) {
        super(cedula, nombre);
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }
}




