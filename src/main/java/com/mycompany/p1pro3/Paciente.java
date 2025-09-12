
package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement(name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)

public class Paciente {

    @XmlElement(name = "telefono")
    private String telefono;
    @XmlElement(name = "fechaNacimiento")
    private String fechaNacimiento;
    @XmlElement(name = "cedula")
    private String cedula;
    @XmlElement(name = "nombre")
    private String nombre;   
}




