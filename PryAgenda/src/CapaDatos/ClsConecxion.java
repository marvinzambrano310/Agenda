/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fundamentos DS
 */
public class ClsConecxion {
    private Connection _base;
    private Statement _tabla;  
    private ResultSet _registros;
    
    private String nom;
    private String dir;
    private String desc;
    
    public ClsConecxion (String dirbase){
        try{
        _base = DriverManager.getConnection("jdbc:ucanaccess://" + dirbase);
        _tabla = _base.createStatement(ResultSet.FETCH_UNKNOWN, ResultSet.TYPE_SCROLL_SENSITIVE);
        System.out.println("*** conexion exitosa ***");
        
        }catch (SQLException err){
              System.out.println("*** conexion errada ***" + err);
        }
    }

   

    public ResultSet registros() {
        return _registros;
    }
    
    public boolean consulta(String tabla) throws SQLException{
        boolean resp=false;
        String cadena= "SELECT * FROM " + tabla;
        _registros=null;
        _tabla.execute(cadena);
        _registros=_tabla.getResultSet();
        if(_registros!=null){
            resp=true;
        }
        return resp;
    }
    
    public boolean insetar(String tabla, String nombre, String telefono, String celular) throws SQLException{
        String values = "'"+nombre+"', '"+telefono+"', '"+celular+"'";
        String comando = "INSERT INTO " + tabla + "(Nombre,TelefonoFijo,Celular) VALUES("+values+")";
        int rowCount = _tabla.executeUpdate(comando);
        this.consulta("Agenda");
        return true;
    }
    public void actualizarC(String tabla, String nom, String telefono, String celular, int clave) throws SQLException{
      
        String comando ="UPDATE " + tabla + " SET "+"Nombre='"+nom+"', TelefonoFijo='"+telefono+"', Celular='"+celular+"' WHERE Id='"+clave+"'";
        _tabla.executeUpdate(comando);
        this.consulta("Agenda");
        
    }
    public void eliminarC(String tabla, int clave) throws SQLException{
        String cadena= "DELETE FROM "+tabla+" WHERE Id="+clave;
        _tabla.executeUpdate(cadena);
        this.consulta("Agenda");
    }  
    
    public void mostrartabla(String tabla) throws SQLException{
        String cadena= "SELECT * FROM " + tabla;
        _registros=null;
        _tabla.execute(cadena);
        _registros=_tabla.getResultSet();
    }
    
    public void busqueda(String tabla, String nombre) throws SQLException{
        String cadena="SELECT * FROM" + tabla + "WHERE nombre='"+nombre+"'";
        _tabla.executeQuery(cadena);
        this.consulta("Agenda");
    }
}
