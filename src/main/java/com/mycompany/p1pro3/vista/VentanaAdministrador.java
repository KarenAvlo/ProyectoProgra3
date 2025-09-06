package com.mycompany.p1pro3.vista;

import com.mycompany.p1pro3.Administrativo;
import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.Indicaciones;
import com.mycompany.p1pro3.Medicamento;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Paciente;
import com.mycompany.p1pro3.Persona;
import com.mycompany.p1pro3.Receta;
import com.mycompany.p1pro3.control.Control;
import com.mycompany.p1pro3.modelo.modelo;
import cr.ac.una.gui.FormHandler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Nicolas ZH
 */
public class VentanaAdministrador extends javax.swing.JFrame {

    public VentanaAdministrador(Control controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controlador = controlador;
        this.estado = new FormHandler();
        initComponents();
        configurarListeners();
        init();
    }

    private void configurarListeners() {
        TablaMedicos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
                    cargarMedicoDesdeTabla();
                }
            }
        });
        //=========Listeners para farmaceutas==========

        TablaFarmaceutas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
                    cargarFarmaceutaDesdeTabla();
                }
            }
        });
        //=========Listeners para Lista de Pacientes==========
        TablaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
                    cargarPacientesDesdeTabla();
                }
            }
        });
        //=========Listeners para Lista de Medicamentos==========
        TablaMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
                    cargarMedicamentoDesdeTabla();
                }
            }
        });
        //=========Listeners para Lista de Recetas==========
        TablaRecetas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
                  cargarRecetaDesdeTabla();
                }
            }
        });
    }

    public void init() {
        // ====== DocumentListener para campos de edición ======
        DocumentListener listenerEdicion = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                indicarCambios();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                indicarCambios();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                indicarCambios();
            }
        };

        // ====== DocumentListener para campos de búsqueda ======
        DocumentListener listenerBusqueda = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarControles();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarControles();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarControles();
            }
        };

        // ====== Campos Médicos ======
        campoId.getDocument().addDocumentListener(listenerEdicion);
        campoId1.getDocument().addDocumentListener(listenerEdicion);
        campoId2.getDocument().addDocumentListener(listenerEdicion);

        cedulatxt1.getDocument().addDocumentListener(listenerBusqueda);
        ResultadoMtxt.getDocument().addDocumentListener(listenerBusqueda);

        // ====== Campos Farmacéutas ======
        CedulaFtxt.getDocument().addDocumentListener(listenerEdicion);
        NombreFtxt.getDocument().addDocumentListener(listenerEdicion);

        CedulaFtxt2.getDocument().addDocumentListener(listenerBusqueda);
        ResultadoFtxt.getDocument().addDocumentListener(listenerBusqueda);

        // ====== Campos Pacientes ======
        CedulaPtxt.getDocument().addDocumentListener(listenerEdicion);
        NombrePtxt.getDocument().addDocumentListener(listenerEdicion);
        FechaNacPtxt.getDocument().addDocumentListener(listenerEdicion);
        TelefonoPtxt.getDocument().addDocumentListener(listenerEdicion);

        CedulaPtxt2.getDocument().addDocumentListener(listenerBusqueda);
        ResultadoPtxt.getDocument().addDocumentListener(listenerBusqueda);

        // ====== Campos Medicamentos ======
        // ✅ Campos de edición activan botones de edición
        CodigoMtxt.getDocument().addDocumentListener(listenerEdicion);
        NombreMedicamentotxt.getDocument().addDocumentListener(listenerEdicion);
        PresentacionMedicamentotxt.getDocument().addDocumentListener(listenerEdicion);

        // ✅ Campos de búsqueda solo activan botones de búsqueda
        CodigoMtxt2.getDocument().addDocumentListener(listenerBusqueda);
        ResultadoMedicamentotxt.getDocument().addDocumentListener(listenerBusqueda);

        // ====== Campos Recetas ======
        CodigoRecetastxt.getDocument().addDocumentListener(listenerBusqueda);
