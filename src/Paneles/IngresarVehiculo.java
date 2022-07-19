/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import Conexion.DataBase;
import Conexion.DataManager;
import interfaz.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Tefy
 */
public class IngresarVehiculo extends javax.swing.JPanel {

    /**
     * Creates new form IngresarVehiculo
     */
     Principal home;
    int[] numeroPlaza = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    int plazasdisponibles = 10;
    
    public IngresarVehiculo(Principal principal) {
        initComponents();
        this.home = principal;
    }
    
     public void buscarPlaca(String placa) {
        DataManager manejador = new DataManager();
        ArrayList<Object> tablavehiculos = new ArrayList<>();
        tablavehiculos = manejador.resultado("SELECT placa FROM vehiculos WHERE placa = '" + placa + "';");

        ArrayList<Object> tablaservicioEntrada = new ArrayList<>();
        tablaservicioEntrada = manejador.resultado("SELECT hora_entrada FROM servicio WHERE placa_vehi = '" + placa + "';");
        if (!tablavehiculos.isEmpty() && tablaservicioEntrada.isEmpty()) {
            jbtnIngreso.setEnabled(true);
            this.ingresoVehiculo(placa);
        } else if (!tablavehiculos.isEmpty() && !tablaservicioEntrada.isEmpty()) {
            System.out.println("Ya ingresado");
        } else if (tablavehiculos.isEmpty() && tablaservicioEntrada.isEmpty()) {
            System.out.println("No registrado");
        }

    }
     
      public void ingresoVehiculo(String placa) {
        try {
            String cedula = null, placa1 = null;
            DataManager manejador = new DataManager();
            ResultSet datos = manejador.obtenerDatos("Select id_usuario, placa from vehiculos where placa='" + placa + "';");
            while (datos.next()) {
                cedula = datos.getString("id_usuario");
                placa1 = datos.getString("placa");
            }
            String horaEntrada = this.obtenerHora();
            String plaza = String.valueOf(this.asignarPlaza());

            DataBase cn = new DataBase();
            Connection cc = cn.conectar();

            String sql = "INSERT INTO servicio (plaza, id_usuario, placa_vehi, hora_entrada) VALUES (?,?,?,?);";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setString(1, plaza);
            psd.setString(2, cedula);
            psd.setString(3, placa1);
            psd.setString(4, horaEntrada);
//            psd.setString(5, "null");
//            psd.setString(6, "null");

            psd.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(IngresarVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
       public String obtenerHora() {
        LocalDateTime locaDate = LocalDateTime.now();
        int hours = locaDate.getHour();
        int minutes = locaDate.getMinute();
        int seconds = locaDate.getSecond();
        System.out.println("Hora actual : " + hours + ":" + minutes);
        return String.valueOf(hours + ":" + minutes);

    }
       
        public int asignarPlaza() {

        int plaza = 0;
        for (int i = 0; i <= numeroPlaza.length; i++) {
            if (numeroPlaza[i] != 0) {
                plaza = numeroPlaza[i];
                numeroPlaza[i] = 0;
                break;
            }
            break;
        }
        return plaza;
    }
        
         public void devolverPlaza(int plaza) {
        for (int i = 0; i < numeroPlaza.length; i++) {
            if (numeroPlaza[i] == 0) {
                numeroPlaza[i] = plaza;
                break;
            }
            break;
        }
    }
         
      public void salidaVehiculo(String placa) {
        try {
            DataManager manejador = new DataManager();
            String horaSalida = this.obtenerHora();
            ArrayList<Object> plaza = new ArrayList<>();
            plaza = manejador.resultado("SELECT plaza FROM servicio WHERE placa_vehi = '" + placa + "';");
            int plazaDevolver = Integer.valueOf(plaza.get(0).toString());
            this.devolverPlaza(plazaDevolver);

            DataBase cn = new DataBase();
            Connection cc = cn.conectar();

            String sql = "Update servicio set hora_salida= '" + horaSalida + "' where placa_vehi='" + placa + "'";
            manejador.ejecutarConsulta(sql);

        } catch (SQLException ex) {
            Logger.getLogger(IngresarVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
      
      public void calcularPrecio(String placa) {
        DataManager manejador = new DataManager();
        ArrayList<Object> horas = new ArrayList<>();
        horas = manejador.resultado("Select hora_entrada, hora_salida from servicio where placa_vehi='" + placa + "'");

        String[] Entrada = String.valueOf(horas.get(0)).split(":");
        String[] Salida = String.valueOf(horas.get(1)).split(":");

        int horaEntrada = Integer.valueOf(Entrada[0]);
        System.out.println(horaEntrada);
        int minutoEntrada = Integer.valueOf(Entrada[1]);
        System.out.println(minutoEntrada);

        int horaSalida = Integer.valueOf(Salida[0]);
        int minutoSalida = Integer.valueOf(Salida[1]);

        int minutosEntrada = (horaEntrada * 60) + minutoEntrada;
        System.out.println(minutosEntrada);
        int minutosSalida = (horaSalida * 60) + minutoSalida;
        System.out.println(minutosSalida);
        int minutosTrans = minutosSalida - minutosEntrada;
        System.out.println(minutosTrans);

        int numeroRestas = 0;
        int residuo = 0;
        if (minutosTrans > 60) {
            while (minutosTrans > 60) {
                minutosTrans = minutosTrans - 60;
                numeroRestas++;
            }
        }

        int precio = numeroRestas * 1;
        if (minutosTrans > 0) {
            precio = precio + 1;
        }
        System.out.println(precio);
        manejador.ejecutarConsulta("Update servicio set precio='" + precio + "'where placa_vehi='" + placa + "'");
        jlblPrecio.setText(String.valueOf(precio));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtPlacaIngreso = new javax.swing.JTextField();
        jbtnIngreso = new javax.swing.JButton();
        jbtnSalida = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jbtnPrecio = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jlblPrecio = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(890, 472));

        jLabel1.setText("Placa: ");

        jbtnIngreso.setText("Ingreso Vehículo");
        jbtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIngresoActionPerformed(evt);
            }
        });

        jbtnSalida.setText("Salida Vehiculo");
        jbtnSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalidaActionPerformed(evt);
            }
        });

