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
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author ADRIAN
 */
public class Ciudad {

    WebDriver driverClassciu = JFPrincipal.driverFull;
    //Metodos para interactuar con la BD
    private static CoreBD bd = new CoreBD(); //Variable usada para interactuar con la BD
    private static String sql = null; //Variable usada para contener los mensajes sql para interactuar con la BD
    private static ResultSet rs = null; //Variable que almacena los resultados devueltos por la BD

    //Atributos
    private int idCiudad;
    private String nombreCiu;
    private int coordXCiu;
    private int coordYCiu;
    private int maderaCiu;
    private int vinoCiu;
    private int marmolCiu;
    private int cristalCiu;
    private int azufreCiu;

    //Metodos
    /**
     * Metodo que devuelve la informacion de la isla en la que estas
     *
     * @return
     * @throws InterruptedException
     */
    public Ciudad getInfoCity() throws InterruptedException {
        //Nos posicionamos en la ventana de isla (es donde esta dota la informacion que necesitamos)
        WebElement btnIsla = driverClassciu.findElement(By.className("viewIsland"));
        btnIsla.click();

        Thread.sleep(1000); //Una vez en ella esperamos.

        //Camturamos toda la informacion de la ciudad necesaria para guardar en la BD
        WebElement contCordX = driverClassciu.findElement(By.id("inputXCoord"));
        WebElement contCordY = driverClassciu.findElement(By.id("inputYCoord"));
        WebElement woodCity = driverClassciu.findElement(By.id("js_GlobalMenu_wood"));
        WebElement wineCity = driverClassciu.findElement(By.id("js_GlobalMenu_wine"));
        WebElement marbleCity = driverClassciu.findElement(By.id("js_GlobalMenu_marble"));
        WebElement crystalCity = driverClassciu.findElement(By.id("js_GlobalMenu_crystal"));
        WebElement sulfurCity = driverClassciu.findElement(By.id("js_GlobalMenu_sulfur"));

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
        List<WebElement> islas = driverClassciu.findElements(By.className("ownCity"));
        String nombreIsla;
        nombreIsla = islas.get(0).getText();

        //Guardamos y mandamos la informacion
        Ciudad miCiudad = new Ciudad(nombreIsla, cordX, cordY, wood, wine, marble, crystal, sulfur);
        return miCiudad;
    }

    /**
     * Metodo que guarda una ciudad en la base de datos usando el metodo
     * "stockCityBD.
     *
     * @param nCiudadEnDesplegable Posicion en el menu desplegable de la ciudad
     * que vamos a mirar
     * @throws InterruptedException Error con el sleep al mirar la ciudad
     */
    public void stockCity(int nCiudadEnDesplegable) throws InterruptedException, SQLException {
        Ciudad miCiudad; //Usado para almacenar lo devuelto por el metodo setInfoCities
        WebElement desplegableIslas;
        Thread.sleep(10); //Despues de hacer click esperamos 10 milisegundos

        List<WebElement> islas = driverClassciu.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu

        desplegableIslas = driverClassciu.findElement(By.id("js_citySelectContainer"));
        desplegableIslas.click();
        Thread.sleep(1000);
        islas = driverClassciu.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu (necesario repetirlo para que no se quede bug)
        Thread.sleep(1000);
        islas.get(nCiudadEnDesplegable).click();
        Thread.sleep(1000);
        miCiudad = getInfoCity();//Guardamos la informacion de la isla

        //Guardamos la informacion de la ciudad en la BD
        stockCityBD(miCiudad);
    }

