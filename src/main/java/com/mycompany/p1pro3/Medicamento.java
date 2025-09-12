package com.mycompany.p1pro3;

import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
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
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@XmlRootElement(name = "medicamento") 
@XmlAccessorType(XmlAccessType.FIELD)

public class Medicamento {
    @XmlElement(name = "codigo")
    private String codigo;
    
    @XmlElement(name = "nombre")
    private String nombre;
    
    @XmlElement(name = "presentacion")
    private String presentacion;
}
