/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Core.CoreBD;
import Interfaz.JFPrincipal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ADRIAN
 */
public class Edificio {
    
    WebDriver driverClassEdi = JFPrincipal.driverFull;
    //Metodos para interactuar con la BD
    private static CoreBD bd = new CoreBD(); //Variable usada para interactuar con la BD
    private static String sql = null; //Variable usada para contener los mensajes sql para interactuar con la BD
    private static ResultSet rs = null; //Variable que almacena los resultados devueltos por la BD

    
    private int idEdificio; //Id usado para saber la prioridad en la cola de construccion
    private String nombreEdificio; //Nombre del edificio (intendencia, cuartel, academia....)
    private int idCiudad; //Id que asocia el edificio que queremos subir y la ciudad a la que pertenece
    private String posIkaEdi; //Nombre especifico de la posicion del edificio en la web de ikariam
    
    
    //Metodos 
    
    /*EDIFICIOS*/
    public void eliminarEdificioBD(int nCiudad, String nombreEdi) throws SQLException{
        sql = "Delete from edificios where id_ciu = " + nCiudad + " and nombre_edi = '" + nombreEdi + "' LIMIT 1";
        bd.actualizarTabla(sql);
    }
    


    //Getters and setters
    public int getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(int idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getPosIkaEdi() {
        return posIkaEdi;
    }

    public void setPosIkaEdi(String posIkaEdi) {
        this.posIkaEdi = posIkaEdi;
    }

    //Constructores
    /**
     * Constructor usado para crear un nuevo edificio en la cola de construccion
     * (Base de datos)
     *
     * @param idEdificio Id usado para saber la prioridad en la cola de
     * construccion
     * @param nombreEdificio Nombre del edificio (intendencia, cuartel,
     * academia....)
     * @param idCiudad Id que asocia el edificio que queremos subir y la ciudad
     * a la que pertenece
     * @param posIkaEdi Nombre especifico de la posicion del edificio en la web
     * de ikariam
     */
    public Edificio(int idEdificio, String nombreEdificio, int idCiudad, String posIkaEdi) {
        this.idEdificio = idEdificio;
        this.nombreEdificio = nombreEdificio;
        this.idCiudad = idCiudad;
        this.posIkaEdi = posIkaEdi;
    }

    public Edificio(String nombreEdificio, int idCiudad, String posIkaEdi) {
        this.nombreEdificio = nombreEdificio;
        this.idCiudad = idCiudad;
        this.posIkaEdi = posIkaEdi;
    }
    

    public Edificio() {
    }
}
