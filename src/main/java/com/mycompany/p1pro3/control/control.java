package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.Administrativo;
import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.GestorMedicos;

import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Persona;
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

    public void abrirVentanaSegunUsuario(TipoUsuario tipo) {
        System.out.println("abrirVentanaSegunUsuario llamado con tipo: " + tipo);
        switch (tipo) {
           /*
           case FARMACEUTA:
               VentanaFarmaceuta ventanaFarmaceuta = new VentanaFarmaceuta(this);
               ventanaFarmaceuta.setVisible(true);
               break;*/
            case MEDICO:
               VentanaMedico ventanaMedico = new VentanaMedico(this);
               ventanaMedico.setVisible(true);
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

    public TipoUsuario validarUsuario(String cedula, String clave) {
        Persona p = modelo.getModelo().getGestorPersonas().login(cedula, clave); // usa tu login centralizado

        if (p == null) {
            return null; // login fallido
        }

        // Determinar tipo de usuario
        if (p instanceof Medico) {
            return TipoUsuario.MEDICO;
        } else if (p instanceof Farmaceuta) {
            return TipoUsuario.FARMACEUTA;
        } else if (p instanceof Administrativo) {
            return TipoUsuario.ADMINISTRATIVO;
        } else {
            return null;
        }
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

    private final modelo modelo;
}
