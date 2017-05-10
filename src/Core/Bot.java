/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Interfaz.JFPrincipal;
import Objetos.Ciudad;
import Objetos.Edificio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author ADRIAN
 */
public class Bot {
    WebDriver driverBot = JFPrincipal.driverFull;
    //Metodos para interactuar con la BD
    private static CoreBD bd = new CoreBD(); //Variable usada para interactuar con la BD
    private static String sql = null; //Variable usada para contener los mensajes sql para interactuar con la BD
    private static ResultSet rs = null; //Variable que almacena los resultados devueltos por la BD


    /*EDIFICIOS*/
    
    /**
     * Guarda un edificio en la base de datos
     *
     * @param miEdificio Objeto del tipo edificio relleno
     * @throws SQLException Error al guardar el edificio en la base de datos
     */
    public void stockBuild(Edificio miEdificio) throws SQLException {
        sql = "insert into edificios values("
                + "null, '"
                + miEdificio.getNombreEdificio() + "', "
                + miEdificio.getIdCiudad() + ", '"
                + miEdificio.getPosIkaEdi() + "')";
        bd.actualizarTabla(sql);
    }


    /**
     * Devuelve la lista completa de los edificios que hay en la lista de construccion
     * @param islandId
     * @return
     * @throws SQLException 
     */
    public ArrayList<Edificio> ListBuilds4Island(int islandId) throws SQLException {
        ArrayList<Edificio> misEdificios = new ArrayList();
        Edificio miEdificio;
        sql = "select * from edificios where id_edi = " + islandId;
        rs = bd.consultarTabla(sql);
        while (rs.next()) {
            miEdificio = new Edificio();
            miEdificio.setIdEdificio(rs.getInt("id_edi"));
            miEdificio.setNombreEdificio(rs.getString("nombre_edi"));
            miEdificio.setIdCiudad(rs.getInt("id_ciudad"));
            miEdificio.setPosIkaEdi(rs.getString("pos_ika_edi"));
            misEdificios.add(miEdificio);
        }
        return misEdificios;
    }

    /*CIUDADES*/
    
    /**
     * Devuelve el numero de ciudades que hay guardadas en el servidor
     * @return numero de ciudades en el servidor
     * @throws SQLException Error al ejecutar el sql de recuento de ciudades 
     */
    public int askCitiesBD() throws SQLException{
        int nCities = 100; //Numero de ciudades almacenadas en el servidor
        sql = "SELECT count(id_ciu) AS 'ciudades' FROM ciudades";
        rs = bd.consultarTabla(sql);
        while(rs.next()){
            nCities = rs.getInt("ciudades");
        }
        return nCities; //En caso de dar 100 significa que algo falla.
    }
    
    public int askCitiesIka(){
        int nCities;
        //WebElement desplegableIslas = driverBot.findElement(By.id("js_citySelectContainer")); //Seleccionamos el desplegable de las islas
        //desplegableIslas.click();
        List<WebElement> islas = driverBot.findElements(By.className("ownCity"));
        nCities = (islas.size() - 1);
        return nCities;
    }
    
    /**
     * Guarda una ciudad en la base de datos
     *
     * @param miCiudad Ciudad a guardar (con los datos rellenos)
     * @throws SQLException Error al insertar la ciudad en la BD.
     */
    public void stockCity(Ciudad miCiudad) throws SQLException {
        sql = "insert into ciudades values("
                + "null, '"
                + miCiudad.getNombreCiu() + ", "
                + miCiudad.getCoordXCiu() + ", "
                + miCiudad.getCoordYCiu() + ", "
                + miCiudad.getMaderaCiu() + ", "
                + miCiudad.getVinoCiu() + ", "
                + miCiudad.getMarmolCiu() + ", "
                + miCiudad.getCristalCiu() + ", "
                + miCiudad.getAzufreCiu() + ")";
        bd.actualizarTabla(sql);
    }

    /**
     * Metodo que devuelve la lista de ciudades del jugador con toda su informacion
     * @return ArrayList del tipo ciudad con toda la informacion completa
     * @throws SQLException Error al recuperar la informacion de las ciudades
     */
    public ArrayList<Ciudad> ListCitiesPlayer() throws SQLException{
        ArrayList<Ciudad> misCiudades = new ArrayList();
        Ciudad miCiudad;
        sql = "select * from ciudades";
        rs = bd.consultarTabla(sql);
        while(rs.next()){
            miCiudad = new Ciudad();
            miCiudad.setIdCiudad(rs.getInt("id_ciu"));
            miCiudad.setNombreCiu(rs.getString("nombre_ciu"));
            miCiudad.setCoordXCiu(rs.getInt("coord_x_ciu"));
            miCiudad.setCoordYCiu(rs.getInt("coord_y_ciu"));
            miCiudad.setMaderaCiu(rs.getInt("madera_ciu"));
            miCiudad.setVinoCiu(rs.getInt("vino_ciu"));
            miCiudad.setMarmolCiu(rs.getInt("marmol_ciu"));
            miCiudad.setCristalCiu(rs.getInt("cristal_ciu"));
            miCiudad.setCristalCiu(rs.getInt("azufre_ciu"));
            misCiudades.add(miCiudad);
        }
        return misCiudades;
    } 
}
