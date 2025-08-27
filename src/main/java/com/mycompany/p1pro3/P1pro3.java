package com.mycompany.p1pro3;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;

public class P1pro3 {

    public static void main(String[] args) throws IOException, JAXBException, Exception {

        GestorFarmaceutas gf = GestorFarmaceutas.cargarDesdeXML();
        System.out.println("Farmaceutas cargados:");
        System.out.println(gf.getListaFarmaceutas());
//        gf.InclusionFarmaceuta("F007", "Karolina Diaz");
//
//        System.out.println("Farmaceutas Agregada:");
//        System.out.print(gf.toString());

        gf.BorrarFarmaceuta("F001");
        System.out.println("Farmaceut@ Eliminada:");
        System.out.print(gf.toString());

        gf.guardar();
    }
}
