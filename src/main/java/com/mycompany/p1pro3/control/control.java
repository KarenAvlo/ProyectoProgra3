
package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.modelo.modelo;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter

public class control {

    public control(modelo modelo){
        this.modelo = modelo;
    }
    
    public control(){
        this(new modelo());
    }
    
    public void prueba1(){
        JOptionPane.showMessageDialog(null, "hola!");
    }
    
    public void cerrarAplicacion(){
    System.out.println("Aplicacion finalizada");
    
    }
    
    
    private final modelo modelo;
}