//        ResultadoRecetastxt.getDocument().addDocumentListener(listenerBusqueda);

        // ====== Actualizar tablas al cambiar pestaña ======
        jTabbedPane1.addChangeListener(e -> {
            int index = jTabbedPane1.getSelectedIndex();
            actualizarControles(); // Mantener botones sincronizados
            actualizarCampos();
            switch (index) {
                case 0:
                    actualizarTablaMedicos();
                    break;
                case 1:
                    actualizarTablaFarmaceutas();
                    break;
                case 2:
                    actualizarTablaPacientes();
                    break;
                case 3:
                    actualizarTablaMedicamentos();
                    estado.changeToAddMode();
                    break;
                case 5:
                     actualizarTablaRecetas();
                    break;
            }
            actualizarControles(); // Mantener botones sincronizados
        });

        // ====== Inicializar tablas ======
        actualizarTablaMedicos();
        actualizarTablaFarmaceutas();
        actualizarTablaPacientes();
        actualizarTablaMedicamentos();
        actualizarTablaRecetas();

        // ====== Cambiar a modo agregar al abrir ======
        cambiarModoAgregar();
        actualizarComponentes();
        configurarSpinnersDashboard();
        cargarMedicamentosComboBox();
        // ====== Mostrar ventana ======
        setVisible(true);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE MODOS
    // -------------------------------------------------------------------------
    private void cambiarModoVista() {
        estado.changeToViewMode();
        actualizarComponentes();
        estado.setModified(false);
    }

    private void cambiarModoAgregar() {
        estado.changeToAddMode();
        actualizarCampos();
        actualizarComponentes();

        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();
        switch (pestanaSeleccionada) {
            case 0: // Médicos
                campoId.requestFocusInWindow();
                campoId.selectAll();
                break;
            case 1: // Farmacéutas
                CedulaFtxt.requestFocusInWindow();
                CedulaFtxt.selectAll();
                break;
            case 3: // Medicamentos
                CodigoMtxt.requestFocusInWindow();
                CodigoMtxt.selectAll();
                break;
        }
    }

    private void cambiarModoEditar() { //modo edicion
        if (estado.isViewing() && estado.getModel() != null) {
            estado.changeToEditMode();
            actualizarComponentes();

            int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();
            if (pestanaSeleccionada == 0) { // Médicos
                campoId1.requestFocusInWindow();
                campoId1.selectAll();
            } else if (pestanaSeleccionada == 1) { // Farmaceutas
                NombreFtxt.requestFocusInWindow();
                NombreFtxt.selectAll();
            }
        }
    }

    private void cambiarModoBuscar() {
        estado.changeToSearchMode();
        actualizarComponentes();

        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();
        switch (pestanaSeleccionada) {
            case 0:
                ResultadoMtxt.requestFocusInWindow();
                ResultadoMtxt.selectAll();
                break;
            case 1:
                CedulaFtxt2.requestFocusInWindow();
                CedulaFtxt2.selectAll();
                break;
            case 2:
                CedulaPtxt2.requestFocusInWindow();
                CedulaPtxt2.selectAll();
                break;
            case 3:
                CodigoMtxt2.requestFocusInWindow();
                CodigoMtxt2.selectAll();
                break;
            case 5:
                CodigoRecetastxt.requestFocusInWindow();
                CodigoRecetastxt.selectAll();
                break;
        }
    }

    // -------------------------------------------------------------------------
    // ACTUALIZACIÓN DE COMPONENTES
    // -------------------------------------------------------------------------
    private void actualizarComponentes() {
        actualizarControles();
        actualizarCampos();
    }

    private void actualizarControles() {
        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();

        switch (pestanaSeleccionada) {
            case 0: // Médicos
                boolean NohaytextoMedicoid = campoId.getText().trim().isEmpty();
                boolean NohaytextoMedicoid1 = campoId1.getText().trim().isEmpty();
                boolean NohaytextoMedicoid2 = campoId2.getText().trim().isEmpty();
                boolean NohaytextoMedico2 = cedulatxt1.getText().trim().isEmpty();

                BotonGuardarMedico.setEnabled(!estado.isViewing() && estado.isModified() || estado.isViewing());
                BotonLimpiarMedico.setEnabled(!NohaytextoMedicoid || !NohaytextoMedicoid1 || !NohaytextoMedicoid2);
                BotonEliminarMedico.setEnabled(!NohaytextoMedicoid);
                BotonBuscarMedico.setEnabled(!NohaytextoMedico2);
                BotonReporteMedico.setEnabled(estado.isViewing() && estado.getModel() != null);

                BotonLimpiarMedico.setText((!NohaytextoMedicoid || !NohaytextoMedicoid1 || !NohaytextoMedicoid2) ? "Limpiar" : "Cancelar");
                break;

            case 1: // Farmacéutas
                boolean noHayTextoFarma = CedulaFtxt.getText().trim().isEmpty();
                boolean noHayTextoFarma2 = NombreFtxt.getText().trim().isEmpty();
                boolean noHayTextoFarma3 = CedulaFtxt2.getText().trim().isEmpty();

                // Botones habilitados según estado.isModified y contenido de los campos
                BotonGuardarF1.setEnabled(!estado.isViewing() && estado.isModified() || estado.isViewing());
                BotonLimpiarF.setEnabled(!noHayTextoFarma || !noHayTextoFarma2);
                BotonEliminarF.setEnabled(!noHayTextoFarma);
                BotonBuscarF.setEnabled(!noHayTextoFarma3);

                // Texto del botón Limpiar/Cambiar a Cancelar
                BotonLimpiarF.setText((!noHayTextoFarma || !noHayTextoFarma2) ? "Limpiar" : "Cancelar");
                break;

            case 2: // Pacientes
                boolean NohayTextoPac = CedulaPtxt.getText().trim().isEmpty();
                boolean NohaytextoPac2 = NombrePtxt.getText().trim().isEmpty();
                boolean NohayTextoPac3 = FechaNacPtxt.getText().trim().isEmpty();
                boolean NohaytextoPac4 = TelefonoPtxt.getText().trim().isEmpty();
                boolean NohaytextoPac5 = CedulaPtxt2.getText().trim().isEmpty();

                BotonBuscarP.setEnabled(!NohaytextoPac5);
                BotonEliminarP.setEnabled(!NohayTextoPac);
                BotonGuardarP.setEnabled(!estado.isViewing() && estado.isModified() || estado.isViewing());
                BotonLimpiarP.setEnabled(!NohayTextoPac || !NohaytextoPac2
                        || !NohayTextoPac3 || !NohaytextoPac4);

                break;

            case 3: // Medicamentos
                boolean noHayTextoMedic = CodigoMtxt.getText().trim().isEmpty();
                boolean noHayTextoMedic2 = NombreMedicamentotxt.getText().trim().isEmpty();
                boolean noHayTextoMedic3 = PresentacionMedicamentotxt.getText().trim().isEmpty();
                boolean noHayTextoMedicBusqueda = CodigoMtxt2.getText().trim().isEmpty();

                BotonGuardarMedicamento.setEnabled(estado.isAdding() || estado.isEditing());
                BotonLimpiarMedicamento.setEnabled(!noHayTextoMedic || !noHayTextoMedic2 || !noHayTextoMedic3);
                BotonEliminarMedicamento.setEnabled(!noHayTextoMedic);
                BotonBuscarMedicamento.setEnabled(!noHayTextoMedicBusqueda);

                BotonLimpiarMedicamento.setText((!noHayTextoMedic || !noHayTextoMedic2 || !noHayTextoMedic3) ? "Limpiar" : "Cancelar");
                break;
            case 5: // Recetas
                boolean NohaytextoRec = CodigoRecetastxt.getText().trim().isEmpty();
                BotonBuscarReceta.setEnabled(!NohaytextoRec);
                break;
        }
    }

    private void actualizarCampos() {
        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();

        // Declarar todas las variables de modoEdicion antes del switch
        boolean modoEdicionMed = !estado.isViewing();
        boolean modoEdicionFarma = !estado.isViewing();
        boolean modoEdicionPac = !estado.isViewing();
        boolean modoEdicionMedic = !estado.isViewing();

        switch (pestanaSeleccionada) {
            case 0: // Médicos
                campoId.setEnabled(estado.getModel() == null || modoEdicionMed || estado.isModified());
                campoId1.setEnabled(estado.getModel() == null || modoEdicionMed || estado.isModified());
                campoId2.setEnabled(estado.getModel() == null || modoEdicionMed || estado.isModified());
                cedulatxt1.setEnabled(true);
                ResultadoMtxt.setEnabled(false);
                break;

            case 1: // Farmaceutas
                CedulaFtxt.setEnabled(estado.getModel() == null || modoEdicionFarma || estado.isModified());
                NombreFtxt.setEnabled(estado.getModel() == null || modoEdicionFarma || estado.isModified());
                CedulaFtxt2.setEnabled(true);
                ResultadoFtxt.setEnabled(false);
//            BotonBuscarF.setEnabled(!CedulaFtxt2.getText().trim().isEmpty());
                break;

            case 2: // Pacientes
                CedulaPtxt.setEnabled(estado.getModel() == null || modoEdicionPac || estado.isModified());
                NombrePtxt.setEnabled(estado.getModel() == null || modoEdicionPac || estado.isModified());
                FechaNacPtxt.setEnabled(estado.getModel() == null || modoEdicionPac || estado.isModified());
                TelefonoPtxt.setEnabled(estado.getModel() == null || modoEdicionPac || estado.isModified());
                CedulaPtxt2.setEnabled(true);
                ResultadoPtxt.setEnabled(false);
//            BotonBuscarP.setEnabled(!CedulaPtxt2.getText().trim().isEmpty());
                break;

            case 3: // Medicamentos             

                CodigoMtxt.setEnabled(estado.getModel() == null || !estado.isViewing() || estado.isModified());
                NombreMedicamentotxt.setEnabled(estado.getModel() == null || !estado.isViewing() || estado.isModified());
                PresentacionMedicamentotxt.setEnabled(estado.getModel() == null || !estado.isViewing() || estado.isModified());
                CodigoMtxt2.setEnabled(true);           // siempre editable para búsqueda
                ResultadoMedicamentotxt.setEnabled(false);
                break;
            case 5: // Recetas falta
                CodigoRecetastxt.setEnabled(true);
//                ResultadoRecetastxt.setEnabled(estado.isAdding() || estado.isSearching() || !estado.isViewing());
                BotonBuscarReceta.setEnabled(!CodigoRecetastxt.getText().trim().isEmpty());
                break;
        }
    }

    private void indicarCambios() {
        estado.setModified(true);
        actualizarControles();
    }

    private void limpiarCampos() {
        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();
        switch (pestanaSeleccionada) {
            case 0:
                estado.setModel(null);
                campoId.setText("");
                campoId1.setText("");
                campoId2.setText("");
                ResultadoMtxt.setText("");
                cambiarModoAgregar();
                break;
            case 1: // Farmacéutas
                estado.setModel(null);
                estado.setModified(false); // ❌ Reinicia el estado para que botones no se habiliten solos
                CedulaFtxt.setText("");
                NombreFtxt.setText("");
                CedulaFtxt2.setText("");
                cambiarModoAgregar();
                actualizarComponentes(); // ✅ Fuerza actualización de botones
                break;

            case 2:
                //limpia pacientes
                estado.setModel(null);
                CedulaPtxt.setText("");
                NombrePtxt.setText("");
                FechaNacPtxt.setText("");
                TelefonoPtxt.setText("");
                CedulaPtxt2.setText("");
                cambiarModoAgregar();
                break;
            case 3:
                //limpia medicamentos
                estado.setModel(null);
                CodigoMtxt.setText("");
                NombreMedicamentotxt.setText("");
                PresentacionMedicamentotxt.setText("");
                CodigoMtxt2.setText("");
                cambiarModoAgregar();
                actualizarCampos();
                break;

        }
    }
    // -------------------------------------------------------------------------
    // OPERACIONES CRUD
    // -------------------------------------------------------------------------
    //----------------Medicos----------------

    private void generarReporte() {
        JOptionPane.showMessageDialog(this, "Función de reporte no implementada aún", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void guardarMedico() {
        try {
            String cedula = campoId.getText().trim();
            String nombre = campoId1.getText().trim();
            String especialidad = campoId2.getText().trim();

            if (cedula.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean exito;
            if (estado.isAdding()) {
                exito = controlador.agregarMedico(cedula, nombre, especialidad);
            } else if (estado.isEditing()) {
                // Para edición, primero eliminamos y luego agregamos (o implementas actualización)
                controlador.eliminarMedico(((Medico) estado.getModel()).getCedula());
                exito = controlador.agregarMedico(cedula, nombre, especialidad);
            } else {
                exito = false;
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Médico guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
//                cambiarModoVista();
//                estado.setModified(false); // Reinicia el estado a no modificado
                estado.setModel(null);     // Establece el modelo como nulo para indicar un nuevo registro
                estado.changeToAddMode();
                limpiarCampos();
                actualizarComponentes();
                actualizarTablaMedicos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el médico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error al guardar médico", ex);
        }
    }

    private void buscarMedico() {
        if (!estado.isSearching()) {
            // Ejecutar búsqueda
            String cedula = cedulatxt1.getText().trim();
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cédula para buscar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico medico = controlador.buscarMedico(cedula);
            if (medico != null) {
                ResultadoMtxt.setText(medico.getNombre());
                estado.setModel(null);
                cambiarModoVista();
                actualizarComponentes();
                JOptionPane.showMessageDialog(this, "Médico encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el médico con esa cédula", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Entrar en modo búsqueda
            cambiarModoBuscar();
        }
    }

    private void eliminarMedico() {
        String cedula = campoId.getText().trim();

        Medico medico = controlador.buscarMedico(cedula);
        if (medico == null) {
            JOptionPane.showMessageDialog(this, "No hay médico seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al médico " + medico.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controlador.eliminarMedico(medico.getCedula());
            if (exito) {
                JOptionPane.showMessageDialog(this, "Médico eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                estado.setModel(null);
                cambiarModoVista();
                actualizarControles();
                actualizarTablaMedicos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el médico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelarOperacion() {
        cambiarModoVista();
    }

    private void actualizarTablaMedicos() {
        try {
            List<Medico> medicos = controlador.listarMedicos();
            DefaultTableModel modelo = (DefaultTableModel) TablaMedicos.getModel();
            modelo.setRowCount(0);

            if (medicos != null) {
                for (Medico m : medicos) {
                    modelo.addRow(new Object[]{
                        m.getCedula(),
                        m.getNombre(),
                        m.getEspecialidad()
                    });
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error al actualizar tabla de médicos", ex);
            JOptionPane.showMessageDialog(this, "Error al cargar los médicos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMedicoDesdeTabla() {
        int selectedRow = TablaMedicos.getSelectedRow();
        if (selectedRow >= 0 && estado.isViewing()) {
            String cedula = TablaMedicos.getValueAt(selectedRow, 0).toString();
            Medico medico = controlador.buscarMedico(cedula);
            if (medico != null) {
                estado.setModel(medico);
                actualizarComponentes();
            }
        }
    }

    //================Fin Medicos===============
    ////--------------Farmaceutas----------------

private void guardarFarmaceuta() {
        try {
            String cedula = CedulaFtxt.getText().trim();
            String nombre = NombreFtxt.getText().trim();

            if (cedula.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y cédula son obligatorios");
                return;
            }

            boolean exito;
            if (estado.isAdding()) {
                exito = controlador.agregarFarmaceuta(cedula, nombre);
            } else if (estado.isEditing()) {
                controlador.eliminarFarmaceuta(((Farmaceuta) estado.getModel()).getCedula());
                exito = controlador.agregarFarmaceuta(cedula, nombre);
            } else {
                exito = false;
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Farmaceuta guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                estado.setModel(null);
                estado.changeToAddMode();
                limpiarCampos();
                actualizarComponentes();
                actualizarTablaFarmaceutas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el farmaceuta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaFarmaceutas() {
        try {
            List<Farmaceuta> farmaceutas = controlador.ListarFarmaceutas();
            DefaultTableModel modelo = (DefaultTableModel) TablaFarmaceutas.getModel();
            modelo.setRowCount(0);

            if (farmaceutas != null) {
                for (Farmaceuta f : farmaceutas) {
                    modelo.addRow(new Object[]{
                        f.getCedula(),
                        f.getNombre()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los farmaceutas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarFarmaceutaDesdeTabla() {
        int selectedRow = TablaFarmaceutas.getSelectedRow();
        if (selectedRow >= 0) {
            String cedula = TablaFarmaceutas.getValueAt(selectedRow, 0).toString();
            Farmaceuta farmaceuta = controlador.buscarFarmaceuta(cedula);
            if (farmaceuta != null) {
                estado.setModel(farmaceuta);
                actualizarComponentes();
            }
        }
    }

    private void eliminarFarmaceuta() {
        String cedula = CedulaFtxt.getText().trim();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Farmaceuta farma = controlador.buscarFarmaceuta(cedula);
        if (farma == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el farmaceuta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al farmaceuta con cédula " + cedula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controlador.eliminarFarmaceuta(cedula);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Farmaceuta eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                estado.setModel(null);
                estado.changeToAddMode();
                limpiarCampos();
                actualizarComponentes();
                actualizarTablaFarmaceutas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el farmaceuta. No se encontró la cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void buscarFarmaceuta() {
        if (!estado.isSearching()) {
            String cedula = CedulaFtxt2.getText().trim();

            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cédula para buscar");
                return;
            }

            Farmaceuta farma = controlador.buscarFarmaceuta(cedula);
            if (farma != null) {
                ResultadoFtxt.setText(farma.getNombre());
                estado.setModel(farma);
                cambiarModoVista();
                actualizarComponentes();
                JOptionPane.showMessageDialog(this, "Farmaceuta encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el farmaceuta con esa cédula", "Error", JOptionPane.ERROR_MESSAGE);
                ResultadoFtxt.setText("No se encontró.");
            }
        } else {
            cambiarModoBuscar();
        }
    }

    //=========Pacientes=========
    private void guardarPaciente() {
        try {
            String cedula = CedulaPtxt.getText().trim();
            String nombre = NombrePtxt.getText().trim();
            String numeroTel = TelefonoPtxt.getText().trim();
            String fechaNac = FechaNacPtxt.getText().trim();

            if (cedula.isEmpty() || nombre.isEmpty()
                    || numeroTel.isEmpty() || fechaNac.isEmpty()) { //verificacion que no sean vacios
                JOptionPane.showMessageDialog(this, "Todos los datos son obligatorios");
                return;
            }

            boolean exito;
            if (estado.isAdding()) { //si esta añadiendo en el field,entonces añadalo
                exito = controlador.agregarPaciente(cedula, nombre, fechaNac, numeroTel);
            } else if (estado.isEditing()) { // si está editando los espacios
                //eliminamos lo que está en proceso
                controlador.EliminarPaciente(((Paciente) estado.getModel()).getCedula());
                //luego añadimos
                exito = controlador.agregarPaciente(cedula, nombre, fechaNac, numeroTel);
            } else {
                exito = false;
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Paciente guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cambiarModoVista();
                estado.setModel(null);     // Establece el modelo como nulo para indicar un nuevo registro
                estado.changeToAddMode();
                limpiarCampos();
                actualizarComponentes();
                actualizarTablaPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar al Paciente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void actualizarTablaPacientes() {
        try {
            List<Paciente> Pacientes = controlador.ListarPacientes();
            DefaultTableModel modelo = (DefaultTableModel) TablaPacientes.getModel();
            modelo.setRowCount(0);

            if (Pacientes != null) {
                for (Paciente P : Pacientes) {
                    modelo.addRow(new Object[]{
                        P.getCedula(),
                        P.getNombre(),
                        P.getFechaNacimiento(),
                        P.getTelefono()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los Pacientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPacientesDesdeTabla() {
        int selectedRow = TablaPacientes.getSelectedRow();
        if (selectedRow >= 0) {
            String cedula = TablaPacientes.getValueAt(selectedRow, 0).toString();
            Paciente Pacx = controlador.buscarPaciente(cedula);
            if (Pacx != null) {
                estado.setModel(Pacx);
                actualizarComponentes();

            }
        }
    }

    private void EliminarPaciente() {
        String cedula = CedulaPtxt.getText().trim();

        Paciente Pacx = controlador.buscarPaciente(cedula);
        if (Pacx == null) {
            JOptionPane.showMessageDialog(this, "No hay Paciente seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese una cedula a eliminar", "Error", JOptionPane.OK_OPTION);
            return;

        }
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de eliminar al paciente con cédula " + cedula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // 4. Llamar al método del controlador para eliminar el farmaceuta
            boolean exito = controlador.EliminarPaciente(cedula);
            if (exito) {
                JOptionPane.showMessageDialog(null, "Paciente eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Opcional: limpiar los campos y actualizar la tabla
                cambiarModoVista();
                actualizarComponentes();
                limpiarCampos();
                actualizarTablaPacientes();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el Paciente. No se encontró la cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void buscarPaciente() {

        if (!estado.isSearching()) {
            String Cedula = CedulaPtxt2.getText().trim();

            if (Cedula.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese una cedula para buscar");
                return;
            }

            Paciente Pacx = controlador.buscarPaciente(Cedula);
            if (Pacx != null) {
                ResultadoPtxt.setText(Pacx.getNombre());
                estado.setModel(null);
                cambiarModoVista();
                actualizarComponentes();
                JOptionPane.showMessageDialog(null, "Paciente encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Si el farmaceuta no se encuentra
                JOptionPane.showMessageDialog(null, "No se encontró el Paciente con esa cédula", "Error", JOptionPane.ERROR_MESSAGE);
                // 🟢 Limpiar el campo de resultado
                ResultadoPtxt.setText("No se encontró.");
            }
        } else {
            // Si el modo no es búsqueda, cambiarlo a búsqueda
            cambiarModoBuscar();
        }

    }
    //==============Medicamentos================

    private void guardarMedicamento() {
        try {
            String codigo = CodigoMtxt.getText().trim();
            String nombre = NombreMedicamentotxt.getText().trim();
            String presentacion = PresentacionMedicamentotxt.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || presentacion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los datos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean exito;
            if (estado.isAdding()) {
                exito = controlador.agregarMedicamento(codigo, nombre, presentacion);
            } else if (estado.isEditing()) {
                controlador.EliminarMedicamento(((Medicamento) estado.getModel()).getCodigo());
                exito = controlador.agregarMedicamento(codigo, nombre, presentacion);
            } else {
                exito = false;
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Medicamento guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                estado.setModel(null);
                estado.changeToAddMode();       // ✅ Cambiar a modo agregar para habilitar campos
                limpiarCampos();
                actualizarComponentes();
                actualizarTablaMedicamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar medicamento", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void EliminarMedicamento() {
        String codigo = CodigoMtxt.getText().trim(); // usar campo de búsqueda para eliminar

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Medicamento med = controlador.buscarMedicamento(codigo);
        if (med == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el medicamento", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el medicamento con código " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = controlador.EliminarMedicamento(codigo);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Medicamento eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                estado.setModel(null);
                estado.changeToAddMode();       // ✅ para habilitar campos nuevamente
                limpiarCampos();
                actualizarComponentes();
                actualizarTablaMedicamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar medicamento", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarMedicamento() {
        if (!estado.isSearching()) {
            String codigo = CodigoMtxt2.getText().trim();
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un código para buscar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medicamento med = controlador.buscarMedicamento(codigo);
            if (med != null) {
                ResultadoMedicamentotxt.setText(med.getNombre());
//                estado.setModel(med);
                cambiarModoVista();           // mostrar datos en modo vista
                actualizarComponentes();
            } else {
                ResultadoMedicamentotxt.setText("No se encontró.");
                JOptionPane.showMessageDialog(this, "No se encontró el medicamento", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            cambiarModoBuscar();
        }
    }

    private void actualizarTablaMedicamentos() {
        try {
            List<Medicamento> lista = controlador.ListarMedicamentos();
            DefaultTableModel modelo = (DefaultTableModel) TablaMedicamentos.getModel();
            modelo.setRowCount(0);
            if (lista != null) {
                for (Medicamento m : lista) {
                    modelo.addRow(new Object[]{m.getCodigo(), m.getNombre(), m.getPresentacion()});
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los medicamentos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMedicamentoDesdeTabla() {
        int fila = TablaMedicamentos.getSelectedRow();
        if (fila >= 0) {
            String codigo = TablaMedicamentos.getValueAt(fila, 0).toString();
            Medicamento med = controlador.buscarMedicamento(codigo);
            if (med != null) {
                estado.setModel(med);
                cambiarModoVista();
                actualizarComponentes();
            }
        }
    }

//=====================RECETAS====================
    
    private void cargarRecetaDesdeTabla() {
    int fila = TablaRecetas.getSelectedRow();
    if (fila >= 0) {
        String codigo = TablaRecetas.getValueAt(fila, 0).toString();
        Receta receta = controlador.buscarReceta(codigo);

        if (receta != null) {
            estado.setModel(receta);      // guardamos en el estado actual
            cambiarModoVista();           // cambiamos a modo vista (como haces en otros módulos)
            actualizarComponentes();      // actualiza botones/campos

            // ⚡ Además: limpiar tabla de indicaciones y llenarla con esta receta
            DefaultTableModel modelo = (DefaultTableModel) TablaIndicaciones.getModel();
            modelo.setRowCount(0);

            for (Indicaciones ind : receta.getIndicaciones()) {
                modelo.addRow(new Object[]{
                    ind.getMedicamento().getNombre(),
                    ind.getCantidad(),
                    ind.getIndicaciones(),
                    ind.getDuracion()
                });
            }
        }
    }
}
   private void actualizarTablaRecetas() {
    try {
        List<Receta> recetas = controlador.ListarRecetas(); // suponiendo que tu controlador tiene este método
        DefaultTableModel modelo = (DefaultTableModel) TablaRecetas.getModel();
        modelo.setRowCount(0); // limpia la tabla

        if (recetas != null) {
            for (Receta r : recetas) {
                modelo.addRow(new Object[]{
                    r.getCodReceta(),
                    r.getPaciente() != null ? r.getPaciente().getNombre() : "Sin paciente",
                    r.getMedico() != null ? r.getMedico().getNombre() : "Sin médico",
                    r.getFechaEmision(),
                    r.getFechaRetiro() != null ? r.getFechaRetiro() : "No retirado",
                    r.getEstado()
                });
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Error al cargar las recetas: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

private void mostrarIndicacionesReceta(Receta receta) {
    DefaultTableModel modelo = (DefaultTableModel) TablaIndicaciones.getModel();
    modelo.setRowCount(0); // limpiar la tabla

    if (receta != null && receta.getIndicaciones() != null) {
        for (Indicaciones i : receta.getIndicaciones()) {
            modelo.addRow(new Object[]{
                i.getMedicamento() != null ? i.getMedicamento().getNombre() : "Sin medicamento",
                i.getCantidad(),
                i.getIndicaciones(),
                i.getDuracion()
            });
        }
    }
}

    
    
    
//private void actualizarTablaRecetas() {
//    try {
//        List<Receta> recetas = controlador.ListarRecetas(); // obtén la lista de recetas desde tu controlador
//        DefaultTableModel modelo = (DefaultTableModel) TablaRecetas.getModel();
//        modelo.setRowCount(0); // limpiamos la tabla
//
//        if (recetas != null) {
//            for (Receta r : recetas) {
//                // Concatenar todas las indicaciones en un solo String
//                StringBuilder indicacionesStr = new StringBuilder();
//                for (Indicaciones i : r.getIndicaciones()) {
//                    indicacionesStr.append(i.getMedicamento().getNombre())
//                                   .append(" x")
//                                   .append(i.getCantidad())
//                                   .append(", ")
//                                   .append(i.getIndicaciones())
//                                   .append(", ")
//                                   .append(i.getDuracion())
//                                   .append(" días; ");
//                }
//
//                modelo.addRow(new Object[]{
//                    r.getCodReceta(),
//                    r.getPaciente().getNombre(),
//                    r.getMedico().getNombre(),
//                    indicacionesStr.toString(),
//                    r.getFechaEmision(),
//                    r.getFechaRetiro(),
//                    r.getEstado()
//                });
//            }
//        }
//
//    } catch (Exception ex) {
//        JOptionPane.showMessageDialog(null,
//            "Error al cargar las recetas: " + ex.getMessage(),
//            "Error",
//            JOptionPane.ERROR_MESSAGE);
//    }
//}
//private void mostrarIndicaciones(List<Indicaciones> lista) {
//    String[] columnas = {"Medicamento", "Cantidad", "Indicaciones", "Duración"};
//    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
//
//    for (Indicaciones i : lista) {
//        modelo.addRow(new Object[]{
//            i.getMedicamento().getNombre(),
//            i.getCantidad(),
//            i.getIndicaciones(),
//            i.getDuracion()
//        });
//    }
//
//    JTable TablaIndicaciones = new JTable(modelo);
//    JScrollPane scroll = new JScrollPane(tablaIndicaciones);
//
//    JOptionPane.showMessageDialog(this, scroll, "Indicaciones de la receta", JOptionPane.INFORMATION_MESSAGE);
//}


    
    
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelMedicos = new javax.swing.JPanel();
        buscartxt = new javax.swing.JPanel();
        ResultadoMtxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        BotonBuscarMedico = new javax.swing.JButton();
        BotonReporteMedico = new javax.swing.JButton();
        Jlabel7 = new javax.swing.JLabel();
        cedulatxt1 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        campoId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        campoId1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        campoId2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        BotonGuardarMedico = new javax.swing.JButton();
        BotonLimpiarMedico = new javax.swing.JButton();
        BotonEliminarMedico = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaMedicos = new javax.swing.JTable();
        PanelFarmaceutas = new javax.swing.JPanel();
        PanelIngresaFarm = new javax.swing.JPanel();
        LabelCedulaF = new javax.swing.JLabel();
        LabelNombreF = new javax.swing.JLabel();
        CedulaFtxt = new javax.swing.JTextField();
        NombreFtxt = new javax.swing.JTextField();
        BotonEliminarF = new javax.swing.JButton();
        BotonLimpiarF = new javax.swing.JButton();
        BotonGuardarF1 = new javax.swing.JButton();
        PanelBusquedaF = new javax.swing.JPanel();
        LabelCedulaFB1 = new javax.swing.JLabel();
        ResultadoFtxt = new javax.swing.JTextField();
        LabelResultadoF2 = new javax.swing.JLabel();
        CedulaFtxt2 = new javax.swing.JTextField();
        BotonBuscarF = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaFarmaceutas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        PanelIngresaFarm1 = new javax.swing.JPanel();
        LabelCedulaP1 = new javax.swing.JLabel();
        LabelNombreF1 = new javax.swing.JLabel();
        CedulaPtxt = new javax.swing.JTextField();
        NombrePtxt = new javax.swing.JTextField();
        BotonEliminarP = new javax.swing.JButton();
        BotonLimpiarP = new javax.swing.JButton();
        BotonGuardarP = new javax.swing.JButton();
        LabelNombreP2 = new javax.swing.JLabel();
        FechaNacPtxt = new javax.swing.JTextField();
        LabelTeltxt = new javax.swing.JLabel();
        TelefonoPtxt = new javax.swing.JTextField();
        PanelBusquedaF1 = new javax.swing.JPanel();
        LabelCedulaFB2 = new javax.swing.JLabel();
        ResultadoPtxt = new javax.swing.JTextField();
        LabelResultadoF3 = new javax.swing.JLabel();
        CedulaPtxt2 = new javax.swing.JTextField();
        BotonBuscarP = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaPacientes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        CodigoMtxt = new javax.swing.JTextField();
        LabelCodigoM = new javax.swing.JLabel();
        NombreMedicamentotxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        PresentacionMedicamentotxt = new javax.swing.JTextField();
        LabelPresentacionM = new javax.swing.JLabel();
        BotonGuardarMedicamento = new javax.swing.JButton();
        BotonLimpiarMedicamento = new javax.swing.JButton();
        BotonEliminarMedicamento = new javax.swing.JButton();
        buscartxt1 = new javax.swing.JPanel();
        ResultadoMedicamentotxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        BotonBuscarMedicamento = new javax.swing.JButton();
        BotonReporteMedicamento = new javax.swing.JButton();
        Jlabel8 = new javax.swing.JLabel();
        CodigoMtxt2 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaMedicamentos = new javax.swing.JTable();
        Dashboard = new javax.swing.JPanel();
        PanelDatos = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        AñoInicio = new javax.swing.JSpinner();
        AñoFin = new javax.swing.JSpinner();
        DiaMesInicio = new javax.swing.JSpinner();
        DiaMesFin = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxMedicamentos = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblMedicamentosGrafico = new javax.swing.JTable();
        BotonSeleccionFechas = new javax.swing.JButton();
        BotonAgregarMedicamentoComboBox = new javax.swing.JButton();
        PanelMedicamentos = new javax.swing.JPanel();
        PanelRecetas = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        buscartxt2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        BotonBuscarReceta = new javax.swing.JButton();
        BotonReporteMedico2 = new javax.swing.JButton();
        Jlabel9 = new javax.swing.JLabel();
        CodigoRecetastxt = new javax.swing.JTextField();
        BotonVerIndicaciones1 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        TablaResultadoHistorico1 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        TablaIndicaciones1 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaRecetas = new javax.swing.JTable();
        BotonVerIndicaciones = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaIndicaciones = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setName("Admisni"); // NOI18N

        buscartxt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        ResultadoMtxt.setEnabled(false);
        ResultadoMtxt.setPreferredSize(new java.awt.Dimension(96, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Cédula");

        BotonBuscarMedico.setText("Buscar");
        BotonBuscarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarMedicoActionPerformed(evt);
            }
        });

        BotonReporteMedico.setText("Reporte");
        BotonReporteMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonReporteMedicoActionPerformed(evt);
            }
        });

        Jlabel7.setText("Resultado:");

        cedulatxt1.setEnabled(false);
        cedulatxt1.setPreferredSize(new java.awt.Dimension(96, 22));
        cedulatxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cedulatxt1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buscartxtLayout = new javax.swing.GroupLayout(buscartxt);
        buscartxt.setLayout(buscartxtLayout);
        buscartxtLayout.setHorizontalGroup(
            buscartxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxtLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(cedulatxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BotonBuscarMedico)
                .addGap(50, 50, 50)
                .addComponent(Jlabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadoMtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscartxtLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonReporteMedico)
                .addGap(206, 206, 206))
        );
        buscartxtLayout.setVerticalGroup(
            buscartxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscartxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ResultadoMtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBuscarMedico)
                    .addComponent(Jlabel7)
                    .addComponent(cedulatxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(BotonReporteMedico)
                .addGap(19, 19, 19))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Medico", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        campoId.setEnabled(false);
        campoId.setPreferredSize(new java.awt.Dimension(96, 22));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Cédula");

        campoId1.setEnabled(false);
        campoId1.setPreferredSize(new java.awt.Dimension(96, 22));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nombre");

        campoId2.setEnabled(false);
        campoId2.setPreferredSize(new java.awt.Dimension(96, 22));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Especialidad");

        BotonGuardarMedico.setText("Guardar");
        BotonGuardarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGuardarMedicoActionPerformed(evt);
            }
        });

        BotonLimpiarMedico.setText("Limpiar");
        BotonLimpiarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonLimpiarMedicoActionPerformed(evt);
            }
        });

        BotonEliminarMedico.setText("Eliminar");
        BotonEliminarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarMedicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonGuardarMedico)
                .addGap(36, 36, 36)
                .addComponent(BotonLimpiarMedico)
                .addGap(47, 47, 47)
                .addComponent(BotonEliminarMedico)
                .addGap(259, 259, 259))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(campoId1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(campoId2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(campoId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(campoId2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonEliminarMedico)
                    .addComponent(BotonLimpiarMedico)
                    .addComponent(BotonGuardarMedico))
                .addGap(24, 24, 24))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        TablaMedicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Especialidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TablaMedicos);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelMedicosLayout = new javax.swing.GroupLayout(PanelMedicos);
        PanelMedicos.setLayout(PanelMedicosLayout);
        PanelMedicosLayout.setHorizontalGroup(
            PanelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMedicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buscartxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        PanelMedicosLayout.setVerticalGroup(
            PanelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMedicosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(buscartxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Médicos", PanelMedicos);

        PanelIngresaFarm.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Farmaceutas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        LabelCedulaF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCedulaF.setText("Cédula:");

        LabelNombreF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelNombreF.setText("Nombre:");

        BotonEliminarF.setText("Eliminar");
        BotonEliminarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarFActionPerformed(evt);
            }
        });

        BotonLimpiarF.setText("Limpiar");
        BotonLimpiarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonLimpiarFActionPerformed(evt);
            }
        });

        BotonGuardarF1.setText("Guardar");
        BotonGuardarF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGuardarF1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelIngresaFarmLayout = new javax.swing.GroupLayout(PanelIngresaFarm);
        PanelIngresaFarm.setLayout(PanelIngresaFarmLayout);
        PanelIngresaFarmLayout.setHorizontalGroup(
            PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresaFarmLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelIngresaFarmLayout.createSequentialGroup()
                        .addComponent(LabelCedulaF, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CedulaFtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BotonGuardarF1))
                .addGap(41, 41, 41)
                .addGroup(PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIngresaFarmLayout.createSequentialGroup()
                        .addComponent(LabelNombreF, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NombreFtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelIngresaFarmLayout.createSequentialGroup()
                        .addComponent(BotonLimpiarF)
                        .addGap(41, 41, 41)
                        .addComponent(BotonEliminarF)))
                .addContainerGap(358, Short.MAX_VALUE))
        );
        PanelIngresaFarmLayout.setVerticalGroup(
            PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresaFarmLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCedulaF)
                    .addComponent(LabelNombreF)
                    .addComponent(CedulaFtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NombreFtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonEliminarF)
                    .addComponent(BotonLimpiarF)
                    .addComponent(BotonGuardarF1))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        PanelBusquedaF.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        LabelCedulaFB1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCedulaFB1.setText("Cédula:");

        LabelResultadoF2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelResultadoF2.setText("Resultado: ");

        BotonBuscarF.setText("Buscar");
        BotonBuscarF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBusquedaFLayout = new javax.swing.GroupLayout(PanelBusquedaF);
        PanelBusquedaF.setLayout(PanelBusquedaFLayout);
        PanelBusquedaFLayout.setHorizontalGroup(
            PanelBusquedaFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBusquedaFLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(LabelCedulaFB1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelBusquedaFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BotonBuscarF)
                    .addComponent(CedulaFtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(LabelResultadoF2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ResultadoFtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(356, Short.MAX_VALUE))
        );
        PanelBusquedaFLayout.setVerticalGroup(
            PanelBusquedaFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBusquedaFLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(PanelBusquedaFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCedulaFB1)
                    .addComponent(ResultadoFtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelResultadoF2)
                    .addComponent(CedulaFtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(BotonBuscarF)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        TablaFarmaceutas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cedula", " Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaFarmaceutas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout PanelFarmaceutasLayout = new javax.swing.GroupLayout(PanelFarmaceutas);
        PanelFarmaceutas.setLayout(PanelFarmaceutasLayout);
        PanelFarmaceutasLayout.setHorizontalGroup(
            PanelFarmaceutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFarmaceutasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelFarmaceutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelIngresaFarm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelBusquedaF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelFarmaceutasLayout.setVerticalGroup(
            PanelFarmaceutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFarmaceutasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(PanelIngresaFarm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelBusquedaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Farmaceutas", PanelFarmaceutas);

        PanelIngresaFarm1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pacientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        LabelCedulaP1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCedulaP1.setText("Cédula:");

        LabelNombreF1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelNombreF1.setText("Nombre:");

        BotonEliminarP.setText("Eliminar");
        BotonEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarPActionPerformed(evt);
            }
        });

        BotonLimpiarP.setText("Limpiar");
        BotonLimpiarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonLimpiarPActionPerformed(evt);
            }
        });

        BotonGuardarP.setText("Guardar");
        BotonGuardarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGuardarPActionPerformed(evt);
            }
        });

        LabelNombreP2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelNombreP2.setText("FechaNacimiento:");

        LabelTeltxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTeltxt.setText("Telefono: ");

        javax.swing.GroupLayout PanelIngresaFarm1Layout = new javax.swing.GroupLayout(PanelIngresaFarm1);
        PanelIngresaFarm1.setLayout(PanelIngresaFarm1Layout);
        PanelIngresaFarm1Layout.setHorizontalGroup(
            PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNombreP2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCedulaP1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(BotonGuardarP)
                        .addGap(38, 38, 38)
                        .addComponent(BotonLimpiarP)
                        .addGap(39, 39, 39)
                        .addComponent(BotonEliminarP))
                    .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                        .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CedulaPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FechaNacPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelTeltxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelNombreF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38)
                        .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NombrePtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TelefonoPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(347, Short.MAX_VALUE))
        );
        PanelIngresaFarm1Layout.setVerticalGroup(
            PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCedulaP1)
                    .addComponent(CedulaPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNombreF1)
                    .addComponent(NombrePtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                        .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelNombreP2)
                            .addComponent(FechaNacPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelTeltxt))
                        .addGap(1, 1, 1))
                    .addComponent(TelefonoPtxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, Short.MAX_VALUE)
                .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonLimpiarP)
                    .addComponent(BotonGuardarP)
                    .addComponent(BotonEliminarP))
                .addContainerGap())
        );

        PanelBusquedaF1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        LabelCedulaFB2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCedulaFB2.setText("Cédula:");

        LabelResultadoF3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelResultadoF3.setText("Resultado: ");

        BotonBuscarP.setText("Buscar");
        BotonBuscarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBusquedaF1Layout = new javax.swing.GroupLayout(PanelBusquedaF1);
        PanelBusquedaF1.setLayout(PanelBusquedaF1Layout);
        PanelBusquedaF1Layout.setHorizontalGroup(
            PanelBusquedaF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBusquedaF1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(LabelCedulaFB2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelBusquedaF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BotonBuscarP)
                    .addComponent(CedulaPtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(LabelResultadoF3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ResultadoPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(356, Short.MAX_VALUE))
        );
        PanelBusquedaF1Layout.setVerticalGroup(
            PanelBusquedaF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBusquedaF1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(PanelBusquedaF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelCedulaFB2)
                    .addComponent(ResultadoPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelResultadoF3)
                    .addComponent(CedulaPtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(BotonBuscarP)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        TablaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cedula", " Nombre", "Fecha Nacimiento", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TablaPacientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelIngresaFarm1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelBusquedaF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(PanelIngresaFarm1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelBusquedaF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pacientes", jPanel3);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Medicamentos", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        CodigoMtxt.setEnabled(false);
        CodigoMtxt.setPreferredSize(new java.awt.Dimension(96, 22));

        LabelCodigoM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCodigoM.setText("Código:");

        NombreMedicamentotxt.setEnabled(false);
        NombreMedicamentotxt.setPreferredSize(new java.awt.Dimension(96, 22));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Nombre:");

        PresentacionMedicamentotxt.setEnabled(false);
        PresentacionMedicamentotxt.setPreferredSize(new java.awt.Dimension(96, 22));

        LabelPresentacionM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelPresentacionM.setText("Presentación:");

        BotonGuardarMedicamento.setText("Guardar");
        BotonGuardarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGuardarMedicamentoActionPerformed(evt);
            }
        });

        BotonLimpiarMedicamento.setText("Limpiar");
        BotonLimpiarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonLimpiarMedicamentoActionPerformed(evt);
            }
        });

        BotonEliminarMedicamento.setText("Eliminar");
        BotonEliminarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarMedicamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonGuardarMedicamento)
                .addGap(36, 36, 36)
                .addComponent(BotonLimpiarMedicamento)
                .addGap(47, 47, 47)
                .addComponent(BotonEliminarMedicamento)
                .addGap(259, 259, 259))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(LabelCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CodigoMtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreMedicamentotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(LabelPresentacionM, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PresentacionMedicamentotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CodigoMtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCodigoM)
                    .addComponent(jLabel8)
                    .addComponent(NombreMedicamentotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPresentacionM)
                    .addComponent(PresentacionMedicamentotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonEliminarMedicamento)
                    .addComponent(BotonLimpiarMedicamento)
                    .addComponent(BotonGuardarMedicamento))
                .addGap(24, 24, 24))
        );

        buscartxt1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        ResultadoMedicamentotxt.setEnabled(false);
        ResultadoMedicamentotxt.setPreferredSize(new java.awt.Dimension(96, 22));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Código:");

        BotonBuscarMedicamento.setText("Buscar");
        BotonBuscarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarMedicamentoActionPerformed(evt);
            }
        });

        BotonReporteMedicamento.setText("Reporte");

        Jlabel8.setText("Resultado:");

        CodigoMtxt2.setEnabled(false);
        CodigoMtxt2.setPreferredSize(new java.awt.Dimension(96, 22));

        javax.swing.GroupLayout buscartxt1Layout = new javax.swing.GroupLayout(buscartxt1);
        buscartxt1.setLayout(buscartxt1Layout);
        buscartxt1Layout.setHorizontalGroup(
            buscartxt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxt1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CodigoMtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BotonBuscarMedicamento)
                .addGap(50, 50, 50)
                .addComponent(Jlabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadoMedicamentotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscartxt1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonReporteMedicamento)
                .addGap(206, 206, 206))
        );
        buscartxt1Layout.setVerticalGroup(
            buscartxt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxt1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscartxt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ResultadoMedicamentotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBuscarMedicamento)
                    .addComponent(Jlabel8)
                    .addComponent(CodigoMtxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(BotonReporteMedicamento)
                .addGap(19, 19, 19))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        TablaMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Presentación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TablaMedicamentos);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buscartxt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(buscartxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Medicamentos", jPanel4);

        Dashboard.setEnabled(false);
        Dashboard.setMaximumSize(new java.awt.Dimension(767, 767));

        PanelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel9.setText("Desde");

        jLabel12.setText("Hasta");

        AñoInicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.YEAR));

        AñoFin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.YEAR));

        DiaMesInicio.setModel(new javax.swing.SpinnerDateModel());

        DiaMesFin.setModel(new javax.swing.SpinnerDateModel());

        jLabel13.setText("Año");

        jLabel14.setText("Día - Mes");

        jLabel15.setText("Medicamentos");

        jComboBoxMedicamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Gráfico Medicamentos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblMedicamentosGrafico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblMedicamentosGrafico);

        BotonSeleccionFechas.setText("Gráfico Recetas");
        BotonSeleccionFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSeleccionFechasActionPerformed(evt);
            }
        });

        BotonAgregarMedicamentoComboBox.setText("Agregar");
        BotonAgregarMedicamentoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAgregarMedicamentoComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDatosLayout = new javax.swing.GroupLayout(PanelDatos);
        PanelDatos.setLayout(PanelDatosLayout);
        PanelDatosLayout.setHorizontalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AñoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AñoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(DiaMesInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DiaMesFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(BotonSeleccionFechas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(BotonAgregarMedicamentoComboBox)
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        PanelDatosLayout.setVerticalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLayout.createSequentialGroup()
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jComboBoxMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonAgregarMedicamentoComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DiaMesInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AñoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(12, 12, 12)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AñoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(DiaMesFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonSeleccionFechas)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelMedicamentos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Medicamentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout PanelMedicamentosLayout = new javax.swing.GroupLayout(PanelMedicamentos);
        PanelMedicamentos.setLayout(PanelMedicamentosLayout);
        PanelMedicamentosLayout.setHorizontalGroup(
            PanelMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        PanelMedicamentosLayout.setVerticalGroup(
            PanelMedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PanelRecetas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recetas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout PanelRecetasLayout = new javax.swing.GroupLayout(PanelRecetas);
        PanelRecetas.setLayout(PanelRecetasLayout);
        PanelRecetasLayout.setHorizontalGroup(
            PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 355, Short.MAX_VALUE)
        );
        PanelRecetasLayout.setVerticalGroup(
            PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PanelMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DashboardLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(PanelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(PanelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelRecetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelMedicamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dashboard", Dashboard);

        buscartxt2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda Recetas", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Código:");

        BotonBuscarReceta.setText("Buscar");

        BotonReporteMedico2.setText("Reporte");

        Jlabel9.setText("Resultado:");

        CodigoRecetastxt.setEnabled(false);
        CodigoRecetastxt.setPreferredSize(new java.awt.Dimension(96, 22));

        BotonVerIndicaciones1.setText("Ver Indicaciones");
        BotonVerIndicaciones1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonVerIndicaciones1ActionPerformed(evt);
            }
        });

        TablaResultadoHistorico1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Paciente", "Medico", "Fecha Emision", "Fecha Retiro", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(TablaResultadoHistorico1);

        TablaIndicaciones1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Medicamento", "Cantidad", "Indicaciones", "Duración"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(TablaIndicaciones1);

        javax.swing.GroupLayout buscartxt2Layout = new javax.swing.GroupLayout(buscartxt2);
        buscartxt2.setLayout(buscartxt2Layout);
        buscartxt2Layout.setHorizontalGroup(
            buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxt2Layout.createSequentialGroup()
                .addGroup(buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buscartxt2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Jlabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(buscartxt2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CodigoRecetastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BotonBuscarReceta)
                                .addGap(18, 18, 18)
                                .addComponent(BotonReporteMedico2))))
                    .addGroup(buscartxt2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BotonVerIndicaciones1)))
                .addContainerGap(419, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscartxt2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buscartxt2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane10)
                    .addContainerGap()))
        );
        buscartxt2Layout.setVerticalGroup(
            buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxt2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(BotonBuscarReceta)
                    .addComponent(CodigoRecetastxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonReporteMedico2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jlabel9)
                .addGap(82, 82, 82)
                .addComponent(BotonVerIndicaciones1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buscartxt2Layout.createSequentialGroup()
                    .addGap(67, 67, 67)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(102, Short.MAX_VALUE)))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        TablaRecetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Paciente", "Medico", "Fecha Emision", "Fecha Retiro", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(TablaRecetas);

        BotonVerIndicaciones.setText("Ver Indicaciones");
        BotonVerIndicaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonVerIndicacionesActionPerformed(evt);
            }
        });

        TablaIndicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Medicamento", "Cantidad", "Indicaciones", "Duración"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(TablaIndicaciones);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BotonVerIndicaciones)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonVerIndicaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buscartxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buscartxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Historico", jPanel6);

        jPanel9.setPreferredSize(new java.awt.Dimension(224, 300));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Hospital Benjamín Nuñez");

        jLabel5.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sistema de Prescripción y Despacho de Medicamentos ");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Acerca de", jPanel7);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          


    private void BotonLimpiarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarMedicoActionPerformed
        if (estado.isViewing()) {
            limpiarCampos();
        } else {
            cancelarOperacion();
        }
    }//GEN-LAST:event_BotonLimpiarMedicoActionPerformed

    private void BotonLimpiarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarFActionPerformed
        if (estado.isViewing()) {
            limpiarCampos();
        } else {
            cancelarOperacion();
        }
    }//GEN-LAST:event_BotonLimpiarFActionPerformed

    private void BotonBuscarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarFActionPerformed
        buscarFarmaceuta();
    }//GEN-LAST:event_BotonBuscarFActionPerformed

    private void BotonGuardarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarMedicoActionPerformed
        guardarMedico();
    }//GEN-LAST:event_BotonGuardarMedicoActionPerformed

    private void BotonEliminarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarMedicoActionPerformed
        eliminarMedico();
    }//GEN-LAST:event_BotonEliminarMedicoActionPerformed

    private void BotonBuscarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarMedicoActionPerformed
        buscarMedico();
    }//GEN-LAST:event_BotonBuscarMedicoActionPerformed

    private void BotonGuardarF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarF1ActionPerformed
        guardarFarmaceuta();
    }//GEN-LAST:event_BotonGuardarF1ActionPerformed

    private void BotonEliminarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarFActionPerformed
        eliminarFarmaceuta();
    }//GEN-LAST:event_BotonEliminarFActionPerformed

    private void cedulatxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cedulatxt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulatxt1ActionPerformed

    private void BotonReporteMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReporteMedicoActionPerformed
        generarReporte();
    }//GEN-LAST:event_BotonReporteMedicoActionPerformed

    private void BotonGuardarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarPActionPerformed
        guardarPaciente();
    }//GEN-LAST:event_BotonGuardarPActionPerformed

    private void BotonLimpiarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarPActionPerformed
        if (estado.isViewing()) {
            limpiarCampos();
        } else {
            cancelarOperacion();
        }
    }//GEN-LAST:event_BotonLimpiarPActionPerformed

    private void BotonEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarPActionPerformed
        EliminarPaciente();
    }//GEN-LAST:event_BotonEliminarPActionPerformed

    private void BotonBuscarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarPActionPerformed
        buscarPaciente();
    }//GEN-LAST:event_BotonBuscarPActionPerformed

    private void BotonGuardarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarMedicamentoActionPerformed
        guardarMedicamento();
    }//GEN-LAST:event_BotonGuardarMedicamentoActionPerformed

    private void BotonLimpiarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarMedicamentoActionPerformed
        if (estado.isViewing()) {
            limpiarCampos();
        } else {
            cancelarOperacion();
        }
    }//GEN-LAST:event_BotonLimpiarMedicamentoActionPerformed

    private void BotonEliminarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarMedicamentoActionPerformed
        EliminarMedicamento();
    }//GEN-LAST:event_BotonEliminarMedicamentoActionPerformed

    private void BotonBuscarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarMedicamentoActionPerformed
        buscarMedicamento();
    }//GEN-LAST:event_BotonBuscarMedicamentoActionPerformed

    private void BotonVerIndicacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonVerIndicacionesActionPerformed
       int fila = TablaRecetas.getSelectedRow();
    if (fila >= 0) {
        String codigo = TablaRecetas.getValueAt(fila, 0).toString(); // código de la receta
        Receta receta = controlador.buscarReceta(codigo); // debes tener este método en tu controlador
        if (receta != null) {
            mostrarIndicacionesReceta(receta);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró la receta seleccionada.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this,
            "Seleccione una receta de la tabla.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_BotonVerIndicacionesActionPerformed
    
    //DashBoard=========================================================
    private final List<String> medicamentosSeleccionados = new ArrayList<>();
    private void configurarSpinnersDashboard() {
        // Spinner solo año
        AñoInicio.setModel(new javax.swing.SpinnerDateModel(new Date(), null, null, java.util.Calendar.YEAR));
        AñoFin.setModel(new javax.swing.SpinnerDateModel(new Date(), null, null, java.util.Calendar.YEAR));

        // Formateo para mostrar solo el año
        JSpinner.DateEditor editorAñoInicio = new JSpinner.DateEditor(AñoInicio, "yyyy");
        AñoInicio.setEditor(editorAñoInicio);

        JSpinner.DateEditor editorAñoFin = new JSpinner.DateEditor(AñoFin, "yyyy");
        AñoFin.setEditor(editorAñoFin);

        // Spinner día-mes
        DiaMesInicio.setModel(new javax.swing.SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        DiaMesFin.setModel(new javax.swing.SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));

        // Formateo para mostrar solo día y mes
        JSpinner.DateEditor editorDiaMesInicio = new JSpinner.DateEditor(DiaMesInicio, "dd-MMM");
        DiaMesInicio.setEditor(editorDiaMesInicio);

        JSpinner.DateEditor editorDiaMesFin = new JSpinner.DateEditor(DiaMesFin, "dd-MMM");
        DiaMesFin.setEditor(editorDiaMesFin);
    }

    private void confirmarSeleccionFechasPastel() {
        // 1. Capturar los valores de los Spinners
        Date fechaAñoInicio = (Date) AñoInicio.getValue();
        Date fechaAñoFin = (Date) AñoFin.getValue();
        Date fechaDiaMesInicio = (Date) DiaMesInicio.getValue();
        Date fechaDiaMesFin = (Date) DiaMesFin.getValue();

        // 2. Convertir a LocalDate (opcional, según tu método)
        LocalDate inicio = LocalDate.of(
                fechaAñoInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(),
                fechaDiaMesInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth(),
                fechaDiaMesInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth()
        );

        LocalDate fin = LocalDate.of(
                fechaAñoFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(),
                fechaDiaMesFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth(),
                fechaDiaMesFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth()
        );

        // 3. Llamar al método del controlador para crear el gráfico
        JFreeChart chart = controlador.crearGraficoPastelRecetasPorEstado(inicio, fin);

        // 4. Mostrarlo en el PanelRecetas
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(
                PanelRecetas.getWidth(),
                PanelRecetas.getHeight()
        ));

        PanelRecetas.removeAll();
        PanelRecetas.setLayout(new java.awt.BorderLayout());
        PanelRecetas.add(chartPanel, java.awt.BorderLayout.CENTER);
        PanelRecetas.validate();
        PanelRecetas.repaint();
    }

    private void crearGraficoPastelRecetasPorEstado(LocalDate fI, LocalDate fF) {
        // Pedimos el gráfico al controlador
        JFreeChart chart = controlador.crearGraficoPastelRecetasPorEstado(fI, fF);

        // Lo metemos en un ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setPreferredSize(null);
        chartPanel.setPreferredSize(new java.awt.Dimension(
                PanelRecetas.getWidth(),
                PanelRecetas.getHeight()
        ));

        // Limpiamos y agregamos al PanelRecetas
        PanelRecetas.removeAll();
        PanelRecetas.setLayout(new java.awt.BorderLayout());
        PanelRecetas.add(chartPanel, java.awt.BorderLayout.CENTER);

        // Forzar actualización visual
        PanelRecetas.validate();
        PanelRecetas.repaint();
    }

    public DefaultTableModel crearTablaMedicamentosPorMes(
            LocalDate inicio, LocalDate fin, List<String> seleccionados, List<Receta> listaRecetas) {

        // Construir los títulos de las columnas dinámicamente: Año-Mes
        List<String> columnas = new ArrayList<>();
        columnas.add("Medicamento");

        LocalDate fecha = inicio.withDayOfMonth(1);
        while (!fecha.isAfter(fin)) {
            columnas.add(fecha.getYear() + "-" + String.format("%02d", fecha.getMonthValue()));
            fecha = fecha.plusMonths(1);
        }

        DefaultTableModel modelo = new DefaultTableModel(columnas.toArray(), 0);

        // Llenar filas
        for (String med : seleccionados) {
            List<Object> fila = new ArrayList<>();
            fila.add(med);

            fecha = inicio.withDayOfMonth(1);
            while (!fecha.isAfter(fin)) {
                int cantidad = 0;
                for (Receta r : listaRecetas) {
                    LocalDate fechaEmision = r.getFechaEmision();
                    if ((fechaEmision.getYear() == fecha.getYear()) && (fechaEmision.getMonthValue() == fecha.getMonthValue())) {
                        for (Indicaciones i : r.getIndicaciones()) {
                            if (i.getMedicamento().getNombre().equals(med)) {
                                cantidad += i.getCantidad();
                            }
                        }
                    }
                }
                fila.add(cantidad);
                fecha = fecha.plusMonths(1);
            }

            modelo.addRow(fila.toArray());
        }

        return modelo;
    }

    private void cargarMedicamentosComboBox() {
        jComboBoxMedicamentos.removeAllItems();
        for (Medicamento m : controlador.ListarMedicamentos()) {
            jComboBoxMedicamentos.addItem(m.getNombre());
        }
    }

    // Acción del botón "Agregar medicamento"
    private void agregarMedicamentoSeleccionado() {
        String seleccionado = (String) jComboBoxMedicamentos.getSelectedItem();
        if (seleccionado != null && !medicamentosSeleccionados.contains(seleccionado)) {
            medicamentosSeleccionados.add(seleccionado);

            // Refrescar tabla y gráfico
            refrescarTablaMedicamentos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ya has agregado este medicamento o no hay selección válida.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refrescarTablaMedicamentos() {
        LocalDate inicio = LocalDate.of(
                ((Date) AñoInicio.getValue()).toInstant().atZone(ZoneId.systemDefault()).getYear(),
                ((Date) DiaMesInicio.getValue()).toInstant().atZone(ZoneId.systemDefault()).getMonth(),
                1
        );

        LocalDate fin = LocalDate.of(
                ((Date) AñoFin.getValue()).toInstant().atZone(ZoneId.systemDefault()).getYear(),
                ((Date) DiaMesFin.getValue()).toInstant().atZone(ZoneId.systemDefault()).getMonth(),
                1
        );

        DefaultTableModel modelo = crearTablaMedicamentosPorMes(
                inicio,
                fin,
                medicamentosSeleccionados,
                controlador.ListarRecetas()
        );
        tblMedicamentosGrafico.setModel(modelo);
    }

    private void generarGraficoMedicamentos() {
        if (medicamentosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe agregar al menos un medicamento para generar el gráfico.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate inicio = LocalDate.of(
                ((Date) AñoInicio.getValue()).toInstant().atZone(ZoneId.systemDefault()).getYear(),
                ((Date) DiaMesInicio.getValue()).toInstant().atZone(ZoneId.systemDefault()).getMonth(),
                1
        );

        LocalDate fin = LocalDate.of(
                ((Date) AñoFin.getValue()).toInstant().atZone(ZoneId.systemDefault()).getYear(),
                ((Date) DiaMesFin.getValue()).toInstant().atZone(ZoneId.systemDefault()).getMonth(),
                1
        );

        JFreeChart chart = controlador.crearGraficoLineaMedicamentos(
                inicio,
                fin,
                medicamentosSeleccionados,
                controlador.ListarRecetas()
        );

        PanelMedicamentos.removeAll();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(PanelMedicamentos.getWidth(), PanelMedicamentos.getHeight()));
        PanelMedicamentos.setLayout(new BorderLayout());
        PanelMedicamentos.add(chartPanel, BorderLayout.CENTER);
        PanelMedicamentos.validate();
        PanelMedicamentos.repaint();
    }

    private void confirmarSeleccionFechasLineal() {
        // 1. Capturar los valores de los Spinners
        Date fechaAñoInicio = (Date) AñoInicio.getValue();
        Date fechaAñoFin = (Date) AñoFin.getValue();
        Date fechaDiaMesInicio = (Date) DiaMesInicio.getValue();
        Date fechaDiaMesFin = (Date) DiaMesFin.getValue();

        // 2. Convertir a LocalDate
        LocalDate inicio = LocalDate.of(
                fechaAñoInicio.toInstant().atZone(ZoneId.systemDefault()).getYear(),
                fechaDiaMesInicio.toInstant().atZone(ZoneId.systemDefault()).getMonth(),
                1 // siempre el primer día del mes
        );

        LocalDate fin = LocalDate.of(
                fechaAñoFin.toInstant().atZone(ZoneId.systemDefault()).getYear(),
                fechaDiaMesFin.toInstant().atZone(ZoneId.systemDefault()).getMonth(),
                1 // primer día del mes
        );

        // ⚠️ Usar la lista global, NO crear una nueva
        if (medicamentosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe agregar al menos un medicamento.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Crear el gráfico de líneas usando el controlador
        JFreeChart chart = controlador.crearGraficoLineaMedicamentos(
                inicio,
                fin,
                medicamentosSeleccionados, // usar la lista global
                controlador.ListarRecetas()
        );

        // 4. Mostrarlo en el PanelMedicamentos
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(
                PanelMedicamentos.getWidth(),
                PanelMedicamentos.getHeight()
        ));

        PanelMedicamentos.removeAll();
        PanelMedicamentos.setLayout(new java.awt.BorderLayout());
        PanelMedicamentos.add(chartPanel, java.awt.BorderLayout.CENTER);
        PanelMedicamentos.validate();
        PanelMedicamentos.repaint();
    }
    
    
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        generarGraficoMedicamentos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BotonSeleccionFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSeleccionFechasActionPerformed
        // TODO add your handling code here:
        confirmarSeleccionFechasPastel();
    }//GEN-LAST:event_BotonSeleccionFechasActionPerformed

    private void BotonAgregarMedicamentoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarMedicamentoComboBoxActionPerformed
        // TODO add your handling code here:
        agregarMedicamentoSeleccionado();
    }//GEN-LAST:event_BotonAgregarMedicamentoComboBoxActionPerformed

    private void BotonVerIndicaciones1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonVerIndicaciones1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonVerIndicaciones1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            modelo modelo = new modelo();
            Control controlador = new Control(modelo);
            try {
                modelo.cargarDatos(); // ✅ carga médicos, pacientes, farmaceutas, etc.
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VentanaAdministrador(controlador).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner AñoFin;
    private javax.swing.JSpinner AñoInicio;
    private javax.swing.JButton BotonAgregarMedicamentoComboBox;
    private javax.swing.JButton BotonBuscarF;
    private javax.swing.JButton BotonBuscarMedicamento;
    private javax.swing.JButton BotonBuscarMedico;
    private javax.swing.JButton BotonBuscarP;
    private javax.swing.JButton BotonBuscarReceta;
    private javax.swing.JButton BotonEliminarF;
    private javax.swing.JButton BotonEliminarMedicamento;
    private javax.swing.JButton BotonEliminarMedico;
    private javax.swing.JButton BotonEliminarP;
    private javax.swing.JButton BotonGuardarF1;
    private javax.swing.JButton BotonGuardarMedicamento;
    private javax.swing.JButton BotonGuardarMedico;
    private javax.swing.JButton BotonGuardarP;
    private javax.swing.JButton BotonLimpiarF;
    private javax.swing.JButton BotonLimpiarMedicamento;
    private javax.swing.JButton BotonLimpiarMedico;
    private javax.swing.JButton BotonLimpiarP;
    private javax.swing.JButton BotonReporteMedicamento;
    private javax.swing.JButton BotonReporteMedico;
    private javax.swing.JButton BotonReporteMedico2;
    private javax.swing.JButton BotonSeleccionFechas;
    private javax.swing.JButton BotonVerIndicaciones;
    private javax.swing.JButton BotonVerIndicaciones1;
    private javax.swing.JTextField CedulaFtxt;
    private javax.swing.JTextField CedulaFtxt2;
    private javax.swing.JTextField CedulaPtxt;
    private javax.swing.JTextField CedulaPtxt2;
    private javax.swing.JTextField CodigoMtxt;
    private javax.swing.JTextField CodigoMtxt2;
    private javax.swing.JTextField CodigoRecetastxt;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JSpinner DiaMesFin;
    private javax.swing.JSpinner DiaMesInicio;
    private javax.swing.JTextField FechaNacPtxt;
    private javax.swing.JLabel Jlabel7;
    private javax.swing.JLabel Jlabel8;
    private javax.swing.JLabel Jlabel9;
    private javax.swing.JLabel LabelCedulaF;
    private javax.swing.JLabel LabelCedulaFB1;
    private javax.swing.JLabel LabelCedulaFB2;
    private javax.swing.JLabel LabelCedulaP1;
    private javax.swing.JLabel LabelCodigoM;
    private javax.swing.JLabel LabelNombreF;
    private javax.swing.JLabel LabelNombreF1;
    private javax.swing.JLabel LabelNombreP2;
    private javax.swing.JLabel LabelPresentacionM;
    private javax.swing.JLabel LabelResultadoF2;
    private javax.swing.JLabel LabelResultadoF3;
    private javax.swing.JLabel LabelTeltxt;
    private javax.swing.JTextField NombreFtxt;
    private javax.swing.JTextField NombreMedicamentotxt;
    private javax.swing.JTextField NombrePtxt;
    private javax.swing.JPanel PanelBusquedaF;
    private javax.swing.JPanel PanelBusquedaF1;
    private javax.swing.JPanel PanelDatos;
    private javax.swing.JPanel PanelFarmaceutas;
    private javax.swing.JPanel PanelIngresaFarm;
    private javax.swing.JPanel PanelIngresaFarm1;
    private javax.swing.JPanel PanelMedicamentos;
    private javax.swing.JPanel PanelMedicos;
    private javax.swing.JPanel PanelRecetas;
    private javax.swing.JTextField PresentacionMedicamentotxt;
    private javax.swing.JTextField ResultadoFtxt;
    private javax.swing.JTextField ResultadoMedicamentotxt;
    private javax.swing.JTextField ResultadoMtxt;
    private javax.swing.JTextField ResultadoPtxt;
    private javax.swing.JTable TablaFarmaceutas;
    private javax.swing.JTable TablaIndicaciones;
    private javax.swing.JTable TablaIndicaciones1;
    private javax.swing.JTable TablaMedicamentos;
    private javax.swing.JTable TablaMedicos;
    private javax.swing.JTable TablaPacientes;
    private javax.swing.JTable TablaRecetas;
    private javax.swing.JTable TablaResultadoHistorico1;
    private javax.swing.JTextField TelefonoPtxt;
    private javax.swing.JPanel buscartxt;
    private javax.swing.JPanel buscartxt1;
    private javax.swing.JPanel buscartxt2;
    private javax.swing.JTextField campoId;
    private javax.swing.JTextField campoId1;
    private javax.swing.JTextField campoId2;
    private javax.swing.JTextField cedulatxt1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxMedicamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblMedicamentosGrafico;
    // End of variables declaration//GEN-END:variables

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaAdministrador.class.getName());

    private final Control controlador; // <-- guardamos el controlador
    private FormHandler estado;

};
