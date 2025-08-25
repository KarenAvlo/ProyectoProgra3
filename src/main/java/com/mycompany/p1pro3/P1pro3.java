package com.mycompany.p1pro3;

public class P1pro3 {

    public static void main(String[] args) {
        GestorMedicamentos g = new GestorMedicamentos();
        g.cargarDesdeXML();

        System.out.println(g.getListaMedicamentos().toString());

    }
}
