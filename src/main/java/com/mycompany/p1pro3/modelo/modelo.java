package com.mycompany.p1pro3.modelo;

import com.mycompany.p1pro3.Hospital;

public class Modelo {
    
    public Modelo(){
        Hospi = new Hospital();
    }
    public Hospital obetenerModelo(){
        return Hospi;
    }
    private final Hospital Hospi;
}
