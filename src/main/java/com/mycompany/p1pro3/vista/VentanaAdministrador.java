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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
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

        campoId.getDocument().addDocumentListener(listener);
        campoId1.getDocument().addDocumentListener(listener);
        campoId2.getDocument().addDocumentListener(listener);
        cedulatxt.getDocument().addDocumentListener(listener);

        CedulaFtxt.getDocument().addDocumentListener(listener);
        NombreFtxt.getDocument().addDocumentListener(listener);

        CedulaFtxt2.getDocument().addDocumentListener(listener);

        // 2️⃣ Actualizar tablas
        jTabbedPane1.addChangeListener(e -> {
            if (jTabbedPane1.getSelectedIndex() == 0) { // pestaña "Médicos"
                actualizarTablaMedicos();
            } else if (jTabbedPane1.getSelectedIndex() == 1) { // pestaña "Farmaceutas"
                actualizarTablaFarmaceutas();
            }
        });

        actualizarTablaMedicos();
        actualizarTablaFarmaceutas();

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
            cedulatxt.requestFocusInWindow();
            cedulatxt.selectAll();
        } else if (pestanaSeleccionada == 1) { // Farmaceutas
            CedulaFtxt2.requestFocusInWindow();
            CedulaFtxt2.selectAll();
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
        BotonGuardarMedico.setEnabled(!estado.isViewing() && estado.isModified()); // Guardar
        BotonLimpiarMedico.setEnabled(!estado.isViewing()); // Limpiar/Cancelar

        boolean NohaytextoMedico = campoId.getText().trim().isEmpty();
        BotonEliminarMedico.setEnabled(!NohaytextoMedico); // Eliminar

        //si hay texto en esa casilla, habilitese
        boolean NohaytextoMedico2=cedulatxt.getText().trim().isEmpty();
        BotonBuscarMedico.setEnabled(!NohaytextoMedico2); // Buscar, 

        BotonReporteMedico.setEnabled(estado.isViewing()); // Reporte

        // Cambiar texto de botones según modo
        if (!estado.isViewing() && estado.getModel()!=null) {
            BotonLimpiarMedico.setText("Limpiar");
        } else {
            BotonLimpiarMedico.setText("Cancelar");
        }

        //--------------Controles farmaceutas-------------
        BotonGuardarF1.setEnabled(!estado.isViewing() && estado.isModified()); // Guardar
        BotonLimpiarF.setEnabled(!estado.isViewing()); // Limpiar/Cancelar
        
        boolean NohaytextoFarma = CedulaFtxt.getText().trim().isEmpty();
        BotonEliminarF.setEnabled(!NohaytextoFarma); // Eliminar

        boolean NohaytextoFarma2 = CedulaFtxt2.getText().trim().isEmpty();
        BotonBuscarF.setEnabled(!NohaytextoFarma2); // Buscar

        // Cambiar texto de botones según modo
        BotonLimpiarF.setText("Limpiar");

    }

    private void actualizarCampos() {

        int pestanaSeleccionada = jTabbedPane1.getSelectedIndex();

        if (pestanaSeleccionada == 0) { //si es la pestaña de medicos
            Medico medico = (Medico) estado.getModel();

            if (medico != null) {
                campoId.setText(medico.getCedula());
                campoId1.setText(medico.getNombre());
                campoId2.setText(medico.getEspecialidad());
            } else {
                campoId.setText("");
                campoId1.setText("");
                campoId2.setText("");
            }

            // Habilitar/deshabilitar campos según modo
            boolean modoEdicion = !estado.isViewing();
            campoId.setEnabled( modoEdicion ||estado.isModified());

//            campoId.setEnabled(estado.isAdding() || estado.isSearching() || modoEdicion);
            campoId1.setEnabled(estado.getModel() == null || modoEdicion);
//            campoId1.setEnabled(modoEdicion);
            campoId2.setEnabled(estado.getModel() == null || modoEdicion);
//            campoId2.setEnabled(modoEdicion);
            cedulatxt.setEnabled(estado.isAdding() || estado.isSearching() || modoEdicion);

        } else if (pestanaSeleccionada == 1) { // Pestaña "Farmaceutas"
            Farmaceuta farmaceuta = (Farmaceuta) estado.getModel();

            if (farmaceuta != null) {
                CedulaFtxt.setText(farmaceuta.getCedula());
                NombreFtxt.setText(farmaceuta.getNombre());
            } else {
                CedulaFtxt.setText("");
                NombreFtxt.setText("");
            }

            // Habilitar/deshabilitar campos según el modo de la pestaña de farmaceutas
            boolean modoEdicion = !estado.isViewing();
            CedulaFtxt.setEnabled(modoEdicion ||estado.isModified());
            NombreFtxt.setEnabled(estado.getModel() == null || modoEdicion);
            CedulaFtxt2.setEnabled(estado.isAdding() || estado.isSearching() || modoEdicion);
        }

    }