    public void lookCities() throws InterruptedException {
        Ciudad miCiudad; //Usado para almacenar lo devuelto por el metodo setInfoCities
        //Click en el desplegable de las ciudades
        WebElement desplegableIslas;

        Thread.sleep(10); //Despues de hacer click esperamos 10 milisegundos

        List<WebElement> islas = driverClassciu.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu

        for (int i = 1; i < islas.size(); i++) { //Por cada elemento...
            desplegableIslas = driverClassciu.findElement(By.id("js_citySelectContainer"));
            miCiudad = new Ciudad();
            desplegableIslas.click();
            Thread.sleep(1000);
            islas = driverClassciu.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu (necesario repetirlo para que no se quede bug)
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
    public int askNumberCitiesBD() throws SQLException {
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
        List<WebElement> islas = driverClassciu.findElements(By.className("ownCity"));
        nCities = (islas.size() - 1);
        return nCities;
    }

    /**
     * Guarda una ciudad en la base de datos (usado por metodo "StockCity)
     *
     * @param miCiudad Ciudad a guardar (con los datos rellenos)
     * @throws SQLException Error al insertar la ciudad en la BD.
     */
    private void stockCityBD(Ciudad miCiudad) throws SQLException {
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
    public static ArrayList<Ciudad> ListCitiesPlayerBD() throws SQLException {
        String sql2;
        ResultSet rs2;
        ArrayList<Ciudad> misCiudades = new ArrayList();
        Ciudad miCiudad;
        sql2 = "select * from ciudades";
        rs2 = bd.consultarTabla(sql2);
        while (rs2.next()) {
            miCiudad = new Ciudad();
            miCiudad.setIdCiudad(rs2.getInt("id_ciu"));
            miCiudad.setNombreCiu(rs2.getString("nombre_ciu"));
            miCiudad.setCoordXCiu(rs2.getInt("coord_x_ciu"));
            miCiudad.setCoordYCiu(rs2.getInt("coord_y_ciu"));
            miCiudad.setMaderaCiu(rs2.getInt("madera_ciu"));
            miCiudad.setVinoCiu(rs2.getInt("vino_ciu"));
            miCiudad.setMarmolCiu(rs2.getInt("marmol_ciu"));
            miCiudad.setCristalCiu(rs2.getInt("cristal_ciu"));
            miCiudad.setAzufreCiu(rs2.getInt("azufre_ciu"));
            misCiudades.add(miCiudad);
        }
        return misCiudades;
    }

    //Getters y setters
    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiu() {
        return nombreCiu;
    }

    public void setNombreCiu(String nombreCiu) {
        this.nombreCiu = nombreCiu;
    }

    public int getCoordXCiu() {
        return coordXCiu;
    }

    public void setCoordXCiu(int coordXCiu) {
        this.coordXCiu = coordXCiu;
    }

    public int getCoordYCiu() {
        return coordYCiu;
    }

    public void setCoordYCiu(int coordYCiu) {
        this.coordYCiu = coordYCiu;
    }

    public int getMaderaCiu() {
        return maderaCiu;
    }

    public void setMaderaCiu(int maderaCiu) {
        this.maderaCiu = maderaCiu;
    }

    public int getVinoCiu() {
        return vinoCiu;
    }

    public void setVinoCiu(int vinoCiu) {
        this.vinoCiu = vinoCiu;
    }

    public int getMarmolCiu() {
        return marmolCiu;
    }

    public void setMarmolCiu(int marmolCiu) {
        this.marmolCiu = marmolCiu;
    }

    public int getCristalCiu() {
        return cristalCiu;
    }

    public void setCristalCiu(int cristalCiu) {
        this.cristalCiu = cristalCiu;
    }

    public int getAzufreCiu() {
        return azufreCiu;
    }

    public void setAzufreCiu(int azufreCiu) {
        this.azufreCiu = azufreCiu;
    }

    //Constructores
    /**
     * Constructor completo para la ciudad
     *
     * @param idCiudad id de la ciudad
     * @param nombreCiu Nombre de la ciudad
     * @param coordXCiu Coordenada x de la ciudad
     * @param coordYCiu Coordenada y de la ciudad
     * @param maderaCiu Madera en la ciudad
     * @param vinoCiu Vino en la ciudad
     * @param marmolCiu Marmol en la ciudad
     * @param cristalCiu Cristal en la ciudad
     * @param azufreCiu Azufre en la ciudad
     */
    public Ciudad(int idCiudad, String nombreCiu, int coordXCiu, int coordYCiu, int maderaCiu, int vinoCiu, int marmolCiu, int cristalCiu, int azufreCiu) {
        this.idCiudad = idCiudad;
        this.nombreCiu = nombreCiu;
        this.coordXCiu = coordXCiu;
        this.coordYCiu = coordYCiu;
        this.maderaCiu = maderaCiu;
        this.vinoCiu = vinoCiu;
        this.marmolCiu = marmolCiu;
        this.cristalCiu = cristalCiu;
        this.azufreCiu = azufreCiu;
    }

    /**
     * Constructor sin id
     *
     * @param nombreCiu Nombre de la ciudad
     * @param coordXCiu Coordenada x de la ciudad
     * @param coordYCiu Coordenada y de la ciudad
     * @param maderaCiu Madera en la ciudad
     * @param vinoCiu Vino en la ciudad
     * @param marmolCiu Marmol en la ciudad
     * @param cristalCiu Cristal en la ciudad
     * @param azufreCiu Azufre en la ciudad
     */
    public Ciudad(String nombreCiu, int coordXCiu, int coordYCiu, int maderaCiu, int vinoCiu, int marmolCiu, int cristalCiu, int azufreCiu) {
        this.nombreCiu = nombreCiu;
        this.coordXCiu = coordXCiu;
        this.coordYCiu = coordYCiu;
        this.maderaCiu = maderaCiu;
        this.vinoCiu = vinoCiu;
        this.marmolCiu = marmolCiu;
        this.cristalCiu = cristalCiu;
        this.azufreCiu = azufreCiu;
    }

    /**
     * Constructor solo para guardar los recursos para una ciudad determinada
     *
     * @param idCiudad id de la ciudad
     * @param maderaCiu Madera en la ciudad
     * @param vinoCiu Vino en la ciudad
     * @param marmolCiu Marmol en la ciudad
     * @param cristalCiu Cristal en la ciudad
     * @param azufreCiu Azufre en la ciudad
     */
    public Ciudad(int idCiudad, int maderaCiu, int vinoCiu, int marmolCiu, int cristalCiu, int azufreCiu) {
        this.idCiudad = idCiudad;
        this.maderaCiu = maderaCiu;
        this.vinoCiu = vinoCiu;
        this.marmolCiu = marmolCiu;
        this.cristalCiu = cristalCiu;
        this.azufreCiu = azufreCiu;
    }

    /**
     * Constructor vacio
     */
    public Ciudad() {
    }

}
