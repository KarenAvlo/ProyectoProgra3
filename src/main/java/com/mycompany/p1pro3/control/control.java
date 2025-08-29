
package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.modelo.modelo;
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
    
    private final modelo modelo;
}
