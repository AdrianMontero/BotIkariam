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
import org.openqa.selenium.support.ui.Sleeper;

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
     * Guarda un edificio en la base de datos (uso desde toda la web)
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
     * Devuelve la lista completa de los edificios que hay en la lista de
     * construccion (uso desde toda la web)
     *
     * @param islandId Isla de la cual queremos encontrar los edificios en cola
     * @return ArrayList de tipo Edificio conteniendo todos los edificios en la
     * isla para ese id
     * @throws SQLException Error al recuperar la lista de edificios de la isla.
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
     * Metodo que devuelve la informacion de la isla en la que estas
     *
     * @return
     * @throws InterruptedException
     */
    public Ciudad getInfoCity() throws InterruptedException {
        //Nos posicionamos en la ventana de isla (es donde esta dota la informacion que necesitamos)
        WebElement btnIsla = driverBot.findElement(By.className("viewIsland"));
        btnIsla.click();

        Thread.sleep(1000); //Una vez en ella esperamos.

        //Camturamos toda la informacion de la ciudad necesaria para guardar en la BD
        WebElement contCordX = driverBot.findElement(By.id("inputXCoord"));
        WebElement contCordY = driverBot.findElement(By.id("inputYCoord"));
        WebElement woodCity = driverBot.findElement(By.id("js_GlobalMenu_wood"));
        WebElement wineCity = driverBot.findElement(By.id("js_GlobalMenu_wine"));
        WebElement marbleCity = driverBot.findElement(By.id("js_GlobalMenu_marble"));
        WebElement crystalCity = driverBot.findElement(By.id("js_GlobalMenu_crystal"));
        WebElement sulfurCity = driverBot.findElement(By.id("js_GlobalMenu_sulfur"));

        //Guardamos los recursos en variables
        String woodText = woodCity.getText();
        String wineText = wineCity.getText();
        String marbleText = marbleCity.getText();
        String crystalText = crystalCity.getText();
        String sulfurText = sulfurCity.getText();

        //Quitamos la "," en caso de que tengamos mas de 999 recursos
        woodText = woodText.replace(",", "");
        wineText = wineText.replace(",", "");
        marbleText = marbleText.replace(",", "");
        crystalText = crystalText.replace(",", "");
        sulfurText = sulfurText.replace(",", "");

        //Tranformamos los String en int
        int wood = Integer.valueOf(woodText);
        int wine = Integer.valueOf(wineText);
        int marble = Integer.valueOf(marbleText);
        int crystal = Integer.valueOf(crystalText);
        int sulfur = Integer.valueOf(sulfurText);
        int cordX = Integer.valueOf(contCordX.getAttribute("value"));
        int cordY = Integer.valueOf(contCordY.getAttribute("value"));

        //Capturamos la informacion del nombre de la isla
        List<WebElement> islas = driverBot.findElements(By.className("ownCity"));
        String nombreIsla;
        nombreIsla = islas.get(0).getText();

        //Guardamos y mandamos la informacion
        Ciudad miCiudad = new Ciudad(nombreIsla, cordX, cordY, wood, wine, marble, crystal, sulfur);
        return miCiudad;
    }

    /**
     * Metodo que se posiciona sobre la ciudad que queremos mirar
     * @param nCiudadEnDesplegable Posicion en el menu desplegable de la ciudad que vamos a mirar
     * @throws InterruptedException Error con el sleep al mirar la ciudad
     */
    public void lookCity(int nCiudadEnDesplegable) throws InterruptedException, SQLException {
        Ciudad miCiudad; //Usado para almacenar lo devuelto por el metodo setInfoCities
        WebElement desplegableIslas;
        Thread.sleep(10); //Despues de hacer click esperamos 10 milisegundos

        List<WebElement> islas = driverBot.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu

        desplegableIslas = driverBot.findElement(By.id("js_citySelectContainer"));
        desplegableIslas.click();
        Thread.sleep(1000);
        islas = driverBot.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu (necesario repetirlo para que no se quede bug)
        Thread.sleep(1000);
        islas.get(nCiudadEnDesplegable).click();
        Thread.sleep(1000);
        miCiudad = getInfoCity();//Guardamos la informacion de la isla
        
        //Guardamos la informacion de la ciudad en la BD
        stockCity(miCiudad);
    }

    public void lookCities() throws InterruptedException {
        Ciudad miCiudad; //Usado para almacenar lo devuelto por el metodo setInfoCities
        //Click en el desplegable de las ciudades
        WebElement desplegableIslas;

        Thread.sleep(10); //Despues de hacer click esperamos 10 milisegundos

        List<WebElement> islas = driverBot.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu

        for (int i = 1; i < islas.size(); i++) { //Por cada elemento...
            desplegableIslas = driverBot.findElement(By.id("js_citySelectContainer"));
            miCiudad = new Ciudad();
            desplegableIslas.click();
            Thread.sleep(1000);
            islas = driverBot.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu (necesario repetirlo para que no se quede bug)
            Thread.sleep(1000);
            islas.get(i).click();
            Thread.sleep(1000);

            miCiudad = getInfoCity();//Guardamos la informacion de la isla

            //Mostramos por consola la informacion devuelta
            System.out.println("------------------------");
            System.out.println(miCiudad.getNombreCiu());
            System.out.println(miCiudad.getCoordXCiu());
            System.out.println(miCiudad.getCoordYCiu());
            System.out.println(miCiudad.getMaderaCiu());
            System.out.println(miCiudad.getVinoCiu());
            System.out.println(miCiudad.getMarmolCiu());
            System.out.println(miCiudad.getCristalCiu());
            System.out.println(miCiudad.getAzufreCiu());
            System.out.println("------------------------");

            Thread.sleep(1000);
        }
    }

    /**
     * Devuelve el numero de ciudades que hay guardadas en el servidor (uso
     * desde toda la web)
     *
     * @return numero de ciudades en el servidor
     * @throws SQLException Error al ejecutar el sql de recuento de ciudades
     */
    public int askCitiesBD() throws SQLException {
        int nCities = 100; //Numero de ciudades almacenadas en el servidor
        sql = "SELECT count(id_ciu) AS 'ciudades' FROM ciudades";
        rs = bd.consultarTabla(sql);
        while (rs.next()) {
            nCities = rs.getInt("ciudades");
        }
        return nCities; //En caso de dar 100 significa que algo falla.
    }

    /**
     * Devuelve el numero de islas que hay en ikariam (uso desde toda la web)
     *
     * @return
     */
    public int askCitiesIka() {
        int nCities;
        //WebElement desplegableIslas = driverBot.findElement(By.id("js_citySelectContainer")); //Seleccionamos el desplegable de las islas
        //desplegableIslas.click();
        List<WebElement> islas = driverBot.findElements(By.className("ownCity"));
        nCities = (islas.size() - 1);
        return nCities;
    }

    /**
     * Guarda una ciudad en la base de datos (uso desde toda la web)
     *
     * @param miCiudad Ciudad a guardar (con los datos rellenos)
     * @throws SQLException Error al insertar la ciudad en la BD.
     */
    public void stockCity(Ciudad miCiudad) throws SQLException {
        sql = "insert into ciudades values("
                + "null, '"
                + miCiudad.getNombreCiu() + "', "
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
     * Metodo que devuelve la lista de ciudades del jugador con toda su
     * informacion (uso desde toda la web)
     *
     * @return ArrayList del tipo ciudad con toda la informacion completa
     * @throws SQLException Error al recuperar la informacion de las ciudades
     */
    public ArrayList<Ciudad> ListCitiesPlayerBD() throws SQLException {
        ArrayList<Ciudad> misCiudades = new ArrayList();
        Ciudad miCiudad;
        sql = "select * from ciudades";
        rs = bd.consultarTabla(sql);
        while (rs.next()) {
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
