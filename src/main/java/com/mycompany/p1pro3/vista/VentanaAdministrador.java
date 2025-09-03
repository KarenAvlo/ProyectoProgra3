package com.mycompany.p1pro3.vista;

import com.mycompany.p1pro3.Farmaceuta;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.control.Control;
import com.mycompany.p1pro3.modelo.modelo;
import cr.ac.una.gui.FormHandler;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

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
        //=========Listeners para Lista de farmaceutas==========

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
//                    cargarPacientesDesdeTabla();
                }
            }
        });
           //=========Listeners para Lista de Medicamentos==========
        TablaMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
//                    cargarMedicamentosDesdeTabla();
                }
            }
        });
            //=========Listeners para Lista de Recetas==========
        TablaRecetas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estado.isViewing()) {
//                    cargarRecetasDesdeTabla();
                }
            }
        });

    }
    
  

    public void init() {
        // Configurar DocumentListeners para detectar cambios
        DocumentListener listener = new DocumentListener() {
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
        //========ListenerCamposMedicos=========
        campoId.getDocument().addDocumentListener(listener);
        campoId1.getDocument().addDocumentListener(listener);
        campoId2.getDocument().addDocumentListener(listener);
        cedulatxt1.getDocument().addDocumentListener(listener);
        ResultadoMtxt.getDocument().addDocumentListener(listener);

        //========ListenerCamposFarmaceutas=========
        CedulaFtxt.getDocument().addDocumentListener(listener);
        NombreFtxt.getDocument().addDocumentListener(listener);
        CedulaFtxt2.getDocument().addDocumentListener(listener);
        ResultadoFtxt.getDocument().addDocumentListener(listener);

        //========ListenerCamposPacientes=========
        CedulaPtxt.getDocument().addDocumentListener(listener);
        NombrePtxt.getDocument().addDocumentListener(listener);
        FechaNacPtxt.getDocument().addDocumentListener(listener);
        TelefonoPtxt.getDocument().addDocumentListener(listener);
        CedulaPtxt2.getDocument().addDocumentListener(listener);
        ResultadoPtxt.getDocument().addDocumentListener(listener);
        //========ListenerCamposMedicamentos===============

        CodigoMtxt.getDocument().addDocumentListener(listener);
        NombreMedicamentotxt.getDocument().addDocumentListener(listener);
        PresentacionMedicamentotxt.getDocument().addDocumentListener(listener);
        CodigoMtxt2.getDocument().addDocumentListener(listener);
        ResultadoMedicamentotxt.getDocument().addDocumentListener(listener);

        //========ListenerCamposRecetas===============
        CodigoRecetastxt.getDocument().addDocumentListener(listener);
        ResultadoRecetastxt.getDocument().addDocumentListener(listener);

        // Actualizamos tablas
        jTabbedPane1.addChangeListener(e -> {
            if (jTabbedPane1.getSelectedIndex() == 0) { // pestaña "Médicos"
                actualizarTablaMedicos();
            } else if (jTabbedPane1.getSelectedIndex() == 1) { // pestaña "Farmaceutas"
                actualizarTablaFarmaceutas();
            } else if (jTabbedPane1.getSelectedIndex() == 2) { // pestaña "Pacientes"
//                actualizarTablaPacientes();
            } else if (jTabbedPane1.getSelectedIndex() == 3) { // pestaña "medicamentos"
//                actualizarTablaMedicamentos();
            }else if (jTabbedPane1.getSelectedIndex() == 5) { // pestaña "recetas"
//                actualizarTablaRecetas();
            }
            
            
        });

        actualizarTablaMedicos();
        actualizarTablaFarmaceutas();
        //                actualizarTablaPacientes();
//                actualizarTablaMedicamentos();
//                actualizarTablaRecetas();



        // 3️⃣ Cambiar a modo AGREGAR al abrir la ventana
        cambiarModoAgregar(); // <-- CAMBIO: antes estaba cambiarModoVista()

        // 4️⃣ Mostrar ventana
        setVisible(true);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE MODOS
    // -------------------------------------------------------------------------
    private void cambiarModoVista() {
        //Pone la ventana en modo de visualizacion
        //los campos se deshabilitan y los botones se configuran para acciones 
        //de visualizacion
        estado.changeToViewMode();
        actualizarComponentes();
        estado.setModified(false);
    }

    private void cambiarModoAgregar() {
        //para agregarNuevomedico
//        if (estado.isViewing()) {
        estado.changeToAddMode();
        actualizarComponentes();
        campoId.requestFocusInWindow();
        campoId.selectAll();
//        }
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

    private void cambiarModoBuscar() { //buscar
        estado.changeToSearchMode();
        actualizarComponentes();

        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();
        if (pestanaSeleccionada == 0) { // Médicos
            ResultadoMtxt.requestFocusInWindow();
            ResultadoMtxt.selectAll();
        } else if (pestanaSeleccionada == 1) { // Farmaceutas
            CedulaFtxt2.requestFocusInWindow();
            CedulaFtxt2.selectAll();
        }
        else if (pestanaSeleccionada == 2) { // Pacientes
            CedulaPtxt2.requestFocusInWindow();
            CedulaPtxt2.selectAll();
        }else if (pestanaSeleccionada == 3) { // Medicamentos
            CodigoMtxt2.requestFocusInWindow();
            CodigoMtxt2.selectAll();
        }
        else if (pestanaSeleccionada == 5) { // Medicamentos
            CodigoRecetastxt.requestFocusInWindow();
            CodigoRecetastxt.selectAll();
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
        //Controles de medico
        boolean NohaytextoMedicoid = campoId.getText().trim().isEmpty();
        boolean NohaytextoMedicoid1 = campoId1.getText().trim().isEmpty();
        boolean NohaytextoMedicoid2 = campoId2.getText().trim().isEmpty();

        BotonGuardarMedico.setEnabled(!estado.isViewing() && estado.isModified() || estado.isViewing()); // Guardar
        BotonLimpiarMedico.setEnabled(!NohaytextoMedicoid || !NohaytextoMedicoid1 || !NohaytextoMedicoid2); // Limpiar/Cancelar

//        boolean NohaytextoMedico = campoId.getText().trim().isEmpty() || campoId1.getText().trim().isEmpty(); 
        BotonEliminarMedico.setEnabled(!NohaytextoMedicoid); // Eliminar

        //si hay texto en esa casilla, habilitese
        boolean NohaytextoMedico2 = cedulatxt1.getText().trim().isEmpty();
        BotonBuscarMedico.setEnabled(!NohaytextoMedico2); // Buscar, 

        BotonReporteMedico.setEnabled(estado.isViewing() && estado.getModel() != null); // Reporte

        // Cambiar texto de botones según modo
        if (!NohaytextoMedicoid || !NohaytextoMedicoid1 || !NohaytextoMedicoid2) {
            BotonLimpiarMedico.setText("Limpiar");
        } else {
            BotonLimpiarMedico.setText("Cancelar");
        }

        //--------------Controles farmaceutas-------------
        boolean NohaytextoFarma = CedulaFtxt.getText().trim().isEmpty();
        boolean NohaytextoFarma2 = NombreFtxt.getText().trim().isEmpty();
        BotonGuardarF1.setEnabled(!estado.isViewing() && estado.isModified() || estado.isViewing()); // Guardar

        BotonLimpiarF.setEnabled(!NohaytextoFarma || !NohaytextoFarma2); // Limpiar/Cancelar

        BotonEliminarF.setEnabled(!NohaytextoFarma); // Eliminar

        boolean NohaytextoFarma3 = CedulaFtxt2.getText().trim().isEmpty();
        BotonBuscarF.setEnabled(!NohaytextoFarma3); // Buscar

        // Cambiar texto de botones según modo
        if (!NohaytextoFarma || !NohaytextoFarma2) {
            BotonLimpiarF.setText("Limpiar");
        } else {
            BotonLimpiarF.setText("Cancelar");
        }

    }

    private void actualizarCampos() {

        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();

        if (pestanaSeleccionada == 0) { //si es la pestaña de medicos
            Medico medico = (Medico) estado.getModel();

            // Habilitar/deshabilitar campos según modo
            boolean modoEdicion = !estado.isViewing();
            campoId.setEnabled(estado.getModel() == null || modoEdicion || estado.isModified());

            campoId1.setEnabled(estado.getModel() == null || modoEdicion || estado.isModified());

            campoId2.setEnabled(estado.getModel() == null || modoEdicion || estado.isModified());

            cedulatxt1.setEnabled(true);
            ResultadoMtxt.setEnabled(estado.isAdding() || estado.isSearching() || modoEdicion);

        } else if (pestanaSeleccionada == 1) { // Pestaña "Farmaceutas"
            Farmaceuta farmaceuta = (Farmaceuta) estado.getModel();

            // Habilitar/deshabilitar campos según el modo de la pestaña de farmaceutas
            boolean modoEdicion = !estado.isViewing();
            CedulaFtxt.setEnabled(estado.getModel() == null || modoEdicion || estado.isModified());
            NombreFtxt.setEnabled(estado.getModel() == null || modoEdicion || estado.isModified());
            CedulaFtxt2.setEnabled(true);

            ResultadoFtxt.setEnabled(estado.isAdding() || estado.isSearching() || modoEdicion);
        }

    }

    private void indicarCambios() {
        estado.setModified(true);
        actualizarControles();
    }

    private void limpiarCampos() {
        estado.setModel(null);
        campoId.setText("");
        campoId1.setText("");
        campoId2.setText("");
        ResultadoMtxt.setText("");

        //limpia farmaceutas
        CedulaFtxt.setText("");
        NombreFtxt.setText("");
        CedulaFtxt2.setText("");
//        estado.setModel(null);
//    estado.setModified(false);

//        actualizarCampos();
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
                limpiarCampos();
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

            if (cedula.isEmpty() || nombre.isEmpty()) { //verificacion que no sean vacios
                JOptionPane.showMessageDialog(this, "Nombre y cédula son obligatorios");
                return;
            }

            boolean exito;
            if (estado.isAdding()) { //si esta añadiendo en el field,entonces añadalo
                exito = controlador.agregarFarmaceuta(cedula, nombre);
            } else if (estado.isEditing()) { // si está editando los espacios
                //eliminamos lo que está en proceso
                controlador.EliminarFarmaceuta(((Farmaceuta) estado.getModel()).getCedula());
                //luego añadimos
                exito = controlador.agregarFarmaceuta(cedula, nombre);
            } else {
                exito = false;
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Farmaceuta guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cambiarModoVista();
                estado.setModel(null);     // Establece el modelo como nulo para indicar un nuevo registro
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
                CedulaFtxt.setText(farmaceuta.getCedula());
                NombreFtxt.setText(farmaceuta.getNombre());
            }
        }
    }

    private void EliminarFarmaceuta() {

        String cedula = CedulaFtxt.getText().trim();

        Farmaceuta farma = controlador.buscarFarmaceuta(cedula);
        if (farma == null) {
            JOptionPane.showMessageDialog(this, "No hay farmaceuta seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar farmaceuta " + farma.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controlador.EliminarFarmaceuta(cedula);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Farmaceuta eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                estado.setModel(null);
                cambiarModoVista();
                actualizarControles();
                actualizarTablaFarmaceutas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar farmaceuta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void buscarFarmaceuta() {

        if (!estado.isSearching()) {
            String Cedula = CedulaFtxt2.getText().trim();

            if (Cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cedula para buscar");
                return;
            }

            Farmaceuta farma = controlador.buscarFarmaceuta(Cedula);
            if (farma != null) {

                limpiarCampos();
                ResultadoFtxt.setText(farma.getNombre());
                estado.setModel(null);
                cambiarModoVista();
                actualizarComponentes();
                JOptionPane.showMessageDialog(this, "Farmaceuta encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Si el farmaceuta no se encuentra
                JOptionPane.showMessageDialog(this, "No se encontró el farmaceuta con esa cédula", "Error", JOptionPane.ERROR_MESSAGE);
                // 🟢 Limpiar el campo de resultado
                ResultadoFtxt.setText("No se encontró.");
            }
        } else {
            // Si el modo no es búsqueda, cambiarlo a búsqueda
            cambiarModoBuscar();
        }

    }

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
        jPanel5 = new javax.swing.JPanel();
        Datos = new javax.swing.JPanel();
        Medicamentos = new javax.swing.JPanel();
        Recetas = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        buscartxt2 = new javax.swing.JPanel();
        ResultadoRecetastxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        BotonBuscarReceta = new javax.swing.JButton();
        BotonReporteMedico2 = new javax.swing.JButton();
        Jlabel9 = new javax.swing.JLabel();
        CodigoRecetastxt = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaRecetas = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setName("Admisni"); // NOI18N

        buscartxt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        ResultadoMtxt.setEnabled(false);
        ResultadoMtxt.setPreferredSize(new java.awt.Dimension(96, 22));
        ResultadoMtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResultadoMtxtActionPerformed(evt);
            }
        });

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
        campoId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoIdActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Cédula");

        campoId1.setEnabled(false);
        campoId1.setPreferredSize(new java.awt.Dimension(96, 22));
        campoId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoId1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nombre");

        campoId2.setEnabled(false);
        campoId2.setPreferredSize(new java.awt.Dimension(96, 22));
        campoId2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoId2ActionPerformed(evt);
            }
        });

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
                .addGap(16, 170, Short.MAX_VALUE)
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
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(buscartxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
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
                .addContainerGap(104, Short.MAX_VALUE))
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
                .addContainerGap(205, Short.MAX_VALUE))
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

        CedulaFtxt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CedulaFtxt2ActionPerformed(evt);
            }
        });

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
                .addContainerGap(203, Short.MAX_VALUE))
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
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Farmaceutas", PanelFarmaceutas);

        PanelIngresaFarm1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pacientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        LabelCedulaP1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCedulaP1.setText("Cédula:");

        LabelNombreF1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelNombreF1.setText("Nombre:");

        NombrePtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombrePtxtActionPerformed(evt);
            }
        });

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

        TelefonoPtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TelefonoPtxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelIngresaFarm1Layout = new javax.swing.GroupLayout(PanelIngresaFarm1);
        PanelIngresaFarm1.setLayout(PanelIngresaFarm1Layout);
        PanelIngresaFarm1Layout.setHorizontalGroup(
            PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                .addGroup(PanelIngresaFarm1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(LabelCedulaP1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelIngresaFarm1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(LabelNombreP2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        CedulaPtxt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CedulaPtxt2ActionPerformed(evt);
            }
        });

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
                .addContainerGap(203, Short.MAX_VALUE))
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
        jScrollPane3.setViewportView(TablaPacientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pacientes", jPanel3);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Medicamentos", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        CodigoMtxt.setEnabled(false);
        CodigoMtxt.setPreferredSize(new java.awt.Dimension(96, 22));

        LabelCodigoM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelCodigoM.setText("Código:");

        NombreMedicamentotxt.setEnabled(false);
        NombreMedicamentotxt.setPreferredSize(new java.awt.Dimension(96, 22));
        NombreMedicamentotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreMedicamentotxtActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Nombre:");

        PresentacionMedicamentotxt.setEnabled(false);
        PresentacionMedicamentotxt.setPreferredSize(new java.awt.Dimension(96, 22));
        PresentacionMedicamentotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PresentacionMedicamentotxtActionPerformed(evt);
            }
        });

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
                .addGap(16, 170, Short.MAX_VALUE)
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
        ResultadoMedicamentotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResultadoMedicamentotxtActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Código:");

        BotonBuscarMedicamento.setText("Buscar");
        BotonBuscarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarMedicamentoActionPerformed(evt);
            }
        });

        BotonReporteMedicamento.setText("Reporte");
        BotonReporteMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonReporteMedicamentoActionPerformed(evt);
            }
        });

        Jlabel8.setText("Resultado:");

        CodigoMtxt2.setEnabled(false);
        CodigoMtxt2.setPreferredSize(new java.awt.Dimension(96, 22));
        CodigoMtxt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CodigoMtxt2ActionPerformed(evt);
            }
        });

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
                .addContainerGap(106, Short.MAX_VALUE))
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
        jScrollPane4.setViewportView(TablaMedicamentos);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buscartxt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
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
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Medicamentos", jPanel4);

        Datos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout DatosLayout = new javax.swing.GroupLayout(Datos);
        Datos.setLayout(DatosLayout);
        DatosLayout.setHorizontalGroup(
            DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        DatosLayout.setVerticalGroup(
            DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        Medicamentos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Medicamentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout MedicamentosLayout = new javax.swing.GroupLayout(Medicamentos);
        Medicamentos.setLayout(MedicamentosLayout);
        MedicamentosLayout.setHorizontalGroup(
            MedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        MedicamentosLayout.setVerticalGroup(
            MedicamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        Recetas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recetas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout RecetasLayout = new javax.swing.GroupLayout(Recetas);
        Recetas.setLayout(RecetasLayout);
        RecetasLayout.setHorizontalGroup(
            RecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        RecetasLayout.setVerticalGroup(
            RecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Datos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(Medicamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(Recetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Datos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Medicamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Recetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dashboard", jPanel5);

        buscartxt2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda Recetas", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        ResultadoRecetastxt.setEnabled(false);
        ResultadoRecetastxt.setPreferredSize(new java.awt.Dimension(96, 22));
        ResultadoRecetastxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResultadoRecetastxtActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Código:");

        BotonBuscarReceta.setText("Buscar");
        BotonBuscarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarRecetaActionPerformed(evt);
            }
        });

        BotonReporteMedico2.setText("Reporte");
        BotonReporteMedico2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonReporteMedico2ActionPerformed(evt);
            }
        });

        Jlabel9.setText("Resultado:");

        CodigoRecetastxt.setEnabled(false);
        CodigoRecetastxt.setPreferredSize(new java.awt.Dimension(96, 22));
        CodigoRecetastxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CodigoRecetastxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buscartxt2Layout = new javax.swing.GroupLayout(buscartxt2);
        buscartxt2.setLayout(buscartxt2Layout);
        buscartxt2Layout.setHorizontalGroup(
            buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxt2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CodigoRecetastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BotonBuscarReceta)
                .addGap(50, 50, 50)
                .addComponent(Jlabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadoRecetastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscartxt2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonReporteMedico2)
                .addGap(206, 206, 206))
        );
        buscartxt2Layout.setVerticalGroup(
            buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxt2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscartxt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(ResultadoRecetastxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBuscarReceta)
                    .addComponent(Jlabel9)
                    .addComponent(CodigoRecetastxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(BotonReporteMedico2)
                .addGap(19, 19, 19))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        TablaRecetas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(TablaRecetas);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buscartxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(buscartxt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Historico", jPanel6);

        jPanel9.setPreferredSize(new java.awt.Dimension(224, 300));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Hospital Benjamín Nuñez");

        jLabel5.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sistema de Prescripción y Despacho de Medicamentos ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Acerca de", jPanel7);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          


    private void BotonLimpiarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarMedicoActionPerformed
        limpiarCampos(); // Clears all the fields
        estado.setModel(null); // Resets the model
        estado.setModified(false); // Resets the modified state
        actualizarComponentes();


    }//GEN-LAST:event_BotonLimpiarMedicoActionPerformed

    private void CedulaFtxt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CedulaFtxt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CedulaFtxt2ActionPerformed

    private void BotonLimpiarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarFActionPerformed
        limpiarCampos();
        actualizarComponentes();
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

    private void BotonReporteMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReporteMedicoActionPerformed

    }//GEN-LAST:event_BotonReporteMedicoActionPerformed

    private void BotonGuardarF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarF1ActionPerformed
        guardarFarmaceuta();
    }//GEN-LAST:event_BotonGuardarF1ActionPerformed

    private void BotonEliminarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarFActionPerformed
        EliminarFarmaceuta();
    }//GEN-LAST:event_BotonEliminarFActionPerformed

    private void ResultadoMtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResultadoMtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResultadoMtxtActionPerformed

    private void cedulatxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cedulatxt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulatxt1ActionPerformed

    private void campoId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoId1ActionPerformed

    private void campoId2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoId2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoId2ActionPerformed

    private void BotonEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonEliminarPActionPerformed

    private void BotonLimpiarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonLimpiarPActionPerformed

    private void BotonGuardarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonGuardarPActionPerformed

    private void CedulaPtxt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CedulaPtxt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CedulaPtxt2ActionPerformed

    private void BotonBuscarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonBuscarPActionPerformed

    private void NombrePtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombrePtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombrePtxtActionPerformed

    private void TelefonoPtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TelefonoPtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelefonoPtxtActionPerformed

    private void NombreMedicamentotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreMedicamentotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreMedicamentotxtActionPerformed

    private void PresentacionMedicamentotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PresentacionMedicamentotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PresentacionMedicamentotxtActionPerformed

    private void BotonGuardarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarMedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonGuardarMedicamentoActionPerformed

    private void BotonLimpiarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarMedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonLimpiarMedicamentoActionPerformed

    private void BotonEliminarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarMedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonEliminarMedicamentoActionPerformed

    private void ResultadoMedicamentotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResultadoMedicamentotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResultadoMedicamentotxtActionPerformed

    private void BotonBuscarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarMedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonBuscarMedicamentoActionPerformed

    private void BotonReporteMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReporteMedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonReporteMedicamentoActionPerformed

    private void CodigoMtxt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CodigoMtxt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CodigoMtxt2ActionPerformed

    private void ResultadoRecetastxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResultadoRecetastxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResultadoRecetastxtActionPerformed

    private void BotonBuscarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarRecetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonBuscarRecetaActionPerformed

    private void BotonReporteMedico2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReporteMedico2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonReporteMedico2ActionPerformed

    private void CodigoRecetastxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CodigoRecetastxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CodigoRecetastxtActionPerformed

    private void campoIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoIdActionPerformed

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
    private javax.swing.JTextField CedulaFtxt;
    private javax.swing.JTextField CedulaFtxt2;
    private javax.swing.JTextField CedulaPtxt;
    private javax.swing.JTextField CedulaPtxt2;
    private javax.swing.JTextField CodigoMtxt;
    private javax.swing.JTextField CodigoMtxt2;
    private javax.swing.JTextField CodigoRecetastxt;
    private javax.swing.JPanel Datos;
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
    private javax.swing.JPanel Medicamentos;
    private javax.swing.JTextField NombreFtxt;
    private javax.swing.JTextField NombreMedicamentotxt;
    private javax.swing.JTextField NombrePtxt;
    private javax.swing.JPanel PanelBusquedaF;
    private javax.swing.JPanel PanelBusquedaF1;
    private javax.swing.JPanel PanelFarmaceutas;
    private javax.swing.JPanel PanelIngresaFarm;
    private javax.swing.JPanel PanelIngresaFarm1;
    private javax.swing.JPanel PanelMedicos;
    private javax.swing.JTextField PresentacionMedicamentotxt;
    private javax.swing.JPanel Recetas;
    private javax.swing.JTextField ResultadoFtxt;
    private javax.swing.JTextField ResultadoMedicamentotxt;
    private javax.swing.JTextField ResultadoMtxt;
    private javax.swing.JTextField ResultadoPtxt;
    private javax.swing.JTextField ResultadoRecetastxt;
    private javax.swing.JTable TablaFarmaceutas;
    private javax.swing.JTable TablaMedicamentos;
    private javax.swing.JTable TablaMedicos;
    private javax.swing.JTable TablaPacientes;
    private javax.swing.JTable TablaRecetas;
    private javax.swing.JTextField TelefonoPtxt;
    private javax.swing.JPanel buscartxt;
    private javax.swing.JPanel buscartxt1;
    private javax.swing.JPanel buscartxt2;
    private javax.swing.JTextField campoId;
    private javax.swing.JTextField campoId1;
    private javax.swing.JTextField campoId2;
    private javax.swing.JTextField cedulatxt1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaAdministrador.class.getName());

    private final Control controlador; // <-- guardamos el controlador
    private FormHandler estado;
};
