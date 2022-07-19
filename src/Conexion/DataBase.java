/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tefy
 */
public class DataBase {

    private String url;
    private String driver;
    private String user;
    private String password;
    private Connection conexion;

    public DataBase() {
        this.driver = "jdbc:mysql";
        this.url = "//localhost/parqueadero";
        this.user = "root";
        this.password = "";
    }

    public Connection conectar() throws SQLException {
        try {
            this.conexion = DriverManager.getConnection(this.driver + ":" + this.url, this.user, this.password);
            if(!this.conexion.isClosed()){
                System.out.println("Conectado");
            }
        } catch (SQLException ex) {
            System.out.println("Error");
        }
        return conexion;
    }
    
    public void cerrar(){
        try{
            if(!this.conexion.isClosed()){
                this.conexion.close();
            }
        }catch(SQLException ex){
            
        }
    }
    
    public Connection getConexion(){
        return conexion; 
    }

}
