package com.mycompany.p1pro3;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;

public class P1pro3 {

    public static void main(String[] args) throws IOException, JAXBException, Exception {

        GestorRecetas gf = GestorRecetas.cargarDesdeXML();
        System.out.println("Recetos cargados:");
   System.out.print(gf.toString());
//        gf.InclusionFarmaceuta("F007", "Karolina Diaz");
//
//        System.out.println("Farmaceutas Agregada:");
//        System.out.print(gf.toString());

//        gf.BorrarFarmaceuta("F001");
//        System.out.println("Farmaceut@ Eliminada:");
//        System.out.print(gf.toString());
//
//        gf.guardar();
    }
}
