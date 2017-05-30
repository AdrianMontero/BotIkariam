/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Interfaz.JFPrincipal;
import Objetos.Ciudad;
import Objetos.Construccion;
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

    Ciudad gestorCiudad = new Ciudad();
    Edificio gestorEdificio = new Edificio();

    WebDriver driverBot = JFPrincipal.driverFull;
    //Metodos para interactuar con la BD
    private static CoreBD bd = new CoreBD(); //Variable usada para interactuar con la BD
    private static String sql = null; //Variable usada para contener los mensajes sql para interactuar con la BD
    private static ResultSet rs = null; //Variable que almacena los resultados devueltos por la BD

    /**
     * Te desplazas a la isla correspondiente al nCiudad...
     *
     * @param nCiudadEnDesplegable Numero de la isla a la que queremos ir
     * @throws InterruptedException Error durante la espera
     */
    public void moverseA(int nCiudadEnDesplegable) throws InterruptedException {
        WebElement desplegableIslas;
        List<WebElement> islas;
        desplegableIslas = driverBot.findElement(By.id("js_citySelectContainer"));
        desplegableIslas.click();
        Thread.sleep(1000);
        islas = driverBot.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu (necesario repetirlo para que no se quede bug)
        Thread.sleep(1000);
        islas.get(nCiudadEnDesplegable).click();
        Thread.sleep(1000);
    }

    /**
     * Muestra la ciudad de la poscion en la que nos posicionamos
     */
    public void mostrarCiudad() {
        //Nos posicionamos en la ventana de isla (es donde esta dota la informacion que necesitamos)
        WebElement btnIsla = driverBot.findElement(By.className("viewCity"));
        btnIsla.click();
    }

    /**
     * Muestra la isla de la cuidad en la que nos ocupamos
     */
    public void mostrarIsla() {
        //Nos posicionamos en la ventana de isla (es donde esta dota la informacion que necesitamos)
        WebElement btnIsla = driverBot.findElement(By.className("viewIsland"));
        btnIsla.click();
    }

    /**
     * Muestra todos los edificios pertenecientes a una ciudad determinada
     *
     * @param nCiudad Ciudad de la cual queremos obtener los edificios
     * @return ArrayList del tipo Dedificio la con toda la informacion referente
     * a estos
     * @throws SQLException Error al conectar con la BD
     */
    public ArrayList<Edificio> getEdificiosPorCiudad(int nCiudad) throws SQLException {
        ArrayList<Edificio> misEdificios = new ArrayList();
        Edificio miEdificio;

        sql = "select * from edificios where id_ciu = " + nCiudad + " order by 'id_edi'";
        rs = bd.consultarTabla(sql);

        while (rs.next()) {
            miEdificio = new Edificio();
            miEdificio.setIdEdificio(rs.getInt("id_edi"));
            miEdificio.setNombreEdificio(rs.getString("nombre_edi"));
            miEdificio.setIdCiudad(rs.getInt("id_ciu"));
            miEdificio.setPosIkaEdi(rs.getString("pos_ika_edi"));
            misEdificios.add(miEdificio);
        }
        return misEdificios;
    }

    /**
     * Metodo el cual devuelve todos los edificios de la base de datos
     *
     * @return ArrayList del tipo edificio
     * @throws SQLException Error al conectar con la BD
     */
    public ArrayList<Edificio> getTolosLosEdificios() throws SQLException {
        ArrayList<Edificio> misEdificios = new ArrayList();
        Edificio miEdificio;

        sql = "select * from edificios order by 'id_edi'";
        rs = bd.consultarTabla(sql);

        while (rs.next()) {
            miEdificio = new Edificio();
            miEdificio.setIdEdificio(rs.getInt("id_edi"));
            miEdificio.setNombreEdificio(rs.getString("nombre_edi"));
            miEdificio.setIdCiudad(rs.getInt("id_ciu"));
            miEdificio.setPosIkaEdi(rs.getString("pos_ika_edi"));
            misEdificios.add(miEdificio);
        }
        return misEdificios;
    }

    /**
     * Metodo que devuelve el listado de construcciones de una ciudad
     *
     * @param nCiudad Ciudad de la cual queremos la lista de construcciones
     * @return Arraylist de tipo construccion
     * @throws SQLException Error al conectar con la BD
     */
    public ArrayList<Construccion> getConstruccionesPorCiudad(int nCiudad) throws SQLException {
        ArrayList<Construccion> misConstrucciones = new ArrayList();
        Construccion miConstruccion;

        sql = "select * from construcciones where id_ciu = " + nCiudad + " order by 'id_con'";
        rs = bd.consultarTabla(sql);

        while (rs.next()) {
            miConstruccion = new Construccion();
            miConstruccion.setIdCiudad(rs.getInt("id_ciu"));
            miConstruccion.setIdConstruccion(rs.getInt("id_con"));
            miConstruccion.setNombreConstruccion(rs.getString("nombre_con"));
            miConstruccion.setPosIkaCon(rs.getString("pos_ika_con"));
            misConstrucciones.add(miConstruccion);
        }

        return misConstrucciones;
    }

    /**
     * Metodo encargado de guardar la lista de construcciones de una ciudad
     *
     * @param nCiudad ciudad a la que queremos asociar las construcciones
     * @throws InterruptedException Error con el hilo
     * @throws SQLException Error al conectar con la BD
     */
    public void setConstruccionesPorCiudad(int nCiudad) throws InterruptedException, SQLException {
        String posIkaCon;
        moverseA(nCiudad);
        mostrarCiudad();
        for (int i = 0; i < 18; i++) {
            Construccion miConstruccion = new Construccion();
            posIkaCon = "js_CityPosition" + i + "Link";
            WebElement construccionCont = driverBot.findElement(By.id(posIkaCon));

            miConstruccion.setIdCiudad(nCiudad);
            miConstruccion.setNombreConstruccion(construccionCont.getAttribute("title"));
            miConstruccion.setPosIkaCon(posIkaCon);

            setConstruccionesBD(miConstruccion);
        }
    }

    /**
     * Guarda un edificio en la base de datos (uso desde toda la web)
     *
     * @param miEdificio Objeto del tipo edificio relleno
     * @throws SQLException Error al guardar el edificio en la base de datos
     */
    public void setEdificioBD(Edificio miEdificio) throws SQLException {
        sql = "insert into edificios values("
                + "null, '"
                + miEdificio.getNombreEdificio() + "', "
                + miEdificio.getIdCiudad() + ", '"
                + miEdificio.getPosIkaEdi() + "')";
        bd.actualizarTabla(sql);
    }

    /**
     * Guarda un edificio en la base de datos (uso desde toda la web)
     *
     * @param miConstruccion Parametro usado para recoger los datos a guardar en
     * la BD
     * @throws SQLException Error al guardar el edificio en la base de datos
     */
    public void setConstruccionesBD(Construccion miConstruccion) throws SQLException {
        sql = "insert into construcciones values("
                + "null, "
                + miConstruccion.getIdCiudad() + ", '"
                + miConstruccion.getNombreConstruccion() + "', '"
                + miConstruccion.getPosIkaCon() + "')";
        bd.actualizarTabla(sql);
    }
}