//    private void NuevoRegistro() {
//        estado.setAdding(true); // Assuming you have this method
//        estado.setModified(true); // Set to modified so the save button remains enabled
//        estado.setModel(null); // Clear the model to indicate a new record
//
//        limpiarCampos(); // Clear the text fields
//
//        actualizarControles();
//
//    }
    private void indicarCambios() {
        estado.setModified(true);
        actualizarControles();
    }

    private void limpiarCampos() {
        estado.setModel(null);
        campoId.setText("");
        campoId1.setText("");
        campoId2.setText("");
        cedulatxt.setText("");

        //limpia farmaceutas
        CedulaFtxt.setText("");
        NombreFtxt.setText("");
        CedulaFtxt2.setText("");

        cambiarModoAgregar();

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
        if (estado.isSearching()) {
            // Ejecutar búsqueda
            String cedula = cedulatxt.getText().trim();
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cédula para buscar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico medico = controlador.buscarMedico(cedula);
            if (medico != null) {
                estado.setModel(medico);
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
            DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
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
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow >= 0 && estado.isViewing()) {
            String cedula = jTable2.getValueAt(selectedRow, 0).toString();
            Medico medico = controlador.buscarMedico(cedula);
            if (medico != null) {
                estado.setModel(medico);
                actualizarComponentes();
            }
        }
    }

    //================Fin Medicos===============
    ////--------------Farmaceutas----------------
//    private void limpiarCamposFarmaceutas() {
//        CedulaFtxt.setText("");
//        NombreFtxt.setText("");
//        CedulaFtxt2.setText("");
//
//        cambiarModoAgregar();
//   }

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
        String cedula = CedulaFtxt2.getText().trim();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cedula a eliminar","Error",JOptionPane.OK_OPTION);
            return;

        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al farmaceuta con cédula " + cedula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // 4. Llamar al método del controlador para eliminar el farmaceuta
            boolean exito = controlador.eliminarFarmaceuta(cedula);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Farmaceuta eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Opcional: limpiar los campos y actualizar la tabla
                limpiarCampos();
                actualizarTablaFarmaceutas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el farmaceuta. No se encontró la cédula.", "Error", JOptionPane.ERROR_MESSAGE);
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
                ResultadoFtxt.setText(farma.getNombre());
                estado.setModel(farma);
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
        cedulatxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        BotonBuscarMedico = new javax.swing.JButton();
        BotonReporteMedico = new javax.swing.JButton();
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
        jTable2 = new javax.swing.JTable();
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
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Datos = new javax.swing.JPanel();
        Medicamentos = new javax.swing.JPanel();
        Recetas = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setName("Admisni"); // NOI18N

        buscartxt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Búsqueda", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        cedulatxt.setEnabled(false);
        cedulatxt.setPreferredSize(new java.awt.Dimension(96, 22));

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

        javax.swing.GroupLayout buscartxtLayout = new javax.swing.GroupLayout(buscartxt);
        buscartxt.setLayout(buscartxtLayout);
        buscartxtLayout.setHorizontalGroup(
            buscartxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxtLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(cedulatxt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(BotonBuscarMedico)
                .addGap(161, 161, 161)
                .addComponent(BotonReporteMedico)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buscartxtLayout.setVerticalGroup(
            buscartxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscartxtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscartxtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cedulatxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBuscarMedico)
                    .addComponent(BotonReporteMedico))
                .addContainerGap(75, Short.MAX_VALUE))
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
                .addGap(16, 16, 16)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(BotonGuardarMedico)
                        .addGap(31, 31, 31)
                        .addComponent(BotonLimpiarMedico)
                        .addGap(31, 31, 31)
                        .addComponent(BotonEliminarMedico)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(campoId1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(campoId2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(campoId2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonGuardarMedico)
                    .addComponent(BotonLimpiarMedico)
                    .addComponent(BotonEliminarMedico))
                .addGap(16, 16, 16))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(19, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(185, Short.MAX_VALUE))
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
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Farmaceutas", PanelFarmaceutas);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pacientes", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Medicamentos", jPanel4);

        Datos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout DatosLayout = new javax.swing.GroupLayout(Datos);
        Datos.setLayout(DatosLayout);
        DatosLayout.setHorizontalGroup(
            DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
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
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dashboard", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
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
        jLabel5.setText("Sistema de Prescribción y Despacho de Medicamentos ");

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
                        .addGap(35, 35, 35)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
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

    private void CedulaFtxt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CedulaFtxt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CedulaFtxt2ActionPerformed

    private void BotonLimpiarFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarFActionPerformed
       limpiarCampos();
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
    private javax.swing.JButton BotonBuscarMedico;
    private javax.swing.JButton BotonEliminarF;
    private javax.swing.JButton BotonEliminarMedico;
    private javax.swing.JButton BotonGuardarF1;
    private javax.swing.JButton BotonGuardarMedico;
    private javax.swing.JButton BotonLimpiarF;
    private javax.swing.JButton BotonLimpiarMedico;
    private javax.swing.JButton BotonReporteMedico;
    private javax.swing.JTextField CedulaFtxt;
    private javax.swing.JTextField CedulaFtxt2;
    private javax.swing.JPanel Datos;
    private javax.swing.JLabel LabelCedulaF;
    private javax.swing.JLabel LabelCedulaFB1;
    private javax.swing.JLabel LabelNombreF;
    private javax.swing.JLabel LabelResultadoF2;
    private javax.swing.JPanel Medicamentos;
    private javax.swing.JTextField NombreFtxt;
    private javax.swing.JPanel PanelBusquedaF;
    private javax.swing.JPanel PanelFarmaceutas;
    private javax.swing.JPanel PanelIngresaFarm;
    private javax.swing.JPanel PanelMedicos;
    private javax.swing.JPanel Recetas;
    private javax.swing.JTextField ResultadoFtxt;
    private javax.swing.JTable TablaFarmaceutas;
    private javax.swing.JPanel buscartxt;
    private javax.swing.JTextField campoId;
    private javax.swing.JTextField campoId1;
    private javax.swing.JTextField campoId2;
    private javax.swing.JTextField cedulatxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaAdministrador.class.getName());

    private final Control controlador; // <-- guardamos el controlador
    private FormHandler estado;
};
