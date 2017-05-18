/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Core.Bot;
import Objetos.Ciudad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADRIAN
 */
public class JPRecursos extends javax.swing.JPanel {

    /**
     * Creates new form JPRecursos
     */
    public JPRecursos() {
        initComponents();
        this.setBounds(0,0,700,370);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtaRecursos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbtnActualizarRec = new javax.swing.JButton();

        setBackground(new java.awt.Color(58, 56, 77));
        setMinimumSize(new java.awt.Dimension(650, 352));
        setPreferredSize(new java.awt.Dimension(688, 352));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtaRecursos.setBackground(new java.awt.Color(58, 56, 77));
        jtaRecursos.setForeground(new java.awt.Color(255, 255, 255));
        jtaRecursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Ciudad", "Madera", "Vino", "Marmol", "Cristal", "Azufre"
            }
        ));
        jtaRecursos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jtaRecursos);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 480, 320));

        jPanel1.setBackground(new java.awt.Color(38, 40, 55));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Recursos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jbtnActualizarRec.setBackground(new java.awt.Color(102, 0, 51));
        jbtnActualizarRec.setText("Actualizar");
        jbtnActualizarRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnActualizarRecActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnActualizarRec, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 140, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 380));
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnActualizarRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnActualizarRecActionPerformed
        DefaultTableModel modelo;
        ArrayList<Ciudad> misCiudades = new ArrayList();
        modelo = (DefaultTableModel) jtaRecursos.getModel();
        modelo.setRowCount(0);
        
        try { //Cargamos los datos de las ciudades le de BD
            misCiudades = Ciudad.ListCitiesPlayerBD();
            for (Ciudad ciudad : misCiudades) {
                modelo.addRow(new Object[]{(String) ciudad.getNombreCiu(), (int) ciudad.getMaderaCiu(), (int) ciudad.getVinoCiu(), (int) ciudad.getMarmolCiu(), (int) ciudad.getCristalCiu(), (int) ciudad.getAzufreCiu()}); //Lo agregamos a la caja
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JPRecursos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbtnActualizarRecActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnActualizarRec;
    private javax.swing.JTable jtaRecursos;
    // End of variables declaration//GEN-END:variables
}
