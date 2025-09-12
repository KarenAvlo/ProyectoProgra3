package com.mycompany.p1pro3;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
@AllArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@XmlRootElement(name = "administrativo")
@XmlAccessorType(XmlAccessType.FIELD)

public class Administrativo extends Persona {
 
    public Administrativo(String ced, String nombre, String clave) {
        super(ced, nombre,clave);
    }
    //como es administativo, puede administrar las listas de medicos y sus funcionalidades
    // de la misma manera con listas de farmaceutas y pacientes
}
