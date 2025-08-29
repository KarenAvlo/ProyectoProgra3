package com.mycompany.p1pro3;

import com.mycompany.p1pro3.control.control;
import com.mycompany.p1pro3.vista.Login;
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
            mostrarInterfaz();
        });
    }

    private void mostrarInterfaz() {
        System.out.println("Iniciando interfaz..");

        control gestorPrincipal = new control();
        Login login = new Login(gestorPrincipal);
        login.init();
    }
}
