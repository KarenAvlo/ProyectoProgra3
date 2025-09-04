package com.mycompany.p1pro3.modelo;

import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medicamento;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Paciente;
import com.mycompany.p1pro3.Receta;
import java.time.LocalDate;
import java.util.List;
import org.jfree.chart.JFreeChart;

public class modelo {

    public modelo() {
        hospital = new Hospital();
    }

    public Hospital getModelo() {
        return hospital;
    }

    //========MEDICOS==========
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
    public boolean agregarFarmaceuta(String cedula, String nombre) {
        return hospital.getGestorFarmaceutas().InclusionFarmaceuta(cedula, nombre);
    }

    public boolean EliminarFarmaceuta(String cedula) {
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

    //=============Pacientes==============
    public boolean agregarPaciente(String cedula, String nombre, String fecha, String telefono) {
        return hospital.getGestorP().InclusionPaciente(cedula, nombre, fecha, telefono);
    }

    public boolean EliminarPaciente(String cedula) {
        return hospital.getGestorP().BorrarPaciente(cedula);
    }

    public List<Paciente> listarPacientes() {
        return hospital.getGestorP().getListaPacientes();
    }

    public Paciente buscarPaciente(String cedula) {
        return hospital.getGestorP().buscarPorCedula(cedula);
    }

    public boolean eliminarPaciente(String cedula) {
        return hospital.getGestorP().BorrarPaciente(cedula);
    }

    //==============Medicamentos===============
    public boolean agregarMedicamento(String codigo, String nombre, String presentacion) {
        return hospital.getFarma().getGestorMedicamentos().InclusionMedicamento(codigo, nombre, presentacion);
    }

    public boolean EliminarMedicamento(String codigo) {
        return hospital.getFarma().getGestorMedicamentos().BorrarMedicamento(codigo);
    }

    public List<Medicamento> listarMedicamentos() {
        return hospital.getFarma().getGestorMedicamentos().getListaMedicamentos();
    }

    public Medicamento buscarMedicamento(String codigo) {
        return hospital.getFarma().getGestorMedicamentos().buscarPorCodigo(codigo);
    }

    public boolean eliminarMedicamento(String codigo) {
        return hospital.getFarma().getGestorMedicamentos().BorrarMedicamento(codigo);
    }

    //===========historicoRecetas=========
    public Receta buscarReceta(String codigo) {
        return hospital.getFarma().getGestorRecetas().buscarPorCodigo(codigo);
    }
    
    public List<Receta> listarRecetas() {
        return hospital.getFarma().getGestorRecetas().getListaRecetas();
    }
    
    //=============== Recetas =============
    public boolean agregarReceta(Receta receta){
        return hospital.getFarma().getGestorRecetas().agregarReceta(receta);
    }
    
    public int cantidadRecetas(){
        return hospital.getFarma().getGestorRecetas().cantidadRecetas();
    }
    
    public JFreeChart crearGraficoPastelRecetasPorEstado(LocalDate fechaInicio, LocalDate fechaFin){
        return hospital.getFarma().getGestorRecetas().crearGraficoPastelRecetasPorEstado(fechaInicio, fechaFin);
    }
    
    //============Otro===============
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
