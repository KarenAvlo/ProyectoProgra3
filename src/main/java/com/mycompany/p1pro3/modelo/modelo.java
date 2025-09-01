package com.mycompany.p1pro3.modelo;
import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medico;
import java.util.List;

public class modelo {
    
    public modelo(){
        hospital = new Hospital();
    }
    public Hospital obtenerModelo(){
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

