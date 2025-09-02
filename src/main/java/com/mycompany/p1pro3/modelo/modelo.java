package com.mycompany.p1pro3.modelo;
import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medico;
import java.util.List;

public class modelo {

    
    public modelo(){
        hospital = new Hospital();
    }
    public Hospital getModelo(){
        return hospital;
    }
    
    public boolean agregarMedico(String cedula, String nombre, String especialidad) {
        return hospital.getGestorMedicos().InclusionMedico(cedula, nombre, especialidad);
    }
    
    public boolean eliminarMedico(String cedula) {
        return hospital.getGestorMedicos().BorrarMedico(cedula);
    }
    
    public Medico buscarMedico(String cedula) {
        return hospital.getGestorMedicos().buscarPorCedula(cedula);
    }
    
    public List<Medico> listarMedicos() {
        return hospital.getGestorMedicos().getListaMedicos();
    }
    
    
    //----Farmaceutas---
    public boolean agregarFarmaceuta(String cedula,String nombre){
    return hospital.getGestorFarmaceutas().InclusionFarmaceuta(cedula, nombre);
    }
    
    public boolean EliminarFarmaceuta(String cedula){
    return hospital.getGestorFarmaceutas().BorrarFarmaceuta(cedula);
    }
    public List<Farmaceuta> listarFarmaceutas() {
        return hospital.getGestorFarmaceutas().getListaFarmaceutas();
    }
    
     public Farmaceuta buscarFarmaceuta(String cedula) {
        return hospital.getGestorFarmaceutas().buscarPorCedula(cedula);
    }
     
    public boolean eliminarFarmaceuta(String cedula) {
        return hospital.getGestorFarmaceutas().BorrarFarmaceuta(cedula);
    }
    
    ///----------Farmaceutas--------
    ///

    
    
    
    public boolean cargarDatos() {
        try {
            hospital.cargarDatos();
            return true;
        } catch (Exception e) {
            System.err.println("Error al cargar datos iniciales: " + e.getMessage());
            return false;
        }
    }
    
    public boolean guardarDatos() {
        return hospital.guardarDatos();
    }
    
    private final Hospital hospital;
}

