/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pc
 */
public class DataManager extends DataBase {

    private Connection conexion;
    private Statement consulta;
    private ResultSet resultados;

    public DataManager() {
        super();
        this.conexion = super.getConexion();
    }

    public void ejecutarConsulta(String sql) {
        try {
            iniciar();
            this.conexion = super.getConexion();
            consulta = conexion.createStatement();
            consulta.execute(sql);
            terminar();
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }

    public ResultSet obtenerDatos(String sql) {
        try {
            iniciar();
            this.consulta = conexion.createStatement();
            this.resultados = this.consulta.executeQuery(sql);
            return this.resultados;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return null;
        }

    }

    private void iniciar() {
        try {
            super.conectar();
            this.conexion = super.getConexion();
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void terminar() {
        super.cerrar();
        this.conexion = super.getConexion();
    }

    public ArrayList<Object> resultado(String sql) {
        try {
            ArrayList<Object> retorno = new ArrayList<>();
            iniciar();
            this.consulta = this.conexion.createStatement();
            this.resultados = this.consulta.executeQuery(sql);
            if (this.resultados.next()) {
                for (int i = 1; i <= (this.resultados.getMetaData().getColumnCount()); i++) {
                    retorno.add(this.resultados.getObject(i));
                }
            }
            terminar();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public void cerrar() {
        try {
            if (!this.conexion.isClosed()) {
                this.conexion.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
