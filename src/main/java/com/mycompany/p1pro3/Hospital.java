package com.mycompany.p1pro3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Hospital {
    
    public void agregarPaciente(Paciente p){
        pacientes.addLast(p);
    }
   
    public boolean borrarMedico(String id) {
        Iterator<Medico> it = medicos.iterator();
        while (it.hasNext()) {
            Medico m = it.next();
            if (m.getCedula().equals(id)) {
                it.remove();
                return true; // eliminado exitosamente
            }
        }
        return false; // no encontrado
    }
    
    public Medico consultarMedico(String ced){
        for (Medico m : medicos) {
            if (m.getCedula().equals(ced)) {
                return m; // Encontrado
            }
        }
        return null; // No encontrado
    }
    
    public void agregarMedico(String id, String nombre, String especialidad) {
        Medico m = new Medico();
        m.setCedula(id);
        m.setNombre(nombre);
        m.setEspecialidad(especialidad);
        m.setClave(id); // la clave inicial = id
        medicos.addLast(m);
    }
    private Farmacia farmacia = new Farmacia();
    private List<Paciente> pacientes = new ArrayList<>();
    private final List<Medico> medicos = new ArrayList<>();
}

