/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Core.Bot;
import Core.CoreBD;
import Interfaz.JFPrincipal;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author ADRIAN
 */
public class HiloPrincipal extends Thread {

    //Clases de gestion
    Bot miBot = new Bot();
    Ciudad gestorCiudades = new Ciudad();
    WebDriver driverClassHP = JFPrincipal.driverFull;

    //Metodos para interactuar con la BD
    private static CoreBD bd = new CoreBD(); //Variable usada para interactuar con la BD
    private static String sql = null; //Variable usada para contener los mensajes sql para interactuar con la BD
    private static ResultSet rs = null; //Variable que almacena los resultados devueltos por la BD

    @Override
    public void run() {
        while (true) {
            try {
                Ciudad miCiudad; //Usado para almacenar lo devuelto por el metodo setInfoCities
                //Click en el desplegable de las ciudades
                WebElement desplegableIslas;

                Thread.sleep(10); //Despues de hacer click esperamos 10 milisegundos

                List<WebElement> islas = driverClassHP.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu

                for (int i = 1; i < islas.size(); i++) { //Por cada elemento...
                    desplegableIslas = driverClassHP.findElement(By.id("js_citySelectContainer"));
                    miCiudad = new Ciudad();
                    desplegableIslas.click();
                    Thread.sleep(1000);
                    islas = driverClassHP.findElements(By.className("ownCity")); //Capturamos los elementos desplegados en el menu (necesario repetirlo para que no se quede bug)
                    Thread.sleep(1000);
                    islas.get(i).click(); //Hacemos click en las islas (por orden "i"
                    Thread.sleep(1000);

                    miCiudad = miCiudad.getInfoCity();//Guardamos la informacion de la isla (recursos, nombre y coordenadas)

                    //ACTUALIZAR EDIFICIOS ISLA
                    miBot.mostrarCiudad(); //Nos posicionamos en la ciudad para hacer las comprobaciones de los edificios.
                    System.out.println("Estoy en la ciudad  " + miCiudad.getNombreCiu());

                    //REALIZAR EXPERIMENTOS ISLA
                    //REALIZAR DONACIONES ISLA
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
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error con los sleep del hilo principal");
            }

        }

    }

}