        jLabel2.setText("Plazas disponibles: ");

        jbtnPrecio.setText("Precio");
        jbtnPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPrecioActionPerformed(evt);
            }
        });

        jLabel4.setText("Total a pagar: ");

        jlblPrecio.setToolTipText("");
        jlblPrecio.setPreferredSize(new java.awt.Dimension(26, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jlblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbtnIngreso)
                                .addGap(51, 51, 51)
                                .addComponent(jbtnSalida)
                                .addGap(35, 35, 35)
                                .addComponent(jbtnPrecio))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(62, 62, 62)
                                .addComponent(jtxtPlacaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPlacaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnIngreso)
                    .addComponent(jbtnSalida)
                    .addComponent(jbtnPrecio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jlblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIngresoActionPerformed
        // TODO add your handling code here:
        this.buscarPlaca(jtxtPlacaIngreso.getText());
        for (int i = 0; i < numeroPlaza.length; i++) {
            System.out.println(numeroPlaza[i]);
        }
        System.out.println("----------------------------------");

    }//GEN-LAST:event_jbtnIngresoActionPerformed

    private void jbtnSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalidaActionPerformed
        // TODO add your handling code here:
        this.salidaVehiculo(jtxtPlacaIngreso.getText());
        for (int i = 0; i < numeroPlaza.length; i++) {
            System.out.println(numeroPlaza[i]);
        }
    }//GEN-LAST:event_jbtnSalidaActionPerformed

    private void jbtnPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPrecioActionPerformed
        // TODO add your handling code here:
        this.calcularPrecio(jtxtPlacaIngreso.getText());
    }//GEN-LAST:event_jbtnPrecioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbtnIngreso;
    private javax.swing.JButton jbtnPrecio;
    private javax.swing.JButton jbtnSalida;
    private javax.swing.JLabel jlblPrecio;
    private javax.swing.JTextField jtxtPlacaIngreso;
    // End of variables declaration//GEN-END:variables
}
