package com.mycompany.p1pro3.vista;

import com.mycompany.p1pro3.Paciente;
import com.mycompany.p1pro3.control.Control;
import com.mycompany.p1pro3.modelo.modelo;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class buscarPaciente extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(buscarPaciente.class.getName());

    /**
     * Creates new form buscarPaciente
     */
    public buscarPaciente(Control control, VentanaMedico ventanaMedico) {
        if (control == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.control = control;
        this.ventanaMedico = ventanaMedico;
        initComponents();
        cargarDatosTabla();
        configurarListeners();
    }
    
    private void cargarDatosTabla() {
        modelo modelo = control.getModelo();
        if (modelo == null) {
            JOptionPane.showMessageDialog(this, "Error: El modelo no está disponible.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            listaPacientes = modelo.getModelo().getGestorP().getListaPacientes();
            actualizarTabla(listaPacientes);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar los datos de pacientes", e);
            JOptionPane.showMessageDialog(this, "Error al cargar los pacientes: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarTabla(List<Paciente> pacientes) {
        DefaultTableModel model = (DefaultTableModel) tblPacientes.getModel();
        model.setRowCount(0); // Limpiar tabla
        for (Paciente paciente : pacientes) {
            model.addRow(new Object[]{
                paciente.getCedula(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getFechaNacimiento()
            });
        }
    }
    
    /**
     * Configura los listeners para los botones y el campo de texto de búsqueda.
     */
    private void configurarListeners() {
        // Listener para el campo de búsqueda (funcionalidad de filtro en tiempo real)
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarPacientes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarPacientes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No usado para plain text
            }
        });

        // Listener para el botón OK
        BotonOK.addActionListener((ActionEvent e) -> {
            int filaSeleccionada = tblPacientes.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String cedula = (String) tblPacientes.getValueAt(filaSeleccionada, 0);
                Paciente pacienteSeleccionado = control.getHospital().getGestorP().buscarPorCedula(cedula);
                if (pacienteSeleccionado != null) {
                    ventanaMedico.pacienteSeleccionado(pacienteSeleccionado);
                    this.dispose(); // Cerrar la ventana
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el paciente en el modelo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un paciente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Listener para el botón Cancelar
        BotonCancelar.addActionListener((ActionEvent e) -> {
            this.dispose(); // Cerrar la ventana
        });
    }
    
    private void filtrarPacientes() {
        String texto = txtBuscar.getText().toLowerCase().trim();
        String filtroPor = (String) jComboBox1.getSelectedItem();
        
        if (texto.isEmpty()) {
            actualizarTabla(listaPacientes); // Mostrar todos si el campo está vacío
            return;
        }

        List<Paciente> pacientesFiltrados = listaPacientes.stream()
            .filter(paciente -> {
                if ("Nombre".equals(filtroPor)) {
                    return paciente.getNombre().toLowerCase().contains(texto);
                } else if ("Cedula".equals(filtroPor)) {
                    return paciente.getCedula().toLowerCase().contains(texto);
                }
                return false;
            })
            .collect(Collectors.toList());
            
        actualizarTabla(pacientesFiltrados);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        BotonOK = new javax.swing.JButton();
        BotonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setText("Filtrar por:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Cedula", " " }));

        txtBuscar.setText("Escribir...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel1.getAccessibleContext().setAccessibleName("TextFiltrarPor");

        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Teléfono", "Fec. Nacimiento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPacientes);

        BotonOK.setText("OK");

        BotonCancelar.setText("Cancelar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonOK)
                .addGap(18, 18, 18)
                .addComponent(BotonCancelar)
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonOK)
                    .addComponent(BotonCancelar))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
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
            VentanaMedico ventanaMedico = new VentanaMedico(controlador);
            try {
                modelo.cargarDatos(); // ✅ carga médicos, pacientes, farmaceutas, etc.
            } catch (Exception e) {
                e.printStackTrace();
            }
            new buscarPaciente(controlador, ventanaMedico).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonCancelar;
    private javax.swing.JButton BotonOK;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPacientes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
    
    private final Control control;
    private final VentanaMedico ventanaMedico;
    private List<Paciente> listaPacientes;
}
