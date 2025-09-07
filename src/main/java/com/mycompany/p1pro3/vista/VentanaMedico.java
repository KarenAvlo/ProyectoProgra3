package com.mycompany.p1pro3.vista;

import com.mycompany.p1pro3.Indicaciones;
import com.mycompany.p1pro3.Medicamento;
import com.mycompany.p1pro3.Medico;
import com.mycompany.p1pro3.Paciente;
import com.mycompany.p1pro3.Receta;
import com.mycompany.p1pro3.control.Control;
import com.mycompany.p1pro3.modelo.modelo;
import cr.ac.una.gui.FormHandler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
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
public class VentanaMedico extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaMedico.class.getName());

    public VentanaMedico(Control controlador, Medico med) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controlador = controlador;
        this.estado = new FormHandler();
        this.medicoActual = med;
        initComponents();
        recetaActual = new Receta();
        nuevasIndicaciones = new Indicaciones();
        this.setLocationRelativeTo(null);
        //configurarListeners();
        init();
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

        configurarSpinnersDashboard();
        cargarMedicamentosComboBox();
        //cargarGraficoRecetas();

        VentanaMedico.addChangeListener(e -> {
            int index = VentanaMedico.getSelectedIndex();

            // Solo nos interesa la pestaña 0
            if (index == 2) {
                actualizarTablaRecetas();
            }
            actualizarControles();
        });
        
        
        actualizarTablaRecetas();
        cambiarModoVista();
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
        if (estado.isViewing()) {
            estado.changeToAddMode();
            actualizarComponentes();
        }
    }

    private void cambiarModoEditar() {
        if (estado.isViewing() && estado.getModel() != null) {
            estado.changeToEditMode();
            actualizarComponentes();
        }
    }

    private void cambiarModoBuscar() {
        estado.changeToSearchMode();
        actualizarComponentes();
    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // ACTUALIZACIÓN DE COMPONENTES
    // -------------------------------------------------------------------------
    private void actualizarComponentes() {
        actualizarControles();
    }

    private void actualizarControles() {
        boolean hayPaciente = recetaActual.getPaciente() != null;
        boolean hayMedicamento = recetaActual.getIndicaciones() != null && !recetaActual.getIndicaciones().isEmpty();

        BotonBuscarPaciente.setEnabled(estado.isViewing()); // Puede buscar paciente en modo vista
        BotonAgregarMedicamento.setEnabled(estado.isViewing()); // Puede agregar medicamentos en modo vista

        BotonGuardarPresc.setEnabled(hayPaciente && hayMedicamento); // Guardar solo si hay cambios
        BotonEliminarPresc.setEnabled(!estado.isViewing() && estado.getModel() != null); // Eliminar solo si hay algo cargado

        BotonDetallesPresc.setEnabled(hayPaciente && hayMedicamento); // Ver detalles solo en modo vista

        BotonLimpiarPresc.setEnabled(estado.isViewing());

        // Texto fijo, ya no sobrecargamos el botón de medicamento
        BotonAgregarMedicamento.setText("Agregar Medicamento");
    }

    // -------------------------------------------------------------------------
    // OPERACIONES CRUD
    // -------------------------------------------------------------------------
    private void limpiarCampos() {
        estado.setModel(null);
        recetaActual = new Receta();
        mostrarNombre.setText("");
        actualizarTabla(recetaActual);
        actualizarControles();
    }

    private void indicarCambios() {
        estado.setModified(true);
        actualizarControles();
    }

    private void cancelarOperacion() {
        cambiarModoVista();
        actualizarControles();
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
        jScrollPane3 = new javax.swing.JScrollPane();
        mostrarNombre = new javax.swing.JTextPane();
        listMedicamentos = new javax.swing.JScrollPane();
        TablaMedicamentosReceta = new javax.swing.JTable();
        SpinnerFechaRetiro = new javax.swing.JSpinner();
        Control = new javax.swing.JPanel();
        BotonBuscarPaciente = new javax.swing.JButton();
        BotonAgregarMedicamento = new javax.swing.JButton();
        AjustePrescrib = new javax.swing.JPanel();
        BotonEliminarPresc = new javax.swing.JButton();
        BotonGuardarPresc = new javax.swing.JButton();
        BotonLimpiarPresc = new javax.swing.JButton();
        BotonDetallesPresc = new javax.swing.JButton();
        Dashboard = new javax.swing.JPanel();
        PanelDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        AñoInicio = new javax.swing.JSpinner();
        AñoFin = new javax.swing.JSpinner();
        DiaMesInicio = new javax.swing.JSpinner();
        DiaMesFin = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxMedicamentos = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicamentosGrafico = new javax.swing.JTable();
        BotonSeleccionFechas = new javax.swing.JButton();
        BotonAgregarMedicamentoComboBox = new javax.swing.JButton();
        PanelMedicamentos = new javax.swing.JPanel();
        PanelRecetas = new javax.swing.JPanel();
        HistoricoRecetas = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaRecetas = new javax.swing.JTable();
        BotonVerIndicaciones = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaIndicaciones = new javax.swing.JTable();
        Acercade = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        VentanaMedico.setToolTipText("");
        VentanaMedico.setMaximumSize(new java.awt.Dimension(800, 600));
        VentanaMedico.setMinimumSize(new java.awt.Dimension(800, 600));
        VentanaMedico.setName("Admisni"); // NOI18N
        VentanaMedico.setPreferredSize(new java.awt.Dimension(800, 600));
        VentanaMedico.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                VentanaMedicoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        RecetaMedica.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receta Médica", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        FechaRetiro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        FechaRetiro.setText("Fecha de retiro");

        NomPaciente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        NomPaciente.setText("Paciente ");

        mostrarNombre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        mostrarNombre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
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

        TablaMedicamentosReceta.setBorder(new javax.swing.border.MatteBorder(null));
        TablaMedicamentosReceta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Medicamento", "Presentación", "Cantidad", "Indicación", "Días"
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

        SpinnerFechaRetiro.setModel(new javax.swing.SpinnerDateModel());

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
                        .addComponent(SpinnerFechaRetiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(166, 166, 166)
                        .addComponent(NomPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RecetaMedicaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(listMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RecetaMedicaLayout.setVerticalGroup(
            RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecetaMedicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RecetaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(FechaRetiro)
                        .addComponent(NomPaciente))
                    .addComponent(SpinnerFechaRetiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(listMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        Control.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Control", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        AjustePrescrib.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajustar Prescribción", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        BotonEliminarPresc.setText("Eliminar Medicamento");
        BotonEliminarPresc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarPrescActionPerformed(evt);
            }
        });

        BotonGuardarPresc.setText("Guardar");
        BotonGuardarPresc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGuardarPrescActionPerformed(evt);
            }
        });

        BotonLimpiarPresc.setText("Limpiar ");
        BotonLimpiarPresc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonLimpiarPrescActionPerformed(evt);
            }
        });

        BotonDetallesPresc.setText("Detalles");
        BotonDetallesPresc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDetallesPrescActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AjustePrescribLayout = new javax.swing.GroupLayout(AjustePrescrib);
        AjustePrescrib.setLayout(AjustePrescribLayout);
        AjustePrescribLayout.setHorizontalGroup(
            AjustePrescribLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AjustePrescribLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(BotonGuardarPresc)
                .addGap(18, 18, 18)
                .addComponent(BotonEliminarPresc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
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
                .addGroup(PrescribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AjustePrescrib, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Control, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RecetaMedica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(144, Short.MAX_VALUE))
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
                .addContainerGap(215, Short.MAX_VALUE))
        );

        Control.getAccessibleContext().setAccessibleName("Prescribir");

        VentanaMedico.addTab("Prescribir", Prescribir);

        Dashboard.setEnabled(false);
        Dashboard.setMaximumSize(new java.awt.Dimension(767, 767));

        PanelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel1.setText("Desde");

        jLabel2.setText("Hasta");

        AñoInicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.YEAR));

        AñoFin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.YEAR));

        DiaMesInicio.setModel(new javax.swing.SpinnerDateModel());

        DiaMesFin.setModel(new javax.swing.SpinnerDateModel());

        jLabel3.setText("Año");

        jLabel4.setText("Día - Mes");

        jLabel8.setText("Medicamentos");

        jComboBoxMedicamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Gráfrico Medicamentos");
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
        jScrollPane1.setViewportView(tblMedicamentosGrafico);

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
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AñoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AñoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(DiaMesInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DiaMesFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(BotonSeleccionFechas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(BotonAgregarMedicamentoComboBox)
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        PanelDatosLayout.setVerticalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLayout.createSequentialGroup()
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxMedicamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonAgregarMedicamentoComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DiaMesInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AñoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(12, 12, 12)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AñoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(DiaMesFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(67, Short.MAX_VALUE))
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
                .addContainerGap(31, Short.MAX_VALUE))
        );

        VentanaMedico.addTab("Dashboard", Dashboard);

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
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(BotonVerIndicaciones)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
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

        javax.swing.GroupLayout HistoricoRecetasLayout = new javax.swing.GroupLayout(HistoricoRecetas);
        HistoricoRecetas.setLayout(HistoricoRecetasLayout);
        HistoricoRecetasLayout.setHorizontalGroup(
            HistoricoRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistoricoRecetasLayout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );
        HistoricoRecetasLayout.setVerticalGroup(
            HistoricoRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistoricoRecetasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        VentanaMedico.addTab("Historico ", HistoricoRecetas);

        jPanel9.setPreferredSize(new java.awt.Dimension(224, 300));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3.png"))); // NOI18N
        jLabel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Hospital Benjamín Nuñez");

        jLabel5.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sistema de Prescripción y Despacho de Medicamentos ");

        javax.swing.GroupLayout AcercadeLayout = new javax.swing.GroupLayout(Acercade);
        Acercade.setLayout(AcercadeLayout);
        AcercadeLayout.setHorizontalGroup(
            AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcercadeLayout.createSequentialGroup()
                .addGroup(AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AcercadeLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addGroup(AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AcercadeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        AcercadeLayout.setVerticalGroup(
            AcercadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcercadeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void mostrarNombreAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_mostrarNombreAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_mostrarNombreAncestorAdded

    private void BotonGuardarPrescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarPrescActionPerformed
        // TODO add your handling code here:
        guardarPrescripcion();
    }//GEN-LAST:event_BotonGuardarPrescActionPerformed

    private void BotonEliminarPrescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarPrescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonEliminarPrescActionPerformed

    private void BotonDetallesPrescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonDetallesPrescActionPerformed
        // TODO add your handling code here:
        mostrarDetalles();
    }//GEN-LAST:event_BotonDetallesPrescActionPerformed

    private void BotonLimpiarPrescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarPrescActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_BotonLimpiarPrescActionPerformed

    private void BotonSeleccionFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSeleccionFechasActionPerformed
        // TODO add your handling code here:
        confirmarSeleccionFechasPastel();
    }//GEN-LAST:event_BotonSeleccionFechasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        generarGraficoMedicamentos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BotonAgregarMedicamentoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarMedicamentoComboBoxActionPerformed
        // TODO add your handling code here:
        agregarMedicamentoSeleccionado();
    }//GEN-LAST:event_BotonAgregarMedicamentoComboBoxActionPerformed

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
    /*    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
     */

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
            Medico med = new Medico();
            try {
                modelo.cargarDatos(); // ✅ carga médicos, pacientes, farmaceutas, etc.
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VentanaMedico(controlador, med).setVisible(true);
        });
    }

    private void abrirBuscarPaciente() {
        buscarPaciente ventana = new buscarPaciente(controlador, this);
        ventana.setVisible(true);
    }

    public void pacienteSeleccionado(Paciente paciente) {
        if (paciente != null) {
            recetaActual.setPaciente(paciente);
            mostrarNombre.setText(paciente.getCedula() + " - " + paciente.getNombre());
            indicarCambios();
            cambiarModoEditar();
            actualizarControles();
        }
    }

    private void abrirBuscarMedicamento() {
        buscarMedicamento ventana = new buscarMedicamento(controlador, this);
        ventana.setVisible(true);
    }

    public void medicamentoSeleccionado(Medicamento medicamento, int can, String indica, int dura) {
        nuevasIndicaciones = new Indicaciones(medicamento, can, indica, dura);
        recetaActual.agregarIndicaciones(nuevasIndicaciones);
        actualizarTabla(recetaActual);
        indicarCambios();
        cambiarModoEditar();
        actualizarControles();

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
                i.getDuracion(),});
        }
    }

    private void guardarPrescripcion() {
        recetaActual.setMedico(medicoActual);
        recetaActual.setCodReceta("R0" + controlador.cantidadRecetas() + 1);
        recetaActual.setFechaEmision(LocalDate.now());
        Date fechaSeleccionada = (Date) SpinnerFechaRetiro.getValue();
        // Conversión a LocalDate
        LocalDate fechaRetiro = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        recetaActual.setFechaRetiro(fechaRetiro);
        recetaActual.setEstado("Confeccionada");
        if (controlador.agregarReceta(recetaActual)) {
            JOptionPane.showMessageDialog(this, "Receta guardada correctamente.");
        }
        indicarCambios();
        cambiarModoEditar();
        actualizarControles();
    }

    private void mostrarDetalles() {
        recetaActual.setMedico(medicoActual);
        recetaActual.setCodReceta("R0" + controlador.cantidadRecetas() + 1);
        recetaActual.setFechaEmision(LocalDate.now());
        Date fechaSeleccionada = (Date) SpinnerFechaRetiro.getValue();
        // Conversión a LocalDate
        LocalDate fechaRetiro = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        recetaActual.setFechaRetiro(fechaRetiro);
        recetaActual.setEstado("Confeccionada");

        if (recetaActual == null || recetaActual.getPaciente() == null || recetaActual.getIndicaciones().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay receta seleccionada o está incompleta.", "Detalles de la receta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        StringBuilder detalles = new StringBuilder();
        detalles.append("Código de Receta: ").append(recetaActual.getCodReceta()).append("\n");
        detalles.append("Paciente: ").append(recetaActual.getPaciente().getCedula())
                .append(" - ").append(recetaActual.getPaciente().getNombre()).append("\n");
        detalles.append("Médico: ").append(recetaActual.getMedico().getNombre()).append("\n");
        detalles.append("Fecha de emisión: ").append(recetaActual.getFechaEmision()).append("\n");
        detalles.append("Fecha de retiro: ").append(recetaActual.getFechaRetiro()).append("\n");
        detalles.append("Estado: ").append(recetaActual.getEstado()).append("\n\n");

        detalles.append("Medicamentos:\n");
        for (Indicaciones i : recetaActual.getIndicaciones()) {
            detalles.append("- ").append(i.getMedicamento().getNombre())
                    .append(" | Presentación: ").append(i.getMedicamento().getPresentacion())
                    .append(" | Cantidad: ").append(i.getCantidad())
                    .append(" | Indicaciones: ").append(i.getIndicaciones())
                    .append(" | Duración: ").append(i.getDuracion()).append(" días\n");
        }

        JOptionPane.showMessageDialog(this, detalles.toString(), "Detalles de la Receta", JOptionPane.INFORMATION_MESSAGE);
    }

    //DashBoard=========================================================
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
//====================Historico==================

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Acercade;
    private javax.swing.JPanel AjustePrescrib;
    private javax.swing.JSpinner AñoFin;
    private javax.swing.JSpinner AñoInicio;
    private javax.swing.JButton BotonAgregarMedicamento;
    private javax.swing.JButton BotonAgregarMedicamentoComboBox;
    private javax.swing.JButton BotonBuscarPaciente;
    private javax.swing.JButton BotonDetallesPresc;
    private javax.swing.JButton BotonEliminarPresc;
    private javax.swing.JButton BotonGuardarPresc;
    private javax.swing.JButton BotonLimpiarPresc;
    private javax.swing.JButton BotonSeleccionFechas;
    private javax.swing.JButton BotonVerIndicaciones;
    private javax.swing.JPanel Control;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JSpinner DiaMesFin;
    private javax.swing.JSpinner DiaMesInicio;
    private javax.swing.JLabel FechaRetiro;
    private javax.swing.JPanel HistoricoRecetas;
    private javax.swing.JLabel NomPaciente;
    private javax.swing.JPanel PanelDatos;
    private javax.swing.JPanel PanelMedicamentos;
    private javax.swing.JPanel PanelRecetas;
    private javax.swing.JPanel Prescribir;
    private javax.swing.JPanel RecetaMedica;
    private javax.swing.JSpinner SpinnerFechaRetiro;
    private javax.swing.JTable TablaIndicaciones;
    private javax.swing.JTable TablaMedicamentosReceta;
    private javax.swing.JTable TablaRecetas;
    private javax.swing.JTabbedPane VentanaMedico;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxMedicamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane listMedicamentos;
    private javax.swing.JTextPane mostrarNombre;
    private javax.swing.JTable tblMedicamentosGrafico;
    // End of variables declaration//GEN-END:variables

    private Paciente pacienteActual;
    private Medico medicoActual;
    private Medicamento medicamentoActual;
    private Receta recetaActual;  //instancia local
    private Indicaciones nuevasIndicaciones; //instancia local
    private final List<String> medicamentosSeleccionados = new ArrayList<>();
    private final Control controlador; // <-- guardamos el controlador
    private FormHandler estado;

};
