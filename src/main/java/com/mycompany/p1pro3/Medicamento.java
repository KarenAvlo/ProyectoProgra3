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
