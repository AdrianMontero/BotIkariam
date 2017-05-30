/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Core.Bot;
import Objetos.Ciudad;
import Core.CoreBD;
import Interfaz.JFPrincipal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ADRIAN
 */
public class HiloPrincipal extends Thread {

    //Clases de gestion
    Bot miBot = new Bot();
    Ciudad gestorCiudades = new Ciudad();
    Edificio gestorEdificios = new Edificio();
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
                    miCiudad.setIdCiudad(i); //Asignamos el id a la ciudad

                    //ACTUALIZACION DE RECURSOS
                    actualizarRecursos(miCiudad); //Guardamos los recursos actualizados en la BD

                    //ACTUALIZAR EDIFICIOS ISLA
                    miBot.mostrarCiudad(); //Nos posicionamos en la ciudad para hacer las comprobaciones de los edificios.
                    System.out.println("Estoy en la ciudad  " + miCiudad.getNombreCiu());

                    //Obtenemos los edificios de la BD
                    ArrayList<Edificio> misEdificios = new ArrayList(); //Constiene los edificios a construir almacenados en la BD
                    misEdificios = miBot.getEdificiosPorCiudad(i); //Conseguimos la lista de edificios por ciudad de la BD
                    //Comprobamos 1x1 si se pueden subir (en caso de que el primero no se pueda se pasa a comprobar los edificios de la siguiente isla).
                    for (Edificio miEdificio : misEdificios) {
                        WebElement btnIkaEdi = driverClassHP.findElement(By.id(miEdificio.getPosIkaEdi()));
                        Thread.sleep(1000);
                        btnIkaEdi.click();
                        Thread.sleep(1000);
                        WebElement btnUpdate = driverClassHP.findElement(By.id("js_buildingUpgradeButton"));
                        if (btnUpdate.getAttribute("class").equals("action_btn disabled")) { //En caso de que este "disabled" no se puede subir, asi que no hacemos nada y pasamos a la siguiente isla
                            //No hacemos nada
                        } else { //Si el boton no esta bloqueado
                            btnUpdate.click(); //Lo pulsamos para subir el edificio
                            gestorEdificios.eliminarEdificioBD(miCiudad.getIdCiudad(), miEdificio.getNombreEdificio()); //Si lo actualizamos hay que borrar el edificio de la BD
                        }
                        break; //Solo queremos que actualize el primer edificio de la lista
                    }

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
                JOptionPane.showMessageDialog(null, "Error en la ejecucion del hilo", "Hilo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la conexion de la base de datos con el hilo principal del programa", "Hilo", JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Metodo usado para actualizar los recursos de una ciudad determinada en la
     * BD
     *
     * @param miCiudad Ciudad la cual contiene los recursos actualizados
     * @throws SQLException Error al conectar con la BD
     */
    private void actualizarRecursos(Ciudad miCiudad) throws SQLException {
        sql = "update ciudades set"
                + " madera_ciu = " + miCiudad.getMaderaCiu()
                + ", vino_ciu = " + miCiudad.getVinoCiu()
                + ", marmol_ciu = " + miCiudad.getMarmolCiu()
                + ", cristal_ciu = " + miCiudad.getCristalCiu()
                + ", azufre_ciu = " + miCiudad.getAzufreCiu()
                + " where id_ciu = " + miCiudad.getIdCiudad();

        bd.actualizarTabla(sql);
    }

}
