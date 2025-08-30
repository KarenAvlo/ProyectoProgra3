
package com.mycompany.p1pro3.control;

import com.mycompany.p1pro3.Administrativo;
import com.mycompany.p1pro3.Farmaceuta;

import com.mycompany.p1pro3.Hospital;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Persona;
import com.mycompany.p1pro3.modelo.modelo;
import com.mycompany.p1pro3.vista.VentanaAdministrador;
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
    /*
    public TipoUsuario tipoUsuarioDesdeString(String tipo) {
    if(tipo == null) return null;

    switch(tipo.toLowerCase()) {
        case "medico":
            return TipoUsuario.MEDICO;
        case "farmaceuta":
            return TipoUsuario.FARMACEUTA;
        case "administrativo":
            return TipoUsuario.ADMINISTRATIVO;
        default:
            return null;
    }
}
    */
    
    public void abrirVentanaSegunUsuario(TipoUsuario tipo) {
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
        Persona p = modelo.obtenerModelo().getGp().login(cedula, clave); // usa tu login centralizado

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
