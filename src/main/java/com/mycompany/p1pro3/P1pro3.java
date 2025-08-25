package com.mycompany.p1pro3;



public class P1pro3 {

    public static void main(String[] args) {
        GestorFarmaceutas gestor1= new GestorFarmaceutas();
        gestor1.cargarDesdeXML();
        
        gestor1.getListaFarmaceutas().forEach(System.out::println);
        
        
//        GestordePacientes gestor = new GestordePacientes();
//
//
//        // Solo le decimos que cargue los pacientes desde XML
//        gestor.cargarDesdeXML();
//
//        // Mostrar la lista
//        gestor.getListaPacientes().forEach(System.out::println);
//
//        // Buscar un paciente
//        Paciente p = gestor.buscarPorCedula("1234");
//        System.out.println("Paciente encontrado: " + p);
    }

}
