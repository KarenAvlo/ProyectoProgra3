
package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.Administrativo;
import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.GestorMedicos;

import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Persona;
import com.mycompany.p1pro3.modelo.modelo;
import com.mycompany.p1pro3.vista.VentanaAdministrador;
import java.util.List;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter

public class control {

    public control(modelo modelo){
        this.modelo = modelo;
    }
    
    public control(){
        this(new modelo());
    }
    
     public Hospital getHospital(){
        return modelo.obtenerModelo();
    }
    
    public void prueba1(){
        JOptionPane.showMessageDialog(null, "hola!");
    }
    
    public void cerrarAplicacion(){
        System.out.println("Aplicacion finalizada");
    
    }
    
    private GestorMedicos getGestorMedicos() {
        return modelo.obtenerModelo().getGestorMedicos();
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
           /*case MEDICO:
               VentanaMedico ventanaMedico = new VentanaMedico(this);
               ventanaMedico.setVisible(true);
               break;
           case FARMACEUTA:
               VentanaFarmaceuta ventanaFarmaceuta = new VentanaFarmaceuta(this);
               ventanaFarmaceuta.setVisible(true);
               break;*/
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
        Persona p = modelo.obtenerModelo().getGestorPersonas().login(cedula, clave); // usa tu login centralizado

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
    
    
    private final modelo modelo;
}
