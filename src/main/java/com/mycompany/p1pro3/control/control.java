
package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.modelo.Modelo;


public class Control {

    public Control(Modelo modelo){
        this.modelo = modelo;
    }
    
    public Control(){
        this(new Modelo());
    }
    
    private final Modelo modelo;
}
