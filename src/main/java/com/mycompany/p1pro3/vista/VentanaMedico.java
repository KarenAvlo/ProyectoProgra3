package com.mycompany.p1pro3.vista;

import com.mycompany.p1pro3.Indicaciones;
import com.mycompany.p1pro3.Medicamento;
import com.mycompany.p1pro3.Paciente;
import com.mycompany.p1pro3.Receta;
import com.mycompany.p1pro3.control.Control;
import com.mycompany.p1pro3.modelo.modelo;
import cr.ac.una.gui.FormHandler;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nicolas ZH
 */
public class VentanaMedico extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaMedico.class.getName());

    public VentanaMedico(Control controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controlador = controlador;
        this.estado = new FormHandler();
        initComponents();
        recetaActual = new Receta();
        nuevasIndicaciones = new Indicaciones();
        //configurarListeners();
        init();
    }
    
   
    
    // ----------------------------
    // Métodos de acciones
    // ----------------------------
    

    
    private void guardarPrescripcion() {
        JOptionPane.showMessageDialog(this, "Prescripción guardada correctamente.");
    }

    private void eliminarPrescripcion() {
        JOptionPane.showMessageDialog(this, "Prescripción eliminada.");
    }

    private void mostrarDetallesPrescripcion() {
        JOptionPane.showMessageDialog(this, "Mostrando detalles de la prescripción...");
    }
    
  
    public void init() {
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
                // No usado para plain text
            }   
        };

        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SeleccionarFecha.removeAllItems();
        SeleccionarFecha.addItem(sdf.format(new Date())); //revisar eso del date, creo que George dijo que mejor no usar
        
        if(!recetaActual.getIndicaciones().isEmpty()){
            cargarTabla(recetaActual);
        }
        
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
        if (estado.isViewing()) {
            estado.changeToAddMode();
            actualizarComponentes();
        }
    }

    private void cambiarModoEditar() { //modo edicion
        if (estado.isViewing() && estado.getModel() != null) {
            estado.changeToEditMode();
            actualizarComponentes();

        }
    }

    private void cambiarModoBuscar() { //buscar
        estado.changeToSearchMode();
        actualizarComponentes();

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
        BotonBuscarPaciente.setEnabled(!estado.isViewing()); // Puede buscar paciente si no está solo viendo
        BotonAgregarMedicamento.setEnabled(!estado.isViewing()); // Puede agregar medicamentos en modo edición
        BotonGuardarPresc.setEnabled(!estado.isViewing() && estado.isModified()); // Guardar solo si hay cambios
        BotonEliminarPresc.setEnabled(estado.isViewing() && estado.getModel() != null); // Eliminar solo si hay algo cargado
        BotonDetallesPresc.setEnabled(estado.isViewing() && estado.getModel() != null); // Ver detalles solo en modo vista

        // Texto fijo, ya no sobrecargamos el botón de medicamento
        BotonAgregarMedicamento.setText("Agregar Medicamento");
    }

     private void actualizarCampos() {
        // En esta versión, no hay campos de texto dinámicos
    }
    
    private void indicarCambios() {
        estado.setModified(true);
        actualizarControles();
    }

    // -------------------------------------------------------------------------
    // OPERACIONES CRUD
    // -------------------------------------------------------------------------
    //----------------Medicos----------------
    //Limpia los campos de medico
    private void limpiarCampos() {
        estado.setModel(null);
        actualizarCampos();
    }


    private void cancelarOperacion() {
        cambiarModoVista();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        VentanaMedico = new javax.swing.JTabbedPane();
        Prescribir = new javax.swing.JPanel();
        RecetaMedica = new javax.swing.JPanel();
        FechaRetiro = new javax.swing.JLabel();
        NomPaciente = new javax.swing.JLabel();
        SeleccionarFecha = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        mostrarNombre = new javax.swing.JTextPane();
        listMedicamentos = new javax.swing.JScrollPane();
        TablaMedicamentosReceta = new javax.swing.JTable();
        Control = new javax.swing.JPanel();
        BotonBuscarPaciente = new javax.swing.JButton();
        BotonAgregarMedicamento = new javax.swing.JButton();
        AjustePrescrib = new javax.swing.JPanel();
        BotonEliminarPresc = new javax.swing.JButton();
        BotonGuardarPresc = new javax.swing.JButton();
        BotonLimpiarPresc = new javax.swing.JButton();
        BotonDetallesPresc = new javax.swing.JButton();
        Dashboard = new javax.swing.JPanel();
        PanelIngresaFarm = new javax.swing.JPanel();
        PanelBusquedaF = new javax.swing.JPanel();
        PanelBusquedaF1 = new javax.swing.JPanel();
        Historico = new javax.swing.JPanel();
        Acercade = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        VentanaMedico.setToolTipText("");
        VentanaMedico.setName("Admisni"); // NOI18N
        VentanaMedico.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                VentanaMedicoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        RecetaMedica.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Receta Médica", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        FechaRetiro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        FechaRetiro.setText("Fecha de retiro");

        NomPaciente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        NomPaciente.setText("Paciente ");

        SeleccionarFecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SeleccionarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeleccionarFechaActionPerformed(evt);
            }
        });

        mostrarNombre.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                mostrarNombreAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane3.setViewportView(mostrarNombre);

        TablaMedicamentosReceta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Medicamento", "Presentación", "Cant", "Indicación", "Días"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listMedicamentos.setViewportView(TablaMedicamentosReceta);

        javax.swing.GroupLayout RecetaMedicaLayout = new javax.swing.GroupLayout(RecetaMedica);
        RecetaMedica.setLayout(RecetaMedicaLayout);
        RecetaMedicaLayout.setHorizontalGroup(
            RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecetaMedicaLayout.createSequentialGroup()
                .addGroup(RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RecetaMedicaLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(FechaRetiro)
                        .addGap(18, 18, 18)
                        .addComponent(SeleccionarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(NomPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RecetaMedicaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(listMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        RecetaMedicaLayout.setVerticalGroup(
            RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecetaMedicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(FechaRetiro)
                        .addComponent(SeleccionarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NomPaciente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(listMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        Control.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Control", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        BotonBuscarPaciente.setText("Buscar Paciente");
        BotonBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarPacienteActionPerformed(evt);
            }
        });

        BotonAgregarMedicamento.setText("Agregar Medicamento");
        BotonAgregarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAgregarMedicamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ControlLayout = new javax.swing.GroupLayout(Control);
        Control.setLayout(ControlLayout);
        ControlLayout.setHorizontalGroup(
            ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(BotonBuscarPaciente)
                .addGap(150, 150, 150)
                .addComponent(BotonAgregarMedicamento)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        ControlLayout.setVerticalGroup(
            ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonBuscarPaciente)
                    .addComponent(BotonAgregarMedicamento))
                .addGap(66, 66, 66))
        );

        AjustePrescrib.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Ajustar Prescribción", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        BotonEliminarPresc.setText("Eliminar");

        BotonGuardarPresc.setText("Guardar");

        BotonLimpiarPresc.setText("Descartar Cambios");

        BotonDetallesPresc.setText("Detalles");

        javax.swing.GroupLayout AjustePrescribLayout = new javax.swing.GroupLayout(AjustePrescrib);
        AjustePrescrib.setLayout(AjustePrescribLayout);
        AjustePrescribLayout.setHorizontalGroup(
            AjustePrescribLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjustePrescribLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(BotonGuardarPresc)
                .addGap(18, 18, 18)
                .addComponent(BotonEliminarPresc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(BotonLimpiarPresc)
                .addGap(64, 64, 64)
                .addComponent(BotonDetallesPresc)
                .addGap(63, 63, 63))
        );
        AjustePrescribLayout.setVerticalGroup(
            AjustePrescribLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AjustePrescribLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(AjustePrescribLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonEliminarPresc)
                    .addComponent(BotonGuardarPresc)
                    .addComponent(BotonLimpiarPresc)
                    .addComponent(BotonDetallesPresc))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout PrescribirLayout = new javax.swing.GroupLayout(Prescribir);
        Prescribir.setLayout(PrescribirLayout);
        PrescribirLayout.setHorizontalGroup(
            PrescribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrescribirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PrescribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrescribirLayout.createSequentialGroup()
                        .addComponent(AjustePrescrib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PrescribirLayout.createSequentialGroup()
                        .addGroup(PrescribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RecetaMedica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PrescribirLayout.setVerticalGroup(
            PrescribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrescribirLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Control, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RecetaMedica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AjustePrescrib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        Control.getAccessibleContext().setAccessibleName("Prescribir");

        VentanaMedico.addTab("Prescribir", Prescribir);

        PanelIngresaFarm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout PanelIngresaFarmLayout = new javax.swing.GroupLayout(PanelIngresaFarm);
        PanelIngresaFarm.setLayout(PanelIngresaFarmLayout);
        PanelIngresaFarmLayout.setHorizontalGroup(
            PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
        );
        PanelIngresaFarmLayout.setVerticalGroup(
            PanelIngresaFarmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        PanelBusquedaF.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Medicamentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout PanelBusquedaFLayout = new javax.swing.GroupLayout(PanelBusquedaF);
        PanelBusquedaF.setLayout(PanelBusquedaFLayout);
        PanelBusquedaFLayout.setHorizontalGroup(
            PanelBusquedaFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelBusquedaFLayout.setVerticalGroup(
            PanelBusquedaFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        PanelBusquedaF1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Recetas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout PanelBusquedaF1Layout = new javax.swing.GroupLayout(PanelBusquedaF1);
        PanelBusquedaF1.setLayout(PanelBusquedaF1Layout);
        PanelBusquedaF1Layout.setHorizontalGroup(
            PanelBusquedaF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        PanelBusquedaF1Layout.setVerticalGroup(
            PanelBusquedaF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelIngresaFarm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(DashboardLayout.createSequentialGroup()
                        .addComponent(PanelBusquedaF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelBusquedaF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(PanelIngresaFarm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelBusquedaF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelBusquedaF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        VentanaMedico.addTab("Dashboard", Dashboard);

        javax.swing.GroupLayout HistoricoLayout = new javax.swing.GroupLayout(Historico);
        Historico.setLayout(HistoricoLayout);
        HistoricoLayout.setHorizontalGroup(
            HistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 776, Short.MAX_VALUE)
        );
        HistoricoLayout.setVerticalGroup(
            HistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 637, Short.MAX_VALUE)
        );

        VentanaMedico.addTab("Historico", Historico);

        jPanel9.setPreferredSize(new java.awt.Dimension(224, 300));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
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

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/2.jpg"))); // NOI18N

        javax.swing.GroupLayout AcercadeLayout = new javax.swing.GroupLayout(Acercade);
        Acercade.setLayout(AcercadeLayout);
        AcercadeLayout.setHorizontalGroup(
            AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcercadeLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AcercadeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        AcercadeLayout.setVerticalGroup(
            AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcercadeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        VentanaMedico.addTab("Acerca de", Acercade);

        getContentPane().add(VentanaMedico, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonAgregarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarMedicamentoActionPerformed
        // TODO add your handling code here:
        abrirBuscarMedicamento();
    }//GEN-LAST:event_BotonAgregarMedicamentoActionPerformed

    private void VentanaMedicoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_VentanaMedicoAncestorAdded
        // TODO add your handling code here:
        
    }//GEN-LAST:event_VentanaMedicoAncestorAdded

    private void BotonBuscarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarPacienteActionPerformed
        // TODO add your handling code here:
        abrirBuscarPaciente();
    }//GEN-LAST:event_BotonBuscarPacienteActionPerformed

    private void SeleccionarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionarFechaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SeleccionarFechaActionPerformed

    private void mostrarNombreAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_mostrarNombreAncestorAdded
        // TODO add your handling code here:
        
    }//GEN-LAST:event_mostrarNombreAncestorAdded
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          


    
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
            new VentanaMedico(controlador).setVisible(true);
        });
    }

    private void abrirBuscarPaciente() {
        buscarPaciente ventana = new buscarPaciente(controlador, this);
        ventana.setVisible(true);
    }
    
    public void pacienteSeleccionado(Paciente paciente) {
        if (paciente != null) {
            // Guardar la referencia al paciente actual
            recetaActual.setPaciente(paciente);

            // Mostrarlo en el campo de texto correspondiente
            mostrarNombre.setText(
                paciente.getCedula() + " - " + paciente.getNombre()
            );
        }
    }


    private void abrirBuscarMedicamento() {
        buscarMedicamento ventana = new buscarMedicamento(controlador, this);
        ventana.setVisible(true);
    }
    
    public void medicamentoSeleccionado(Medicamento medicamento, int can, String indica, int dura){
        nuevasIndicaciones = new Indicaciones(medicamento, can, indica, dura);
        recetaActual.agregarIndicaciones(nuevasIndicaciones);
    }
    
    private void cargarTabla(Receta receta) {
        DefaultTableModel model = (DefaultTableModel) TablaMedicamentosReceta.getModel();
        model.setRowCount(0);
        
        for (Indicaciones i : receta.getIndicaciones()) {
            model.addRow(new Object[]{
                i.getMedicamento().getNombre(),
                i.getMedicamento().getPresentacion(),
                i.getCantidad(),
                i.getIndicaciones(),
                i.getDuracion(),
            });
        }
    }
    
    private void actualizarTabla(Receta receta) {
        DefaultTableModel model = (DefaultTableModel) TablaMedicamentosReceta.getModel();
        model.setRowCount(0); // Limpiar tabla
        for (Indicaciones i : receta.getIndicaciones()) {
            model.addRow(new Object[]{
                i.getMedicamento().getNombre(),
                i.getMedicamento().getPresentacion(),
                i.getCantidad(),
                i.getIndicaciones(),
                i.getDuracion(),
            });
        }
    }
    

















    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Acercade;
    private javax.swing.JPanel AjustePrescrib;
    private javax.swing.JButton BotonAgregarMedicamento;
    private javax.swing.JButton BotonBuscarPaciente;
    private javax.swing.JButton BotonDetallesPresc;
    private javax.swing.JButton BotonEliminarPresc;
    private javax.swing.JButton BotonGuardarPresc;
    private javax.swing.JButton BotonLimpiarPresc;
    private javax.swing.JPanel Control;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JLabel FechaRetiro;
    private javax.swing.JPanel Historico;
    private javax.swing.JLabel NomPaciente;
    private javax.swing.JPanel PanelBusquedaF;
    private javax.swing.JPanel PanelBusquedaF1;
    private javax.swing.JPanel PanelIngresaFarm;
    private javax.swing.JPanel Prescribir;
    private javax.swing.JPanel RecetaMedica;
    private javax.swing.JComboBox<String> SeleccionarFecha;
    private javax.swing.JTable TablaMedicamentosReceta;
    private javax.swing.JTabbedPane VentanaMedico;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane listMedicamentos;
    private javax.swing.JTextPane mostrarNombre;
    // End of variables declaration//GEN-END:variables

   

    private Paciente pacienteActual;
    private Medicamento medicamentoActual;
    private Receta recetaActual;  //instancia local
    private Indicaciones nuevasIndicaciones; //instancia local
    private final Control controlador; // <-- guardamos el controlador
    private FormHandler estado;
    
};
