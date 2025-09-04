package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.Administrativo;
import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.GestorMedicos;

import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medicamento;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Paciente;
import com.mycompany.p1pro3.Persona;
import com.mycompany.p1pro3.Receta;
import static com.mycompany.p1pro3.control.TipoUsuario.ADMINISTRATIVO;
import static com.mycompany.p1pro3.control.TipoUsuario.MEDICO;
import com.mycompany.p1pro3.modelo.modelo;
import com.mycompany.p1pro3.vista.VentanaAdministrador;
import com.mycompany.p1pro3.vista.VentanaMedico;
import java.util.List;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Control {

    public Control(modelo modelo) {
        this.modelo = modelo;
    }

    public Control() {
        this(new modelo());
    }

    public Hospital getHospital() {
        return modelo.getModelo();
    }

    public void prueba1() {
        JOptionPane.showMessageDialog(null, "hola!");
    }

    public void cerrarAplicacion() {
        System.out.println("Aplicacion finalizada");

    }
    
    public void abrirVentanaSegunUsuario(TipoUsuario tipo) {
        System.out.println("abrirVentanaSegunUsuario llamado con tipo: " + tipo);
        switch (tipo) {
            /*
           case FARMACEUTA:
               VentanaFarmaceuta ventanaFarmaceuta = new VentanaFarmaceuta(this);
               ventanaFarmaceuta.setVisible(true);
               break;*/
            case MEDICO:
                break;
            case ADMINISTRATIVO:
                VentanaAdministrador ventanaAdmin = new VentanaAdministrador(this);
                ventanaAdmin.setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Usuario no reconocido");
                break;
        }
    }
    public void abrirVentanaMedico(Medico med) {
        VentanaMedico ventanaMedico = new VentanaMedico(this, med);
        ventanaMedico.setVisible(true);
    }
    
    

    public Persona validarUsuario(String cedula, String clave) {
        Persona p = modelo.getModelo().getGestorPersonas().login(cedula, clave); // usa tu login centralizado
        return p;
    }

    public TipoUsuario tipoDeUsuario(Persona p) {
        if (p instanceof Medico) {
            return TipoUsuario.MEDICO;
        }
        if (p instanceof Farmaceuta) {
            return TipoUsuario.FARMACEUTA;
        }
        if (p instanceof Administrativo) {
            return TipoUsuario.ADMINISTRATIVO;
        }
        return null;
    }
    
    
    //===========Medicos==============
    private GestorMedicos getGestorMedicos() {
        return modelo.getModelo().getGestorMedicos();
    }

    public boolean agregarMedico(String cedula, String nombre, String especialidad) {
        boolean exito = modelo.agregarMedico(cedula, nombre, especialidad);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public boolean eliminarMedico(String cedula) {
        boolean exito = modelo.eliminarMedico(cedula);
        if (exito) {
            // Guardar los cambios después de eliminar
            return modelo.guardarDatos();
        }
        return false;
    }

    public Medico buscarMedico(String cedula) {
        return modelo.buscarMedico(cedula);
    }

    public List<Medico> listarMedicos() {
        return modelo.listarMedicos();
    }



    //=================Farmaceutas=============
    public boolean agregarFarmaceuta(String cedula, String nombre) {
        boolean exito = modelo.agregarFarmaceuta(cedula, nombre);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public boolean EliminarFarmaceuta(String cedula) {
        boolean exito = modelo.EliminarFarmaceuta(cedula);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public List<Farmaceuta> ListarFarmaceutas() {
        return modelo.listarFarmaceutas();
    }
    
    public Farmaceuta buscarFarmaceuta(String cedula) {
        return modelo.buscarFarmaceuta(cedula);
    }
    
    public boolean eliminarFarmaceuta(String cedula) {
        boolean exito = modelo.eliminarFarmaceuta(cedula);
        if (exito) {
            // Guardar los cambios después de eliminar
            return modelo.guardarDatos();
        }
        return false;
    }
    
    //=============Pacientes=============
    public boolean agregarPaciente(String cedula, String nombre,String fecha,String tel) {
        boolean exito = modelo.agregarPaciente(cedula, nombre, fecha, tel);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public boolean EliminarPaciente(String cedula) {
        boolean exito = modelo.EliminarPaciente(cedula);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public List<Paciente> ListarPacientes() {
        return modelo.listarPacientes();
    }
    
    public Paciente buscarPaciente(String cedula) {
        return modelo.buscarPaciente(cedula);
    }
    
    public boolean eliminarPaciente(String cedula) {
        boolean exito = modelo.EliminarPaciente(cedula);
        if (exito) {
            // Guardar los cambios después de eliminar
            return modelo.guardarDatos();
        }
        return false;
    }
    
    //========medicamentos========
    public boolean agregarMedicamento(String codigo, String nombre,String presentacion) {
        boolean exito = modelo.agregarMedicamento(codigo, nombre, presentacion);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public boolean EliminarMedicamento(String codigo) {
        boolean exito = modelo.EliminarMedicamento(codigo);
        if (exito) {
            return modelo.guardarDatos();
        }
        return false;
    }

    public List<Medicamento> ListarMedicamentos() {
        return modelo.listarMedicamentos();
    }
    
    public Medicamento buscarMedicamento(String cod) {
        return modelo.buscarMedicamento(cod);
    }
    
    public boolean eliminarMedicamento(String cod) {
        boolean exito = modelo.EliminarMedicamento(cod);
        if (exito) {
            // Guardar los cambios después de eliminar
            return modelo.guardarDatos();
        }
        return false;
    }
    
    //================= Recetas =============
    
    public boolean agregarReceta(Receta receta){
        return modelo.agregarReceta(receta);
    }
    
    public int cantidadRecetas(){
        return modelo.cantidadRecetas();
    }
    
    
    //============historico===========
    
     public Receta buscarReceta(String cod) {
        return modelo.buscarReceta(cod);
    }
     public List<Receta> ListarRecetas() {
        return modelo.listarRecetas();
    }
    
    

    private final modelo modelo;
}
