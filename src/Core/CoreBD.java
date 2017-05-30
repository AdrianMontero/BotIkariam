/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author adria
 */
public class CoreBD {

    Connection conexion = null;
    Statement stmt = null;
    String sql = null;
    ResultSet rs = null;

    /**
     * Conecta el proyecto con la BD
     */
    public void conectarSqlite() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://greenmonster.servehttp.com:3306/ikariam", "bot", "grmtoor");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CoreBD.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problema para encontar la BBDD");
        } catch (SQLException ex) {
            Logger.getLogger(CoreBD.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Con la BBDD");
        }
    }

    /**
     * Cierra la conexion con la BD
     *
     * @throws SQLException Error al cerrar la conexion con la BD
     */
    public void desconectarBBDD() throws SQLException {
        conexion.close();
    }

    /**
     * Realiza una consulta a la BD
     *
     * @param _sql Sentencia con la que queremos realizar la consulta
     * @return Devuelve un Resultset con la respuesta
     * @throws SQLException Error al conectar con la BD
     */
    public ResultSet consultarTabla(String _sql) throws SQLException {
        if (conexion == null) {
            conectarSqlite();
        }
        stmt = conexion.createStatement();
        //Empezamos a trabajar con la BBDD
        rs = stmt.executeQuery(_sql);

        return rs;

    }

    /**
     * Actualiza la tabla de la bd
     *
     * @param _sql sentencia que queremos usar para actualizar la BD
     * @throws SQLException Error al conectar con la BD
     */
    public void actualizarTabla(String _sql) throws SQLException {
        if (conexion == null) {
            conectarSqlite();
        }
        stmt = conexion.createStatement();
        //Empezamos a trabajar con la BBDD
        stmt.executeUpdate(_sql);
    }
}
