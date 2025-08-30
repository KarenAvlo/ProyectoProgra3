package com.mycompany.p1pro3;

import com.mycompany.p1pro3.control.control;
import com.mycompany.p1pro3.vista.VentanaPrincipal;
import com.mycompany.p1pro3.modelo.modelo;
import jakarta.xml.bind.JAXBException;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class P1pro3 {

    public static void main(String[] args) throws IOException, JAXBException, Exception {
        
        try {
            System.setOut(new PrintStream(
                    new FileOutputStream(FileDescriptor.out), true,
                    StandardCharsets.UTF_8.name()));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedEncodingException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        new P1pro3().init();
        System.out.println("Aplicación inicializada..");
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            try {
                mostrarInterfaz();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void mostrarInterfaz() throws Exception {
        System.out.println("Iniciando interfaz..");

        // 1️⃣ Crear modelo
        modelo modelo = new modelo();

        // 2️⃣ Cargar personas desde XML
        modelo.obtenerModelo().getGp().cargarTodo();

        // 3️⃣ Crear controlador con modelo
        control gestorPrincipal = new control(modelo);

        // 4️⃣ Crear ventana principal y pasar controlador
        VentanaPrincipal ventana = new VentanaPrincipal(gestorPrincipal);
        ventana.setVisible(true);
    }
        
}
        /*
        try {
            System.setOut(new PrintStream(
                    new FileOutputStream(FileDescriptor.out), true,
                    StandardCharsets.UTF_8.name()));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedEncodingException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        new P1pro3().init();

        System.out.println("Aplicación inicializada..");
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            mostrarInterfaz();
        });
    }

    private void mostrarInterfaz() {
         System.out.println("Iniciando interfaz..");

        control gestorPrincipal = new control();
        VentanaPrincipal ventana = new VentanaPrincipal(gestorPrincipal);
        ventana.setVisible(true);
    }}
    */


